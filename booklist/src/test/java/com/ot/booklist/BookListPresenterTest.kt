package com.ot.booklist

import app.cash.turbine.test
import com.ot.booklist.api.Book
import com.ot.booklist.api.BookListResponse
import com.ot.core.AppDispatchers
import com.ot.core.StringResource
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody
import okio.IOException
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.HttpException
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class BookListPresenterTest {
    private lateinit var repository: BookListRepository
    private val testDispatcher = StandardTestDispatcher()
    private val dispatchers = AppDispatchers(
        work = testDispatcher,
        result = testDispatcher,
    )

    private lateinit var presenter: BookListPresenter

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        presenter = BookListPresenter(repository, dispatchers)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `fetchBooks should return the same number of uibooks as response`() = runTest {
        coEvery { repository.fetchBooks(0) } returns Result.success(dummyBooks)

        presenter.fetchBooks()

        presenter.state.test {
            // expect loading
            assertTrue(awaitItem() is BookListState.Loading)

            val item = awaitItem()
            assertTrue(item is BookListState.Books)
            assertEquals(dummyBooks.size, (item as BookListState.Books).items.size)
            assertEquals(dummyBooks[0].title, item.items[0].title)
        }
    }

    @Test
    fun `fetchBooks should return error if repository returns failure`() = runTest {
        coEvery { repository.fetchBooks(0) } returns Result.failure(Throwable("fake reason"))

        presenter.fetchBooks()

        presenter.state.test {
            // expect loading
            assertTrue(awaitItem() is BookListState.Loading)

            val item = awaitItem()
            assertTrue(item is BookListState.Error)
        }
    }

    @Test
    fun `fetchBooks should return network error when ioexception is thrown`() = runTest {
        coEvery { repository.fetchBooks(0) } returns Result.failure(IOException("fake io exception"))

        presenter.fetchBooks()

        presenter.state.test {
            assertTrue(awaitItem() is BookListState.Loading)
            val item = awaitItem()
            assertTrue(item is BookListState.Error)
            val error = (item as BookListState.Error).message
            assertTrue(error is StringResource.Id)
            val message = (error as StringResource.Id).id
            assertEquals(R.string.error_network, message)
        }
    }

    @Test
    fun `fetchBooks should return server error when httpexception is thrown`() = runTest {
        coEvery { repository.fetchBooks(0) } returns Result.failure(
            HttpException(
                Response.error<BookListResponse>(
                    500,
                    ResponseBody.create(null, "fake error response"),
                ),
            ),
        )

        presenter.fetchBooks()

        presenter.state.test {
            assertTrue(awaitItem() is BookListState.Loading)
            val item = awaitItem()
            assertTrue(item is BookListState.Error)
            val error = (item as BookListState.Error).message
            assertTrue(error is StringResource.Id)
            val message = (error as StringResource.Id).id
            assertEquals(R.string.error_server, message)
        }
    }

    @Test
    fun `fetchBooks should return a generic error when any other exception is thrown`() = runTest {
        coEvery { repository.fetchBooks(0) } returns Result.failure(IllegalStateException("fake exception"))

        presenter.fetchBooks()

        presenter.state.test {
            assertTrue(awaitItem() is BookListState.Loading)
            val item = awaitItem()
            assertTrue(item is BookListState.Error)
            val error = (item as BookListState.Error).message
            assertTrue(error is StringResource.Id)
            val message = (error as StringResource.Id).id
            assertEquals(R.string.error_generic, message)
        }
    }

    private val dummyBooks = listOf(
        Book(
            isbn = "1234",
            title = "fake title",
            description = "fake description",
            image = "fake-image-url",
        ),
        Book(
            isbn = "1234",
            title = "fake title",
            description = "fake description",
            image = "fake-image-url",
        ),
    )
}
