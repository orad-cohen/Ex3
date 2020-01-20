package Tests;

import static org.junit.jupiter.api.Assertions.*;

import Server.Game_Server;
import Server.game_service;
import gameClient.GameAuto;
import static gameClient.MyGameGUI.*;

import gameClient.GameClient;
import org.junit.jupiter.api.Test;
import utils.StdDraw;

import static org.junit.jupiter.api.Assertions.*;

class GameAutoTest {




    @Test
    public void run(){


           GameAuto game = new GameAuto();
           game.run();




    }


}