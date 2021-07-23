package Graphics;/*

Name: Ethan Chen
Class: ZZZDiscardCardsScreen
Description: JavaFX Graphics for Discard Cards

*/
import Game.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ZZZDiscardCardsScreen {

    private static CheckBox[] checkBoxes;

    // gives options for which cards to discard from hand
    public static void display(Game pandemic) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);

        VBox vbox = new VBox();
        vbox.setSpacing(20);
        vbox.setPadding(new Insets(10, 10, 10, 10));

        LinkedList<PlayerCard> hand = pandemic.currPlayer.getHand();
        int numCards = hand.size();
        int numNeeded = numCards-7;

        checkBoxes = new CheckBox[numCards];

        Label discardLabel = new Label("Please select the cards you would like to discard");
        Label discardLabel2 = new Label("Please select " + numNeeded + " or more cards");
        vbox.getChildren().addAll(discardLabel, discardLabel2);

        int index = 0;
        for(PlayerCard card : hand) {
            CheckBox cardCheckBox = new CheckBox(card.getCardName());
            vbox.getChildren().add(cardCheckBox);
            checkBoxes[index] = cardCheckBox;
            index++;
        }

        Button confirm = new Button("Confirm Discard");
        confirm.setOnAction(e -> {
            if(isValidDiscard(numNeeded)) {
                LinkedList<PlayerCard> cardsToDiscard = getChosenPlayerCards(hand);
                pandemic.discardCards(cardsToDiscard);
                stage.close();
            }
        });
        vbox.getChildren().add(confirm);

        Scene scene = new Scene(vbox, 500, 500);
        stage.setScene(scene);
        stage.showAndWait();
    }

    private static LinkedList<PlayerCard> getChosenPlayerCards(LinkedList<PlayerCard> hand) {
        LinkedList<Integer> indicesToDiscard = new LinkedList();
        for(int x = 0; x< checkBoxes.length; x++) {
            if(checkBoxes[x].isSelected()) {
                indicesToDiscard.add(x);
            }
        }

        LinkedList<PlayerCard> cardsToDiscard = new LinkedList();
        for(Integer index : indicesToDiscard) {
            if(index != null) {
                cardsToDiscard.add(hand.get(index));
            }
        }

        return cardsToDiscard;

    }

    private static boolean isValidDiscard(int numNeeded) {
        int counter = 0;
        for(CheckBox cbox : checkBoxes) {
            if(cbox.isSelected()) {
                counter++;
            }
        }
        if(counter >= numNeeded) {
            return true;
        } else {
            return false;
        }
    }

}
