package app;

import controller.ScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    public static final String TITLE = "CodeNames 5x5";

    @Override
    public void start(Stage stage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/mainView.fxml"));
        Scene scene = new Scene(parent, 1850, 1000);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMaximized(true);
        ScreenController controller = new ScreenController();
        controller.setStage(stage);
        controller.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}