package ru.itmo.sd.calculator.visitor

import ru.itmo.sd.calculator.tree.Brace
import ru.itmo.sd.calculator.tree.NumberToken
import ru.itmo.sd.calculator.tree.Operation

interface TokenVisitor {
    fun visit(token: Brace)
    fun visit(token: Operation)
    fun visit(token: NumberToken)
}