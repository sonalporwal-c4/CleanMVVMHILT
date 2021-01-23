package com.cleanmvvm.starwars.data.dtoimport com.cleanmvvm.starwars.data.base.BaseDTOTestimport org.junit.Assertclass SpeciesDetailsDTOTest : BaseDTOTest<SpeciesDetailsDTO>(SpeciesDetailsDTO::class.java) {    override var serverJson: String = """     {        "name": "Human",         "homeworld": "http://swapi.dev/api/planets/9/",         "language": "Galactic Basic"     }    """.trimIndent()    override fun assertClassUnderTestValues(classUnderTest: SpeciesDetailsDTO) {        Assert.assertEquals("name should match", "Human", classUnderTest.name)        Assert.assertEquals("homeworld should match", "http://swapi.dev/api/planets/9", classUnderTest.homeworld)        Assert.assertEquals("language should match", "Galactic Basi", classUnderTest.language)    }}