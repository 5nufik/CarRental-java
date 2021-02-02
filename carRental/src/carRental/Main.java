package carRental;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {
    private static Stage pStage;
    private static final Toolkit kit = Toolkit.getDefaultToolkit();
    private static final Dimension ScreenSize = kit.getScreenSize();
    private static Scene pScene;

    @Override
    public void start(Stage stage) throws Exception{
        stage.setResizable(false);
        stage.setTitle("Прокат машин");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png" )));
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Main.fxml")));
        stage.setScene(scene);
        stage.show();
        stage.setX((int) ScreenSize.getWidth() / 2 - stage.getWidth() / 2);
        stage.setY((int) ScreenSize.getHeight() / 2 - stage.getHeight() / 2);
        setPrimaryStage(stage);
        setPrimaryScene(scene);
    }

    private void setPrimaryStage(Stage pStage) {
        this.pStage = pStage;
    }

    public static Stage getPrimaryStage() {
        return pStage;
    }

    private void setPrimaryScene(Scene pScene) {
        this.pScene = pScene;
    }

    public static void setScene(String title, Scene scene) {
        pStage.setTitle(title);
        pStage.setScene(scene);
        pStage.setX((int) ScreenSize.getWidth() / 2 - pStage.getWidth() / 2);
        pStage.setY((int) ScreenSize.getHeight() / 2 - pStage.getHeight() / 2);
        pStage.show();
    }

    public static void goMain() {
        setScene("Прокат машин", pScene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
