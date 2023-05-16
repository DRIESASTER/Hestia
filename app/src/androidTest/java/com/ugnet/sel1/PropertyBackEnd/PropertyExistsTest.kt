package com.ugnet.sel1.PropertyBackEnd

import com.ugnet.sel1.domain.repository.PropertiesRepository
import com.ugnet.sel1.domain.useCases.PropertyExists
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


class PropertyExistsTest {
    /*@Mock
    private lateinit var repo: PropertiesRepository

    @InjectMocks
    private lateinit var propertyExists: PropertyExists

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `invoke should return true when property exists`() = runBlocking {
        // Arrange
        val id = "exampleId"
        `when`(repo.propertyExists(id)).thenReturn(true)

        // Act
        val result = propertyExists(id)

        // Assert
        assertTrue(result)
    }

    @Test
    fun `invoke should return false when property does not exist`() = runBlocking {
        // Arrange
        val id = "exampleId"
        `when`(repo.propertyExists(id)).thenReturn(false)

        // Act
        val result = propertyExists(id)

        // Assert
        assertTrue(!result)
    }*/
}