package Graphics;/*

Name: Ethan Chen
Class: ZZZExpandMapScreen
Description: JavaFX Graphics for Expanding the Map

*/
import Game.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ZZZExpandMapScreen {

    // expands the map to a more descriptive screen
    public static void display(Game pandemic) throws FileNotFoundException {
        Image world = new Image(new FileInputStream("src/Images/World copy.jpg"));
        ImageView worldView = new ImageView(world);
        worldView.setFitHeight(750);
        worldView.setFitWidth(1500);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Group g = new Group();
        g.getChildren().add(worldView);

        for (City[] connections : Runner.connections) {
            Line connectionLine1 = new Line();
            connectionLine1.setFill(Color.TAN);
            if (Math.abs(connections[0].getxCoord() - connections[1].getxCoord()) > 300) { // lines between cities
                Line connectionLine2 = new Line();
                connectionLine2.setFill(Color.TAN);
                if (connections[0].getxCoord() > connections[1].getxCoord()) {
                    connectionLine1.setStartX(connections[0].getxCoord()*1500/650);
                    connectionLine1.setStartY(connections[0].getyCoord()*1500/650);
                    connectionLine1.setEndX(1500);
                    connectionLine1.setEndY((connections[0].getyCoord()*1500/650 + (connections[1].getyCoord())*1500/650) / 2);
                    connectionLine2 = new Line();
                    connectionLine2.setStartX(0);
                    connectionLine2.setStartY((connections[0].getyCoord()*1500/650 + (connections[1].getyCoord())*1500/650) / 2);
                    connectionLine2.setEndX(connections[1].getxCoord()*1500/650);
                    connectionLine2.setEndY(connections[1].getyCoord()*1500/650);
                } else {
                    connectionLine1.setStartX(connections[1].getxCoord()*1500/650);
                    connectionLine1.setStartY(connections[1].getyCoord()*1500/650);
                    connectionLine1.setEndX(1500);
                    connectionLine1.setEndY((connections[1].getyCoord()*1500/650 + (connections[0].getyCoord())*1500/650) / 2);
                    connectionLine2 = new Line();
                    connectionLine2.setStartX(0);
                    connectionLine2.setStartY((connections[1].getyCoord()*1500/650 + (connections[0].getyCoord())*1500/650) / 2);
                    connectionLine2.setEndX(connections[0].getxCoord()*1500/650);
                    connectionLine2.setEndY(connections[0].getyCoord()*1500/650);
                }
                g.getChildren().addAll(connectionLine1, connectionLine2);
            } else {
                connectionLine1.setStartX(connections[0].getxCoord()*1500/650);
                connectionLine1.setStartY(connections[0].getyCoord()*1500/650);
                connectionLine1.setEndX(connections[1].getxCoord()*1500/650);
                connectionLine1.setEndY(connections[1].getyCoord()*1500/650);
                g.getChildren().add(connectionLine1);
            }
        }

        for (City c : pandemic.cities) {
            Circle dot = new Circle();
            dot.setCenterX(c.getxCoord() * 1500 / 650);
            dot.setCenterY(c.getyCoord() * 1500 / 650);
            dot.setRadius(10);
            if (c.getDiseaseColor() == 0) {
                dot.setFill(Color.BLUE);
            } else if (c.getDiseaseColor() == 1) {
                dot.setFill(Color.YELLOW);
            } else if (c.getDiseaseColor() == 2) {
                dot.setFill(Color.BLACK);
            } else {
                dot.setFill(Color.RED);
            }
            Text t = new Text(c.getName());
            t.setX(c.getxCoord() * 1500 / 650 - 10);
            t.setY(c.getyCoord() * 1500 / 650 + 20);
            t.setFont(Font.font(12));
            t.setFill(Color.MAROON);

            g.getChildren().addAll(dot, t);

            if(c.hasResearchStation()) {
                Circle innerDot = new Circle();
                innerDot.setCenterX(c.getxCoord() * 1500 / 650);
                innerDot.setCenterY(c.getyCoord() * 1500 / 650);
                innerDot.setRadius(7);
                innerDot.setFill(Color.TAN);
                g.getChildren().add(innerDot);
            }

            if (c.getNumDiseaseCubes() != 0) {
                Text numCubes = new Text(String.valueOf(c.getNumDiseaseCubes()));
                numCubes.setX(c.getxCoord() * 1500 / 650 - 5);
                numCubes.setY(c.getyCoord() * 1500 / 650 + 5);
                numCubes.setFont(Font.font(15));
                if(c.getDiseaseColor() != 1) {
                    numCubes.setFill(Color.WHITE);
                }
                g.getChildren().add(numCubes);
            }

        }

        Queue<Player> playersCopy = pandemic.players.copy();
        playersCopy.enqueue(pandemic.currPlayer);
        Player p1 = playersCopy.dequeue();

        while(p1.getPlayerNumber() != 0) {
            playersCopy.enqueue(p1);
            p1 = playersCopy.dequeue();
        }

        int counter = 0;
        Rectangle playerMarkerp1 = new Rectangle();
        playerMarkerp1.setHeight(12);
        playerMarkerp1.setWidth(12);
        playerMarkerp1.setX(p1.getCurrCity().getxCoord() * 1500 / 650 - 25);
        playerMarkerp1.setY(p1.getCurrCity().getyCoord() * 1500 / 650 - 5);
        playerMarkerp1.setFill(Color.DARKRED);
        g.getChildren().add(playerMarkerp1);
        counter++;

        for (Player p : playersCopy) {

            if (p != null) {

                Rectangle playerMarker = new Rectangle();
                playerMarker.setHeight(12);
                playerMarker.setWidth(12);

                if (counter == 1) {
                    playerMarker.setX(p.getCurrCity().getxCoord() * 1500 / 650 - 13);
                    playerMarker.setY(p.getCurrCity().getyCoord() * 1500 / 650 - 22);
                    playerMarker.setFill(Color.ORANGE);
                    counter++;
                } else if (counter == 2) {
                    playerMarker.setX(p.getCurrCity().getxCoord() * 1500 / 650 + 2);
                    playerMarker.setY(p.getCurrCity().getyCoord() * 1500 / 650 - 22);
                    playerMarker.setFill(Color.PURPLE);
                    counter++;
                } else if (counter == 3) {
                    playerMarker.setX(p.getCurrCity().getxCoord() * 1500 / 650 + 13);
                    playerMarker.setY(p.getCurrCity().getyCoord() * 1500 / 650 - 5);
                    playerMarker.setFill(Color.GREEN);
                    counter++;
                }
                g.getChildren().add(playerMarker);

            }

            Button close = new Button("Close");
            close.setLayoutX(1350);
            close.setLayoutY(700);
            close.setOnAction(e -> {
                stage.close();
            });

            g.getChildren().add(close);
        }


        Scene scene = new Scene(g, 1500, 750);
        stage.setScene(scene);
        stage.showAndWait();
    }


}
