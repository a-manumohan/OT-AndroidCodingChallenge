package com.ot.core

import android.content.Context

sealed interface StringResource {
    data class Id(val id: Int) : StringResource

    data class Text(val text: String) : StringResource
}

fun Context.getString(resource: StringResource): String = when (resource) {
    is StringResource.Id -> this.getString(resource.id)
    is StringResource.Text -> resource.text
}
