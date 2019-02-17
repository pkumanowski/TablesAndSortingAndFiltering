package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage formStage) throws Exception {

        Stage welcomeStage = new Stage();

        FXMLLoader formLoader = new FXMLLoader(Main.class.getResource("/view/MainWindowView.fxml"));
        AnchorPane formPane = formLoader.load();
        Scene formScene = new Scene(formPane);
        formScene.getStylesheets().add(getClass().getResource("/view/MainWindowStyle.css").toExternalForm());
        MainWindowController mainController = formLoader.getController();
        mainController.setMain(this, formStage);
        formStage.setTitle("Formularz");
        formStage.setScene(formScene);
        formStage.show();

        FXMLLoader welcomeLoader = new FXMLLoader(Main.class.getResource("/view/WelcomeWindowView.fxml"));
        AnchorPane welcomePane = welcomeLoader.load();
        Scene welcomeScene = new Scene(welcomePane);
        welcomeScene.getStylesheets().add(getClass().getResource("/view/WelcomeWindowStyle.css").toExternalForm());
        WelcomeWindowController welcomeController = welcomeLoader.getController();
        welcomeController.setMain(this, welcomeStage);
        welcomeStage.setTitle("Welcome");
        welcomeStage.setScene(welcomeScene);
        welcomeStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
