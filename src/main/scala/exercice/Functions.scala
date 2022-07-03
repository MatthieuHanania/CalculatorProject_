package exercice
import scala.util.parsing.combinator._

object Functions {

  def showExpr(e: Expr): String ={
    e match {

      case Numbers(e) => e.toString
      case X => "X"

      case add(val1,val2) => "("+showExpr(val1)+"+"+showExpr(val2)+")"
      case mul(val1,val2) => "("+showExpr(val1)+"*"+showExpr(val2)+")"
      case cos(val1) =>  "cos("+showExpr(val1)+")"
      case sin(val1) =>  "sin("+showExpr(val1)+")"
    }
  }

  def eval(e: Expr)(x: Double): Double={

    e match {
      case Numbers(value) =>value
      case X => x
      case add(Numbers(a),Numbers(b))=> a+b
      case add(val1,val2)=> Numbers(eval(val1)(x)).value + Numbers(eval(val2)(x)).value

      case mul(Numbers(a),Numbers(b))=> a*b
      case mul(val1,val2)=> Numbers(eval(val1)(x)).value * Numbers(eval(val2)(x)).value

      case cos(Numbers(a)) => math.cos(a)
      case cos(val2)=> math.cos(Numbers(eval(val2)(x)).value)

      case sin(Numbers(a))=> math.sin(a)
      case sin(val2)=> math.sin(Numbers(eval(val2)(x)).value)
    }
  }

  def derivate(e:Expr):Expr={
    e match {
      case Numbers(value) =>Numbers(0)
      case X => Numbers(1)
      case mul(Numbers(a),Numbers(b)) => derivate(Numbers(a*b))
      case mul(X,X) => mul(Numbers(2),X)
      case mul(X,expr) =>expr

      case add(a,b) => add(derivate(a),derivate(b))
      case mul(a,b) => add(mul(derivate(a),b), mul(derivate(b),a))

      case cos(expr) => mul(derivate(expr),mul(Numbers(-1),sin(expr)))
      case sin(expr) => mul(derivate(expr),cos(expr))

    }
  }

  def simplify(e: Expr): Expr={
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
      case mul (Numbers(0),_)=>Numbers(0)
      case mul (_,Numbers(0)) =>Numbers(0)
      case mul(X, val2 : Numbers) if val2.value==1 => X
      //case mul(X, val2 : Numbers) if val2.value==0 => Numbers(0)
      case mul(X, val2 : Numbers) =>mul(X,val2)
      case mul(X,val2 :Expr) =>mul(X,simplify(val2))
      case mul(val1,X) =>mul(X,simplify(val1))
      case mul(val1,val2) if showExpr(val1).contains('X') || showExpr(val2).contains('X') =>mul(simplify(val1),simplify(val2))
      case mul(val1,val2) =>simplify(mul(simplify(val1),simplify(val2)))


      case sin(X) => sin(X)
      case sin(Numbers(a)) =>Numbers(math.sin(a))
      case sin(val1) => sin(simplify(val1))

      case cos(X) => cos(X)
      case cos(Numbers(a)) => Numbers(math.cos(a))
      case cos(val1) => cos(simplify(val1))
    }
  }


}
