package Graphics;/*

Name: Ethan Chen
Class: ZZZForecastScreen
Description: JavaFX Graphics for Forecast Event

*/
import Game.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ZZZForecastScreen {

    // pulls up screen allowing the player to reorder the top 5 cards on the infection deck
    public static void display(Game pandemic, PlayerCard cardSelected, Label updateLabel) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        VBox vbox = new VBox();
        vbox.setSpacing(20);
        vbox.setPadding(new Insets(10, 10, 10, 10));

        LinkedList<InfectionCard> topCards = new LinkedList();
        for(int x = 0; x<5; x++) {
            topCards.add(pandemic.infectionDeck.pop());
        }

        Button submit = new Button("Submit");
        submit.setDisable(true);

        ComboBox<String> c1 = new ComboBox<>();
        ComboBox<String> c2 = new ComboBox<>();
        ComboBox<String> c3 = new ComboBox<>();
        ComboBox<String> c4 = new ComboBox<>();
        ComboBox<String> c5 = new ComboBox<>();

        HBox hbox1 = new HBox();
        hbox1.setSpacing(20);
        Label l1 = new Label("Top");
        hbox1.getChildren().addAll(l1, c1);
        for(InfectionCard card : topCards) {
            c1.getItems().add(card.getInfectionCity().getName());
        }
        c1.setOnAction(e -> {
            if(c2.getValue() != null && c2.getValue().equals(c1.getValue())) {
                c2.setValue(null);
            }
            if(c3.getValue() != null && c3.getValue().equals(c1.getValue())) {
                c3.setValue(null);
            }
            if(c4.getValue() != null && c4.getValue().equals(c1.getValue())) {
                c4.setValue(null);
            }
            if(c5.getValue() != null && c5.getValue().equals(c1.getValue())) {
                c5.setValue(null);
            }

            if(c1.getValue() != null && c2.getValue()!= null && c3.getValue() != null
                    && c4.getValue() != null && c5.getValue() != null) {
                submit.setDisable(false);
            } else {
                submit.setDisable(true);
            }

        });

        HBox hbox2 = new HBox();
        hbox2.setSpacing(20);
        Label l2 = new Label("Second");
        hbox2.getChildren().addAll(l2, c2);
        for(InfectionCard card : topCards) {
            c2.getItems().add(card.getInfectionCity().getName());
        }
        c2.setOnAction(e -> {
            if(c1.getValue() != null && c1.getValue().equals(c2.getValue())) {
                c1.setValue(null);
            }
            if(c3.getValue() != null && c3.getValue().equals(c2.getValue())) {
                c3.setValue(null);
            }
            if(c4.getValue() != null && c4.getValue().equals(c2.getValue())) {
                c4.setValue(null);
            }
            if(c5.getValue() != null && c5.getValue().equals(c2.getValue())) {
                c5.setValue(null);
            }

            if(c1.getValue() != null && c2.getValue()!= null && c3.getValue() != null
                    && c4.getValue() != null && c5.getValue() != null) {
                submit.setDisable(false);
            } else {
                submit.setDisable(true);
            }

        });

        HBox hbox3 = new HBox();
        hbox3.setSpacing(20);
        Label l3 = new Label("Third");
        hbox3.getChildren().addAll(l3, c3);
        for(InfectionCard card : topCards) {
            c3.getItems().add(card.getInfectionCity().getName());
        }
        c3.setOnAction(e -> {
            if(c1.getValue() != null && c1.getValue().equals(c3.getValue())) {
                c1.setValue(null);
            }
            if(c2.getValue() != null && c2.getValue().equals(c3.getValue())) {
                c2.setValue(null);
            }
            if(c4.getValue() != null && c4.getValue().equals(c3.getValue())) {
                c4.setValue(null);
            }
            if(c5.getValue() != null && c5.getValue().equals(c3.getValue())) {
                c5.setValue(null);
            }

            if(c1.getValue() != null && c2.getValue()!= null && c3.getValue() != null
                    && c4.getValue() != null && c5.getValue() != null) {
                submit.setDisable(false);
            } else {
                submit.setDisable(true);
            }

        });

        HBox hbox4 = new HBox();
        hbox4.setSpacing(20);
        Label l4 = new Label("Fourth");
        hbox4.getChildren().addAll(l4, c4);
        for(InfectionCard card : topCards) {
            c4.getItems().add(card.getInfectionCity().getName());
        }
        c4.setOnAction(e -> {
            if(c1.getValue() != null && c1.getValue().equals(c4.getValue())) {
                c1.setValue(null);
            }
            if(c2.getValue() != null && c2.getValue().equals(c4.getValue())) {
                c2.setValue(null);
            }
            if(c3.getValue() != null && c3.getValue().equals(c4.getValue())) {
                c3.setValue(null);
            }
            if(c5.getValue() != null && c5.getValue().equals(c4.getValue())) {
                c5.setValue(null);
            }

            if(c1.getValue() != null && c2.getValue()!= null && c3.getValue() != null
                    && c4.getValue() != null && c5.getValue() != null) {
                submit.setDisable(false);
            } else {
                submit.setDisable(true);
            }

        });

        HBox hbox5 = new HBox();
        hbox5.setSpacing(20);
        Label l5 = new Label("Fifth");
        hbox5.getChildren().addAll(l5, c5);
        for(InfectionCard card : topCards) {
            c5.getItems().add(card.getInfectionCity().getName());
        }
        c5.setOnAction(e -> {
            if(c1.getValue() != null && c1.getValue().equals(c5.getValue())) {
                c1.setValue(null);
            }
            if(c2.getValue() != null && c2.getValue().equals(c5.getValue())) {
                c2.setValue(null);
            }
            if(c3.getValue() != null && c3.getValue().equals(c5.getValue())) {
                c3.setValue(null);
            }
            if(c4.getValue() != null && c4.getValue().equals(c5.getValue())) {
                c4.setValue(null);
            }

            if(c1.getValue() != null && c2.getValue()!= null && c3.getValue() != null
                    && c4.getValue() != null && c5.getValue() != null) {
                submit.setDisable(false);
            } else {
                submit.setDisable(true);
            }

        });

        submit.setOnAction(e -> {
            int index1 = c5.getItems().indexOf(c5.getValue());
            pandemic.infectionDeck.push(topCards.get(index1));
            int index2 = c4.getItems().indexOf(c4.getValue());
            pandemic.infectionDeck.push(topCards.get(index2));
            int index3 = c3.getItems().indexOf(c3.getValue());
            pandemic.infectionDeck.push(topCards.get(index3));
            int index4 = c2.getItems().indexOf(c2.getValue());
            pandemic.infectionDeck.push(topCards.get(index4));
            int index5 = c1.getItems().indexOf(c1.getValue());
            pandemic.infectionDeck.push(topCards.get(index5));

            if(pandemic.currPlayer.getOperativeNumber() != 5 || cardSelected.getHasBeenPlayed()) {
                pandemic.currPlayer.discardFromHand(cardSelected);
            } else {
                cardSelected.playCard();
            }

            stage.close();
        });

        vbox.getChildren().addAll(hbox1, hbox2, hbox3, hbox4, hbox5, submit);

        Scene scene = new Scene(vbox, 500, 500);
        stage.setScene(scene);
        stage.setTitle("Forecast");
        stage.showAndWait();
    }
}
