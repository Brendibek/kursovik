package sample;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
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
    public Label colorTestCountLabel;

    public Button startColorTestBtn;

    public void startDaltonTest(){
        fullScreenSwitch();
    }

    public void showColorTestSettings(){
        menu.setVisible(false);
        colorTestMenu.setVisible(true);
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

    public void test(){
        int sliderValue = (int)Math.round(colorTestCountSlider.getValue());
        colorTestCountLabel.setText(String.valueOf(sliderValue));
    }
}
