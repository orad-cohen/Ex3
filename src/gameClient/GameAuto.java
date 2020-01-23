package gameClient;

import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.node_data;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import things.Fruit;
import things.Robot;
import utils.Point3D;

import java.util.*;

public class GameAuto extends Thread{
    static DGraph _graph = new DGraph();
    static volatile Graph_Algo _algo = new Graph_Algo();
    static game_service game;
    static node_data[] node_arr;
    volatile ArrayList<Robot> Robots = new ArrayList<>();
    volatile LinkedList<Fruit> Fruits = new LinkedList();
    volatile HashMap<Integer,String> f = new HashMap<>();

    public static void updateGraph(){
        _graph = MyGameGUI.getGraph();
        _algo.init(_graph);
        node_arr = new node_data[_graph.getV().size()];
        Iterator<node_data> NodeIte = _graph.getV().iterator();
        for (int i=0; NodeIte.hasNext();i++){
            node_arr[i] = NodeIte.next();
        }
        }
    public void PlaceRobots(){
        String line = game.toString();
        try {
            JSONObject arr = new JSONObject(line);
            JSONObject robot = new JSONObject(arr.get("GameServer").toString());
            int robot_num = robot.getInt("robots");
            int[][] Paths = this.getFruits();

            for (int i =0 ; i<Paths[0].length||i<robot_num;i++){
                game.addRobot(i);
                if(i==robot_num-1){
                    break;
                }
            }

        }
        catch (Exception e){
            e.printStackTrace();


        }

        getRobots();

    }

    private void getRobots(){
        Robots.clear();
        List<String> log = game.getRobots();
        if(log!=null) {
            String robot_json = log.toString();
            try {
                JSONArray line= new JSONArray(robot_json);
                for(int i=0; i< line.length();i++) {

                    JSONObject j= line.getJSONObject(i);
                    JSONObject jrobots = j.getJSONObject("Robot");
                    String loc = jrobots.getString("pos");
                    String[] xyz = loc.split(",");
                    double x = Double.parseDouble(xyz[0]);
                    double y = Double.parseDouble(xyz[1]);
                    double z = Double.parseDouble(xyz[2]);
                    Point3D p = new Point3D(x,y,z);
                    int rid = jrobots.getInt("id");
                    int src = jrobots.getInt("src");
                    int dest = jrobots.getInt("dest");
                    double val = jrobots.getDouble("value");
                    int speed = jrobots.getInt("speed");
                    Robot r = new Robot(rid,src,dest,p,val,speed);
                    Robots.add(r);


                }
            } catch (JSONException e) { e.printStackTrace(); }
        }

    }



    public void BotsUpdate() {
        List<String> log = GameClient.Moves();
        if (log != null) {
            String robot_json = log.toString();
            try {
                JSONArray line = new JSONArray(robot_json);

                for (int i = 0; i < line.length(); i++) {

                    JSONObject j = line.getJSONObject(i);
                    JSONObject jrobots = j.getJSONObject("Robot");
                    String loc = jrobots.getString("pos");
                    String[] xyz = loc.split(",");
                    double x = Double.parseDouble(xyz[0]);
                    double y = Double.parseDouble(xyz[1]);
                    Point3D p = new Point3D(x, y);
                    int rid = jrobots.getInt("id");
                    int src = jrobots.getInt("src");
                    int dest = jrobots.getInt("dest");
                    double val = jrobots.getDouble("value");
                    int speed = jrobots.getInt("speed");
                    Robots.get(rid).Update(rid, src, dest, p, val, speed);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }

    public void FruitUpdate() {
        Fruits.clear();
        List<String> log = game.getFruits();
        if(log!=null) {
            String fru_json = log.toString();
            try {
                JSONArray line= new JSONArray(fru_json);
                for(int i =0; i<line.length();i++) {
                    JSONObject j = line.getJSONObject(i);
                    JSONObject fru = j.getJSONObject("Fruit");
                    String loc = fru.getString("pos");
                    String[] xyz = loc.split(",");
                    double x = Double.parseDouble(xyz[0]);
                    double y = Double.parseDouble(xyz[1]);
                    double z = Double.parseDouble(xyz[2]);
                    Point3D p = new Point3D(x,y,z);
                    double value = fru.getDouble("value");
                    int type = fru.getInt("type");
                    Fruit f = new Fruit(value,type,p);
                    Fruits.add(f);

                }
            } catch (JSONException e) { e.printStackTrace(); }
        }

    }


    public int[][] getFruits(){
        try{
            JSONArray Fruits = new JSONArray(game.getFruits().toString());
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
            int x = node_arr[i].getKey();
            Iterator<edge_data> EdgeIte = _graph.getE(node_arr[i].getKey()).iterator();
            while (EdgeIte.hasNext()){
                int t= EdgeIte.next().getDest();
                if(iSBetween(node_arr[i].getLocation(),loc,_graph.getNode(t).getLocation())){
                    if(type == 1&&node_arr[i].getLocation().y()>loc.y()){
                        arr[0][0]=node_arr[i].getKey();
                        arr[0][1]=t;
                        return arr;
                    }
                    else {
                        arr[0][1]=node_arr[i].getKey();
                        arr[0][0]=t;
                        return arr;

                    }

                }}}

        return null;
    }
    public static boolean iSBetween(Point3D a, Point3D b, Point3D c){

        double EPS = 0.0001;
        double Distance = a.distance3D(b)+b.distance3D(c)-a.distance3D(c);
        if(Math.abs(Distance)<EPS&&Math.abs(Distance)*-1<EPS){
            return true;
        }
        else{
            return false;
        }

    }






    public void setBestPath(int id){
        Robot r = Robots.get(id);
        if(r.getSrc()==r.Last()){
            return;
        }

        int node =0;
        int[][] FruitPaths = getFruits();
        List<node_data> NodePath = null;
        double shortest_dist=1000;
        for(int i = 0; i<FruitPaths.length;i++){
            if(r.getSrc()==FruitPaths[i][0]){
                r.setDest(FruitPaths[i][1]);
                return;
            }
            else if(r.getSrc()==FruitPaths[i][1]){
                r.setDest(FruitPaths[i][0]);
                return;
            }
            else if(shortest_dist>_algo.shortestPathDist(r.getSrc(),FruitPaths[i][0] )) {
                NodePath = _algo.shortestPath(r.getSrc(), FruitPaths[i][0]);

            }
            else{
                continue;
            }
        }

        r.setDest(NodePath.get(1).getKey());



    }

    public void Auto(){

        new Thread(new Runnable(){
            @Override
            public void run() {
                BotMover();    }
        }).start();

        while (game.isRunning()){
            game.move();
            BotsUpdate();
            FruitUpdate();
            for(Robot r : Robots){
                setBestPath(r.getID());

            }
            try{
                sleep(50);
            }
            catch (Exception e){

            }
        }
}
    public void BotMover(){
        while(game.isRunning()){
            game.move();
            for(Robot r : Robots){
                if (r.getDest()!=-1&&r.Last()==r.getSrc()){
                    continue;
                }
                else{
                    game.chooseNextEdge(r.getID(), r.getDest());
                }

        }
            try{
                sleep(40);
            }
            catch (Exception e){

            }

        }
    }

    @Override
    public void run(){
        GameClient _Game = new GameClient();
        _Game.SetGame(23);
        game = GameClient.getGame();
        MyGameGUI _Gui = new MyGameGUI();
        _Gui.init();
        updateGraph();
        PlaceRobots();

        game.startGame();

        try{
            _Gui.start();
        }
        catch (Exception e){

        }

        while(!game.isRunning()){
            try{

                Thread.sleep(50);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        int i = 0;
        System.out.println(game.isRunning());
        Auto();





    }
}
