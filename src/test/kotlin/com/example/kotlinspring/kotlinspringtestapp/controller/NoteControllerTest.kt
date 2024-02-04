package com.example.kotlinspring.kotlinspringtestapp.controller


import com.example.kotlinspring.kotlinspringtestapp.model.Note
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
internal class NoteControllerTest @Autowired constructor (
    private val  mockMvc: MockMvc,
    private val objectMapper : ObjectMapper) {

    val baseUrl = "/api"

    @Nested
    @DisplayName("GET /api/notes")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetNewNote {
        @Test
        fun `should return all notes`(){
            // when

            mockMvc.get("$baseUrl/notes")
                .andDo {
                    print()
                }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].name") {
                        value("Grocery")
                    }
                }
        }


        @Test
        fun `should return a single note`(){
            val name = "Grocery"
            // when
            mockMvc.get("$baseUrl/note/$name")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.name") {
                        value("Grocery")
                    }
                }
        }

        @Test
        fun `should return NOT FOUND if the name does not exist `(){

            val name = "not exist"

            mockMvc.get("$baseUrl/note/$name")
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }

        }
    }


    @Nested
    @DisplayName("POST /api/notes")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PostNewNote {

        @Test
        fun `should add new note`(){
            val newNote = Note(
                id = "5",
                name = "Some Note",
                description = "Some Note Description",
                createdDate = Date()
            )

            // when
            val performPost = mockMvc.post("$baseUrl/notes") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newNote)
            }
            // then
            performPost.andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(newNote))
                    }
                    jsonPath( "$.id") { value(newNote.id)}
                    jsonPath( "$.name") { value(newNote.name)}
                    jsonPath( "$.description") { value(newNote.description)}
                }

            mockMvc.get("$baseUrl/note/${newNote.name}")
                .andExpect {
                    content {
                        json(objectMapper.writeValueAsString(newNote))
                    }
                }
        }

        @Test
        fun `should return BAD REQUEST if note with given name already exists`(){
            // given
            val invalidNote = Note(id = "2", name = "Laundry", description = "Do Laundry Tomorrow", createdDate = Date())

            // when
            val performPost = mockMvc.post("$baseUrl/notes") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidNote)
            }

            // then
            performPost.andDo {
                print()
            }.andExpect {
                status { isBadRequest() }
            }
        }
    }

    @Nested
    @DisplayName("PATCH /api/notes")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PatchNote {

        @Test
        fun `should update an existing note`(){
            // when

            val updateNote = Note(id = "1", name = "Coffee", description = "Buy Coffee", createdDate = Date())


            val performPatch = mockMvc.patch("$baseUrl/notes") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updateNote)
            }

            // then
            performPatch
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(updateNote))
                    }

                }

            mockMvc.get("$baseUrl/note/${updateNote.name}")
                .andExpect {
                    content {
                        json(objectMapper.writeValueAsString(updateNote))
                    }
                }
        }


        @Test
        fun `should return NOT FOUND if no note with given ID exists `(){
            val updateNote = Note(id = "5", name = "Coffee", description = "Buy Coffee", createdDate = Date())

            val performPatch = mockMvc.patch("$baseUrl/notes") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updateNote)
            }

            // then
            performPatch
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }
        }

    }

    @Nested
    @DisplayName("DELETE /api/note")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteNote{

        @Test
        fun `should delete a note by ID`(){
            // when
            val deleteNote = Note(id = "4", name = "Gym", description = "Go to gym", createdDate = Date())

            val performDelete = mockMvc.delete("$baseUrl/note") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(deleteNote)
            }

            // then
            performDelete
                .andDo { print() }
                .andExpect {
                     status { isOk() }
                }



            mockMvc.get("$baseUrl/note/${deleteNote.id}")
                .andExpect {
                    status { isNotFound() }
                }

        }
    }
}