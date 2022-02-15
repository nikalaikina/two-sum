package com.github.nikalaikina.twosum

import org.scalacheck.Prop.forAll
import org.scalacheck.Properties

object TwoSumSolverTest extends Properties("TwoSumSolver") {

  property("list_contains_addends") = forAll { (sum: Int, list: List[Int]) =>
    TwoSumSolver.findSums(sum, list).flatten.forall(list.contains)
  }

  property("addends_make_sum") = forAll { (sum: Int, list: List[Int]) =>
    TwoSumSolver.findSums(sum, list).forall(_.sum == sum)
  }

  property("two_addends") = forAll { (sum: Int, list: List[Int]) =>
    TwoSumSolver.findSums(sum, list).forall(_.size == 2)
  }

  property("pairs_are_different") = forAll { (sum: Int, list: List[Int]) =>
    val result = TwoSumSolver.findSums(sum, list)
    result.map(_.toSet).size == result.size
  }
}
