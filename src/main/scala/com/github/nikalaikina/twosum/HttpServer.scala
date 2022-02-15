package com.github.nikalaikina.twosum

import cats.data.Kleisli
import cats.effect.Concurrent
import cats.implicits._
import org.http4s._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.Http4sDsl
import org.http4s.implicits._
import org.http4s.server.Router

class HttpServer[F[_] : Concurrent] extends Http4sDsl[F] {

  val twoSumService: HttpRoutes[F] = HttpRoutes.of[F] {
    case req @ POST -> Root / "upload" / IntVar(sum) =>
      for {
        input <- req.as[List[List[Int]]]
        response <- Ok(TwoSumSolver.solve(sum, input))
      } yield response
  }

  val httpApp: Kleisli[F, Request[F], Response[F]] = Router("/" -> twoSumService).orNotFound

}
