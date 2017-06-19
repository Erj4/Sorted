package com.xor.sorted;

import com.xor.sorted.Sorter;
import com.xor.sorted.sorts.*;
import java.lang.Math;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.*;
import javafx.stage.Stage;

public class Main extends Application {
  int POINTS_NUMBER = 450;
  int MAX_VALUE = 800;
  int SLIDER_MIN = 0;
  int SLIDER_MAX = 8;
  int SLIDER_DEF = 8;

  public static void main(String[] args){
    launch(args);
  }

  public void start(Stage stage){
    Canvas canvas = new Canvas(1800, MAX_VALUE);
    HBox canvasContainer = new HBox(canvas);
    Slider speedSlider = new Slider(SLIDER_MIN, SLIDER_MAX, SLIDER_DEF);
    speedSlider.setMaxWidth(200);
    HBox controlBar = new HBox(speedSlider);
    VBox root = new VBox(canvasContainer, controlBar);
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
    speedSlider.valueProperty().addListener((ov, b, a)->sorter.setDelay((int) Math.pow(10, (double) a)));
    sorter.setDelay((int) Math.pow(10, SLIDER_DEF));

    sorter.queue(new Quick(sorter));
    sorter.queue(new Insertion(sorter));
    sorter.queue(new Bubble(sorter));
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
