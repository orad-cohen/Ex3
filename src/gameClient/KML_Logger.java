package gameClient;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import dataStructure.node_data;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class  KML_Logger {
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
    public void MessAround(){
        Element lineString = doc.createElement("LineString");
        root.appendChild(lineString);

        Element extrude = doc.createElement("extrude");
        extrude.appendChild(doc.createTextNode("1"));
        lineString.appendChild(extrude);

        Element tesselate = doc.createElement("tesselate");
        tesselate.appendChild(doc.createTextNode("1"));
        lineString.appendChild(tesselate);

        Element altitudeMode = doc.createElement("altitudeMode");
        altitudeMode.appendChild(doc.createTextNode("absolute"));
        lineString.appendChild(altitudeMode);


    }


    public static void main(String[] args){

       KML_Logger one = new KML_Logger();
       try{
           FileWriter file = new FileWriter("text.kml");
           one.MessAround();
           File whoa = new File("text.kml");
           one.writeFile(whoa);
       }
       catch (Exception e){

       }





    }


}
