package se2203.assignment1;

import javafx.application.Platform;

import java.util.Arrays;

public class SelectionSort extends SortingHubController implements SortingStrategy{
    private int [] list;
    private SortingHubController controller;
    public SelectionSort (int[] list, SortingHubController controller) {
        this.list = list;
        this.controller = controller;
    }
    //selection sort method
    @Override
    public void sort(int[] numbers) {
        for (int i = 0; i < (numbers.length); i++) {//creates a loop that iterates through the objects in the array
            int index = i;
            for (int j = i + 1; j < (numbers.length); j++) {
                if (numbers[j]<numbers[index]) {
                    index = j;
                }
            }
            int temp = numbers[i]; //creates temporary variable used to hold value of a[i]
            numbers[i] = numbers[index];
            numbers[index] = temp;//swaps values by using temp variable
            Platform.runLater(() ->{
                controller.updateGraph(numbers);
            });
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        SortingHubController.done = true;
    }

    @Override
    public void run() {
        sort(list);

    }
}

