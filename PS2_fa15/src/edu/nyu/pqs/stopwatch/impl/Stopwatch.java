package edu.nyu.pqs.stopwatch.impl;

import java.util.ArrayList;
import java.util.List;

import edu.nyu.pqs.stopwatch.api.IStopwatch;

/**
 * The Stopwatch is a thread safe class for creating Stopwatch objects for timing tasks. It
 * implements Istopwatch interface. The Stopwatch objects support the typical operations of a
 * physical stopwatch: start, stop, restart, and the recording of laps. Different threads can share
 * a single stopwatch object and safely call any of the stopwatch methods.
 * 
 */
public final class Stopwatch implements IStopwatch {
  private final String id;
  private long startTime;
  private List<Long> lapTimes;
  private boolean isRunning;
  private Object lock;

  /*
   * stores the end time of the last finished lap. Its value is 0 initially and changes with each
   * completed lap.
   */
  private long lastLapTime;

  Stopwatch(String id) {
    this.id = id;
    startTime = 0;
    lapTimes = new ArrayList<Long>();
    isRunning = false;
    lastLapTime = 0;
    lock = new Object();
  }
  
  /**
   * Returns the Id of this Stopwatch
   * @return the Id of this Stopwatch.  Will never be empty or null and should be unique.
   */
  @Override
  public String getId() {
    return this.id;
  }
  
  /**
   * Starts the Stopwatch.
   * @throws IllegalStateException if called when the stopwatch is already running
   */
  @Override
  public void start() {
    synchronized (lock) {
      if (isRunning) {
        throw new IllegalStateException("Stopwatch already running");
      }
      startTime = System.currentTimeMillis();
      isRunning = true;
    }
  }

  /**
   * Stores the time elapsed since the last time lap() was called
   * or since start() was called if this is the first lap.
   * @throws IllegalStateException if called when the Stopwatch isn't running
   */
  @Override
  public void lap() {
    long currentLapTime = System.currentTimeMillis();
    synchronized (lock) {
      if (!isRunning) {
        throw new IllegalStateException("Stopwatch is not running");
      }
      if (lastLapTime == 0) {
        lapTimes.add(currentLapTime - startTime);
      } else {
        lapTimes.add(currentLapTime - lastLapTime);
      }
      lastLapTime = currentLapTime;
    }
  }

  /**
   * Stops the stopwatch (and records one final lap).
   * @throws IllegalStateException if called when the stopwatch isn't running
   */
  @Override
  public void stop() {
    lap();
    synchronized (lock) {
      isRunning = false;
    }
  }

  /**
   * Resets the stopwatch.  If the stopwatch is running, this method stops the
   * watch and resets it.  This also clears all recorded laps.
   */
  @Override
  public void reset() {
    synchronized (lock) {
      isRunning = false;
      startTime = 0;
      lastLapTime = 0;
      lapTimes.clear();
    }
  }

  /**
   * Returns a list of lap times (in milliseconds).  This method can be called at
   * any time and will not throw an exception.
   * @return a list of recorded lap times or an empty list if no times are recorded.
   */
  @Override
  public List<Long> getLapTimes() {
    synchronized (lock) {
      return new ArrayList<Long>(lapTimes);
    }
  }

  @Override
  public String toString() {
    synchronized (lock) {
      return "Stopwatch id=" + id + ", startTime=" + startTime + ", lapTimes=" + lapTimes
          + ", lastLapTime=" + lastLapTime;
    }
  }

  /**
   * overriding equals for logical equality between two Stopwatch objects based on their IDs as each
   * object's ID is unique
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Stopwatch)) {
      return false;
    }
    Stopwatch other = (Stopwatch) obj;
    if (!id.equals(other.id)) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 17;
    result = prime * result + id.hashCode();
    return result;
  }
}