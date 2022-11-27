package com.example.demo;

import javafx.fxml.FXML;


import java.util.ArrayList;
import java.util.Arrays;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class HelloController {
    int x = 25;//12 by 12 sections with the middle 3 rows being the tunnels
    int y = 25;
    Button[][] btn = new Button[x][y];
    int[][] gameGrid = new int[x][y];
    int[][] backGrounGrid = new int[x][y];

    ArrayList<String> predetors = new ArrayList<String>(Arrays.asList("Wolves","Foxes"));//,"Bears","Hyenas","Foxes"
    ArrayList<String> prey = new ArrayList<String>(Arrays.asList("Bunnies","Dear"));//,"Dear",,"Swans","Cows"
    ArrayList<String> envoirments = new ArrayList<String>(Arrays.asList("Forest","Open Field","Watering Hole","Food Patch"));

    ArrayList<String> resources = new ArrayList<String>(Arrays.asList("Hunger Up","Thirst Up","Weather Control"));;

    ArrayList<Predetor> myPredetors = new ArrayList<>();
    ArrayList<Prey> myPrey = new ArrayList<>();

    //GridPane gPane = new GridPane();
//    Image k = new Image("resources/Koala.jpg");
    @FXML
    private AnchorPane aPane;

    @FXML
    private GridPane gPane;

    @FXML
    private ListView lstPredetors,lstPrey,lstResources,lstdeadanddayData;

    @FXML
    private Slider weatherChosenSlider;

    @FXML
    private Label lblAnimalChosen,lblMaxPreyNum,lblPedetorNum,lblResourceChosen,lblWeatherChangeExp;

    @FXML
    private TextField txtMaxAmountPred,txtMaxAmountPrey;

    @FXML
    private Button buttonSetMaxPred,buttonMaxPrey;

    String tempAnimal = " ";
    boolean clickedOnList = false;
    boolean dayNight = false;//false is day, true is night
    long dayTime;
    long nightTime;
    int evaportaionTime = 0;
    long evolutionTime;

    long updateGraph;

    ArrayList<Double> dayAndDeathNums = new ArrayList<>();
    int randWeather = (int)(Math.random()*50);//between 0 - 15- means rain; between 0-25 means snow; between 25 -50 means rain

    long foodGrowTime;
    boolean assignAlphaAndBeta = false;

    boolean continueReprod = false;
    int decomposionLevels = -1;
    int level = 0;
    int caseNum = 0;

    int maxPredetorNum = 0;
    int maxPreyNum = 0;

    int waterMultiValue = 3;
    int sunMultiValue = 2;

    int evolutionPeriod = 0;
    boolean drought = false;
    String weatherClicked = " ";

    int count = 0;

    @FXML
    private LineChart lineChartShow;

    @FXML
    NumberAxis xAxis;

    @FXML
    NumberAxis yAxis;

    XYChart.Series seriesOne = new XYChart.Series();


    @FXML
    private void handleStart(ActionEvent event) {
        //backGrounGrid; 0 -- the walking path. 1 -- hunting grounds. 2 -- walls
//        gPane.setMinSize(0,0);
        //gPane.setPadding(new Insets(btn[i][j]));
        //gPane.setHgap(10);
        //gPane.setVgap(10);
        //gPane.setGridLinesVisible(true);
        //gPane.setAlignment(Pos.CENTER);

        updateGraph = System.nanoTime();
        lineChartShow.setAnimated(false);
        xAxis.setAutoRanging(false);
        xAxis.setLabel("time");
        yAxis.setLabel("population");

        lineChartShow.setTitle("Average Prey Population");
        lineChartShow.getData().addAll(seriesOne);

        lblWeatherChangeExp.setText(" 3 Days ");
        for(int i=0; i<btn.length; i++){
            for(int j=0; j<btn[0].length;j++){

                //Initializing 2D buttons with values i,j
                btn[i][j] = new Button();
                btn[i][j].setStyle("-fx-background-color:#9c8472");

                btn[i][j].setPrefWidth(25);

//                btn[i][j].setPrefSize(25, 5);
                //Paramters:  object, columns, rows
                gPane.add(btn[i][j], j, i);
                gameGrid[i][j]=0;
                backGrounGrid[i][j] = 0;

                btn[i][j].setStyle("-fx-background-color:#d3d3d3");

                if (i <= 12 && j <= 12){
                    btn[i][j].setStyle("-fx-background-color:#066b26");
                    backGrounGrid[i][j] = 1;
                }

                if (i <= 12 && j >= 12){
                    btn[i][j].setStyle("-fx-background-color:#00ff44");
                    backGrounGrid[i][j] = 2;
                }

                if (i >= 12 && j <= 12){
                    btn[i][j].setStyle("-fx-background-color:#24b524");
                    backGrounGrid[i][j] = 3;
                }

                if (i >= 12 && j >= 12){
                    btn[i][j].setStyle("-fx-background-color:#529e52");
                    backGrounGrid[i][j] = 4;
                }

                if(i > 18 && j > 18){
                    btn[i][j].setStyle("-fx-background-color:#2e66c7");
                    backGrounGrid[i][j] = 5;
                }

                if (i <= 9 && j >= 16){
                    int iX = (int)(Math.random()*9);
                    int iY = (int)(Math.random()*8)+15;
                    int rand = (int)(Math.random()*2);
                    if (rand == 0){
                        btn[i][j].setStyle("-fx-background-color:#ffc15e");
                        backGrounGrid[i][j] = 6;
                    }else{
                        btn[i][j].setStyle("-fx-background-color:#6ae01b");
                        backGrounGrid[i][j] = 7;
                    }

                }
            }
        }

        for (int i = 0; i < resources.size(); i++) {
            lstResources.getItems().add("");
        }

        for (int i = 0; i < predetors.size(); i++) {
            lstPredetors.getItems().add(predetors.get(i));
        }

        for (int i = 0; i < prey.size(); i++) {
            lstPrey.getItems().add(prey.get(i));
        }

        gPane.setGridLinesVisible(true);

        gPane.setVisible(true);
        dayTime = System.nanoTime();
        nightTime = System.nanoTime();
        evolutionTime = System.nanoTime();
        foodGrowTime = System.nanoTime();
        start();
    }

//The colors of the objects on the grid
    public void updateScreen(){

//        System.out.println("snowtiimeeeeeee");

        for(int i=0; i<btn.length; i++) {
            for (int j = 0; j < btn[0].length; j++) {

//
                if (gameGrid[i][j] == 0){

                    if (dayNight == false){

                        if (randWeather >= 25){
//                            System.out.println("its snow time");
                            btn[i][j].setStyle("-fx-background-color:#ebe4e4");
                        }else if (backGrounGrid[i][j] == 1){
                            btn[i][j].setStyle("-fx-background-color:#066b26");
                        }else if (backGrounGrid[i][j] == 2){
                            btn[i][j].setStyle("-fx-background-color:#00ff44");
                        }else if (backGrounGrid[i][j] == 3){
                            btn[i][j].setStyle("-fx-background-color:#24b524");
                        }else if (backGrounGrid[i][j] == 4){
                            btn[i][j].setStyle("-fx-background-color:#529e52");
                        }

                        if (backGrounGrid[i][j] == 5){//watering hole
                            btn[i][j].setStyle("-fx-background-color:#2e66c7");
                            if (evaportaionTime > waterMultiValue){
                                btn[i][j].setStyle("-fx-background-color:#a9bad6");
                            }
                        }else if (backGrounGrid[i][j] == 6){//carrot
                            btn[i][j].setStyle("-fx-background-color:#ffc15e");
                            if (evaportaionTime > waterMultiValue){
                                btn[i][j].setStyle("-fx-background-color:#fff3e0");
                            }

                        }else if (backGrounGrid[i][j] == 7){//grass
                            btn[i][j].setStyle("-fx-background-color:#6ae01b");
                            if (evaportaionTime > waterMultiValue){
                                btn[i][j].setStyle("-fx-background-color:#acd492");
                            }

                        }


                    }else{
                        if (randWeather >= 25){
//                            System.out.println("its snow time");
                            btn[i][j].setStyle("-fx-background-color:#706d6d");
                        }else if (backGrounGrid[i][j] == 1){
                            btn[i][j].setStyle("-fx-background-color:#023813");
                        }else if (backGrounGrid[i][j] == 2){
                            btn[i][j].setStyle("-fx-background-color:#007d21");
                        }else if (backGrounGrid[i][j] == 3){
                            btn[i][j].setStyle("-fx-background-color:#24b524");
                        }else if (backGrounGrid[i][j] == 4){
                            btn[i][j].setStyle("-fx-background-color:#156315");
                        }


                        if (backGrounGrid[i][j] == 5){
                            btn[i][j].setStyle("-fx-background-color:#1a3666");
                            if (evaportaionTime > waterMultiValue){
                                btn[i][j].setStyle("-fx-background-color:#4d5461");
                            }
                        }else if (backGrounGrid[i][j] == 6){
                            btn[i][j].setStyle("-fx-background-color:#705528");//705528
                        }else if (backGrounGrid[i][j] == 7){
                            btn[i][j].setStyle("-fx-background-color:#35700d");//35700d
                        }



                    }



                }

                if (gameGrid[i][j] == 9){
                    btn[i][j].setStyle("-fx-background-color:#f2ff00");
                }else if (gameGrid[i][j] == 10){
                    btn[i][j].setStyle("-fx-background-color:#00fffb");
                }else if (gameGrid[i][j] == 11){
                    btn[i][j].setStyle("-fx-background-color:#6f00ff");
                }

                if (gameGrid[i][j]== 1) {//predetor
//                    System.out.println("Size:" + myPredetors.size());

                    for (int k = 0; k < myPredetors.size(); k++) {

                            myPredetors.get(k).getColors(myPredetors,btn);

                    }

                    for (int k = 0; k < myPrey.size(); k++) {

                        myPrey.get(k).getColors(myPrey,btn);
                    }

                }

                    for (int k = 0; k < myPredetors.size(); k++) {
                        if (gameGrid[myPredetors.get(k).getX()][myPredetors.get(k).getY()] < 0) {
                            if (myPredetors.get(k).isDecompose() == true) {
                                if (gameGrid[myPredetors.get(k).getX()][myPredetors.get(k).getY()] < 0 && gameGrid[myPredetors.get(k).getX()][myPredetors.get(k).getY()] >= -5) {
//                        System.out.println("stage 1")''
                                    btn[myPredetors.get(k).getX()][myPredetors.get(k).getY()].setStyle("-fx-background-color:#55875a");//////////////////

                                } else {
//                        System.out.println("stage 2");
                                    btn[myPredetors.get(k).getX()][myPredetors.get(k).getY()].setStyle("-fx-background-color:#28382a");//////////////////
                                }
                            }
                        }



                    }

                for (int k = 0; k < myPrey.size(); k++) {
                    if (gameGrid[myPrey.get(k).getX()][myPrey.get(k).getY()] < 0) {
                        if (myPrey.get(k).isDecompose() == true) {
                            if (gameGrid[myPrey.get(k).getX()][myPrey.get(k).getY()] < 0 && gameGrid[myPrey.get(k).getX()][myPrey.get(k).getY()] >= -5) {
//                        System.out.println("stage 1")''
                                btn[myPrey.get(k).getX()][myPrey.get(k).getY()].setStyle("-fx-background-color:#55875a");//////////////////

                            } else {
//                        System.out.println("stage 2");
                                btn[myPrey.get(k).getX()][myPrey.get(k).getY()].setStyle("-fx-background-color:#28382a");//////////////////
                            }
                        }
                    }



                }



            }
        }

        if (myPredetors.size() > 5 && myPredetors.size() <= 19){
            level = 1;
        }else if (myPredetors.size() > 20){
            level = 2;
        }

        if (level == 1){
            for (int i = 0; i < 1; i++) {
                lstResources.getItems().set(i,resources.get(i));
            }

        }else if (level == 2){
            for (int i = 0; i < resources.size(); i++) {

                lstResources.getItems().set(i,resources.get(i));
            }

        }


    }

    @FXML
    private void clickedOnListPredetors(){
        int indexClicked = lstPredetors.getSelectionModel().getSelectedIndex();

        tempAnimal = lstPredetors.getItems().get(indexClicked).toString();
        lblAnimalChosen.setText(tempAnimal);
        clickedOnList = true;

    }

    @FXML
    private void butIncreaseAllHealth(){
        for (int i = 0; i < myPredetors.size(); i++) {
            myPredetors.get(i).setThirstAndHungerToZero();
        }
        for (int i = 0; i < myPrey.size(); i++) {
            myPrey.get(i).setThirstAndHungerToZero();
        }
    }

    String resoruceClickedOn = " ";

    @FXML
    private void clickOnListResources(){
        int indexClicked = lstResources.getSelectionModel().getSelectedIndex();
///"Hunger Up","Thirst Up","Weather Control"
        if (indexClicked == 0){
            resoruceClickedOn = "Hunger Up";

        }else if (indexClicked == 1){
            resoruceClickedOn = "Thirst Up";

        }else if (indexClicked == 2){
            resoruceClickedOn = "Weather Control";

        }
        dropItems();
//        clickedOnList = true;

    }

    public void dropItems(){
        int randXDrop = (int)(Math.random()*25);
        int randYDrop = (int)(Math.random()*25);

        if (resoruceClickedOn.equals("Hunger Up")){
            gameGrid[randXDrop][randYDrop] = 9;
        }else if (resoruceClickedOn.equals("Thirst Up")){
            gameGrid[randXDrop][randYDrop] = 10;
        }else if (resoruceClickedOn.equals("Weather Control")){
            gameGrid[randXDrop][randYDrop] = 11;
        }
    }

    ArrayList<Predetor> tempLeaders = new ArrayList<>();
//    private boolean continueMoving = true;
    private boolean findBeta = false;


    @FXML
    private void clickedOnListPrey(){
        int indexClicked = lstPrey.getSelectionModel().getSelectedIndex();
        tempAnimal = lstPrey.getItems().get(indexClicked).toString();

        lblAnimalChosen.setText(tempAnimal);
        clickedOnList = true;

    }

    ArrayList<Predetor> followingCords = new ArrayList<>();

    public void packFollowsPreyToKill(int[][] gameGrid, int[][] backGroundGrid, ArrayList<Predetor> tempPred){

        for (int j = 0; j < myPrey.size(); j++) {
            followingCords.clear();

//            followingCords.add(myPrey.get(j).getX());
//            followingCords.add(myPrey.get(j).getY());

                for (int i = 0; i < myPredetors.size(); i++) {

                    for (int k = myPrey.get(j).getX()-3; k < myPrey.get(j).getX()+3; k++) {
//                        System.out.println("k is: " + k);
                        for (int z = myPrey.get(j).getY()-3; z < myPrey.get(j).getY()+3; z++) {
//                            System.out.println("z is: " + z);
                            if (k >= 0 && z >= 0 && k < gameGrid.length && z < gameGrid[0].length){

                                if(myPredetors.get(i).getX() >= k && myPredetors.get(i).getX() <= myPrey.get(j).getX()+3 &&
                                        myPredetors.get(i).getY() >= z && myPredetors.get(i).getY() <= myPrey.get(j).getY()+3){

//                                    System.out.println("Theres a pred aruond the prey");
                                    //                                    continueMoving = false;

                                    if (myPredetors.get(i).isFoundPreyMove() == false){

                                        myPredetors.get(i).setFollowPrey(true);
                                        myPredetors.get(i).setPreyIndex(j);
                                        myPredetors.get(i).setFoundPreyMove(true);

                                    }


//                                    followingCords.add(myPredetors.get(i));
//                                    followingCords.add(myPredetors.get(i));

                                }else{
                                    myPredetors.get(i).setFollowPrey(false);
                                }
                            }


                        }
                    }

                }

        }


    }

    long timeCheckPerSec = System.nanoTime();
    double numDays = 0;
    double numPreyDead = 0;
////////////COME BACK TO THIS
    int total = 0;

    //The timer
    public void start() {

        new AnimationTimer() {
            @Override
            public void handle(long now) {

                if (now - updateGraph > 1000000000.0){
                    updateGraph = System.nanoTime();
                    xAxis.setLowerBound(count-10);
                    xAxis.setUpperBound(count + 2);


                    total++;
                    if (total >= 10){
                       seriesOne.getData().remove(0);
                    }

                    seriesOne.setName("balhh");
                    seriesOne.getData().add(new XYChart.Data(count, (myPrey.size())));
                    count++;
                }


////////////////FOXESSSSSSSSSSSSSSSSSSSSSSSSSS
                if (now - timeCheckPerSec > 1000000000.0){
//                    findAlphaAndBetta(myPredetors);
                    int count = 0;
                    for (int i = 0; i < myPredetors.size(); i++) {
                        if (myPredetors.get(i).getTypeOfPredetor().equals("Wolves")){
                            count++;
                            if (count == 1){
                                myPredetors.get(i).setRank("Alpha");
                            }else if (count == 2){
                                myPredetors.get(i).setRank("Beta");
                            }
                        }
                    }

                    count = 0;

                    for (int i = 0; i < myPredetors.size(); i++) {
                        if (myPredetors.get(i).getTypeOfPredetor().equals("Foxes")){
                            count++;
                            if (count == 1){
                                myPredetors.get(i).setRank("Alpha");
                            }else if (count == 2){
                                myPredetors.get(i).setRank("Beta");
                            }
                        }
                    }

                    double avg = (numPreyDead/numDays);

                    lstdeadanddayData.getItems().add(avg);

                    System.out.println("num days: " + numDays);
                    System.out.println("num dead: " + numPreyDead);

                    timeCheckPerSec = System.nanoTime();
                }

                if (now - dayTime > 10000000000.0 && dayNight == false){
                    dayNight = true;

                    dayAndDeathNums.add(numPreyDead/numDays);


                    for (int i = 0; i < myPredetors.size(); i++) {
                        if (myPredetors.get(i).getHunger() >= 5 && myPredetors.get(i).isDecompose() == false){
                            numPreyDead += myPredetors.get(i).checkNeighborOppositeToKill(myPredetors,myPrey,gameGrid);
                        }
                        myPredetors.get(i).setHunger(-1);
                        myPredetors.get(i).setEgo(1);
                    }
//                    System.out.println("Its Night Time");
                    randWeather = (int)(Math.random()*50);
                    dayTime = System.nanoTime();
                    nightTime = System.nanoTime();
                }

                if (now - nightTime > 10000000000.0 && dayNight == true){
                    dayNight = false;
//                    System.out.println("Its Day Time");
                    evaportaionTime++;
                    numDays++;
                    nightTime = System.nanoTime();
                    dayTime = System.nanoTime();
                }

                if (now - evolutionTime > 10000000000.0){
                    //caseNum is 0 means 1st level evolution, caseNum 1 means 2nd level evoultion, 2 means 3rd level evolution
                    evolutionPeriod++;

                    evolutionTime = System.nanoTime();

                }


                if (myPredetors.size() > 0){
                    for (int i = 0;i<myPredetors.size();i++){


                        if(now - myPredetors.get(i).getStartTime() > 1000000000.0){


                            if (myPredetors.get(i).isDecompose() == false){
                                if (myPredetors.get(i).getHunger() < 5 ){
                                    numPreyDead += myPredetors.get(i).checkNeighborOppositeToKill(myPredetors,myPrey,gameGrid);
                                }else if (decideProbability(9) > 93){
                                    numPreyDead += myPredetors.get(i).checkNeighborOppositeToKill(myPredetors,myPrey,gameGrid);
                                }else if (myPredetors.get(i).getHunger() >= 5 && backGrounGrid[myPredetors.get(i).getX()][myPredetors.get(i).getY()] == 3){
                                    numPreyDead += myPredetors.get(i).checkNeighborOppositeToKill(myPredetors,myPrey,gameGrid);
                                }

                                myPredetors.get(i).checkNeighborOppositeAlpha(myPredetors,gameGrid);

                            }


                            if (now - myPredetors.get(i).getMoveTime() > 2000000000.0 && randWeather >= 25){
                                if (myPredetors.get(i).isDecompose() == false && myPredetors.get(i).isContinueMoving() == true && myPredetors.get(i).isFoundPreyMove() == true){
                                    if (myPredetors.get(i).getTypeOfPredetor().equals("Wolves")){
                                        myPredetors.get(i).changeLoc(gameGrid,backGrounGrid,myPredetors,myPrey);

                                    }else if (myPredetors.get(i).getTypeOfPredetor().equals("Foxes")){
                                        myPredetors.get(i).changeLoc(gameGrid,backGrounGrid,myPredetors,myPrey);

                                    }
                                }
                                myPredetors.get(i).setMoveTime();
                            }else if (now - myPredetors.get(i).getMoveTime() > 1000000000.0 && randWeather < 25){
                                if (myPredetors.get(i).isDecompose() == false && myPredetors.get(i).isContinueMoving() == true && myPredetors.get(i).isFoundPreyMove() == true){
                                    if (myPredetors.get(i).getTypeOfPredetor().equals("Wolves")){
                                        myPredetors.get(i).changeLoc(gameGrid,backGrounGrid,myPredetors,myPrey);

                                    }else if (myPredetors.get(i).getTypeOfPredetor().equals("Foxes")){
                                        myPredetors.get(i).changeLoc(gameGrid,backGrounGrid,myPredetors,myPrey);

                                    }
                                }
                                myPredetors.get(i).setMoveTime();
                            }

                            if (myPredetors.get(i).getHunger() < 0 || myPredetors.get(i).getThirst() < 0){
                                gameGrid[myPredetors.get(i).getX()][myPredetors.get(i).getY()] = myPredetors.get(i).getDecomposeLimit();
                                myPredetors.get(i).setDecompose(true);
                            }


                            for (int j = 0; j < backGrounGrid.length; j++) {
                                for (int k = 0; k < backGrounGrid[0].length; k++) {

                                    //on the lake
                                    if (myPredetors.get(i).getX() == j && myPredetors.get(i).getY() == k && backGrounGrid[j][k] == 5 && myPredetors.get(i).getThirst() < 5 && evaportaionTime < waterMultiValue){
                                        myPredetors.get(i).setContinueMoving(false);
                                        if (now - myPredetors.get(i).getDrinkingTime() > 5000000000.0 && myPredetors.get(i).isContinueMoving() == false && myPredetors.get(i).isFoundPreyMove() == true){

                                            myPredetors.get(i).setContinueMoving(true);

                                            myPredetors.get(i).changeLoc(gameGrid,backGrounGrid,myPredetors,myPrey);
                                            System.out.println("set to true on wRER");
                                            myPredetors.get(i).setThirst(1);//the higher the thrist the better
                                            myPredetors.get(i).setDrinkingTime();
                                        }
                                    }

//                                    System.out.println(continueMoving);

                                    if (myPredetors.get(i).getX() == j && myPredetors.get(i).getY() == k && (backGrounGrid[j][k] == 6 || backGrounGrid[j][k] == 7 ) && myPredetors.get(i).getHunger() < 5 && evaportaionTime < waterMultiValue){
                                        myPredetors.get(i).setContinueMoving(false); //on the food patch
//                                        System.out.println("on gradss");
                                        if (now - myPredetors.get(i).getEatingTime() > 5000000000.0 && myPredetors.get(i).isContinueMoving() == false && myPredetors.get(i).isFoundPreyMove() == true){
                                            System.out.println("set to true on gradd");
                                            myPredetors.get(i).setContinueMoving(true);
                                            myPredetors.get(i).changeLoc(gameGrid,backGrounGrid,myPredetors,myPrey);
                                            myPredetors.get(i).setHunger(1);//the higher the thrist the better
                                            myPredetors.get(i).setEatingTime();
                                        }
                                    }

                                    if (myPredetors.get(i).getX() == j && myPredetors.get(i).getY() == k){
                                        if (gameGrid[j][k] == 9){
                                            myPredetors.get(i).setHunger(1);
                                            backGrounGrid[j][k] = 0;
                                            System.out.println("Predetor on health up");
                                        }else if (gameGrid[j][k] == 10){
                                            myPredetors.get(i).setThirst(1);
                                            gameGrid[j][k] = 0;
                                            System.out.println("Predetor on thirst up");
                                        }else if (gameGrid[j][k] == 11){
                                            randWeather = (int)(Math.random()*25);
                                            backGrounGrid[j][k] = 0;
                                            System.out.println("predetor on weather control");
                                        }
                                    }


                                }
                            }

                            packFollowsPreyToKill(gameGrid,backGrounGrid,myPredetors);


                            if (myPredetors.size() <= maxPredetorNum){
//new features added into reproduse
                                for (int j = 0;j<myPredetors.size();j++){
                                    if(backGrounGrid[myPredetors.get(i).getX()][myPredetors.get(i).getY()] == 1 && decideProbability(9) > 50 && myPredetors.get(j).checkNeighborOpposite(myPredetors,i,j,evolutionPeriod) && myPredetors.get(j).isDecompose() == false){

                                        System.out.println("mated");

                                        myPredetors.get(j).setPrego(true);

                                        if (myPredetors.get(j).isPrego()){

                                            if(now - myPredetors.get(j).getPregTime() > 3000000000.0) {
                                                System.out.println("gave birth");
                                                myPredetors.get(j).reproduce(myPredetors.get(j).getTypeOfPredetor(),myPredetors,gameGrid);
//                                                    myPredetors.get(j).setPrego(false);
                                                myPredetors.get(j).setPregTime();
                                            }

                                        }

                                    }
                                }

                            }



                            myPredetors.get(i).setTypeOfAge(0);

                            myPredetors.get(i).setAge(1);

                            myPredetors.get(i).resetStartTime();
                        }

                        if (myPredetors.get(i).getAge() > 10 && myPredetors.get(i).getAge() < 30){
                            myPredetors.get(i).setTypeOfAge(1);
//                            System.out.println(myPredetors.get(i).getTypeOfAge());

                        }else if (myPredetors.get(i).getAge() >= 30 && myPredetors.get(i).getAge() < 50){
                            myPredetors.get(i).setTypeOfAge(2);
//                            System.out.println(myPredetors.get(i).getTypeOfAge());

                        }else if (myPredetors.get(i).getAge() >= 50){

                            if (decideProbability(1) >= 5){

                                gameGrid[myPredetors.get(i).getX()][myPredetors.get(i).getY()] = myPredetors.get(i).getDecomposeLimit();
                                myPredetors.get(i).setDecompose(true);

                            }

                        }

                        if (myPredetors.get(i).isDecompose() == true){
//                        myPredetors.get(i).setDecomposionTime();
                            if (now - myPredetors.get(i).getDecomposionTime() > 2000000000.0){
                                gameGrid[myPredetors.get(i).getX()][myPredetors.get(i).getY()] = myPredetors.get(i).getDecomposeLimit();
                                myPredetors.get(i).setDecomposeLimit(-1);
                                System.out.println(myPredetors.get(i).getDecomposeLimit());
                                myPredetors.get(i).setDecomposionTime();
                            }

                        }

                        if (myPredetors.get(i).getDecomposeLimit() <= -9){
                            gameGrid[myPredetors.get(i).getX()][myPredetors.get(i).getY()] = 0;
//                                myPredetors.get(i).setDecomposionTime();
                            myPredetors.remove(i);
                        }



                    }
                }

                if (myPrey.size() > 0){
                    for (int i = 0;i<myPrey.size();i++){


                        if(now - myPrey.get(i).getStartTime() > 1000000000.0){

                            if (myPrey.get(i).isDecompose() == false){
                                if (myPrey.get(i).getTypeOfPrey().equals("Bunnies")){
                                    myPrey.get(i).changeLoc(gameGrid,backGrounGrid,false,myPredetors,-1,myPrey);
                                }else if (myPrey.get(i).getTypeOfPrey().equals("Dear")){
                                    myPrey.get(i).changeLoc(gameGrid,backGrounGrid,false,myPredetors,-1,myPrey);
                                }
                            }


                            if (myPrey.get(i).getHunger() < 0 || myPrey.get(i).getThirst() < 0){
                                gameGrid[myPrey.get(i).getX()][myPrey.get(i).getY()] = myPrey.get(i).getDecomposeLimit();
                                myPrey.get(i).setDecompose(true);
                            }




                            myPrey.get(i).setTypeOfAge(0);
//                            System.out.println(myPredetors.get(i).getTypeOfAge());
                            myPrey.get(i).setAge(1);
//                            System.out.println(myPredetors.get(i).getAge());

                            myPrey.get(i).resetStartTime();
                        }

                        if (myPrey.get(i).getAge() > 3 && myPrey.get(i).getAge() < 8){
                            myPrey.get(i).setTypeOfAge(1);
//                            System.out.println(myPredetors.get(i).getTypeOfAge());

                        }else if (myPrey.get(i).getAge() >= 8){
                            myPrey.get(i).setTypeOfAge(2);
//                            System.out.println(myPredetors.get(i).getTypeOfAge());

                        }


                        if (myPrey.get(i).getAge() > 10 && myPrey.get(i).getAge() < 30){
                            myPrey.get(i).setTypeOfAge(1);
//                            System.out.println(myPredetors.get(i).getTypeOfAge());

                        }else if (myPrey.get(i).getAge() >= 30 && myPrey.get(i).getAge() < 50){
                            myPrey.get(i).setTypeOfAge(2);
//                            System.out.println(myPredetors.get(i).getTypeOfAge());

                        }else if (myPrey.get(i).getAge() >= 50){

                            if (decideProbability(1) >= 5){

                                gameGrid[myPrey.get(i).getX()][myPrey.get(i).getY()] = myPrey.get(i).getDecomposeLimit();
                                myPrey.get(i).setDecompose(true);

                                if (myPrey.get(i).isDecompose() == true){
//                        myPredetors.get(i).setDecomposionTime();
                                    if (now - myPrey.get(i).getDecomposionTime() > 2000000000.0){
                                        gameGrid[myPrey.get(i).getX()][myPrey.get(i).getY()] = myPrey.get(i).getDecomposeLimit();
                                        myPrey.get(i).setDecomposeLimit(-1);
                                        System.out.println(myPrey.get(i).getDecomposeLimit());
                                        myPrey.get(i).setDecomposionTime();
                                    }

                                }

                                if (myPrey.get(i).getDecomposeLimit() <= -9){
                                    gameGrid[myPrey.get(i).getX()][myPrey.get(i).getY()] = 0;
//                                myPredetors.get(i).setDecomposionTime();
                                    myPrey.remove(i);
                                }


                            }

                        }

                        for (int j = 0; j < backGrounGrid.length; j++) {
                            for (int k = 0; k < backGrounGrid[0].length; k++) {

                                //on the lake
                                if (myPrey.get(i).getX() == j && myPrey.get(i).getY() == k && backGrounGrid[j][k] == 5 && myPrey.get(i).getThirst() < 5 && evaportaionTime < waterMultiValue){
                                    myPrey.get(i).setContinueMoving(false);
                                    if (now - myPrey.get(i).getDrinkingTime() > 5000000000.0 && myPrey.get(i).isContinueMoving() == false){

                                        myPrey.get(i).setContinueMoving(true);
                                        myPrey.get(i).changeLoc(gameGrid,backGrounGrid,false,myPredetors,-1,myPrey);
                                        System.out.println("set to true on wRER");
                                        myPrey.get(i).setThirst(1);//the higher the thrist the better
                                        myPrey.get(i).setDrinkingTime();
                                    }
                                }

//                                    System.out.println(continueMoving);

                                if (myPrey.get(i).getX() == j && myPrey.get(i).getY() == k && (backGrounGrid[j][k] == 6 || backGrounGrid[j][k] == 7 ) && myPrey.get(i).getHunger() < 5 && evaportaionTime < waterMultiValue){
                                    myPrey.get(i).setContinueMoving(false); //on the food patch
//                                        System.out.println("on gradss");
                                    if (now - myPrey.get(i).getEatingTime() > 5000000000.0 && myPrey.get(i).isContinueMoving() == false){
                                        System.out.println("set to true on gradd");
                                        myPrey.get(i).setContinueMoving(true);
                                        myPrey.get(i).changeLoc(gameGrid,backGrounGrid,false,myPredetors,-1,myPrey);
                                        myPrey.get(i).setHunger(1);//the higher the thrist the better
                                        myPrey.get(i).setEatingTime();
                                    }
                                }

                                if (myPrey.get(i).getX() == j && myPrey.get(i).getY() == k){
                                    if (gameGrid[j][k] == 9){
                                        myPrey.get(i).setHunger(1);
                                        gameGrid[j][k] = 0;
                                        System.out.println("Prey on health up");
                                    }else if (gameGrid[j][k] == 10){
                                        myPrey.get(i).setThirst(1);
                                        gameGrid[j][k] = 0;
                                        System.out.println("Prey on thirst up");
                                    }else if (gameGrid[j][k] == 11){
                                        randWeather = (int)(Math.random()*25);
                                        gameGrid[j][k] = 0;
                                        System.out.println("Prey on weather control");
                                    }
                                }


                            }
                        }


                    }
                }

                updateScreen();

            }
        }.start();
    }



    public int decideProbability(int chance){
        switch (chance) {
            case 0://greater chance of happening
                return (int)(Math.random()*10);
            case 1:
                return (int)(Math.random()*20);
            case 2:
                return (int)(Math.random()*30);
            case 3:
                return (int)(Math.random()*40);
            case 4:
                return (int)(Math.random()*50);
            case 5:
                return (int)(Math.random()*60);
            case 6:
                return (int)(Math.random()*70);
            case 7:
                return (int)(Math.random()*80);
            case 8:
                return (int)(Math.random()*90);
            case 9://lesser chance of happening
                return (int)(Math.random()*100);
            default:
                return chance;
        }
    }

    @FXML
    private void btnSetMaxAmountOfPredetors(){
        int numInp = Integer.parseInt(txtMaxAmountPred.getText());
        maxPredetorNum = numInp;
        lblPedetorNum.setText(numInp + "");
        buttonSetMaxPred.setDisable(true);
    }

    @FXML
    private void btnSetMaxAmountOPrey(){
        int numInp = Integer.parseInt(txtMaxAmountPrey.getText());

        lblMaxPreyNum.setText(numInp + "");
        buttonMaxPrey.setDisable(true);
    }

    private void checkAnimalToSpawn(int randX,int randY,String ani){
//        System.out.println("x "+ randX);
//        System.out.println("y " + randY);

        if (ani.equals("Wolves") && tempAnimal.equals("Wolves")){
            myPredetors.add(new Predetor("Wolves",randX,randY));
            gameGrid[randX][randY] = 1;
        }else if (ani.equals("Foxes")&& tempAnimal.equals("Foxes")){
            myPredetors.add(new Predetor("Foxes",randX,randY));
            gameGrid[randX][randY] = 1;
        }else if (ani.equals("Bunnies")&& tempAnimal.equals("Bunnies")){
            myPrey.add(new Prey("Bunnies",randX,randY));
            gameGrid[randX][randY] = 1;
        }else if (ani.equals("Dear")&& tempAnimal.equals("Dear")){
            myPrey.add(new Prey("Dear",randX,randY));
            gameGrid[randX][randY] = 1;
        }




    }

    private void spawnAnimals(String area){
//        int randXForest = (int)(Math.random()*12);
//        int randYForest = (int)(Math.random()*14);

        for (int i = 0; i < predetors.size(); i++) {
            if (area.equals("Forest")){
                checkAnimalToSpawn((int)(Math.random()*12),(int)(Math.random()*12),predetors.get(i));

            }else if (area.equals("Food Patch")){
                checkAnimalToSpawn((int)(Math.random()*12),(int)(Math.random()*12)+12,predetors.get(i));

            }else if (area.equals("Open Field")){
                checkAnimalToSpawn((int)(Math.random()*12)+12,(int)(Math.random()*12),predetors.get(i));

            }else if (area.equals("Watering Hole")){
                checkAnimalToSpawn((int)(Math.random()*12)+12,(int)(Math.random()*12)+12,predetors.get(i));

            }

        }

        for (int i = 0; i < prey.size(); i++) {
            if (area.equals("Forest")){
                checkAnimalToSpawn((int)(Math.random()*12),(int)(Math.random()*12),prey.get(i));

            }else if (area.equals("Food Patch")){
                checkAnimalToSpawn((int)(Math.random()*12),(int)(Math.random()*12)+12,prey.get(i));

            }else if (area.equals("Open Field")){
                checkAnimalToSpawn((int)(Math.random()*12)+12,(int)(Math.random()*12),prey.get(i));

            }else if (area.equals("Watering Hole")){
                checkAnimalToSpawn((int)(Math.random()*12)+12,(int)(Math.random()*12)+12,prey.get(i));

            }

        }

        updateScreen();
    }

    @FXML
    private void btnForestArea(){
        spawnAnimals("Forest");

    }

    @FXML
    private void btnFoodPatch(){
        spawnAnimals("Food Patch");


    }

    @FXML
    private void btnOpenFeild(){
        spawnAnimals("Open Field");

    }

    @FXML
    private void btnWateringHole(){
        spawnAnimals("Watering Hole");

    }


    @FXML
    private void btnTriggerRainyDay(){
        weatherClicked = "Rain";
    }

    @FXML
    private void btnTriggerDrought(){
        waterMultiValue = 1;
        evaportaionTime = 0;
        lblWeatherChangeExp.setText(" 1 Day ");
    }
    @FXML
    private void dragedWeatherRates(){
        System.out.println("Drag detected");
//        weatherChosenSlider.getValue();
        if (weatherClicked.equals("Rain")){
            System.out.println(weatherChosenSlider.getValue());
            if (weatherChosenSlider.getValue() < 40){
                waterMultiValue = 4;
                evaportaionTime = 0;
                lblWeatherChangeExp.setText(" 4 Days ");
            }else if (weatherChosenSlider.getValue() <= 40 && weatherChosenSlider.getValue() < 60){
                waterMultiValue = 6;
                evaportaionTime = 0;
                lblWeatherChangeExp.setText(" 6 Days ");
            }else if (weatherChosenSlider.getValue() >= 60){
                waterMultiValue = 8;
                evaportaionTime = 0;
                lblWeatherChangeExp.setText(" 8 Days ");
            }

        }


    }



}