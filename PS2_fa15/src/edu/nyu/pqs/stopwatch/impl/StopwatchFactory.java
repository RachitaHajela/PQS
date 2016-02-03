package edu.nyu.pqs.stopwatch.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.nyu.pqs.stopwatch.api.IStopwatch;

/**
 * The StopwatchFactory is a thread-safe factory class for IStopwatch objects. It maintains
 * references to all created IStopwatch objects and provides a convenient method for getting a list
 * of those objects.
 *
 */
public final class StopwatchFactory {

  private static final Set<String> usedIds = new HashSet<String>();
  private static final List<IStopwatch> allStopwatches = new ArrayList<IStopwatch>();
  private static final Object lock = new Object();

  private StopwatchFactory() {
  }

  /**
   * Creates and returns a new IStopwatch object
   * 
   * @param id
   *          The identifier of the new object
   * @return The new IStopwatch object
   * @throws IllegalArgumentException
   *           if <code>id</code> is empty, null, or already taken.
   */
  public static IStopwatch getStopwatch(String id) throws IllegalArgumentException {

    if (id == null) {
      throw new IllegalArgumentException("ID cannot be null");
    }
    if (id.equals("")) {
      throw new IllegalArgumentException("ID cannot be empty");
    }

    synchronized (lock) {
      if (usedIds.contains(id)) {
        throw new IllegalArgumentException("ID already in use");
      }
      IStopwatch stopwatchObject = new Stopwatch(id);
      usedIds.add(id);
      allStopwatches.add(stopwatchObject);
      return stopwatchObject;
    }
  }

  /**
   * Returns a list of all created stopwatches
   * 
   * @return a List of all created IStopwatch objects. Returns an empty list if no IStopwatches have
   *         been created.
   */
  public static List<IStopwatch> getStopwatches() {
    synchronized (lock) {
      return new ArrayList<IStopwatch>(allStopwatches);
    }
  }
}