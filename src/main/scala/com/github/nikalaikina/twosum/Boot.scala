package com.github.nikalaikina.twosum

import cats.effect.{ExitCode, IO, IOApp}
import org.http4s.blaze.server.BlazeServerBuilder

object Boot extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = {
    val serverBuilder = BlazeServerBuilder[IO]
      .bindHttp(8080, "localhost")
      .withHttpApp(new HttpServer[IO].httpApp)

    serverBuilder.resource.use(_ => IO.never).as(ExitCode.Success)
  }

}
