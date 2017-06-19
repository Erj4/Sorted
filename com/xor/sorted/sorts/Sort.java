package com.xor.sorted.sorts;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.lang.Comparable;
import javafx.concurrent.Task;
import com.xor.sorted.Sorter;

public abstract class Sort {
  private static final int DEFAULT_DELAY = 10000;

  protected AtomicInteger delay = new AtomicInteger();
  protected boolean done = true;
  protected ArrayList<Integer> list;
  protected boolean paused = false;
  protected boolean killed = false;
  private Sorter sorter;

  public Sort(Sorter sorter, int delay){
    this.list = sorter.getList();
    this.delay = new AtomicInteger(delay);
    this.sorter = sorter;
  }

  public Sort(Sorter sorter){
    this.list = sorter.getList();
    this.sorter = sorter;
  }
  public void sort(){
    done=false;
    Task<ArrayList<Integer>> task = new Task<ArrayList<Integer>>() {
      @Override
      protected ArrayList<Integer> call() throws Exception {
        tick();
        sortImpl();
        done=true;
        sorter.nextSort();
        killed=false;
        return list;
      }
    };
    Thread thread = new Thread(task);
    thread.setDaemon(true);
    thread.start();
  }

  public abstract void sortImpl();

  public void pause(){
    paused=true;
  }
  public void resume(){
    paused=false;
  }
  public void end(){
    killed=true;
  }

  public boolean isDone(){
    return done;
  }

  public void setDelay(Integer delay){
    if(delay==null) delay = DEFAULT_DELAY;
    this.delay.set(delay);
  }

  public int getDelay(){
    return delay.get();
  }

  public ArrayList<Integer> getList(){
    synchronized (list) {
      return new ArrayList<Integer>(list);
    }
  }

  protected void tick(){
    long start = System.nanoTime();
    long end=0;
    do {end = System.nanoTime();} while(start + delay.get() >= end);
    while(paused) {}
  }

  protected void swap(int a, int b){
    tick();
    Integer temp = list.get(a);
    synchronized(list){
      list.set(a, list.get(b));
      list.set(b, temp);
    }
  }

  protected boolean compare(int a, int b){
    tick();
    return list.get(a) > list.get(b);
  }

  protected boolean compareVal(int a, int b){
    tick();
    return a > b;
  }

  protected boolean shouldKill(){
    boolean temp = killed;
    killed = false;
    return temp;
  }
}
