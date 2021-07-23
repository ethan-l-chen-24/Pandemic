package Graphics;/*

Name: Ethan Chen
Class: ZZZSettingsScreen
Description: JavaFX Graphics for Settings

*/
import Game.*;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ZZZSettingsScreen {

    private static HBox difficultyHBox, numPlayershbox, p1hbox, p2hbox, p3hbox, p4hbox;
    private static Label p1label, p2label, p3label, p4label, p1role, p2role, p3role, p4role;
    private static TextField p1textField, p2textField, p3textField, p4textField;
    private static ComboBox<String> p1RoleChoices, p2RoleChoices, p3RoleChoices, p4RoleChoices;

    private static VBox vbox;

    // vars
    static int difficulty;
    static int numPlayers;
    private static String[] playerNames;
    private static String[] playerRoles;
    private static int[] playerRoleInts;
    static Queue<Player> playerQueue = new Queue();

    // gives options for difficulty, number of players, and allows input of names and player roles to setup the game
    public static void display(City[] cities) {
        Stage stage = new Stage();

        vbox = new VBox();
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setSpacing(30);


        setupPlayers();
        setupDifficultyButtons();
        setNumPlayersButtons();


        Button startButton = new Button("Start");
        startButton.setOnAction(e -> {
            setVars(cities);
            stage.close();
        });

        vbox.getChildren().addAll(difficultyHBox, numPlayershbox, p1hbox, p2hbox, p3hbox, p4hbox, startButton);

        Scene scene = new Scene(vbox, 500, 500);
        stage.setScene(scene);
        stage.setTitle("Settings");

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });

        stage.showAndWait();

    }

    public static void setupPlayers() {
        p1role = new Label("Role: ");
        p2role = new Label("Role: ");
        p3role = new Label("Role: ");
        p4role = new Label("Role: ");

        p1hbox = new HBox();
        p1hbox.setSpacing(10);
        p1label = new Label("Player 1: ");
        p1textField = new TextField();
        p1RoleChoices = new ComboBox<>();
        p1RoleChoices.getItems().addAll("Medic", "Operations Specialist", "Quarantine Specialist", "Researcher", "Scientist", "Contingency Planner");
        p1hbox.getChildren().addAll(p1label, p1textField, p1role, p1RoleChoices);

        p2hbox = new HBox();
        p2hbox.setSpacing(10);
        p2label = new Label("Player 2: ");
        p2textField = new TextField();
        p2RoleChoices = new ComboBox<>();
        p2RoleChoices.getItems().addAll("Medic", "Operations Specialist", "Quarantine Specialist", "Researcher", "Scientist", "Contingency Planner");
        p2hbox.getChildren().addAll(p2label, p2textField, p2role, p2RoleChoices);

        p3hbox = new HBox();
        p3hbox.setSpacing(10);
        p3label = new Label("Player 3: ");
        p3textField = new TextField();
        p3RoleChoices = new ComboBox<>();
        p3RoleChoices.getItems().addAll("Medic", "Operations Specialist", "Quarantine Specialist", "Researcher", "Scientist", "Contingency Planner");
        p3hbox.getChildren().addAll(p3label, p3textField, p3role, p3RoleChoices);

        p4hbox = new HBox();
        p4hbox.setSpacing(10);
        p4label = new Label("Player 4: ");
        p4textField = new TextField();
        p4RoleChoices = new ComboBox<>();
        p4RoleChoices.getItems().addAll("Medic", "Operations Specialist", "Quarantine Specialist", "Researcher", "Scientist", "Contingency Planner");
        p4hbox.getChildren().addAll(p4label, p4textField, p4role, p4RoleChoices);

        for (Node j : p1hbox.getChildren()) {
            j.setDisable(true);
        }
        for (Node j : p2hbox.getChildren()) {
            j.setDisable(true);
        }
        for (Node j : p3hbox.getChildren()) {
            j.setDisable(true);
        }
        for (Node j : p4hbox.getChildren()) {
            j.setDisable(true);
        }

    }

    public static void setupDifficultyButtons() {
        difficultyHBox = new HBox();
        difficultyHBox.setSpacing(10);
        RadioButton easy = new RadioButton("Easy");
        RadioButton medium = new RadioButton("Medium");
        RadioButton hard = new RadioButton("Hard");
        ToggleGroup difficultyToggle = new ToggleGroup();
        easy.setToggleGroup(difficultyToggle);
        medium.setToggleGroup(difficultyToggle);
        hard.setToggleGroup(difficultyToggle);
        difficultyHBox.getChildren().addAll(easy, medium, hard);

        difficultyToggle.selectedToggleProperty().addListener((v, oldValue, newValue) -> {
            if (newValue.equals(easy)) {
                difficulty = 0;
            }
            if (newValue.equals(medium)) {
                difficulty = 1;
            }
            if (newValue.equals(hard)) {
                difficulty = 2;
            }
        });

    }

    public static void setNumPlayersButtons() {
        numPlayershbox = new HBox();
        numPlayershbox.setSpacing(10);
        RadioButton one = new RadioButton("1");
        RadioButton two = new RadioButton("2");
        RadioButton three = new RadioButton("3");
        RadioButton four = new RadioButton("4");
        ToggleGroup numPlayersToggle = new ToggleGroup();
        one.setToggleGroup(numPlayersToggle);
        two.setToggleGroup(numPlayersToggle);
        three.setToggleGroup(numPlayersToggle);
        four.setToggleGroup(numPlayersToggle);

        numPlayersToggle.selectedToggleProperty().addListener((v, oldValue, newValue) -> {
            if (newValue.equals(one)) {
                for (Node j : p1hbox.getChildren()) {
                    j.setDisable(false);
                }
                for (Node j : p2hbox.getChildren()) {
                    j.setDisable(true);
                }
                for (Node j : p3hbox.getChildren()) {
                    j.setDisable(true);
                }
                for (Node j : p4hbox.getChildren()) {
                    j.setDisable(true);
                }
                numPlayers = 1;
            }
            if (newValue.equals(two)) {
                for (Node j : p1hbox.getChildren()) {
                    j.setDisable(false);
                }
                for (Node j : p2hbox.getChildren()) {
                    j.setDisable(false);
                }
                for (Node j : p3hbox.getChildren()) {
                    j.setDisable(true);
                }
                for (Node j : p4hbox.getChildren()) {
                    j.setDisable(true);
                }
                numPlayers = 2;
            }
            if (newValue.equals(three)) {
                for (Node j : p1hbox.getChildren()) {
                    j.setDisable(false);
                }
                for (Node j : p2hbox.getChildren()) {
                    j.setDisable(false);
                }
                for (Node j : p3hbox.getChildren()) {
                    j.setDisable(false);
                }
                for (Node j : p4hbox.getChildren()) {
                    j.setDisable(true);
                }
                numPlayers = 3;
            }
            if (newValue.equals(four)) {
                for (Node j : p1hbox.getChildren()) {
                    j.setDisable(false);
                }
                for (Node j : p2hbox.getChildren()) {
                    j.setDisable(false);
                }
                for (Node j : p3hbox.getChildren()) {
                    j.setDisable(false);
                }
                for (Node j : p4hbox.getChildren()) {
                    j.setDisable(false);
                }
                numPlayers = 4;
            }
        });

        numPlayershbox.getChildren().addAll(one, two, three, four);
    }

    private static void setVars(City[] cities) {
        playerNames = new String[numPlayers];
        playerRoles = new String[numPlayers];
        playerRoleInts = new int[numPlayers];
        playerNames[0] = p1textField.getText();
        playerRoles[0] = p1RoleChoices.getValue();
        if (numPlayers > 1) {
            playerNames[1] = p2textField.getText();
            playerRoles[1] = p2RoleChoices.getValue();
        }
        if (numPlayers > 2) {
            playerNames[2] = p3textField.getText();
            playerRoles[2] = p3RoleChoices.getValue();
        }
        if (numPlayers > 3) {
            playerNames[3] = p4textField.getText();
            playerRoles[3] = p4RoleChoices.getValue();
        }

        for(int x = 0; x<numPlayers; x++) {
            if(playerRoles[x].equals("Medic")) {
                playerRoleInts[x] = 0;
            }
            if(playerRoles[x].equals("Operations Specialist")) {
                playerRoleInts[x] = 1;
            }
            if(playerRoles[x].equals("Quarantine Specialist")) {
                playerRoleInts[x] = 2;
            }
            if(playerRoles[x].equals("Researcher")) {
                playerRoleInts[x] = 3;
            }
            if(playerRoles[x].equals("Scientist")) {
                playerRoleInts[x] = 4;
            }
            if(playerRoles[x].equals("Contingency Planner")) {
                playerRoleInts[x] = 5;
            }
        }

        for(int x = 0; x<numPlayers; x++) {
            Player newPlayer = new Player(playerNames[x], playerRoleInts[x], cities[0]);
            newPlayer.setPlayerNumber(x);
            playerQueue.enqueue(newPlayer);
            }
        }


    }