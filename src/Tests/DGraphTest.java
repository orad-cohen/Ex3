package Tests;

import dataStructure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Point3D;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class DGraphTest {
    private Node n1;
    private Node n2;
    private Node n3;
    private Node n4;


    @BeforeEach
    public void setup(){
        Point3D rand = new Point3D(1, 1);
        Point3D rand1 = new Point3D(2, 2);
        Point3D rand2 = new Point3D(3, 3);
        Point3D rand3 = new Point3D(4,4 );
        n1 = new Node(rand);
        n2 = new Node(rand1);
        n3= new Node(rand2);
        n4 = new Node(rand3);

    }


    @Test
    void addNode() {
        DGraph graph = new DGraph();
        graph.addNode(n1);;
        graph.addNode(n2);;
        graph.addNode(n3);;
        assertEquals(graph.nodeSize(),3);
        graph.addNode(n4);
        assertEquals(graph.nodeSize(),4);

    }

    @Test
    void connect() {
        DGraph graph = new DGraph();
        graph.addNode(n1);;
        graph.addNode(n2);;
        graph.addNode(n3);;
        graph.addNode(n4);
        Iterator<node_data> keys = graph.getV().iterator();
        int n = keys.next().getKey();
        int c;
        while(keys.hasNext()){
            c= keys.next().getKey();
            graph.connect(n, c, 0);
            assertNotNull(graph.getE(n));
            n = c;}



    }

    @Test
    void getV() {
        DGraph graph = new DGraph();
        graph.addNode(n1);;
        graph.addNode(n2);;
        graph.addNode(n3);;
        graph.addNode(n4);;
        graph.connect(0, 1, 0);
        graph.connect(1, 2, 0);
        graph.connect(2, 3, 0);
        graph.connect(3, 0, 0);
        Point3D rand = new Point3D(1, 1);
        Point3D rand1 = new Point3D(2, 2);
        Point3D rand2 = new Point3D(3, 3);
        Point3D rand3 = new Point3D(4,4 );
        LinkedList<Point3D> list = new LinkedList<>();
        list.add(rand);
        list.add(rand1);
        list.add(rand2);
        list.add(rand3);

        Iterator<Point3D> listite = list.iterator();
        Iterator<node_data> ite = graph.getV().iterator();
        while(ite.hasNext()){
            assertEquals(ite.next().getLocation().toString(), listite.next().toString());


        }





    }

    @Test
    void getE() {
        DGraph graph = new DGraph();
        graph.addNode(n1);
        graph.addNode(n2);
        graph.addNode(n3);
        graph.addNode(n4);

        graph.connect(0, 1, 0);
        graph.connect(0, 2, 0);
        graph.connect(0, 3, 0);
        graph.connect(1, 1, 0);
        graph.connect(1, 2, 0);
        graph.connect(2, 3, 0);
        Collection<edge_data> n1edge = graph.getE(0);
            Iterator<edge_data> ite1 = n1edge.iterator();
            edge_data nextedge = ite1.next();
            while (ite1.hasNext()) {
                System.out.println(nextedge.getSrc() + " -> " + nextedge.getDest());
                nextedge = ite1.next();
            }

    }

    @Test
    void removeNode() {
        DGraph graph = new DGraph();
        graph.addNode(n1);;
        graph.addNode(n2);;
        graph.addNode(n3);;
        graph.addNode(n4);;
        graph.connect(0, 1, 0);
        graph.connect(0, 2, 0);
        graph.connect(3, 0, 0);
        graph.connect(2, 3, 0);

        graph.removeNode(2);
        Collection<edge_data> n1edge = graph.getE(0);
        Collection<edge_data> n4edge = graph.getE(3);
        Iterator<edge_data> ite1 = n1edge.iterator();
        Iterator<edge_data> ite4 = n4edge.iterator();
        while(ite1.hasNext()){
            Edge e1 = (Edge)ite1.next();
            System.out.println(e1.getSrc()+" to "+e1.getDest());
        }
        while(ite4.hasNext()){
            Edge e4 =(Edge)ite4.next();
            System.out.println(e4.getSrc()+" to "+e4.getDest());

        }
    }




}