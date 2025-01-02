package parsix.core

import dev.forkhandles.result4k.Success
import kotlin.reflect.KClass

typealias ParseMap<O> = Parse<Map<String, Any?>, O>

/**
 * Start building a complex parser using a generic [Map] as input.
 * This is quite useful when you have some unstructured data coming from a CSV, an
 * HTTP Request, etc...
 *
 * See [tests][ParseMapKtTest] to understand how to use it.
 * @see dev.forkhandles.partial.invoke
 */
fun <A, B> parseInto(f: (A) -> B): ParseMap<(A) -> B> =
    { _ -> Success(f) }

/**
 * Start building a complex parser using a generic object [T] as input.
 * This is quite useful when you have already deserialized your raw stream into an object,
 * but needs further refinement before it can be given to your business logic.
 *
 * See [tests][parsix.core.greedy.ParseObjKtTest] to understand how to use it.
 * @see dev.forkhandles.partial.invoke
 */
fun <T : Any, A, B> parseInto(
    @Suppress("UNUSED_PARAMETER") _typeinference: KClass<T>,
    f: (A) -> B
): Parse<T, (A) -> B> =
    { _ -> Success(f) }