package ru.itmo.sd.calculator.tokenizer

import ru.itmo.sd.calculator.tree.NumberToken
import ru.itmo.sd.calculator.tree.Token

class Tokenizer {
    var state: State = StartState
    val tokens = mutableListOf<Token>()
    val numberBuffer = StringBuilder()

    fun tokenize(input: String): List<Token> {
        tokens.clear()
        input.forEach {
            state.handle(it, this)
        }
        if (numberBuffer.isNotEmpty()) {
            tokens += NumberToken(numberBuffer.toString().toInt())
        }
        return tokens
    }
}
