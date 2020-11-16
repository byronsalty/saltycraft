package com.saltylabs.minecraft;


public class Game {
    MineHandler handler;
    boolean gameStarted;

    public Game() {
        this.init();
    }
    public void init() {
        this.gameStarted = false;
    }


    public void playerSaid(String player, String saying) {
        System.out.println("player: " + player);
        System.out.println("said: " + saying);
    
        if (saying.equals("lets play")) {
          startGame();
        }
    }
    
    public void commandPlayed(String command) {
        System.out.println("command: " + command);

        // if (saying.equals("lets play")) {
        //   startGame();
        // }
    }


    public void startGame() {
        System.out.println("starting game");
        gameStarted = true;
        writeToMinecraft("/say OK - Let's get started");
        // reset any variables
        qsleep(4000);
        // begin game
        startNight();
    }

    public static void qsleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            // do nothing
        }
    }

    public void startNight() {
        // update gametime...
        writeToMinecraft("/time set 16000");
        qsleep(200);
        writeToMinecraft("/time set 16500");
        qsleep(200);
        writeToMinecraft("/time set 17000");
        qsleep(200);
        writeToMinecraft("/time set 17500");
        qsleep(200);
        writeToMinecraft("/time set 18000");
        qsleep(2000);
        writeToMinecraft("/say There's been a murder!");
        qsleep(2000);
        assignRoles();
    }

    public void assignRoles() {
        writeToMinecraft("/msg vsalty you're the seer");
    }



    public void writeToMinecraft(String toWrite) {
        this.handler.writeToMinecraft(toWrite);
    }

    public void setWriteHandler(MineHandler mineHandler) {
        this.handler = mineHandler;
    }

}
