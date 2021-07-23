package Game;/*

Name: Ethan Chen
Class: Player
Description: Class for a Player - players have names, roles, and a hand of cards that they individually own and are able
                                  to play. They also have a city they are currently in and can move around.

*/

public class Player {

    // instance variables

    private String playerName;
    private LinkedList<PlayerCard> hand; // a hand is effectively a list of cards
    private City currCity;
    private int operativeNumber; // int meanings - 0 = Medic, 1 = Operations Expert, 2 = Quarantine Specialist,
                                 // 3 = Researcher, 4 = Scientist, 5 = Contingency Planner
    private int playerNumber; // player 1, 2, 3, etc. Keeps track of who is who, and is important for keeping track of
                              // which player corresponds to which player piece in the graphics component

    // constructor

    public Player(String playerName, int operativeNumber, City startCity) {
        this.playerName = playerName;
        hand = new LinkedList<PlayerCard>();
        this.currCity = startCity;
        this.operativeNumber = operativeNumber;
    }

    // class methods

    public void addToHand(PlayerCard card) {
        hand.add(card);
    } // adds a card to a players hand

    public PlayerCard discardFromHand(PlayerCard card) {// removes a card from a players hand
        return hand.remove(card);
    }

    public void moveTo(City nextCity) { // moves to another city
        currCity = nextCity;
    }

    // getters and setters

    public String getName() {
        return playerName;
    }

    public LinkedList<PlayerCard> getHand() {
        return hand;
    }

    public City getCurrCity() {
        return currCity;
    }

    public int getOperativeNumber() {
        return operativeNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    @Override
    public String toString() {
        return "Player Name: " + playerName + "\nPlayer Role: " + operativeNumber + "\nCurrent City: " + currCity + "\nCurrent Hand: " + hand + "\n";
    }

    // Tester

    public static void main(String[] args) { // creates a player, shows that the player can move and can both
                                             // add and remove cards from their hand
        City atlanta = new City("Atlanta", 0, 0, 0);
        City tokyo = new City("Tokyo", 3, 0, 0);
        Player newPlayer = new Player("Ethan", 0, atlanta);
        System.out.println(newPlayer);

        PlayerCard pc = new PlayerCard(0, "Tokyo", 5, 3);
        newPlayer.addToHand(pc);
        newPlayer.moveTo(tokyo);
        System.out.println(newPlayer);

        PlayerCard discardedCard = newPlayer.discardFromHand(pc);
        System.out.println(newPlayer);
        System.out.println(discardedCard);
    }
}
