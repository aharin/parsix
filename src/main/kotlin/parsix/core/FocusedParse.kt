package parsix.core

import dev.forkhandles.result4k.Success
import dev.forkhandles.result4k.Failure


/**
 * Focus on an aspect of the input and parse it.
 * In case of failure, it will be mapped accordingly to [mapErr].
 *
 * @sample parsix.core.FocusedParseKtTest
 */
inline fun <I, T, O> focusedParse(
    crossinline focus: (I) -> T,
    crossinline parse: Parse<T, Any?>,
    crossinline mapOk: (I) -> O,
    crossinline mapErr: (I, ParseError) -> ParseError,
): Parse<I, O> = { inp ->
    when (val parsed = parse(focus(inp))) {
        is Success ->
            Success(mapOk(inp))

        is Failure ->
            Failure(mapErr(inp, parsed.reason))
    }
}