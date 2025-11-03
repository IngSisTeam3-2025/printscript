package formatter.internal.table

import formatter.internal.rule.IfBraceBelowLineRule
import formatter.internal.rule.IfBraceSameLineRule
import formatter.internal.rule.IndentsInsideIfBlockRule
import formatter.internal.rule.LineBreakAfterStatementRule
import formatter.internal.rule.LineBreaksAfterPrintlnRule
import formatter.internal.rule.MandatorySingleSpaceRule
import formatter.internal.rule.NoSpacingAroundEqualsRule
import formatter.internal.rule.SpacingAfterColonRule
import formatter.internal.rule.SpacingAroundEqualsRule
import formatter.internal.rule.SpacingAroundOperatorRule
import formatter.internal.rule.SpacingBeforeColonRule
import formatter.internal.visitor.factory.ContextVisitorFactory
import formatter.internal.visitor.factory.IfBraceBelowLineVisitorFactory
import formatter.internal.visitor.factory.IfBraceSameLineVisitorFactory
import formatter.internal.visitor.factory.IndentsInsideIfVisitorFactory
import formatter.internal.visitor.factory.LineBreakAfterStatementVisitorFactory
import formatter.internal.visitor.factory.LineBreaksAfterPrintlnVisitorFactory
import formatter.internal.visitor.factory.MandatorySingleSpaceVisitorFactory
import formatter.internal.visitor.factory.NoSpacingAroundEqualsVisitorFactory
import formatter.internal.visitor.factory.SpacingAfterColonVisitorFactory
import formatter.internal.visitor.factory.SpacingAroundEqualsVisitorFactory
import formatter.internal.visitor.factory.SpacingAroundOperatorVisitorFactory
import formatter.internal.visitor.factory.SpacingBeforeColonVisitorFactory
import model.node.DivideNode
import model.node.MinusNode
import model.node.MultiplyNode
import model.node.PlusNode

internal object PrintScriptV10 : ContextVisitorTableBuilder {
    override val factories: Map<String, ContextVisitorFactory> = mapOf(
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

internal object PrintScriptV11 : ContextVisitorTableBuilder {
    override val factories: Map<String, ContextVisitorFactory> = PrintScriptV10.factories + mapOf(
        IndentsInsideIfBlockRule.signature to IndentsInsideIfVisitorFactory(),
        IfBraceSameLineRule.signature to IfBraceSameLineVisitorFactory(),
        IfBraceBelowLineRule.signature to IfBraceBelowLineVisitorFactory(),
    )
}
