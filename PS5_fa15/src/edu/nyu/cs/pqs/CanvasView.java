package edu.nyu.cs.pqs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The view consists of a drawing canvas, 5 colors and a clean button. User can create any number of
 * views. Note: the size of the drawing area remains the same even if the window is resized.
 * 
 * The code for drawing has been inspired from :
 * "http://www.ssaurel.com/blog/learn-how-to-make-a-swing-painting-and-drawing-application/"
 * 
 *
 */
public class CanvasView implements CanvasListener {

  private CanvasModel model;
  private JFrame frame;
  private JPanel optionsPanel;
  private PaintPanel canvas;
  private JButton redButton;
  private JButton blackButton;
  private JButton greenButton;
  private JButton blueButton;
  private JButton magentaButton;
  private JButton clearButton;

  CanvasView(CanvasModel model) {
    this.model = model;
    model.addListener(this);
  }

  /**
   * initializes all the panels and buttons.
   */
  public void setUp() {

    frame = new JFrame("Canvas");

    redButton = new JButton();
    redButton.setBackground(Color.RED);
    blackButton = new JButton();
    blackButton.setBackground(Color.BLACK);
    greenButton = new JButton();
    greenButton.setBackground(Color.GREEN);
    blueButton = new JButton();
    blueButton.setBackground(Color.BLUE);
    magentaButton = new JButton();
    magentaButton.setBackground(Color.MAGENTA);
    clearButton = new JButton("Clean");

    addListenerOnButtons();

    optionsPanel = new JPanel();

    optionsPanel.add(redButton);
    optionsPanel.add(blackButton);
    optionsPanel.add(blueButton);
    optionsPanel.add(greenButton);
    optionsPanel.add(magentaButton);
    optionsPanel.add(clearButton);

    canvas = new PaintPanel();

    frame.add(canvas, BorderLayout.CENTER);
    frame.getContentPane().add(optionsPanel, BorderLayout.SOUTH);

    frame.setSize(500, 500);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);

  }

  private void addListenerOnButtons() {

    clearButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        model.cleanCanvas();

      }
    });

    redButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        model.changeColor(Color.RED);

      }
    });

    blackButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        model.changeColor(Color.BLACK);
        ;

      }
    });

    blueButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        model.changeColor(Color.BLUE);
        ;

      }
    });

    greenButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        model.changeColor(Color.GREEN);
        ;

      }
    });

    magentaButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        model.changeColor(Color.MAGENTA);
      }
    });
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void cleanCanvas() {
    canvas.clear();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void mousePressed(int x, int y) {
    canvas.mouseIsPressed(x, y);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void mouseDragged(int x, int y) {
    canvas.mouseIsDragged(x, y);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void colorChange(Color color) {
    canvas.setColor(color);
  }

  /**
   * 
   * This class creates the Canvas and draws the lines on the GUI
   *
   */
  private class PaintPanel extends JPanel {

    private static final long serialVersionUID = 7156782703884961572L;
    private Image image;
    private Graphics2D graphics;

    private int currentX, currentY, oldX, oldY;

    PaintPanel() {
      setDoubleBuffered(false);

      addMouseListener(new MouseAdapter() {
        public void mousePressed(MouseEvent e) {
          model.mousePressed(e.getX(), e.getY());
        }
      });

      addMouseMotionListener(new MouseMotionAdapter() {
        public void mouseDragged(MouseEvent e) {
          model.mouseDragged(e.getX(), e.getY());
        }
      });
    }

    protected void paintComponent(Graphics g) {
      if (image == null) {
        image = createImage(getSize().width, getSize().height);
        graphics = (Graphics2D) image.getGraphics();
        // enable antialiasing
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setPaint(Color.black);
        clear();
      }
      g.drawImage(image, 0, 0, null);
    }

    private void clear() {
      graphics.setPaint(Color.white);
      graphics.fillRect(0, 0, getSize().width, getSize().height);
      graphics.setPaint(Color.black);
      repaint();
    }

    private void setColor(Color color) {
      graphics.setPaint(color);
    }

    private void mouseIsPressed(int x, int y) {
      oldX = x;
      oldY = y;
    }

    private void mouseIsDragged(int x, int y) {
      currentX = x;
      currentY = y;

      if (graphics != null) {

        graphics.drawLine(oldX, oldY, currentX, currentY);
        repaint();
        oldX = currentX;
        oldY = currentY;
      }
    }
  }
}