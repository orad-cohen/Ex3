package gameClient;

import Server.Game_Server;
import Server.game_service;
import dataStructure.DGraph;
import dataStructure.Node;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.Point3D;

import java.util.Iterator;

public class MyGameGUI {
    static DGraph _gg = new DGraph();
    static game_service game;

    public static void main(String[] args) {
        init(2);
    }

    public static void init(int scenario_num){

        game_service game = Game_Server.getServer(scenario_num);

        try{
            JSONObject gameJSON = new JSONObject(game.getGraph());
            String gameNodes = gameJSON.get("Nodes").toString();
            String gameEdges = gameJSON.get("Edges").toString();

            JSONArray nodes = new JSONArray(gameNodes);
            JSONArray edges = new JSONArray(gameEdges);

            for(int i=0; i<nodes.length(); i++){
                int id = (int) nodes.getJSONObject(i).get("id");
                Point3D loc = new Point3D((String) nodes.getJSONObject(i).get("pos"));

                _gg.addNode(new Node(id,loc));

            }

            for(int i=0; i<edges.length(); i++){
                int src = (int)edges.getJSONObject(i).get("src");
                int dest = (int)edges.getJSONObject(i).get("dest");
                double w = (double)edges.getJSONObject(i).get("w");

                _gg.connect(src,dest,w);

            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }




    public static void DrawNodes(){

    }
    public static void DrawEdges(){

    }
    public static void DrawRobots(){

    }
    public static void DrawFruits(){

    }






















}