package controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private static final Background ANTIQUEWHITE_BACKGROUND = new Background(new BackgroundFill(Color.ANTIQUEWHITE, null, null));
    private static final int CARD_VISUALS_COUNT = 2;
    private static final String[] CARD_VISUALS_COLORS = {"yellow", "blue", "red"};
    private static final Font TAHOMA_FONT = new Font("Tahoma", 20);
    private static String currentChosenColor;
    private int redNumCount;
    private int blueNumCount;

    private WordGenerator wg = new WordGenerator();

    private List<String> words;

    private Stage stage;

    private Background basicCardBackground;

    {
        BackgroundSize backgroundSize = new BackgroundSize(1, 1, true, true, false, false);
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

    private void setState(Pane currentPane) {
        chooseTeamDialog();
        if (currentChosenColor == null) return;
        Random rand = new Random();
        String url;
        switch (currentChosenColor) {
            case "red":
                url = "/pics/Red".concat(String.valueOf(rand.nextInt(CARD_VISUALS_COUNT) + 1)).concat(".png");
                redNumCount += 1;
                setRedNum(redNumCount);
                break;
            case "blue":
                url = "/pics/Blue".concat(String.valueOf(rand.nextInt(CARD_VISUALS_COUNT) + 1)).concat(".png");
                blueNumCount += 1;
                setBlueNum(blueNumCount);
                break;
            default:
                url = "/pics/Yellow".concat(String.valueOf(rand.nextInt(CARD_VISUALS_COUNT) + 1)).concat(".png");
                break;
        }
        getSetPanesActResetStateOnClick(currentPane);
        BackgroundSize backgroundSize = new BackgroundSize(1, 1, true, true, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(new Image(getClass().getResource(url).toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        currentPane.setBackground(background);
        currentPane.getChildren().forEach(o -> o.setVisible(false));
    }

    private void chooseTeamDialog() {
        currentChosenColor = null;
        Dialog dialog = new ChoiceDialog<>();
        GridPane grid = new GridPane();
        grid.setBackground(ANTIQUEWHITE_BACKGROUND);
        List<Button> buttons = generateButtons(dialog);
        for (int i = 0; i < buttons.size(); i++) {
            grid.add(buttons.get(i), i, 0);
        }
        dialog.getDialogPane().setContent(grid);
        dialog.setTitle("Выбор команды агента");
        dialog.setHeaderText("Нажмите на нужный цвет:");
        dialog.showAndWait();
    }

    private List<Button> generateButtons(Dialog dialog) {
        List<Button> buttonList = new ArrayList<>();
        Color color;
        Button current;
        Tooltip tooltip;
        for (String colorName : CARD_VISUALS_COLORS) {
            color = Color.valueOf(colorName).desaturate();
            current = new Button();
            current.setBackground(new Background(new BackgroundFill(color, new CornerRadii(5), null)));
            current.setMinSize(100, 100);
            current.setMaxSize(100, 100);
            tooltip = new Tooltip();
            tooltip.setFont(TAHOMA_FONT);
            tooltip.setText(colorName);
            current.setTooltip(tooltip);
            current.setOnMouseClicked(event -> {
                        currentChosenColor = colorName;
                        dialog.close();
                    }
            );
            buttonList.add(current);
        }
        Button cancelButton = new Button("Отмена");
        cancelButton.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(5), null)));
        cancelButton.setMinSize(130, 100);
        cancelButton.setMaxSize(130, 100);
        cancelButton.setOnMouseClicked(event -> dialog.close());
        buttonList.add(cancelButton);
        return buttonList;
    }


    private void getSetPanesActResetStateOnClick(Pane currentPane) {
        currentPane.setOnMouseClicked(event -> {
            Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
            dialog.setHeaderText("Вернуть значение?");
            dialog.getDialogPane().setBackground(ANTIQUEWHITE_BACKGROUND);
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                currentPane.setBackground(basicCardBackground);
                currentPane.getChildren().forEach(o -> o.setVisible(true));
                currentPane.setOnMouseClicked(eventNext -> setState(currentPane));
                dialog.close();
            }
        });
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
            Pane pane = (Pane) (text1.getParent().getParent());
            pane.setBackground(basicCardBackground);
            String word = words.get(i);
            text1.setText(word);
            Text text2 = (Text) (scene.lookup("#word".concat(String.valueOf(i)).concat("_2")));
            text2.setText(word);
            if (word.length() > 10) {
                text1.setFont(new Font(text1.getFont().getName(), text1.getFont().getSize() * 0.7));
                text2.setFont(new Font(text2.getFont().getName(), text2.getFont().getSize() * 0.7));
            } else if (word.length() > 8) {
                text1.setFont(new Font(text1.getFont().getName(), text1.getFont().getSize() * 0.8));
                text2.setFont(new Font(text2.getFont().getName(), text2.getFont().getSize() * 0.8));
            } else if (word.length() > 6) {
                text1.setFont(new Font(text1.getFont().getName(), text1.getFont().getSize() * 0.9));
                text2.setFont(new Font(text2.getFont().getName(), text2.getFont().getSize() * 0.9));
            } else {
                text1.setFont(new Font(text1.getFont().getName(), 30));
                text2.setFont(new Font(text2.getFont().getName(), 28));
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