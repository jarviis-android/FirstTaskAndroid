package com.example.mytest

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.mytest.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName
    private lateinit var binding: ActivityMainBinding
    private val mContext: Context by lazy { this@MainActivity }
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val circles = arrayOf(
            binding.circle1, binding.circle2, binding.circle3, binding.circle4, binding.circle5,
            binding.circle6, binding.circle7, binding.circle8, binding.circle9, binding.circle10,
            binding.circle11, binding.circle12, binding.circle13, binding.circle14, binding.circle15,
            binding.circle16, binding.circle17, binding.circle18, binding.circle19, binding.circle20,
            binding.circle21, binding.circle22, binding.circle23, binding.circle24, binding.circle25,
            binding.circle26, binding.circle27)

        // Animate the single circles
//        animateCircle(circles)
        // Animate the circles in batches of 3
        animateDrawableSequentially(circles)
    }

    /**
     * Animate single ImageView
     */
    private fun animateCircle(circles: Array<ImageView>) {
        var indexOuter = 0
        var animationInterval = 1000L

        fun setBackgroundAndClear() {
            if (indexOuter > 0) {
                Glide.with(mContext)
                    .load(getDrawable(R.drawable.circular_border))
                    .into(circles[indexOuter - 1])
            }

            if(indexOuter == circles.size) indexOuter = 0

            Glide.with(mContext)
                .load(getDrawable(R.drawable.circular_border_red))
                .into(circles[indexOuter])

            indexOuter ++
            handler.postDelayed({setBackgroundAndClear()}, animationInterval)
        }
        setBackgroundAndClear()
    }

    /**
     * Animate three ImageView
     */
    private fun animateDrawableSequentially(imageViews: Array<ImageView>) {
        val colors = arrayOf(
            getDrawable(R.drawable.circular_border),
            getDrawable(R.drawable.circular_border_blue),
            getDrawable(R.drawable.circular_border_green),
            getDrawable(R.drawable.circular_border_red)
        )
        var currentIndex = 0
        val animationInterval = 1000L // 1 second delay

        // Recursive function to update colors
        fun updateColors() {

            // Apply colors to the current 4 ImageViews involved in the transition
            for (i in 0 until 4) {
                val targetIndex = (currentIndex + i) % imageViews.size
                Glide.with(mContext)
                    .load(colors[i])
                    .into(imageViews[targetIndex])
            }

            // Increment the index for the next cycle
            currentIndex++

            // Schedule the next update
            handler.postDelayed({ updateColors() }, animationInterval)
        }

        // Start the animation loop
        updateColors()
    }
}