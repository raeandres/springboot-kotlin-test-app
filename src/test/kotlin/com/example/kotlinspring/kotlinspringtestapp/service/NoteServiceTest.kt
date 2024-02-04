package com.example.kotlinspring.kotlinspringtestapp.service

import com.example.kotlinspring.kotlinspringtestapp.datasource.NoteDataSource
import com.example.kotlinspring.kotlinspringtestapp.datasource.mock.MockNotesDataSource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class NoteServiceTest {

    private val notesDataSource : NoteDataSource = mockk(relaxed = true)

    private val noteService = NoteService(notesDataSource)


    @Test
    fun `should call its data source to retrieve notes`() {
        // given


        // when
        val notes = noteService.getAllNotes()

        // then
        verify { notesDataSource.getAllNotes() }
    }
}