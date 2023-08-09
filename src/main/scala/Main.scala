package main

object Hello {
    def main(args: Array[String]): Unit = {
        println("Hello, world")
        val kien = new Person("Kien")
        val makx = new Person("Makx")
        println(kien.introduce())
        println(kien.greeting(makx))

    }
}