package things;

import utils.Point3D;


public class Robot {


    int src;
    int dest;
    int id;
    double value;
    Point3D pos;
    int speed;
    int node;


    public Robot() {;}

    public Robot(int rid, int src, int dest,Point3D pos,double value,int s) {
        this.id=rid;
        this.src=src;
        this.dest=dest;
        this.value=value;
        this.pos=pos;
        this.speed=s;
        this.setLast(-1);
    }

    //Getters and Setters

    public int Last() { return this.node;}
    public void setLast(int x){this.node=x;}
    public int getSpeed() { return this.speed; }
    public void Update(int rid, int src, int dest, Point3D pos, double value, int s){
        this.id=rid;
        this.src=src;
        this.dest=dest;
        this.value=value;
        this.pos=pos;
        this.speed=s;


    }
    public double getV() { return this.value; }
    public void setV(double v) { this.value=v; }

    public int getID() { return this.id; }

    public void setSrc(int s) { this.src=s; }
    public int getSrc() { return this.src; }

    public int getDest() { return this.dest; }
    public void setDest(int d) { this.dest=d; }

    public Point3D getPos() { return this.pos; }
    public void setPos(Point3D np) { this.pos= np; }
    public void setPos(double x, double y, double z) { this.pos=new Point3D(x,y,z); }
    public void incSpeed() { this.speed++; }
}