//package com.example.demo;
//
//import java.util.ArrayList;
//
//public class Ant{
//    private int x;
//    private int y;
//    private int typeOfAnt;
//    private int maleFemale;
//    private int age;
//    String loc;
//    private long startTime;
//    private long reproduceTime;
//    public Ant(int x,int y){
//        //0 = male; 1 = female
//        maleFemale = (int)(Math.random()*2);
//        this.x = x;
//        this.y = y;
//        this.age = 0;
//        startTime = System.nanoTime();
//        reproduceTime = System.nanoTime();
//
//
//    }
//    public void changeLoc(int[][] gameGrid){
//        System.out.println("test1");
//        boolean check = false;
//        int count = 0;
//        while(!check){
//            System.out.println("y in while: " + y);
//            System.out.println("x in while: " + x);
//            System.out.println("while");
//            count++;
//            if(count==7){
//                break;
//            }
//
//            int tempx = x;
//            int tempy = y;
//            if(Math.random()>.5){
//                tempx++;
//            }else {
//                tempx--;
//            }
//            if(Math.random()>.5){
//                tempy++;
//            }else {
//                tempy--;
//            }System.out.println("test");
//            System.out.println(tempx);
//            System.out.println("tempy:" + tempy);
//            if (gameGrid[tempx][tempy]==0){
//
//                check=true;
//                if(maleFemale == 0){
//                    gameGrid[tempx][tempy]=1;
//                }else if(maleFemale == 1){
//                    gameGrid[tempx][tempy]=2;
//                }
//                gameGrid[x][y]=0;
//                x=tempx;
//                y=tempy;
//            }
//        }
//
////          System.out.println("x: " + x);
//
//    }
//
//    //this code can also be inside of the controller class if necessary.
//    public boolean checkNeighborOpposite(ArrayList<Ant> tempAnt){
//
//        for (int i = 0;i<tempAnt.size();i++){
////                System.out.println(tempAnt.get(i).getX());
//            if(tempAnt.get(i).getX() >=x-1 && tempAnt.get(i).getX()<=x+1 &&
//                    tempAnt.get(i).getY() >=y-1 && tempAnt.get(i).getY()<=y+1 &&
//                    tempAnt.get(i).getX()!=x && tempAnt.get(i).getY()!=y &&
//            maleFemale != tempAnt.get(i).getMaleFemale()){
//
//                return true;
//            }
//        }
//
//        return false;
//    }
//    private ArrayList<Locations> tempLocs = new ArrayList<>();
////    public void reproduce(ArrayList<Ant> tempAnt, int tempGrid[][]){
////
////        tempLocs.clear();
////        for(int i = x-1; i<x+2;i++){
////            for(int j = y-1;j<y+2;j++){
////                if(x<tempGrid.length && y <tempGrid[0].length){
////                    if(checkEmptyAroundMe(i,j,tempGrid)){
////                        System.out.println("j:" + j);
////                        tempLocs.add(new Locations(i,j));
////                    }
////                }
////            }
////        }
////        if(tempLocs.size()>0){
////            int newLoc = (int)(Math.random()*tempLocs.size());
//////            System.out.println("y: " +tempLocs.get(newLoc).getY());
////            tempAnt.add(new Ant(tempLocs.get(newLoc).getX(),tempLocs.get(newLoc).getY()));
//////            System.out.println("y: " +tempLocs.get(newLoc).getY());
////            tempGrid[tempAnt.get(tempAnt.size()-1).getX()][tempAnt.get(tempAnt.size()-1).getY()]=1;
//////            System.out.println("blah" + tempAnt.get(tempAnt.size()-1).getY());
////            reproduceTime = System.nanoTime();
////        }
////
////    }
//    public void reproduce(ArrayList<Ant> tempAnt, int tempGrid[][]){
//
//        tempLocs.clear();
//        for(int i = x-1; i<x+2;i++){
//            for(int j = y-1;j<y+2;j++){
//                if(x<tempGrid.length && y <tempGrid[0].length){
//                    if(checkEmptyAroundMe(i,j,tempGrid)){
//                        System.out.println("j:" + j);
//                        tempLocs.add(new Locations(i,j));
//                    }
//                }
//            }
//        }
//        if(tempLocs.size()>0){
//            int newLoc = (int)(Math.random()*tempLocs.size());
////            System.out.println("y: " +tempLocs.get(newLoc).getY());
//            tempAnt.add(new Ant(tempLocs.get(newLoc).getX(),tempLocs.get(newLoc).getY()));
////            System.out.println("y: " +tempLocs.get(newLoc).getY());
//            tempGrid[tempAnt.get(tempAnt.size()-1).getX()][tempAnt.get(tempAnt.size()-1).getY()]=1;
////            System.out.println("blah" + tempAnt.get(tempAnt.size()-1).getY());
//            reproduceTime = System.nanoTime();
//        }
//
//    }
//
//    public boolean checkEmptyAroundMe(int i, int j, int tempGrid[][]){
//        return tempGrid[i][j] ==0;
//    }
//    public int getX(){
//        return this.x;
//    }
//    public void resetStartTime(){
//        startTime = System.nanoTime();
//    }
//    public long getStartTime(){
//        return startTime;
//    }
//
//    public long getReproduceTime() {
//        return reproduceTime;
//    }
//
//    public int getY() {
//        return this.y;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }
//
//    public int getAge() {
//        return age;
//    }
//
//    public int getMaleFemale() {
//        return maleFemale;
//    }
//
//}
