package com.example.kotlinspring.kotlinspringtestapp.controller

import com.example.kotlinspring.kotlinspringtestapp.model.Note
import com.example.kotlinspring.kotlinspringtestapp.service.NoteService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class NoteController(private val noteService: NoteService) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> = ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(e: IllegalArgumentException): ResponseEntity<String> = ResponseEntity(e.message, HttpStatus.BAD_REQUEST)


    @GetMapping("/notes")
    fun getAllNotes(): List<Note> {
        return noteService.getAllNotes()
    }

    @GetMapping("/note/{name}")
    fun getNote(@PathVariable name: String) = noteService.getNote(name)


    @PostMapping("/notes")
    @ResponseStatus(HttpStatus.CREATED)
    fun addNote(@RequestBody note: Note) : Note  = noteService.addNote(note)


    @PatchMapping("/notes")
    fun updateNotes(@RequestBody note: Note) : Note = noteService.updateNote(note)


    @DeleteMapping("/note")
    fun removeNoteById(@RequestBody note: Note) = noteService.removeNote(note)
}