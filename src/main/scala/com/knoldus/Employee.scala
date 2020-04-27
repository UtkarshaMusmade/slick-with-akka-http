package com.knoldus

import slick.jdbc.H2Profile.api._
import slick.lifted.{ProvenShape, Rep, Tag}

case class Employee(employeeId: String, employeeName: String, employeDesignation: String)

class EmployeeTable(tag: Tag) extends Table[Employee](tag, "EMPLOYEE") {
  def employeeId: Rep[String] = column[String]("EMPLOYEE_ID")

  def employeeName: Rep[String] = column[String]("NAME")

  def employeeDesignation: Rep[String] = column[String]("DESIGNATION")

  override def * : ProvenShape[Employee] = (employeeId, employeeName, employeeDesignation) <> (Employee.tupled, Employee.unapply)
}

