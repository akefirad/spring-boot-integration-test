package com.example.fixtures

import io.kotest.property.RandomSource

object MyFixture {
    val seed = System.currentTimeMillis()
    val source = RandomSource.seeded(seed)
}