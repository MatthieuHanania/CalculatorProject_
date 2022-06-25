package exercice

sealed trait Expr

case class Numbers(value:Double) extends Expr

case class add(val1:Expr, val2:Expr) extends Expr
case class mul(val1:Expr, val2:Expr) extends Expr
case class sin(val1 : Expr) extends Expr //The result is in the radiant form
case class cos(val2 : Expr) extends Expr ////The result is in the radiant form
case object X extends Expr

//cos(3*5+cos(10*X))

//cos(add(mul(3,5),cos(mul(10,X))