package main.kotlin.homeworks

import main.kotlin.utils.Get

class Lesson3 {
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