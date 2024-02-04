package com.example.kotlinspring.kotlinspringtestapp.datasource.mock

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class MockNotesDataSourceTest {


    private val mockNotesDataSource = MockNotesDataSource()


    @Test
    fun `should provide a collection of saved notes`(){
        //when
        val notes = mockNotesDataSource.getAllNotes()

        //then
        assertThat(notes).isNotEmpty
        assertThat(notes).allSatisfy{it.id.isNotBlank()}

    }
}