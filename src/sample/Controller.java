package sample;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import resource.Values;

import java.util.*;

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

    public VBox colorTestQuestionForm;
    public Rectangle colorTestV1, colorTestV2, colorTestV3, colorTestV4;
    public RadioButton colorTestV1Radio, colorTestV2Radio, colorTestV3Radio, colorTestV4Radio;
    private int colorTestCorrectAnswers = 0, colorTestQuestionNum = 0;
    private ArrayList<int[]> colorTestQuestions = new ArrayList<>();
    public VBox colorTestResult;
    public Label colorTestResultLabel;

    public ImageView trainingImage;
    public Label trainingClickCountLabel;
    private int trainingImageClickCount, trainingImagePcCount;


    private Random random = new Random();

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
        if(colorTestCountSlider.getValue()!=0){
            for(int i = 0; i < colorTestCountSlider.getValue(); i++){
                int[] temp = {random.nextInt(235), random.nextInt(235), random.nextInt(235), random.nextInt(4)};
                colorTestQuestions.add(temp);
            }

            int[] temp = colorTestQuestions.get(colorTestQuestionNum);
            colorTestDraw(temp[0], temp[1], temp[2], temp[3]);

            colorTestMenu.setVisible(false);
            fullScreenSwitch();
            colorTestQuestionForm.setVisible(true);
        }
    }

    public void colorTestSubmitQuestion(){
        try{
            int[] temp = colorTestQuestions.get(colorTestQuestionNum);

            if((temp[3] == 0 && colorTestV1Radio.isSelected()) || (temp[3] == 1 && colorTestV2Radio.isSelected()) || (temp[3] == 2 && colorTestV3Radio.isSelected()) || (temp[3] == 3 && colorTestV4Radio.isSelected()))
                colorTestCorrectAnswers++;

            colorTestQuestionNum++;

            temp = colorTestQuestions.get(colorTestQuestionNum);
            colorTestDraw(temp[0], temp[1], temp[2], temp[3]);
        }catch (IndexOutOfBoundsException ex){
            colorTestResult.setVisible(true);
            colorTestResultLabel.setText(Math.round(100/colorTestCountSlider.getValue() * colorTestCorrectAnswers) + "%");
            colorTestQuestionForm.setVisible(false);
            colorTestCorrectAnswers = 0;
            colorTestQuestionNum = 0;
            colorTestQuestions.clear();
        }
        colorTestV1Radio.setSelected(false);
        colorTestV2Radio.setSelected(false);
        colorTestV3Radio.setSelected(false);
        colorTestV4Radio.setSelected(false);
    }

    private void colorTestDraw(int R, int G, int B, int answer){
        int k = 15;
        if(answer == 0)
            colorTestV1.setFill(Color.rgb(R + k, G + k, B + k));
        else
            colorTestV1.setFill(Color.rgb(R, G, B));

        if(answer == 1)
            colorTestV2.setFill(Color.rgb(R + k, G + k, B + k));
        else
            colorTestV2.setFill(Color.rgb(R, G, B));

        if(answer == 2)
            colorTestV3.setFill(Color.rgb(R + k, G + k, B + k));
        else
            colorTestV3.setFill(Color.rgb(R, G, B));

        if(answer == 3)
            colorTestV4.setFill(Color.rgb(R + k, G + k, B + k));
        else
            colorTestV4.setFill(Color.rgb(R, G, B));
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

            colorTestResult.setVisible(false);
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
