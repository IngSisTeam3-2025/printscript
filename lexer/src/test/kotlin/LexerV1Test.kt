//package lexer.tests
//
//import LexicalAnalyzer
//import lexer.tests.definitions.HaystackDefinitions
//import lexer.tests.reader.MainFileReader
//import lexer.tests.tester.GoldenFileTester
//import org.junit.jupiter.api.Test
//
//class LexerV1Test {
//
//    private val goldenTester = GoldenFileTester("src/test/resources/golden")
//    private val mainReader = MainFileReader("src/test/resources/main")
//
//    @Test
//    fun `test-haystack`() {
//        val text = mainReader.readAsString("test-haystack")
//
//        val lexer = LexicalAnalyzer(
//            input = text.iterator(),
//            matchers = listOf(
//                HaystackDefinitions.hay to HaystackDefinitions.HAY,
//                HaystackDefinitions.needle to HaystackDefinitions.NEEDLE
//            )
//        )
//
//        val tokens = generateSequence { if (lexer.hasNext()) lexer.next().getOrNull() else null }.toList()
//
//        tokens.forEach {
//            println("${it.type.name}: '${it.lexeme}' at ${it.start.line},${it.start.column}")
//        }
//
//        val hayCount = tokens.count { it.type.name == "HAY" }
//        val needleTokens = tokens.filter { it.type.name == "NEEDLE" }
//
//        val output = buildString {
//            appendLine("HAY: $hayCount")
//            appendLine("NEEDLE: ${needleTokens.size}")
//            if (needleTokens.isNotEmpty()) {
//                val positions = needleTokens.joinToString(",") {
//                    "${it.start.line},${it.start.column}"
//                }
//                appendLine("NEEDLE_POS: $positions")
//            }
//        }
//
//        goldenTester.assertMatchesGolden("test-haystack", output)
//    }
//}
