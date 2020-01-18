package gameClient;
import java.lang.Thread;
import utils.StdDraw;

import static gameClient.MyGameGUI.*;

public class GuiUpdate extends Thread {
    @Override
    public void run() {
        while (MyGameGUI.getGame().isRunning()){
           try{
               StdDraw.backgound();
               DrawNodes();
               DrawEdges();
               DrawFruits();
               StdDraw.DrawRobot();
               String s = ""+(MyGameGUI.getGame().timeToEnd()/1000);
               StdDraw.DrawTime(s);
               StdDraw.show();
               Thread.sleep(100);
           }
            catch (Exception e){

            }
        }

        return;

    }
}
