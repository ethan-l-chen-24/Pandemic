package Game;/*

Name: Ethan Chen
Class: InfectionCard
Description: Class for Infection Cards - Infection Cards are pulled at the end of each turn, and one disease cube is
                                         placed on the city each pulled card designates.

*/

public class InfectionCard {

    // instance variables

    private City infectionCity; // City the card corresponds to

    // Constructor

    public InfectionCard(City infectionCity) {
        this.infectionCity = infectionCity;
    }

    // Getters and Setters

    public City getInfectionCity() {
        return infectionCity;
    }

    @Override
    public String toString() {
        return infectionCity.getName();
    }
}
