package com.github.nikalaikina.twosum

import cats.Monad
import cats.data.Kleisli
import org.http4s._
import org.http4s.dsl.Http4sDsl
import org.http4s.server.Router

class HttpServer[F[_] : Monad] extends Http4sDsl[F] {

  val twoSumService: HttpRoutes[F] = HttpRoutes.of[F] {
    case GET -> Root / "two-sum" =>
      Ok("hi")
  }

  val httpApp: Kleisli[F, Request[F], Response[F]] = Router("/" -> twoSumService).orNotFound

}
