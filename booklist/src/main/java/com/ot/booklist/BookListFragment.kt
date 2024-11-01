package com.ot.booklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ot.booklist.databinding.FragmentBookListBinding
import com.ot.booklist.di.BookListComponentProvider
import com.ot.booklist.ui.BooksAdapter
import com.ot.core.DynamicWindow
import com.ot.core.getString
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookListFragment : Fragment() {
    @Inject
    lateinit var bookListPresenter: BookListPresenter

    private var binding: FragmentBookListBinding? = null

    private val adapter = BooksAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentBookListBinding.inflate(inflater, container, false)
        return binding?.root!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (view.context.applicationContext as BookListComponentProvider)
            .bookListComponent()
            .inject(this)

        val binding = this.binding
        requireNotNull(binding)
        binding.bookList.adapter = adapter
        val widthWindowSizeClass = (activity as DynamicWindow).getWidthSizeClass()
        binding.bookList.layoutManager =
            when (widthWindowSizeClass) {
                WindowWidthSizeClass.Medium -> GridLayoutManager(requireContext(), 2)
                WindowWidthSizeClass.Expanded -> GridLayoutManager(requireContext(), 3)
                else -> LinearLayoutManager(requireContext())
            }
        renderBooks()
        bookListPresenter.fetchBooks()
    }

    override fun onDetach() {
        super.onDetach()
        bookListPresenter.cleanup()
    }

    private fun renderBooks() {
        lifecycleScope.launch {
            bookListPresenter.state.collectLatest {
                when (it) {
                    is BookListState.Books -> {
                        showLoading(show = false)
                        showError(show = false)
                        adapter.books = it.items
                    }

                    is BookListState.Error -> {
                        showLoading(false)
                        showError(show = true, message = it.message.getString(requireContext()))
                    }

                    BookListState.Loading -> {
                        showLoading(true)
                        showError(false)
                    }
                }
            }
        }
    }

    private fun showLoading(show: Boolean) {
        val binding = this.binding
        requireNotNull(binding)
        binding.loading.visibility = show.visibility()
    }

    private fun showError(show: Boolean, message: String = "") {
        val binding = this.binding
        requireNotNull(binding)
        binding.errorView.visibility = show.visibility()
        binding.errorView.bind(message)
    }

    private fun Boolean.visibility() = when (this) {
        true -> View.VISIBLE
        false -> View.GONE
    }
}
