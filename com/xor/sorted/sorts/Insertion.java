package com.xor.sorted.sorts;

import com.xor.sorted.sorts.Sort;
import java.util.ArrayList;
import com.xor.sorted.Sorter;

public class Insertion extends Sort {

  public Insertion(Sorter sorter, int delay){
    super(sorter, delay);
  }

  public Insertion(Sorter sorter){
    super(sorter);
  }

  public void sortImpl(){
    for(int i=1; compareVal(list.size(), i); i++){
      for(int j=i; compareVal(j, 0)&&compare(j-1, j); j--){
        swap(j-1, j);
      }
    }
  }

}
