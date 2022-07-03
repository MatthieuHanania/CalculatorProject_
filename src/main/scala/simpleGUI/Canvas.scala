package simpleGUI

import exercice.Expr
import exercice.Functions.eval

import scala.swing.Panel
import java.awt.{Color, Graphics2D}
import java.awt.geom._

class Canvas extends Panel {

  var centerColor = Color.yellow

  var darts = List[Dart]()

  var points = List[Point]()

  var test = List[Int]()

  var X_CENTER = 510

  var Y_CENTER = 340

  override def paintComponent(g: Graphics2D) {

    g.clearRect(0,0, size.width, size.height)

    // Start by erasing this Canvas
    g.setColor(new Color(255,255,255))
    g.fill(new Rectangle2D.Double(0,100,1000,500))

    //g.setColor(new Color(255,255,255))
    //g.draw(new Line2D.Double(100,100,100,100)                 // left border
    //g.drawLine(0,50,1000,50)

    //Horizontal lines
    for(i <-100 to 600 by 30) {
      g.drawLine(0,i,1000,i)
      g.setColor(new Color(0,0,0))
    }

    // Verticales lines
    for(i <-0 to 1000 by 30){
      g.drawLine(i,100,i,600)
      if(i==100){
        g.setColor(Color.darkGray)
      }
    }

    //Center
    g.setColor(new Color(255,0,0))
    g.drawLine(0,Y_CENTER,1000,Y_CENTER) //horizontal
    g.drawLine(X_CENTER,100,X_CENTER,600)


    //Test for the line
    for(point <- points){
      g.setColor(point.color)
      if(Y_CENTER-point.y.toInt >= 100 & X_CENTER+point.x.toInt <= 1000 & Y_CENTER-point.y.toInt <= 600){
        g.fillOval(X_CENTER+point.x.toInt, Y_CENTER-point.y.toInt, 5, 5)
      }
    }
    repaint()

  }

  def viderlaliste(): Unit ={
    points = List[Point]()
  }

  def drawfunction(expr : Expr, color : Color): Unit ={
    for(i <- -1000 to 1000){
      points = points :+ new Point(i.toDouble, eval(expr)(i.toDouble/30)*30, color)
    }
  }

  /** Add a "dart" to list of things to display */
  def throwDart(dart: Dart) {
    darts = darts :+ dart
    // Tell Scala that the display should be repainted
  }
}