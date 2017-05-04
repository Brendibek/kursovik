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

    public Button backToMenuBtn;

    private boolean fullScreen = false;

    public void startDaltonTest(){
        fullScreenSwitch();
    }

    public void startColorTest(){
        fullScreenSwitch();
    }

    public void startTraining(){
        fullScreenSwitch();
    }

    public void exit(){
        System.exit(0);
    }


    public void fullScreenSwitch(){
        if(fullScreen){
            Values.stage.setFullScreen(false);
            menu.setVisible(true);
            backToMenuBtn.setVisible(false);
        }else{
            Values.stage.setFullScreen(true);
            menu.setVisible(false);
            backToMenuBtn.setVisible(true);
        }

        fullScreen = !fullScreen;
    }

    public void backToMenu(){
        fullScreenSwitch();
    }
}
