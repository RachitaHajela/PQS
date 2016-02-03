package edu.nyu.cs.pqs;

/**
 * This class describes the logical game board behind the Connect Four Game.
 *
 */
final class ConnectFourGameBoard {

  /**
   * It defines the type of Chip or Coins that can fill the board.
   *
   */
   enum Chip {
    RED, BLUE, EMPTY;
  }

  private int numOfRows;
  private int numOfColumns;
  private int numToWin;
  private Chip[][] gameBoard;

  // keeps track of the total spots available to fill in the game board.
  private int totalSpotsAvailable;

  // keeps track of the next row that can be filled in a particular column.
  private int nextRowAvailableToFill[];

  ConnectFourGameBoard(int rows, int columns, int numToWin) {
    this.numOfRows = rows;
    this.numOfColumns = columns;
    this.numToWin = numToWin;
    gameBoard = new Chip[rows][columns];
    nextRowAvailableToFill = new int[columns];
  }

  /**
   * utility constructor to make copy of the given board. Useful in testing look-ahead moves for the
   * computer
   * 
   * @param copyFromGameBoard
   *          the board from which copy is made
   */
  ConnectFourGameBoard(ConnectFourGameBoard copyFromGameBoard) {
    this.numOfColumns = copyFromGameBoard.numOfColumns;
    this.numOfRows = copyFromGameBoard.numOfRows;
    this.numToWin = copyFromGameBoard.numToWin;
    this.nextRowAvailableToFill = new int[numOfColumns];
    this.totalSpotsAvailable = copyFromGameBoard.totalSpotsAvailable;
    this.gameBoard = new Chip[numOfRows][numOfColumns];

    for (int col = 0; col < this.numOfColumns; col++) {
      for (int row = 0; row < this.numOfRows; row++) {
        this.gameBoard[row][col] = copyFromGameBoard.getChipAt(row, col);
      }
      nextRowAvailableToFill[col] = copyFromGameBoard.nextRowAvailableToFill[col];
    }
  }

  /**
   * initializes the board
   */
  void setGameBoard() {
    for (int j = 0; j < numOfColumns; j++) {
      for (int i = 0; i < numOfRows; i++) {
        gameBoard[i][j] = Chip.EMPTY;
      }
      nextRowAvailableToFill[j] = numOfRows - 1;
      totalSpotsAvailable = numOfColumns * numOfRows;
    }
  }

  /**
   * resets all the values on the board.
   */
  void resetGameBoard() {
    setGameBoard();
  }

  /**
   * gives the row number of the next spot that can be filled in a particular column.
   * 
   * @param column
   *          the column foe which row number of next available spot is required.
   * @return row number
   */
  int getNextRowAvailableToFill(int column) {
    return nextRowAvailableToFill[column];
  }

  /**
   * checks if a spot is available to be filled in a particular column
   * 
   * @param column
   *          the column for which spot is checked
   * @return true is spot is available otherwise false
   */
  boolean isSpotAvailable(int column) {
    if (getNextRowAvailableToFill(column) >= 0)
      return true;
    else
      return false;
  }

  /**
   * returns the Chip type (Red, Blue or empty) at the intersection of the given row and column
   * 
   * @param row
   *          the row
   * @param column
   *          the column
   * @return RED, BLUE or EMPTY
   */
  Chip getChipAt(int row, int column) {
    return gameBoard[row][column];
  }

  /**
   * checks if the board is full and no spot is available to fill
   * 
   * @return true if there are no spots available to be filled , false otherwise
   */
  boolean isBoardFull() {
    return (totalSpotsAvailable == 0);
  }

  /**
   * inserts the Chip at a particular spot on te board.
   * 
   * @param column
   *          the column in which chip is dropped.
   * @param color
   *          The type of chip - RED or BlUE
   * @return true if Chip was successfully inserted, false otherwise
   */
  boolean insertChipAt(int column, Chip color) {
    if (isSpotAvailable(column)) {
      int row = getNextRowAvailableToFill(column);
      gameBoard[row][column] = color;
      nextRowAvailableToFill[column]--;
      totalSpotsAvailable--;
      return true;
    } else {
      return false;
    }
  }

  /**
   * checks if Chip dropped in the particular column results in winning situation. Calls 4 other
   * functions to check the winning combination in the row (checkHorizontal), in the column
   * (checkVertical), two diagonals (checkForwardDiagonal and checkBackwardDiagonal)
   * 
   * @param column
   *          the column
   * @param color
   *          Chip Type
   * @return true if results in winning situation, false otherwise
   * 
   * @throws IllegalArgumentException
   *           if the column was empty
   */
  boolean isWinningMove(int column, Chip color) throws IllegalArgumentException {
    int row = getNextRowAvailableToFill(column);
    row++;
    if (row >= numOfRows) {
      throw new IllegalArgumentException("column was empty, no chip dropped");
    }
    if (checkHorizontal(row, color) || checkVertical(column, color)
        || checkForwardDiagonal(row, column, color) || checkBackwardDiagonal(row, column, color)) {
      return true;
    }
    return false;
  }

  private boolean checkHorizontal(int row, Chip color) {
    int counter = 0;
    for (int col = 0; col < numOfColumns; col++) {
      if (gameBoard[row][col] == color) {
        counter++;
        if (counter == numToWin) {
          return true;
        }
      } else {
        counter = 0;
      }
    }
    return false;
  }

  private boolean checkVertical(int column, Chip color) {
    int counter = 0;
    for (int row = 0; row < numOfRows; row++) {
      if (gameBoard[row][column] == color) {
        counter++;
        if (counter == numToWin) {
          return true;
        }
      } else {
        counter = 0;
      }
    }
    return false;
  }

  private boolean checkForwardDiagonal(int row, int column, Chip color) {
    int counter = 0;
    int distance = Math.min(row, column);
    int rowIndex = row - distance;
    int columnIndex = column - distance;

    for (; rowIndex < numOfRows && rowIndex <= row + 3 && columnIndex < numOfColumns
        && columnIndex <= column + 3; rowIndex++, columnIndex++) {
      if (gameBoard[rowIndex][columnIndex] == color) {
        counter++;
        if (counter >= numToWin) {
          return true;
        }
      } else {
        counter = 0;
      }
    }
    return false;
  }

  private boolean checkBackwardDiagonal(int row, int column, Chip color) {
    int counter = 0;
    int distance = Math.min(numOfRows - row - 1, column);
    int rowIndex = row + distance;
    int columnIndex = column - distance;

    for (; rowIndex >= 0 && rowIndex >= row - 3 && columnIndex < numOfColumns
        && columnIndex <= column + 3; rowIndex--, columnIndex++) {
      if (gameBoard[rowIndex][columnIndex] == color) {
        counter++;
        if (counter >= numToWin) {
          return true;
        }
      } else {
        counter = 0;
      }
    }
    return false;
  }

  int getNumOfColumns() {
    return numOfColumns;
  }

  /**
   * Checks if dropping in the column will lead to winning situation. Does not actually drop in the
   * column. Provides look ahead functionality.
   * 
   * @param column
   *          the column
   * @param color
   *          color Chip type
   * @return true if move can lead to winning situation, false otherwise.
   */
  boolean checkIfWinningMove(int column, Chip color) {
    ConnectFourGameBoard gameBoardCopy = new ConnectFourGameBoard(this);
    if (gameBoardCopy.insertChipAt(column, color)) {
      return gameBoardCopy.isWinningMove(column, color);
    } else {
      return false;
    }
  }
}