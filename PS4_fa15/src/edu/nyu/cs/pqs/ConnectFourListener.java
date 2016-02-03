package edu.nyu.cs.pqs;

/**
 * Interface for Connect Four Game listener. The methods have been created assuming that the
 * View/Listener will be dealing with some sort of UI.
 * 
 *
 */
public interface ConnectFourListener {

  /**
   * display that the game has begun and which player goes first.
   * 
   * @param playerID
   *          - the ID of the player who goes first
   */
  public void gameStart(ConnectFourPlayerID playerID);

  /**
   * Notifies which player's turn it is to play so they take appropriate action.
   * 
   * @param playerID
   *          - the ID of the player whose turn it is to play.
   * @param gameBoard
   *          -the logical gameboard associated with the current game.
   */
  public void playerTurn(ConnectFourPlayerID playerID, ConnectFourGameBoard gameBoard);

  /**
   * display the move made by the player. It sets the slot in the board UI to a particular color
   * depending on which player has made that move.
   * 
   * @param row
   *          - the row in which move was made.
   * @param column
   *          - the column in which move was made
   * @param playerID
   *          - the ID of the player who played.
   */
  public void playerMove(int row, int column, ConnectFourPlayerID playerID);

  /**
   * kills the view.
   */
  public void quit();

  /**
   * resets the UI as a new game begins.
   */
  public void reset();

  /**
   * displays that the particular column is full and player should try some other column.
   */
  public void columnFull();

  /**
   * displays that the game is over and the winner's name.
   * 
   * @param playerID
   *          - the ID of the player who won the game.
   */
  public void win(ConnectFourPlayerID playerID);

  /**
   * displays that the game is over and ended in draw.
   */
  public void gameDraw();
}