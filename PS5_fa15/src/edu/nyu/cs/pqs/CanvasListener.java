package edu.nyu.cs.pqs;

import java.awt.Color;

public interface CanvasListener {

  /**
   * Cleans the canvas. The drawing color is set to default: BLACK
   */
  public void cleanCanvas();

  /**
   * saves coordinates of the point where mouse is pressed.
   * 
   * @param x
   *          -the x coordinate
   * @param y
   *          -the y coordinate
   */
  public void mousePressed(int x, int y);

  /**
   * draws the line wherever mouse is dragged
   * 
   * @param x
   *          -the x coordinate
   * @param y
   *          -the Y coordinate
   */
  public void mouseDragged(int x, int y);

  /**
   * changes the color of the line
   * 
   * @param color
   *          -the color to be used in painting
   */
  public void colorChange(Color color);

}
