package Graphics;/*

Name: Ethan Chen
Class: ZZZMoveScreen
Description: JavaFX Graphics for Move

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

public class ZZZMoveScreen {

    static int index = -1;

    // gives a list of the available cities to move to that connect to the player's current city
    public static void display(Game pandemic, Label updateLabel) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        VBox vbox = new VBox();

        Label label = new Label("Choose where you would like to move: ");
        vbox.getChildren().add(label);

        City currCity = pandemic.currPlayer.getCurrCity();
        ToggleGroup toggleGroup = new ToggleGroup();
        for(City c : currCity.getRoadsTo()) {
            RadioButton moveToButton = new RadioButton(c.getName());
            moveToButton.setToggleGroup(toggleGroup);
            vbox.getChildren().add(moveToButton);
        }

        LinkedList<City> researchStationCopy = new LinkedList();

        if(currCity.hasResearchStation()) {
            researchStationCopy = pandemic.researchStations.copy();
            researchStationCopy.remove(pandemic.currCity);
            for (City c : researchStationCopy) {
                if (c != null) {
                    if (c.hasResearchStation() && !c.equals(pandemic.currCity)) {
                        RadioButton moveToButton = new RadioButton(c.getName() + " (via research station)");
                        moveToButton.setToggleGroup(toggleGroup);
                        vbox.getChildren().add(moveToButton);
                    }
                }
            }
        }

        Button submit = new Button("Submit");
        submit.setDisable(true);
        LinkedList<City> finalResearchStationCopy = researchStationCopy;
        submit.setOnAction(e -> {
            index = toggleGroup.getToggles().indexOf(toggleGroup.getSelectedToggle());
            City moveTo;
            if(index < currCity.getRoadsTo().size()) {
                moveTo = currCity.getRoadsTo().get(index);
                pandemic.move(moveTo);
            } else {
                moveTo = finalResearchStationCopy.get(index - currCity.getRoadsTo().size());
                pandemic.move(moveTo);
            }
            updateLabel.setText("You have moved to " + moveTo.getName() + ".");
            stage.close();
        });

        toggleGroup.selectedToggleProperty().addListener( (v, oldValue, newValue) -> {
            submit.setDisable(false);
        });


        vbox.getChildren().add(submit);
        vbox.setSpacing(20);
        vbox.setPadding(new Insets(10, 10, 10, 10));

        Scene scene = new Scene(vbox, 500, 500);
        stage.setScene(scene);
        stage.setTitle("Move");
        stage.showAndWait();

    }
}
