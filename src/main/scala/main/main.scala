package main
import exercice._

import exercice.Functions.showExpr

object main extends App {

  val val1 = showExpr(add(Numbers(1),Numbers(2)))

  println(val1)

}