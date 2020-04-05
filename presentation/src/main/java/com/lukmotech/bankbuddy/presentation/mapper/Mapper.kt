package com.lukmotech.bankbuddy.presentation.mapper

interface Mapper<T, E> {
    fun from(e: E): T

    fun to(t: T): E
}