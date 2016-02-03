package edu.nyu.cs.pqs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.Point;

import org.junit.Test;

/*
 * This class tests the CanvasModel
 *
 */
public class testCanvasModel {

  CanvasModel model = CanvasModel.getInstance();
  DummyListener dummyListener = new DummyListener(model);

  @Test
  public void testRemoveListener() {
    CanvasView view = new CanvasView(model);
    assertTrue(model.removeListener(view));
    assertTrue(!(model.removeListener(view)));
  }

  @Test
  public void testMousePressed() {
    model.mousePressed(2, 3);
    Point testPoint = new Point(2, 3);
    assertEquals(testPoint, dummyListener.getCurrentPoint());
  }

  @Test
  public void testMouseDragged() {
    model.mouseDragged(4, 5);
    Point testPoint = new Point(4, 5);
    assertEquals(testPoint, dummyListener.getCurrentPoint());
  }

  @Test
  public void testChangeColor() {
    model.changeColor(Color.BLUE);
    assertEquals(Color.BLUE, dummyListener.getCurrentColor());
    model.changeColor(Color.GREEN);
    assertEquals(Color.GREEN, dummyListener.getCurrentColor());
  }

  @Test
  public void testCleanCanvas() {
    assertTrue(!(dummyListener.isCleanCanvas()));
    model.cleanCanvas();
    assertTrue(dummyListener.isCleanCanvas());
  }

  /**
   * dummyListener for testing
   *
   */
  private class DummyListener implements CanvasListener {
    private Point currentPoint;
    private Color currentColor = Color.GRAY;
    private boolean cleanCanvas = false;

    public DummyListener(CanvasModel model) {
      model.addListener(this);
    }

    @Override
    public void mousePressed(int x, int y) {
      currentPoint = new Point(x, y);
    }

    @Override
    public void mouseDragged(int x, int y) {
      currentPoint = new Point(x, y);
    }

    @Override
    public void colorChange(Color color) {
      currentColor = color;
    }

    @Override
    public void cleanCanvas() {
      cleanCanvas = true;
    }

    public Point getCurrentPoint() {
      return currentPoint;
    }

    public Color getCurrentColor() {
      return currentColor;
    }

    public boolean isCleanCanvas() {
      return cleanCanvas;
    }
  }
}
