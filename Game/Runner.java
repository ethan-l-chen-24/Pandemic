package Game;/*

Name: Ethan Chen
Class: Runner
Description: The Text-Based Console Game Version of Pandemic

Bonus: Includes the static method loadCards() that loads the array of cities from the text file

*/

//import edu.princeton.cs.algs4.StdIn;
//import edu.princeton.cs.algs4.StdOut;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Runner {

    // class variables

    public static Game pandemic;
    public static LinkedList<City[]> connections = new LinkedList();
/*
    // main runner

    public static void main(String[] args) throws IOException {
        setupRolesAndBoard();
        while (!pandemic.gameEnd) {
            playTurn();
        }
        displayGameStats();
    }

    public static void setupRolesAndBoard() throws IOException { // create players and world
        City[] cities = loadCardsFromTxt();

        StdOut.println("Welcome to Pandemic");

        StdOut.println("Please select a difficulty; 0: easy, 1: medium, 2: hard");
        int difficulty = StdIn.readInt();
        StdOut.println();

        Queue<Player> players = new Queue();
        StdOut.println("How many players would you like to play with?");
        int numPlayers = StdIn.readInt();
        StdOut.println();
        for (int x = 1; x <= numPlayers; x++) {
            StdOut.println("Player " + x + " name: ");
            String name = StdIn.readString();
            StdOut.println("What role would " + name + " like to pick?");
            StdOut.println("0: Medic, 1: Operations Expert, 2: Quarantine Specialist, 3: Researcher, 4: Scientist, 5: Contingency Planner");
            int role = StdIn.readInt();
            StdOut.println();
            Player p = new Player(name, role, cities[0]);
            players.enqueue(p);
        }

        pandemic = new Game(players, cities, difficulty);
    }

    public static void playTurn() { // play (game.currPlayer)'s turn
        StdOut.println(pandemic.currPlayer.getName() + "'s turn");
        StdOut.println();
        displayWorldStatus(pandemic); // print out list of options
        StdOut.println();
        while (pandemic.actionsRemaining != 0) { // do 4 actions, stop when actionsRemaining = 0
            listOfOptions();
            int actionChoice = StdIn.readInt();
            StdOut.println();

            if (actionChoice == 0) { // all of the options
                move(pandemic);
            } else if (actionChoice == 1) {
                treat(pandemic);
            } else if (actionChoice == 2) {
                playCard(pandemic);
            } else if (actionChoice == 3) {
                createResearchStation(pandemic);
            } else if (actionChoice == 4) {
                travelViaResearchStation(pandemic);
            } else if (actionChoice == 5) {
                tradeCards(pandemic);
            } else if (actionChoice == 6) {
                displayWorldStatus(pandemic);
            } else if (actionChoice == 7) {
                displayPlayerStatus(pandemic);
            } else if (actionChoice == 8) {
                findCure(pandemic);
            } else if (actionChoice == 9) {
                discardCards(pandemic);
            } else if (actionChoice == 10) {
                pandemic.actionsRemaining = 0;
            } else {
                StdOut.println("Please read the instructions next time");
            }
            StdOut.println();
        }

        LinkedList<PlayerCard> pulledPlayerCards = pandemic.drawPlayerCards(); // draw player cards at end of turn
        for (PlayerCard pulledCard : pulledPlayerCards) {
            StdOut.println("You have pulled  " + pulledCard + "  from the player deck");
        }
        StdOut.println();

        LinkedList<InfectionCard> pulledInfectionCards = pandemic.drawInfectionCards(); // draw infection cards at end of turn
        for (InfectionCard pulledCard : pulledInfectionCards) {
            StdOut.println("You have pulled  " + pulledCard + "  from the infection deck");
        }
        pandemic.proceedTurn();

    }

    public static void listOfOptions() {
        StdOut.println("You have " + pandemic.actionsRemaining + " actions left");
        StdOut.println("What would you like to do?");
        StdOut.println("0: Move"); // works
        StdOut.println("1: Treat a Disease"); // works
        StdOut.println("2: Play a Card"); // travel works
        StdOut.println("3: Build a Research Station"); // works
        StdOut.println("4: Travel Via a Research Station"); // works
        StdOut.println("5: Share Cards"); // works
        StdOut.println("6: View current world status"); // works
        StdOut.println("7: View player status"); // works
        StdOut.println("8: Find cure"); // works
        StdOut.println("9: Discard cards"); // works
        StdOut.println("10: End turn"); // works
    }

    public static void move(Game pandemic) { // moves a player to surrounding cities
        int numMoveOptions = pandemic.currCity.getRoadsTo().size();
        StdOut.println("Where would you like to move?");
        String concatString = ""; // prints lists of cities to move to
        for (int x = 0; x < numMoveOptions; x++) {
            concatString += (x + ": " + pandemic.currCity.getRoadsTo().get(x).getName() + "  ");
        }
        concatString += (numMoveOptions + ": cancel");
        StdOut.println(concatString);
        int moveTo = StdIn.readInt();
        if (moveTo >= numMoveOptions || moveTo < 0) {
            return;
        }

        pandemic.move(pandemic.currCity.getRoadsTo().get(moveTo)); // moves
        StdOut.println("You are now in  " + pandemic.currCity);
    }

    public static void treat(Game pandemic) { // treat disease cubes in a players city
        if (pandemic.currCity.getNumDiseaseCubes() > 0) {
            pandemic.treat(); // treats
            StdOut.println("There are now " + pandemic.currCity.getNumDiseaseCubes() + " disease cubes in  " + pandemic.currCity.getName());
        } else {
            StdOut.println("There were no disease cubes at this location");
        }
    }

    public static void playCard(Game pandemic) { // play a card in a players hand
        StdOut.println("Which card from your hand would you like to play?");
        int numCardOptions = pandemic.currPlayer.getHand().size();
        String concatString = ""; // prints out list of options from cards in players hand
        for (int x = 0; x < numCardOptions; x++) {
            concatString += (x + ": " + pandemic.currPlayer.getHand().get(x) + "  ");
        }
        concatString += (numCardOptions + ": cancel");
        StdOut.println(concatString);
        int whichCard = StdIn.readInt();
        if (whichCard >= numCardOptions || whichCard < 0) {
            return;
        }
        PlayerCard cardToPlay = pandemic.currPlayer.getHand().get(whichCard);


        if (cardToPlay.getCardType() == 0) { // if card is a city card
            // CITY CARDS
            City cardCity = pandemic.cities[cardToPlay.getCardIndex()];
            if (cardCity.equals(pandemic.currCity)) {
                charterFlight(pandemic); // charter flight because in same city as card
            } else {
                directFlight(pandemic, cardCity); // direct flight to city chosen by card
            }
            pandemic.currPlayer.discardFromHand(cardToPlay);
        } else { // if card is an event card
            // EVENT CARDS
            eventCards(pandemic, cardToPlay);
        }
    }

    private static void eventCards(Game pandemic, PlayerCard cardToPlay) { // playing an event card
        if (cardToPlay.getCardName().equals("One Quiet Night")) { // one quiet night
            pandemic.quietNight(); // don't pull infection cards next turn
            if(pandemic.currPlayer.getOperativeNumber() != 5) {
                pandemic.currPlayer.discardFromHand(cardToPlay); // discard card
            }
            return;

        } else if (cardToPlay.getCardName().equals("Forecast")) { // forecast
            LinkedList<InfectionCard> top6Cards = new LinkedList();
            if (pandemic.infectionDeck.size() >= 6) {
                for (int x = 0; x < 6; x++) {
                    top6Cards.add(pandemic.infectionDeck.pop());
                }
            } else {
                for (int x = 0; x < pandemic.infectionDeck.size(); x++) {
                    top6Cards.add(pandemic.infectionDeck.pop());
                }
            }
            LinkedList<InfectionCard> top6CardsCopy = top6Cards.copy(); // receive list of top 5 infection cards and
                                                                        // rearrange them however you like to
            int counter = 1;
            Stack<InfectionCard> intermediate = new Stack();
            while (!top6Cards.isEmpty()) {
                StdOut.println(counter + "?");
                int cardOrders = top6Cards.size();
                String concatString = "";
                for (int x = 0; x < cardOrders; x++) {
                    concatString += (x + ": " + top6Cards.get(x).getInfectionCity().getName() + "  ");
                }
                concatString += (cardOrders + ": cancel");
                StdOut.println(concatString);
                int whichCard = StdIn.readInt();
                if (whichCard >= cardOrders || whichCard < 0) {
                    intermediate = new Stack();
                    for(InfectionCard card : top6CardsCopy) {
                        intermediate.push(card);
                    }
                    for(InfectionCard card : intermediate) {
                        pandemic.infectionDeck.push(card);
                    }
                    return;
                }
                intermediate.push(top6Cards.get(whichCard));
                top6Cards.remove(top6Cards.get(whichCard));
            }
            for(InfectionCard card : intermediate) {
                pandemic.infectionDeck.push(card);
            }
            if(pandemic.currPlayer.getOperativeNumber() != 5) {
                pandemic.currPlayer.discardFromHand(cardToPlay);
            }
            StdOut.println("You have reordered the top cards");

        } else if (cardToPlay.getCardName().equals("Airlift")) { // airlift
            int numCityOptions = pandemic.cities.length - 1;
            StdOut.println("Where would you like to be airlifted to?"); // move a player to anywhere on the map
            String concatString = "";
            for (int x = 0; x < pandemic.cities.length; x++) {
                if (!pandemic.cities[x].equals(pandemic.currCity)) {
                    concatString += (x + ": " + pandemic.cities[x] + "  ");
                }
            }
            concatString += (numCityOptions + ": cancel");
            StdOut.println(concatString);
            int whichCity = StdIn.readInt();
            if (whichCity < pandemic.cities.length && whichCity >= 0) {
                City chosenCity = pandemic.cities[whichCity];
                pandemic.airlift(chosenCity, pandemic.currPlayer);
                StdOut.println("You have been airlifted to  " + chosenCity.getName());
                if(pandemic.currPlayer.getOperativeNumber() != 5) {
                    pandemic.currPlayer.discardFromHand(cardToPlay);
                }
                return;
            } else {
                return;
            }

        } else if (cardToPlay.getCardName().equals("Government Grant")) { // government grant
            int numCityOptions = pandemic.cities.length;
            StdOut.println("Which city would you like a research station in?"); // build a research station in any city
            String concatString = "";
            for (int x = 0; x < pandemic.cities.length; x++) {
                    concatString += (x + ": " + pandemic.cities[x] + "  ");
            }
            concatString += (numCityOptions + ": cancel");
            StdOut.println(concatString);
            int whichCity = StdIn.readInt();
            if (whichCity < pandemic.cities.length && whichCity >= 0) {
                City chosenCity = pandemic.cities[whichCity];
                if (!chosenCity.hasResearchStation()) {
                    pandemic.researchStation(chosenCity);
                    StdOut.println("You put a research station in  " + chosenCity.getName());
                    if(pandemic.currPlayer.getOperativeNumber() != 5) {
                        pandemic.currPlayer.discardFromHand(cardToPlay);
                    }
                } else {
                    StdOut.println("This city already has a research station");
                }
                return;
            } else {
                return;
            }


        } else if (cardToPlay.getCardName().equals("Resilient Population")) { // resilient popultation
            StdOut.println("Which infection card would you like to remove from the game?"); // remove any one infection
                                                                                            // card from the game
            int numInfectionCardOptions = pandemic.infectionDiscard.size();
            String concatString = "";
            for (int x = 0; x < numInfectionCardOptions; x++) {
                InfectionCard eachCard = pandemic.infectionDiscard.popFromIndex(x);
                concatString += (x + ": " + eachCard.getInfectionCity().getName() + "  ");
                pandemic.infectionDiscard.push(eachCard);
            }
            concatString += numInfectionCardOptions + ": cancel";
            StdOut.println(concatString);
            int whichInfectionCard = StdIn.readInt();
            if (whichInfectionCard >= numInfectionCardOptions || whichInfectionCard < 0) {
                return;
            } else {
                InfectionCard chosenCard = pandemic.infectionDiscard.popFromIndex(numInfectionCardOptions-whichInfectionCard-1);
                StdOut.println("You have chosen to rid  " + chosenCard.getInfectionCity().getName() + "  from the Infection Deck");
                if(pandemic.currPlayer.getOperativeNumber() != 5) {
                    pandemic.currPlayer.discardFromHand(cardToPlay);
                }
            }
        }
    }

    private static void charterFlight(Game pandemic) { // move from current city to any city
        String concatString = "";
        for (int x = 0; x < pandemic.cities.length; x++) {
            if (!pandemic.cities[x].equals(pandemic.currCity)) {
                concatString += (x + ": " + pandemic.cities[x] + "  ");
            }
        }
        StdOut.println(concatString);
        int whichCity = StdIn.readInt();
        if (whichCity < pandemic.cities.length && whichCity >= 0) {
            City chosenCity = pandemic.cities[whichCity];
            pandemic.charterFlight(chosenCity);
            StdOut.println("You have taken a charter flight to  " + chosenCity.getName());
        } else {
            return;
        }
    }

    private static void directFlight(Game pandemic, City cardCity) { // move from current city to the city designated
        pandemic.directFlight(cardCity);
        StdOut.println("You have taken a direct flight to  " + cardCity.getName());
    }

    public static void createResearchStation(Game pandemic) { // creates a research station
        if (pandemic.currCity.hasResearchStation() == false) { // if there isn't already a research station
            boolean foundCard = false;
            for (PlayerCard card : pandemic.currPlayer.getHand()) {
                if (card.getCardType() == 1 && pandemic.cities[card.getCardIndex()].equals(pandemic.currCity.getName())) {
                    StdOut.println("There is now a research station in  " + pandemic.currCity);
                    pandemic.currPlayer.discardFromHand(card);
                    foundCard = true;
                } else if (pandemic.currPlayer.getOperativeNumber() == 1) {
                    StdOut.println("There is now a research station in  " + pandemic.currCity);
                    pandemic.researchStation(pandemic.currCity);
                    pandemic.currPlayer.discardFromHand(card);
                    return;
                }
            }
            if (!foundCard) {
                StdOut.println("You did not have the card to do so");
            } else {
                pandemic.researchStation(pandemic.currCity);
            }
        } else {
            StdOut.println("There was already a research station at this location");
        }
    }

    public static void travelViaResearchStation(Game pandemic) { // move using a research station
        if (!pandemic.currCity.hasResearchStation()) {
            StdOut.println("There is not a research station at your current city so you cannot travel");
        }

        if (pandemic.currPlayer.getOperativeNumber() == 1) {
            charterFlight(pandemic);
            return;
        }

        StdOut.println("Which city would you like to travel to?");
        int numCityOptions = pandemic.researchStations.size();
        String concatString = "";
        for (int x = 0; x < numCityOptions; x++) {
            if (!pandemic.researchStations.get(x).equals(pandemic.currCity)) {
                concatString += (x + ": " + pandemic.researchStations.get(x) + "  ");
            }
        }
        concatString += (numCityOptions + ": cancel");
        StdOut.println(concatString);
        int whichCity = StdIn.readInt();
        if (whichCity >= numCityOptions || whichCity < 0) {
            return;
        }
        City shuttleCity = pandemic.researchStations.get(whichCity);
        pandemic.shuttleFlight(shuttleCity);
        StdOut.println("You are now in Chicago");
    }

    public static void tradeCards(Game pandemic) { // trade cards with another player
        StdOut.println("Which card from your hand would you like to trade?");
        int numCardOptions = pandemic.currPlayer.getHand().size();
        String concatString = "";
        for (int x = 0; x < numCardOptions; x++) {
            concatString += (x + ": " + pandemic.currPlayer.getHand().get(x) + "  ");
        }
        concatString += (numCardOptions + ": cancel");
        StdOut.println(concatString);
        int whichCard = StdIn.readInt();
        if (whichCard >= numCardOptions || whichCard < 0) {
            return;
        }
        PlayerCard cardToTrade = pandemic.currPlayer.getHand().get(whichCard);
        if (!pandemic.cities[cardToTrade.getCardIndex()].equals(pandemic.currCity) || pandemic.currPlayer.getOperativeNumber() == 5) {
            StdOut.println("You must be in the city of the card you are trying to trade to trade.");
            return;
        }

        StdOut.println("Who would you like to trade with?");
        concatString = "";
        int numPlayerOptions = pandemic.playersOnCurrCity.size();
        for (int x = 0; x < numPlayerOptions; x++) {
            concatString += (x + ": " + pandemic.playersOnCurrCity.get(x).getName() + "  ");
        }
        concatString += (numPlayerOptions + ": cancel");
        StdOut.println(concatString);
        int whichPlayer = StdIn.readInt();
        if (whichPlayer >= numPlayerOptions || whichPlayer < 0) {
            return;
        }
        Player playerToTrade = pandemic.playersOnCurrCity.get(whichPlayer);

        if (playerToTrade.getHand().size() >= 7) {
            StdOut.println("This player has too many cards and cannot trade");
        } else {
            pandemic.tradeCards(playerToTrade, cardToTrade);
        }
    }

    public static void displayWorldStatus(Game pandemic) { // display all of the cities and their disease cubes
        for (City c : pandemic.cities) {
            StdOut.println(c);
        }
    }

    public static void displayPlayerStatus(Game pandemic) {
        StdOut.println(pandemic.currPlayer);
    } // display current
                                                                                        // player's status (cards, name)

    public static void findCure(Game pandemic) { // find a cure using 5 player cards of the color of the disease you
                                                 // want to cure
        if (!pandemic.currCity.hasResearchStation()) { // has to be a research station at the city to find cure
            StdOut.println("You cannot create a cure because there is not a research station at this city.");
            return;
        }

        StdOut.println("What color would you like to find a cure for? 0: blue, 1: yellow, 2: black, 3: red, 4: Cancel");
        int cureColor = StdIn.readInt();
        if (cureColor == 4) {
            return;
        } else if (pandemic.cured[cureColor]) {
            StdOut.println("This disease has already been cured");
            return;
        }

        LinkedList<PlayerCard> correctColorCard = new LinkedList();
        for (PlayerCard cardToCure : pandemic.currPlayer.getHand()) { // choose the 5 cards from the hand
            if (cardToCure.getCardColor() == cureColor) {
                correctColorCard.add(cardToCure);
            }
        }
        if (correctColorCard.size() >= 4 && pandemic.currPlayer.getOperativeNumber() == 4) {
            LinkedList<PlayerCard> toRemoveFromHand = new LinkedList();
            for (int i = 1; i < 5; i++) {
                StdOut.println("Card " + i + "?");

                int numCardOptions = correctColorCard.size();
                String concatString = "";
                for (int x = 0; x < numCardOptions; x++) {
                    concatString += (x + ": " + correctColorCard.get(x) + "  ");
                }
                concatString += (numCardOptions + ": cancel");
                StdOut.println(concatString);
                int whichCard = StdIn.readInt();
                if (whichCard == numCardOptions) {
                    return;
                }
                toRemoveFromHand.add(correctColorCard.get(whichCard));
            }
            for (PlayerCard removed : toRemoveFromHand) {
                pandemic.currPlayer.discardFromHand(removed);
            }
            pandemic.discoverCure(cureColor);
            StdOut.println("You have discovered this cure");
        } else if (correctColorCard.size() >= 5) {
            LinkedList<PlayerCard> toRemoveFromHand = new LinkedList();
            for (int i = 1; i < 5; i++) {
                StdOut.println("Card " + i + "?");

                int numCardOptions = correctColorCard.size();
                String concatString = "";
                for (int x = 0; x < numCardOptions; x++) {
                    concatString += (x + ": " + correctColorCard.get(x) + "  ");
                }
                concatString += (numCardOptions + ": cancel");
                StdOut.println(concatString);
                int whichCard = StdIn.readInt();
                if (whichCard == numCardOptions) {
                    return;
                }
                toRemoveFromHand.add(correctColorCard.get(whichCard));
            }
            for (PlayerCard removed : toRemoveFromHand) {
                pandemic.currPlayer.discardFromHand(removed);
            }
            pandemic.discoverCure(cureColor);
            StdOut.println("You have discovered this cure");
        } else {
            StdOut.println("You do not have enough of the correct color cards to discover a cure");
        }
    }

    public static void discardCards(Game pandemic) { // remove cards from your hand
        StdOut.println("Which card from your hand would you like to discard?");
        int numCardOptions = pandemic.currPlayer.getHand().size();
        String concatString = "";
        for (int x = 0; x < numCardOptions; x++) {
            concatString += (x + ": " + pandemic.currPlayer.getHand().get(x) + "  ");
        }
        concatString += (numCardOptions + ": cancel");
        StdOut.println(concatString);
        int whichCard = StdIn.readInt();
        if (whichCard >= numCardOptions || whichCard < 0) {
            return;
        }
        PlayerCard cardToDiscard = pandemic.currPlayer.getHand().get(whichCard);
        pandemic.currPlayer.discardFromHand(cardToDiscard);
    }

    public static void displayGameStats() { //
        if (pandemic.gameWin) {
            StdOut.println("Game Result: Win!");
        } else {
            StdOut.println("Game Result: Lose :(");
        }
    }

    // Load Cards
*/
    public static City[] loadCardsFromTxt() throws IOException { // loads all of the cards from the text file using a
                                                                 // buffered reader
        BufferedReader buf = new BufferedReader(new FileReader(new File("src/TextFiles/Locations.txt"))); // real txt file
       // BufferedReader buf = new BufferedReader(new FileReader(new File("src/TextFiles/Blue.txt"))); // tester txt file
        String line = buf.readLine();
        StringTokenizer tok = new StringTokenizer(line, " ");
        int numCities = Integer.valueOf(tok.nextToken()); // first number - # cities
        int numConnections = Integer.valueOf(tok.nextToken()); // second number - # number connections

        City[] cityList = new City[numCities];

        for (int x = 0; x < numCities; x++) {
            line = buf.readLine();
            tok = new StringTokenizer(line, ";"); // breaks line by semicolon
            tok.nextToken();
            String name = tok.nextToken(); // first name
            String color = tok.nextToken(); // then color
            int xCoord = Integer.valueOf(tok.nextToken()); // then x-coordinate
            int yCoord = Integer.valueOf(tok.nextToken()); // then y-coordinate

            int colorNum = -1;
            if (color.equals("blue")) {
                colorNum = 0;
            } else if (color.equals("yellow")) {
                colorNum = 1;
            } else if (color.equals("black")) {
                colorNum = 2;
            } else {
                colorNum = 3;
            }

            City newCity = new City(name, colorNum, xCoord, yCoord);
            cityList[x] = newCity; // puts the city in this list of cities that is returned
        }

        for (int x = 0; x < numConnections; x++) {
            line = buf.readLine();
            tok = new StringTokenizer(line, " ");
            City city1 = cityList[Integer.valueOf(tok.nextToken())];
            City city2 = cityList[Integer.valueOf(tok.nextToken())];
            city1.addRoadsTo(city2); // for each city, puts the other in their (roadsTo)
            city2.addRoadsTo(city1);

            City[] connectingCities = new City[2];
            connectingCities[0] = city1;
            connectingCities[1] = city2;
            connections.add(connectingCities);

        }

        return cityList;
    }


}
