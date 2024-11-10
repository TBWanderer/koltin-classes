package main.kotlin.homeworks

import main.kotlin.utils.Get
import kotlin.math.PI

class Lesson4 {
    fun run() {
        val get = Get()

        val mode = get.int("Enter mode: ")
        when (mode) {
            1 -> {
                val r = get.float("Enter radius (may to enter float point num): ")
                println("S of circle with gotten radius: ${circleArea(r)}")
            }
            2 -> {
                val numbers = get.str("Enter nums in one line: ").split(" ").mapNotNull {
                    try {
                        it.toInt()
                    } catch (e: NumberFormatException) {
                        null
                    }
                }

                println("The number of even numbers: ${evenCount(numbers)}")
            }
            3 -> {
                val n = get.int("Enter N to get N!: ")
                println("$n! equals ${fact(n)}")
            }
        }
    }

    private fun circleArea(r: Float) : Double {
        return PI * r * r
    }

    private fun evenCount(numbers: List<Int>) : Int {
        return numbers.count { it % 2 == 0 }
    }

    private fun fact(n: Int) : Int {
        if (n == 1) {5
            return 1
        }

        if (n <= 0 ) {
            println("This num can't realized by this func to fact")
            return -1
        }

        return n * fact(n-1)
    }
}