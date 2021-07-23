package Graphics;/*

Name: Ethan Chen
Class: ZZZRuleBookScreen
Description: JavaFX Graphics for Rules

*/
import Game.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ZZZRuleBook {

    // shows rules
    public static void display() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(10);

        Label space1 = new Label("");
        Label space2 = new Label("");
        Label space3 = new Label("");
        Label space4 = new Label("");
        Label space5 = new Label("");
        Label space6 = new Label("");
        Label space7 = new Label("");

        Label rules1 = new Label("Rules: ");
        Label rules2 = new Label("Goal: Eradicate all 4 diseases.");
        Label rules3 = new Label("You can play this game with up to 4 players, and each player plays a turn until " +
                "the game ends.In a player's turn, they have 4 actions, and a player has a set of choices for each " +
                "action.");
        Label rules4 = new Label("Move: move to a city connected to the city you are in.");
        Label rules5 = new Label("Play a card: Event card - play the designated events.            Flight - move to " +
                "the city of the city card you played, unless you are in that city, then move anywhere on the map.");
        Label rules6 = new Label("             Research Station - build a research station in the city you are in, if you " +
                "also play that card.            Treat a disease: remove one disease cube from the city you are in.");
        Label rules7 = new Label("            Trade: Give a card to a player you are in the same city as, if that card matches " +
                "the city.            Find cure: Trade in 5 cards of the same color to find the cure for that color");
        Label rules8 = new Label("At the end of each turn, 2 player cards and 2 or more infection " +
                "cards are pulled. If an epidemic occurs, the bottom card of the infection deck is triple infected " +
                "and the infection discard is reshuffled on top of the deck.");
        Label rules9 = new Label("Events: One Quiet Night - Don't pull infection cards for this turn.           " +
                " Airlift - Move any player to any city");
        Label rules10 = new Label("            Forecast - Reorder the top 5 cards on the infection deck          " +
                "  Government Grant - Build a research station in any city");
        Label rules11 = new Label("            Resilient Population - Remove any one card from the infection " +
                "discard and out of the game");
        Label rules12 = new Label("Roles: Medic - Remove all disease cubes when you treat, or if the disease is " +
                "cured, remove all disease cubes upon entry of a city");
        Label rules13 = new Label("            Operations Expert - Build a research station in the current city " +
                "without discarding a card, or move from a research station to any city by discarding any city card.");
        Label rules14 = new Label("            Researcher - Give any city card from your hand to another player in " +
                "the same city without the card matching the city.");
        Label rules15 = new Label("            Scientist - You only need 4 cards of the same disease color to " +
                "Discover a Cure instead of 5.");
        Label rules16 = new Label("            Quarantine Specialist - All disease cube placements and outbreaks " +
                "are prevented in your current city and all connecting cities.");
        Label rules17 = new Label("You can have a maximum of 7 cards in your hand at any time, and once you reach " +
                "that limit you must discard down to 7. This will occur at the end of your turn.");
        Label rules18 = new Label("The game ends in a loss if 9 outbreaks have occurred, if the number of disease " +
                "cubes on the board for one of the diseases exceeds 24, or if the player deck runs out.");
        Label rules19 = new Label("The game ends in a win if all 4 diseases are eradicated.");
        Label rules20 = new Label("If you are still confused, look up the rules online. Good luck!");

        Button close = new Button("Close");
        close.setOnAction(e -> stage.close());

        vbox.getChildren().addAll(rules1, rules2, space1, rules3, space2, rules4, rules5, rules6,
                rules7, space3, rules8, space4, rules9, rules10, rules11, space5, rules12, rules13, rules14, rules15,
                rules16, space6, rules17, space7, rules18, rules19, rules20, close);

        Scene scene = new Scene(vbox, 1500, 1000);
        stage.setScene(scene);
        stage.showAndWait();
    }

}
