package com.news.newsapp.customui

import android.content.Context
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.news.newsapp.databinding.ErrorRefreshLayoutBinding

class ErrorRefreshView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val TOTAL_SECONDS = 20  //seconds for count down
    private val COUNT_DOWN_INTERVAL = 1000L //interval for count down DO NOT CHANGE
    private val TOTAL_TIME = TOTAL_SECONDS * COUNT_DOWN_INTERVAL // total time that counted down

    private val binding = ErrorRefreshLayoutBinding.inflate(LayoutInflater.from(context), this)

    private var timer: CountDownTimer? = null

    /*
     * CountDownTimer takes two arguments TOTAL_TIME is seconds long and COUNT_DOWN_INTERVAL one is interval
     * in milliseconds long. When error occurred if user do nothing it will refresh news
     */
    fun startCountDownTimer(progressListener: ProgressListener){
        cancelCountDownTimer()
        timer = null
        timer = object : CountDownTimer(
            this.TOTAL_TIME,
            this.COUNT_DOWN_INTERVAL
        ) {
            override fun onFinish() {
                binding.circleProgress.progress = 0f
                progressListener.progressFinished()
            }

            override fun onTick(millisUntilFinished: Long) {
                val resultantFloat: Float = (this@ErrorRefreshView.TOTAL_TIME - millisUntilFinished).toFloat() / this@ErrorRefreshView.TOTAL_TIME
                binding.circleProgress.progress = resultantFloat
            }
        }.start()
    }

    fun cancelCountDownTimer(){
        timer?.cancel()
    }

    interface ProgressListener {
        fun progressFinished()
    }
}