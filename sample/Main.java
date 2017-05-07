package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import resource.Values;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Values.stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Меню");
        primaryStage.setScene(new Scene(root, 900, 600));
//        primaryStage.setResizable(false); //disable resizable Window
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH); //fullScreenMode disable esc
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
