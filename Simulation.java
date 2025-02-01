public class Simulation {
    public static void main(String[] args) {
        // Initialize bodies and spacecraft
        StdDraw.setScale(-10, 10);
        Body[] bodies = {
                new Body(1,2, 0, 0, 5.97e23, "saturn.gif"),
                new Body(-3, -2, 0, 0, 6.42e24, "earth.gif")
        };
        //StdDraw.setCanvasSize(800, 800);
        int N = bodies.length;

        Spacecraft spacecraft = new Spacecraft(-8, 0, 0, 0, 1e26, "kevin.gif");

        // Set the canvas size
        StdDraw.enableDoubleBuffering();

        while (true) {
            // Clear the canvas
            StdDraw.clear();

            double[] xForces = new double[N];
            double[] yForces = new double[N];
            for (int i =  0; i < N; i++) {
                xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
                yForces[i] = bodies[i].calcNetForceExertedByY(bodies);
            }

            for (int i = 0; i < N; i++) {
                bodies[i].update(0.1, xForces[i], yForces[i]);

            }
            
            // Update forces and positions
            for (Body body : bodies) {
                double fX = spacecraft.calcForceExertedByX(body);
                double fY = spacecraft.calcForceExertedByY(body);
                spacecraft.update(0.1, fX, fY);
                body.update(0.1, -fX, -fY);
            }

            // Control the spacecraft
            spacecraft.control();

            // Draw bodies and spacecraft
            for (Body body : bodies) {
                body.draw();
            }
            spacecraft.draw();

            // Show the drawing
            StdDraw.show();
            StdDraw.pause(20);
        }
    }
    }



