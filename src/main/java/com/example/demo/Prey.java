package com.example.demo;

import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.Arrays;
public class Prey {
    private int x;
    private int y;
    private String typeOfPrey;
    private int maleFemale;
    private int age;
    private int hunger;
    private int thirst;
    private int typeOfAge;
    String loc;
    private long startPreyTime;
    private long drinkingTime;
    private long eatingTime;
    private long reproduceTime;
    private boolean runAway;
    private boolean searchResources;

    int decomposeLimit;
    boolean isDecompose;
    long decomposionTime;
    private boolean continueMoving;
    private boolean shooAwayFromDecom = false;

    //    private long ageTime;
    private String colors;
    private ArrayList<Locations> tempLocs = new ArrayList<>();

    int tempx;
    int tempy;

    public Prey(String type,int x,int y){
        //0 = male; 1 = female
        maleFemale = (int)(Math.random()*2);
        eatingTime = System.nanoTime();
        drinkingTime = System.nanoTime();
        this.x = x;
        this.y = y;
        this.age = 0;//0 is child, 1 is a adult, 2 is a elder
        this.typeOfAge = 0;
        startPreyTime = System.nanoTime();
        reproduceTime = System.nanoTime();
        typeOfPrey = type;
        runAway = false;
        searchResources = false;
        hunger = 0;
        thirst = 0;
        continueMoving = true;
    }
    public boolean isContinueMoving() {
        return continueMoving;
    }

    public void setContinueMoving(boolean continueMoving) {
        this.continueMoving = continueMoving;
    }

    public int getThirst() {
        return thirst;
    }

    public void setThirst(int thirst) {
        this.thirst += thirst;
    }

    public void setHunger(int hunger) {
        this.hunger += hunger;
    }

    public int getHunger() {
        return hunger;
    }

    public long getDrinkingTime() {
        return drinkingTime;
    }

    public void setDrinkingTime() {
        drinkingTime = System.nanoTime();
    }

    public void setEatingTime() {
        eatingTime = System.nanoTime();
    }

    public long getEatingTime() {
        return eatingTime;
    }

    public int getDecomposeLimit() {
        return decomposeLimit;
    }

    public void setDecomposeLimit(int decomposeLimit) {
        this.decomposeLimit += decomposeLimit;
    }

    public long getDecomposionTime() {
        return decomposionTime;
    }

    public void setDecomposionTime() {
        decomposionTime = System.nanoTime();
    }

    public boolean isDecompose() {
        return isDecompose;
    }

    public void setDecompose(boolean decompose) {
        isDecompose = decompose;
    }

    public void setThirstAndHungerToZero(){
        thirst = 0;
        hunger = 0;
    }

    public boolean isRunAway() {
        return runAway;
    }

    public void setRunAway(boolean runAway) {
        this.runAway = runAway;
    }

    public void runAwayMoveAwayFromPred(ArrayList<Predetor> tempPred, ArrayList<Prey> tempPrey, int[][] gameGrid,int predIndex){
        runAway = true;
        if (x < tempPred.get(predIndex).getX() && y > tempPred.get(predIndex).getY()){
            tempx--;
            tempy++;

        }else if (x < tempPred.get(predIndex).getX() && y < tempPred.get(predIndex).getY()){//topleft
            tempx--;
            tempy--;

        }else if (x > tempPred.get(predIndex).getX() && y < tempPred.get(predIndex).getY()){//bottom left
            tempx++;
            tempy--;

        }else if (x > tempPred.get(predIndex).getX() && y > tempPred.get(predIndex).getY()){//bottom right
            tempx++;
            tempy++;

        }else if (x < tempPred.get(predIndex).getX() && y == tempPred.get(predIndex).getY()){//top
            tempx--;

        }else if (x == tempPred.get(predIndex).getX() && y < tempPred.get(predIndex).getY()){//left
            tempy--;

        }else if (x > tempPred.get(predIndex).getX() && y == tempPred.get(predIndex).getY()){//bottom
            tempx++;

        }else if (x == tempPred.get(predIndex).getX() && y > tempPred.get(predIndex).getY()){//right
            tempy++;

        }
    }

    public void changeLoc(int[][] gameGrid, int[][] backGroundGrid,boolean run,ArrayList<Predetor> tempPred, int predIndex,ArrayList<Prey> tempPrey){
//        System.out.println("test1");
        boolean check = false;
        int count = 0;
        while(!check){
//            System.out.println("y in while: " + y);
//            System.out.println("x in while: " + x);
//            System.out.println("while");
            count++;

            if(count==7){
                break;
            }

            tempx = x;
            tempy = y;

            for (int j = 0; j < tempPrey.size(); j++) {
                for (int i = 0; i < tempPred.size(); i++) {
                    for (int k = tempPrey.get(j).getX()-2; k < tempPrey.get(j).getX()+2; k++) {
//                        System.out.println("k is: " + k);
                        for (int z = tempPrey.get(j).getY()-2; z < tempPrey.get(j).getY()+2; z++) {
//                            System.out.println("z is: " + z);
                            if (k >= 0 && z >= 0 && k < gameGrid.length && z < gameGrid[0].length){

                                if(x >= k && x <= tempPrey.get(j).getX()+2 &&
                                        y >= z && y <= tempPrey.get(j).getY()+2 && (tempPrey.get(j).isDecompose() == true || tempPred.get(i).isDecompose() == true) ){
                                    shooAwayFromDecom = true;


                                }else{
                                    shooAwayFromDecom = false;
                                }
                            }


                        }
                    }
                    if (shooAwayFromDecom == true){
                        if ((x < tempPred.get(i).getX() && y > tempPred.get(i).getY()) || (x < tempPrey.get(j).getX() && y > tempPrey.get(j).getY())){
                            tempx--;
                            tempy++;
                            break;
                        }else if ((x < tempPred.get(i).getX() && y < tempPred.get(i).getY())||(x < tempPrey.get(j).getX() && y < tempPrey.get(j).getY())){//topleft
                            tempx--;
                            tempy--;
                            break;
                        }else if ((x > tempPred.get(i).getX() && y < tempPred.get(i).getY()) || (x > tempPrey.get(j).getX() && y < tempPrey.get(j).getY())){//bottom left
                            tempx++;
                            tempy--;
                            break;
                        }else if ((x > tempPred.get(i).getX() && y > tempPred.get(i).getY()) || (x > tempPrey.get(j).getX() && y > tempPrey.get(j).getY())){//bottom right
                            tempx++;
                            tempy++;
                            break;
                        }else if ((x < tempPred.get(i).getX() && y == tempPred.get(i).getY()) || (x < tempPrey.get(j).getX() && y == tempPrey.get(j).getY())){//top
                            tempx--;
                            break;
                        }else if ((x == tempPred.get(i).getX() && y < tempPred.get(i).getY()) || (x == tempPrey.get(j).getX() && y < tempPrey.get(j).getY())){//left
                            tempy--;
                            break;
                        }else if ((x > tempPred.get(i).getX() && y == tempPred.get(i).getY()) || (x > tempPrey.get(j).getX() && y == tempPrey.get(j).getY())){//bottom
                            tempx++;
                            break;
                        }else if ((x == tempPred.get(i).getX() && y > tempPred.get(i).getY()) || (x == tempPrey.get(j).getX() && y > tempPrey.get(j).getY())){//right
                            tempy++;
                            break;
                        }
                    }
                }





            }


            if (run == true){
                if (x < tempPred.get(predIndex).getX() && y > tempPred.get(predIndex).getY()){
                    tempx--;
                    tempy++;

                }else if (x < tempPred.get(predIndex).getX() && y < tempPred.get(predIndex).getY()){//topleft
                    tempx--;
                    tempy--;

                }else if (x > tempPred.get(predIndex).getX() && y < tempPred.get(predIndex).getY()){//bottom left
                    tempx++;
                    tempy--;

                }else if (x > tempPred.get(predIndex).getX() && y > tempPred.get(predIndex).getY()){//bottom right
                    tempx++;
                    tempy++;

                }else if (x < tempPred.get(predIndex).getX() && y == tempPred.get(predIndex).getY()){//top
                    tempx--;

                }else if (x == tempPred.get(predIndex).getX() && y < tempPred.get(predIndex).getY()){//left
                    tempy--;

                }else if (x > tempPred.get(predIndex).getX() && y == tempPred.get(predIndex).getY()){//bottom
                    tempx++;

                }else if (x == tempPred.get(predIndex).getX() && y > tempPred.get(predIndex).getY()){//right
                    tempy++;

                }

            }else{
                if(Math.random()>.5){
                    tempx++;
                }else {
                    tempx--;
                }

                if(Math.random()>.5){
                    tempy++;
                }else {
                    tempy--;
                }
            }




            for (int i = 0; i < backGroundGrid.length; i++) {
                for (int j = 0; j < backGroundGrid[0].length; j++) {

                    if (tempx < 0 && tempy < 0){

                        tempx = 1;
                        tempy = 1;
//                System.out.println("went out at tempx < 0 && tempy < 0");

                    } else if (tempx >= gameGrid.length && tempy >= gameGrid[0].length){

                        tempx = gameGrid.length-1;
                        tempy = gameGrid[0].length-1;
//                System.out.println("went out at tempx > gameGrid.length && tempy > gameGrid[0].length");

                    }else if (tempx < 0 && tempy >= gameGrid[0].length){

                        tempx = 1;
                        tempy = gameGrid[0].length-1;
//                System.out.println("went out at tempx < 0 && tempy > gameGrid[0].length");

                    }else if (tempy < 0 && tempx >= gameGrid.length){

                        tempy = 1;
                        tempx = gameGrid.length-1;
//                System.out.println("went out at tempy < 0 && tempx > gameGrid.length");

                    }else if (tempx < 0){
//                tempx += 1;
                        tempx = 1;
//                System.out.println("went out at tempx < 0");

                    }else if (tempy < 0){

                        tempy = 1;
//                System.out.println("went out at tempy < 0");

                    }else if (tempx >= gameGrid.length){
//                tempx -= 1;
                        tempx = gameGrid.length-1;
//                System.out.println("went out at tempx > gameGrid.length");

                    }else if (tempy >= gameGrid[0].length){

                        tempy = gameGrid[0].length-1;
//                System.out.println("went out at tempy > gameGrid[0].length");
                    }


                }
            }


            if (gameGrid[tempx][tempy] == 0){
//                System.out.println("good to go");
                gameGrid[x][y] = 0;

                gameGrid[tempx][tempy] = 1;
                x=tempx;
                y=tempy;
            }else{
//                System.out.println("overlaped, checking again");
                if(Math.random()>.5){
                    tempx++;
                }else {
                    tempx--;
                }

                if(Math.random()>.5){
                    tempy++;
                }else {
                    tempy--;
                }
            }


            runAway = false;
            check = true;

        }

//          System.out.println("x: " + x);

    }

    public boolean checkNeighborOpposite(ArrayList<Prey> tempPrey){

        for (int i = 0;i<tempPrey.size();i++){
//                System.out.println(tempAnt.get(i).getX());
            if(tempPrey.get(i).getX() >=x-1 && tempPrey.get(i).getX()<=x+1 &&
                    tempPrey.get(i).getY() >=y-1 && tempPrey.get(i).getY()<=y+1 &&
                    tempPrey.get(i).getX()!=x && tempPrey.get(i).getY()!=y &&
                    maleFemale != tempPrey.get(i).getMaleFemale()){

                return true;
            }
        }

        return false;
    }

    public boolean checkEmptyAroundMe(int i, int j, int tempGrid[][]){
        return tempGrid[i][j] ==0;
    }

    public void reproduce(ArrayList<Prey> tempWolf, int tempGrid[][]){

        tempLocs.clear();
        for(int i = x-1; i<x+2;i++){
            for(int j = y-1;j<y+2;j++){
                if(x<tempGrid.length && y <tempGrid[0].length){
                    if(checkEmptyAroundMe(i,j,tempGrid)){
                        System.out.println("j:" + j);
                        tempLocs.add(new Locations(i,j));
                    }
                }
            }
        }
        if(tempLocs.size()>0){
            int newLoc = (int)(Math.random()*tempLocs.size());
//            System.out.println("y: " +tempLocs.get(newLoc).getY());
//            tempWolf.add(new Predetor(tempLocs.get(newLoc).getX(),tempLocs.get(newLoc).getY()));
//            System.out.println("y: " +tempLocs.get(newLoc).getY());
            tempGrid[tempWolf.get(tempWolf.size()-1).getX()][tempWolf.get(tempWolf.size()-1).getY()]=1;
//            System.out.println("blah" + tempAnt.get(tempAnt.size()-1).getY());
            reproduceTime = System.nanoTime();
        }

    }

//    public void lookForSpeicalResources(ArrayList<Prey> tempPrey, int[][] backgroundGrid, int[][] gameGrid){
//
//        for (int i = 0; i < backgroundGrid.length; i++) {
//            for (int l = 0; l < backgroundGrid[0].length; l++) {
//
//                if(i >= x-1 && i <= x+1 &&
//                        l >= y-1 && l <= y+1 &&
//                        i !=x && l !=y &&
//                        (backgroundGrid[i][l] == 9 || backgroundGrid[i][l] == 10 || backgroundGrid[i][l] == 11)){
//                    searchResources = true;
//                }
//            }
//        }
//
//    }


    //////Getters and setters

    public int getX(){
        return this.x;
    }
    public int getY() {
        return this.y;
    }

    public String getTypeOfPrey() {
        return typeOfPrey;
    }

    public int getMaleFemale() {
        return maleFemale;
    }

    public void setAge(int age) {
        this.age += age;
    }
    public int getAge() {
        return age;
    }

    public void setTypeOfAge(int typeOfAge) {
        this.typeOfAge = typeOfAge;
    }

    public int getTypeOfAge() {
        return typeOfAge;
    }

    public void resetStartTime(){
        startPreyTime = System.nanoTime();
    }
    public long getStartTime(){
        return startPreyTime;
    }

    public void resetReproduceTime(){
        reproduceTime = System.nanoTime();
    }
    public long getReproduceTime() {
        return reproduceTime;
    }


    public void getColors(ArrayList<Prey> myPred, Button[][] btn) {

        for (int i = 0; i < myPred.size(); i++) {

            if (myPred.get(i).getTypeOfPrey().equals("Bunnies")){
                if (myPred.get(i).getTypeOfAge() == 0){
//                    System.out.println("Ret 1");


                    if (myPred.get(i).getMaleFemale() == 1){
                        btn[myPred.get(i).getX()][myPred.get(i).getY()].setStyle("-fx-background-color:linear-gradient(#ff00cc,#f18aff)");
                    }else{
                        btn[myPred.get(i).getX()][myPred.get(i).getY()].setStyle( "-fx-background-color:#f18aff");

                    }


                }else if (myPred.get(i).getTypeOfAge() == 1){
//                    System.out.println("Ret 2");

                    if (myPred.get(i).getMaleFemale() == 1){
                        btn[myPred.get(i).getX()][myPred.get(i).getY()].setStyle("-fx-background-color:linear-gradient(#ff00cc,#e000ff)");
                    }else{
                        btn[myPred.get(i).getX()][myPred.get(i).getY()].setStyle("-fx-background-color:#e000ff");

                    }


                }else if (myPred.get(i).getTypeOfAge() == 2){
//                    System.out.println("Ret 3");

                    if (myPred.get(i).getMaleFemale() == 1){
                        btn[myPred.get(i).getX()][myPred.get(i).getY()].setStyle("-fx-background-color:linear-gradient(#ff00cc,#8b3b96)");
                    }else{
                        btn[myPred.get(i).getX()][myPred.get(i).getY()].setStyle("-fx-background-color:#8b3b96");

                    }

//                    return "-fx-background-color:#728c79";

                }
            }

            if (myPred.get(i).getTypeOfPrey().equals("Dear")){

                if (myPred.get(i).getTypeOfAge() == 0){
//                    System.out.println("Ret 4");

                    if (myPred.get(i).getMaleFemale() == 1){
                        btn[myPred.get(i).getX()][myPred.get(i).getY()].setStyle("-fx-background-color:linear-gradient(#ff00cc,#f1ff75)");
                    }else{
                        btn[myPred.get(i).getX()][myPred.get(i).getY()].setStyle("-fx-background-color:#f1ff75");

                    }

//                    return "-fx-background-color:#ebb878";

                }else if (myPred.get(i).getTypeOfAge() == 1){
//                    System.out.println("Ret 5");

                    if (myPred.get(i).getMaleFemale() == 1){
                        btn[myPred.get(i).getX()][myPred.get(i).getY()].setStyle("-fx-background-color:linear-gradient(#ff00cc,#f1ff75)");
                    }else{
                        btn[myPred.get(i).getX()][myPred.get(i).getY()].setStyle("-fx-background-color:#f1ff75");

                    }
//                    return "-fx-background-color:#ff8e00";

                }else if (myPred.get(i).getTypeOfAge() == 2){
//                    System.out.println("Ret 6");

                    if (myPred.get(i).getMaleFemale() == 1){
                        btn[myPred.get(i).getX()][myPred.get(i).getY()].setStyle("-fx-background-color:linear-gradient(#ff00cc,#97a32f)");
                    }else{
                        btn[myPred.get(i).getX()][myPred.get(i).getY()].setStyle("-fx-background-color:#97a32f");

                    }

//                    return "-fx-background-color:#804e0f";

                }
            }

        }

//        return "#000000";
    }

}
