package main

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class PeopleService {
    var people: List[Person] = List()
    
    def welcome(newPerson: Person): Future[Person] = {
        people = people :+ newPerson
        Future.successful(newPerson)
    }

    def getAllPeople(): Future[List[Person]] = Future.successful(people.toList)
}