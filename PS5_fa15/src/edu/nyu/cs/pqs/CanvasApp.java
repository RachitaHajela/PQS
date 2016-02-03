package edu.nyu.cs.pqs;

/**
 * Launches the application with two windows. User can press and drag the mouse to paint on the
 * Canvas. User can also select one of the five colors and can clean the canvas.
 *
 */
public class CanvasApp {

  public static void main(String[] args) {
    new CanvasApp().startPainting();

  }

  private void startPainting() {

    CanvasModel model = CanvasModel.getInstance();
    CanvasView view1 = new CanvasView(model);
    CanvasView view2 = new CanvasView(model);
    CanvasView view3 = new CanvasView(model);

    view1.setUp();
    view2.setUp();
    view3.setUp();
  }

}
