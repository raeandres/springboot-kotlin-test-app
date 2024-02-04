package com.example.kotlinspring.kotlinspringtestapp.datasource.mock

import com.example.kotlinspring.kotlinspringtestapp.datasource.NoteDataSource
import com.example.kotlinspring.kotlinspringtestapp.model.Note
import com.example.kotlinspring.kotlinspringtestapp.service.NoteService
import org.springframework.stereotype.Repository
import java.util.*
import kotlin.IllegalArgumentException
import kotlin.NoSuchElementException

@Repository
class MockNotesDataSource : NoteDataSource {

    val notes = mutableListOf(
        Note(id = "1", name = "Grocery", description = "buy grocery for today", createdDate = Date()),
        Note(id = "2", name = "Laundry", description = "Do Laundry Tomorrow", createdDate = Date()),
        Note(id = "3", name = "Bills", description = "Pay Bills", createdDate = Date()),
        Note(id = "4", name = "Gym", description = "Go to gym", createdDate = Date())
    )

    override fun getAllNotes(): List<Note> {
        return notes
    }

    override fun getNote(name: String): Note {
       return notes.singleOrNull {
            it.name == name
        } ?: throw NoSuchElementException("Could not find note with name: $name")
    }

    override fun createNote(note: Note): Note {
        if (notes.any { it.name == note.name }) {
            throw IllegalArgumentException("Note with same name: ${note.name} already exists")
        }
        notes.add(note)
        return note
    }

    override fun updateNote(note: Note): Note {
        val currentNote = notes.singleOrNull { it.id == note.id }
            ?: throw NoSuchElementException("Could not find a note with the id: ${note.id}")

        notes.remove(currentNote)
        notes.add(note)

        return note
    }

    override fun removeNote(note: Note): String {
        val currentNote = notes.singleOrNull { it.id == note.id }
            ?: throw NoSuchElementException("Could not find a note with the id: ${note.id}")

        notes.remove(currentNote)
        if (notes.find { it.id  == note.id} != null) {
            return "Note with id: ${note.id} is still existing"
        }
        return "Note with id: ${note.id} Successfully deleted"
    }

}