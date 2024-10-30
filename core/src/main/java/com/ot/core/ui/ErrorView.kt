package com.ot.core.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.ot.core.databinding.ViewErrorBinding

class ErrorView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : ConstraintLayout(context, attrs) {
    private var binding: ViewErrorBinding? = null

    init {
        binding = ViewErrorBinding.inflate(LayoutInflater.from(context), this)
    }

    fun bind(message: String) {
        val binding = this.binding
        requireNotNull(binding)
        binding.errorMessage.text = message
    }
}
