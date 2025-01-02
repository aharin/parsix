package parsix.test

import dev.forkhandles.result4k.Failure
import org.junit.jupiter.api.Assertions.fail
import parsix.core.Parse
import parsix.core.ParseError
import parsix.core.TerminalError

fun <I, O> neverCalled(): Parse<I, O> =
    { _ -> fail("it shouldn't have been called") }

data class TestError(val error: String) : TerminalError {
    companion object {
        fun <I, O> lift(err: String): Parse<I, O> =
            { _ -> this.of(err) }

        fun of(err: String = "failed"): Failure<ParseError> =
            Failure(TestError(err))
    }
}
