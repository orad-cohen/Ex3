package Tests;

import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.Node;
import dataStructure.node_data;
import gui.DrawGraph;
import org.junit.jupiter.api.Test;
import utils.Point3D;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Graph_AlgoTest {

    @Test
    void init() {
    }

    @Test
    void testInit() {
        Graph_Algo Graph = new Graph_Algo();
        Graph.init("wow.json");
        DrawGraph graph = new DrawGraph();
        graph.Draw(Graph.copy());
    }

    @Test
    void save() {
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
        graph.connect(4, 1, 0);
        graph.connect(1, 4, 0);
        graph.connect(1, 6, 0);
        graph.connect(6, 0, 0);
        graph.connect(6, 2, 0);
        graph.connect(6, 3, 0);
        graph.connect(3, 6, 0);
        graph.connect(3, 5, 0);
        graph.connect(5, 3, 0);
        Graph_Algo algo = new Graph_Algo();
        algo.init(graph);
        algo.save("wow.json");
    }

    @Test
    void isConnected() {
        Point3D rand = new Point3D(1, 1);
        Point3D rand1 = new Point3D(2, 2);
        Point3D rand2 = new Point3D(3, 3);
        Point3D rand3 = new Point3D(4,4 );
        Node n1 = new Node(rand);
        Node n2 = new Node(rand1);
        Node n3= new Node(rand2);
        Node n4 = new Node(rand3);
        DGraph graph = new DGraph();
        graph.addNode(n1);;
        graph.addNode(n2);;
        graph.addNode(n3);;
        graph.addNode(n4);;
        graph.connect(0, 1, 0);
        graph.connect(1, 2, 0);
        graph.connect(2, 3, 0);
        graph.connect(0, 3, 0);
        Graph_Algo algo = new Graph_Algo();
        algo.init(graph);
        assertTrue(algo.isConnected());


    }

    @Test
    void testIsConnected() {
    }

    @Test
    void shortestPathDist() {
        Point3D rand = new Point3D(0, 0);
        Point3D rand1 = new Point3D(-2, 2);
        Point3D rand2 = new Point3D(2, 2);
        Point3D rand3 = new Point3D(1,4 );
        Point3D rand4 = new Point3D(-2, 6);
        Point3D rand5 = new Point3D(2, 6);
        Point3D rand6 = new Point3D(0, 5);
        Point3D rand7 = new Point3D(1,7 );
        Node n1 = new Node(rand);
        Node n2 = new Node(rand1);
        Node n3= new Node(rand2);
        Node n4 = new Node(rand3);
        Node n5 = new Node(rand4);
        Node n6 = new Node(rand5);
        Node n7= new Node(rand6);
        Node n8 = new Node(rand7);
        DGraph graph = new DGraph();
        graph.addNode(n1);
        graph.addNode(n2);
        graph.addNode(n3);
        graph.addNode(n4);
        graph.addNode(n5);
        graph.addNode(n6);
        graph.addNode(n7);
        graph.addNode(n8);
        graph.connect(0, 1, 0);
        graph.connect(0, 2, 0);
        graph.connect(0, 3, 0);
        graph.connect(1, 4, 0);
        graph.connect(2, 3, 0);
        graph.connect(3, 4, 0);
        graph.connect(2, 5, 0);
        graph.connect(3, 6, 0);
        graph.connect(4, 7, 0);
        graph.connect(4, 5, 0);
        graph.connect(5, 7, 0);
        graph.connect(6, 5, 0);
        graph.connect(6, 7, 0);
        Graph_Algo algo = new Graph_Algo();
        algo.init(graph);
        System.out.println(algo.shortestPathDist(0,7));
        System.out.println(algo.shortestPathDist(0,5));
        DrawGraph agraph = new DrawGraph();
        agraph.Draw(algo.copy());

    }



    @Test
    void shortestPath() {
        Point3D rand = new Point3D(0, 0);
        Point3D rand1 = new Point3D(-2, 2);
        Point3D rand2 = new Point3D(2, 2);
        Point3D rand3 = new Point3D(1,4 );
        Point3D rand4 = new Point3D(-2, 6);
        Point3D rand5 = new Point3D(2, 6);
        Point3D rand6 = new Point3D(0, 5);
        Point3D rand7 = new Point3D(1,7 );
        Node n1 = new Node(rand);
        Node n2 = new Node(rand1);
        Node n3= new Node(rand2);
        Node n4 = new Node(rand3);
        Node n5 = new Node(rand4);
        Node n6 = new Node(rand5);
        Node n7= new Node(rand6);
        Node n8 = new Node(rand7);
        DGraph graph = new DGraph();
        graph.addNode(n1);
        graph.addNode(n2);
        graph.addNode(n3);
        graph.addNode(n4);
        graph.addNode(n5);
        graph.addNode(n6);
        graph.addNode(n7);
        graph.addNode(n8);
        graph.connect(0, 1, 0);
        graph.connect(0, 2, 0);
        graph.connect(0, 3, 0);
        graph.connect(1, 4, 0);
        graph.connect(2, 3, 0);
        graph.connect(3, 4, 0);
        graph.connect(2, 5, 0);
        graph.connect(3, 6, 0);
        graph.connect(4, 7, 0);
        graph.connect(4, 5, 0);
        graph.connect(5, 7, 0);
        graph.connect(6, 5, 0);
        graph.connect(6, 7, 0);
        Graph_Algo algo = new Graph_Algo();
        algo.init(graph);
        List<node_data> y = algo.shortestPath(0,5);
        List<node_data> x = algo.shortestPath(0,7);

        for(int p=0; p < x.size(); p++)
        {
            System.out.println("Node: "+x.get(p).getKey()+" with weight of "+x.get(p).getWeight());
        }
        System.out.println("\n");
        for(int p=0; p < y.size(); p++)
        {
            System.out.println("Node: "+y.get(p).getKey()+" with weight of "+y.get(p).getWeight());
        }

        DrawGraph agraph = new DrawGraph();
        agraph.Draw(algo.copy());
    }

    @Test
    void TSP() {
        Point3D rand = new Point3D(1, 1);
        Point3D rand1 = new Point3D(2, 2);
        Point3D rand2 = new Point3D(3, 3);
        Point3D rand3 = new Point3D(4,4 );
        Point3D rand4 = new Point3D(5,5 );
        Point3D rand5 = new Point3D(6,6 );
        Point3D rand6 = new Point3D(7,7 );
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
        graph.connect(4, 1, 0);
        graph.connect(1, 4, 0);
        graph.connect(1, 6, 0);
        graph.connect(6, 0, 0);
        graph.connect(6, 2, 0);
        graph.connect(6, 3, 0);
        graph.connect(3, 6, 0);
        graph.connect(3, 5, 0);
        graph.connect(5, 3, 0);
        Graph_Algo algo = new Graph_Algo();
        algo.init(graph);
        LinkedList<Integer> targets = new LinkedList<>();
        targets.add(4);
        targets.add(6);
        targets.add(5);
        List<node_data> list = algo.TSP(targets);
        Iterator<node_data> nodes = list.iterator();

        while(nodes.hasNext()){
            System.out.println("Node key: "+ nodes.next().getKey());
        }









    }

    @Test
    void copy() {
    }
}