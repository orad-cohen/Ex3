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

import java.util.Iterator;
import java.util.List;

import java.util.Iterator;

public class MyGameGUI {
    static DGraph _gg = new DGraph();
    static game_service game;

    public static void main(String[] args) {

        Iterator<node_data> ite = _gg.getV().iterator();
        while (ite.hasNext()){
            node_data l = ite.next();
            System.out.println("key : "+l.getKey() +"Position"+l.getLocation());
        }
        init(2);
        Drawg();
        StdDraw.pause(20320142);
    }

    public static void init(int scenario_num) {

        game_service game = Game_Server.getServer(scenario_num);

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
        }
        catch (Exception e){

        }
    }

    public static void Drawg(){
        StdDraw.Init(_gg,game);
        StdDraw.DrawCanvas();
        DrawNodes();
        DrawEdges();
        DrawFruits();
        DrawRobots();
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


    }
    public static void DrawFruits () {
        try {
            JSONArray Fruits = new JSONArray(game.getFruits());
            int i = 0;
            while (i < Fruits.length()) {
                JSONObject curFruit = new JSONObject(Fruits.get(i));
                int value = Integer.parseInt(curFruit.get("value").toString());
                int type = Integer.parseInt(curFruit.get("type").toString());
                Point3D loc = new Point3D(curFruit.get("pos").toString());
                StdDraw.DrawFruit(value, type, loc);
            }


        } catch (Exception e) {

        }


    }
}
















