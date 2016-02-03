package edu.nyu.cs.pqs;

/**
 * This class represents a player playing the connect four game. It has a type- Human or Computer and an ID player one 
 * or player two. In future the code can be extended to keep track of more stats for the players. 
 * Builder Pattern is used to build a player.
 * A computer player looks ahead a single move and makes that move if it results in a win, otherwise makes a
 * random move.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.nyu.cs.pqs.ConnectFourGameBoard.Chip;

public class ConnectFourPlayer {

  /**
   * Represents the type of the player - Human or Computer.
   * 
   */
  public enum PlayerType {
    HUMAN, COMPUTER;
  }

  private final String name;
  private final PlayerType playerType;
  private final ConnectFourPlayerID playerID;
  private final int numOfGamesPlayed;
  private final int numOfGamesWon;

  /**
   * Builder for construction of Player. The fields name, playertype, playerID are mandatory. Other
   * fields are optional.
   * 
   *
   */
  public static class Builder {
    private String name;
    private PlayerType playerType;
    private ConnectFourPlayerID playerID;
    private int numOfGamesPlayed = 0;
    private int numOfGamesWon = 0;

    public Builder(String name, PlayerType playerType, ConnectFourPlayerID playerID) {
      this.name = name;
      this.playerType = playerType;
      this.playerID = playerID;
    }

    public Builder gamesPlayed(int val) {
      numOfGamesPlayed = val;
      return this;
    }

    public Builder gamesWon(int val) {
      numOfGamesWon = val;
      return this;
    }

    public ConnectFourPlayer build() {
      return new ConnectFourPlayer(this);
    }
  }

  private ConnectFourPlayer(Builder builder) {
    this.name = builder.name;
    this.playerID = builder.playerID;
    this.playerType = builder.playerType;
    this.numOfGamesPlayed = builder.numOfGamesPlayed;
    this.numOfGamesWon = builder.numOfGamesWon;
  }

  public String getName() {
    return name;
  }

  public PlayerType getPlayerType() {
    return playerType;
  }

  public ConnectFourPlayerID getPlayerID() {
    return playerID;
  }

  /**
   * can return a move for a player depending on the current state of game. Currently it only
   * returns a move for a computer player (returns -1 for human players). The function can be
   * extended if the types of players change, extend or strategy for a player changes.
   * 
   * @param gameBoard
   *          -the current logical gameboard
   * @return -the column number for the appropriate move
   */
  public int getMove(ConnectFourGameBoard gameBoard) {
    if (playerType == PlayerType.COMPUTER) {
      return getColumnComputerPlayer(gameBoard);
    }
    return -1;
  }

  /*
   * returns either the winning column number or if a look-ahead win is not possible then random
   * column number.
   */
  private int getColumnComputerPlayer(ConnectFourGameBoard gameBoard) {
    List<Integer> list = new ArrayList<Integer>();

    for (int column = 0; column < gameBoard.getNumOfColumns(); column++) {
      if (gameBoard.isSpotAvailable(column)) {
        list.add(column);
      }
    }
    if (list.isEmpty()) {
      throw new IllegalStateException("no columns available");
    }
    for (int column = 0; column < list.size(); column++) {
      if (gameBoard.checkIfWinningMove(column, this.getColor())) {
        return column;
      }
    }
    Collections.shuffle(list);
    return list.get(0);
  }

  /**
   * Returns the chip color of the player depending on the Player ID. Player one gets RED and Player
   * Two gets BLUE.
   * 
   * @return Chip{@link Chip}
   */
  public Chip getColor() {
    if (getPlayerID() == ConnectFourPlayerID.ONE) {
      return Chip.RED;
    } else {
      return Chip.BLUE;
    }
  }

  public int getNumOfGamesPlayed() {
    return numOfGamesPlayed;
  }

  public int getNumOfGamesWon() {
    return numOfGamesWon;
  }

  /**
   * Two players are considered equal if their names are same (regardless of the case). The API
   * assumes that the name of the computer will always be "Computer"
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof ConnectFourPlayer)) {
      return false;
    }
    ConnectFourPlayer other = (ConnectFourPlayer) obj;
    return (this.name.equalsIgnoreCase(other.getName()));
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 17;
    result = prime * result + ((name == null) ? 0 : name.toLowerCase().hashCode());
    return result;
  }

  @Override
  public String toString() {
    return name + ", PlayerType:" + playerType + ", ID " + playerID;
  }
}