package sample;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import resource.Values;

import java.awt.*;

public class Controller {
    public VBox menu;
    public Button daltonBtn;
    public Button colorBtn;
    public Button trainingBtn;
    public Button exitBtn;
    public Pane winPanel;

    public void startDaltonTest(){

    }

    public void startColorTest(){

    }

    public void startTraining(){
        //Values.stage.setWidth(1280);
        //Values.stage.setHeight(720);
        Values.stage.setFullScreen(true);
        //menu.setVisible(false);
    }

    public void trey() {
        Values.stage.setIconified(true);
    }
    public void exit(){
        System.exit(0);
    }

    //перетаскивание окна начало
    int Xstart, Ystart, Xmove, Ymove;
    boolean mouseDown = false;

    public void downWin(){
        Point location = MouseInfo.getPointerInfo().getLocation();
        Xstart = (int)location.getX();
        Ystart = (int)location.getY();
        mouseDown = true;
    }

    public void moveWin(){
        Point location = MouseInfo.getPointerInfo().getLocation();
        if(mouseDown == true) {
            Xmove = (int)location.getX();
            Ymove = (int)location.getY();
            Values.stage.setX(Values.stage.getX() + (Xmove - Xstart));
            Values.stage.setY(Values.stage.getY() + (Ymove - Ystart));
            Xstart = (int)location.getX();
            Ystart = (int)location.getY();
        }
    }
    //перетаскивание окна конец

    public void upWin(){
        mouseDown = false;
    }
}
