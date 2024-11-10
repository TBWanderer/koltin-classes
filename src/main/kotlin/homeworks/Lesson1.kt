package main.kotlin.homeworks

import main.kotlin.utils.Get

class Lesson1 {
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