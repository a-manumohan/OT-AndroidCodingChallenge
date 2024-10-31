package com.ot.booklist

import com.ot.booklist.api.Book
import com.ot.booklist.api.BookListApi
import com.ot.booklist.api.BookListResponse
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BookListRepositoryImplTest {
    private lateinit var bookListApi: BookListApi
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var repository: BookListRepository

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        bookListApi = mockk()
        repository = BookListRepositoryImpl(bookListApi)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `fetchBooks should return the result with the same number of books from api`() = runTest {
        coEvery { bookListApi.getBooks(any(), 0) } returns dummyBookResponse

        val bookResult = repository.fetchBooks(0)
        assertTrue(bookResult.isSuccess)
        assertEquals(dummyBookResponse.results.books.size, bookResult.getOrElse { emptyList() }.size)
    }

    @Test
    fun `fetchBooks should return failure if api throws any exception`() = runTest {
        val reason = "fake reason"
        coEvery { bookListApi.getBooks(any(), any()) } throws Throwable(reason)

        val bookResult = repository.fetchBooks(0)
        assertTrue(bookResult.isFailure)
        assertEquals(reason, bookResult.exceptionOrNull()?.message)
    }

    private val dummyBookResponse = BookListResponse(
        results = BookListResponse.Results(
            books = listOf(
                Book(
                    isbn = "fake isbn",
                    title = "fake title",
                    description = "fake desc",
                    image = "fake image url",
                ),
                Book(
                    isbn = "fake isbn",
                    title = "fake title",
                    description = "fake desc",
                    image = "fake image url",
                ),
            ),
        ),
    )
}
