package edu.nyu.cs.pqs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.nyu.cs.pqs.ConnectFourGameBoard.Chip;
import edu.nyu.cs.pqs.ConnectFourPlayer.PlayerType;

public class ConnectFourPlayerTest {

  ConnectFourPlayer player1;
  ConnectFourPlayer player2;
  ConnectFourPlayer player3;

  @Before
  public void setUp() {
    player1 =
        new ConnectFourPlayer.Builder("John", PlayerType.HUMAN, ConnectFourPlayerID.ONE).build();
    player2 =
        new ConnectFourPlayer.Builder("john", PlayerType.HUMAN, ConnectFourPlayerID.TWO)
            .gamesPlayed(2).gamesWon(1).build();
    player3 =
        new ConnectFourPlayer.Builder("Computer", PlayerType.COMPUTER, ConnectFourPlayerID.TWO)
            .build();
  }

  @Test
  public void testAllGetterMethods() {
    assertEquals("John", player1.getName());
    assertEquals(PlayerType.HUMAN, player1.getPlayerType());
    assertEquals(ConnectFourPlayerID.ONE, player1.getPlayerID());
    assertEquals(2, player2.getNumOfGamesPlayed());
    assertEquals(0, player1.getNumOfGamesPlayed());
    assertEquals(1, player2.getNumOfGamesWon());
    assertEquals(Chip.BLUE, player3.getColor());
    assertEquals(Chip.RED, player1.getColor());
  }

  @Test
  public void testGetMove_computerPlayer() {
    ConnectFourGameBoard board = new ConnectFourGameBoard(6, 7, 4);
    board.insertChipAt(1, Chip.BLUE);
    board.insertChipAt(3, Chip.BLUE);

    assertTrue(player3.getMove(board) != -1);

    board.insertChipAt(2, Chip.BLUE);
    assertTrue(player3.getMove(board) == 0);
  }

  @Test
  public void testGetMove_humanPlayer() {
    ConnectFourGameBoard board = new ConnectFourGameBoard(6, 7, 4);
    board.insertChipAt(1, Chip.BLUE);
    board.insertChipAt(3, Chip.BLUE);

    assertTrue(player1.getMove(board) == -1);
  }

  @Test
  public void testEqualsAndHashcode() {
    assertTrue(player1.equals(player2));
    assertFalse(player1.equals(player3));
    assertFalse(player1.equals(null));
    assertTrue(player1.equals(player1));
    assertEquals(player1.hashCode(), player2.hashCode());
    assertFalse(player1.hashCode() == player3.hashCode());
  }
}