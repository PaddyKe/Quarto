package GUI;

import game.Board;
import game.Figure;
import interfaces.Player;
import interfaces.PlayerNotificatior;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import player.HumanPlayer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable, PlayerNotificatior {

    @FXML
    private ImageView figure0;
    @FXML
    private ImageView figure1;
    @FXML
    private ImageView figure2;
    @FXML
    private ImageView figure3;
    @FXML
    private ImageView figure4;
    @FXML
    private ImageView figure5;
    @FXML
    private ImageView figure6;
    @FXML
    private ImageView figure7;
    @FXML
    private ImageView figure8;
    @FXML
    private ImageView figure9;
    @FXML
    private ImageView figure10;
    @FXML
    private ImageView figure11;
    @FXML
    private ImageView figure12;
    @FXML
    private ImageView figure13;
    @FXML
    private ImageView figure14;
    @FXML
    private ImageView figure15;
    @FXML
    private ImageView selectedFigure;
    @FXML
    private Button applyButton;
    @FXML
    private TilePane figures;
    @FXML
    private Label messageLabel;
    @FXML
    private Canvas gameField;

    private GraphicsContext drawer;

    private Board board;

    private static final int FIGURE_SIZE = 75;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //this.figures.disableProperty().bind(Bindings.not(Bindings.equal("Wähle eine Figur", messageLabel.textProperty())));
        this.drawer = gameField.getGraphicsContext2D();

        try {
            this.drawer.drawImage(SwingFXUtils.toFXImage(ImageIO.read(this.getClass().getResource("/resources/background.png")), null), 0, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedImage br = new BufferedImage(75,75,BufferedImage.TYPE_INT_RGB);
        br.getGraphics().fillOval(0,0,75,75);
        this.figure0.setImage(SwingFXUtils.toFXImage(br, null));
        this.figure1.setImage(SwingFXUtils.toFXImage(br, null));
        this.figure4.setImage(SwingFXUtils.toFXImage(br, null));

        initGame(new HumanPlayer(), new HumanPlayer(), FIGURE_SIZE);
        this.drawField(FIGURE_SIZE);
    }

    private void initGame(Player p1, Player p2, int size) {
        this.board = new Board(p1, p2, this);
        this.board.nextRound();
        this.drawFigures(size);
    }


    private void drawFigures(int size) {
        Figure[] figures = this.board.getRemainingFiguresArray();

        int counter = 0;
        this.figure0.setImage((figures[counter++] != null) ? figures[counter - 1].toImage(size) : null);
        this.figure1.setImage((figures[counter++] != null) ? figures[counter - 1].toImage(size) : null);
        this.figure2.setImage((figures[counter++] != null) ? figures[counter - 1].toImage(size) : null);
        this.figure3.setImage((figures[counter++] != null) ? figures[counter - 1].toImage(size) : null);
        this.figure4.setImage((figures[counter++] != null) ? figures[counter - 1].toImage(size) : null);
        this.figure5.setImage((figures[counter++] != null) ? figures[counter - 1].toImage(size) : null);
        this.figure6.setImage((figures[counter++] != null) ? figures[counter - 1].toImage(size) : null);
        this.figure7.setImage((figures[counter++] != null) ? figures[counter - 1].toImage(size) : null);
        this.figure8.setImage((figures[counter++] != null) ? figures[counter - 1].toImage(size) : null);
        this.figure9.setImage((figures[counter++] != null) ? figures[counter - 1].toImage(size) : null);
        this.figure10.setImage((figures[counter++] != null) ? figures[counter - 1].toImage(size) : null);
        this.figure11.setImage((figures[counter++] != null) ? figures[counter - 1].toImage(size) : null);
        this.figure12.setImage((figures[counter++] != null) ? figures[counter - 1].toImage(size) : null);
        this.figure13.setImage((figures[counter++] != null) ? figures[counter - 1].toImage(size) : null);
        this.figure14.setImage((figures[counter++] != null) ? figures[counter - 1].toImage(size) : null);
        this.figure15.setImage((figures[counter++] != null) ? figures[counter - 1].toImage(size) : null);
    }

    private void drawField(int size) {
        Figure[] field = this.board.getField();

        for (int i = 0; i < field.length; i++) {
            if (field[i] != null) {
                int[] coordinates = indexToCoordinates(i, size);
                this.drawer.drawImage(field[i].toImage(size), coordinates[0], coordinates[1]);
            }
        }
    }

    public void figureSelect(MouseEvent mouseEvent) {
        int index = Integer.parseInt(((ImageView)mouseEvent.getSource()).getId().replaceAll("figure", ""));
        this.board.handleFigureClick(index);
    }

    public void gameFieldClicked(MouseEvent mouseEvent) {
        //System.out.println(mouseEvent);
        int index = Controller.mousePositionToIndex((int)mouseEvent.getX(), (int)mouseEvent.getY());
        if(index > -1)
            this.board.handleFieldClick(index);
    }


    public void applyButton(MouseEvent mouseEvent) {
        this.board.nextRound();
    }

    @Override
    public void notifyPlayer(String notification) {
        this.messageLabel.setText(notification);
    }

    @Override
    public void resetNotification() {

    }

    @Override
    public void updateView() {
        this.drawField(FIGURE_SIZE);
        this.drawFigures(FIGURE_SIZE);
        this.selectedFigure.setImage(board.getSelectedFigure() != null ? board.getSelectedFigure().toImage((int)selectedFigure.getFitWidth()) : null);
    }

    @Override
    public void setPlayer(String name) {
        //TODO tell the players whos turn it is.
    }

    private static int mousePositionToIndex(int x, int y) {
        int radius = 55;
        if(Math.sqrt(Math.pow(291 - x, 2) + Math.pow(58 - y, 2)) <= radius) {
            return 0;
        } else if(Math.sqrt(Math.pow(368 - x, 2) + Math.pow(134 - y, 2)) <= radius) {
            return 1;
        }  else if(Math.sqrt(Math.pow(446 - x, 2) + Math.pow(212 - y, 2)) <= radius) {
            return 2;
        } else if(Math.sqrt(Math.pow(523 - x, 2) + Math.pow(289 - y, 2)) <= radius) {
            return 3;
        } else if(Math.sqrt(Math.pow(213 - x, 2) + Math.pow(134 - y, 2)) <= radius) {
            return 4;
        } else if(Math.sqrt(Math.pow(291 - x, 2) + Math.pow(212 - y, 2)) <= radius) {
            return 5;
        } else if(Math.sqrt(Math.pow(368 - x, 2) + Math.pow(289 - y, 2)) <= radius) {
            return 6;
        } else if(Math.sqrt(Math.pow(446 - x, 2) + Math.pow(367 - y, 2)) <= radius) {
            return 7;
        } else if(Math.sqrt(Math.pow(135 - x, 2) + Math.pow(212 - y, 2)) <= radius) {
            return 8;
        } else if(Math.sqrt(Math.pow(213 - x, 2) + Math.pow(289 - y, 2)) <= radius) {
            return 9;
        } else if(Math.sqrt(Math.pow(291 - x, 2) + Math.pow(367 - y, 2)) <= radius) {
            return 10;
        } else if(Math.sqrt(Math.pow(368 - x, 2) + Math.pow(444 - y, 2)) <= radius) {
            return 11;
        } else if(Math.sqrt(Math.pow(58 - x, 2) + Math.pow(289 - y, 2)) <= radius) {
            return 12;
        } else if(Math.sqrt(Math.pow(135 - x, 2) + Math.pow(367 - y, 2)) <= radius) {
            return 13;
        } else if(Math.sqrt(Math.pow(213 - x, 2) + Math.pow(444 - y, 2)) <= radius) {
            return 14;
        } else if(Math.sqrt(Math.pow(291 - x, 2) + Math.pow(522 - y, 2)) <= radius) {
            return 15;
        } else {
            return -1;
        }
    }

    private static int[] indexToCoordinates(int index, int size) {
        switch (index) {
            case 0:
                return new int[] {291 - (size / 2), 58 - (size / 2) };
            case 1:
                return new int[] {368 - (size / 2), 134 - (size / 2)};
            case 2:
                return new int[] {446 - (size / 2), 212 - (size / 2)};
            case 3:
                return new int[] {523 - (size / 2), 289 - (size / 2)};
            case 4:
                return new int[] {213 - (size / 2), 134 - (size / 2)};
            case 5:
                return new int[] {291 - (size / 2), 212 - (size / 2)};
            case 6:
                return new int[] {368 - (size / 2), 289 - (size / 2)};
            case 7:
                return new int[] {446 - (size / 2), 367 - (size / 2)};
            case 8:
                return new int[] {135 - (size / 2), 212 - (size / 2)};
            case 9:
                return new int[] {213 - (size / 2), 289 - (size / 2)};
            case 10:
                return new int[] {291 - (size / 2), 367 - (size / 2)};
            case 11:
                return new int[] {368 - (size / 2), 444 - (size / 2)};
            case 12:
                return new int[] {58 - (size / 2) , 289 - (size / 2)};
            case 13:
                return new int[] {135 - (size / 2), 367 - (size / 2)};
            case 14:
                return new int[] {213 - (size / 2), 444 - (size / 2)};
            case 15:
                return new int[] {291 - (size / 2), 522 - (size / 2)};
            default:
                return new int[]{};
        }
    }

}
