package parsix.core.greedy

import dev.forkhandles.result4k.Success
import dev.forkhandles.result4k.mapFailure
import parsix.core.IndexError
import parsix.core.Parse
import parsix.core.Parsed

fun <I, O> manyOf(
    parse: Parse<I, O>
): Parse<Iterable<I>, List<O>> = { inp ->
    inp.foldIndexed(
        Success(ArrayList<O>()) as Parsed<ArrayList<O>>
    ) { i, z, item ->
        lift2(
            z,
            parse(item).mapFailure { IndexError(i, it) }
        ) { zv, iv ->
            zv.add(iv)
            Success(zv)
        }
    }
}