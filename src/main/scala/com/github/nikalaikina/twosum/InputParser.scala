package com.github.nikalaikina.twosum

import cats.instances.either._
import cats.syntax.either._
import cats.syntax.option._
import cats.syntax.traverse._
import com.github.nikalaikina.twosum.InputParser.ParsingError.{NotANumber, NotAnArray}
import com.github.nikalaikina.twosum.TwoSumSolver.Row

import scala.annotation.tailrec

object InputParser extends App {

  def parse(string: String): Either[ParsingError, List[Row]] = {
    for {
      rows <- parseArray(string)
      lists <- rows.traverse(parseArray)
      numbers <- lists.traverse(_.traverse(parseNumber))
    } yield numbers
  }

  private def parseArray(string: String): Either[ParsingError, List[String]] = {
    string match {
      case s"[$str]" => splitArray(str).asRight
      case str => NotAnArray(str).asLeft
    }
  }

  @tailrec
  private def splitArray(string: String, acc: List[String] = Nil): List[String] = {
    string.trim.headOption match {
      case Some('[') =>
        val (word, tail) = string.indexOf(']').some.filter(_ != -1).fold((string, ""))(x => string.splitAt(x + 1))
        splitArray(tail.dropWhile(c => c == ',' || c == ' '), word :: acc)
      case Some(_) =>
        val (word, tail) = string.indexOf(',').some.filter(_ != -1).fold((string, ""))(string.splitAt)
        splitArray(tail.tail, word :: acc)
      case None =>
        acc.reverse
    }
  }

  private def parseNumber(string: String): Either[ParsingError, Int] = {
    string.trim.toIntOption.toRight(NotANumber(string))
  }

  sealed trait ParsingError

  object ParsingError {
    case class NotAnArray(str: String) extends ParsingError
    case class NotANumber(str: String) extends ParsingError
  }
}
