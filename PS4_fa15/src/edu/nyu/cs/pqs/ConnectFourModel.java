package edu.nyu.cs.pqs;

import java.util.ArrayList;
import java.util.List;

import edu.nyu.cs.pqs.ConnectFourGameBoard.Chip;

/**
 * this class manages and controls the flow of the game via ConnectFourGameBoard and
 * ConnectFourPlayer. The game can either be btw two Humans or Human vs. Computer. The class also
 * fires events appropriate for the listeners on this class.
 * 
 */
final class ConnectFourModel {

  private ConnectFourGameBoard gameBoard;

  private List<ConnectFourListener> listeners;

  private ConnectFourPlayer Player1;
  private ConnectFourPlayer Player2;
  private ConnectFourPlayer currentPlayer;
  private int numOfRows;
  private int numOfColumns;

  /**
   * Constructor for creating a game model. Model throws IllegalArgumentException if both players
   * have same ID or numToWin is greater than the numOfColumns.
   * 
   */
  public ConnectFourModel(int rows, int columns, int numToWin, ConnectFourPlayer player1,
      ConnectFourPlayer player2) {
    if (numToWin > columns) {
      throw new IllegalArgumentException(
          "number to win is greater than the number of columns in the game");
    }
    if (player1.getPlayerID().equals(player2.getPlayerID()) || player1.equals(player2)) {
      throw new IllegalArgumentException("Both players are same or have same ID");
    }
    gameBoard = new ConnectFourGameBoard(rows, columns, numToWin);
    listeners = new ArrayList<ConnectFourListener>();
    this.Player1 = player1;
    this.Player2 = player2;
    currentPlayer = player1;
    this.numOfRows = rows;
    this.numOfColumns = columns;
  }

  /**
   * Adds a ConnectFourListener on this model
   * 
   * @param listener
   *          the listener
   */
  public void addListener(ConnectFourListener listener) {
    listeners.add(listener);
  }

  /**
   * initializes the values of the ConnectFourGameBoard object and Starts the game. First player's
   * turn is announced.
   */
  public void startGame() {
    gameBoard.setGameBoard();
    fireGameStartEvent();
  }

  /**
   * Manage dropping a chip/coin in the column
   * 
   * @param column
   *          the column in which chip is dropped.
   * @return true if the move is successful, false otherwise
   */
  public boolean manageMove(int column) {
    if (moveSuccessful(column)) {
      if (!gameOver(column, currentPlayer.getColor())) {
        changePlayerTurn();
      }
      return true;
    } else {
      return false;
    }
  }

  /*
   * toggles the player's turns and notifies the players.
   */

  private void changePlayerTurn() {
    if (currentPlayer.equals(Player1)) {
      currentPlayer = Player2;
    } else {
      currentPlayer = Player1;
    }
    ConnectFourGameBoard gameBoardCopy = new ConnectFourGameBoard(this.gameBoard);
    firePlayerTurnEvent(currentPlayer.getPlayerID(), gameBoardCopy);
  }

  private boolean gameOver(int column, Chip chip) {
    if (gameBoard.isWinningMove(column, currentPlayer.getColor())) {
      fireGameEndedInWinEvent(currentPlayer.getPlayerID());
      return true;
    }
    if (gameBoard.isBoardFull()) {
      fireGameEndedInDrawEvent();
      return true;
    }
    return false;
  }

  private boolean moveSuccessful(int column) {
    if (gameBoard.isSpotAvailable(column)) {
      gameBoard.insertChipAt(column, currentPlayer.getColor());
      int row = gameBoard.getNextRowAvailableToFill(column);
      row++;
      firePlayerMoveEvent(row, column, currentPlayer.getPlayerID());
      return true;
    } else {
      fireColumnFullEvent(currentPlayer.getPlayerID());
      return false;
    }
  }

  /**
   * Player has quit the game. It calls the function to kill the UI and end the game.
   */
  public void quit() {
    fireGameQuitEvent();
  }

  /**
   * Resets the game, with the same players but new game.
   */
  public void reset() {
    currentPlayer = Player1;
    fireGameResetEvent();
    startGame();
  }

  private void fireGameResetEvent() {
    for (ConnectFourListener listener : listeners) {
      listener.reset();
    }
  }

  private void fireGameQuitEvent() {
    for (ConnectFourListener listener : listeners) {
      listener.quit();
    }

  }

  private void fireGameStartEvent() {
    for (ConnectFourListener listener : listeners) {
      listener.gameStart(currentPlayer.getPlayerID());
    }
  }

  private void fireGameEndedInDrawEvent() {
    for (ConnectFourListener listener : listeners) {
      listener.gameDraw();
    }
  }

  private void fireGameEndedInWinEvent(ConnectFourPlayerID playerID) {
    for (ConnectFourListener listener : listeners) {
      listener.win(playerID);
    }
  }

  private void fireColumnFullEvent(ConnectFourPlayerID playerID) {
    for (ConnectFourListener listener : listeners) {
      listener.columnFull();
    }
  }

  private void firePlayerTurnEvent(ConnectFourPlayerID playerID, ConnectFourGameBoard gameBoard2) {
    for (ConnectFourListener listener : listeners) {
      listener.playerTurn(playerID, gameBoard);
    }
  }

  private void firePlayerMoveEvent(int row, int column, ConnectFourPlayerID playerID) {
    for (ConnectFourListener listener : listeners) {
      listener.playerMove(row, column, playerID);
    }
  }

  public int getNumOfRows() {
    return numOfRows;
  }

  public int getNumOfColumns() {
    return numOfColumns;
  }
}