package edu.nyu.cs.pqs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.nyu.cs.pqs.ConnectFourGameBoard.Chip;

public class ConnectFourGameBoardTest {
  ConnectFourGameBoard board;

  @Before
  public void initialize() {
    board = new ConnectFourGameBoard(6, 7, 4);
    board.setGameBoard();
  }

  @Test
  public void testSetGameBoard() {
    assertTrue(board.isSpotAvailable(4));
    assertFalse(board.isBoardFull());
    assertEquals(5, board.getNextRowAvailableToFill(0));
    assertEquals(Chip.EMPTY, board.getChipAt(2, 5));
    assertEquals(Chip.EMPTY, board.getChipAt(5, 0));
    assertEquals(7, board.getNumOfColumns());
  }

  @Test
  public void testResetGameBoard() {
    board.insertChipAt(1, Chip.BLUE);
    board.insertChipAt(3, Chip.RED);
    assertEquals(4, board.getNextRowAvailableToFill(1));

    board.resetGameBoard();
    assertEquals(Chip.EMPTY, board.getChipAt(5, 1));
    assertEquals(5, board.getNextRowAvailableToFill(3));
  }

  @Test
  public void testInsertChipAt() {
    board.insertChipAt(1, Chip.RED);
    assertEquals(Chip.RED, board.getChipAt(5, 1));
    assertEquals(Chip.EMPTY, board.getChipAt(4, 1));
    assertEquals(4, board.getNextRowAvailableToFill(1));

    board.insertChipAt(1, Chip.RED);
    board.insertChipAt(1, Chip.RED);
    board.insertChipAt(1, Chip.RED);
    board.insertChipAt(1, Chip.BLUE);
    board.insertChipAt(1, Chip.RED);

    assertFalse(board.insertChipAt(1, Chip.RED));

  }

  @Test
  public void testIsBoardFull() {
    assertFalse(board.isBoardFull());
    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < 7; j++) {
        board.insertChipAt(j, Chip.RED);
      }
    }
    assertTrue(board.isBoardFull());
  }

  @Test
  public void testCheckHorizontalWinningMove() {
    board.insertChipAt(2, Chip.BLUE);
    board.insertChipAt(3, Chip.BLUE);
    board.insertChipAt(4, Chip.BLUE);
    board.insertChipAt(5, Chip.BLUE);

    assertTrue(board.isWinningMove(5, Chip.BLUE));
    assertFalse(board.isWinningMove(5, Chip.RED));
  }

  @Test
  public void testCheckVerticalWinningMove() {
    board.insertChipAt(2, Chip.RED);
    board.insertChipAt(2, Chip.BLUE);
    board.insertChipAt(2, Chip.BLUE);
    board.insertChipAt(2, Chip.BLUE);
    board.insertChipAt(2, Chip.BLUE);

    assertTrue(board.isWinningMove(2, Chip.BLUE));
    assertFalse(board.isWinningMove(2, Chip.RED));
  }

  @Test
  public void testCheckForwardDiagonalWinningMove() {
    board.insertChipAt(0, Chip.RED);
    board.insertChipAt(0, Chip.BLUE);
    board.insertChipAt(0, Chip.BLUE);
    board.insertChipAt(0, Chip.RED);
    board.insertChipAt(1, Chip.RED);
    board.insertChipAt(1, Chip.BLUE);
    board.insertChipAt(1, Chip.RED);
    board.insertChipAt(2, Chip.RED);
    board.insertChipAt(2, Chip.RED);
    board.insertChipAt(3, Chip.RED);

    assertTrue(board.isWinningMove(1, Chip.RED));
    assertFalse(board.isWinningMove(0, Chip.BLUE));
  }

  @Test
  public void testCheckBackwardDiagonalWinningMove() {
    board.insertChipAt(2, Chip.RED);
    board.insertChipAt(2, Chip.RED);
    board.insertChipAt(3, Chip.BLUE);
    board.insertChipAt(3, Chip.BLUE);
    board.insertChipAt(3, Chip.RED);
    board.insertChipAt(4, Chip.RED);
    board.insertChipAt(4, Chip.BLUE);
    board.insertChipAt(4, Chip.BLUE);
    board.insertChipAt(4, Chip.RED);
    board.insertChipAt(5, Chip.RED);
    board.insertChipAt(5, Chip.RED);
    board.insertChipAt(5, Chip.BLUE);
    board.insertChipAt(5, Chip.BLUE);
    board.insertChipAt(5, Chip.RED);

    assertTrue(board.isWinningMove(5, Chip.RED));
    assertTrue(board.isWinningMove(3, Chip.RED));
    assertFalse(board.isWinningMove(2, Chip.BLUE));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsWinningMoveException() {
    board.isWinningMove(1, Chip.BLUE);
  }

  @Test
  public void testCheckIfWinningMove() {
    board.insertChipAt(2, Chip.BLUE);
    board.insertChipAt(3, Chip.BLUE);
    board.insertChipAt(4, Chip.BLUE);

    assertTrue(board.checkIfWinningMove(5, Chip.BLUE));
    assertFalse(board.checkIfWinningMove(2, Chip.BLUE));
    assertEquals(Chip.EMPTY, board.getChipAt(5, 5));
  }

  @Test
  public void testCheckIfWinningMove_columnFull() {
    board.insertChipAt(2, Chip.BLUE);
    board.insertChipAt(2, Chip.RED);
    board.insertChipAt(2, Chip.BLUE);
    board.insertChipAt(2, Chip.BLUE);
    board.insertChipAt(2, Chip.RED);
    board.insertChipAt(2, Chip.BLUE);

    assertFalse(board.checkIfWinningMove(2, Chip.BLUE));
  }
}