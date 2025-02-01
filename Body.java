import java.awt.*;
import java.awt.image.BufferedImage;

public class Body {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    private BufferedImage planetImage;

    public double RAD = 0;
    private static final double SOFTENING_PARAMETER = 1e9;

    private static final double G = 6.67e-11;

    public Body(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;

        planetImage = generatePlanetImage();
    }

    public Body(Body b) {
        xxPos = b.xxPos;
        yyPos = b.yyPos;
        xxVel = b.xxVel;
        yyVel = b.yyVel;
        mass = b.mass;
        imgFileName = b.imgFileName;
        // this(b.xxPos, b.yyPos, b.xxVel, b.yyVel, b.mass, b.imgFileName);

        planetImage = generatePlanetImage();
    }

    private BufferedImage generatePlanetImage() {
        int diameter = 50; // Example diameter
        BufferedImage image = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        // Draw a circle representing the planet
        g2d.setColor(Color.BLUE); // Example color
        g2d.fillOval(0, 0, diameter, diameter);

        g2d.dispose();
        return image;
    }

    public double calcDistance(Body b) {
        double dx = this.xxPos - b.xxPos;
        double dy = this.yyPos - b.yyPos;
        double r = Math.hypot(dx, dy);
        return r;
    }

    public double calcForceExertedBy(Body b) {
        double r = calcDistance(b) + SOFTENING_PARAMETER;
        double F = G * this.mass * b.mass / (r * r);
        return F;
    }

    /** this.mass or this.calcDistance(b) needed? */

    public double calcForceExertedByX(Body b) {
        double F = calcForceExertedBy(b);
        double r = calcDistance(b);
        return F * (b.xxPos - this.xxPos) / r;
    }

    public double calcForceExertedByY(Body b) {
        double F = calcForceExertedBy(b);
        double r = calcDistance(b);
        return F * (b.yyPos - this.yyPos) / r;
    }

    /** Body[] allBodys = {samh, rocinante, aegir};
     samh.calcNetForceExertedByX(allBodys);
     samh.calcNetForceExertedByY(allBodys); */
    public double calcNetForceExertedByX(Body[] allBodys) {
        double netForceX = 0;
        for (Body b : allBodys) {
            if (!this.equals(b)) {
                netForceX += calcForceExertedByX(b);
            }
        }
        return netForceX;
    }

    public double calcNetForceExertedByY(Body[] allBodys) {
        double netForceY = 0;
        for (Body b : allBodys) {
            if (!this.equals(b)) {
                netForceY += calcForceExertedByY(b);
            }
        }
        return netForceY;
    }

    public void update(double dt, double fX, double fY) {
        double ax = fX / mass;
        double ay = fY / mass;
        xxVel += dt * ax;
        yyVel += dt * ay;
        xxPos += dt * xxVel;
        yyPos += dt * yyVel;
    }

    public void draw() {
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
//        StdDraw.picture(xxPos, yyPos, planetImage);
    }

//    public void draw() {
//        double canvasX = StdDraw.userXToCanvas(xxPos);
//        double canvasY = StdDraw.userYToCanvas(yyPos);
//        int imgWidth = planetImage.getWidth();
//        int imgHeight = planetImage.getHeight();
//        Graphics g = StdDraw.getGraphics();
//        g.drawImage(planetImage, (int) (canvasX - imgWidth / 2), (int) (canvasY - imgHeight / 2), null);
//    }

    public boolean isColliding(Body b) {
        return calcDistance(b) < 2 * RAD;
    }

    public void handleCollision(Body b) {
        if (isColliding(b)) {
            double m1 = this.mass;
            double m2 = b.mass;
            double v1x = this.xxVel;
            double v1y = this.yyVel;
            double v2x = b.xxVel;
            double v2y = b.yyVel;

            double newV1x = (v1x * (m1 - m2) + 2 * m2 * v2x) / (m1 + m2);
            double newV1y = (v1y * (m1 - m2) + 2 * m2 * v2y) / (m1 + m2);
            double newV2x = (v2x * (m2 - m1) + 2 * m1 * v1x) / (m1 + m2);
            double newV2y = (v2y * (m2 - m1) + 2 * m1 * v1y) / (m1 + m2);

            this.xxVel = newV1x;
            this.yyVel = newV1y;
            b.xxVel = newV2x;
            b.yyVel = newV2y;
        }
    }
}
