package com.chichkanov.aviasally.core.domain

interface BaseTransformer<T, R> {
    fun transform(data: T): R
}