package com.xor.sorted.sorts;

import com.xor.sorted.sorts.Sort;
import java.util.ArrayList;
import com.xor.sorted.Sorter;

public class Quick extends Sort {
  public Quick(Sorter sorter, int delay){
    super(sorter, delay);
  }

  public Quick(Sorter sorter){
    super(sorter);
  }

  public void sortImpl(){
    quicksort(0, list.size() - 1);
  }

  private void quicksort(int low, int high) {
    int i = low, j = high;
    int pivot = list.get(low + (high-low)/2);
    while (compareVal(j+1, i)) {
      while (compareVal(pivot, list.get(i))) {
        i++;
      }
      while (compareVal(list.get(j), pivot)) {
        j--;
      }
      if (compareVal(j+1, i)) {
        swap(i, j);
        i++;
        j--;
      }
      if(shouldKill()) return;
    }
    // Recursion!!!
    if (compareVal(j, low)) quicksort(low, j);
    if (compareVal(high, i)) quicksort(i, high);
  }
}
