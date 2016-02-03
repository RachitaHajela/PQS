package edu.nyu.cs.pqs;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * A canvas painting model. The model has the points where the mouse is pressed and dragged on the
 * canvas. Also which color is picked to draw. It passes the information to the listeners.
 * 
 *
 */
final class CanvasModel {

  private List<CanvasListener> listeners;
  private static CanvasModel instance = new CanvasModel();

  private CanvasModel() {
    listeners = new ArrayList<CanvasListener>();
  }

  /**
   * returns the single instance of the model.
   * 
   * @return the model object
   */
  public static CanvasModel getInstance() {
    return instance;
  }

  /**
   * adds canvaslistener
   * 
   * @param listener
   *          -the listener
   */
  public void addListener(CanvasListener listener) {
    listeners.add(listener);
  }

  /**
   * removes the listener from the list
   * 
   * @param listener
   *          -listener which unsubscribes
   * @return true if removal was successful, false otherwise.
   */
  public boolean removeListener(CanvasListener listener) {
    return listeners.remove(listener);
  }

  /**
   * the location of new mouse press
   * 
   * @param x
   *          -the x coordinate
   * @param y
   *          -the y coordinate
   */
  public void mousePressed(int x, int y) {
    fireMousePressedEvent(x, y);
  }

  /**
   * the point of mouse drag to draw line on canvas wherever mouse is dragged
   * 
   * @param x
   * @param y
   */
  public void mouseDragged(int x, int y) {
    fireMouseDraggedEvent(x, y);
  }

  /**
   * erase everything from the canvas
   */
  public void cleanCanvas() {
    fireCleanCanvasEvent();
  }

  /**
   * change of color
   * 
   * @param color
   *          - the new color
   */
  public void changeColor(Color color) {
    fireColorChangeEvent(color);
  }

  private void fireCleanCanvasEvent() {
    for (CanvasListener listener : listeners) {
      listener.cleanCanvas();
    }
  }

  private void fireMousePressedEvent(int x, int y) {
    for (CanvasListener listener : listeners) {
      listener.mousePressed(x, y);
    }
  }

  private void fireMouseDraggedEvent(int x, int y) {
    for (CanvasListener listener : listeners) {
      listener.mouseDragged(x, y);
    }
  }

  private void fireColorChangeEvent(Color color) {
    for (CanvasListener listener : listeners) {
      listener.colorChange(color);
    }
  }
}
