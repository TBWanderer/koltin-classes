package main.kotlin.homeworks

class Lesson7 {
    fun run() {
        val circle = Circle(5.0)
        println("Circle area: ${circle.area()}")
        println("Circle perimeter: ${circle.perimeter()}")
        circle.draw()

        val rectangle = Rectangle(4.0, 6.0)
        println("Rectangle area: ${rectangle.area()}")
        println("Rectangle perimeter: ${rectangle.perimeter()}")
        rectangle.draw()
    }

    abstract class Shape {
        abstract fun area(): Double
        abstract fun perimeter(): Double
    }

    interface Drawable {
        fun draw()
    }

    class Circle(private val radius: Double) : Shape(), Drawable {
        override fun area(): Double = Math.PI * radius * radius
        override fun perimeter(): Double = 2 * Math.PI * radius
        override fun draw() {
            println("Drawing a circle with radius $radius")
        }
    }

    class Rectangle(private val width: Double, private val height: Double) : Shape(), Drawable {
        override fun area(): Double = width * height
        override fun perimeter(): Double = 2 * (width + height)
        override fun draw() {
            println("Drawing a rectangle with width $width and height $height")
        }
    }
}