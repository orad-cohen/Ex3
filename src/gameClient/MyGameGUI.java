package gameClient;

import Server.game_service;
import dataStructure.DGraph;
import dataStructure.Node;
import dataStructure.edge_data;
import dataStructure.node_data;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.Point3D;
import utils.StdDraw;

import java.util.Iterator;
import java.util.LinkedList;

import static gameClient.GameAuto.game;

public class MyGameGUI extends Thread{

    static DGraph _gg = new DGraph();
    static KML_Logger kml = new KML_Logger();


    public static void init() {

        try {


            JSONObject gameJSON = new JSONObject(GameClient.GetGraph());
            String gameNodes = gameJSON.get("Nodes").toString();
            String gameEdges = gameJSON.get("Edges").toString();

            JSONArray nodes = new JSONArray(gameNodes);
            JSONArray edges = new JSONArray(gameEdges);

            for (int i = 0; i < nodes.length(); i++) {
                int id = (int) nodes.getJSONObject(i).get("id");
                Point3D loc = new Point3D((String) nodes.getJSONObject(i).get("pos"));

                _gg.addNode(new Node(id, loc));

            }

            for (int i = 0; i < edges.length(); i++) {
                int src = (int) edges.getJSONObject(i).get("src");
                int dest = (int) edges.getJSONObject(i).get("dest");
                double w = (double) edges.getJSONObject(i).get("w");

                _gg.connect(src, dest, w);
            }

            StdDraw.DrawCanvas();

            StdDraw.enableDoubleBuffering();





        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void DrawEverything(){
        try{
            StdDraw.backgound();
            DrawNodes();
            DrawEdges();
            DrawFruits();
            StdDraw.DrawRobot();
            String s = ""+(GameClient.getGame().timeToEnd()/1000);
            StdDraw.DrawTime(s);
            StdDraw.show();

        }
        catch (Exception e){
            e.printStackTrace();
        }



    }

    public static void DrawNodes(){
        Iterator<node_data> nodeIte = _gg.getV().iterator();
        while (nodeIte.hasNext()) {
            node_data next = nodeIte.next();
            StdDraw.DrawNode(next.getLocation(), next);
        }


    }
    public static void DrawEdges () {
        Iterator<node_data> nodeIte = _gg.getV().iterator();
        while (nodeIte.hasNext()) {
            Iterator<edge_data> edgeIte = _gg.getE(nodeIte.next().getKey()).iterator();
            while (edgeIte != null && edgeIte.hasNext()) {
                edge_data next = edgeIte.next();
                StdDraw.DrawEdge(next);            }


        }


    }
    public static void AddRobot(int src){
        game.addRobot(src);
    }
    public static void MoveRobot(int id, int dest){
        game.chooseNextEdge(id,dest);
    }

    public static void PlaceRandom () {
        LinkedList<Integer> bots = new LinkedList<>();
        try{
            JSONObject obj = new JSONObject(GameClient.getGame().toString());
            JSONObject obj2 = new JSONObject(obj.get("GameServer").toString());
            int i = Integer.parseInt(obj2.get("robots").toString());
            while(i>0){
                int x = (int)(Math.random()*1000*_gg.nodeSize()%_gg.nodeSize());
                if(bots.contains(x)){
                    continue;
                }
                else{
                    bots.add(x);
                    game.addRobot(x);
                    i--;
                }


            }

            StdDraw.DrawRobot();


        }

        catch (Exception e){
            e.printStackTrace();
        }




    }
    public static void DrawFruits () {
        try {
            JSONArray Fruits = new JSONArray(GameClient.GetFruits());
            int i = 0;
            while (i < Fruits.length()) {
                JSONObject Fruit = new JSONObject(Fruits.get(i).toString());
                JSONObject curFruit = new JSONObject(Fruit.get("Fruit").toString());

                int type = Integer.parseInt(curFruit.get("type").toString());
                Point3D loc = new Point3D(curFruit.get("pos").toString());
                StdDraw.DrawFruit(type, loc);
                i++;
            }


        } catch (Exception e) {

        }


    }


    public static game_service getGame(){
        return GameClient.getGame();

    }
    public static DGraph getGraph(){
        return _gg;
    }

    @Override
    public void run() {

        while(GameClient.getGame().timeToEnd()/100>10){
            DrawEverything();
            try{
                sleep(30);
            }
            catch (Exception e){

            }


        }










    }
}