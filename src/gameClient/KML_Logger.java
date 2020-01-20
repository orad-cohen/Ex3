package gameClient;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import dataStructure.DGraph;
import dataStructure.graph;
import dataStructure.node_data;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class  KML_Logger extends Thread{
    private Document doc;
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
    public void addPath(List<node_data> path, String pathName) {
        Element placemark = doc.createElement("Placemark");
        root.appendChild(placemark);

        if(pathName != null) {
            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode(pathName));
            placemark.appendChild(name);
        }

        Element lineString = doc.createElement("LineString");
        placemark.appendChild(lineString);

        Element extrude = doc.createElement("extrude");
        extrude.appendChild(doc.createTextNode("1"));
        lineString.appendChild(extrude);

        Element tesselate = doc.createElement("tesselate");
        tesselate.appendChild(doc.createTextNode("1"));
        lineString.appendChild(tesselate);

        Element altitudeMode = doc.createElement("altitudeMode");
        altitudeMode.appendChild(doc.createTextNode("absolute"));
        lineString.appendChild(altitudeMode);

        Element coords = doc.createElement("coordinates");
        String points = "";
        ListIterator<node_data> itr = path.listIterator();
        while(itr.hasNext()) {
            node_data p = itr.next();
            points += p.getLocation().x() + "," + p.getLocation().y()+ "\n";
        }
        coords.appendChild(doc.createTextNode(points));
        lineString.appendChild(coords);
    }

    /**
     * Write this KML object to a file.
     * @param file
     * @return
     */
    public boolean writeFile(File file) {
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


    public void addItem(String longta,String lat,String alt,String time,char type){
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
        coordinates.appendChild(doc.createTextNode(longta+","+lat+","+alt));
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


    public static void main(String[] args){
       KML_Logger one = new KML_Logger();
       try{
           FileWriter file = new FileWriter("new.kml");
           one.addItem("34","35","0","1",'r');
           one.addItem("34.001","35.001","0","2",'r');
           one.addItem("34.002","35.002","0","3",'r');
           one.addItem("34.003","35.003","0","4",'r');
           one.addItem("34.004","35.004","0","5",'r');
           one.addItem("34.005","35.005","0","6",'r');
           one.addItem("34.006","35.006","0","7",'r');
           one.addItem("34.007","35.007","0","8",'r');
           one.addItem("34.008","35.008","0","9",'r');
           one.addItem("34.009","35.009","0","10",'r');
           one.addEdge("34,35,0","34.001,35.001,0");
           File whoa = new File("new.kml");
           one.writeFile(whoa);
       }
       catch (Exception e){

       }



    }
    @Override
    public void run(){



    }


}
