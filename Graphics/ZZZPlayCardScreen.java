package Graphics;/*

Name: Ethan Chen
Class: ZZZPlayCardcreen
Description: JavaFX Graphics for Playing a Card

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

public class ZZZPlayCardScreen {

    private static RadioButton charter, researchStation, direct, playEvent;

    // gives a list of the players cards in their hand to play
    public static void display(Game pandemic, Label updateLabel) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        VBox vbox = new VBox();
        VBox vbox2 = new VBox();
        vbox2.setSpacing(20);

        Label label = new Label("Which card would you like to play? ");
        vbox.getChildren().add(label);

        ToggleGroup toggleGroup = new ToggleGroup();
        for (PlayerCard c : pandemic.currPlayer.getHand()) {
            RadioButton playCard = new RadioButton(c.getCardName());
            playCard.setToggleGroup(toggleGroup);
            vbox.getChildren().add(playCard);
        }

        Label actionLabel = new Label("What action would you like to perform with this card?");
        vbox.getChildren().add(actionLabel);

        Button submit = new Button("Submit");
        submit.setDisable(true);
        submit.setOnAction(e -> {
            int index = toggleGroup.getToggles().indexOf(toggleGroup.getSelectedToggle());
            PlayerCard cardSelected = pandemic.currPlayer.getHand().get(index);
            City cardCity = null;
            if(cardSelected.getCardType() == 0) {
                cardCity = pandemic.cities[cardSelected.getCardIndex()];
            }
            if (charter.isSelected()) {
                ZZZCharterFlightScreen.display(pandemic, cardSelected, pandemic.currPlayer, updateLabel);
            } else if (researchStation.isSelected()) {
                if (!pandemic.cities[cardSelected.getCardIndex()].hasResearchStation()) {
                    pandemic.researchStation(cardCity);
                    pandemic.currPlayer.discardFromHand(cardSelected);
                    updateLabel.setText("You have built a research station in " + cardCity.getName() + ".");
                } else {
                    updateLabel.setText("There is already a research station in " + cardCity.getName() + ".");
                }
            } else if (direct.isSelected()) {
                if(pandemic.currPlayer.getOperativeNumber() == 1 && pandemic.currCity.hasResearchStation()) {
                    ZZZCharterFlightScreen.display(pandemic, cardSelected, pandemic.currPlayer, updateLabel);
                } else {
                    pandemic.directFlight(cardCity);
                }
                pandemic.currPlayer.discardFromHand(cardSelected);
                updateLabel.setText("You have taken a direct flight to " + cardCity.getName() + ".");
            } else if (playEvent.isSelected()) {
                if (cardSelected.getCardName().equals("Airlift")) {
                    ZZZAirliftScreen.display(pandemic, cardSelected, updateLabel);
                } else if (cardSelected.getCardName().equals("One Quiet Night")) {
                    pandemic.quietNight();
                    if(pandemic.currPlayer.getOperativeNumber() != 5 || cardSelected.getHasBeenPlayed()) {
                        pandemic.currPlayer.discardFromHand(cardSelected);
                    } else {
                        cardSelected.playCard();
                    }
                } else if (cardSelected.getCardName().equals("Forecast")) {
                    ZZZForecastScreen.display(pandemic, cardSelected, updateLabel);
                } else if (cardSelected.getCardName().equals("Government Grant")) {
                    ZZZGovernmentGrantScreen.display(pandemic, cardSelected, updateLabel);
                } else {
                    ZZZResilientPopulationScreen.display(pandemic, cardSelected, updateLabel);
                }
            }
            stage.close();
        });

        toggleGroup.selectedToggleProperty().addListener((v, oldValue, newValue) -> {
            submit.setDisable(true);
            checkOptionsForToggle(toggleGroup.getToggles().indexOf(newValue), vbox2, pandemic, submit);
        });

        vbox.getChildren().add(vbox2);
        vbox.getChildren().add(submit);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(20);


        Scene scene = new Scene(vbox, 500, 500);
        stage.setScene(scene);
        stage.setTitle("Play Card");
        stage.showAndWait();
    }

    private static void checkOptionsForToggle(int indexOf, VBox vbox, Game pandemic, Button confirm) {

        vbox.getChildren().clear();

        charter = new RadioButton();
        researchStation = new RadioButton();
        playEvent = new RadioButton();
        direct = new RadioButton();

        ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.selectedToggleProperty().addListener((v, oldValue, newValue) -> {
            confirm.setDisable(false);
        });

        PlayerCard cardSelected = pandemic.currPlayer.getHand().get(indexOf);
        if (cardSelected.getCardName().equals(pandemic.currPlayer.getCurrCity().getName())) {
            charter = new RadioButton("Charter Flight");
            charter.setToggleGroup(toggleGroup);
            vbox.getChildren().add(charter);
            if(pandemic.currPlayer.getOperativeNumber() != 1) {
                researchStation = new RadioButton("Research Station");
                researchStation.setToggleGroup(toggleGroup);
                vbox.getChildren().add(researchStation);
            }
        } else if (cardSelected.getCardType() == 1) {
            playEvent = new RadioButton("Play Event");
            playEvent.setToggleGroup(toggleGroup);
            vbox.getChildren().add(playEvent);
        } else {
            direct = new RadioButton("Direct Flight");
            direct.setToggleGroup(toggleGroup);
            vbox.getChildren().add(direct);
        }
    }

}



