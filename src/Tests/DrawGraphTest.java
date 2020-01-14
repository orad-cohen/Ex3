package Tests;

import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.Node;
import gui.DrawGraph;
import org.junit.jupiter.api.Test;
import utils.Point3D;
import utils.StdDraw;

import static org.junit.jupiter.api.Assertions.*;

class DrawGraphTest {

    @Test
    void draw() {
        Point3D rand = new Point3D(-4, -1);
        Point3D rand1 = new Point3D(0, 4);
        Point3D rand2 = new Point3D(3, 0);
        Point3D rand3 = new Point3D(0,-4 );
        Point3D rand4 = new Point3D(6,-4.5 );
        Point3D rand5 = new Point3D(-4,-4 );
        Point3D rand6 = new Point3D(0,0 );
        Node n1 = new Node(rand);
        Node n2 = new Node(rand1);
        Node n3= new Node(rand2);
        Node n4 = new Node(rand3);
        Node n5 = new Node(rand4);
        Node n6 = new Node(rand5);
        Node n7 = new Node(rand6);
        DGraph graph = new DGraph();
        graph.addNode(n1);
        graph.addNode(n2);
        graph.addNode(n3);
        graph.addNode(n4);
        graph.addNode(n5);;
        graph.addNode(n6);;
        graph.addNode(n7);;
        graph.connect(0, 1, 0);
        graph.connect(1, 2, 0);
        graph.connect(2, 3, 0);
        graph.connect(4, 5, 0);
        graph.connect(3, 4, 0);
        graph.connect(5, 6, 0);
        graph.connect(6, 0, 0);
        graph.connect(3, 5, 0);
        graph.connect(5, 3, 0);
        Graph_Algo algo = new Graph_Algo();
        algo.init(graph);
        DrawGraph GraphDraw = new DrawGraph();
        GraphDraw.init(graph);
        GraphDraw.Draw();
        StdDraw.pause(124024);



    }
}