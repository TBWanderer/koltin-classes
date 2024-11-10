package main.kotlin.homeworks

import main.kotlin.utils.Get

class Lesson2 {
    fun run() {
        val get = Get()

        val mode = get.int("Enter mode: ")
        when (mode) {
            1 -> {
                val a = get.int("Enter section 1: ")
                val b = get.int("Enter section 2: ")
                val c = get.int("Enter section 3: ")

                if (a < b + c && b < a + c && c < a + b) {
                    println("Triangle with gotten sections exists")
                } else {
                    println("Triangle with gotten sections doesn't exists")
                }
            }
            2 -> {
                val hours = get.int("Enter count of hours: ")

                when (hours) {
                    in 5..11 -> {
                        println("Morning")
                    }
                    in 12..17 -> {
                        println("Day")
                    }
                    in 18..22 -> {
                        println("Evening")
                    }
                    in 23..24, in 0..4 -> {
                        println("Night")
                    }
                    else -> {
                        println("Error")
                    }
                }
            }
            3 -> {
                val year = get.int("Enter year: ")

                if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                    println("The gotten year is Leap Year")
                } else {
                    println("The gotten year isn't Leap Year")
                }
            }
            4 -> {
                val score = get.int("Enter score: ")

                var scoreInRF = ""
                var scoreInECTS = ""

                when (score) {
                    in 0..30 -> {
                        scoreInRF = "2"
                        scoreInECTS = "F"
                    }

                    in 31..50 -> {
                        scoreInRF = "2"
                        scoreInECTS = "FX"
                    }

                    in 51..60 -> {
                        scoreInRF = "3"
                        scoreInECTS = "E"
                    }

                    in 61..68 -> {
                        scoreInRF = "3"
                        scoreInECTS = "D"
                    }

                    in 69..85 -> {
                        scoreInRF = "4"
                        scoreInECTS = "C"
                    }

                    in 86..94 -> {
                        scoreInRF = "5"
                        scoreInECTS = "B"
                    }

                    in 95..100 -> {
                        scoreInRF = "5"
                        scoreInECTS = "A"
                    }

                    else -> {
                        println("Incorrect score")

                    }
                }

                println("Score in RF: $scoreInRF")
                println("Score in ECTS: $scoreInECTS")
            }
            else -> {
                println("Incorrect mode")
            }
        }
    }
}