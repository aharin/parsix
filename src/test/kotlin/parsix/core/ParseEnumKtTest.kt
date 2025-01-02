package parsix.core

import dev.forkhandles.result4k.Failure
import dev.forkhandles.result4k.Success
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ParseEnumKtTest {
    enum class TestEnum(override val key: String) : ParsableEnum {
        Test1("one"),
        Test2("two")
    }

    @Test
    fun `it returns Test1 on one`() {
        assertEquals(
            Success(TestEnum.Test1),
            parseEnum<TestEnum>()("one")
        )
    }

    @Test
    fun `it returns Test2 on two`() {
        assertEquals(
            Success(TestEnum.Test2),
            parseEnum<TestEnum>()("two")
        )
    }

    @Test
    fun `it fails on unknown value`() {
        assertEquals(
            Failure(EnumError("unknown", setOf("one", "two"))),
            parseEnum<TestEnum>()("unknown")
        )
    }
}