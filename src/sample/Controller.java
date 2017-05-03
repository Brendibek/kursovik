package sample;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import resource.Values;

public class Controller {
    public VBox menu;
    public Button daltonBtn;
    public Button colorBtn;
    public Button trainingBtn;
    public Button exitBtn;

    public void startDaltonTest(){

    }

    public void startColorTest(){

    }

    public void startTraining(){
        Values.stage.setFullScreen(true);
        menu.setVisible(false);
    }

    public void exit(){
        System.exit(0);
    }
}
