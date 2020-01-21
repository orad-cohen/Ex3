package gameClient;

import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.Edge;
import dataStructure.edge_data;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.Point3D;
import utils.StdDraw;

import static utils.StdDraw.*;
import gameClient.MyGameGUI;

import java.util.Iterator;
import java.util.List;

public class GameAuto extends Thread{
    static DGraph _graph = new DGraph();
    Graph_Algo _algo = new Graph_Algo();
    static game_service game;








    public static void Driver(){

        try{

            _graph = MyGameGUI.getGraph();
            String game = GameClient.getGame().toString();

            JSONObject arr = new JSONObject(game);
            JSONObject robot = new JSONObject(arr.get("GameServer").toString());
            int robot_num = robot.getInt("robots");

            PlaceRobots(robot_num);



        }
        catch (Exception e){
            e.printStackTrace();
        }





    }
    public static void PlaceRobots(int num){
        GameClient.AddRobot(num);
    }
    public static void Auto(){


        List<String> log = GameClient.getGame().move();
        try{

            for(int i=0;i<log.size();i++) {
                String robot_json = log.get(i);
                JSONObject line = new JSONObject(robot_json);
                JSONObject ttt = line.getJSONObject("Robot");

                int rid = ttt.getInt("id");
                int src = ttt.getInt("src");
                int dest = ttt.getInt("dest");
                if(dest!=-1){

                    return;

                }
                Iterator<edge_data> Edges = MyGameGUI.getGraph().getE(src).iterator();
                Integer[] arr = new Integer[MyGameGUI.getGraph().getE(src).size()];
                int c = 0;
                while(Edges.hasNext()){
                    arr[c++] = Edges.next().getDest();
                }


                dest = arr[(int)((Math.random()*100)%arr.length)];
                GameClient.getGame().chooseNextEdge(rid, dest);



            }		}
        catch(Exception e){
            e.printStackTrace();
        }}



    public void run() {
        GameClient _Game = new GameClient();
        _Game.SetGame(0);
        _Game.AddRobot(0);
        MyGameGUI _Gui = new MyGameGUI();
        _Gui.init();
        try{
           _Game.start();
           _Game.join();
            _Gui.start();


        }
        catch (Exception e){

        }


        while(!GameClient.isRunning()){
            try{

                Thread.sleep(30);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        int i = 0;
        String s = " ";
        while (GameClient.isRunning()) {
            Auto();
            s = GameClient.getGame().toString();
            try{
                sleep(20);
            }
            catch (Exception e){

            }


        }
        System.out.println(s);











    }
}
