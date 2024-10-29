package com.ot.core.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.ot.core.databinding.ViewLoadingBinding

class LoadingView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {
    private var binding: ViewLoadingBinding? = null

    init {
        binding = ViewLoadingBinding.inflate(LayoutInflater.from(context), this)
    }
}
