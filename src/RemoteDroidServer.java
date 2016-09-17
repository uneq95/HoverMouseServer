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
	private static boolean isSecondaryCamActive = false;

	public static void main(String[] args) {

		new ServerUtil();
		try {
			robot = new Robot();
			server = new ServerSocket(Constants.SERVER_PORT); // Create a server
																// socket on
			// port 8993
			client = server.accept(); // Listens for a connection to be made to
										// this socket and accepts it
			in = new BufferedReader(new InputStreamReader(client.getInputStream())); // the
																						// input
																						// stream
																						// where
																						// data
																						// will
																						// come
																						// from
																						// client
		} catch (IOException e) {
			System.out.println("Error in opening Socket");
			System.exit(-1);
		} catch (AWTException e) {
			System.out.println("Error in creating robot instance");
			System.exit(-1);
		}
		int x = 0, y = 0;
		// read input from client while it is connected
		while (isConnected) {
			try {

				line = in.readLine();
				// System.out.println(line);//read input from client
				switch (line) {
				case Constants.LEFT_DOWN_ACTION:
					robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
					break;
				case Constants.LEFT_UP_ACTION:
					robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
					break;
				case Constants.RIGHT_CLICK_ACTION:
					robot.mousePress(InputEvent.BUTTON3_MASK);
					robot.mouseRelease(InputEvent.BUTTON3_MASK);
					break;
				case Constants.CAMERA_SWAP:
					isSecondaryCamActive = !isSecondaryCamActive;
					break;
				case Constants.PAGE_UP_ACTION:
					robot.keyPress(KeyEvent.VK_PAGE_UP);
					robot.keyRelease(KeyEvent.VK_PAGE_UP);
					break;
				case Constants.PAGE_DOWN_ACTION:
					robot.keyPress(KeyEvent.VK_PAGE_DOWN);
					robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
					break;
				case Constants.SCROLL_ACTION:
					String scrollData =in.readLine();
					int wheelAmt =Integer.parseInt(scrollData);
					robot.mouseWheel(wheelAmt/100);
					break;
				default:
					System.out.println(line); // print whatever we get from
					// client
					String[] cordinates = line.split(" ");
					int dx = Integer.parseInt(cordinates[0]);
					int dy = Integer.parseInt(cordinates[1]);
					Point p = MouseInfo.getPointerInfo().getLocation();
					if (dx > -5 & dx < 0)
						dx = 0;
					if (dy > -5 & dy < 0)
						dy = 0;
					x = p.x;
					y = p.y;
					// MouseUtils.mouseGlide(x, y, dx, dy, 100, 100);
					if (!isSecondaryCamActive) {
						// when primary camera is active
						x = x + dy;
						y = y - dx;
					} else {
						// when secondary camera is active
						// TODO change code to adapt to secondary cam
						x = x + dy;
						y = y + dx;
					}
					//x=3*x;
					//y=3*y;
					robot.mouseMove(x, y);
					/*
					 * for (i = 0; i < 10; i++) { x = x + dy / 5; y = y - dx /
					 * 5; robot.mouseMove(x, y); robot.delay(1); }
					 */
					// robot.mouseMove(x+2*dy, y-2*dx);
					if (x < 0)
						x = 0;
					else if (x > 1366)
						x = 1366;

					if (y < 0)
						y = 0;
					else if (y > 768)
						y = 768;
					
//					robot.mouseMove(x, y);
				}

			} catch (IOException e) {
				System.out.println("Read failed");
				try {
					server.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.exit(-1);
			}
		}
	}
}