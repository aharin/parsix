package parsix.core.greedy

import dev.forkhandles.result4k.Failure
import dev.forkhandles.result4k.Success
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import parsix.core.IndexError
import parsix.core.ManyErrors
import parsix.core.Parse
import parsix.test.TestError

internal class ParseCollectionKtTest {
    @Test
    fun `it parses all items when successful`() {
        assertEquals(
            Success(listOf("1", "2", "3")),
            manyOf { inp: Int -> Success(inp.toString()) }
                .invoke(listOf(1, 2, 3))
        )
    }

    @Test
    fun `it returns all errors on failure`() {
        val parse: Parse<Int, String> = { inp: Int ->
            if (inp % 2 == 0)
                TestError.of("failed ${inp}")
            else
                Success(inp.toString())
        }

        assertEquals(
            Failure(
                ManyErrors(
                    setOf(
                        IndexError(1, TestError("failed 2")),
                        IndexError(3, TestError("failed 4")),
                    )
                )
            ),
            manyOf(parse)
                .invoke(listOf(1, 2, 3, 4, 5))
        )
    }
}