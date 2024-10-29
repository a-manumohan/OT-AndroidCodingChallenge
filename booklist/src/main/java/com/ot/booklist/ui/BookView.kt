package com.ot.booklist.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.ot.booklist.R
import com.ot.booklist.databinding.ViewBookBinding

class BookView
    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
    ) : CardView(context, attrs) {
        private var binding: ViewBookBinding? = null

        init {
            binding = ViewBookBinding.inflate(LayoutInflater.from(context), this)
            elevation = context.resources.getDimensionPixelSize(R.dimen.elevation).toFloat()
            useCompatPadding = true
        }

        fun bind(uiBook: UiBook) {
            val binding = this.binding
            requireNotNull(binding)
            binding.bookTitle.text = uiBook.title
            binding.bookDescription.text = uiBook.description
            binding.bookImage.contentDescription =
                context.resources.getString(R.string.content_description_book_image, uiBook.title)
            val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
            Glide
                .with(this)
                .load(uiBook.image)
                .apply(requestOptions)
                .into(binding.bookImage)
        }
    }
