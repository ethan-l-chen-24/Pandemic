package Graphics;/*

Name: Ethan Chen
Class: ZZZZPandemicGraphics
Description: The runnable, cumulative view and controller for the graphics

*/
import Game.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class ZZZZPandemicGraphics extends Application {

    // JAVA GRAPHICS OBJECTS

    // Stage
    Stage window;

    // Main Scene
    Scene scene;

    //Border Layout
    BorderPane layout;

    // Map
    Image world = new Image(new FileInputStream("src/Images/World copy.jpg"));
    ImageView worldView = new ImageView(world);

    //Main HBoxes
    HBox bottomHBox;

    // Labels
    Label title;
    Label currPlayerLabel;
    Label movesLeft;
    Label blue, yellow, black, red;
    Label infectionCardNumLabel;
    Label numOutbreaksLabel;
    Label updateLabel;

    // Buttons
    Button move;
    Button playCard;
    Button trade;
    Button findCure;
    Button endTurn;
    Button treat;
    Button researchStation;

    // Rectangles
    Rectangle playerMarkerp1, playerMarkerp2, playerMarkerp3, playerMarkerp4;

    // Central area
    Group g;

    // Controller Vars
    Game pandemic;
    City[] cities;
    Queue<Player> playerQueue;
    int numPlayers;

    // HBoxes that need to be changed
    HBox p1hbox1, p1hbox2, p2hbox1, p2hbox2, p3hbox1, p3hbox2, p4hbox1, p4hbox2;

    public ZZZZPandemicGraphics() throws FileNotFoundException {
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception { // main runner for the graphics
        ZZZTitleScreen.display(); // shows the title screen
        cities = Runner.loadCardsFromTxt();
        ZZZSettingsScreen.display(cities); // shows the settings screen
        playerQueue = ZZZSettingsScreen.playerQueue;
        pandemic = new Game(playerQueue, cities, ZZZSettingsScreen.difficulty); // creates the game

        for (Player p : playerQueue) { // finds the number of players
            if (p != null) {
                numPlayers++;
            }
        }
        numPlayers += 1;

        window = primaryStage;
        layout = new BorderPane();
        //layout.setStyle("-fx-background-color: rgba(47, 130, 245, 1);"); // adds blue background, but it is more clear
                                                                           // without it so I have commented it out

        center(); // creates the center, top, left, right, and bottom of the borderLayout/borderPane
        top();
        left();
        right();
        bottom();

        scene = new Scene(layout, 1500, 1000);
        window.setScene(scene);
        window.show();
    }

    private void center() { // creates the center, which is the map
        worldView.setFitWidth(850);
        worldView.setFitHeight(425);
        worldView.setX(0);
        worldView.setY(0);

        g = new Group();
        g.getChildren().addAll(worldView);

        for (City[] connections : Runner.connections) {
            Line connectionLine1 = new Line();
            if (Math.abs(connections[0].getxCoord() - connections[1].getxCoord()) > 300) { // lines between cities
                Line connectionLine2 = new Line();
                if (connections[0].getxCoord() > connections[1].getxCoord()) {
                    connectionLine1.setStartX(connections[0].getxCoord()*850/650);
                    connectionLine1.setStartY(connections[0].getyCoord()*850/650);
                    connectionLine1.setEndX(850);
                    connectionLine1.setEndY((connections[0].getyCoord()*850/650 + (connections[1].getyCoord())*850/650) / 2);
                    connectionLine2 = new Line();
                    connectionLine2.setStartX(0);
                    connectionLine2.setStartY((connections[0].getyCoord()*850/650 + (connections[1].getyCoord())*850/650) / 2);
                    connectionLine2.setEndX(connections[1].getxCoord()*850/650);
                    connectionLine2.setEndY(connections[1].getyCoord()*850/650);
                } else {
                    connectionLine1.setStartX(connections[1].getxCoord()*850/650);
                    connectionLine1.setStartY(connections[1].getyCoord()*850/650);
                    connectionLine1.setEndX(850);
                    connectionLine1.setEndY((connections[1].getyCoord()*850/650 + (connections[0].getyCoord())*800/650) / 2);
                    connectionLine2 = new Line();
                    connectionLine2.setStartX(0);
                    connectionLine2.setStartY((connections[1].getyCoord()*850/650 + (connections[0].getyCoord())*850/650) / 2);
                    connectionLine2.setEndX(connections[0].getxCoord()*850/650);
                    connectionLine2.setEndY(connections[0].getyCoord()*850/650);
                }
                g.getChildren().addAll(connectionLine1, connectionLine2);
            } else {
                connectionLine1.setStartX(connections[0].getxCoord()*850/650);
                connectionLine1.setStartY(connections[0].getyCoord()*850/650);
                connectionLine1.setEndX(connections[1].getxCoord()*850/650);
                connectionLine1.setEndY(connections[1].getyCoord()*850/650);
                g.getChildren().add(connectionLine1);
            }
        }

        for (City c : cities) { // dots for each city in the color of its disease color
            Circle dot = new Circle();
            dot.setCenterX(c.getxCoord()*850/650);
            dot.setCenterY(c.getyCoord()*850/650);
            dot.setRadius(3);
            if (c.getDiseaseColor() == 0) {
                dot.setFill(Color.BLUE);
            } else if (c.getDiseaseColor() == 1) {
                dot.setFill(Color.YELLOW);
            } else if (c.getDiseaseColor() == 2) {
                dot.setFill(Color.BLACK);
            } else {
                dot.setFill(Color.RED);
            }

            g.getChildren().addAll(dot);
        }

        int counter = 0; // adds all of the player markers
        playerMarkerp1 = new Rectangle();
        playerMarkerp1.setHeight(5);
        playerMarkerp1.setWidth(5);
        playerMarkerp1.setX(pandemic.currPlayer.getCurrCity().getxCoord()*850/650 - 10);
        playerMarkerp1.setY(pandemic.currPlayer.getCurrCity().getyCoord()*850/650 - 2);
        playerMarkerp1.setFill(Color.DARKRED);
        g.getChildren().add(playerMarkerp1);
        counter++;

        for (Player p : pandemic.players) {

            if (p != null) {

                if (counter == 1) {
                    playerMarkerp2 = new Rectangle();
                    playerMarkerp2.setX(p.getCurrCity().getxCoord()*850/650 - 7);
                    playerMarkerp2.setY(p.getCurrCity().getyCoord()*850/650 - 8);
                    playerMarkerp2.setHeight(5);
                    playerMarkerp2.setWidth(5);
                    playerMarkerp2.setFill(Color.ORANGE);
                    g.getChildren().add(playerMarkerp2);
                    counter++;
                } else if (counter == 2) {
                    playerMarkerp3 = new Rectangle();
                    playerMarkerp3.setX(p.getCurrCity().getxCoord()*850/650 + 2);
                    playerMarkerp3.setY(p.getCurrCity().getyCoord()*850/650 - 8);
                    playerMarkerp3.setHeight(5);
                    playerMarkerp3.setWidth(5);
                    playerMarkerp3.setFill(Color.PURPLE);
                    g.getChildren().add(playerMarkerp3);
                    counter++;
                } else if (counter == 3) {
                    playerMarkerp4 = new Rectangle();
                    playerMarkerp4.setX(p.getCurrCity().getxCoord()*850/650 + 5);
                    playerMarkerp4.setY(p.getCurrCity().getyCoord()*850/650 - 2);
                    playerMarkerp4.setHeight(5);
                    playerMarkerp4.setWidth(5);
                    playerMarkerp4.setFill(Color.GREEN);
                    g.getChildren().add(playerMarkerp4);
                    counter++;
                }


            }

        }

        layout.setCenter(g);

    }

    private void top() { // top of the layout, which is the name, actions remaining, update, and expandMap button
       HBox topHBox = new HBox();
        title = new Label("Pandemic");
        currPlayerLabel = new Label(pandemic.currPlayer.getName() + "'s Turn");
        movesLeft = new Label("Actions Remaining: " + pandemic.actionsRemaining);
        Button expandMap = new Button("Expand Map");
        expandMap.setOnAction(e -> {
            try {
                ZZZExpandMapScreen.display(pandemic);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        updateLabel = new Label("Welcome to Pandemic!");

        Button rules = new Button("Rules");
        rules.setOnAction(e -> ZZZRuleBook.display());

        topHBox.getChildren().addAll(title, rules);
        topHBox.setSpacing(10);
        topHBox.setAlignment(Pos.CENTER);

        VBox vbox = new VBox();
        vbox.setSpacing(20);
        vbox.setPrefHeight(30);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(topHBox, currPlayerLabel, movesLeft, expandMap, updateLabel); // create these objects
                                                                                              // and put them in a vbox

        layout.setTop(vbox);
    }

    private void left() { // Left contains the players and each of their playerCards
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setPrefWidth(300);
        vbox.setAlignment(Pos.CENTER);

        // two hboxes for each player that contain their cards, put 3 in the first one, 4 in the second one
        // do this for all players

        Player p1 = pandemic.currPlayer;
        Label p1Label = new Label(p1.getName() + ": " + getOperativeName(p1));
        p1Label.setTextFill(Color.DARKRED);
        p1hbox1 = new HBox();
        p1hbox1.setAlignment(Pos.CENTER);
        p1hbox1.setSpacing(3);
        p1hbox2 = new HBox();
        p1hbox2.setSpacing(3);
        p1hbox2.setAlignment(Pos.CENTER);
        p1hbox1.setPrefWidth(100);
        p1hbox2.setPrefWidth(100);

        for (int x = 0; x < 4; x++) {
            if (x < p1.getHand().size()) {
                PlayerCard c = p1.getHand().get(x);
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
                cardLabel.setFont(Font.font(10));
                p1hbox1.getChildren().add(cardLabel);
            }
        }

        for (int x = 4; x < p1.getHand().size(); x++) {
            PlayerCard c = p1.getHand().get(x);
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
            cardLabel.setFont(Font.font(10));
            p1hbox2.getChildren().add(cardLabel);
        }

        Separator s1 = new Separator();
        s1.setMinHeight(30);

        vbox.getChildren().addAll(p1Label, p1hbox1, p1hbox2, s1);

        if (numPlayers > 1) {
            Player p2 = playerQueue.dequeue();
            playerQueue.enqueue(p2);
            Label p2Label = new Label(p2.getName() + ": " + getOperativeName(p2));
            p2Label.setTextFill(Color.ORANGE);
            p2hbox1 = new HBox();
            p2hbox1.setAlignment(Pos.CENTER);
            p2hbox1.setSpacing(3);
            p2hbox2 = new HBox();
            p2hbox2.setSpacing(3);
            p2hbox2.setAlignment(Pos.CENTER);
            p2hbox1.setPrefWidth(100);
            p2hbox2.setPrefWidth(100);

            for (int x = 0; x < 4; x++) {
                if (x < p2.getHand().size()) {
                    PlayerCard c = p2.getHand().get(x);
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
                    cardLabel.setFont(Font.font(10));
                    p2hbox1.getChildren().add(cardLabel);
                }
            }

            for (int x = 4; x < p2.getHand().size(); x++) {
                PlayerCard c = p2.getHand().get(x);
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
                cardLabel.setFont(Font.font(10));
                p2hbox2.getChildren().add(cardLabel);
            }

            Separator s2 = new Separator();
            s2.setMinHeight(30);

            vbox.getChildren().addAll(p2Label, p2hbox1, p2hbox2, s2);
        }

        if (numPlayers > 2) {
            Player p3 = playerQueue.dequeue();
            playerQueue.enqueue(p3);
            Label p3Label = new Label(p3.getName() + ": " + getOperativeName(p3));
            p3Label.setTextFill(Color.PURPLE);
            p3hbox1 = new HBox();
            p3hbox1.setAlignment(Pos.CENTER);
            p3hbox1.setSpacing(3);
            p3hbox2 = new HBox();
            p3hbox2.setSpacing(3);
            p3hbox2.setAlignment(Pos.CENTER);
            p3hbox1.setPrefWidth(100);
            p3hbox2.setPrefWidth(100);

            for (int x = 0; x < 4; x++) {
                if (x < p3.getHand().size()) {
                    PlayerCard c = p3.getHand().get(x);
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
                    cardLabel.setFont(Font.font(10));
                    p3hbox1.getChildren().add(cardLabel);
                }
            }

            for (int x = 4; x < p3.getHand().size(); x++) {
                PlayerCard c = p3.getHand().get(x);
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
                cardLabel.setFont(Font.font(10));
                p3hbox2.getChildren().add(cardLabel);
            }

            Separator s3 = new Separator();
            s3.setMinHeight(30);

            vbox.getChildren().addAll(p3Label, p3hbox1, p3hbox2, s3);
        }

        if (numPlayers > 3) {
            Player p4 = playerQueue.dequeue();
            playerQueue.enqueue(p4);
            Label p4Label = new Label(p4.getName() + ": " + getOperativeName(p4));
            p4Label.setTextFill(Color.GREEN);
            p4hbox1 = new HBox();
            p4hbox1.setAlignment(Pos.CENTER);
            p4hbox1.setSpacing(3);
            p4hbox2 = new HBox();
            p4hbox2.setSpacing(3);
            p4hbox2.setAlignment(Pos.CENTER);
            p4hbox1.setPrefWidth(100);
            p4hbox2.setPrefWidth(100);

            for (int x = 0; x < 4; x++) {
                if (x < p4.getHand().size()) {
                    PlayerCard c = p4.getHand().get(x);
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
                    cardLabel.setFont(Font.font(10));
                    p4hbox1.getChildren().add(cardLabel);
                }
            }

            for (int x = 4; x < p4.getHand().size(); x++) {
                PlayerCard c = p4.getHand().get(x);
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
                cardLabel.setFont(Font.font(10));
                p4hbox2.getChildren().add(cardLabel);
            }

            vbox.getChildren().addAll(p4Label, p4hbox1, p4hbox2);
        }

        vbox.setSpacing(10);
        layout.setLeft(vbox);

    }

    private void right() { // right contains labels that represent the state of the game e.g. number of cubes, number
                           // of outbreaks, etc.
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(15);
        vbox.setPrefWidth(250);

        // create these and put them all in a VBox

        infectionCardNumLabel = new Label("Player Cards Left: " + pandemic.playerDeck.size());
        numOutbreaksLabel = new Label("Number of Outbreaks Until Loss: " + (9 - pandemic.outbreakCounter));
        Separator s1 = new Separator();
        s1.setMinHeight(30);
        Separator s2 = new Separator();
        s2.setMinHeight(30);
        Label totalCubes = new Label("Total Cubes: ");
        blue = new Label("Blue: " + pandemic.totalCubes[0] + " " + diseaseStatus(0));
        yellow = new Label("Yellow: " + pandemic.totalCubes[1] + " " + diseaseStatus(1));
        black = new Label("Black: " + pandemic.totalCubes[2] + " " + diseaseStatus(2));
        red = new Label("Red: " + pandemic.totalCubes[3] + " " + diseaseStatus(3));
        vbox.getChildren().addAll(infectionCardNumLabel, s1, numOutbreaksLabel, s2, totalCubes, blue, yellow, black, red);
        layout.setRight(vbox);
    }

    private String diseaseStatus(int i) { // when called returns the string state of the disease given by its color
        if (pandemic.eradicated[i]) {
            return "(Eradicated)";
        }
        if (pandemic.cured[i]) {
            return "(Cured)";
        }
        return "";
    }

    private void bottom() { // ALL OF THE BUTTONS
        bottomHBox = new HBox();
        bottomHBox.setPrefHeight(100);
        bottomHBox.setPadding(new Insets(10, 10, 10, 10));
        move = new Button("Move");
        move.setOnAction(e -> { // sends to move screen
            ZZZMoveScreen.display(pandemic, updateLabel);
            movePiece(pandemic.currPlayer.getPlayerNumber(), pandemic.currPlayer);
            movesLeft.setText("Actions Remaining: " + pandemic.actionsRemaining);
            resetNumCubeLabels();
            if (turnEnd()) {
                endTurn();
            }
        });
        move.setPrefHeight(100);
        move.setPrefWidth(200);

        treat = new Button("Treat");
        treat.setOnAction(e -> { // treats the current city
            if (pandemic.currCity.getNumDiseaseCubes() > 0) {
                pandemic.treat();
                movesLeft.setText("Actions Remaining: " + pandemic.actionsRemaining); // update graphics
                resetNumCubeLabels();
                updateLabel.setText("You have treated " + pandemic.currCity.getName() + ".");
            }
            if (turnEnd()) {
                endTurn();
            }
        });
        treat.setPrefHeight(100);
        treat.setPrefWidth(200);

        playCard = new Button("Play A Card");
        playCard.setOnAction(e -> { // sends to the play card screen
            ZZZPlayCardScreen.display(pandemic, updateLabel);
            movesLeft.setText("Actions Remaining: " + pandemic.actionsRemaining); // update graphics
            movePiece(pandemic.currPlayer.getPlayerNumber(), pandemic.currPlayer);
            for(Player p : pandemic.players) {
                if(p != null) {
                    movePiece(p.getPlayerNumber(), p);
                }
            }
            resetPlayerHand(pandemic.currPlayer);
            if (turnEnd()) {
                endTurn();
            }
        });
        playCard.setPrefHeight(100);
        playCard.setPrefWidth(200);

        trade = new Button("Trade");
        trade.setOnAction(e -> { // sends to the trade screen
            ZZZTradeScreen.display(pandemic, updateLabel);
            movesLeft.setText("Actions Remaining: " + pandemic.actionsRemaining); // update graphics
            resetPlayerHand(pandemic.currPlayer);
            for(Player p : playerQueue) {
                resetPlayerHand(p);
            }
            if (turnEnd()) {
                endTurn();
            }
        });
        if(numPlayers == 1) {
            trade.setDisable(true);
        }
        trade.setPrefHeight(100);
        trade.setPrefWidth(200);

        findCure = new Button("Find Cure");
        findCure.setOnAction(e -> { // sends to find cure screen
            ZZZFindCureScreen.display(pandemic, updateLabel);
            resetNumCubeLabels();
            movesLeft.setText("Actions Remaining: " + pandemic.actionsRemaining); // update graphics
            resetPlayerHand(pandemic.currPlayer);
            if (turnEnd()) {
                endTurn();
            }
        });
        findCure.setPrefHeight(100);
        findCure.setPrefWidth(200);

        endTurn = new Button("End Turn");
        endTurn.setOnAction(e -> { // sends to end turn screen
            endTurn();
            movesLeft.setText("Actions Remaining: " + pandemic.actionsRemaining); // update graphics
        });
        endTurn.setPrefHeight(100);
        endTurn.setPrefWidth(200);

        if(pandemic.currPlayer.getOperativeNumber() == 1) {
            researchStation = new Button("Research Station");
            researchStation.setPrefHeight(100);
            researchStation.setPrefWidth(200);
            researchStation.setOnAction(e -> { // creates research station in current city
                if(!pandemic.currCity.hasResearchStation()) {
                    pandemic.researchStation(pandemic.currCity);
                }
                movesLeft.setText("Actions Remaining: " + pandemic.actionsRemaining);
                if (turnEnd()) {
                    endTurn();
                }
            });
            bottomHBox.getChildren().addAll(move, treat, playCard, trade, findCure, researchStation, endTurn);
        } else {
            bottomHBox.getChildren().addAll(move, treat, playCard, trade, findCure, endTurn);
        }


        bottomHBox.setAlignment(Pos.CENTER);
        layout.setBottom(bottomHBox);
    }

    // other methods

    private boolean turnEnd() { // checks if at the end of the turn, or when actionsRemaining = 0
        if (pandemic.actionsRemaining == 0) {
            return true;
        }
        return false;
    }

    private void endTurn() { // updates graphics at the end of a players turn
        ZZZEndTurnScreen.display(pandemic, updateLabel);
        resetPlayerHand(pandemic.currPlayer);
        resetNumCubeLabels();
        pandemic.proceedTurn();
        currPlayerLabel.setText(pandemic.currPlayer.getName() + "'s Turn");
        movesLeft.setText("Actions Remaining: " + pandemic.actionsRemaining);
        infectionCardNumLabel.setText("Player Cards Left: " + pandemic.playerDeck.size());
        numOutbreaksLabel.setText("Number of Outbreaks Until Loss: " + (9 - pandemic.outbreakCounter));


        if(pandemic.gameEnd == true) { // if the game is over, send to end game screen
            if(pandemic.gameWin) {
                ZZZEndGameScreen.display(0);
                window.close();
            } else {
                ZZZEndGameScreen.display(1);
                window.close();
            }
        }

        operationsSpecialist();
    }

    private void operationsSpecialist() { // gives the operationsSpecialist the ability to make a research station in
                                          // city without needing the corresponding playerCard
        bottomHBox.getChildren().clear();
        if(pandemic.currPlayer.getOperativeNumber() == 1) {
            researchStation = new Button("Research Station");
            researchStation.setPrefHeight(100);
            researchStation.setPrefWidth(200);
            researchStation.setOnAction(e -> {
                if(!pandemic.currCity.hasResearchStation()) {
                    pandemic.researchStation(pandemic.currCity);
                }
                movesLeft.setText("Actions Remaining: " + pandemic.actionsRemaining);
                if (turnEnd()) {
                    endTurn();
                }
            });
            bottomHBox.getChildren().addAll(move, treat, playCard, trade, findCure, researchStation, endTurn);
        } else {
            bottomHBox.getChildren().addAll(move, treat, playCard, trade, findCure, endTurn);
        }
    }

    private void resetPlayerHand(Player currPlayer) { // resets the entire left side of the screen
        if (currPlayer.getPlayerNumber() == 0) {
            p1hbox1.getChildren().clear();
            p1hbox2.getChildren().clear();

            for (int x = 0; x < 4; x++) {
                if (x < currPlayer.getHand().size()) {
                    PlayerCard c = currPlayer.getHand().get(x);
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
                    cardLabel.setFont(Font.font(10));
                    p1hbox1.getChildren().add(cardLabel);
                }
            }

            for (int x = 4; x < currPlayer.getHand().size(); x++) {
                PlayerCard c = currPlayer.getHand().get(x);
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
                cardLabel.setFont(Font.font(10));
                p1hbox2.getChildren().add(cardLabel);
            }
        } else if (currPlayer.getPlayerNumber() == 1) {
            p2hbox1.getChildren().clear();
            p2hbox2.getChildren().clear();

            for (int x = 0; x < 4; x++) {
                if (x < currPlayer.getHand().size()) {
                    PlayerCard c = currPlayer.getHand().get(x);
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
                    cardLabel.setFont(Font.font(10));
                    p2hbox1.getChildren().add(cardLabel);
                }
            }

            for (int x = 4; x < currPlayer.getHand().size(); x++) {
                PlayerCard c = currPlayer.getHand().get(x);
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
                cardLabel.setFont(Font.font(10));
                p2hbox2.getChildren().add(cardLabel);
            }
        } else if (currPlayer.getPlayerNumber() == 2) {
            p3hbox1.getChildren().clear();
            p3hbox2.getChildren().clear();

            for (int x = 0; x < 4; x++) {
                if (x < currPlayer.getHand().size()) {
                    PlayerCard c = currPlayer.getHand().get(x);
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
                    cardLabel.setFont(Font.font(10));
                    p3hbox1.getChildren().add(cardLabel);
                }
            }

            for (int x = 4; x < currPlayer.getHand().size(); x++) {
                PlayerCard c = currPlayer.getHand().get(x);
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
                cardLabel.setFont(Font.font(10));
                p3hbox2.getChildren().add(cardLabel);
            }
        } else {
            p4hbox1.getChildren().clear();
            p4hbox2.getChildren().clear();

            for (int x = 0; x < 4; x++) {
                if (x < currPlayer.getHand().size()) {
                    PlayerCard c = currPlayer.getHand().get(x);
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
                    cardLabel.setFont(Font.font(10));
                    p4hbox1.getChildren().add(cardLabel);
                }
            }

            for (int x = 4; x < currPlayer.getHand().size(); x++) {
                PlayerCard c = currPlayer.getHand().get(x);
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
                cardLabel.setFont(Font.font(10));
                p4hbox2.getChildren().add(cardLabel);
            }
        }

    }

    private void movePiece(int playerNumber, Player player) { // moves the player pieces on the center screen
        if (playerNumber == 0) {
            playerMarkerp1.setX(player.getCurrCity().getxCoord()*900/700 - 10);
            playerMarkerp1.setY(player.getCurrCity().getyCoord()*900/700 - 2);
        } else if (playerNumber == 1) {
            playerMarkerp2.setX(player.getCurrCity().getxCoord()*900/700 - 7);
            playerMarkerp2.setY(player.getCurrCity().getyCoord()*900/700 - 8);
        } else if (playerNumber == 2) {
            playerMarkerp3.setX(player.getCurrCity().getxCoord()*900/700 + 2);
            playerMarkerp3.setY(player.getCurrCity().getyCoord()*900/700 - 8);
        } else if (playerNumber == 3) {
            playerMarkerp4.setX(player.getCurrCity().getxCoord()*900/700 + 5);
            playerMarkerp4.setY(player.getCurrCity().getyCoord()*900/700 - 2);
        }
    }

    private String getOperativeName(Player player) { // gets string version of operative number to append to player name
        if (player.getOperativeNumber() == 0) {
            return "Medic";
        } else if (player.getOperativeNumber() == 1) {
            return "Operations Expert";
        } else if (player.getOperativeNumber() == 2) {
            return "Quarantine Specialist";
        } else if (player.getOperativeNumber() == 3) {
            return "Researcher";
        } else if (player.getOperativeNumber() == 4) {
            return "Scientist";
        } else {
            return "Contingency Planner";
        }
    }

    private void resetNumCubeLabels() { // resets labels for number of cubes on right of screen
        blue.setText("Blue: " + pandemic.totalCubes[0] + " " + diseaseStatus(0));
        yellow.setText("Yellow: " + pandemic.totalCubes[1] + " " + diseaseStatus(1));
        black.setText("Black: " + pandemic.totalCubes[2] + " " + diseaseStatus(2));
        red.setText("Red: " + pandemic.totalCubes[3] + " " + diseaseStatus(3));
    }

}
