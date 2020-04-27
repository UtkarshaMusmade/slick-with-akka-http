package com.knoldus

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import slick.jdbc.H2Profile.api._

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.io.StdIn

object Main extends App{
  implicit val actorSystem: ActorSystem = ActorSystem("system")
  implicit val materializer: ActorMaterializer =
    ActorMaterializer()(actorSystem)
  implicit val executionContext: ExecutionContextExecutor = actorSystem.dispatcher
  val database =  Database.forConfig("database")
  val employeeManagementRepository = new EmployeeManagementRepository(database)
  val employeeManagementRoutes = new EmployeeManagementRoutes(employeeManagementRepository)
  val routes: Route = employeeManagementRoutes.routes

  val bindingFuture: Future[Http.ServerBinding] = Http().bindAndHandle(routes, "localhost", 8081)
  println(s"Server online at http://localhost:8081/\nPress RETURN to stop...")
  StdIn.readLine()
  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => actorSystem.terminate())
}
