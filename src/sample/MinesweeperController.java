package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MinesweeperController {
    @FXML
    Button startBtn;
    @FXML
    Button exitBtn;
    private MinesweeperBoard boardScene;

    public void initialize() throws Exception {
        this.boardScene = new MinesweeperBoard(10, 10);
    }

    @FXML
    private void startGame(MouseEvent mouseEvent) throws Exception {
        Stage stage = (Stage) startBtn.getScene().getWindow();
        boardScene.start(stage);
    }

    @FXML
    public void exitGame(MouseEvent mouseEvent) {
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }
}
