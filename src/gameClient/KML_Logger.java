package gameClient;

import dataStructure.edge_data;
import dataStructure.node_data;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import utils.StdDraw;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Iterator;

public class  KML_Logger extends Thread{
    private static Document doc;
    private Element root;
    public KML_Logger() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.newDocument();
            Element kml = doc.createElementNS("http://www.opengis.net/kml/2.2", "kml");
            doc.appendChild(kml);
            root = doc.createElement("Document");
            kml.appendChild(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Write this KML object to a file.
     * @param file
     * @return
     */
    public static boolean writeFile(File file) {
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource src = new DOMSource(doc);
            StreamResult out = new StreamResult(file);
            transformer.transform(src, out);
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Read the KML file into this object.
     * @param file
     */
    public void readFile(File file) {
        // TODO read KML file
    }


    public void addRobot(String pos,String time){
        Element Placemark = doc.createElement("Placemark");
        root.appendChild(Placemark);

        Element Style = doc.createElement("Style");
        Placemark.appendChild(Style);

        Element IconStyle = doc.createElement("IconStyle");
        Style.appendChild(IconStyle);

        Element Icon = doc.createElement("Icon");
        IconStyle.appendChild(Icon);

        Element href = doc.createElement("href");
        href.appendChild(doc.createTextNode("http://maps.google.com/mapfiles/kml/shapes/webcam.png"));
        Icon.appendChild(href);

        Element tiTimeStampme = doc.createElement("TimeStamp");
        Placemark.appendChild(tiTimeStampme);

        Element when = doc.createElement("when");
        when.appendChild(doc.createTextNode(time));
        tiTimeStampme.appendChild(when);

        Element Point = doc.createElement("Point");
        Placemark.appendChild(Point);

        Element drawOrder = doc.createElement("gx:drawOrder");
        drawOrder.appendChild(doc.createTextNode("1"));
        Point.appendChild(drawOrder);

        Element coordinates = doc.createElement("coordinates");
        coordinates.appendChild(doc.createTextNode(pos));
        Point.appendChild(coordinates);

    }

    public void addFruit(String pos,String time,int type){
        Element Placemark = doc.createElement("Placemark");
        root.appendChild(Placemark);

        Element Style = doc.createElement("Style");
        Placemark.appendChild(Style);

        Element IconStyle = doc.createElement("IconStyle");
        Style.appendChild(IconStyle);

        Element Icon = doc.createElement("Icon");
        IconStyle.appendChild(Icon);

        Element href = doc.createElement("href");
        if(type == -1) {
            href.appendChild(doc.createTextNode("http://maps.google.com/mapfiles/kml/paddle/red-stars.png"));
        }
        else{
            href.appendChild(doc.createTextNode("http://maps.google.com/mapfiles/kml/paddle/ylw-stars.png"));
        }
        Icon.appendChild(href);

        Element tiTimeStampme = doc.createElement("TimeStamp");
        Placemark.appendChild(tiTimeStampme);

        Element when = doc.createElement("when");
        when.appendChild(doc.createTextNode(time));
        tiTimeStampme.appendChild(when);

        Element Point = doc.createElement("Point");
        Placemark.appendChild(Point);

        Element drawOrder = doc.createElement("gx:drawOrder");
        drawOrder.appendChild(doc.createTextNode("1"));
        Point.appendChild(drawOrder);

        Element coordinates = doc.createElement("coordinates");
        coordinates.appendChild(doc.createTextNode(pos));
        Point.appendChild(coordinates);

    }

    public void addNode(String pos){
        Element Placemark = doc.createElement("Placemark");
        root.appendChild(Placemark);

        Element Point = doc.createElement("Point");
        Placemark.appendChild(Point);

        Element drawOrder = doc.createElement("gx:drawOrder");
        drawOrder.appendChild(doc.createTextNode("1"));
        Point.appendChild(drawOrder);

        Element coordinates = doc.createElement("coordinates");
        coordinates.appendChild(doc.createTextNode(pos));
        Point.appendChild(coordinates);

    }

    public void addEdge(String pos1, String pos2){
        Element Placemark = doc.createElement("Placemark");
        root.appendChild(Placemark);

        Element Style = doc.createElement("Style");
        Placemark.appendChild(Style);

        Element LineStyle = doc.createElement("LineStyle");
        Style.appendChild(LineStyle);

        Element color = doc.createElement("color");
        color.appendChild(doc.createTextNode("ff0000ff"));
        LineStyle.appendChild(color);

        Element width = doc.createElement("width");
        width.appendChild(doc.createTextNode("2"));
        LineStyle.appendChild(width);

        Element LineString = doc.createElement("LineString");
        Placemark.appendChild(LineString);

        Element coordinates = doc.createElement("coordinates");
        coordinates.appendChild(doc.createTextNode(pos1+" "+pos2));
        LineString.appendChild(coordinates);
    }

    public void insertRobot(long time){
        try{
            JSONArray bots = new JSONArray(GameClient.getGame().getRobots().toString());

            for(int i = 0;i<bots.length();i++){
                JSONObject robObj = new JSONObject(bots.get(i).toString());
                JSONObject curBot = new JSONObject(robObj.get("Robot").toString());

                String pos = curBot.get("pos").toString();
                addRobot(pos,Long.toString(time));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void insertFruit(long time){
        try{
            JSONArray Fruits = new JSONArray(GameClient.getGame().getFruits().toString());

            for(int i = 0;i<Fruits.length();i++){
                JSONObject Fruit = new JSONObject(Fruits.get(i).toString());
                JSONObject curFruit = new JSONObject(Fruit.get("Fruit").toString());

                int type = curFruit.getInt("type");
                String pos = curFruit.get("pos").toString();
                addFruit(pos,Long.toString(time),type);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void SaveFile(String PathName){

        File kmlFile = new File(PathName+".kml");
        writeFile(kmlFile);
    }

    @Override
    public void run(){
        while (!(GameAuto.getLogger()==1)){
            try{
                sleep(40);
            }
            catch (Exception e){

            }
        }

        while (MyGameGUI.getGraph()==null){

        }

        Iterator<node_data> nodes = MyGameGUI.getGraph().getV().iterator();
        while(nodes.hasNext()){
            try{
                sleep(30);
            }
            catch (Exception e ){

            }
            node_data CurNode = nodes.next();
            addNode(CurNode.getLocation().toString());
            Iterator<edge_data> EdgeIte = MyGameGUI.getGraph().getE(CurNode.getKey()).iterator();
            while(EdgeIte.hasNext()){
                edge_data CurEdge = EdgeIte.next();
                String pos2 = MyGameGUI.getGraph().getNode(CurEdge.getDest()).getLocation().toString();
                addEdge(CurNode.getLocation().toString(),pos2);
            }

        }
        long maxTime = 0;
        maxTime = GameClient.getGame().timeToEnd();

        while (GameClient.getGame().timeToEnd()/100>10) {
            try {
                sleep(500);
            }
            catch (Exception e){

            }
            insertRobot(maxTime - GameClient.getGame().timeToEnd());
            insertFruit(maxTime - GameClient.getGame().timeToEnd());
        }

        SaveFile(StdDraw.SaveKml());




    }


    public static void main(String[] args){
        KML_Logger one = new KML_Logger();
        try{
            one.addRobot("34,35,0","0");
            one.addEdge("34,35,0","34.001,35.001,0");
            one.addRobot("34.001,35.001,0","1");
            one.addEdge("34.001,35.001,0","34.002,35.002,0");
            one.addRobot("34.002,35.002,0","2");
            File whoa = new File("new.kml");
            one.writeFile(whoa);
        }
        catch (Exception e){

        }


    }


}
