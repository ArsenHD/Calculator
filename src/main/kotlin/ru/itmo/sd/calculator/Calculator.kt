package ru.itmo.sd.calculator

import ru.itmo.sd.calculator.tokenizer.Tokenizer
import ru.itmo.sd.calculator.tree.Token
import ru.itmo.sd.calculator.visitor.CalcVisitor
import ru.itmo.sd.calculator.visitor.ParserVisitor
import ru.itmo.sd.calculator.visitor.PrintVisitor
import ru.itmo.sd.calculator.visitor.TokenVisitor

fun main() {
    val input = readLine()!!

    val tokenizer = Tokenizer()
    val parser = ParserVisitor()
    val printer = PrintVisitor()
    val calculator = CalcVisitor()

    val tokens = tokenizer.tokenize(input)

    tokens.accept(parser)
    val reversePolishNotation = parser.tokens

    reversePolishNotation.accept(printer)
    val expression = printer.result

    reversePolishNotation.accept(calculator)
    val result = calculator.result

    println("$expression = $result")
}

fun List<Token>.accept(visitor: TokenVisitor) = forEach { it.accept(visitor) }