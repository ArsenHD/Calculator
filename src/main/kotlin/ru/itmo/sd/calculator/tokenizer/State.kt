package ru.itmo.sd.calculator.tokenizer

import ru.itmo.sd.calculator.tree.Brace
import ru.itmo.sd.calculator.tree.NumberToken
import ru.itmo.sd.calculator.tree.Operation
import ru.itmo.sd.calculator.tree.Token

sealed class State {
    abstract fun handle(ch: Char, tokenizer: Tokenizer)

    fun braceOrOperator(ch: Char): Token =
            when (ch) {
                in operators -> Operation(ch)
                in braces -> Brace(ch)
                else -> error("Operator or brace expected, but got $ch")
            }

    val operators = listOf('+', '-', '*', '/')
    val braces = listOf('(', ')')
}

object StartState: State() {
    override fun handle(ch: Char, tokenizer: Tokenizer) {
        when {
            ch in operators -> tokenizer.tokens += Operation(ch)
            ch in braces -> tokenizer.tokens += Brace(ch)
            ch.isDigit() -> {
                tokenizer.state = NumberState
                tokenizer.numberBuffer.append(ch)
            }
            ch.isWhitespace() -> Unit
            else -> tokenizer.state = ErrorState
        }
    }
}

object ErrorState: State() {
    override fun handle(ch: Char, tokenizer: Tokenizer) = Unit
}

object NumberState: State() {
    override fun handle(ch: Char, tokenizer: Tokenizer) {
        when {
            ch.isDigit() -> {
                tokenizer.numberBuffer.append(ch)
            }
            ch in operators || ch in braces -> {
                tokenizer.state = StartState
                val number = tokenizer.numberBuffer.toString().toInt()
                tokenizer.tokens += NumberToken(number)
                tokenizer.numberBuffer.clear()
                tokenizer.tokens += braceOrOperator(ch)
            }
            ch.isWhitespace() -> Unit
            else -> tokenizer.state = ErrorState
        }
    }
}
