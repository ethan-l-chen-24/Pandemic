package Graphics;/*

Name: Ethan Chen
Class: ZZZAirliftScreen
Description: JavaFX Graphics for Airlift Event

*/

import Game.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ZZZAirliftScreen {

    // gives options for who to airlift
    public static void display(Game pandemic, PlayerCard cardSelected, Label updateLabel) {
        Stage stage = new Stage(); // creates the display
        stage.initModality(Modality.APPLICATION_MODAL);
        VBox vbox = new VBox();
        vbox.setSpacing(20);
        vbox.setPadding(new Insets(10, 10, 10, 10));

        Label label = new Label("Who would you like to airlift? ");
        vbox.getChildren().add(label);

        ToggleGroup toggleGroup = new ToggleGroup();
        LinkedList<Player> playersAvailableToMove = new LinkedList();

        RadioButton firstPlayer = new RadioButton(pandemic.currPlayer.getName());
        firstPlayer.setToggleGroup(toggleGroup);
        vbox.getChildren().add(firstPlayer);
        playersAvailableToMove.add(pandemic.currPlayer);

        for (Player p : pandemic.players) {
            if (p != null) {
                RadioButton tradeWith = new RadioButton(p.getName());
                tradeWith.setToggleGroup(toggleGroup);
                vbox.getChildren().add(tradeWith);
                playersAvailableToMove.add(p);
            }
        }

        playersAvailableToMove.add(pandemic.currPlayer);

        Button submit = new Button("Submit");
        submit.setOnAction(e -> {
            int indexChosen = toggleGroup.getToggles().indexOf(toggleGroup.getSelectedToggle());
            Player playerChosen = playersAvailableToMove.get(indexChosen);
            ZZZCharterFlightScreen.display(pandemic, cardSelected, playerChosen, updateLabel);
            stage.close();
        });
        vbox.getChildren().add(submit);

        Scene scene = new Scene(vbox, 500, 500);
        stage.setScene(scene);
        stage.showAndWait();

    }
}
