package exercice

import scala.util.parsing.combinator._
//cos(2*x)
//(4*x)+(2*x*2*2*)
object Parser extends JavaTokenParsers {

  def addi:Parser[Expr] = multi ~ rep("+"~ multi)^^{
    case t1~lst => lst.foldLeft(t1){
      case(a,_ ~b) => add(a,b)
    }
  }
  def multi:Parser[Expr] = Unité ~ rep("*"~ Unité)^^{
    case f1~lst => lst.foldLeft(f1){
      case(a,_ ~b) => mul(a,b)
    }
  }

  def Unité:Parser[Expr] = Number |Variable|Cos|Sin | "(" ~> addi <~ ")" ^^ (x=>x)
  def Number : Parser[Expr] = floatingPointNumber ^^ {x => Numbers(x.toDouble)}
  def Variable:Parser[Expr] = ("X"|"x") ^^ {_ => X}
  def Cos:Parser[Expr] = "cos("~>addi<~")" ^^ {x:Expr=>cos(x)}
  def Sin:Parser[Expr] = "sin("~>addi<~")" ^^ {x:Expr=>sin(x)}

  def testBigParser(): Unit ={
    try {
      println(parseAll(addi,"2*x+1").get)
    } catch {
      case e : Exception => println("Error with this function")
    }
  }
}