package gui;

import game.Board;
import game.Figure;
import interfaces.Player;
import interfaces.PlayerNotificatior;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import player.HumanPlayer;
import player.NotStupid;
import player.RandomPlayer;
import player.TheMaster;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
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
    @FXML
    private Label playerLabel;
    @FXML
    private MenuItem nameSetter;
    @FXML
    private RadioMenuItem opponentPVP;
    @FXML
    private RadioMenuItem opponentRandom;
    @FXML
    private RadioMenuItem opponentNotStupid;
    @FXML
    private RadioMenuItem opponentTheMaster;




    private GraphicsContext drawer;

    private Board board;
    private Image defaultBackgound;
    private static final int FIGURE_SIZE = 75;

    private static final boolean DISABLE_ACCEPT_BUTTON = true;




    public void shutdown(WindowEvent event) {
        board.gameRunning = false;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //this.figures.disableProperty().bind(Bindings.not(Bindings.equal("Wähle eine Figur", messageLabel.textProperty())));
        this.applyButton.setDisable(DISABLE_ACCEPT_BUTTON);
        this.drawer = gameField.getGraphicsContext2D();
        try {
            this.defaultBackgound = SwingFXUtils.toFXImage(ImageIO.read(this.getClass().getResource("/resources/background.png")), null);
            this.drawer.drawImage(this.defaultBackgound, 0, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedImage br = new BufferedImage(75,75,BufferedImage.TYPE_INT_RGB);
        br.getGraphics().fillOval(0,0,75,75);
        this.figure0.setImage(SwingFXUtils.toFXImage(br, null));
        this.figure1.setImage(SwingFXUtils.toFXImage(br, null));
        this.figure4.setImage(SwingFXUtils.toFXImage(br, null));



        // das ist der interessante part:
        //initGame(new HumanPlayer("Fiona"), new NotStupid(), FIGURE_SIZE);
        initGame(new HumanPlayer(), new HumanPlayer(), FIGURE_SIZE);

        // ab jetzt duerfte es wieder uninterssant werden.
        this.nameSetter.setText(this.board.getHumanPlayer().getName());
        this.drawField(FIGURE_SIZE);
    }

    private void initGame(Player p1, Player p2, int size) {
        this.board = new Board(p1, p2, this);
        this.board.nextRound();
        this.drawFigures(size);
        this.drawField(size);
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
        if(DISABLE_ACCEPT_BUTTON) this.applyButton(mouseEvent);
    }

    public void gameFieldClicked(MouseEvent mouseEvent) {
        if(!this.board.gameRunning) {
            this.restart();
        } else {
            //System.out.println(mouseEvent);
            int index = Controller.mousePositionToIndex((int) mouseEvent.getX(), (int) mouseEvent.getY());
            if (index > -1)
                this.board.handleFieldClick(index);
            if (DISABLE_ACCEPT_BUTTON) this.applyButton(mouseEvent);
        }
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
        this.playerLabel.setText(name);
    }

    @Override
    public void showWin(int[] fields) {
        this.drawer.setFill(Color.RED);
        for(int i : fields) {
            int[] pos = indexToCoordinates(i, FIGURE_SIZE);
            this.drawer.fillOval(pos[0] + FIGURE_SIZE / 3, pos[1] + FIGURE_SIZE / 3, 25, 25);
        }
    }

    @Override
    public void setStyle(Paint p) {
        this.messageLabel.setTextFill(p);
        this.playerLabel.setTextFill(p);
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

    public void restart() {
        //this.board = new Board(this.board.getP1(), this.board.getP2(), this);
        this.playerLabel.setTextFill(Color.BLACK);
        this.messageLabel.setTextFill(Color.BLACK);
        this.drawer.drawImage(this.defaultBackgound, 0, 0);
        initGame(this.board.getP1(), this.board.getP2(), FIGURE_SIZE);
    }

    public void setPlayerName(ActionEvent actionEvent) {
        Player hp;
        if(this.board.is101())
            hp = this.board.getLastPlayer();
        else
            hp = this.board.getHumanPlayer();

        String oldName = hp.getName();

        TextInputDialog dialog = new TextInputDialog(hp.getName());
        ((Stage) dialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image(getClass().getResourceAsStream("/resources/icon.png")));
        dialog.setTitle("Name festlegen");
        dialog.setHeaderText("Bitte gebe dir einen Namen");
        dialog.setContentText("Name: ");
        Optional<String> name = dialog.showAndWait();
        name.ifPresent(n -> hp.setName(n));
        this.nameSetter.setText(hp.getName());
        if(this.playerLabel.getText().equals(oldName)) this.playerLabel.setText(hp.getName());
    }

    public void selectOpponent(ActionEvent actionEvent) {

        RadioMenuItem selected = (RadioMenuItem) actionEvent.getSource();

        if(selected.equals(this.opponentPVP)) {
            if(!(this.board.getP1() instanceof HumanPlayer))
                this.board.setP1(new HumanPlayer());
            if(!(this.board.getP2() instanceof HumanPlayer))
                this.board.setP2(new HumanPlayer());




        } else if(selected.equals(this.opponentRandom)){
            Player not = this.board.getNotOnTurn(); // not -> not on turn
            if(not.equals(this.board.getP1()))
                this.board.setP1(new RandomPlayer());
            else
                this.board.setP2(new RandomPlayer());




        } else if(selected.equals(opponentNotStupid)) {
            Player not = this.board.getNotOnTurn(); // not -> not on turn
            if(not.equals(this.board.getP1()))
                this.board.setP1(new NotStupid());
            else
                this.board.setP2(new NotStupid());



        } else if(selected.equals(this.opponentTheMaster)) {
            Player not = this.board.getNotOnTurn(); // not -> not on turn
            if(not.equals(this.board.getP1()))
                this.board.setP1(new TheMaster());
            else
                this.board.setP2(new TheMaster());


        }


    }

    public void showAbout(ActionEvent actionEvent) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        ((Stage) a.getDialogPane().getScene().getWindow()).getIcons().add(new Image(getClass().getResourceAsStream("/resources/icon.png")));
        a.setTitle("Über");
        a.setHeaderText("Quarto von Patrick Kempf");
        a.setContentText("Dieses Spiel basiert auf dem gleichnamigen Brettspiel \"Quarto!\" (siehe https://de.wikipedia.org/wiki/Quarto!).\n\n" +
                         "Die PC-Version wurde von Patrick Kempf entwickelt und ist frei über GitHub (https://github.com/PaddyKe/Quarto) unter der MIT-Lizenz erhältlich.\n\n Viel Spaß beim Spielen");
        a.showAndWait();
    }

    public void showInstructions(ActionEvent actionEvent) throws URISyntaxException, IOException {
        java.awt.Desktop.getDesktop().browse(new URI("https://github.com/PaddyKe/Quarto/blob/rules/Rules.md"));
    }
}
