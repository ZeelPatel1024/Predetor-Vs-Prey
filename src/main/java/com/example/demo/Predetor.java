package com.example.demo;

import javafx.scene.control.Button;
import org.w3c.dom.UserDataHandler;

import java.util.ArrayList;
import java.util.Arrays;

public class Predetor {
    private int x;
    private int y;
    private String typeOfPredetor;
    private int maleFemale;
    private int age;
    private int typeOfAge;
    String loc;
    private long startWolfTime;
    private long reproduceTime;

    /////traits of animal
    private int hunger;
    private int thirst;
    private int ego;
    private int intelligence;

    private long pregTime;
    private boolean isPrego;

    private long drinkingTime;
    private long eatingTime;
    private boolean isDecompose;

    private boolean foundPreyMove;

    private long moveTime;
    private long moveSnowTime;

    int decomposeLimit;

    long decomposionTime;


    private int preyIndex;

    private boolean allowOtherMating = false;

    private boolean shooAwayFromDecom = false;

    private String rank;
    int health;

//    private long ageTime;
    private String colors;
    private ArrayList<Locations> tempLocs = new ArrayList<>();

    private ArrayList<Integer> predLocs = new ArrayList<>();
    private ArrayList<Integer> predTargetLocs = new ArrayList<>();
    private ArrayList<Integer> xDist = new ArrayList<>();
    private ArrayList<Integer> yDist = new ArrayList<>();


    public Predetor(String type,int x,int y){
        //0 = male; 1 = female
        maleFemale = (int)(Math.random()*2);
        this.x = x;
        decomposeLimit = -1;
        this.y = y;
        this.age = 0;//0 is child, 1 is a adult, 2 is a elder
        this.typeOfAge = 0;
        isDecompose = false;
        health = 20;
        intelligence = (int)(Math.random()*30+1);
        hunger = 10;
        thirst = 10;
        ego = 0;
        rank = " ";
        startWolfTime = System.nanoTime();
        moveTime = System.nanoTime();
        reproduceTime = System.nanoTime();
        pregTime = System.nanoTime();
        eatingTime = System.nanoTime();
        drinkingTime = System.nanoTime();
        isPrego = false;
        decomposionTime = System.nanoTime();
        typeOfPredetor = type;
        continueMoving = true;
        foundPreyMove = true;
//        colors = c;

    }

    public long getMoveTime() {
        return moveTime;
    }

    public void setMoveTime() {
        moveTime = System.nanoTime();
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

//    private boolean foundPreyMove = false;

    public boolean isFoundPreyMove() {
        return foundPreyMove;
    }

    public void setFoundPreyMove(boolean foundPreyMove) {
        this.foundPreyMove = foundPreyMove;
    }

    public void changeLoc(int[][] gameGrid, int[][] backGroundGrid, ArrayList<Predetor> tempPred, ArrayList<Prey> tempPrey){
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

            int tempx = x;
            int tempy = y;

            for (int j = 0; j < tempPred.size(); j++) {

                for (int k = tempPred.get(j).getX()-2; k < tempPred.get(j).getX()+2; k++) {
//                        System.out.println("k is: " + k);
                    for (int z = tempPred.get(j).getY()-2; z < tempPred.get(j).getY()+2; z++) {
//                            System.out.println("z is: " + z);
                        if (k >= 0 && z >= 0 && k < gameGrid.length && z < gameGrid[0].length){

                            if(x >= k && x <= tempPred.get(j).getX()+2 &&
                                    y >= z && y <= tempPred.get(j).getY()+2 && tempPred.get(j).isDecompose() == true){
                                shooAwayFromDecom = true;


                            }else{
                                shooAwayFromDecom = false;
                            }
                        }


                    }
                }

                if (shooAwayFromDecom == true){
                    if (x < tempPred.get(j).getX() && y > tempPred.get(j).getY()){
                        tempx--;
                        tempy++;
                        break;
                    }else if (x < tempPred.get(j).getX() && y < tempPred.get(j).getY()){//topleft
                        tempx--;
                        tempy--;
                        break;
                    }else if (x > tempPred.get(j).getX() && y < tempPred.get(j).getY()){//bottom left
                        tempx++;
                        tempy--;
                        break;
                    }else if (x > tempPred.get(j).getX() && y > tempPred.get(j).getY()){//bottom right
                        tempx++;
                        tempy++;
                        break;
                    }else if (x < tempPred.get(j).getX() && y == tempPred.get(j).getY()){//top
                        tempx--;
                        break;
                    }else if (x == tempPred.get(j).getX() && y < tempPred.get(j).getY()){//left
                        tempy--;
                        break;
                    }else if (x > tempPred.get(j).getX() && y == tempPred.get(j).getY()){//bottom
                        tempx++;
                        break;
                    }else if (x == tempPred.get(j).getX() && y > tempPred.get(j).getY()){//right
                        tempy++;
                        break;
                    }
                }



            }


            if (isFollowPrey() == false && shooAwayFromDecom == false){
//                System.out.println("move rand");

                    if(Math.random()>.5){
                        tempx++;
                    }else {
                        tempx--;
                    }

                    if(Math.random()<.5){
                        tempy++;
                    }else {
                        tempy--;
                    }

            }else{
//                System.out.println("fixed MOve");
                System.out.println("fixed move: " + isFoundPreyMove());
                if (tempPrey.size()-1 > 0 && isFoundPreyMove() == true){

                    setTempX(tempPrey.get(getPreyIndex()).getX() - x);
                    setTempY(tempPrey.get(getPreyIndex()).getY() - y);

                    if (x != tempPrey.get(getPreyIndex()).getX() || y != tempPrey.get(getPreyIndex()).getY()){


                        tempPrey.get(getPreyIndex()).changeLoc(gameGrid,backGroundGrid,true,tempPred,getPreyIndex(),tempPrey);

                        if (getTempX() > 0){

                            tempx--;

                        }else if (getTempX() < 0){

                            tempx++;

                        }

                        if (getTempY() > 0){

                            tempy--;

                        }else if (getTempY() < 0){

                            tempy++;

                        }

                        setFoundPreyMove(false);

                    }

                }

            }




            //IN BOUNDS
            if (tempx < 0 && tempy < 0){

                tempx = 1;
                tempy = 1;

            } else if (tempx > gameGrid.length && tempy > gameGrid[0].length){

                tempx = gameGrid.length-1;
                tempy = gameGrid[0].length-1;
//                System.out.println("went out at tempx > gameGrid.length && tempy > gameGrid[0].length");

            }else if (tempx < 0 && tempy > gameGrid[0].length){

                tempx = 1;
                tempy = gameGrid[0].length-1;
//                System.out.println("went out at tempx < 0 && tempy > gameGrid[0].length");

            }else if (tempy < 0 && tempx > gameGrid.length){

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

            }else if (tempx > gameGrid.length){
//                tempx -= 1;
                tempx = gameGrid.length-1;
//                System.out.println("went out at tempx > gameGrid.length");

            }else if (tempy > gameGrid[0].length){

                tempy = gameGrid[0].length-1;
//                System.out.println("went out at tempy > gameGrid[0].length");
            }
//            System.out.println("tempX: " + tempx);
//            System.out.println("tempY" + tempy);
            if (tempx < 25 && tempy < 25){

                if (gameGrid[tempx][tempy] == 0){
//                System.out.println("good to go");
                    gameGrid[x][y] = 0;
                    gameGrid[tempx][tempy] = 1;
                    x=tempx;
                    y=tempy;

                } else{
//                System.out.println("overlaped, checking again");
                    if(Math.random()>.5){
                        tempx++;
                    }else {
                        tempx--;
                    }

                    if(Math.random()<.5){
                        tempy++;
                    }else {
                        tempy--;
                    }
                }
            }


            check = true;

        }

    }

    private int tempX;
    private int tempY;

    private boolean continueMoving;

    public boolean isContinueMoving() {
        return continueMoving;
    }

    public void setContinueMoving(boolean continueMoving) {
        this.continueMoving = continueMoving;
    }

    public int getPreyIndex() {
        return preyIndex;
    }

    public void setPreyIndex(int preyIndex) {
        this.preyIndex = preyIndex;
    }

    public int getTempX() {
        return tempX;
    }

    public int getTempY() {
        return tempY;
    }

    public void setTempX(int tempX) {
        this.tempX += tempX;
    }

    public void setTempY(int tempY) {
        this.tempY += tempY;
    }

    boolean followPrey = false;

    public boolean isFollowPrey() {
        return followPrey;
    }

    public void setFollowPrey(boolean followAlpha) {
        this.followPrey = followAlpha;
    }

    public boolean checkNeighborOpposite(ArrayList<Predetor> tempPred, int i, int i2, int changeEvolution){

        if (changeEvolution > 10){
            if(tempPred.get(i).getX() >= x-1 && tempPred.get(i).getX() <= x+1 &&
                    tempPred.get(i).getY() >= y-1 && tempPred.get(i).getY() <= y+1 &&
                    tempPred.get(i).getX()!=x && tempPred.get(i).getY()!=y &&
                    maleFemale != tempPred.get(i).getMaleFemale() && tempPred.get(i).getTypeOfPredetor().equals(tempPred.get(i2).getTypeOfPredetor())){
                return true;
            }
        }else{
            if(tempPred.get(i).getX() >= x-1 && tempPred.get(i).getX() <= x+1 &&
                    tempPred.get(i).getY() >= y-1 && tempPred.get(i).getY() <= y+1 &&
                    tempPred.get(i).getX()!=x && tempPred.get(i).getY()!=y &&
                    maleFemale != tempPred.get(i).getMaleFemale()){
                return true;
            }
        }

        return false;
    }

    ///You need to check this
    public void checkNeighborOppositeAlpha(ArrayList<Predetor> tempPred,int[][]gameGrid){

        for (int j = 0; j < tempPred.size(); j++) {
            if(tempPred.get(j).getX() >= x-1 && tempPred.get(j).getX() <= x+1 &&
                    tempPred.get(j).getY() >= y-1 && tempPred.get(j).getY() <= y+1 &&
                    tempPred.get(j).getX()!=x && tempPred.get(j).getY()!=y && getRank().equals("Alpha") && tempPred.get(j).equals("Alpha")){


                System.out.println("1 Ego of alpha: " + getEgo());
                System.out.println("1 Intelligence of alpha: " + getIntelligence());

                System.out.println("2 Ego of alpha: " + tempPred.get(j).getEgo());
                System.out.println("2 Intelligence of alpha: " + tempPred.get(j).getIntelligence());

                if (getEgo() > 5 && getIntelligence() > 15){
                    tempPred.get(j).setHealth(-5);
                }else if (getEgo() <= 5 && getIntelligence() <= 15){
                    tempPred.get(j).setHealth(-1);
                }

                if (tempPred.get(j).getHealth() <=0){
                    System.out.println("killed the other alpha");
                    gameGrid[tempPred.get(j).getX()][tempPred.get(j).getY()] = 0;
                    tempPred.remove(j);

                }
            }
        }


    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health += health;
    }

    public int checkNeighborOppositeToKill(ArrayList<Predetor> tempPred, ArrayList<Prey> tempPrey, int[][] gamegrid){

        int killed = 0;

        for (int j = 0; j < tempPrey.size(); j++) {
            if(tempPrey.get(j).getX() >= x-1 && tempPrey.get(j).getX() <= x+1 &&
                    tempPrey.get(j).getY() >= y-1 && tempPrey.get(j).getY() <= y+1){

                gamegrid[tempPrey.get(j).getX()][tempPrey.get(j).getY()] = 0;
                tempPrey.remove(j);
                killed++;
                hunger++;
            }
        }

        return killed;

    }

    public boolean checkEmptyAroundMe(int i, int j, int tempGrid[][]){
        return tempGrid[i][j] == 0;
    }

    public void reproduce(String type, ArrayList<Predetor> tempWolf, int tempGrid[][]){

        tempLocs.clear();

        for(int i = x-1; i <x+2; i++){
            for(int j = y-1;j<y+2;j++){
                if(i <= tempGrid.length-1 && j <= tempGrid[0].length-1 && i >= 0 && j >= 0){
//                    System.out.println();
                    if(checkEmptyAroundMe(i,j,tempGrid)){
//                        System.out.println("j:" + j);
                        tempLocs.add(new Locations(i,j));
                    }
                }
            }
        }
        if(tempLocs.size()>0){
            int newLoc = (int)(Math.random()*tempLocs.size());
//            System.out.println("y: " +tempLocs.get(newLoc).getY());
            tempWolf.add(new Predetor(type,tempLocs.get(newLoc).getX(),tempLocs.get(newLoc).getY()));
//            System.out.println("y: " +tempLocs.get(newLoc).getY());
            tempGrid[tempWolf.get(tempWolf.size()-1).getX()][tempWolf.get(tempWolf.size()-1).getY()]=1;
//            System.out.println("blah" + tempAnt.get(tempAnt.size()-1).getY());
            reproduceTime = System.nanoTime();
        }

    }

    //////Getters and setters
    public int getThirst() {
        return thirst;
    }

    public void setThirst(int thirst) {
        this.thirst += thirst;
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

    public void setThirstAndHungerToZero(){
        thirst = 0;
        hunger = 0;
    }

    public void setPregTime() {
        pregTime = System.nanoTime();
    }

    public void setPrego(boolean prego) {
        isPrego = prego;
    }

    public long getPregTime() {
        return pregTime;
    }

    public boolean isPrego() {
        return isPrego;
    }

    public int getX(){
        return this.x;
    }

    public int getY() {
        return this.y;

    }

    public String getTypeOfPredetor() {
        return typeOfPredetor;
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
        startWolfTime = System.nanoTime();
    }
    public long getStartTime(){
        return startWolfTime;
    }

    public void getColors(ArrayList<Predetor> myPred, Button[][] btn) {

        for (int i = 0; i < myPred.size(); i++) {


            if (myPred.get(i).getTypeOfPredetor().equals("Wolves")){

                if (myPred.get(i).getTypeOfAge() == 0){
//                    System.out.println("Ret 1");

                    if (myPred.get(i).getMaleFemale() == 1){
                        btn[myPred.get(i).getX()][myPred.get(i).getY()].setStyle("-fx-background-color:linear-gradient(#ff00cc,#ff8f82)");
                    }else{
                        btn[myPred.get(i).getX()][myPred.get(i).getY()].setStyle( "-fx-background-color:#ff8f82");
                    }


                }else if (myPred.get(i).getTypeOfAge() == 1){
//                    System.out.println("Ret 2");
                    if (myPred.get(i).getMaleFemale() == 1){
                        btn[myPred.get(i).getX()][myPred.get(i).getY()].setStyle("-fx-background-color:linear-gradient(#ff00cc,#ff1b00)");
                    }else{
                        btn[myPred.get(i).getX()][myPred.get(i).getY()].setStyle("-fx-background-color:#ff1b00");

                    }

                }else if (myPred.get(i).getTypeOfAge() == 2){
//                    System.out.println("Ret 3");

                    if (myPred.get(i).getMaleFemale() == 1){
                        btn[myPred.get(i).getX()][myPred.get(i).getY()].setStyle("-fx-background-color:linear-gradient(#ff00cc,#750c00)");
                    }else{
                        btn[myPred.get(i).getX()][myPred.get(i).getY()].setStyle("-fx-background-color:#750c00");

                    }


//                    return "-fx-background-color:#728c79";

                }

                if (myPred.get(i).getRank().equals("Alpha") && myPred.get(i).getMaleFemale() == 1){
                    btn[myPred.get(i).getX()][myPred.get(i).getY()].setStyle("-fx-background-color:linear-gradient(#ff00cc,#0800ff)");
                }else if (myPred.get(i).getRank().equals("Alpha") && myPred.get(i).getMaleFemale() == 0){
                    btn[myPred.get(i).getX()][myPred.get(i).getY()].setStyle("-fx-background-color:#0800ff");
                }else if (myPred.get(i).getRank().equals("Beta") && myPred.get(i).getMaleFemale() == 1){
                    btn[myPred.get(i).getX()][myPred.get(i).getY()].setStyle("-fx-background-color:linear-gradient(#ff00cc,#38354f)");
                }else if (myPred.get(i).getRank().equals("Beta") && myPred.get(i).getMaleFemale() == 0){
                    btn[myPred.get(i).getX()][myPred.get(i).getY()].setStyle("-fx-background-color:#38354f");
                }

            }

            if (myPred.get(i).getTypeOfPredetor().equals("Foxes")){

                if (myPred.get(i).getTypeOfAge() == 0){
//                    System.out.println("Ret 4");

                    if (myPred.get(i).getMaleFemale() == 1){
                        btn[myPred.get(i).getX()][myPred.get(i).getY()].setStyle("-fx-background-color:linear-gradient(#ff00cc,#ffc680)");
                    }else{
                        btn[myPred.get(i).getX()][myPred.get(i).getY()].setStyle("-fx-background-color:#ffc680");

                    }

//                    return "-fx-background-color:#ebb878";

                }else if (myPred.get(i).getTypeOfAge() == 1){
//                    System.out.println("Ret 5");

                    if (myPred.get(i).getMaleFemale() == 1){
                        btn[myPred.get(i).getX()][myPred.get(i).getY()].setStyle("-fx-background-color:linear-gradient(#ff00cc,#ff8d00)");
                    }else{
                        btn[myPred.get(i).getX()][myPred.get(i).getY()].setStyle("-fx-background-color:#ff8d00");

                    }
//                    return "-fx-background-color:#ff8e00";

                }else if (myPred.get(i).getTypeOfAge() == 2){
//                    System.out.println("Ret 6");

                    if (myPred.get(i).getMaleFemale() == 1){
                        btn[myPred.get(i).getX()][myPred.get(i).getY()].setStyle("-fx-background-color:linear-gradient(#ff00cc,#b88700)");
                    }else{
                        btn[myPred.get(i).getX()][myPred.get(i).getY()].setStyle("-fx-background-color:#b88700");

                    }

//                    return "-fx-background-color:#804e0f";

                }

                if (myPred.get(i).getRank().equals("Alpha") && myPred.get(i).getMaleFemale() == 1){
                    btn[myPred.get(i).getX()][myPred.get(i).getY()].setStyle("-fx-background-color:linear-gradient(#ff00cc,#ffc800)");
                }else if (myPred.get(i).getRank().equals("Alpha") && myPred.get(i).getMaleFemale() == 0){
                    btn[myPred.get(i).getX()][myPred.get(i).getY()].setStyle("-fx-background-color:#ffc800");
                }else if (myPred.get(i).getRank().equals("Beta") && myPred.get(i).getMaleFemale() == 1){
                    btn[myPred.get(i).getX()][myPred.get(i).getY()].setStyle("-fx-background-color:linear-gradient(#ff00cc,#17ad4b)");
                }else if (myPred.get(i).getRank().equals("Beta") && myPred.get(i).getMaleFemale() == 0){
                    btn[myPred.get(i).getX()][myPred.get(i).getY()].setStyle("-fx-background-color:#17ad4b");
                }
            }

        }

//        return "#000000";
    }

    public void setHunger(int hunger) {
        this.hunger += hunger;
    }

    public int getHunger() {
        return hunger;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence += intelligence;
    }

    public int getEgo() {
        return ego;
    }

    public void setEgo(int ego) {
        this.ego += ego;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
