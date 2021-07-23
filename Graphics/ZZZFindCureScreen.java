package Graphics;/*

Name: Ethan Chen
Class: ZZZFindCureScreen
Description: JavaFX Graphics for Find Cure

*/
import Game.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ZZZFindCureScreen {

    // gives the player an option to choose what color disease to cure, and which 5 cards to discard to find the cure
    // note: only works if they are in a city with a research station
    private static LinkedList<CheckBox> checkBoxes = new LinkedList();

    public static void display(Game pandemic, Label updateLabel) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        VBox vbox = new VBox();

        Label label = new Label("What color cure would you like to find? ");
        vbox.getChildren().add(label);

        ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton blue = new RadioButton("Blue");
        if(pandemic.cured[0] == true ) {
            blue.setDisable(true);
        }
        blue.setToggleGroup(toggleGroup);
        RadioButton yellow = new RadioButton("Yellow");
        if(pandemic.cured[1] == true) {
            yellow.setDisable(true);
        }
        yellow.setToggleGroup(toggleGroup);
        RadioButton black = new RadioButton("Black");
        if(pandemic.cured[2] == true) {
            black.setDisable(true);
        }
        black.setToggleGroup(toggleGroup);
        RadioButton red = new RadioButton("Red");
        if(pandemic.cured[3] == true) {
            red.setDisable(true);
        }
        red.setToggleGroup(toggleGroup);

        vbox.getChildren().addAll(blue, yellow, black, red);

        VBox vboxLower = new VBox();
        vboxLower.setSpacing(20);
        vbox.getChildren().add(vboxLower);

        toggleGroup.selectedToggleProperty().addListener( (v, oldValue, newValue) -> {
            vboxLower.getChildren().clear();
            checkBoxes = addCheckBoxes(toggleGroup.getToggles().indexOf(newValue), vboxLower, pandemic);
        });

        Button submit = new Button("Submit");
        submit.setDisable(true);
        submit.setOnAction(e -> {
            if(isValidSelection(pandemic)) {
                if(pandemic.currPlayer.getCurrCity().hasResearchStation()) {
                    LinkedList<PlayerCard> chosenPlayerCards = getChosenPlayerCards(pandemic);
                    pandemic.discoverCure(chosenPlayerCards.get(0).getCardColor());
                    pandemic.discardCards(chosenPlayerCards);
                    updateLabel.setText("You have found the cure for " + getColor(chosenPlayerCards.get(0).getCardColor()) + ".");
                    stage.close();
                }
            }
        });

        toggleGroup.selectedToggleProperty().addListener( (v, oldValue, newValue) -> {
            submit.setDisable(false);
        });


        vbox.getChildren().add(submit);
        vbox.setSpacing(20);
        vbox.setPadding(new Insets(10, 10, 10, 10));

        Scene scene = new Scene(vbox, 500, 500);
        stage.setScene(scene);
        stage.setTitle("Find Cure");
        stage.showAndWait();
    }

    private static LinkedList<PlayerCard> getChosenPlayerCards(Game pandemic) {
        LinkedList<Integer> indicesOfChosenCheckBoxes = new LinkedList();
        LinkedList<PlayerCard> chosenPlayerCards = new LinkedList();

        for(int x = 0; x<checkBoxes.size(); x++) {
            if(checkBoxes.get(x).isSelected()) {
                indicesOfChosenCheckBoxes.add(x);
            }
        }

        for(Integer index : indicesOfChosenCheckBoxes) {
            if(index != null) {
                chosenPlayerCards.add(pandemic.currPlayer.getHand().get(index));
            }
        }
        return chosenPlayerCards;
    }

    private static boolean isValidSelection(Game pandemic) {
        int counter = 0;

        for(CheckBox cbox : checkBoxes) {
            if(cbox.isSelected()) {
                counter++;
            }
        }

        if(counter >= 5 || (pandemic.currPlayer.getOperativeNumber() == 4 && counter >= 4)) {
            return true;
        } else {
            return false;
        }
    }

    private static LinkedList<CheckBox> addCheckBoxes(int indexOf, VBox vbox, Game pandemic) {
        Label label2 = new Label("Which cards would you like to use to create the cure? ");
        vbox.getChildren().add(label2);

        LinkedList<CheckBox> checkBoxLinkedList = new LinkedList();

        for(PlayerCard c : pandemic.currPlayer.getHand()) {
            if(c.getCardColor() == indexOf) {
                CheckBox option = new CheckBox(c.getCardName());
                vbox.getChildren().add(option);
                checkBoxLinkedList.add(option);
            }
        }

        return checkBoxLinkedList;
    }


    private static String getColor(int index) {
        if(index == 0) {
            return "Blue";
        } else if(index == 1) {
            return "Yellow";
        } else if(index == 2) {
            return "Black";
        } else {
            return "Red";
        }
    }

}
