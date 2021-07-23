package Game;/*

Name: Ethan Chen
Class: Player
Description: Class for a PlayerCard - PlayerCards are the cards that players have in their hands. Players use
                                      PlayerCards to move or create cures. There are also two special types of player
                                      cards, event cards that have special abilities and epidemic cards, which spread
                                      the diseases further.

*/

public class PlayerCard {

    // instance variables

    private int cardType; // int meanings - 0: City, 1: Event, 2: Epidemic
    private String cardName;
    private int cardColor; // int meanings - 0: blue, 1: yellow, 2: black, 3: red, else 4
    private int cardIndex; // if it is a city card, this value is the index in the city array in the game class, else -1

    private boolean hasBeenPlayed; // for contigency planner to denote when a card is played for the second time

    // constructor

    public PlayerCard(int cardType, String cardName, int cardIndex, int cardColor) {
        this.cardType = cardType;
        this.cardName = cardName;
        this.cardColor = cardColor;
        this.cardIndex = cardIndex;
        this.hasBeenPlayed = false;
    }

    // getters and setters

    public int getCardType() {
        return cardType;
    }

    public String getCardName() {
        return cardName;
    }

    public int getCardColor() {
        return cardColor;
    }

    public int getCardIndex() {
        return cardIndex;
    }

    public void playCard() {
        hasBeenPlayed = true;
    }

    public boolean getHasBeenPlayed() {
        return hasBeenPlayed;
    }

    @Override
    public String toString() {
        return cardName + ": " + cardColor;
    }


}
