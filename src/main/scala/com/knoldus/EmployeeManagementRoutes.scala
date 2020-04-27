package com.knoldus

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
import play.api.libs.json.{Json, OFormat}

class EmployeeManagementRoutes(employeeManagementRepository: EmployeeManagementRepository) extends PlayJsonSupport {
  implicit val employeeFormat: OFormat[Employee] = Json.format[Employee]

  val routes: Route = pathPrefix("employee") {
    path("add-employee") {
      (post & entity(as[Employee])) { employee =>
        onSuccess(employeeManagementRepository.insert(employee)) {
          result => complete(result)
        }
      }
    } ~
      path("list-all") {
        get {
          onSuccess(employeeManagementRepository.list) { result =>
            complete(result)
          }
        }
      } ~
      path(Segment) { employeeId =>
        (put & entity(as[Employee])) { employee =>
          onSuccess(employeeManagementRepository.update(employeeId, employee)) {
            result => complete(result)
          }
        } ~
          get {
            onSuccess(employeeManagementRepository.findById(employeeId)) {
              case None => complete("No record exists")
              case Some(result) => complete(result)
            }
          } ~
          delete {
            onSuccess(employeeManagementRepository.delete(employeeId)) {
              result => complete(result)
            }
          }
      }
  }
}
