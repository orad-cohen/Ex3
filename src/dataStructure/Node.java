package dataStructure;

import utils.Point3D;

public class Node implements node_data {
    private int key =Integer.MIN_VALUE;
    private Point3D loc;
    private double weight;
    private int tag;
    private String info;

    public Node(Point3D loc){

        this.loc=loc;
        this.weight=Double.POSITIVE_INFINITY;
        this.tag=0;
        this.info="info";



    }
    public Node(int key, String Loc, double weight, int tag, String info){
        this.key=key;
        this.loc=new Point3D(Loc);
        this.weight=weight;
        this.tag=tag;
        this.info=info;



    }


    public Node(int _id ,node_data n){
        this.key=_id;
        this.loc=n.getLocation();
        this.weight=n.getWeight();
        this.tag=n.getTag();
        this.info=n.getInfo();

    }
    public Node(node_data n){
        this.key=n.getKey();
        this.loc=n.getLocation();
        this.weight=n.getWeight();
        this.tag=n.getTag();
        this.info=n.getInfo();

    }

    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public Point3D getLocation() {
        return this.loc;
    }

    @Override
    public void setLocation(Point3D p) {
        this.loc = p;

    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(double w) {
        this.weight=w;

    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        this.info = s;

    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag=t;

    }



}
