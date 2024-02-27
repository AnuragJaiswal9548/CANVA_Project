package com.example.projectca


import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import android.util.DisplayMetrics
import android.view.MotionEvent

class Fan(context: Context?) : View(context) {
    private lateinit var p: Paint
    private lateinit var q: Paint
    private var rect1Left = 100f
    private var rect1Top = 100f
    private var rect1Right = 500f
    private var rect1Bottom = 500f

    private var rect2Left = 600f
    private var rect2Top = 100f
    private var rect2Right = 1000f
    private var rect2Bottom = 500f

    private var isRect1MovingForward = true
    private var isRect2MovingForward = true
    private var isMoving = false
    private var screenWidth = 0f
    private var screenHeight = 0f
    private val initialRect1Left = 100f
    private val initialRect1Top = 100f
    private val initialRect1Right = 500f
    private val initialRect1Bottom = 500f

    private val initialRect2Left = 600f
    private val initialRect2Top = 100f
    private val initialRect2Right = 1000f
    private val initialRect2Bottom = 500f

    init {
        init()
    }

    private fun init() {
        p = Paint()
        p.color = Color.GREEN

        q = Paint()
        q.color = Color.BLUE

        // Get screen dimensions
        val displayMetrics = context.resources.displayMetrics
        screenWidth = displayMetrics.widthPixels.toFloat()
        screenHeight = displayMetrics.heightPixels.toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawColor(Color.GRAY)

        // Draw rectangles
        canvas.drawRect(rect1Left, rect1Top, rect1Right, rect1Bottom, p)
        canvas.drawRect(rect2Left, rect2Top, rect2Right, rect2Bottom, q)

        // If movement is enabled, continue moving
        if (isMoving) {
            moveRectangles()
            postInvalidateOnAnimation()
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // Start moving when touched
                isMoving = true
                startMoving()
            }
            MotionEvent.ACTION_UP -> {
                // Stop moving and return to original position when released
                isMoving = false
                returnToOriginalPosition()
            }
        }
        return true
    }

    private fun moveRectangles() {
        val diagonalLength = Math.sqrt((screenWidth * screenWidth + screenHeight * screenHeight).toDouble()).toFloat()

        // Movement logic for rectangle 1
        if (isRect1MovingForward) {
            rect1Left += 5 * screenWidth / diagonalLength
            rect1Top += 5 * screenHeight / diagonalLength
            rect1Right += 5 * screenWidth / diagonalLength
            rect1Bottom += 5 * screenHeight / diagonalLength

            if (rect1Right >= screenWidth && rect1Bottom >= screenHeight) {
                isRect1MovingForward = false
            }
        } else {
            rect1Left -= 5 * screenWidth / diagonalLength
            rect1Top -= 5 * screenHeight / diagonalLength
            rect1Right -= 5 * screenWidth / diagonalLength
            rect1Bottom -= 5 * screenHeight / diagonalLength

            if (rect1Left <= initialRect1Left && rect1Top <= initialRect1Top) {
                isRect1MovingForward = true
            }
        }

        // Movement logic for rectangle 2
        if (isRect2MovingForward) {
            rect2Left -= 5 * screenWidth / diagonalLength
            rect2Top += 5 * screenHeight / diagonalLength
            rect2Right -= 5 * screenWidth / diagonalLength
            rect2Bottom += 5 * screenHeight / diagonalLength

            if (rect2Left <= 0 && rect2Bottom >= screenHeight) {
                isRect2MovingForward = false
            }
        } else {
            rect2Left += 5 * screenWidth / diagonalLength
            rect2Top -= 5 * screenHeight / diagonalLength
            rect2Right += 5 * screenWidth / diagonalLength
            rect2Bottom -= 5 * screenHeight / diagonalLength

            if (rect2Right >= initialRect2Right && rect2Top <= initialRect2Top) {
                isRect2MovingForward = true
            }
        }
    }

    private fun startMoving() {
        // Schedule the movement at regular intervals
        postInvalidateOnAnimation()
    }

    private fun returnToOriginalPosition() {
        // Reset rectangle positions to initial coordinates
        rect1Left = initialRect1Left
        rect1Top = initialRect1Top
        rect1Right = initialRect1Right
        rect1Bottom = initialRect1Bottom

        rect2Left = initialRect2Left
        rect2Top = initialRect2Top
        rect2Right = initialRect2Right
        rect2Bottom = initialRect2Bottom

        invalidate()
    }
}




