package com.ot.booklist.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.ot.booklist.R
import com.ot.booklist.databinding.ViewBookBinding

class BookView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : CardView(context, attrs) {
    private var _binding: ViewBookBinding? = null
    private val binding get() = _binding!!

    init {
        _binding = ViewBookBinding.inflate(LayoutInflater.from(context), this)
        elevation = context.resources.getDimensionPixelSize(R.dimen.elevation).toFloat()
        useCompatPadding = true
    }

    fun bind(uiBook: UiBook) {
        binding.bookTitle.text = uiBook.title
        binding.bookDescription.text = uiBook.description
        binding.bookImage.contentDescription =
            context.resources.getString(R.string.content_description_book_image, uiBook.title)
        Glide.with(this).load(uiBook.image).into(binding.bookImage)

    }
}