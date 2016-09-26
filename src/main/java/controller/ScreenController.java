package controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import service.WordGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class ScreenController {
    private int redNumCount;
    private int blueNumCount;

    private WordGenerator wg = new WordGenerator();

    private List<String> words;

    private Stage stage;

    private Background basicCardBackground;

    {
        BackgroundSize backgroundSize = new BackgroundSize(340, 190, false, false, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(new Image(getClass().getResource("/pics/card0.png").toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        basicCardBackground = new Background(backgroundImage);
    }

    //Common elements
    @FXML
    private GridPane gridPane;
    @FXML
    private Text redNum, blueNum;
    @FXML
    private Pane pane0, pane1, pane2, pane3, pane4, pane5, pane6, pane7, pane8, pane9, pane10, pane11, pane12, pane13;
    @FXML
    private Pane pane14, pane15, pane16, pane17, pane18, pane19, pane20, pane21, pane22, pane23, pane24;

    private void setState(Pane current) {
        String choosenColor = chooseTeamView();
        Random rand = new Random();
        String url;
        if (choosenColor == null) return;
        switch (choosenColor) {
            case "red":
                url = "/pics/Red".concat(String.valueOf(rand.nextInt(2)+1)).concat(".png");
                redNumCount += 1;
                setRedNum(redNumCount);
                break;
            case "blue":
                url = "/pics/Blue".concat(String.valueOf(rand.nextInt(2)+1)).concat(".png");
                blueNumCount += 1;
                setBlueNum(blueNumCount);
                break;
            default:
                url = "/pics/Yellow".concat(String.valueOf(rand.nextInt(2)+1)).concat(".png");
                break;
        }
        current.setDisable(true);
        BackgroundSize backgroundSize = new BackgroundSize(310, 190, false, false, false, false);
        BackgroundImage backgroundImage = new BackgroundImage( new Image( getClass().getResource(url).toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        current.setBackground(background);
        current.getChildren().forEach(o -> o.setVisible(false));
    }

    private String chooseTeamView() {
        String choosenColor = null;
        ChoiceDialog<String> dialog = new ChoiceDialog<>("yellow", "blue", "red");
        GridPane grid = new GridPane();
        grid.setBackground(new Background(new BackgroundFill(Color.ANTIQUEWHITE, null, null)));
        List<Button> buttons = generateButtons(dialog);
        for (int i = 0; i < buttons.size(); i++) {
            grid.add(buttons.get(i), i, 0);
        }
        dialog.getDialogPane().setContent(grid);
        dialog.setTitle("Выберите команду агента:");
        Optional<String> optional = dialog.showAndWait();
        if (optional.isPresent()) {
            choosenColor = optional.get();
        }
        return choosenColor;
    }

    private List<Button> generateButtons(ChoiceDialog<String> dialog) {
        List<Button> buttonList = new ArrayList<>();
        List<Color> colorList = new ArrayList<>();
        Color color;
        Button current;
        List<String> dialogData = dialog.getItems();
        dialogData.add(dialog.getSelectedItem());
        for (String colorName : dialogData) {
            color = Color.valueOf(colorName).desaturate();
            colorList.add(color);
            current = new Button();
            current.setBackground(new Background(new BackgroundFill(color, new CornerRadii(5), null)));
            current.setMinSize(100, 100);
            current.setMaxSize(100, 100);
            buttonList.add(current);
        }
        for (int i = 0; i < buttonList.size(); i++) {
            current = buttonList.get(i);
            final int j = i;
            final Color color1 = colorList.get(i);
            current.setOnAction(o -> {
                        final Button b = (Button) o.getSource();
                        b.setBackground(new Background(new BackgroundFill(color1.saturate().brighter(), new CornerRadii(5), null)));
                        buttonList.stream().filter(button -> !button.equals(b)).forEach(button -> button.setDisable(true));
                        dialog.setSelectedItem(dialogData.get(j));
                    }
            );
        }
        return buttonList;
    }

    @FXML
    private void setRedNum(Integer num) {
        if (num == null) redNum.setText("0");
        else redNum.setText(num.toString());
    }

    @FXML
    private void setBlueNum(Integer num) {
        if (num == null) blueNum.setText("0");
        else blueNum.setText(num.toString());
    }

    @FXML
    public void startNewGame() {
        this.words = wg.listOfWords();
        redNumCount = 0;
        blueNumCount = 0;
        setRedNum(null);
        setBlueNum(null);
        setPanesNewWords(gridPane.getScene());
    }

    @FXML
    private void setPanesNewWords(Scene scene) {
        for (int i = 0; i < 25; i++) {
            Text text1 = (Text) (scene.lookup("#word".concat(String.valueOf(i)).concat("_1")));
            text1.getParent().setDisable(false);
            Pane pane = (Pane) (text1.getParent());
            pane.setBackground(basicCardBackground);
            String word = words.get(i);
            text1.setText(word);
            Text text2 = (Text) (scene.lookup("#word".concat(String.valueOf(i)).concat("_2")));
            text2.setText(word);
            if (word.length() > 10) {
                text1.setFont(new Font(text1.getFont().getName(), text1.getFont().getSize()*0.75));
                text2.setFont(new Font(text2.getFont().getName(), text2.getFont().getSize()*0.75));
            }
            pane.getChildren().forEach(o -> o.setVisible(true));
        }

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        this.words = wg.listOfWords();
        setPanesNewWords(stage.getScene());
        stage.show();
    }


    @FXML
    protected void setPaneNewState0() {
        setState(pane0);
    }

    @FXML
    protected void setPaneNewState1() {
        setState(pane1);
    }

    @FXML
    protected void setPaneNewState2() {
        setState(pane2);
    }

    @FXML
    protected void setPaneNewState3() {
        setState(pane3);
    }

    @FXML
    protected void setPaneNewState4() {
        setState(pane4);
    }

    @FXML
    protected void setPaneNewState5() {
        setState(pane5);
    }

    @FXML
    protected void setPaneNewState6() {
        setState(pane6);
    }

    @FXML
    protected void setPaneNewState7() {
        setState(pane7);
    }

    @FXML
    protected void setPaneNewState8() {
        setState(pane8);
    }

    @FXML
    protected void setPaneNewState9() {
        setState(pane9);
    }

    @FXML
    protected void setPaneNewState10() {
        setState(pane10);
    }

    @FXML
    protected void setPaneNewState11() {
        setState(pane11);
    }

    @FXML
    protected void setPaneNewState12() {
        setState(pane12);
    }

    @FXML
    protected void setPaneNewState13() {
        setState(pane13);
    }

    @FXML
    protected void setPaneNewState14() {
        setState(pane14);
    }

    @FXML
    protected void setPaneNewState15() {
        setState(pane15);
    }

    @FXML
    protected void setPaneNewState16() {
        setState(pane16);
    }

    @FXML
    protected void setPaneNewState17() {
        setState(pane17);
    }

    @FXML
    protected void setPaneNewState18() {
        setState(pane18);
    }

    @FXML
    protected void setPaneNewState19() {
        setState(pane19);
    }

    @FXML
    protected void setPaneNewState20() {
        setState(pane20);
    }

    @FXML
    protected void setPaneNewState21() {
        setState(pane21);
    }

    @FXML
    protected void setPaneNewState22() {
        setState(pane22);
    }

    @FXML
    protected void setPaneNewState23() {
        setState(pane23);
    }

    @FXML
    protected void setPaneNewState24() {
        setState(pane24);
    }


}