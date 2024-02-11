package com.instance.mancala2;

public class Player {

    private int sleeveStones;
    private int hand;
    private int wins;
    private String name;
    private MancalaGame game;
    public Player(String name, MancalaGame game){

        this.name = name;
        this.game = game;
    }
    public int getSleeveStones() {
        return sleeveStones;
    }

    public void setSleeveStones(int sleeveStones) {
        this.sleeveStones = sleeveStones;
    }
    public int useSleeveStone() {
        this.sleeveStones--;
        return 1;
    }
    public void storeStone(){
        System.out.println("STORE");
        if(hand>0) {
            game.addMove(ActionType.STORE_STONE,-1);
            setSleeveStones(getSleeveStones() + 1);
            hand--;
            System.out.println("++++++ ");
            System.out.println(getSleeveStones() + " stones");
        }

    }
    public void unsleeveStone(){
        System.out.println("UNSLEEVE");
        if(sleeveStones>0) {
            game.addMove(ActionType.UNSLEEVE_STONE,-1);
            setSleeveStones(getSleeveStones() - 1);
            hand++;
            System.out.println("++++++ ");
            System.out.println(getSleeveStones() + " stones");
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
