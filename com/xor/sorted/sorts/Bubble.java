package com.xor.sorted.sorts;

import com.xor.sorted.sorts.Sort;
import java.util.ArrayList;
import com.xor.sorted.Sorter;

public class Bubble extends Sort {

  public Bubble(Sorter sorter, int delay){
    super(sorter, delay);
  }

  public Bubble(Sorter sorter){
    super(sorter);
  }

  public void sortImpl(){
    int n = list.size();
    for(int i=0; compareVal(n, i); i++){
      for(int j=1; compareVal(n-i, j); j++){
        if(compare(j-1, j)){
          swap(j-1, j);
          if(shouldKill()) return;
        }
      }
    }
  }

}
