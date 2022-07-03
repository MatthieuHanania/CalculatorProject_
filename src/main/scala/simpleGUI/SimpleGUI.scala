package simpleGUI

import exercice.Functions.{derivate, showExpr, simplify}
import exercice.Parser.{addi, parseAll}

import scala.swing._
import scala.swing.BorderPanel.Position._
import event._
import scala.util.Random
import java.awt.{Color}

object SimpleGUI extends SimpleSwingApplication {

  override def top: Frame = new MainFrame{
    title = "Interface for the project"

    menuBar = new MenuBar { //barre de menu avec la fenêtre exit
      contents += new Menu("File") {
        contents += new MenuItem(Action("Exit") {
          sys.exit(0)
        })
      }
    }

    val label = new Label {
      text = "Write your function just right here : f(x) ="
      font = new Font("Calibri", java.awt.Font.ITALIC, 14)
    }

    val label_top = new Label {
      text = "Type your expression !"
      font = new Font("Calibri", java.awt.Font.BOLD, 30)
    }


    val button_draw = new Button {
      text = "Draw the function"
      foreground = Color.black
      background = Color.white
      enabled = true
      tooltip = "Click to draw the function"
    }

    val button_simplify = new Button {
      text = "Simplify the function"
      foreground = Color.black
      background = Color.white
      enabled = true
      tooltip = "Click to simplify the function"
    }

    val button_derivate = new Button {
      text = "Derivate the function"
      foreground = Color.black
      background = Color.white
      enabled = true
      tooltip = "Click to derivate the function"
    }

    val button_delete = new Button {
      text = "Erase the functions"
      foreground = Color.black
      background = Color.white
      enabled = true
      tooltip = "Click to erase the functions"
    }

    val textArea = new TextArea {
      text = ""
      background = Color.white
    }

    val gridPanel = new GridPanel(6,1) {
      contents += label
      contents += textArea
      contents += button_draw
      contents += button_simplify
      contents += button_derivate
      contents += button_delete
    }

    val canvas = new Canvas() { //là où va apparaître la fonction
      preferredSize = new Dimension(1000, 550)
      resizable = false //Can change the window size
    }


    // react to events
    reactions += {
      case ButtonClicked(component) if component == button_draw =>
        try{
          label_top.text = "The function is : "+showExpr(parseAll(addi, textArea.text).get)
          canvas.drawfunction(parseAll(addi, textArea.text).get,new Color(Random.nextInt(Int.MaxValue)))
        } catch {
          case e : Exception => label_top.text = "There is an error in your expression"
            repaint()
        }

      case ButtonClicked(component) if component == button_simplify =>
        try{
          label_top.text = "The simplified is : "+showExpr(simplify(parseAll(addi, textArea.text).get))
          textArea.text = showExpr(simplify(parseAll(addi, textArea.text).get))
          canvas.drawfunction(simplify(parseAll(addi, textArea.text).get),new Color(Random.nextInt(Int.MaxValue)))
        } catch {
          case e : Exception => label_top.text = "There is an error in your expression"
            repaint()
        }

      case ButtonClicked(component) if component == button_derivate =>
        try{
          label_top.text = "The derivated is : "+showExpr(simplify(simplify(derivate(parseAll(addi, textArea.text).get))))
          canvas.drawfunction(simplify(simplify(derivate(parseAll(addi, textArea.text).get))),new Color(Random.nextInt(Int.MaxValue)))
        } catch {
          case e : Exception => label_top.text = "There is an error in your expression"
            repaint()
        }

      case ButtonClicked(component) if component == button_delete =>
        try{
          label_top.text = "Functions got erased"
          canvas.viderlaliste()
          textArea.text = ""
          repaint()
        } catch {
          case e : Exception => label_top.text = "Error"
            repaint()
        }

    }

    size = new Dimension(1000,550) //taille de la fenêtre
    centerOnScreen

    listenTo(button_draw)
    listenTo(button_simplify)
    listenTo(button_derivate)
    listenTo(button_delete)

    // choose a top-level Panel and put components in it
    // Components may include other Panels
    contents = new BorderPanel {
      layout(gridPanel) = South
      layout(canvas) = Center
      layout(label_top) = North
    }

  }
}