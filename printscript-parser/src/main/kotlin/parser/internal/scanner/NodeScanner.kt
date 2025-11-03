package parser.internal.scanner

import parser.internal.buffer.TokenBuffer
import parser.internal.model.scan.NodeScan
import parser.internal.table.GrammarTable
import type.option.Option
import type.option.Option.None
import type.option.getOrElse
import type.option.maxBy
import type.option.maxByOrEqual
import type.outcome.Outcome

internal class NodeScanner {

    fun scan(
        buffer: TokenBuffer,
        table: GrammarTable,
    ): NodeScan {
        if (!buffer.hasNext()) return NodeScan.Empty

        var sliceSize = 1
        var bestNode: Option<NodeScan.Ok> = None
        var bestError: Option<NodeScan.Error> = None

        while (buffer.hasNext(sliceSize)) {
            val slice = buffer.peek(sliceSize).toList()

            when (val outcome = table.dispatchStatement(slice)) {
                is Outcome.Ok -> {
                    val match = outcome.value
                    val candidate = NodeScan.Ok(match.node, match.consumed)
                    bestNode = bestNode.maxBy({ it.consumed }, candidate)
                }
                is Outcome.Error -> {
                    val fail = outcome.error
                    val candidate = NodeScan.Error(fail.message, fail.category, fail.consumed)
                    bestError = bestError.maxByOrEqual({ it.consumed }, candidate)
                }
            }
            sliceSize++
        }
        return when {
            bestNode is Option.Some && bestError is Option.Some -> {
                if (bestError.value.consumed > bestNode.value.consumed) {
                    bestError.value
                } else {
                    bestNode.value
                }
            }
            else -> bestNode.getOrElse { bestError.getOrElse { NodeScan.Empty } }
        }
    }
}
