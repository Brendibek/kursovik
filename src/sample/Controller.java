package sample;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import resource.Values;

public class Controller {
    public VBox menu;

    public Button daltonBtn;
    public Button colorBtn;
    public Button trainingBtn;
    public Button exitBtn;

    public Button backToMenuBtn;

    public VBox colorTestMenu;

    public Slider colorTestCountSlider;
    public Button startColorTestBtn;
    public Button colorTestBackToMenu;
    public CheckBox colorCheck;
    public ColorPicker colorPicker;

    public void startDaltonTest(){
        fullScreenSwitch();
    }

    public void showColorTestSettings(){
        menu.setVisible(false);
        colorTestMenu.setVisible(true);
    }

    public void hideColorTestSettings(){
        menu.setVisible(true);
        colorTestMenu.setVisible(false);
    }

    public void startColorTest(){
        colorTestMenu.setVisible(false);
        fullScreenSwitch();
    }

    public void startTraining(){
        fullScreenSwitch();
    }

    public void exit(){
        System.exit(0);
    }

    public void fullScreenSwitch(){
        if(Values.stage.isFullScreen()){
            Values.stage.setFullScreen(false);
            menu.setVisible(true);
            backToMenuBtn.setVisible(false);
        }else{
            Values.stage.setFullScreen(true);
            menu.setVisible(false);
            backToMenuBtn.setVisible(true);
        }
    }

    public void backToMenu(){
        fullScreenSwitch();
    }
}
