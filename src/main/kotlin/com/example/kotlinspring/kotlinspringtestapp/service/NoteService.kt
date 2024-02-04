package com.example.kotlinspring.kotlinspringtestapp.service

import com.example.kotlinspring.kotlinspringtestapp.datasource.NoteDataSource
import com.example.kotlinspring.kotlinspringtestapp.model.Note
import org.springframework.stereotype.Service
import java.util.*

@Service
class NoteService(private val noteDataSource: NoteDataSource) {

    fun getAllNotes(): List<Note> {
        return noteDataSource.getAllNotes()
    }

    fun getNote(name: String) : Note {
        return noteDataSource.getNote(name)
    }

    fun addNote(note: Note): Note {
        return noteDataSource.createNote(note)
    }

    fun updateNote(note: Note) : Note {
        return noteDataSource.updateNote(note)
    }

    fun removeNote(id: Note) : String {
        return noteDataSource.removeNote(id)
    }
}