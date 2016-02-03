package edu.nyu.cs.pqs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * This class shows a starting menu to the users to select the game mode, initializes the players
 * and starts the game.
 * 
 *
 */
public final class ConnectFourApp {

  private ConnectFourModel model;
  private ConnectFourPlayer player1;
  private ConnectFourPlayer player2;
  private ConnectFourPlayerFactory factory;
  private ConnectFourView view1;

  private JFrame menuFrame;
  private JTextField player1Name;
  private JTextField player2Name;
  private JPanel player1Panel;
  private JPanel player2Panel;
  private JButton enter1;
  private JButton enter2;
  private JButton onePlayer;
  private JButton twoPlayer;

  public static void main(String[] args) {
    new ConnectFourApp().startMenu();
  }

  private void startMenu() {

    menuFrame = new JFrame("Menu");

    createPlayerPanels();

    JPanel optionPanel = createOptionsPanel();

    menuFrame.getContentPane().add(optionPanel);

    menuFrame.setVisible(true);
    menuFrame.setSize(400, 200);
    menuFrame.setLocationRelativeTo(null);
    menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  private JPanel createOptionsPanel() {

    JPanel optionPanel = new JPanel();
    optionPanel.setLayout(new GridLayout(5, 1, 4, 4));

    JLabel menuLabel = new JLabel(" Select Mode ", SwingConstants.CENTER);
    menuLabel.setBorder(BorderFactory.createLineBorder(Color.black));
    menuLabel.setForeground(Color.BLUE);

    onePlayer = new JButton("One Player");
    twoPlayer = new JButton("Two Player");

    optionPanel.add(menuLabel, BorderLayout.NORTH);
    optionPanel.add(onePlayer, BorderLayout.NORTH);
    optionPanel.add(twoPlayer, BorderLayout.NORTH);

    onePlayer.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        player1Panel.setVisible(true);
        onePlayer.setEnabled(false);
        twoPlayer.setEnabled(false);
      }
    });

    twoPlayer.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        player1Panel.setVisible(true);
        player2Panel.setVisible(true);
        enter1.setEnabled(false);
        onePlayer.setEnabled(false);
        twoPlayer.setEnabled(false);
      }
    });

    optionPanel.add(player1Panel);
    optionPanel.add(player2Panel);
    return optionPanel;
  }

  private void createPlayerPanels() {
    player1Panel = new JPanel(new BorderLayout());
    player2Panel = new JPanel(new BorderLayout());
    player1Name = new JTextField("Enter Player1 Name");
    player2Name = new JTextField("Enter Player2 Name");
    enter1 = new JButton("Enter");
    enter2 = new JButton("Enter");

    player1Panel.add(player1Name, BorderLayout.CENTER);
    player1Panel.add(enter1, BorderLayout.EAST);
    player1Panel.setVisible(false);
    enter1.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        startOnePlayerGame();
      }
    });

    player2Panel.add(player2Name, BorderLayout.CENTER);
    player2Panel.add(enter2, BorderLayout.EAST);
    player2Panel.setVisible(false);
    enter2.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        startTwoPlayerGame();
      }
    });
  }

  private void startTwoPlayerGame() {
    String name1 = player1Name.getText();
    String name2 = player2Name.getText();
    factory = ConnectFourPlayerFactory.getFactoryInstance();
    player1 = factory.getHumanPlayer(name1, ConnectFourPlayerID.ONE);
    player2 = factory.getHumanPlayer(name2, ConnectFourPlayerID.TWO);

    model = new ConnectFourModel(6, 7, 4, player1, player2);
    view1 = new ConnectFourView(player1, player2, model);

    menuFrame.dispose();
    model.startGame();
  }

  private void startOnePlayerGame() {
    String name1 = player1Name.getText();
    factory = ConnectFourPlayerFactory.getFactoryInstance();
    player1 = factory.getHumanPlayer(name1, ConnectFourPlayerID.ONE);
    player2 = factory.getComputerPlayer();

    model = new ConnectFourModel(6, 7, 4, player1, player2);
    view1 = new ConnectFourView(player1, player2, model);

    menuFrame.dispose();
    model.startGame();
  }
}