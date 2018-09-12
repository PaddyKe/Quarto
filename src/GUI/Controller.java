package GUI;

import interfaces.PlayerNotificatior;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;

import javax.imageio.ImageIO;
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.figures.disableProperty().bind(Bindings.equal("Wähle eine Figur", messageLabel.textProperty()));
        this.drawer = gameField.getGraphicsContext2D();

    }

    public void figureSelect(MouseEvent mouseEvent) {
        System.out.println(mouseEvent);
    }

    public void gameFieldClicked(MouseEvent mouseEvent) {
        System.out.println(mouseEvent);

    }

    public void applyButton(MouseEvent mouseEvent) {

        gameField.getGraphicsContext2D().drawImage(new Image("file:P:\\Patrick\\Documents\\Processing_Projects\\quarto\\quarto\\wood.jpg"), 0, 0);


        System.out.println(figures.isDisabled());


    }

    @Override
    public void notifyPlayer(String notification) {
        this.messageLabel.setText(notification);
    }

    @Override
    public void resetNotification() {

    }
}
