package app;

import controller.ScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    public static final String TITLE = "CodeNames field 5x5 (by Zaiets A.Y.) v.1.1.2b";

    @Override
    public void start(Stage stage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/mainView.fxml"));
        Scene scene = new Scene(parent);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMaximized(true);
        stage.setMinWidth(1000);
        stage.setMinHeight(720);
        stage.setFullScreen(false);
        ScreenController controller = new ScreenController();
        controller.setStage(stage);
        controller.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}