import java.awt.AWTException;
import java.awt.Robot;

public class MouseUtils {

	public static void mouseGlide(int x1, int y1, int x2, int y2, int t, int n) {
	    try {
	        Robot r = new Robot();
	        double dx = (x2 - x1) / ((double) n);
	        double dy = (y2 - y1) / ((double) n);
	        double dt = t / ((double) n);
	        for (int step = 1; step <= n; step++) {
	            Thread.sleep((int) dt);
	            r.mouseMove((int) (x1 + dx * step), (int) (y1 + dy * step));
	        }
	    } catch (AWTException e) {
	        e.printStackTrace();
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
	}
}
