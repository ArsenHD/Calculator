package ru.itmo.sd.calculator.visitor

import ru.itmo.sd.calculator.tree.Brace
import ru.itmo.sd.calculator.tree.NumberToken
import ru.itmo.sd.calculator.tree.Operation
import ru.itmo.sd.calculator.tree.Token

class CalcVisitor: ParserVisitor() {
    val result: Int
        get() {
            require(stack.size == 1) { "The computation is not yet finished" }
            val token = stack.last()
            require(token is NumberToken) { "Number expected, but got '$token'" }
            return token.value
        }

    private val stack = ArrayDeque<Token>()

    override fun visit(token: Operation) {
        val value = when (val operation = token.operation) {
            '+' -> binaryOperation(Int::plus)
            '-' -> binaryOperation(Int::minus)
            '*' -> binaryOperation(Int::times)
            '/' -> binaryOperation(Int::div)
            else -> error("Unknown operation $operation")
        }
        stack.addLast(NumberToken(value))
    }

    override fun visit(token: NumberToken) {
        stack.addLast(token)
    }

    override fun visit(token: Brace) = Unit

    private fun binaryOperation(operation: Int.(Int) -> Int): Int {
        require(stack.size >= 2) { "At least 2 elements on stack are required, but found: ${stack.size}"}
        val right = stack.removeLast()
        val left = stack.removeLast()
        require(left is NumberToken) { "Number expected, but got '$left'" }
        require(right is NumberToken) { "Number expected, but got '$right'" }
        return left.value.operation(right.value)
    }
}