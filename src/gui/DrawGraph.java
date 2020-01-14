package gui;

import algorithms.Graph_Algo;
import algorithms.graph_algorithms;
import dataStructure.*;
import utils.Point3D;

import utils.StdDraw;

import javax.swing.*;
import java.awt.*;

import java.awt.event.*;
import java.io.Serializable;
import java.util.EventListener;
import java.util.Iterator;
import java.util.LinkedList;

public class DrawGraph implements Serializable {
    private static final long serialVersionUID = 1L;
    private Graph_Algo _graphAlgo;
    private graph _graph;

    public  graph getGraph(){
        return this._graph;
    }
    public DrawGraph()
    {
        _graphAlgo=new Graph_Algo();
        _graph=new DGraph();
        StdDraw.setGui(this);
    }
    public DrawGraph(graph g)
    {
        this._graph = g;
        _graphAlgo=new Graph_Algo();
        _graphAlgo.init(this._graph);
        StdDraw.setGui(this);
    }

    public void Edges(){
        Iterator<node_data> nodes = _graph.getV().iterator();
        LinkedList<edge_data> NodeEdges = new LinkedList<>();

        while(nodes.hasNext()){//get edges into the lniked list
            node_data CurNode = nodes.next();
            if(_graph.getE(CurNode.getKey())!=null){
                Iterator<edge_data> EdgeIte = _graph.getE(CurNode.getKey()).iterator();
                while(EdgeIte.hasNext()){
                    NodeEdges.add(EdgeIte.next());
                }
            }
        }
        for(int  i = 0; i<NodeEdges.size();i++){// draw them 1 by 1.
            StdDraw.setPenColor(Color.red);//red for edges
            StdDraw.setPenRadius();
            double x0 = _graph.getNode(NodeEdges.get(i).getSrc()).getLocation().x();
            double x1 = _graph.getNode(NodeEdges.get(i).getDest()).getLocation().x();
            double y0 = _graph.getNode(NodeEdges.get(i).getSrc()).getLocation().y();
            double y1 = _graph.getNode(NodeEdges.get(i).getDest()).getLocation().y();
            StdDraw.line(x0,y0,x1,y1);
            double nx;
            double ny;
            double midx =(x0+x1)/2;
            double midy = (y0+y1)/2;
            if(x0>x1){nx = x0-Math.abs(x0-x1)/10;}// Calculation for Drawing edges with the correct orientation
            else{nx = x0+Math.abs(x0-x1)/10;}
            if(y0>midy){ny = y0-Math.abs(y0-y1)/10;}
            else{ny = y0+Math.abs(y0-y1)/10;}
            StdDraw.text(midx,midy,""+NodeEdges.get(i).getWeight());
            StdDraw.setPenRadius(0.01);
            StdDraw.setPenColor(Color.YELLOW);
            StdDraw.point(nx,ny);




        }




        }
    public void nodes(){//for drwaing nodes
        Iterator<node_data> nodes = _graph.getV().iterator();
        LinkedList<Point3D> NodeLocation = new LinkedList<>();
        LinkedList<Integer> NodeKey = new LinkedList<>();

        while(nodes.hasNext()){//put it into linked list
            node_data CurNode = nodes.next();
            Point3D loc = CurNode.getLocation();
            NodeLocation.add(loc);
            NodeKey.add(CurNode.getKey());
        }
        StdDraw.setPenRadius(0.01);
        for(int i = 0; i<NodeLocation.size();i++){//draw them by their xy value
            StdDraw.setPenColor(Color.BLUE);
            StdDraw.point(NodeLocation.get(i).x(),NodeLocation.get(i).y());
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.text(NodeLocation.get(i).x(),NodeLocation.get(i).y()+0.2, ""+NodeKey.get(i));
        }
    }

    public void Draw(graph g){//Draw everything
        this._graphAlgo = new Graph_Algo(g);
        this._graph =g ;
        Iterator<node_data> nodes = g.getV().iterator();
        LinkedList<Point3D> NodeLocation = new LinkedList<>();
        LinkedList<Integer> NodeKey = new LinkedList<>();

        double Max_x=0;
        double Min_x=0;
        double Max_y=0;
        double Min_y=0;
        while(nodes.hasNext()){//get the max for canvassize
            node_data CurNode = nodes.next();
            Point3D loc = CurNode.getLocation();
            NodeLocation.add(loc);
            Max_x = Math.max(Max_x, loc.x());
            Max_y = Math.max(Max_y, loc.y());
            Min_x = Math.min(Min_x, loc.x());
            Min_y = Math.min(Min_y, loc.y());
            NodeKey.add(CurNode.getKey());
        }
        StdDraw.setCanvasSize((int)(Math.abs(Min_x)+Math.abs(Max_x))+900,(int)(Math.abs(Min_y)+Math.abs(Max_y))+900);// than draw thee canvas
        StdDraw.setXscale(Min_x-10, Max_x+10);
        StdDraw.setYscale(Min_y-10, Max_y+10);
        Edges();//invoke drawing nodes and edges.
        nodes();






    }
    public void Draw(){
        Draw(_graph);//Drawing
    }
    public void init(graph gr)//init from graph
    {
        this._graph = gr;
        this._graphAlgo._graph = gr;
    }
    public void init(String name)//init from file.
    {
        this._graphAlgo.init(name);
        this._graph=_graphAlgo._graph;
        Draw();
    }
    public Graph_Algo getAlgo(){//for STDDRAW
        return this._graphAlgo;
    }
}


