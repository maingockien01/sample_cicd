import org.scalatest.funsuite.AnyFunSuite

import main.Person

class PersonTest extends AnyFunSuite {
    test("Kien should introduce his name") {
        val kien = new Person("Kien")
        assert(kien.introduce() === "Hi, I am Kien")
    }
}