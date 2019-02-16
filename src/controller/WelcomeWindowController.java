package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class WelcomeWindowController {

    @FXML
    public TextField authorTextField;
    @FXML
    public TextField dateTextFiled;
    @FXML
    public TextField classInfoText;
    @FXML
    public TextField projectInfoText;
    @FXML
    public Button nextId;
    @FXML
    public Button cancelId;


    public void setMain(Main main, Stage primaryStage) {

        cancelId.setOnAction(event -> {
            System.exit(0);
        });
        nextId.setOnAction(event -> {
            primaryStage.close();
        });
    }
}
