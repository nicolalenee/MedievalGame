import java.util.Scanner;
import java.util.Objects;
import java.io.*;

public class MedievalGame {

  /* Instance Variables */
  private Player player;

  /* Main Method */
  public static void main(String[] args) {
    
    Scanner console = new Scanner(System.in);
    MedievalGame game = new MedievalGame();

    game.player = game.start(console);

    game.addDelay(500);
    System.out.println("\nLet's take a quick look at you to make sure you're ready to head out the door.");
    System.out.println(game.player);

    game.addDelay(1000);
    System.out.println("\nWell, you're off to a good start, let's get your game saved so we don't lose it.");
    game.save();

    game.addDelay(2000);
    System.out.println("We just saved your game...");
    System.out.println("Now we are going to try to load your character to make sure the save worked...");

    game.addDelay(1000);
    System.out.println("Deleting character...");
    String charName = game.player.getName();
    game.player = null;

    game.addDelay(1500);
    game.player = game.load(charName, console);
    System.out.println("Loading character...");

    game.addDelay(2000);
    System.out.println("Now let's print out your character again to make sure everything loaded:");

    game.addDelay(500);
    System.out.println(game.player);
  } // End of main

  /* Instance Methods */
  private Player start(Scanner console) {
    // Add start functionality here
    // print the homescreen art
    Art.homeScreen();
    // start prompt
    System.out.println("Welcome to the Medieval Game!");
    System.out.println("Would you like to load a game or start a new game? Enter 'y' to load a game, or 'n' to start a new game. ");
    String answer = console.next().toLowerCase();
    // if the player chooses to load a game:
    if (answer.equals("y")) {
      System.out.println("Oh! I remember you! Let me get your equipment ready");
      player = load(console.next(), console);
      // if the player chooses to create a new game:
    } else if (answer.equals("n")) {
      System.out.println("To get you equipped, enter your name: ");
      String newPlayer = console.next();
      player = new Player(newPlayer);
    } else {
      System.out.print("Sorry, I only accept two answers for entry. A simple 'y' or 'n' will suffice.");
    }
    
    return player;
  } // End of start

  private void save() {
    // Add save functionality here
    String fileName = player.getName() + ".svr";
    try {
      FileOutputStream userSaveFile = new FileOutputStream(fileName);
    ObjectOutputStream playerSaver = new ObjectOutputStream(userSaveFile);
    playerSaver.writeObject(player);
    } catch (IOException e) {
      System.out.println("Something went wrong...");
    }
  
  } // End of save

  private Player load(String playerName, Scanner console) {
    // Add load functionality here
    Player loadedPlayer;
    try{
      FileInputStream userLoadFile = new FileInputStream(playerName + ".svr");
      ObjectInputStream playerLoader = new ObjectInputStream(userLoadFile);
     loadedPlayer = (Player) playerLoader.readObject();

    } catch (IOException | ClassNotFoundException e) {
      addDelay(1500);
      System.out.println("\nTThere was a problem loading your character. For now we'll create a new character with the name: " + playerName);
      addDelay(2000);
      loadedPlayer = new Player(playerName);
    }
    return loadedPlayer;
  } // End of load

  // Adds a delay to the console so it seems like the computer is "thinking"
  // or "responding" like a human, not instantly like a computer.
  private void addDelay(int time) {
    try {
      Thread.sleep(time);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}