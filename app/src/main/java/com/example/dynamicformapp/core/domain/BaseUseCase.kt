package com.example.dynamicformapp.core.domain


abstract class BaseUseCase<IO, VO> {

    abstract operator fun invoke(output: IO): VO
}