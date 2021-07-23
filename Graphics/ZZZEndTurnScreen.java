package Graphics;/*

Name: Ethan Chen
Class: ZZZEndTurnScreen
Description: JavaFX Graphics for End of a Player's Turn

*/
import Game.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ZZZEndTurnScreen {

    private static boolean epidemicOccurred = false;
    private static boolean outbreakOccurred = false;

    // displays pulling of PlayerCards, InfectionCards, and moves to discardCards screen if necessary
    public static void display(Game pandemic, Label updateLabel) {

        epidemicOccurred = false;
        outbreakOccurred = false;

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(20);

        Label pCardsLabel = new Label("Player Cards Pulled: ");
        Label iCardsLabel = new Label("Infection Cards Pulled: ");

        Button confirm = new Button("Confirm");
        confirm.setDisable(true);
        confirm.setOnAction(e -> {
            if (pandemic.currPlayer.getHand().size() > 7) {
                ZZZDiscardCardsScreen.display(pandemic);
            }
            if (!pandemic.outbrokenCities.isEmpty()) {
                outbreakOccurred = true;
            }
            if (epidemicOccurred && outbreakOccurred) {
                String labelString = "An epidemic occurred in " + pandemic.lastEpidemicLocation.getName() + " and outbreaks " +
                        "occurred in: ";
                for (City c : pandemic.outbrokenCities) {
                    if (c != null) {
                        if (c.equals(pandemic.outbrokenCities.get(pandemic.outbrokenCities.size() - 1))) {
                            labelString += (c.getName());
                        } else {
                            labelString += (c.getName() + ", ");
                        }
                    }
                }
                updateLabel.setText(labelString);
            } else if (epidemicOccurred) {
                updateLabel.setText("An epidemic occurred in " + pandemic.lastEpidemicLocation.getName());
            } else if (outbreakOccurred) {
                String labelString = "Outbreaks occurred in: ";
                for (City c : pandemic.outbrokenCities) {
                    if (c != null) {
                        if (c.equals(pandemic.outbrokenCities.get(pandemic.outbrokenCities.size() - 1))) {
                            labelString += (c.getName());
                        } else {
                            labelString += (c.getName() + ", ");
                        }
                    }
                }
                updateLabel.setText(labelString);
            } else {
                updateLabel.setText("Nothing bad occurred.");
            }
            stage.close();
        });

        Button pCardsButton, iCardsButton;
        pCardsButton = new Button("Pull Player Cards");
        iCardsButton = new Button("Pull Infection Cards");
        iCardsButton.setDisable(true);
        HBox pCardsHBox = new HBox();
        pCardsHBox.setSpacing(10);
        pCardsHBox.getChildren().add(new Label());
        HBox iCardsHBox = new HBox();
        iCardsHBox.setSpacing(10);
        iCardsHBox.getChildren().add(new Label());

        pCardsButton.setOnAction(e -> {
            pCardsButton.setDisable(true);
            iCardsButton.setDisable(false);
            LinkedList<PlayerCard> pulledPlayerCards = pandemic.drawPlayerCards();
            pCardsHBox.getChildren().clear();
            for (PlayerCard c : pulledPlayerCards) {
                Label cardLabel = new Label(c.getCardName());
                if (c.getCardColor() == 0) {
                    cardLabel.setTextFill(Color.BLUE);
                    cardLabel.setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, new CornerRadii(4), new BorderWidths(2))));
                } else if (c.getCardColor() == 1) {
                    cardLabel.setBorder(new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID, new CornerRadii(4), new BorderWidths(2))));
                } else if (c.getCardColor() == 2) {
                    cardLabel.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(4), new BorderWidths(2))));
                } else if (c.getCardColor() == 3) {
                    cardLabel.setTextFill(Color.RED);
                    cardLabel.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(4), new BorderWidths(2))));
                } else if (c.getCardColor() == 4) {
                    cardLabel.setTextFill(Color.GREEN);
                    cardLabel.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, new CornerRadii(4), new BorderWidths(2))));
                }
                cardLabel.setFont(Font.font(15));
                pCardsHBox.getChildren().add(cardLabel);

                if (c.getCardType() == 2) {
                    epidemicOccurred = true;
                }

            }
        });


        iCardsButton.setOnAction(e -> {
            iCardsButton.setDisable(true);
            confirm.setDisable(false);
            LinkedList<InfectionCard> pulledInfectionCards = pandemic.drawInfectionCards();
            iCardsHBox.getChildren().clear();
            for (InfectionCard c : pulledInfectionCards) {
                if (c != null) {
                    Label cardLabel = new Label(c.getInfectionCity().getName());
                    if (c.getInfectionCity().getDiseaseColor() == 0) {
                        cardLabel.setTextFill(Color.BLUE);
                        cardLabel.setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, new CornerRadii(4), new BorderWidths(2))));
                    } else if (c.getInfectionCity().getDiseaseColor() == 1) {
                        cardLabel.setBorder(new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID, new CornerRadii(4), new BorderWidths(2))));
                    } else if (c.getInfectionCity().getDiseaseColor() == 2) {
                        cardLabel.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(4), new BorderWidths(2))));
                    } else if (c.getInfectionCity().getDiseaseColor() == 3) {
                        cardLabel.setTextFill(Color.RED);
                        cardLabel.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(4), new BorderWidths(2))));
                    } else if (c.getInfectionCity().getDiseaseColor() == 4) {
                        cardLabel.setTextFill(Color.GREEN);
                        cardLabel.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, new CornerRadii(4), new BorderWidths(2))));
                    }
                    cardLabel.setFont(Font.font(15));
                    iCardsHBox.getChildren().add(cardLabel);
                }
            }
        });


        vbox.getChildren().addAll(pCardsLabel, pCardsButton, pCardsHBox, iCardsLabel, iCardsButton, iCardsHBox, confirm);

        Scene scene = new Scene(vbox, 500, 500);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.showAndWait();
    }

}
