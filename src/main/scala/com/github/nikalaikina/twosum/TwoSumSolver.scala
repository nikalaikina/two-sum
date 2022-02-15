package com.github.nikalaikina.twosum

object TwoSumSolver {
  type Row = List[Int]
  type Result = List[Int]

  def solve(sum: Int, data: List[Row]): List[List[Result]] = {
    data.map(findSums(sum, _).map(_.toList).toList)
  }

  // If performance is critical can be done in one go
  // but i like it this way because it is readable and complexity is the same
  def findSums(sum: Int, data: List[Int]): Set[Set[Int]] = {
    val contains = data.toSet
    contains.collect { case x if contains(sum - x) => Set(x, sum - x) }
  }
}
