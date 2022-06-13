package exercice
import scala.util.parsing.combinator._

object Functions {

  def showExpr(e: Expr): String ={
    e match {
      case add(val1,val2) => "("+showExpr(val1)+" + "+showExpr(val2)+")"
      case mul(val1,val2) => "("+showExpr(val1)+" * "+showExpr(val2)+")"
      case cos(val1) =>  "cos("+showExpr(val1)+")"
      case sin(val1) =>  "sin("+showExpr(val1)+")"

      case Numbers(e) => e.toString
      case X => "x"
    }
  }

  def eval(e: Expr)(x: Double =0): Double={
    x
  }

  def simplify(e: Expr): Expr={



  }


}
