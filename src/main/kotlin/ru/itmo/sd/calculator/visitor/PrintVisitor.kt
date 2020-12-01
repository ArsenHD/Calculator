package ru.itmo.sd.calculator.visitor

import ru.itmo.sd.calculator.tree.Brace
import ru.itmo.sd.calculator.tree.NumberToken
import ru.itmo.sd.calculator.tree.Operation

class PrintVisitor: ParserVisitor() {
    val result: String
        get() = builder.toString()

    private val builder = StringBuilder()

    override fun visit(token: Brace) {
        builder.append(token.brace).append(' ')
    }

    override fun visit(token: NumberToken) {
        builder.append(token.value).append(' ')
    }

    override fun visit(token: Operation) {
        builder.append(token.operation).append(' ')
    }
}