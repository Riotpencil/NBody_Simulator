public class NBody {
    public static double readRadius(String filename) {
        In in = new In(filename);

//        int firstItemInFile = in.readInt();
//        double secondItemInFile = in.readDouble();
//        String thirdItemInFile = in.readString();
//        String fourthItemInFile = in.readString();
//        double fifthItemInFile = in.readDouble();

        /** Although N is not used, it is necessary to read it from the file. */
        int N = in.readInt();
        double R = in.readDouble();
        return R;
    }

    public static Body[] readBodies(String filename) {
        In in = new In(filename);
        int N = in.readInt();
        double R = in.readDouble();
        Body[] bodies = new Body[N];
        for (int i = 0; i < N; i++) {
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            String imgFileName = in.readString();
            bodies[i] = new Body(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
        }
        return bodies;
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        boolean bounce = Boolean.parseBoolean(args[2]);
        String filename = args[3];

        double radius = readRadius(filename);
        Body[] bodies = readBodies(filename);
        int N = bodies.length;

        StdDraw.enableDoubleBuffering();

        StdDraw.setScale(-radius, radius);
        StdDraw.clear();

        StdDraw.picture(0, 0, "images/starfield.jpg");

        for (Body b : bodies) {
            b.draw();
        }
        // StdDraw.show();

        double time = 0;
        while (time < T) {
            double[] xForces = new double[N];
            double[] yForces = new double[N];
            for (int i =  0; i < N; i++) {
                xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
                yForces[i] = bodies[i].calcNetForceExertedByY(bodies);
                /** xForces[0] = 1.0138727977023836E27 */
                 }

            for (int i = 0; i < N; i++) {
                bodies[i].update(dt, xForces[i], yForces[i]);
                /** it will cause overlapping */
                //bodies[i].draw();
            }

            if (bounce) {
                for (int i = 0; i < N; i++) {
                    for (int j = i + 1; j < N; j++) {
                        bodies[i].handleCollision(bodies[j]);
                    }
                }
            }

            StdDraw.picture(0, 0, "images/starfield.jpg");
            for (Body b: bodies) {
                b.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            time += dt;
        }

        StdOut.printf("%d\n", bodies.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < bodies.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
                    bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);
        }

    }
}
