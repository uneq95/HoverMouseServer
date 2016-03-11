import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.MouseInfo;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.PointerInfo;

public class RemoteDroidServer {

    private static ServerSocket server = null;
    private static Socket client = null;
    private static BufferedReader in = null;
    private static String line;
    private static boolean isConnected = true;
    private static Robot robot;
    private static final int SERVER_PORT = 8998;


    public static void main(String[] args) {
        boolean leftpressed = false;
        boolean rightpressed = false;

        try {
            robot = new Robot();
            server = new ServerSocket(SERVER_PORT); //Create a server socket on port 8998
            client = server.accept(); //Listens for a connection to be made to this socket and accepts it
            in = new BufferedReader(new InputStreamReader(client.getInputStream())); //the input stream where data will come from client
        } catch (IOException e) {
            System.out.println("Error in opening Socket");
            System.exit(-1);
        } catch (AWTException e) {
            System.out.println("Error in creating robot instance");
            System.exit(-1);
        }
        int x = 0, y = 0, i;
        //read input from client while it is connected
        while (isConnected) {
            try {

                line = in.readLine();
                //System.out.println(line);//read input from client
                if (line.equals("left_down")) {
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);                    	
                }else if(line.equals("left_up")){
                	robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                	
                } else if (line.equals("right")) {
                    robot.mousePress(InputEvent.BUTTON3_MASK);
                    robot.mouseRelease(InputEvent.BUTTON3_MASK);
                } else {
                    //  System.out.println(line); //print whatever we get from client
                    String[] cordinates = line.split(" ");
                    int dx = Integer.parseInt(cordinates[0]);
                    int dy = Integer.parseInt(cordinates[1]);
                    Point p = MouseInfo.getPointerInfo().getLocation();
                    x = p.x;
                    y = p.y;
                    //MouseUtils.mouseGlide(x, y, dx, dy, 100, 100);
                    for (i = 0; i < 10; i++) {
                        x = x + dy / 5;
                        y = y - dx / 5;
                        robot.mouseMove(x, y);
                        robot.delay(1);
                    }
                    //robot.mouseMove(x+2*dy, y-2*dx);
                    if (x < 0)
                        x = 0;
                    else if (x > 1366)
                        x = 1366;

                    if (y < 0)
                        y = 0;
                    else if (y > 768)
                        y = 768;


//                    System.out.println(x);
//                    System.out.println(y);
                }


              /*  //if user clicks on next
                if(line.equalsIgnoreCase("next")){
                    //Simulate press and release of key 'n'
                    robot.keyPress(KeyEvent.VK_N);
                    robot.keyRelease(KeyEvent.VK_N);
                }
                //if user clicks on previous
                else if(line.equalsIgnoreCase("previous")){
                    //Simulate press and release of key 'p'
                    robot.keyPress(KeyEvent.VK_P);
                    robot.keyRelease(KeyEvent.VK_P);
                }
                //if user clicks on play/pause
                else if(line.equalsIgnoreCase("play")){
                    //Simulate press and release of spacebar
                    robot.keyPress(KeyEvent.VK_SPACE);
                    robot.keyRelease(KeyEvent.VK_SPACE);
                }
                //input will come in x,y format if user moves mouse on mousepad
                else if(line.contains(",")){
                    float movex=Float.parseFloat(line.split(",")[0]);//extract movement in x direction
                    float movey=Float.parseFloat(line.split(",")[1]);//extract movement in y direction
                    Point point = MouseInfo.getPointerInfo().getLocation(); //Get current mouse position
                    float nowx=point.x;
                    float nowy=point.y;
                    robot.mouseMove((int)(nowx+movex),(int)(nowy+movey));//Move mouse pointer to new location
                }
                //if user taps on mousepad to simulate a left click
                else if(line.contains("left_click")){
                    //Simulate press and release of mouse button 1(makes sure correct button is pressed based on user's dexterity)
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                }
                //Exit if user ends the connection
                else if(line.equalsIgnoreCase("exit")){
                    isConnected=false;
                    //Close server and client socket
                  //  server.close();
                  //  client.close();
                }*/
            } catch (IOException e) {
                System.out.println("Read failed");
                System.exit(-1);
            }
        }
    }
}