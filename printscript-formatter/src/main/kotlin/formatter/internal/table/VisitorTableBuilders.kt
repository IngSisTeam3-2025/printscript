package formatter.internal.table

import formatter.internal.rule.LineBreakAfterStatementRule
import formatter.internal.rule.LineBreaksAfterPrintlnRule
import formatter.internal.rule.MandatorySingleSpaceRule
import formatter.internal.rule.NoSpacingAroundEqualsRule
import formatter.internal.rule.SpacingAfterColonRule
import formatter.internal.rule.SpacingAroundEqualsRule
import formatter.internal.rule.SpacingAroundOperatorRule
import formatter.internal.rule.SpacingBeforeColonRule
import formatter.internal.visitor.factory.LineBreakAfterStatementVisitorFactory
import formatter.internal.visitor.factory.LineBreaksAfterPrintlnVisitorFactory
import formatter.internal.visitor.factory.MandatorySingleSpaceVisitorFactory
import formatter.internal.visitor.factory.NoSpacingAroundEqualsVisitorFactory
import formatter.internal.visitor.factory.SpacingAfterColonVisitorFactory
import formatter.internal.visitor.factory.SpacingAroundEqualsVisitorFactory
import formatter.internal.visitor.factory.SpacingAroundOperatorVisitorFactory
import formatter.internal.visitor.factory.SpacingBeforeColonVisitorFactory
import formatter.internal.visitor.factory.VisitorFactory
import model.node.DivideNode
import model.node.MinusNode
import model.node.MultiplyNode
import model.node.PlusNode

internal object PrintScriptV10 : VisitorTableBuilder {
    override val factories: Map<String, VisitorFactory> = mapOf(
        NoSpacingAroundEqualsRule.signature to NoSpacingAroundEqualsVisitorFactory(),
        SpacingAroundEqualsRule.signature to SpacingAroundEqualsVisitorFactory(),
        SpacingBeforeColonRule.signature to SpacingBeforeColonVisitorFactory(),
        SpacingAfterColonRule.signature to SpacingAfterColonVisitorFactory(),
        MandatorySingleSpaceRule.signature to MandatorySingleSpaceVisitorFactory(),
        LineBreaksAfterPrintlnRule.signature to LineBreaksAfterPrintlnVisitorFactory(),
        SpacingAroundOperatorRule.signature to SpacingAroundOperatorVisitorFactory(
            listOf(PlusNode, MinusNode, MultiplyNode, DivideNode),
        ),
        LineBreakAfterStatementRule.signature to LineBreakAfterStatementVisitorFactory(),
    )
}

internal object PrintScriptV11 : VisitorTableBuilder {
    override val factories: Map<String, VisitorFactory> = PrintScriptV10.factories + mapOf()
}
