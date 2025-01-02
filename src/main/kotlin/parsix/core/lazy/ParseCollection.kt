package parsix.core.lazy

import dev.forkhandles.result4k.Failure
import dev.forkhandles.result4k.Success
import dev.forkhandles.result4k.mapFailure
import parsix.core.IndexError
import parsix.core.Parse
import parsix.core.Parsed

fun <I, O> lazyManyOf(
    parse: Parse<I, O>
): Parse<Iterable<I>, List<O>> = parse@{ inp ->
    inp.foldIndexed(
        Success(ArrayList<O>()) as Parsed<ArrayList<O>>
    ) { i, z, item ->
        lazyLift2(
            z,
            { parse(item).mapFailure { IndexError(i, it) } }
        ) { zv, iv ->
            zv.add(iv)
            Success<ArrayList<O>>(zv)
        }.apply {
            if (this is Failure) return@parse this
        }
    }
}
