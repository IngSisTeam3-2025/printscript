import org.junit.jupiter.api.Test

class LexerV11GoldenTest : BaseLexerGoldenTest() {

    @Test
    fun testConstDecl() = runGoldenTest("1.1", "const_decl")

    @Test
    fun testIfElse() = runGoldenTest("1.1", "if_else")

    @Test
    fun testReadInputEnv() = runGoldenTest("1.1", "read_input_env")
}
