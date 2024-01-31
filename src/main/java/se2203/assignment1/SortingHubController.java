package se2203.assignment1;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class SortingHubController implements Runnable{
    //this is to make sure reset doesn't reset mid sort
    public static boolean done = false;
    @FXML
    private Rectangle rectangle;
    @FXML
    private Pane mainPane;
    static int [] intArray;
    private SortingStrategy sortingMethod;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private Label sliderValue;
    @FXML
    private Slider slider;
    //will display the slider's numerical value
    @FXML
    void displaySliderValue(){
        int sliderVal = (int) slider.getValue();
        sliderValue.setText(String.valueOf(sliderVal));
    }
    public void setSortStrategy(){
        if(comboBox.getSelectionModel().getSelectedItem()=="Selection Sort"){
            sortingMethod = new SelectionSort(intArray,this);
        }else{
            sortingMethod = new MergeSort(intArray,this);
        }
    }
    //depending on combobox value, sorts integers that way
    public void sortPressed(){
        setSortStrategy();
        Thread sortThread = new Thread(sortingMethod);
        sortThread.start();

    }
//sort methods will call this to update graphs in real time
    public void updateGraph(int [] list){
        double x = 0;
        for(int i =0;i< list.length;i++){
            Rectangle rec = (Rectangle) mainPane.getChildren().get(i);
            rec.setX(x+2);
            rec.setY(302-(list[i]*(302.0/ intArray.length)));
            rec.setWidth((743.0/ intArray.length)-1);
            rec.setHeight(list[i]*(302.0/ intArray.length));
            x = x+(743.0/(intArray.length));
        }
    }
    //no clue
    @Override
    public void run() {

    }
    //reset everything to 64
    public void initialize(){
        sliderValue.setText("64");
        slider.setValue(64);
        comboBox.getItems().addAll("Merge Sort","Selection Sort");
        //intArray will have the size of the slider value
        randomArray();
        setUpBars();
    }
    //creates random array
    public void randomArray(){
        intArray = new int[(int) slider.getValue()];
        for (int i =1;i< intArray.length;i++){
            //gives random integer between 1 and the array length
            intArray[i]= (int)(Math.random()*(intArray.length)+1);
            for (int j = 0;j<i;j++){
                //if number already exists in array, replace with another number that doesn't
                if(intArray[i]==intArray[j]){
                    intArray[i]= (int)(Math.random()*(intArray.length)+1);
                    j=0;
                }
            }
        }
        intArray[0]= (int)(Math.random()*(intArray.length)+1);
        for (int j = 0;j< intArray.length;j++){
            if(intArray[0]==intArray[j]){
                intArray[0]= (int)(Math.random()*(intArray.length)+1);
                j=0;
            }
        }
    }
    //this will create a random array and setup the bars
    public void setUpBars(){
        //clears pane, randomizes array and displays randomized array
        mainPane.getChildren().clear();
        randomArray();
        double x = 0;
        for (int i =0; i< intArray.length;i++){
            rectangle = new Rectangle(x+2,302-(intArray[i]*(302.0/ intArray.length)),(743.0/intArray.length)-1,intArray[i]*(302.0/ intArray.length));
            rectangle.setFill(Color.RED);
            mainPane.getChildren().add(rectangle);
            x = x+(743.0/(intArray.length));
        }
        displaySliderValue();
    }
    //reset everything to 64
    public void reset(){
        if(done){
            sliderValue.setText("64");
            slider.setValue(64);
            comboBox.setValue("Merge Sort");
            //display random array
            setUpBars();
        }else{
            done = false;
        }
    }
}