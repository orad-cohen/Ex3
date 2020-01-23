package gameClient;

import Server.Game_Server;
import Server.game_service;

import java.util.List;

public class GameClient extends Thread{
    static volatile game_service game;



    public static synchronized game_service getGame(){

        return game;
    }

    public static synchronized void SetGame(int sc){
        game = Game_Server.getServer(sc);


    }
    public static synchronized String GetGraph(){
        return getGame().getGraph();
    }
    public static synchronized String GetFruits(){
        return getGame().getFruits().toString();
    }
    public static synchronized boolean isRunning(){
        return getGame().isRunning();
    }
    public static synchronized void  AddRobot(int src){
        getGame().addRobot(src);
    }
    public static synchronized void  MoveRobot(int id, int dest){
        getGame().chooseNextEdge(id,dest);

    }

    public static synchronized List<String> Moves(){
        return getGame().move();
    }
    public static synchronized void StopGame(){
        getGame().stopGame();
    }


    public static synchronized String GetRobots(){
        return getGame().getRobots().toString();
    }
    public static synchronized void StartGame(){
        getGame().startGame();

    }

    @Override
    public void run(){

        getGame().startGame();




    }


}
