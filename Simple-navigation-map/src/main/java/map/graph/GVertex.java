package map.graph;

import java.awt.geom.Point2D;

public class GVertex<V> {
    public GVertex(V info, Point2D.Float position) {
        this.info = info;
        this.position = position;
    }
    public V getInfo() {
        return info;
    }
    public Point2D.Float getPosition() {
        return position;
    }


    @Override
    public String toString() {
        return String.format("{%s, (%4.2f, %4.2f)}",
                getInfo(), getPosition().getX(), getPosition().getY());
    }



    private final V info;
    private Point2D.Float position;
}

