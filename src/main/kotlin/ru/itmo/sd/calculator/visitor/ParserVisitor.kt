package ru.itmo.sd.calculator.visitor

import ru.itmo.sd.calculator.tree.Brace
import ru.itmo.sd.calculator.tree.NumberToken
import ru.itmo.sd.calculator.tree.Operation
import ru.itmo.sd.calculator.tree.Token

open class ParserVisitor: TokenVisitor {
    private val operators = ArrayDeque<Token>()
    private val queue = mutableListOf<Token>()

    val tokens: List<Token>
        get() {
            while (operators.isNotEmpty()) {
                queue += operators.removeLast()
            }
            return queue
        }

    override fun visit(token: Brace) {
        if (token.isOpen) {
            operators.addLast(token)
        } else {
            val openBrace = Brace('(')
            while (operators.last() != openBrace) {
                queue += operators.removeLast()
            }
            require(operators.last() == openBrace) { "Unmatched parentheses" }
            operators.removeLast()
        }
    }

    override fun visit(token: Operation) {
        var last = operators.lastOrNull()
        while (last is Operation && precedence.getValue(last) >= precedence.getValue(token)) {
            queue += operators.removeLast()
            last = operators.lastOrNull()
        }
        operators.addLast(token)
    }

    override fun visit(token: NumberToken) {
        queue += token
    }

    private val precedence = mapOf(
        Operation('+') to 0,
        Operation('-') to 0,
        Operation('*') to 1,
        Operation('/') to 1,
    )
}