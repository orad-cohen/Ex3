package things;

import utils.Point3D;

public class Fruit {

        private double value;
        private int type;
        private Point3D pos;
        public int from;
        public int to;
        public int tag;

        public Fruit() {;}
        public Fruit(double value, int y, Point3D pos) {
            this.value=value;
            this.pos=pos;
            this.type=y;
        }

        public Fruit(Fruit f) {
            this.from=f.from;
            this.to=f.to;
            this.pos=f.pos;
            this.type=f.type;
            this.value=f.value;
        }

        public void setTag(int x) {this.tag=x;}
        public int getTag() { return this.tag; }
        public double getValue() { return value; }
        public void setValue(double value) { this.value = value; }

        public int getType() { return type; }
        public void setType(int type) { this.type = type; }

        public Point3D getPos() { return pos; }
        public void setPos(Point3D pos) { this.pos = pos; }

        public int getFrom() { return from; }
        public void setFrom(int from) { this.from = from; }

        public int getTo() { return to; }
        public void setTo(int to) { this.to = to; }


}
