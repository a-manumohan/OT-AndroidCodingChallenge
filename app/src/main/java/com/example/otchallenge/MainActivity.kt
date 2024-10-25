package com.example.otchallenge

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentFactory
import com.ot.booklist.BookListFragment

class MainActivity(
    private val fragmentFactory: FragmentFactory,
) : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = fragmentFactory
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        showBookListFragment()
    }

    private fun showBookListFragment() {
        val fragment =
            supportFragmentManager.fragmentFactory.instantiate(
                classLoader,
                BookListFragment::class.java.name,
            )
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment, "BOOK_LIST")
            .commit()
    }
}
