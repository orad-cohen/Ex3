package gameClient;
import java.lang.Thread;
import utils.StdDraw;

import static gameClient.MyGameGUI.*;

public class GuiUpdate extends Thread {
    static StdDraw Draw;
    @Override
    public void run() {

        while (GameClient.isRunning()){

           try{
               Draw.backgound();
               DrawNodes();
               DrawEdges();
               DrawFruits();
               Draw.DrawRobot();
               String s = ""+(GameClient.getGame().timeToEnd()/1000);
               Draw.DrawTime(s);
               Draw.show();
               Thread.sleep(100);
           }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        return;

    }
}
