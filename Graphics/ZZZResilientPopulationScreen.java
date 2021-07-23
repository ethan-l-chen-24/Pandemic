package Graphics;/*

Name: Ethan Chen
Class: ZZZResilientPopulationScreen
Description: JavaFX Graphics for Resilient Population Event

*/
import Game.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ZZZResilientPopulationScreen {

    private static Stage stage;

    // allows the player to choose one card from the infection discard to remove from the game entirely
    public static void display(Game pandemic, PlayerCard cardSelected, Label updateLabel) {
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        VBox vbox = new VBox();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(vbox);

        Label label = new Label("Which infection card would you like to remove from the game?");
        vbox.getChildren().add(label);
        ToggleGroup toggleGroup = new ToggleGroup();
        for(InfectionCard c : pandemic.infectionDiscard) {
            RadioButton infectDiscarded = new RadioButton(c.getInfectionCity().getName());
            infectDiscarded.setToggleGroup(toggleGroup);
            vbox.getChildren().add(infectDiscarded);
        }

        Button submit = new Button("Submit");
        submit.setOnAction(e -> {
            int index = toggleGroup.getToggles().indexOf(toggleGroup.getSelectedToggle());
            InfectionCard card = pandemic.infectionDiscard.popFromIndex(index);
            if(pandemic.currPlayer.getOperativeNumber() != 5 || cardSelected.getHasBeenPlayed()) {
                pandemic.currPlayer.discardFromHand(cardSelected);
            } else {
                cardSelected.playCard();
            }
            updateLabel.setText("You have removed " + card.getInfectionCity().getName() + " from the infection deck.");
            stage.close();
        });
        submit.setDisable(true);
        vbox.getChildren().add(submit);

        toggleGroup.selectedToggleProperty().addListener((v, oldValue, newValue) -> submit.setDisable(false));

        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(20);

        Scene scene = new Scene(scrollPane, 500, 500);
        stage.setScene(scene);
        stage.setTitle("Resilient Population");
        stage.showAndWait();
    }
}
