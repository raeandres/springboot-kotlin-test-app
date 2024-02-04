package com.example.kotlinspring.kotlinspringtestapp.datasource

import com.example.kotlinspring.kotlinspringtestapp.model.Note

interface NoteDataSource {

    fun getAllNotes(): List<Note>

    fun getNote(name: String): Note

    fun createNote(note: Note) : Note

    fun updateNote(note: Note) : Note

    fun removeNote(id: Note) : String

}