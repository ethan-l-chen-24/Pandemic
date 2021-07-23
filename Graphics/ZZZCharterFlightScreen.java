package Graphics;/*

Name: Ethan Chen
Class: ZZZCharterFlightScreen
Description: JavaFX Graphics for Charter Flight

*/

import Game.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ZZZCharterFlightScreen {

    // gives options for where to move the player, and moves them
    public static void display(Game pandemic, PlayerCard charterCard, Player player, Label updateLabel) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        VBox vbox = new VBox();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(vbox);

        Label label = new Label("Where would you like to travel? ");
        vbox.getChildren().add(label);

        Button submit = new Button("Submit");

        ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.selectedToggleProperty().addListener((v, oldValue, newValue) -> submit.setDisable(false));
        for (City c : pandemic.cities) {
            RadioButton cityCard = new RadioButton(c.getName());
            cityCard.setToggleGroup(toggleGroup);
            vbox.getChildren().add(cityCard);
        }

        submit.setDisable(true);
        submit.setOnAction(e -> {
            int index = toggleGroup.getToggles().indexOf(toggleGroup.getSelectedToggle());
            City chosenCity = pandemic.cities[index];
            if(charterCard.getCardType() == 0) {
                pandemic.charterFlight(chosenCity);
                pandemic.currPlayer.discardFromHand(charterCard);
                updateLabel.setText("You have taken a charter flight to " + chosenCity.getName() + ".");
            } else {
                pandemic.airlift(chosenCity, player);
                if(pandemic.currPlayer.getOperativeNumber() != 5 || charterCard.getHasBeenPlayed()) {
                    pandemic.currPlayer.discardFromHand(charterCard);
                } else {
                    charterCard.playCard();
                }
                updateLabel.setText(player.getName() + " has taken been airlifted to " + chosenCity.getName() + ".");
            }
            stage.close();
        });

        vbox.getChildren().add(submit);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(20);


        Scene scene = new Scene(scrollPane, 500, 500);
        stage.setScene(scene);
        stage.setTitle("Charter Flight");
        stage.showAndWait();
    }
}
