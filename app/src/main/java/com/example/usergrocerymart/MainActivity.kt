package com.example.usergrocerymart

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_splash)

        val textViewGrocery = findViewById<TextView>(R.id.textViewGrocery)
        val textViewInstantly = findViewById<TextView>(R.id.textViewInstantly)
        val lineView = findViewById<View>(R.id.lineView)

        // Delay for 0.5 seconds (500 milliseconds) to show the first TextView
        textViewGrocery.postDelayed({
            textViewGrocery.animate()
                .alpha(1f)
                .setDuration(500) // Animation duration (0.5 seconds)
                .start()
        }, 500) // Delay for 0.5 seconds

        // Delay for 1 second (1000 milliseconds) to show the second TextView
        textViewInstantly.postDelayed({
            textViewInstantly.animate()
                .alpha(1f)
                .setDuration(500) // Animation duration (0.5 seconds)
                .start()
        }, 1000) // Delay for 1 second (0.5 seconds after the first animation)

        // Delay for 1.5 seconds (1500 milliseconds) to show the line View
        lineView.postDelayed({
            lineView.animate()
                .alpha(1f)
                .setDuration(500) // Animation duration (0.5 seconds)
                .start()
        }, 1500) // Delay for 1.5 seconds (0.5 seconds after the second animation)
    }
}