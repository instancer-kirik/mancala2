package com.instance.mancala2;

public class Player {

    private int sleeveStone;
    private int hand;
    private int wins;
    private String name;

    public Player(String name){

        this.name = name;
    }
    public int getSleeveStone() {
        return sleeveStone;
    }

    public void setSleeveStone(int sleeveStone) {
        this.sleeveStone = sleeveStone;
    }
    public int useSleeveStone() {
        this.sleeveStone--;
        return 1;
    }
    public void storeStone(){
        System.out.println("STORE");
        if(hand>0) {
            setSleeveStone(getSleeveStone() + 1);
            hand--;
            System.out.println("+++++++");
        }
    }


    public int getWins() {
        return wins;
    }
    public int addWin() {
        wins++;
        return wins;
    }
    public void setWins(int wins) {
        this.wins = wins;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void addStonesToHand(int stones) {
        this.hand += stones;
    }
    public int drop(int num) {
        if(this.hand>0) {
            this.hand = this.hand - num;
        }
        return num;
    }
    public int getHand() {
        return hand;
    }

    public void addScore(int capturedStones) {
    }
}
