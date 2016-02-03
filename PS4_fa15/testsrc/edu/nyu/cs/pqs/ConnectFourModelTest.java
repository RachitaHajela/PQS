package edu.nyu.cs.pqs;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/* This class only tests ConnectFourModel constructor. 
 * All other functions of Model were tested with the GUI when the game was played. 
 * 
 */
public class ConnectFourModelTest {

  ConnectFourPlayerFactory factory;
  ConnectFourPlayer player1;
  ConnectFourPlayer player2;
  ConnectFourModel model;

  @Before
  public void initialize() {
    factory = ConnectFourPlayerFactory.getFactoryInstance();
    player1 = factory.getHumanPlayer("Sheldon", ConnectFourPlayerID.ONE);
    player2 = factory.getHumanPlayer("Amy", ConnectFourPlayerID.TWO);
  }

  @Test
  public void testConstructor() {
    model = new ConnectFourModel(6, 7, 4, player1, player2);
    assertEquals(7, model.getNumOfColumns());
    assertEquals(6, model.getNumOfRows());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNumToWinException() {
    model = new ConnectFourModel(6, 7, 8, player1, player2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithPlayerIDException() {
    ConnectFourPlayer player3 = factory.getComputerPlayer();
    model = new ConnectFourModel(6, 7, 4, player2, player3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithSamePlayerException() {
    ConnectFourPlayer player3 = factory.getHumanPlayer("sheldon", ConnectFourPlayerID.TWO);
    model = new ConnectFourModel(6, 7, 4, player1, player3);
  }
}