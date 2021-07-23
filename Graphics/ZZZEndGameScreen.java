package Graphics;/*

Name: Ethan Chen
Class: ZZZEndGameScreen
Description: JavaFX Graphics for End of the Game

*/
import Game.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ZZZEndGameScreen {

    // displays result of game
    public static void display(int i) {
        Stage stage = new Stage();
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);

        if(i == 0) {
            Label winLabel = new Label("You win!");
            vbox.getChildren().add(winLabel);
        } else {
            Label loseLabel = new Label("You lost! The pandemic spread across the world!");
            vbox.getChildren().add(loseLabel);
        }

        Button playAgain = new Button("Close Game");
        playAgain.setOnAction(e -> {
            stage.close();
        });
        vbox.getChildren().add(playAgain);

        Scene scene = new Scene(vbox, 500, 500);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
