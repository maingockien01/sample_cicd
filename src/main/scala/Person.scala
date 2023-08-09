package main

import spray.json.DefaultJsonProtocol._

case class Person (val name: String) {

    def introduce(): String = s"Hi, I am $name"

    def greeting (other: Person): String = {
        val otherName = other.name
        s"Hi $otherName, nice to meet you"
    }
}