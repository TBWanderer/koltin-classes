package main.kotlin.homeworks

class Lesson6 {
    fun run() {
        val lion = Lion(5)
        lion.roar()

        val wolf = Wolf(4)
        wolf.roar()

        val cat = Cat(3, "Whiskers")
        cat.roar()

        val dog = Dog(2, "Buddy")
        dog.roar()

        val cow = Cow(6, "Bessie")
        cow.roar()
    }

    open class Animal(var age: Int) {
        open fun roar() {}
    }

    class Lion(age: Int) : Animal(age) {
        override fun roar() {
            println("Roar! Class Lion.")
        }
    }

    class Wolf(age: Int) : Animal(age) {
        override fun roar() {
            println("Aoo! Class Wolf.")
        }
    }

    open class Pet(age: Int, var name: String) : Animal(age) {
        override fun roar() {
            println("Pet sound")
        }
    }

    class Cat(age: Int, name: String) : Pet(age, name) {
        override fun roar() {
            println("Meow! Class Cat, my name is $name.")
        }
    }

    class Dog(age: Int, name: String) : Pet(age, name) {
        override fun roar() {
            println("Woof! Class Dog, my name is $name.")
        }
    }

    class Cow(age: Int, name: String) : Pet(age, name) {
        override fun roar() {
            println("Moo! Class Cow, my name is $name.")
        }
    }
}