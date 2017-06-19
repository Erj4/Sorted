package com.xor.sorted;

import java.lang.Thread;

import javafx.concurrent.Task;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.*;
import java.util.ArrayDeque;
import com.xor.sorted.sorts.Sort;

public class Sorter{

  private ArrayList<Integer> originalList;
  Sort currentSort=null;

  int POINTS_NUMBER;
  int MAX_VALUE;


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
}
