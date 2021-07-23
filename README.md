# Pandemic

Name: Ethan Chen
Date: May 17, 2020
Class: Data Structures and Algorithms, Bakker
Project: Pandemic

Description: A graphical and text-based version of the Pandemic board game
Note: All methods have more clear descriptions of their variables in their respective classes
Note: Each Graphics file has been preceded by ZZZ in order to move it to the bottom of the list in the src file.
      The main graphics file has ben preceded by ZZZZ so it is at the very bottom.

Runnable Game Classes:
    Runner - Text-based console game
    ZZZZPandemic Graphics - Graphics UI game

Model Object Classes:
    City - A City in Pandemic, which is a variable for cards and the player. It contains a name, disease color, disease
            cubes, and link to other cities
    InfectionCard - A card in Pandemic that contains a city, and is pulled at the end of each turn to infect the map
    PlayerCard - A card in Pandemic that can either be a city card, an event card, or an epidemic card. City and event
                 cards are playable and are held in players' hands, while epidemic cards infect the map
    Player - A player in Pandemic that has a name, role, and a specific hand unique to that player. The player is able
             to move around and play cards
    Game - The cumulative model class that combines every object into one.

Data Structure Classes:
    Node - Contains a piece of data and a pointer to a next node
    LinkedList - Links nodes together and provides method to add or take away from that list
    Stack - A LIFO data structure
    Queue - A FIFO data structure


Other Graphics Classes:
    ZZZAirliftScreen - Graphics for airlift event
    ZZZCharterFlightScreen - Graphics for charter flight action
    ZZZDiscardCardsScreen -  Graphics for discard cards when necessary
    ZZZEndGameScreen - Graphics for end of the game
    ZZZEndTurnScreen - Graphics for end of a player's turn (pull infection and player cards)
    ZZZExpandMapScreen - Graphics for expanding the map
    ZZZFindCureScreen - Graphics for find cure action
    ZZZForecastScreen - Graphics for forecast event
    ZZZGovernmentGrantScreen - Graphics for government grant event
    ZZZMoveScreen - Graphics for move action
    ZZZPlayCardScreen - Graphics for play card action
    ZZZResilientPopulationScreen - Graphics for resilient population event
    ZZZRuleBook - Graphics for rule book
    ZZZSettingsScreen - Graphics for settings
    ZZZTitleScreen - Graphics for the title
    ZZZTradeScreen - Graphics for trade action

*** Unfortunately it is extremely difficult to comment out graphics, so the best way to look at the code is with each
    screen side by side

I would include my presentation as part of this submission as well, but unfortunately I do not believe I am able to
submit it to Github, so feel free to look at it again from the shared google folder.

In the meantime, I just want to say thank you Mr. Bakker. This class as well as Digital Logic have been really great
with you these past couple of years. I can't wait to keep on coding, and I must say good luck to you and your future
classes in the future!

Other Files:
Locations.txt - the text file containing the data to create the map of the world with cities
Blue.txt - the text file I used for testing


