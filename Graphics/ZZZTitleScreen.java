package Graphics;/*

Name: Ethan Chen
Class: ZZZTitleScreen
Description: JavaFX Graphics for Title

*/
import Game.*;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ZZZTitleScreen {

    public ZZZTitleScreen() throws FileNotFoundException {
    }

    // a title screen - leads to settings screen or rules depending on what is selected
    public static void display() throws FileNotFoundException {

        Image titleImage = new Image(new FileInputStream("src/Images/pandemicLogo.jpeg"));
        ImageView titleView = new ImageView(titleImage);
        titleView.setFitWidth(450);
        titleView.setFitHeight(225);

        Stage stage = new Stage();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });


        VBox vbox = new VBox();
        Button play = new Button("Play Pandemic");
        play.setOnAction(e -> {
            stage.close();
        });

        Button rules = new Button("Rules");
        rules.setOnAction(e -> {
            ZZZRuleBook.display();
        });

        vbox.getChildren().addAll(titleView, play, rules);

        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(30);
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox, 500, 500);
        stage.setScene(scene);
        stage.showAndWait();
    }

}
