package com.cleanmvvm.starwars.data.base

import io.mockk.MockKAnnotations

abstract class BaseTest<ClassUnderTest> {
    abstract var classUnderTest: ClassUnderTest

    open fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

}