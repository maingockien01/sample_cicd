package main

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.server.Directives._
import spray.json.DefaultJsonProtocol._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Route._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util._
import scala.io.StdIn


object Server extends App {
    implicit val system = ActorSystem("Server")
    val peopleService = new PeopleService()
    implicit val personFormat = jsonFormat1(Person)
    val route = path("join") {
        post {
            entity(as[Person]) { person =>
                onComplete(peopleService.welcome(person)) {
                    case Success(res) => complete(res)
                    case Failure(ex) => complete(StatusCodes.InternalServerError)
                }
            }
        }
    } ~ path("all") {
        get {
            onComplete(peopleService.getAllPeople()) {
                case Success(res) => complete(res)
                case Failure(ex) => complete(StatusCodes.InternalServerError)
            }
        }
    }

    val server = Http().newServerAt("localhost", 9090).bind(route)
    server.map { _ =>
        println("Successfully started on localhost:9090 ")
    } recover { case ex =>
        println("Failed to start the server due to: " + ex.getMessage)
    }
    StdIn.readLine() 
    server
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done


}