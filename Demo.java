public class Demo {
    public static void main(String[] args) {
        // Set the canvas size
        StdDraw.setCanvasSize(800, 800);

        // Enable double buffering for smoother drawing
        StdDraw.enableDoubleBuffering();

        while (true) {
            // Check if the mouse is pressed
            if (StdDraw.mousePressed()) {
                // Get the mouse coordinates
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();

                // Draw a point at the mouse coordinates
                StdDraw.point(x, y);

                // Show the drawing
                StdDraw.show();
            }
        }
    }
}
