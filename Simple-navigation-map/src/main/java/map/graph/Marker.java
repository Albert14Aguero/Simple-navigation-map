package map.graph;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.IOException;

public class Marker<V> {

    public Marker(GVertex<V> startVertex, GVertex<V> endVertex) {
        this.startVertex = startVertex;
        this.endVertex = endVertex;
        this.startPosition = startVertex.getPosition();
        this.endPosition = endVertex.getPosition();
        this.dt = 0.0;
        this.t = 0.0;
        this.moving = false;

        try {
            this.icon = ImageIO.read(getClass().getResourceAsStream("../../images/logo.png"));
            this.icon =this.icon.getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING);
        } catch (IOException ex) {

            System.err.printf("Excepción: '%s'%n", ex.getMessage());
        }
    }

    public void start() {
        t = 0.0;
        setMoving(true);
    }

    public void move() {
        if (moving = (t <= 1.0)) {
            t += dt;
        }
    }

    public void paint(Graphics2D g) {
        if (icon != null) {
            g.drawImage(icon,
                    (int) ((startPosition.x + t * (endPosition.x - startPosition.x)) - icon.getWidth(null) / 2),
                    (int) ((startPosition.y + t * (endPosition.y - startPosition.y)) - icon.getHeight(null) / 2),
                    null);
        } else {
            g.setStroke(MARKER_STROKE);
            g.setColor(MARKER_COLOR);
            g.drawOval(
                    (int) ((startPosition.x + t * (endPosition.x - startPosition.x)) - S1 / 2),
                    (int) ((startPosition.y + t * (endPosition.y - startPosition.y)) - S1 / 2),
                    S1, S1);
        }
    }

    public void setStartVertex(GVertex<V> startVertex) {
        this.startVertex = startVertex;
        this.startPosition = startVertex.getPosition();
    }

    public GVertex<V> getEndVertex() {
        return endVertex;
    }

    public void setEndVertex(GVertex<V> endVertex) {
        this.endVertex = endVertex;
        this.endPosition = endVertex.getPosition();
    }

    public void recalculateVelocity() {
        Point2D.Float p0 = startVertex.getPosition();
        Point2D.Float p1 = endVertex.getPosition();

        float dx = p1.x - p0.x;
        float dy = p1.y - p0.y;
        double dm = Math.sqrt(dx * dx + dy * dy);
        double dr = MIN_DR + Math.random() * (MAX_DR - MIN_DR);
        dt = dr / dm;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    private Image icon = null;

    private static final Stroke MARKER_STROKE = new BasicStroke(8f);
    private static final Color MARKER_COLOR = new Color(96, 96, 220);
    private static final int S1 = 56;
    private static final double MIN_DR = 4.0;
    private static final double MAX_DR = 5.5;

    private GVertex<V> startVertex;
    private GVertex<V> endVertex;
    private Point2D.Float startPosition;
    private Point2D.Float endPosition;
    private double dt;
    private double t;
    private boolean moving;
}

