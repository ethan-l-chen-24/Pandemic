package Graphics;/*

Name: Ethan Chen
Class: ZZZAGovernmentGrantScreen
Description: JavaFX Graphics for Government Grant Event

*/
import Game.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ZZZGovernmentGrantScreen {

    // gives a list of cities that the player can choose to put a research station in
    public static void display(Game pandemic, PlayerCard charterCard, Label updateLabel) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        VBox vbox = new VBox();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(vbox);

        Label label = new Label("Where would you like to build a research station? ");
        vbox.getChildren().add(label);

        Button submit = new Button("Submit");

        ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.selectedToggleProperty().addListener((v, oldValue, newValue) -> submit.setDisable(false));
        LinkedList<City> citiesWithoutResearchStations = new LinkedList();
        for (City c : pandemic.cities) {
            if(!c.hasResearchStation()) {
                RadioButton city = new RadioButton(c.getName());
                city.setToggleGroup(toggleGroup);
                vbox.getChildren().add(city);
                citiesWithoutResearchStations.add(c);
            }
        }

        submit.setDisable(true);
        submit.setOnAction(e -> {
            int index = toggleGroup.getToggles().indexOf(toggleGroup.getSelectedToggle());
            City chosenCity = citiesWithoutResearchStations.get(index);
            pandemic.researchStation(chosenCity);
            pandemic.actionsRemaining += 1;
            if(pandemic.currPlayer.getOperativeNumber() != 5 || charterCard.getHasBeenPlayed()) {
                pandemic.currPlayer.discardFromHand(charterCard);
            } else {
                charterCard.playCard();
            }
            updateLabel.setText("You have built a research station in " + chosenCity.getName() + ".");
            stage.close();
        });

        vbox.getChildren().add(submit);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(20);

        Scene scene = new Scene(scrollPane, 500, 500);
        stage.setScene(scene);
        stage.setTitle("Government Grant");
        stage.showAndWait();
    }
}
