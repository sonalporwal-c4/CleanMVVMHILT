package com.cleanmvvm.starwars.data.base

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.junit.Test

abstract class BaseDTOTest<ClassUnderTest>(val clazz: Class<ClassUnderTest>) {

    abstract var serverJson: String
    val moshi = Moshi.Builder().build()
    val adapter: JsonAdapter<ClassUnderTest> = moshi.adapter(clazz)

    @Test
    fun testParsing(){
        val classUnderTest: ClassUnderTest = adapter.fromJson(serverJson)!!
        assertClassUnderTestValues(classUnderTest)
    }

    abstract fun assertClassUnderTestValues(classUnderTest: ClassUnderTest)

}
