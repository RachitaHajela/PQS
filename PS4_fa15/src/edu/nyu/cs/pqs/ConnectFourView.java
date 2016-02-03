package edu.nyu.cs.pqs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import edu.nyu.cs.pqs.ConnectFourPlayer.PlayerType;

/**
 * 
 * This is a simple view implementation of the Connect Four Game. It implements the
 * ConnectFourListenerInterface. The view consists of a Game Board, Buttons to drop a chip in a
 * particular column of the board, an information area which displays all the information relevant
 * to the present game, a reset button and a quit button.
 * 
 * This view can be extended in the future to display more details of the player's and game
 * statistics.
 * 
 * Multiple views can be created of the same game and they will display the same information and
 * work the same.
 *
 */
public final class ConnectFourView implements ConnectFourListener {

  private ConnectFourPlayer player1;
  private ConnectFourPlayer player2;
  private ConnectFourModel model;
  private int numOfRows;
  private int numOfColumns;

  private JFrame frame;
  private JPanel dropPanel;
  private JPanel gamePanel;
  private JPanel infoPanel;
  private JPanel[][] board;
  private JTextArea infoArea;
  private JPanel optionsPanel;
  private JButton quitButton;
  private JButton resetButton;

  public ConnectFourView(ConnectFourPlayer player1, ConnectFourPlayer player2,
      ConnectFourModel model) {
    this.player1 = player1;
    this.player2 = player2;
    this.model = model;
    this.numOfRows = model.getNumOfRows();
    this.numOfColumns = model.getNumOfColumns();
    model.addListener(this);
    setUpUI();
  }

  private void setUpUI() {

    frame = new JFrame("Four Connect");

    setUpDropPanel();

    setUpGamePanel();

    setUpInfoPanel();

    frame.add(dropPanel, BorderLayout.NORTH);
    frame.add(gamePanel, BorderLayout.CENTER);
    frame.getContentPane().add(infoPanel, BorderLayout.SOUTH);
    frame.setSize(500, 500);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }

  private void setUpInfoPanel() {

    infoArea = new JTextArea();
    infoArea.setSize(300, 100);
    infoPanel = new JPanel(new BorderLayout());
    quitButton = new JButton("Quit");
    resetButton = new JButton("Reset");
    optionsPanel = new JPanel(new BorderLayout());

    resetButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        resetButtonPressed();
      }
    });

    quitButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        quitButtonPressed();
      }
    });

    optionsPanel.add(resetButton, BorderLayout.NORTH);
    optionsPanel.add(quitButton, BorderLayout.SOUTH);
    infoPanel.add(new JScrollPane(infoArea), BorderLayout.CENTER);
    infoPanel.add(optionsPanel, BorderLayout.EAST);
    infoPanel.setPreferredSize(new Dimension(500, 75));
    ;
  }

  private void setUpGamePanel() {

    board = new JPanel[numOfRows][numOfColumns];
    gamePanel = new JPanel(new GridLayout(numOfRows, numOfColumns, 3, 3));

    for (int i = 0; i < numOfRows; i++) {
      for (int j = 0; j < numOfColumns; j++) {
        JPanel slot = new JPanel();
        slot.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        slot.setBackground(Color.white);
        board[i][j] = slot;
        gamePanel.add(board[i][j]);
      }
    }
  }

  private void setUpDropPanel() {

    dropPanel = new JPanel(new GridLayout(1, numOfColumns, 3, 3));

    for (int i = 0; i < numOfColumns; i++) {
      final int column;
      column = i;
      JButton button = new JButton(" " + (i + 1));
      button.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
          dropButtonPressed(column);
        }
      });
      dropPanel.add(button, BorderLayout.EAST);
    }
  }

  private void dropButtonPressed(int column) {
    model.manageMove(column);
  }

  private void quitButtonPressed() {
    model.quit();
  }

  private void resetButtonPressed() {
    model.reset();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void gameStart(ConnectFourPlayerID playerID) {
    infoArea.append("Game Starts! \n");
    if (player1.getPlayerID() == playerID) {
      infoArea.append(player1.getName() + "'s turn\n");
    } else {
      infoArea.append(player2.getName() + "'s turn\n");
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void playerTurn(ConnectFourPlayerID playerID, ConnectFourGameBoard gameBoard) {
    if (player1.getPlayerID() == playerID) {
      infoArea.append(player1.getName() + "'s turn\n");
    } else if (player2.getPlayerID() == playerID && player2.getPlayerType() == PlayerType.COMPUTER) {
      infoArea.append(player2.getName() + "'s turn\n");
      int column = player2.getMove(gameBoard);
      model.manageMove(column);
    } else {
      infoArea.append(player2.getName() + "'s turn\n");
    }

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void playerMove(int row, int column, ConnectFourPlayerID playerID) {
    if (player1.getPlayerID() == playerID) {
      board[row][column].setBackground(Color.RED);
    } else {
      board[row][column].setBackground(Color.BLUE);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void quit() {
    frame.dispose();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void reset() {
    frame.dispose();
    setUpUI();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void columnFull() {
    infoArea.append("Column is full, try another column\n");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void win(ConnectFourPlayerID playerID) {
    if (player1.getPlayerID() == playerID) {
      infoArea.append("Game Over! " + player1.getName() + " Won\n");
    } else {
      infoArea.append("Game Over! " + player2.getName() + " Won\n");
    }
    dropPanel.setVisible(false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void gameDraw() {
    infoArea.append("Game Over! Draw\n");
    dropPanel.setVisible(false);
  }
}