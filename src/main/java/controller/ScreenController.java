package controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import service.WordGenerator;
import utils.WordContainer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class ScreenController {
    public static final String[] BLUE_TEAM_CARD_PICS = {"/pics/Blue1.png", "/pics/Blue2.png"};
    public static final String[] RED_TEAM_CARD_PICS = {"/pics/Red1.png", "/pics/Red2.png"};
    public static final String[] YELLOW_TEAM_CARD_PICS = {"/pics/Yellow1.png", "/pics/Yellow2.png"};
    public static final String WORD_CARD_PIC = "/pics/card0.png";
    public static final Background ANTIQUEWHITE_BACKGROUND = new Background(new BackgroundFill(Color.ANTIQUEWHITE, null, null));
    public static final BackgroundSize BACKGROUND_SIZE_TYPICAL = new BackgroundSize(1, 1, true, true, false, false);
    public static final int CARD_VISUALS_COUNT = 2;
    public static final String[] CARD_VISUALS_COLORS = {"yellow", "blue", "red"};
    public static final Font TAHOMA_FONT = new Font("Tahoma", 20);

    private static String currentChosenColor;

    private int redNumCount = 0;
    private int blueNumCount = 0;
    private WordGenerator wg = new WordGenerator();
    private static List<String> words;

    private Stage stage;

    private Background basicCardBackground;
    private List<Background> blueCardBackgrounds = new ArrayList<>();
    private List<Background> redCardBackgrounds = new ArrayList<>();
    private List<Background> yellowCardBackgrounds = new ArrayList<>();

    {
        basicCardBackground = new Background(new BackgroundImage(new Image(getClass().getResource(WORD_CARD_PIC).toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BACKGROUND_SIZE_TYPICAL));
        for (String url : BLUE_TEAM_CARD_PICS) {
            blueCardBackgrounds.add(new Background(new BackgroundImage(new Image(getClass().getResource(url).toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BACKGROUND_SIZE_TYPICAL)));
        }
        for (String url : RED_TEAM_CARD_PICS) {
            redCardBackgrounds.add(new Background(new BackgroundImage(new Image(getClass().getResource(url).toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BACKGROUND_SIZE_TYPICAL)));
        }
        for (String url : YELLOW_TEAM_CARD_PICS) {
            yellowCardBackgrounds.add(new Background(new BackgroundImage(new Image(getClass().getResource(url).toExternalForm()), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BACKGROUND_SIZE_TYPICAL)));
        }
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
        int index = rand.nextInt(CARD_VISUALS_COUNT);
        Background background;
        switch (currentChosenColor) {
            case "red":
                background = redCardBackgrounds.get(index);
                redNumCount += 1;
                setRedNum(redNumCount);
                break;
            case "blue":
                background = blueCardBackgrounds.get(index);
                blueNumCount += 1;
                setBlueNum(blueNumCount);
                break;
            default:
                background = yellowCardBackgrounds.get(index);
                break;
        }
        getSetPanesActResetStateOnClick(currentPane);
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
                if (redCardBackgrounds.contains(currentPane.getBackground())) {
                    redNumCount -= 1;
                    setRedNum(redNumCount);
                } else if (blueCardBackgrounds.contains(currentPane.getBackground())) {
                    blueNumCount -= 1;
                    setBlueNum(blueNumCount);
                }
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
        else redNum.setText(String.valueOf(num));
    }

    @FXML
    private void setBlueNum(Integer num) {
        if (num == null) blueNum.setText("0");
        else blueNum.setText(String.valueOf(num));
    }

    @FXML
    public void startNewGame() {
        redNumCount = 0;
        blueNumCount = 0;
        setRedNum(null);
        setBlueNum(null);
        setPanesNewWords(gridPane.getScene(), true);
    }

    @FXML
    private void setPanesNewWords(Scene scene, boolean clearPanes) {
        words = wg.giveMeWordsToPlay();
        for (int i = 0; i < 25; i++) {
            Text text1 = (Text) (scene.lookup("#word".concat(String.valueOf(i)).concat("_1")));
            if (clearPanes) {
                Pane currentPane = (Pane) (text1.getParent().getParent());
                resetPane(currentPane);
            }
            String word = words.get(i);
            text1.setText(word);
            Text text2 = (Text) (scene.lookup("#word".concat(String.valueOf(i)).concat("_2")));
            text2.setText(word);
            text1.setFont(new Font(text1.getFont().getName(), 30));
            text2.setFont(new Font(text2.getFont().getName(), 28));
            if (word.length() > 10) {
                text1.setFont(new Font(text1.getFont().getName(), text1.getFont().getSize() * 0.76));
                text2.setFont(new Font(text2.getFont().getName(), text2.getFont().getSize() * 0.76));
            } else if (word.length() > 8) {
                text1.setFont(new Font(text1.getFont().getName(), text1.getFont().getSize() * 0.84));
                text2.setFont(new Font(text2.getFont().getName(), text2.getFont().getSize() * 0.84));
            } else if (word.length() > 5) {
                text1.setFont(new Font(text1.getFont().getName(), text1.getFont().getSize() * 0.92));
                text2.setFont(new Font(text2.getFont().getName(), text2.getFont().getSize() * 0.92));
            }
        }
    }

    private void resetPane(Pane currentPane) {
        currentPane.setBackground(basicCardBackground);
        currentPane.getChildren().forEach(o -> o.setVisible(true));
        currentPane.setOnMouseClicked(event -> setState(currentPane));
    }

    public void setStage(Stage stage) {
        this.stage = stage;

    }

    public void show() {
        setVocabularyFromDialog("Выбор словаря");
        setPanesNewWords(stage.getScene(), false);
        stage.show();
    }

    private void setVocabularyFromDialog(String dialogMessage) {
        Dialog<String> dialog = new ChoiceDialog<>("Оригинальный словарь", "Альтернативный словарь", "Файл (.txt, разделитель слов - пробел, слова - до 15 букв)");
        dialog.setTitle(dialogMessage);
        dialog.setHeaderText("Какой словарь использовать для игры?\n(отмена для выбора базового словаря)");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String s = result.get();
            switch (s) {
                case "Оригинальный словарь":
                    wg.setVocabularyFromInnerData(WordContainer.DEFAULT_ORIGINAL);
                    break;
                case "Альтернативный словарь":
                    wg.setVocabularyFromInnerData(WordContainer.ADDITIONAL_VOCABULARY);
                    break;
                case "Файл (.txt, разделитель слов - пробел, слова - до 15 букв)":
                    if (!wg.setVocabularyFromFile(setVocabularyFileAdressDialog("Выберие файл словаря")))
                        setVocabularyFromDialog("Некорректный файл. Выберите другой словарь");
                    break;
            }
        } else {
            wg.setVocabularyFromInnerData(WordContainer.DEFAULT_ORIGINAL);
        }
    }

    private File setVocabularyFileAdressDialog(String message) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(message);
        fileChooser.getExtensionFilters().add((new FileChooser.ExtensionFilter("Text Files", "*.txt")));
        fileChooser.setInitialDirectory(new File("/"));
        return fileChooser.showOpenDialog(stage);
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