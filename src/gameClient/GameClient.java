package gameClient;

import Server.Game_Server;
import Server.game_service;


import java.security.PublicKey;
import java.util.List;

public class GameClient extends Thread{
    static volatile game_service game;
    volatile static boolean shouldStop = true;


    public static synchronized game_service getGame(){

        return game;
    }

    public static synchronized void SetGame(int sc){
        game = Game_Server.getServer(sc);


    }
    public static boolean getShouldStop(){
        return shouldStop;
    }
    public static void shouldstart(){
        shouldStop = true;
    }
    public static String GetGraph(){
        return getGame().getGraph();
    }
    public static String GetFruits(){
        return getGame().getFruits().toString();
    }
    public static boolean isRunning(){
        return getGame().isRunning();
    }
    public static void AddRobot(int src){
        getGame().addRobot(src);
    }
    public static void MoveRobot(int id, int dest){
        getGame().chooseNextEdge(id,dest);
        try{
            Thread.sleep(20);
        }
        catch (Exception e){

        }
    }

    public static List<String> Moves(){
        return getGame().move();
    }
    public static void StopGame(){
        getGame().stopGame();
    }


    public static String GetRobots(){
        return getGame().getRobots().toString();
    }
    public static void StartGame(){
        getGame().startGame();

    }

    @Override
    public void run(){

        getGame().startGame();



    }


}
