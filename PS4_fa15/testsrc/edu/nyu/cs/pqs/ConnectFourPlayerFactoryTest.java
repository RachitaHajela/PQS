package edu.nyu.cs.pqs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import edu.nyu.cs.pqs.ConnectFourGameBoard.Chip;
import edu.nyu.cs.pqs.ConnectFourPlayer.PlayerType;

public class ConnectFourPlayerFactoryTest {

  ConnectFourPlayerFactory factory1;
  ConnectFourPlayerFactory factory2;

  @Before
  public void setupAndTestConstructor() {
    factory1 = ConnectFourPlayerFactory.getFactoryInstance();
    factory2 = ConnectFourPlayerFactory.getFactoryInstance();
    assertTrue(factory1.equals(factory2));
  }

  @Test
  public void testGetHumanPlayer() {
    ConnectFourPlayer player = factory1.getHumanPlayer("John", ConnectFourPlayerID.ONE);
    assertEquals("John", player.getName());
    assertEquals(PlayerType.HUMAN, player.getPlayerType());
    assertEquals(Chip.RED, player.getColor());
    assertEquals(ConnectFourPlayerID.ONE, player.getPlayerID());   
  }

  @Test
  public void testGetComputerPlayer() {
    ConnectFourPlayer player = factory2.getComputerPlayer();
    assertEquals("Computer", player.getName());
    assertEquals(PlayerType.COMPUTER, player.getPlayerType());
    assertEquals(Chip.BLUE, player.getColor());
    assertEquals(ConnectFourPlayerID.TWO, player.getPlayerID());
  }
}
