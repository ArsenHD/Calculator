package ru.itmo.sd.calculator.tree

import ru.itmo.sd.calculator.visitor.TokenVisitor

interface Token {
    fun accept(visitor: TokenVisitor)
}

data class Brace(val brace: Char) : Token {
    val isOpen: Boolean = brace == '('

    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }
}

data class Operation(val operation: Char): Token {
    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }
}

data class NumberToken(val value: Int) : Token {
    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }
}