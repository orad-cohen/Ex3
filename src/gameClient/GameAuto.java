package gameClient;

import Server.game_service;
import dataStructure.DGraph;

public class GameAuto extends Thread{
    DGraph _graph = new DGraph();
    game_service _game;



    @Override
    public void run(){
        MyGameGUI.init();
        _graph  = MyGameGUI.getGraph();
        _game = MyGameGUI.getGame();

    }
















}
