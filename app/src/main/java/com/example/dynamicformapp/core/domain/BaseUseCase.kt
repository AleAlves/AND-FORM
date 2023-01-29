package com.example.dynamicformapp.core.domain


abstract class BaseUseCase<T, S> {

    abstract operator fun invoke(input: S): T
}

