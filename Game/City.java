package Game;/*

Name: Ethan Chen
Class: City
Description: Class for a City - contains everything necessary to know about a city in pandemic, such as name, location
                                and which cities it is connected to, acting as a map of the world. Players move
                                within cities, treat diseases in cities, and build research stations or trade in cities.

*/


public class City {

    // Instance Variables

    private String name;
    private int diseaseColor; // Integer meanings - 0: blue, 1: yellow, 2: black, 3: red
    private int numDiseaseCubes;
    private LinkedList<City> roadsTo; // cities with a road to this city
    private boolean hasResearchStation;
    private int xCoord; // coordinates for graphics
    private int yCoord;

    // Constructor

    public City(String name, int diseaseColor, int xCoord, int yCoord) {
        this.name = name;
        this.diseaseColor = diseaseColor;
        this.numDiseaseCubes = 0;
        this.roadsTo =  new LinkedList<City>();
        if(name.equals("Atlanta")) {
            this.hasResearchStation = true;
        } else {
            this.hasResearchStation = false;
        }
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    // class methods

    public void increaseDiseaseCubes(int numOfIncrease) { // increases number of disease cubes in city by specified amount
        numDiseaseCubes += numOfIncrease;
    }

    public void decreaseDiseaseCubes(int numOfDecrease) { // decreases number of disease cubes in city by specified amount
        numDiseaseCubes -= numOfDecrease;
    }

    public void clearAllDiseaseCubes() { // removes all disease cubes
        numDiseaseCubes = 0;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDiseaseColor() {
        return diseaseColor;
    }

    public int getNumDiseaseCubes() {
        return numDiseaseCubes;
    }

    public void setNumDiseaseCubes(int numDiseaseCubes) {
        this.numDiseaseCubes = numDiseaseCubes;
    }

    public LinkedList<City> getRoadsTo() {
        return roadsTo;
    }

    public void addRoadsTo(City linkedCity) {
        this.roadsTo.add(linkedCity);
    }

    public boolean hasResearchStation() {
        return hasResearchStation;
    }

    public void createResearchStation() {
        this.hasResearchStation = true;
    }

    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    // to string method

    @Override
    public String toString() {
        return name + ": # Disease Cubes - " + numDiseaseCubes;
    }

}
