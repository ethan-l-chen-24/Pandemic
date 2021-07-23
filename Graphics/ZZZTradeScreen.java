package Graphics;/*

Name: Ethan Chen
Class: ZZZTradeScreen
Description: JavaFX Graphics for Trade

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

public class ZZZTradeScreen {

    // gives option for who to trade with and then what cards are available to trade
    public static void display(Game pandemic, Label updateLabel) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        VBox vbox = new VBox();

        Label label = new Label("Who would you like to trade with? ");
        vbox.getChildren().add(label);

        City currCity = pandemic.currPlayer.getCurrCity();
        ToggleGroup toggleGroup = new ToggleGroup();
        LinkedList<Player> playersAvailableToTrade = new LinkedList();
        for (Player p : pandemic.players) {
            if (p != null && !p.equals(pandemic.currPlayer) && p.getCurrCity().equals(pandemic.currPlayer.getCurrCity())) {
                RadioButton tradeWith = new RadioButton(p.getName());
                tradeWith.setToggleGroup(toggleGroup);
                vbox.getChildren().add(tradeWith);
                playersAvailableToTrade.add(p);
            }
        }

        Label label2 = new Label("Tradeable Cards: ");
        vbox.getChildren().add(label2);

        ToggleGroup toggleGroup2 = new ToggleGroup();
        LinkedList<PlayerCard> cards = new LinkedList();
        for (PlayerCard c : pandemic.currPlayer.getHand()) {
            if (c != null) {
                if (c.getCardName().equals(currCity) || pandemic.currPlayer.getOperativeNumber() == 3) {
                    cards.add(c);
                    RadioButton tradeCard = new RadioButton(c.getCardName());
                    tradeCard.setToggleGroup(toggleGroup2);
                    vbox.getChildren().add(tradeCard);
                }
            }

        }

        Button submit = new Button("Submit");
        submit.setDisable(true);
        submit.setOnAction(e -> {
            int indexPlayer = toggleGroup.getToggles().indexOf(toggleGroup.getSelectedToggle());
            int indexCard = toggleGroup2.getToggles().indexOf(toggleGroup2.getSelectedToggle());
            Player playerToTradeWith = playersAvailableToTrade.get(indexPlayer);
            PlayerCard cardToTrade = cards.get(indexCard);
            pandemic.tradeCards(playerToTradeWith, cardToTrade);
            updateLabel.setText(pandemic.currPlayer.getName() + " has traded " + cardToTrade.getCardName() + " to " + playerToTradeWith.getName() + ".");
            stage.close();
        });

        toggleGroup2.selectedToggleProperty().addListener((v, oldValue, newValue) -> {
            submit.setDisable(false);
        });


        vbox.getChildren().add(submit);
        vbox.setSpacing(20);
        vbox.setPadding(new Insets(10, 10, 10, 10));

        Scene scene = new Scene(vbox, 500, 500);
        stage.setScene(scene);
        stage.setTitle("Trade");
        stage.showAndWait();
    }

}
