package com.xor.sorted;

import java.util.ArrayList;
import java.util.ArrayDeque;
import com.xor.sorted.sorts.Sort;

public class Sorter{

  private ArrayList<Integer> originalList;
  private Sort currentSort=null;
  private Integer customDelay=null;

  private int POINTS_NUMBER;
  private int MAX_VALUE;

  private ArrayDeque<Sort> sortQueue = new ArrayDeque<>();

  Sorter(ArrayList<Integer> list, int POINTS_NUMBER, int MAX_VALUE){
    this.originalList = list;
    this.POINTS_NUMBER = POINTS_NUMBER;
    this.MAX_VALUE = MAX_VALUE;
  }

  void queue(Sort sort){
    sortQueue.add(sort);
    if(currentSort == null || currentSort.isDone()) nextSort();
  }

  public void nextSort(){
    if(currentSort!=null && !currentSort.isDone()) currentSort.end();
    Sort nextSort = sortQueue.poll();
    if(nextSort!=null){
      currentSort = nextSort;
      currentSort.setDelay(customDelay);
      currentSort.sort();
    }
  }

  public ArrayList<Integer> getList(){
    return new ArrayList<Integer>(originalList);
  }

  public ArrayList<Integer> getCurrentList(){
    if (currentSort==null) return getList();
    else return currentSort.getList();
  }

  public boolean isDone(){
    if (currentSort==null) return false;
    return currentSort.isDone();
  }

  public void setDelay(Integer delay){
    if (currentSort!=null) currentSort.setDelay(delay);
    customDelay = delay;
  }
}
