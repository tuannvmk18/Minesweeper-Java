package sample;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Random;

public class MinesweeperBoard {
    private final int WINDOW_WIDTH = 500;
    private final int WINDOW_HEIGHT = 500;
    private final int N;
    private final int numberOfBoom;
    private final int[][] rootMap;
    private final Button[][] btnGroup;
    GridPane gridPane;

    public MinesweeperBoard(int N, int numberOfBoom) {
        this.N = N;
        this.numberOfBoom = numberOfBoom;
        this.btnGroup = new Button[N][N];
        this.rootMap = new int[N][N];
        this.initMap();
        this.printRootMap();
    }

    public void start(Stage window) throws Exception {
        try {
            gridPane = new GridPane();
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    this.btnGroup[i][j] = new Button();
                    this.btnGroup[i][j].setFont(new Font(20));
                    this.btnGroup[i][j].setId(i + "_" + j);
                    this.btnGroup[i][j].setOnMouseClicked(this::handleClick);
                    this.btnGroup[i][j].setMinSize(WINDOW_WIDTH / N, WINDOW_HEIGHT / N);
                    this.btnGroup[i][j].setFocusTraversable(false);
                    switch (rootMap[i][j]) {
                        case 1: {
                            btnGroup[i][j].setTextFill(Color.BLUE);
                            break;
                        }
                        case 2: {
                            btnGroup[i][j].setTextFill(Color.GREEN);
                            break;
                        }
                        case 3: {
                            btnGroup[i][j].setTextFill(Color.RED);
                            break;
                        }
                        default: {
                            btnGroup[i][j].setTextFill(Color.VIOLET);
                            break;
                        }
                    }
                    this.gridPane.add(this.btnGroup[i][j], j, i);
                }
            }
            Scene scene = new Scene(gridPane, 500, 500);
            window.setResizable(false);
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void initMap() {
        Random rand = new Random();
        for (int i = 0; i < numberOfBoom; i++) {
            int x = rand.nextInt(N);
            int y = rand.nextInt(N);
            if (rootMap[x][y] == 0) rootMap[x][y] = -1;
            else i--;
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int count = 0;
                if (rootMap[i][j] != 0) continue;
                for (int x = -1; x < 2; x++) {
                    for (int y = -1; y < 2; y++) {
                        if (x + i >= 0 && y + j >= 0 && x + i < N && y + j < N && rootMap[x + i][y + j] == -1) {
                            count++;
                        }
                    }
                    rootMap[i][j] = count;
                }
            }
        }
    }

    private void printRootMap() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(this.rootMap[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public void handleClick(MouseEvent p) {
        if(p.getButton() == MouseButton.PRIMARY) {
            Button temp = (Button) p.getSource();
            int x = Integer.parseInt(String.valueOf(temp.getId().charAt(0)));
            int y = Integer.parseInt(String.valueOf(temp.getId().charAt(2)));
            runAlgorithm(x, y);
        }
    }

    public void runAlgorithm(int x, int y) {
        if (btnGroup[x][y].isDisable()) return;

        if (rootMap[x][y] == -1) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setContentText("Booooom, You lose");
            alert.showAndWait();
            return;
        }

        if (rootMap[x][y] != 0) {
            btnGroup[x][y].setText("" + rootMap[x][y]);

            if (!btnGroup[x][y].isDisable()) {
                btnGroup[x][y].setDisable(true);
            }
            return;
        }

        if (!btnGroup[x][y].isDisable()) {
            btnGroup[x][y].setDisable(true);
        }
        for (int posX = -1; posX < 2; posX++) {
            for (int posY = -1; posY < 2; posY++) {
                if (x + posX >= 0 && y + posY >= 0 && x + posX < N && y + posY < N && rootMap[x + posX][y + posY] != -1) {
                    runAlgorithm(x + posX, y + posY);
                }
            }
        }
    }
}