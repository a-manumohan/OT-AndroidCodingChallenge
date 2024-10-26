package com.ot.booklist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.ot.booklist.di.BookListComponentProvider
import com.ot.core.AppDispatchers
import com.ot.core.getString
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookListFragment : Fragment() {
    @Inject
    lateinit var bookListPresenter: BookListPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? = inflater.inflate(R.layout.fragment_book_list, container, false)

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        (view.context.applicationContext as BookListComponentProvider).bookListComponent()
            .inject(this)

        renderBooks()
        bookListPresenter.fetchBooks()
    }

    private fun renderBooks() {
        lifecycleScope.launch {
            bookListPresenter.state.collectLatest {
                when (it) {
                    is BookListState.Books -> {
                        Log.e("Manu", it.items.toString())
                    }

                    is BookListState.Error -> {

                        Log.e("Manu", requireContext().getString(it.message))
                    }

                    BookListState.Loading -> {

                        Log.e("Manu", "Loading")
                    }
                }
            }
        }
    }
}
