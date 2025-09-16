import org.junit.jupiter.api.Test

class LexerV10GoldenTest : BaseLexerGoldenTest() {

    @Test
    fun testVarDecl() = runGoldenTest("1.0", "var_decl")

    @Test
    fun testPrintlnConcat() = runGoldenTest("1.0", "println_concat")

    @Test
    fun testInvalidChar() = runGoldenTest("1.0", "invalid")
}
