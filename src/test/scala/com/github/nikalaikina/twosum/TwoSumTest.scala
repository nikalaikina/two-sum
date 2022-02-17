package com.github.nikalaikina.twosum

import cats.effect.IO
import cats.effect.unsafe.IORuntime
import io.circe.literal._
import org.http4s._
import org.http4s.circe.CirceEntityDecoder._
import org.http4s.circe._
import org.http4s.client.Client
import org.http4s.dsl.io._
import org.http4s.implicits.http4sLiteralsSyntax
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class TwoSumTest extends AnyFlatSpec with should.Matchers {

  implicit val runtime: IORuntime = cats.effect.unsafe.IORuntime.global

  val client: Client[IO] = Client.fromHttpApp(new HttpServer[IO].httpApp)

  it should "give correct response" in {
    val request: Request[IO] = Request(POST, uri"/upload/7")
      .withEntity(json"""[[11, -4, 3, 4, 3, 2], [2, 5, 5, 3, 1]]""")

    val response = client.expect[List[List[List[Int]]]](request).unsafeRunSync()
    val expected = Set(Set(Set(-4, 11), Set(3, 4)), Set(Set(2, 5)))
    response.map(_.map(_.toSet).toSet).toSet shouldBe expected
  }

  it should "give 400 for not a number" in {
    val request: Request[IO] = Request(POST, uri"/upload/7").withEntity("[[1, abc]]")
    client.status(request).unsafeRunSync() shouldBe Status.BadRequest
  }
}
