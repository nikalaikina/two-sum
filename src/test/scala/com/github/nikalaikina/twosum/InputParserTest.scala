package com.github.nikalaikina.twosum

import com.github.nikalaikina.twosum.InputParser.ParsingError.{NotANumber, NotAnArray}
import com.github.nikalaikina.twosum.InputParser.parse
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class InputParserTest extends AnyFlatSpec with should.Matchers {

  it should "parse successfully" in {
    val expected = Right(List(List(11, -4, 3, 4, 3, 2), List(2, 5, 5, 3, 0, 1)))
    parse("[[11, -4, 3, 4, 3, 2], [2, 5, 5, 3, 0, 1]]") shouldBe expected
  }

  it should "give not an array" in {
    parse("11, -4], [2]]") shouldBe Left(NotAnArray("11, -4], [2]]"))
  }

  it should "give not an array for inner array" in {
    parse("[[11, -4], 2]") shouldBe Left(NotAnArray("2"))
  }

  it should "give not a number" in {
    parse("[[11, -4], [2, abc]]") shouldBe Left(NotANumber("abc"))
  }
}
