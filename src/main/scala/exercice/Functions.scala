package exercice
import scala.util.parsing.combinator._

object Functions {

  def showExpr(e: Expr): String ={
    e match {

      case Numbers(e) => e.toString
      case X => "X"

      case add(val1,val2) => "("+showExpr(val1)+" + "+showExpr(val2)+")"
      case mul(val1,val2) => "("+showExpr(val1)+" * "+showExpr(val2)+")"
      case cos(val1) =>  "cos("+showExpr(val1)+")"
      case sin(val1) =>  "sin("+showExpr(val1)+")"
    }
  }

  def eval(e: Expr)(x: Double): Double={

    e match {
      case Numbers(value) =>value
      case X => x
      case add(Numbers(a),Numbers(b))=> a+b
      case add(val1,val2)=> eval(add(Numbers(eval(val1)(x)),Numbers(eval(val2)(x))))(x)

      case mul(Numbers(a),Numbers(b))=> a*b
      case mul(val1,val2)=> eval(mul(Numbers(eval(val1)(x)),Numbers(eval(val2)(x))))(x)

      case cos(Numbers(a)) => math.cos(a)
      case cos(val2)=> eval(cos(Numbers(eval(val2)(x))))(x)

      case sin(Numbers(a))=> math.sin(a)
      case sin(val2)=> eval(sin(Numbers(eval(val2)(x))))(x)
    }
  }


  def simplify(e: Expr): Expr={

    println("(((((((((((((",showExpr(e)," ((((((((((((((((")
    e match {

      case Numbers(a) => Numbers(a)
      case X => X

      case add(Numbers(val1),Numbers(val2)) =>Numbers(val1+val2)
      case add(X, val2 : Numbers) if val2.value==0 => X
      case add(X, val2 : Numbers) =>add(X,val2)
      case add(X,val2 :Expr) =>add(X,simplify(val2))
      case add(val1,X) =>add(X,simplify(val1))
      case add(val1,val2) if showExpr(val1).contains('X') || showExpr(val2).contains('X') =>add(simplify(val1),simplify(val2))
      case add(val1,val2) =>simplify(add(simplify(val1),simplify(val2)))

      case mul(Numbers(val1),Numbers(val2)) =>Numbers(val1*val2)
      case mul (Numbers(0),val2)=>
        print("cc")
        Numbers(0)
      case mul (val1,Numbers(0)) =>
        print("ccd")
        Numbers(0)
      case mul(X, val2 : Numbers) if val2.value==1 => X
      case mul(X, val2 : Numbers) if val2.value==0 => Numbers(0)
      case mul(X, val2 : Numbers) =>mul(X,val2)
      case mul(X,val2 :Expr) =>mul(X,simplify(val2))
      case mul(val1,X) =>mul(X,simplify(val1))
      case mul(val1,val2) if showExpr(val1).contains('X') || showExpr(val2).contains('X') =>mul(simplify(val1),simplify(val2))
      case mul(val1,val2) =>simplify(mul(simplify(val1),simplify(val2)))

      case sin(X) => sin(X)
      case sin(Numbers(a)) =>Numbers(math.cos(a))
      case sin(val1) => sin(simplify(val1))

      case cos(X) => cos(X)
      case cos(Numbers(a)) => Numbers(math.sin(a))
      case cos(val1) => cos(simplify(val1))

    }
  }


}
