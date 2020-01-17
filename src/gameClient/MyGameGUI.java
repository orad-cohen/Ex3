package gameClient;

import Server.Game_Server;
import Server.game_service;
import dataStructure.DGraph;
import dataStructure.Node;
import dataStructure.edge_data;
import dataStructure.node_data;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.Point3D;
import utils.StdDraw;
import dataStructure.edge_data;
import dataStructure.node_data;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.Point3D;
import utils.StdDraw;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import java.util.Iterator;

public class MyGameGUI implements Serializable {
    private static final long serialVersionUID = 1L;
    static DGraph _gg = new DGraph();
    static game_service game;



    public static void init() {
        int scenario_num = (int)(Math.random()*100%24);
        game = Game_Server.getServer(scenario_num);

        try {
            JSONObject gameJSON = new JSONObject(game.getGraph());
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
            DrawEverything();
        }
        catch (Exception e){

        }
    }

    public static void DrawEverything(){
        StdDraw.Init(_gg,game);
        StdDraw.DrawCanvas();
        DrawNodes();
        DrawEdges();
        DrawFruits();
        DrawRobots();
        StdDraw.pause(124124124);
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
                StdDraw.DrawEdge(next);
            }


        }


    }
    public static void DrawRobots () {
        System.out.println(game);
        LinkedList<Integer> bots = new LinkedList<>();
        try{
            JSONObject obj = new JSONObject(game.toString());
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
            JSONArray Fruits = new JSONArray(game.getFruits());
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
        return game;

    }
}
















