package com.ot.booklist

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.window.layout.WindowMetricsCalculator
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

    private var _binding: FragmentBookListBinding? = null
    private val binding get() = _binding!!

    private val adapter = BooksAdapter()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBookListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        (view.context.applicationContext as BookListComponentProvider)
            .bookListComponent()
            .inject(this)

        binding.bookList.adapter = adapter
        val widthWindowSizeClass = (activity as DynamicWindow).getWidthSizeClass()
        binding.bookList.layoutManager = when (widthWindowSizeClass) {
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
                        adapter.books = it.items
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
