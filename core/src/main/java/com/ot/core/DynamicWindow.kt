package com.ot.core

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass

interface DynamicWindow {
    fun getWidthSizeClass(): WindowWidthSizeClass
}