package sample;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import resource.Values;

import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
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
    public int trainingImageClickCount, trainingImagePcCount;
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

    private long timerr = System.currentTimeMillis();
    public void startTraining(){
        fullScreenSwitch();
        trainingImageClickCount = 0;
        trainingImagePcCount = -1;
        trainingClickCountLabel.setText("Кількість натиснень: 0  Кількість пропусків: 0");
        trainingClickCountLabel.setVisible(true);
        trainingImage.setVisible(true);
        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                moveImageTraining("pc");
                if(!Values.stage.isFullScreen())
                {
                    trainingClickCountLabel.setVisible(false);
                    trainingImageClickCount = 0;
                    trainingImage.setVisible(false);
                    timer.cancel();
                }
            }
        };
        timer.schedule(task, 0, 1000);
    }

    public void trainingImageClick(){
        moveImageTraining("click");
    }

    public void moveImageTraining(String who){
        if(((who.equals("pc"))&&(System.currentTimeMillis() - timerr >= 1000))||(who.equals("click"))){
            Random random = new Random();
            int posX = random.nextInt((int)(Values.stage.getWidth() - trainingImage.getFitWidth()));
            int posY = random.nextInt((int)(Values.stage.getHeight() - trainingImage.getFitHeight()));
            trainingImage.setLayoutX(posX);
            trainingImage.setLayoutY(posY);
            timerr = System.currentTimeMillis();

            if(who.equals("pc")) trainingImagePcCount++;
            else trainingImageClickCount++;

            Platform.runLater(() -> trainingClickCountLabel.setText("Кількість натиснень: " + trainingImageClickCount + " Кількість пропусків: " + trainingImagePcCount));
        }

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
