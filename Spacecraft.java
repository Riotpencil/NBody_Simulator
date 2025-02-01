import java.awt.event.KeyEvent;
public class Spacecraft extends Body {
    public Spacecraft(double xP, double yP, double xV, double yV, double m, String img) {
        super(xP, yP, xV, yV, m, img);
    }

    public void control() {
        if (StdDraw.isKeyPressed(KeyEvent.VK_UP)) {
            yyVel += 0.1;
        }
        if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN)) {
            yyVel -= 0.1;
        }
        if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT)) {
            xxVel -= 0.1;
        }
        if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT)) {
            xxVel += 0.1;
        }
    }
}