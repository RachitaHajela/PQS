package edu.nyu.cs.pqs;

import edu.nyu.cs.pqs.ConnectFourPlayer.PlayerType;

/**
 * Factory to generate the ConnectFourPlayer Objects. Factory Pattern is used to generate Human or
 * Computer Players. This class can be extended in future to generate players from existing players
 * or to generate players containing statistics.
 * 
 * Known disadvantage- Players can still be generated directly from the builder. Advantage - Ease of
 * getting player objects. No arguments needed to be specified for Computer player. Less number of
 * arguments than builder in generating human player
 * 
 * The class itself follows Singleton Factory pattern. Only one instance of Factory is required.
 */
public class ConnectFourPlayerFactory {

  private static ConnectFourPlayerFactory INSTANCE;
  private static Object lock = new Object();

  private ConnectFourPlayerFactory() {
  }

  public static ConnectFourPlayerFactory getFactoryInstance() {
    synchronized (lock) {
      if (INSTANCE == null) {
        INSTANCE = new ConnectFourPlayerFactory();
      }
      return INSTANCE;
    }
  }

  /**
   * generates Human Player
   * 
   * @param name
   *          the name of the player
   * @param id
   *          the playrID
   * @return human player
   */
  public ConnectFourPlayer getHumanPlayer(String name, ConnectFourPlayerID id) {
    return new ConnectFourPlayer.Builder(name, PlayerType.HUMAN, id).build();
  }

  /**
   * generates Computer Player. The Computer player is always given ID Two
   * 
   * @return Computer Player
   */
  public ConnectFourPlayer getComputerPlayer() {
    return new ConnectFourPlayer.Builder("Computer", PlayerType.COMPUTER, ConnectFourPlayerID.TWO)
        .build();
  }
}