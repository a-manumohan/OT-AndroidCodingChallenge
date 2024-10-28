package com.ot.booklist.ui

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class BooksAdapter : RecyclerView.Adapter<BookViewHolder>() {
    var books = emptyList<UiBook>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val bookView =
            BookView(parent.context)
        bookView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        return BookViewHolder(bookView)
    }

    override fun getItemCount(): Int {
        return books.size
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(books[position])
    }
}

class BookViewHolder(private val view: BookView) : RecyclerView.ViewHolder(view) {
    fun bind(uiBook: UiBook) {
        view.bind(uiBook)
    }
}