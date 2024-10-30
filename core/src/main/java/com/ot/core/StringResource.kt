package com.ot.core

import android.content.Context

sealed interface StringResource {
    data class Id(val id: Int) : StringResource

    data class Text(val text: String) : StringResource
}

fun StringResource.getString(context: Context): String = when (this) {
    is StringResource.Id -> context.getString(this.id)
    is StringResource.Text -> this.text
}
