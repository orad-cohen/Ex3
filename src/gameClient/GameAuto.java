package gameClient;

import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.*;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.Point3D;
import utils.StdDraw;

import static utils.StdDraw.*;
import gameClient.MyGameGUI;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class GameAuto extends Thread{
    static DGraph _graph = new DGraph();
    static Graph_Algo _algo = new Graph_Algo();
    static game_service game;
    static node_data[] node_arr;
    static HashMap<Integer,Boolean> BotsOnTheMove = new HashMap<>();
    static HashMap<Integer,Integer> FruitsOnTheMove = new HashMap();






    public static void RobotDriver() {




    }






    public static void PlaceRobots(){
        String game = GameClient.getGame().toString();
        try {
            JSONObject arr = new JSONObject(game);
            JSONObject robot = new JSONObject(arr.get("GameServer").toString());
            int robot_num = robot.getInt("robots");
            int[][] Paths = getFruits();
            for (int i =0 ; i<Paths[0].length;i++){
                GameClient.AddRobot(Paths[i][0]);
                BotsOnTheMove.put(i, true);
                if(i==robot_num-1){
                    return;
                }

            }
        }
        catch (Exception e){
            e.printStackTrace();


    }    }



    public static void updateGraph(){
        _graph = MyGameGUI.getGraph();
        _algo.init(_graph);
        node_arr = new node_data[_graph.getV().size()];
        Iterator<node_data> NodeIte = _graph.getV().iterator();
        for (int i=0; NodeIte.hasNext();i++){
            node_arr[i] = NodeIte.next();
        }}



    public static int[][] getFruits(){
        try{

            JSONArray Fruits = new JSONArray(GameClient.GetFruits());
            int[][] arr = new int[Fruits.length()][2];
            int i = 0;
            while (i < Fruits.length()) {
                JSONObject Fruit = new JSONObject(Fruits.get(i).toString());
                JSONObject curFruit = new JSONObject(Fruit.get("Fruit").toString());
                Point3D loc = new Point3D(curFruit.get("pos").toString());

                int type = curFruit.getInt("type");
                int[][] _Path = Between(loc,type);
                arr[i][0] = _Path[0][0];
                arr[i][1] = _Path[0][1];

                i++;}
            return arr;

        }
        catch (Exception e){

        }

        return null;


    }
    public static int[][] Between(Point3D loc,int type){
        int[][] arr = new int[1][2];
        for(int i = 0; i<node_arr.length;i++){
            Iterator<edge_data> EdgeIte = _graph.getE(node_arr[i].getKey()).iterator();
            while (EdgeIte.hasNext()){
                int t= EdgeIte.next().getDest();
                if(iSBetween(node_arr[i].getLocation(),loc,_graph.getNode(t).getLocation())){
                    if(type == 1&&node_arr[i].getLocation().y()>loc.y()){
                        arr[0][0]=node_arr[i].getKey();
                        arr[0][1]=t;

                        return arr;
                    }
                    else if(type==-1&&node_arr[i].getLocation().y()<loc.y()){
                        arr[0][0]=node_arr[i].getKey();
                        arr[0][1]=t;
                        return arr;

                    }
                    else{
                        continue;
                    }
                }}}
        return null;
    }
    public static boolean iSBetween(Point3D a, Point3D b, Point3D c){

        double EPS = 0.001;
        double Distance = a.distance3D(b)+b.distance3D(c)-a.distance3D(c);
        if(Distance<EPS&&Distance>(EPS*-1)){
            return true;
        }
        else{
            return false;
        }

    }


    public static void BotToFruit(int id, List<node_data> Path, int dest){
        List<String> log = GameClient.getGame().move();

        for(int i=1;i<Path.size();i++) {
            String robot_json = log.get(id);
            try {
                JSONObject line = new JSONObject(robot_json);
                JSONObject ttt = line.getJSONObject("Robot");
                int dst = ttt.getInt("dest");

                if (dst != -1) {
                    sleep(100);
                    i--;
                    continue;
                }
                else {
                    GameClient.MoveRobot(id, Path.get(i).getKey());
                    sleep(100);
                }


            } catch (Exception e) {
                e.printStackTrace();

            }
            return;
        }

            GameClient.MoveRobot(id,dest);
            BotsOnTheMove.put(id,false);
            FruitsOnTheMove.de
            return;






    }












    public static void Auto(){
        List<String> log = GameClient.getGame().move();
        int [][] FruitPaths = getFruits();
        try{
            for(int i=0;i<log.size();i++) {
                String robot_json = log.get(i);
                JSONObject line = new JSONObject(robot_json);
                JSONObject ttt = line.getJSONObject("Robot");
                int rid = ttt.getInt("id");
                int src = ttt.getInt("src");
                int dest = ttt.getInt("dest");
                if(!BotsOnTheMove.get(rid)||dest!=-1){
                    continue;
                }
                for(int j=0;j<FruitPaths.length;j++){
                    if(FruitsOnTheMove.get(FruitPaths[i][0])==null){
                        List<node_data> Path = _algo.shortestPath(src, FruitPaths[i][0]);
                        if(Path==null){
                            GameClient.getGame().chooseNextEdge(rid,FruitPaths[i][1]);
                        }
                        else{
                            FruitsOnTheMove.put(FruitPaths[i][0],FruitPaths[i][1]);
                            BotsOnTheMove.put(rid,true);
                            int finalJ = j;
                            new Thread(
                                    new Runnable() {
                                        public void run() {
                                            BotToFruit(rid, Path, FruitPaths[finalJ][1]);
                                        }
                                    }
                            ).start();
                        }


                    }

                    else if(FruitsOnTheMove.get(FruitPaths[i][0])==FruitPaths[i][1]){
                        continue;
                    }
                    else{
                        List<node_data> Path = _algo.shortestPath(src, FruitPaths[i][0]);
                        FruitsOnTheMove.put(FruitPaths[i][0],FruitPaths[i][1]);
                        BotsOnTheMove.put(rid,true);
                        int finalJ = j;
                        new Thread(
                                new Runnable() {
                                    public void run() {
                                        BotToFruit(rid, Path, FruitPaths[finalJ][1]);
                                    }
                                }
                        ).start();


                    }
                }
                try{
                    sleep(30);
                }
                catch (Exception e){

                }
            }
            return;
        }
        catch(Exception e){
            e.printStackTrace();
        }}


    @Override
    public void run(){
        GameClient _Game = new GameClient();
        _Game.SetGame(3);
        MyGameGUI _Gui = new MyGameGUI();
        _Gui.init();
        updateGraph();
        PlaceRobots();

        try{
            _Game.start();
            _Game.join();
            _Gui.start();


        }
        catch (Exception e){

        }


        while(!GameClient.isRunning()){
            try{

                Thread.sleep(100);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        int i = 0;

        while (GameClient.isRunning()) {
            if(i==0){


                i++;
            }

            try{
                Auto();

                Thread.sleep(100);
            }
            catch (Exception e){
                e.printStackTrace();

            }



        }


    }
}
