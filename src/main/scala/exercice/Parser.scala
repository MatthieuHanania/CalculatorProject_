package exercice
import exercice.TestParser.floatingPointNumber

import scala.util.parsing.combinator._

object Parser extends JavaTokenParsers {

  def Form:Parser[Expr] = Term ~ rep("+"~ Term)^^{
    case t1~lst => lst.foldLeft(t1){
      case(a,_ ~b) => add(a,b)
    }
  }
  def Term:Parser[Expr] = Factor ~ rep("*"~ Factor)^^{
    case f1~lst => lst.foldLeft(f1){
      case(a,_ ~b) => mul(a,b)
    }
  }

  def Factor:Parser[Expr] = Number |Variable|Cos|Sin | "(" ~> Form <~ ")" ^^ (x=>x)
  def Number : Parser[Expr] = floatingPointNumber ^^ {x => Numbers(x.toDouble)}
  def Variable:Parser[Expr] = "X" ^^ {_ => X}
  def Cos:Parser[Expr] = "cos("~>Form<~")" ^^ {x:Expr=>cos(x)}
  def Sin:Parser[Expr] = "sin("~>Form<~")" ^^ {x:Expr=>sin(x)}

  def testBigParser(): Unit ={
    try {
      println(parseAll(Form,"cos(X+cos(10*2))").get)
    } catch {
      case e : Exception => println("Error with this function")
    }
  }
}

