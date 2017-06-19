package com.xor.sorted;

import com.xor.sorted.Sorter;
import com.xor.sorted.sorts.*;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;

public class Main extends Application {
  int POINTS_NUMBER = 20000;
  int MAX_VALUE = 800;

  public static void main(String[] args){
    launch(args);
  }

  public void start(Stage stage){
    Canvas canvas = new Canvas(1900, MAX_VALUE);
    Pane root = new Pane(canvas);
    Scene scene = new Scene(root);
    stage.setTitle("Sorted!");
    stage.setScene(scene);
    canvas.getGraphicsContext2D().setFill(Color.RED);
    stage.show();

    ArrayList<Integer> list = getRandomPoints();

    Sorter sorter = new Sorter(list, POINTS_NUMBER, MAX_VALUE);
    AnimationTimer at = new AnimationTimer(){
      public void handle(long now){
        draw(sorter, canvas);
      }
    };
    at.start();

    sorter.queue(new Bubble(sorter, 100));
    sorter.queue(new Quick(sorter));
  }

  ArrayList<Integer> getRandomPoints(){
    return getRandomPoints(POINTS_NUMBER, 0, MAX_VALUE);
  }

  ArrayList<Integer> getRandomPoints(int number, int min, int max){
    ArrayList<Integer> numbers = new ArrayList<>();
    java.util.Random rng = new java.util.Random();
    for(int i=0; i<number; i++){
      numbers.add(rng.nextInt(MAX_VALUE+1));
    }
    return numbers;
  }

  void draw(Sorter sorter, Canvas canvas){
    ArrayList<Integer> numbers;
    numbers = new ArrayList<Integer>(sorter.getCurrentList());

    GraphicsContext gc = canvas.getGraphicsContext2D();
    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    double xIncrement=canvas.getWidth()/POINTS_NUMBER;
    double yIncrement=canvas.getHeight()/MAX_VALUE;

    if(sorter.isDone()){
      gc.setFill(Color.GREEN);
      gc.setStroke(Color.GREEN);
    }

    for(int i=0; i<numbers.size(); i++){
      gc.fillRect(xIncrement*i, canvas.getHeight()-yIncrement*numbers.get(i),
        xIncrement, yIncrement*numbers.get(i));
      gc.strokeRect(xIncrement*i, canvas.getHeight()-yIncrement*numbers.get(i),
        xIncrement, yIncrement*numbers.get(i));
    }
  }
}
