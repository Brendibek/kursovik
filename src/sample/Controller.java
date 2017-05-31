package sample;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import resource.Values;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Controller {
    private JSONParser jsonParser = new JSONParser();

    public VBox menu;

    public Button daltonBtn;
    public Button colorBtn;
    public Button trainingBtn;
    public Button exitBtn;

    public Button backToMenuBtn;

    public VBox daltonTestMenu;
    public Slider daltonTestCountSlider;
    public Button startDaltonTestBtn;
    public Button daltonTestBackToMenu;

    private ArrayList<String[]> daltonTestQuestions = new ArrayList<>();
    public VBox daltonTestQuestionForm;
    public ImageView daltonImage;
    public Label daltonQuestion;
    public VBox daltonAnswers;
    public TextField daltonAnswer;
    public RadioButton daltonTestV1Radio, daltonTestV2Radio, daltonTestV3Radio, daltonTestV4Radio;
    private int daltonCurrentCorrectAnswer = -1, daltonTestCorrectAnswers = 0, daltonTestQuestionNum = 0;
    public VBox daltonTestResult;
    public Label daltonTestResultLabel;

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

    public VBox userPane;
    public Label userEmail;
    public Button signInBtn;

    public VBox signForm;
    public TextField emailField;
    public PasswordField passwordField;
    public PasswordField passwordField2;
    public Button signSubmitBtn;

    public void signIn(){
        if(!userEmail.isVisible()) {
            showSignForm();
            passwordField2.setVisible(false);
            signSubmitBtn.setText("Увійти");
        }else{
            userEmail.setVisible(false);
            signInBtn.setText("Увійти");
        }
    }

    public void signUp(){
        showSignForm();
        passwordField2.setVisible(true);
        signSubmitBtn.setText("Зареєструватися");
    }

    public void signSubmit(){
        String email = emailField.getText();
        String pass = passwordField.getText();

        if(passwordField2.isVisible()){
            String pass2 = passwordField2.getText();

            if(email.contains("@") && email.contains(".") && !email.contains(",")){
                if(pass.length()>=6){
                    if(pass.equals(pass2)){
                        //md5

                        DBConnector.addUser(email, pass, pass2);
                        emailField.clear();
                        passwordField.clear();
                        passwordField2.clear();
                    }else{
                        //разные пароли
                    }
                }else{
                    //пароль слишком короткий
                }
            }else{
                //не правильный мейл
            }
        }else{
            try {
                String[] user = DBConnector.getUserByEmail(email).get(0);

                if(user[4].equals("1")) {
                    System.out.println(user[3]);
                    if(user[3].equals(pass)){
                        userEmail.setText(email);
                        userEmail.setVisible(true);
                        signInBtn.setText("Вийти");
                        System.out.println("success");
                    }else{
                        System.out.println("wrong password");
                    }
                }else{
                    System.out.println("not confirmed");
                }
            }catch (NullPointerException ex){
                System.out.println("no user");
            }

        }
    }

    private void showSignForm(){
        signForm.setVisible(true);
        menu.setVisible(false);
        userPane.setVisible(false);
        backToMenuBtn.setVisible(true);
    }

    public void startDaltonTest(){
        fullScreenSwitch();
        daltonTestMenu.setVisible(false);
        daltonTestQuestions = DBConnector.getQuestions();

        daltonQuestionInit(daltonTestQuestions.get(daltonTestQuestionNum));

        daltonTestQuestionForm.setVisible(true);
    }


    public void daltonTestSubmitQuestion(){
        try {
            if (daltonAnswer.isVisible()) {
                if (Integer.parseInt(daltonAnswer.getText()) == daltonCurrentCorrectAnswer)
                    daltonTestCorrectAnswers++;
            } else {
                if ((daltonCurrentCorrectAnswer == 0 && daltonTestV1Radio.isSelected()) || (daltonCurrentCorrectAnswer == 1 && daltonTestV2Radio.isSelected()) || (daltonCurrentCorrectAnswer == 2 && daltonTestV3Radio.isSelected()) || (daltonCurrentCorrectAnswer == 3 && daltonTestV4Radio.isSelected()))
                    daltonTestCorrectAnswers++;
            }
            daltonTestQuestionNum++;

            daltonQuestionInit(daltonTestQuestions.get(daltonTestQuestionNum));
        }catch (IndexOutOfBoundsException ex){
            daltonTestResult.setVisible(true);
            daltonTestResultLabel.setText(Math.round(100/colorTestCountSlider.getValue() * colorTestCorrectAnswers) + "%");
            daltonTestEnd();
        }catch (NumberFormatException ex){
            //empty field
        }
    }

    public void showDaltonTestSettings(){
        menu.setVisible(false);
        daltonTestMenu.setVisible(true);
    }


    public void hideDaltonTestSettings(){
        menu.setVisible(true);
        daltonTestMenu.setVisible(false);
    }

    private void daltonTestEnd(){
        daltonTestQuestionForm.setVisible(false);
        daltonTestCorrectAnswers = 0;
        daltonTestQuestionNum = 0;
    }

    private void daltonQuestionInit(String[] question){
        try {
            daltonImage.setImage(new Image(question[2]));

            String json = question[3];
            JSONObject object = (JSONObject) jsonParser.parse(json);

            if(object.size() == 1){
                daltonCurrentCorrectAnswer = Integer.parseInt(object.get("answer").toString());

                daltonAnswers.setVisible(false);
                daltonAnswer.setVisible(true);
            }else{
                daltonCurrentCorrectAnswer = Integer.parseInt(object.get("answerNum").toString());

                daltonTestV1Radio.setText(object.get("answer1").toString());
                daltonTestV2Radio.setText(object.get("answer2").toString());
                daltonTestV3Radio.setText(object.get("answer3").toString());
                daltonTestV4Radio.setText(object.get("answer4").toString());

                daltonAnswer.setVisible(false);
                daltonAnswers.setVisible(true);
            }

        }catch (ParseException ex){
            ex.printStackTrace();
        }
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
            colorTestEnd();
        }
        colorTestV1Radio.setSelected(false);
        colorTestV2Radio.setSelected(false);
        colorTestV3Radio.setSelected(false);
        colorTestV4Radio.setSelected(false);
    }

    private void colorTestEnd(){
        colorTestQuestionForm.setVisible(false);
        colorTestCorrectAnswers = 0;
        colorTestQuestionNum = 0;
        colorTestQuestions.clear();
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
            backToMenu();
        }else{
            Values.stage.setFullScreen(true);
            menu.setVisible(false);
            userPane.setVisible(false);
            backToMenuBtn.setVisible(true);
        }
    }

    public void backToMenu(){
        Values.stage.setFullScreen(false);
        menu.setVisible(true);
        userPane.setVisible(true);
        backToMenuBtn.setVisible(false);
        userPane.setVisible(true);
        signForm.setVisible(false);

        colorTestResult.setVisible(false);
        colorTestEnd();

        daltonTestResult.setVisible(false);
        daltonTestEnd();
    }
}
