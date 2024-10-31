package com.ot.booklist

import com.ot.booklist.api.Book
import com.ot.booklist.ui.UiBook
import com.ot.core.AppDispatchers
import com.ot.core.StringResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class BookListPresenter
@Inject
constructor(
    private val bookLinkRepository: BookListRepository,
    private val appDispatchers: AppDispatchers,
) : CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext = job + appDispatchers.work

    private val _state = MutableStateFlow<BookListState>(BookListState.Loading)

    val state: StateFlow<BookListState> = _state

    fun fetchBooks() {
        launch {
            withContext(appDispatchers.result) {
                _state.emit(BookListState.Loading)
            }
            val bookResult = bookLinkRepository.fetchBooks(0)
            if (bookResult.isSuccess) {
                val uiBooks = bookResult.getOrElse { emptyList() }.map { toUiBook(it) }
                withContext(appDispatchers.result) {
                    _state.emit(BookListState.Books(uiBooks))
                }
            } else {
                val error = bookResult.exceptionOrNull()
                withContext(appDispatchers.result) {
                    _state.emit(mapError(error))
                }
            }
        }
    }

    fun cleanup() {
        job.cancel()
    }

    private fun toUiBook(book: Book): UiBook = UiBook(
        title = book.title,
        description = book.description,
        image = book.image,
    )

    private fun mapError(t: Throwable?): BookListState.Error = when (t) {
        is IOException -> BookListState.Error(StringResource.Id(R.string.error_network))
        is HttpException -> BookListState.Error(StringResource.Id(R.string.error_server))
        else -> BookListState.Error(StringResource.Id(R.string.error_generic))
    }
}
