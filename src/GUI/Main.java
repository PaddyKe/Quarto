package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();//FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Quarto the Game by Patrick Kempf");
        Controller c = loader.getController();
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/icon.png")));
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> c.shutdown(event));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
