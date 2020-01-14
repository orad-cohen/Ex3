package dataStructure;

public class Edge implements edge_data{
    private int src,dest;
    private double weight;
    private int tag;
    private String info = "info";


    public Edge(int src, int dest, double weight){
        this.src=src;
        this.dest=dest;
        this.weight=weight;


    }
    public Edge(int src, int dest, double weight, int tag, String info){
        this.src=src;
        this.dest=dest;
        this.weight=weight;
        this.tag=tag;
        this.info=info;


    }


    @Override
    public int getSrc() {
        return this.src;
    }

    @Override
    public int getDest() {
        return this.dest;
    }

    @Override
    public double getWeight() {
        return this.weight;
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
        this.tag = t;

    }
}
