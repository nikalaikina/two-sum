package com.github.nikalaikina.twosum

import cats.instances.either._
import cats.syntax.either._
import cats.syntax.traverse._
import com.github.nikalaikina.twosum.InputParser.ParsingError.{IncorrectShape, NotANumber}
import com.github.nikalaikina.twosum.TwoSumSolver.Row

object InputParser extends App {

  def parse(string: String): Either[ParsingError, List[Row]] = {
    for {
      rows <- parseArray(string)
      lists = rows.map(_.split(",").toList)
      numbers <- lists.traverse(_.traverse(parseNumber))
    } yield numbers
  }

  private def parseArray(string: String): Either[ParsingError, List[String]] = {
    string.trim match {
      case s"[[$str]]" => str.split("\\]\\s*,\\s*\\[").toList.asRight
      case _ => IncorrectShape.asLeft
    }
  }

  private def parseNumber(string: String): Either[ParsingError, Int] = {
    string.trim.toIntOption.toRight(NotANumber(string.trim))
  }

  sealed trait ParsingError

  object ParsingError {
    case object IncorrectShape extends ParsingError
    final case class NotANumber(str: String) extends ParsingError
  }
}
