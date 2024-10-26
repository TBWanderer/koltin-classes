import kotlin.math.PI

fun main() {
    val hw = Lesson4HW()
    hw.run()
}

class Lesson4HW {
    fun run() {
        val get = Get()

        val mode = get.int("Enter mode: ")
        when (mode) {
            1 -> {
                val r = get.float("Enter radius (may to enter float point num): ")
                val s = PI * r * r
                println("S of circle with gotten radius: $s")
            }
            2 -> {
                val numbers = get.str("Enter nums in one line: ").split(" ").mapNotNull {
                    try {
                        it.toInt()
                    } catch (e: NumberFormatException) {
                        null
                    }
                }

                val cnt = numbers.count { it % 2 == 0 }

                println("The number of even numbers: $cnt")
            }
            3 -> {
                val n = get.int("Enter N to get N!: ")
                println("$n! equals ${fact(n)}")
            }
        }
    }

    fun fact(n: Int) : Int {
        if (n == 1) {
            return 1
        }

        if (n <= 0 ) {
            println("This num can't realized by this func to fact")
            return -1
        }

        return n * fact(n-1)
    }
}

class Lesson3HW {
    fun run() {
        val get = Get()

        val mode = get.int("Enter mode: ")
        when (mode) {
            1 -> {
                val N = get.int("Enter N to get Σ(i=1, n=N) 1/i: ")
                var sum = 0.0

                for (i in 1..N) {
                    sum += 1 / i
                }
                println("Σ equals: $sum")
            }
            2 -> {
                val N = get.int("Enter N to get factorial: ")
                var factorial = 1

                for (i in 1..N) {
                    factorial *= i
                }

                println("Факториал числа $N: $factorial")
            }
            3 -> {

                val M = get.int("Enter M (count of friends): ")
                val friends = mutableListOf<Map<String, Any>>()

                for (i in 1..M) {
                    val name = get.str("Enter friend's #$i name: ")
                    val age = get.int("Enter friend's #$i age: ")

                    friends.add(mapOf("name" to name, "age" to age))
                }

                val youngestFriend = friends.minByOrNull { it["age"] as Int }
                println("The youngest friend: ${youngestFriend?.get("name")}")
            }
            4 -> {
                val n = get.int("Enter count of apps")

                val batteryConsumption = get.str("Enter data about apps: ").split(" ").map { it.toInt() }

                val totalConsumption = batteryConsumption.sum()

                val fullHours = 100 / totalConsumption

                println(fullHours)
            }
        }
    }
}

class Lesson2HW {
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

class Lesson1HW {
    fun run() {
        val get = Get()

        val mode = get.int("Enter mode: ")
        when (mode) {
            1 -> {
                val name = get.int("Enter name: ")
                val age = get.int("Enter age: ")
                val email = get.str("Enter email: ")

                println("Your name: $name. You are $age years old. Email: $email")
            }
            2 -> {
                val weight = get.int("Enter weight(kg): ")
                val height = get.int("Enter height(cm): ") / 100.0
                val ibm = weight * 1.0 / (height * height)
                println("Index Body Mass: $ibm")

                if (ibm > 30) {
                    println("Bad")
                } else if (ibm > 25) {
                    println("Not good")
                } else if (ibm > 20){
                    println("Normal")
                } else {
                    println("Less than normal")
                }
            }
            3 -> {
                val kids = get.int("Enter count of kids: ")
                val apples = get.int("Enter count of apples: ")
                val integer = apples / kids
                val remainder = apples - kids * integer
                println("Each student will get $integer apples")
                println("There will be $remainder apples left")
            }
            else -> {
                println("Incorrect mode")
            }
        }
    }
}

class Get {
    fun int(prompt: String) : Int {
        print(prompt)
        val res = readln().toIntOrNull()

        if (res != null) {
            return res
        } else {
            println("Input must be an integer")
            return int(prompt)
        }
    }

    fun float(prompt: String) : Float {
        print(prompt)
        val res = readln().toFloatOrNull()

        if (res != null) {
            return res
        } else {
            println("Input must be floating point number")
            return float(prompt)
        }
    }

    fun str(prompt: String) : String {
        print(prompt)
        return readln()
    }
}