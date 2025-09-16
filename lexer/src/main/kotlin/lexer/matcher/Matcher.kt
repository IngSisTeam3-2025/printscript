package lexer.matcher

interface Matcher<M> {
    fun match(input: CharSequence): M?
}
