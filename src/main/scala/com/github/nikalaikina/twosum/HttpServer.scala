package com.github.nikalaikina.twosum

import cats.data.Kleisli
import cats.effect.Concurrent
import cats.implicits._
import com.github.nikalaikina.twosum.InputParser.ParsingError
import com.github.nikalaikina.twosum.InputParser.ParsingError.NotANumber
import io.circe.generic.auto._
import org.http4s._
import org.http4s.circe.CirceEntityEncoder._
import org.http4s.dsl.Http4sDsl
import org.http4s.implicits._
import org.http4s.server.Router

class HttpServer[F[_] : Concurrent] extends Http4sDsl[F] {

  private val WrongInputCode = "wrong.input.type"

  val twoSumService: HttpRoutes[F] = HttpRoutes.of[F] {
    case req@POST -> Root / "upload" / IntVar(sum) =>
      for {
        body <- req.as[String]
        response <- InputParser.parse(body) match {
          case Right(input) => Ok(TwoSumSolver.solve(sum, input))
          case Left(error) => BadRequest(errorResponse(error))
        }
      } yield response
  }

  private def errorResponse(error: ParsingError): HttpError = {
    val message = error match {
      case ParsingError.IncorrectShape =>
        "Request body should be an array of arrays of numbers, like: [[1, 2], [3]]"
      case NotANumber(str) =>
        s"Request body should be an array of arrays of numbers, `$str` is not a valid number"
    }

    HttpError(WrongInputCode, message)
  }

  val httpApp: Kleisli[F, Request[F], Response[F]] = Router("/" -> twoSumService).orNotFound

}
