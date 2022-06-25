package main
import exercice._
import exercice.Functions.{eval, showExpr, simplify}
import exercice.Parser._
import exercice.SimpleParser._
import exercice.TestParser.testParser

object main extends App{

  val add1 = add(Numbers(4), add(Numbers(1),Numbers(2)) )
  val add2 = add(X, Numbers(2))
  val add3 = add(X, add(Numbers(1),Numbers(2)))
  val add4 = add(Numbers(5), add(X,Numbers(2)))
  val add5 = add( add(Numbers(5),Numbers(5)), add(X,Numbers(2)))

  val mul0 = mul(Numbers(4), mul(Numbers(1),Numbers(2)) )
  val mul1 = mul(Numbers(6),Numbers(2))
  val mul2 = mul(X, Numbers(2))
  val mul3 = mul(X, mul(Numbers(1),Numbers(2)))
  val mul4 = mul(Numbers(5), mul(X,Numbers(2)))
  val mul5 = mul( mul(Numbers(5),Numbers(5)), mul(X,Numbers(2)))
  val mul6 = mul( mul(X,Numbers(0)), mul(X,Numbers(2)))
  val mul7 = mul( mul(X,Numbers(0)), mul(Numbers(5),Numbers(2)))

  val cos1 = cos(add(Numbers(4), add(Numbers(1),Numbers(2)) ))
  val cos2 = cos(add(X, add(Numbers(1),Numbers(2)) ))
  val cos3 = cos(add(add(X,Numbers(2)), add(Numbers(1),Numbers(2)) ))

  println("final : "+ showExpr(simplify(
    cos3
  )))
  println("eval"+eval( simplify(cos3) )(x=3) )

  //println(test)

  //println(testParser)
  println(testBigParser)

}