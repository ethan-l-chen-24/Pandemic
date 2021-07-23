package Game;/*

Name: Ethan Chen
Class: Game
Description: The Cumulative Model Class - Creates the game and all of its necessary variables, has all of the methods to
                                          play the game.

*/

import Data_Structures.*;
import java.io.IOException;

public class Game {

    // instance variables

    public Queue<Player> players;
    public City[] cities;
    public Stack<InfectionCard> infectionDeck;
    public Stack<PlayerCard> playerDeck;
    public Stack<InfectionCard> infectionDiscard;
    public LinkedList<City> researchStations; // list of all cities with research stations
    public LinkedList<Player> playersOnCurrCity;
    public City lastEpidemicLocation; // city where last epidemic card was pulled for

    public int difficulty; // 0 = easy, 1 = medium, 2 = hard, decides how many epidemic cards are put in the deck
    public boolean[] cured; // each index - 0: blue, 1: yellow, 2: black, 3: red
    public boolean[] eradicated; // each index - 0: blue, 1: yellow, 2: black, 3: red
    public int[] totalCubes; // each index - 0: blue, 1: yellow, 2: black, 3: red
    public int infectionRate; // goes up each time an epidemic card is pulled, index for infectionRatePossibilities
    public int[] infectionRatePossibilities = {2, 2, 2, 3, 3, 4, 5}; // how many cards are pulled each turn (depending on number
                                                              // of epidemic cards pulled
    public int outbreakCounter; // how many outbreaks have occurred

    public LinkedList<City> outbrokenCities; // cities since last outbreak that have had outbreaks

    public  boolean gameEnd;
    public boolean gameWin;

    public boolean quietNight;

    public Player currPlayer;
    public City currCity;
    public int actionsRemaining;

    // constructor

    public Game(Queue<Player> players, City[] cities, int difficulty) {
        this.players = new Queue();
        this.cities = cities;
        this.difficulty = difficulty;
        this.cured = new boolean[4];
        this.infectionRate = 0;
        this.outbreakCounter = 0;
        this.gameEnd = false;
        setupGame(players);
    }

    //SETUP PHASE

    private void setupGame(Queue<Player> players) { // sets up the game
        setupOtherVars();
        setupDecks();
        setupBoard(players);
        setupPlayerHands();
        setupFirstPlayer();
        checkIfEradicated();
    }

    private void setupOtherVars() { // initializes variables that are not used immediately
        cured = new boolean[4];
        eradicated = new boolean[4];
        totalCubes = new int[]{0, 0, 0, 0};
        researchStations = new LinkedList();
        researchStations.add(cities[0]);
        outbrokenCities = new LinkedList();
        quietNight = false;
        lastEpidemicLocation = null;
    }

    private void setupDecks() { // creates the infection and player decks
        infectionDeck = new Stack();
        playerDeck = new Stack();
        infectionDiscard = new Stack();


        for (int x = 0; x < cities.length; x++) { // creates new infection card for each city and adds to deck
            InfectionCard newInfectionCard = new InfectionCard(cities[x]);
            infectionDeck.push(newInfectionCard);
        }

        for (int x = 0; x < cities.length; x++) { // creates new player card for each city and adds to deck
            PlayerCard newPlayerCard = new PlayerCard(0, cities[x].getName(), x, cities[x].getDiseaseColor());
            playerDeck.push(newPlayerCard);
        }

        int numEpidemicCards;
        if (difficulty == 0) { // chooses number of epidemic cards based on difficulty
            numEpidemicCards = 4;
        } else if (difficulty == 1) {
            numEpidemicCards = 5;
        } else {
            numEpidemicCards = 6;
        }

        for (int x = 0; x < numEpidemicCards; x++) { // adds that many epidemic cards to the player deck
            PlayerCard epidemic = new PlayerCard(2, "Epidemic", -1, 4);
            playerDeck.push(epidemic);
        }

        // INSERT EVENT CARDS HERE
        PlayerCard oneQuietNight = new PlayerCard(1, "One Quiet Night", -1, 4);
        PlayerCard forecast = new PlayerCard(1, "Forecast", -1, 4);
        PlayerCard airlift = new PlayerCard(1, "Airlift", -1, 4);
        PlayerCard governmentGrant = new PlayerCard(1, "Government Grant", -1, 4);
        PlayerCard resilientPop = new PlayerCard(1, "Resilient Population", -1, 4);
        playerDeck.push(oneQuietNight);
        playerDeck.push(forecast);
        playerDeck.push(airlift);
        playerDeck.push(governmentGrant);
        playerDeck.push(resilientPop); // adds all of the event cards to the deck

        infectionDeck.shuffle(); // shuffles both decks
        playerDeck.shuffle();
    }

    private void setupBoard(Queue<Player> players) { // puts the board in the initial state of the game
                                                     // 3 disease cubes on first 3 infection cards pulled
                                                     // 2 disease cubes on next 3 infection cards pulled
                                                     // 1 disease cube on last 3 infection cards pulled
        for (int x = 11; x > 2; x--) {
            InfectionCard infectCard = infectionDeck.pop();
            infectionDiscard.push(infectCard);
            infectCard.getInfectionCity().increaseDiseaseCubes(x / 3); // mathematical algorithm that adds
                                                        // the correct number of disease cubes in the right cities
            totalCubes[infectCard.getInfectionCity().getDiseaseColor()] += x / 3;
        }

        this.players = players;
    }

    private void setupPlayerHands() { // puts cards in players' hands
        int numCardsPulled;
        if(players.size() == 1) { // chooses number of cards in each players hands initially based on number of players
            numCardsPulled = 5;
        } else if(players.size() == 2) {
            numCardsPulled = 4;
        } else if(players.size() == 3) {
            numCardsPulled = 3;
        } else {
            numCardsPulled = 2;
        }

        for (Player player : players) { // gives each player that many cards from the player deck
            for (int x = 0; x < numCardsPulled; x++) {
                PlayerCard topCard = playerDeck.pop();
                if (topCard.getCardType() == 2) {
                    epidemic(); // if they pull an epidemic play the epidemic
                } else {
                    player.addToHand(topCard); // if not an epidemic, add it to their hand
                }
            }
        }
    }

    private void setupFirstPlayer() { // designates a first player and gets them ready to play their turn
        currPlayer = players.dequeue();
        currCity = currPlayer.getCurrCity();
        actionsRemaining = 4;
        findPlayersOnCurrCity();
    }

    //PLAYER TURN PHASE
    // Movements

    public void move(City nextCity) { // moves a player
        currPlayer.moveTo(nextCity); // movement
        currCity = nextCity;
        actionsRemaining--; // reduction of actions
        if (cured[nextCity.getDiseaseColor()] && currPlayer.getOperativeNumber() == 0) { // automatic clearing of cubes
                                                                                        // if it is cured and medic
            totalCubes[currCity.getDiseaseColor()] -= nextCity.getNumDiseaseCubes();
            nextCity.decreaseDiseaseCubes(nextCity.getNumDiseaseCubes());
        }
        findPlayersOnCurrCity();
    }

    public void directFlight(City toCity) { // same method as move under a different method name
        currPlayer.moveTo(toCity);
        currCity = toCity;
        actionsRemaining--;
        if (cured[toCity.getDiseaseColor()] && currPlayer.getOperativeNumber() == 0) {
            totalCubes[currCity.getDiseaseColor()] -= toCity.getNumDiseaseCubes();
            toCity.decreaseDiseaseCubes(toCity.getNumDiseaseCubes());
        }
        findPlayersOnCurrCity();
    }

    public void charterFlight(City toCity) { // same method as move under a different method name
        currPlayer.moveTo(toCity);
        currCity = toCity;
        actionsRemaining--;
        if (cured[toCity.getDiseaseColor()] && currPlayer.getOperativeNumber() == 0) {
            totalCubes[currCity.getDiseaseColor()] -= toCity.getNumDiseaseCubes();
            toCity.decreaseDiseaseCubes(toCity.getNumDiseaseCubes());
        }
        findPlayersOnCurrCity();
    }

    public void shuttleFlight(City toCity) { // same method as move under a different method name
        currPlayer.moveTo(toCity);
        currCity = toCity;
        actionsRemaining--;
        if (cured[toCity.getDiseaseColor()] && currPlayer.getOperativeNumber() == 0) {
            totalCubes[currCity.getDiseaseColor()] -= toCity.getNumDiseaseCubes();
            toCity.decreaseDiseaseCubes(toCity.getNumDiseaseCubes());
        }
        findPlayersOnCurrCity();
    }

    public void airlift(City toCity, Player playerToMove) { // moves a designated player to the designated city
                                                            // same as move, except not for currPlayer and doesn't
                                                            // reduce actions because it is an event
        playerToMove.moveTo(toCity);
        if(playerToMove.equals(currPlayer)) {
            currCity = toCity;
        }
        if (cured[toCity.getDiseaseColor()] && playerToMove.getOperativeNumber() == 0) {
            totalCubes[currCity.getDiseaseColor()] -= toCity.getNumDiseaseCubes();
            toCity.decreaseDiseaseCubes(toCity.getNumDiseaseCubes());
        }
        findPlayersOnCurrCity();
    }

    // Actions

    public void researchStation(City cityToAdd) { // adds a research station to the designated city
        cityToAdd.createResearchStation();
        researchStations.add(cityToAdd);
        actionsRemaining--;
    }

    public void treat() { // treats the disease cubes in a city
        if (currPlayer.getOperativeNumber() == 0 || cured[currCity.getDiseaseColor()]) { // medic or cured
            totalCubes[currCity.getDiseaseColor()] -= currCity.getNumDiseaseCubes(); // remove all the disease cubes
            currCity.clearAllDiseaseCubes();
        } else {
            totalCubes[currCity.getDiseaseColor()]--; // remove just 1
            currCity.decreaseDiseaseCubes(1);
        }
        actionsRemaining--;
        checkIfEradicated();
    }

    public void tradeCards(Player p, PlayerCard c) { // trade card c from currPlayer's hand to player p
        p.addToHand(currPlayer.discardFromHand(c));
        actionsRemaining--;
    }

    public void discoverCure(int curedColor) { // discover a cure
        cured[curedColor] = true; // set the curedColor color to cured
        if(currPlayer.getOperativeNumber() == 0) {
            totalCubes[currCity.getDiseaseColor()] -= currCity.getNumDiseaseCubes();
            currCity.clearAllDiseaseCubes();
        }
        actionsRemaining--;
    }

    // Extra - other methods necessary for game to run

    private void findPlayersOnCurrCity() { // updates playesOnCurrCity variable for a new city, important for trading
        playersOnCurrCity = new LinkedList();
        for (Player p : players) {
            if (p != null && p.getCurrCity().equals(currCity)) {
                playersOnCurrCity.add(p);
            }
        }
    }

    public void quietNight() { // if one quiet night event card played, don't pull infection cards (see below)
        quietNight = true;
    }

    public void resilientPop(int indexToDiscard) { // remove the card at the index in the discard from the game
        infectionDiscard.popFromIndex(indexToDiscard);
    }

    // Turn End

    public LinkedList<PlayerCard> drawPlayerCards() { // pull 2 player cards for the current player and put in hand

        LinkedList<PlayerCard> pulledCards = new LinkedList();
        boolean firstEpidemic = false;
        for (int x = 0; x < 2; x++) {

            if (playerDeck.isEmpty()) { // lose if run out of playercards
                gameEnd = true;
                gameWin = false;
                return pulledCards;
            }

            PlayerCard topCard = playerDeck.pop();
            pulledCards.add(topCard);
            if (topCard.getCardType() == 2) {
                if (firstEpidemic == true) { // if this is the second epidemic card this pull, put it back on top of
                                             // player deck
                    playerDeck.push(topCard);
                    pulledCards.remove(topCard);
                } else {
                    epidemic(); // epidemic
                }
                firstEpidemic = true;
            } else {
                currPlayer.addToHand(topCard);
            }
        }

        return pulledCards;
    }

    public void discardCards(LinkedList<PlayerCard> cardsToDiscard) { // Discard each of these cards from the current
                                                                      // player's hand
        for (PlayerCard card : cardsToDiscard) {
            currPlayer.discardFromHand(card);
        }
    }

    private void epidemic() { // epidemic card pulled

        infectionRate++; // infection rate increases by 1
        if(infectionDeck.isEmpty()) {
            stackDiscardOnTop();
        }
        InfectionCard infectCard = infectionDeck.pullBottom(); // infect the bottom card with 3 disease cubes if not
                                                               // eradicated
        lastEpidemicLocation = infectCard.getInfectionCity();
        if(infectionDeck.isEmpty()) {
            stackDiscardOnTop();
        }
        infectionDiscard.push(infectCard);
        int infectCardColor = infectCard.getInfectionCity().getDiseaseColor();
        if (!eradicated[infectCardColor]) {
            infectCard.getInfectionCity().increaseDiseaseCubes(3);
            totalCubes[infectCardColor] += 3;
            checkForOutbreaks();
        }

        infectionDiscard.shuffle(); // shuffle the discarded infection cards and stack them back on top
        stackDiscardOnTop();
    }

    private void stackDiscardOnTop() { // put every card from the shuffled discard back on top the infection deck
        while (!infectionDiscard.isEmpty()) {
            infectionDeck.push(infectionDiscard.pop());
        }
    }

    private void outbreak(City city) { // if an outbreak occurs (more than 3 disease cubes in a city)
        outbreakCounter++;
        for (City neighboringCities : city.getRoadsTo()) { // passes one disease cube onto each neighboring city
            if (!eradicated[neighboringCities.getDiseaseColor()]) {
                if (!outbrokenCities.contains(neighboringCities)) { // each city can be outbroken once per turn

                    neighboringCities.increaseDiseaseCubes(1);
                    int infectCardColor = neighboringCities.getDiseaseColor();
                    totalCubes[infectCardColor]++;
                }
                checkForOutbreaks(); // possibility to cause new outbreaks, so check again
            }
        }
    }

    public LinkedList<InfectionCard> drawInfectionCards() { // draw infectionRatePossibilites[infectionRate] infection
                                                            // cards at the end of each players turn
        if (!quietNight) { // if it is a quiet night, don't pull any
            LinkedList<InfectionCard> cardsPulled = new LinkedList();
            for (int x = 0; x < infectionRatePossibilities[infectionRate]; x++) {
                if (infectionDeck.isEmpty()) {
                    stackDiscardOnTop();
                }

                InfectionCard infectCard = infectionDeck.pop();
                int infectCardColor = infectCard.getInfectionCity().getDiseaseColor();

                if (!eradicated[infectCardColor]) { // infect each city card pulled if disease not eradicated or in
                                                    // range of quarantine specialist
                    if (!qsInSurroundingCity(infectCard)) {
                        infectCard.getInfectionCity().increaseDiseaseCubes(1);
                        totalCubes[infectCardColor]++;
                        infectionDiscard.push(infectCard);
                        cardsPulled.add(infectCard);
                        checkForOutbreaks();
                    } else {
                        cardsPulled.add(infectCard);
                    }
                }
            }
            return cardsPulled;
        } else {
            quietNight = false;
            return new LinkedList();
        }
    }

    public boolean qsInSurroundingCity(InfectionCard infectCard) { // quarantine specialist - no surrounding cities can
                                                                   // be infected
        if (currPlayer.getOperativeNumber() == 2) {
            if (infectCard.getInfectionCity().equals(currCity)) {
                return true;
            }
            for (City surroundingCities : currCity.getRoadsTo()) {
                if (infectCard.getInfectionCity().equals(surroundingCities)) {
                    return true;
                }
            }
        }

        for (Player p : players) {
            if (p != null && p.getOperativeNumber() == 2) {
                if (infectCard.getInfectionCity().equals(p.getCurrCity())) {
                    return true;
                }
                for (City surroundingCities : p.getCurrCity().getRoadsTo()) {
                    if (infectCard.getInfectionCity().equals(surroundingCities)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void checkForOutbreaks() { // checks if outbreaks occur
        for (City city : cities) {
            if (city.getNumDiseaseCubes() > 3 && !outbrokenCities.contains(city)) { // reduces number of cubes down to 3
                int infectCardColor = city.getDiseaseColor();
                totalCubes[infectCardColor] -= (city.getNumDiseaseCubes() - 3);
                outbrokenCities.add(city);
                city.setNumDiseaseCubes(3);
                outbreak(city); // outbreak occurs
            }
        }
        for (City c : outbrokenCities) {
            if (c != null && c.getNumDiseaseCubes() > 3) {
                totalCubes[c.getDiseaseColor()] -= (c.getNumDiseaseCubes() - 3);
                c.setNumDiseaseCubes(3);
            }
        }
    }

    // nextTurn

    public void checkGameEnd() { // checks if game over

        if (outbreakCounter >= 9) { // lose if there have been more than 9 outbreaks
            gameEnd = true;
            gameWin = false;
        }
        // lose if there are 25 or more of any single color of disease cube
        if (totalCubes[0] >= 24 || totalCubes[1] >= 24 || totalCubes[2] >= 24 || totalCubes[3] >= 24) {
            gameEnd = true;
            gameWin = false;
        }
        if (eradicated[0] && eradicated[1] && eradicated[2] && eradicated[3]) { // win if all diseases eradicated
            gameEnd = true;
            gameWin = true;
        }
    }

    public void checkIfEradicated() { // if there are no more cubes of a color that disease is eradicated
                                      // and can't come back
        for (int x = 0; x < 4; x++) {
            if (totalCubes[x] == 0) {
                eradicated[x] = true;
            }
        }
    }

    public void proceedTurn() { // move to the next players turn
        checkGameEnd(); // check game end conditions in which case the game would end
        checkIfEradicated(); // update disease states
        players.enqueue(currPlayer); // current player goes to back of line
        currPlayer = players.dequeue(); // new current player is player who was at the front of the line
        currCity = currPlayer.getCurrCity(); // reset new current city
        actionsRemaining = 4; // reset actions
        outbrokenCities = new LinkedList(); // reset outbroken cities
    }

    // Tester

    public static void main(String[] args) throws IOException { // tests that you can create a game and players
        City[] cities = Runner.loadCardsFromTxt();

        Player p1 = new Player("Ethan", 0, cities[0]);
        Queue<Player> players = new Queue<Player>();
        players.enqueue(p1);
        Player p2 = new Player("David", 1, cities[0]);
        players.enqueue(p2);

        Game game = new Game(players, cities, 0);
        System.out.println(game.currPlayer);
        System.out.println(players);
        System.out.println(game.currCity);
        System.out.println(game.infectionRate);
    }

}
