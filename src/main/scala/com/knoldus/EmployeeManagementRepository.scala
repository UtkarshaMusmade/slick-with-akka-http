package com.knoldus

import slick.jdbc.H2Profile.api._
import slick.lifted.TableQuery

import scala.concurrent.Future

trait DAOComponent {

  def insert(employee: Employee): Future[Int]

  def update(id: String, employee: Employee): Future[Int]

  def delete(id: String): Future[Int]

  def list: Future[Seq[Employee]]

  def findById(id: String): Future[Option[Employee]]

  def count: Future[Int]

}

class EmployeeManagementRepository(database: Database) extends DAOComponent {
  val employeeTable = TableQuery[EmployeeTable]

  database.run(employeeTable.schema.create)

  private def filterQuery(id: String): Query[EmployeeTable, Employee, Seq] =
    employeeTable.filter(_.employeeId === id)

  override def insert(employee: Employee): Future[Int] = {
    database.run(employeeTable += employee)
  }

  override def update(id: String, entity: Employee): Future[Int] =
    database.run(filterQuery(id).update(entity))

  override def delete(id: String): Future[Int] =
    database.run(filterQuery(id).delete)

  override def list: Future[Seq[Employee]] = {
    val query = for {
      employee <- employeeTable
    } yield employee
    database.run(query.result)
  }

  override def findById(id: String): Future[Option[Employee]] =
    database.run(
      employeeTable
        .filter(_.employeeId === id)
        .take(1)
        .result
        .headOption)

  override def count: Future[Int] =
    database.run(employeeTable.length.result)
}

