package sample;

import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import resource.Values;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

    public ImageView trainingImage;
    public Label trainingClickCountLabel;
    public int trainingImageClickCount = 0;
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

        trainingClickCountLabel.setText("Кількість натиснень: 0");
        trainingClickCountLabel.setVisible(true);
        trainingImage.setVisible(true);

        Random random = new Random();
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                int posX = random.nextInt((int)(Values.stage.getWidth() - trainingImage.getFitWidth()));
                int posY = random.nextInt((int)(Values.stage.getHeight() - trainingImage.getFitHeight()));
                trainingImage.setLayoutX(posX);
                trainingImage.setLayoutY(posY);
                if(!Values.stage.isFullScreen())
                {
                    trainingClickCountLabel.setVisible(false);
                    trainingImageClickCount = 0;
                    trainingImage.setVisible(false);
                    service.shutdown();
                }
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    public void trainingImageClick(){
        trainingImageClickCount++;
        trainingClickCountLabel.setText("Кількість натиснень: " + trainingImageClickCount);
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
