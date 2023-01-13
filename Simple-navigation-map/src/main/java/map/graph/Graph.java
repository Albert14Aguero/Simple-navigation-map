package map.graph;


import util.Collection;
import util.LinkedList;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.Iterator;
import java.util.Random;

public class Graph <V, E>{

    public Graph() {
        this.dijkstra = new Dijkstra<>();
        this.vertices = new LinkedList<>();
        this.edges = new LinkedList<>();
        this.active = false;
        this.m = null;
        try {
            this.image1 = ImageIO.read(getClass().getResourceAsStream("../../images/casa1.png"));
            this.image2 = ImageIO.read(getClass().getResourceAsStream("../../images/casa2.png"));
            this.image3 = ImageIO.read(getClass().getResourceAsStream("../../images/casa3.png"));
            this.image4 = ImageIO.read(getClass().getResourceAsStream("../../images/Semaforo.png"));

            this.image1 =this.image1.getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING);
            this.image2 =this.image2.getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING);
            this.image3 =this.image3.getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING);
            this.image4 =this.image4.getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING);
        } catch (IOException ex) {
        }


    }

    public static Graph load() {
        Graph<Integer, Double> r = new Graph<>();
        for (int i = 0; i < 24; i++) {
            int e = i+1;
            float x = 90 * ((i % 6)+1) + 22 * (i%6);
            float y = 90 * ((i / 6)+1) + 22 * (i / 6);
            r.add(e, new Point2D.Float(x, y));
        }
        Random random = new Random();

        for (GVertex v: r.vertices){
            if ((int)v.getInfo() != 24) {
                if ((int) v.getInfo() > 18) {
                    int num2 = 1 + (int) (Math.random() * (2 - 1));
                    if (num2 == 1) {
                        r.add((int) v.getInfo(), (int) (v.getInfo()) + 1, 0.1 + (1 - 0.1) * random.nextDouble());
                    }
                } else if (((int)v.getInfo()) % 6 == 0) {
                    int num3 = 1 + (int) (Math.random() * (2 - 1));
                    if (num3 == 1) {
                        r.add((int) v.getInfo(), (int) (v.getInfo()) + 6, 0.1 + (1 - 0.1) * random.nextDouble());
                    }
                } else {
                    int num = 1 + (int) (Math.random() * (4 - 1));
                    switch (num) {
                        case 1:
                            r.add((int) v.getInfo(), (int) (v.getInfo()) + 1, 0.1 + (1 - 0.1) * random.nextDouble());
                            break;
                        case 2:
                            r.add((int) v.getInfo(), (int) (v.getInfo()) + 6, 0.1 + (1 - 0.1) * random.nextDouble());
                            break;
                        case 3:
                        case 4:
                            r.add((int) v.getInfo(), (int) (v.getInfo()) + 1, 0.1 + (1 - 0.1) * random.nextDouble());
                            r.add((int) v.getInfo(), (int) (v.getInfo()) + 6, 0.1 + (1 - 0.1) * random.nextDouble());
                            break;
                        default:
                            break;
                    }
                }
            }

        }


        return r;
    }

    public GVertex<V> getVertex(V v) {
        GVertex<V> r = null;
        for (GVertex<V> t : getVertices()) {
            if (t.getInfo().equals(v)) {
                r = t;
                break;
            }
        }
        return r;
    }

    public Collection<GVertex<V>> getAdjacent(GVertex<V> v) {
        Collection<GVertex<V>> r = new LinkedList<>();
        for (Edge<V, E> e : getEdges()) {
            if (e.getHead().getInfo().equals(v.getInfo())) {
                r.add(-1, e.getTail());
            }
            if (e.getTail().getInfo().equals(v.getInfo())) {
                r.add(-1, e.getHead());
            }
        }
        return r;
    }

    public void add(V v, Point2D.Float position) {
        getVertices().add(-1, new GVertex<>(v, position));
    }

    public void add(GVertex<V> tail, GVertex<V> head, E w) {
        if ((tail == null) || (head == null)) {
            throw new NullPointerException("No existe el vértice.");
        }
        getEdges().add(-1, new Edge<>(tail, head, w));
    }

    public void add(V t, V h, E w) {
        add(getVertex(t), getVertex(h), w);
    }

    public void init() {
        runner = new Thread(() -> {
            run();
        });
        runner.start();
    }

    public void run() {

        GVertex v = getVertices().get(0);

        m= new Marker(v, v);

        while (runner == Thread.currentThread()) {
            if (isActive()) {
                updateMarker(m);
            } else {
            }
            try {
                Thread.sleep(MAX_WAIT);
            } catch (InterruptedException ex) {
            }
        }
    }

    private void updateMarker(Marker m) {
        if (m.isMoving()) {
            m.move();
        } else {
            synchronized (Graph.this) {
                GVertex<V> v0 = m.getEndVertex();
                if (dVertices.isEmpty()){
                    active = false;
                    dVertices = null;
                }
                else{
                    GVertex<V> v1 = dVertices.get(0);
                    dVertices.remove(0);
                    m.setStartVertex(v0);
                    m.setEndVertex(v1);
                    m.recalculateVelocity();
                    m.start();
                }

            }
        }
    }

    @Override
    public String toString() {
        return String.format("G: (%n   V: %s,%n   E: %s%n)",
                getVertices(), getEdges());
    }

    public Rectangle getBounds() {
        float x0, x1, y0, y1;
        x0 = x1 = y0 = y1 = 0f;
        boolean f = false;

        Iterator<GVertex<V>> i = getVertices().iterator();
        while (i.hasNext()) {
            GVertex<V> v = i.next();

            if (!f) {
                x0 = x1 = v.getPosition().x;
                y0 = y1 = v.getPosition().y;
            }
            f = true;

            x0 = Math.min(x0, v.getPosition().x);
            x1 = Math.max(x1, v.getPosition().x);
            y0 = Math.min(y0, v.getPosition().y);
            y1 = Math.max(y1, v.getPosition().y);
        }

        if (!f) {
            throw new IllegalArgumentException();
        }

        Rectangle r = new Rectangle(
                (int) (x0), (int) (y0),
                (int) (x1 - x0), (int) (y1 - y0)
        );
        r.grow(S0 / 2, S0 / 2);
        return r;
    }

    public void paint(Graphics bg) {
        Graphics2D g = (Graphics2D) bg;

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);


        g.setColor(Color.DARK_GRAY);
        g.setStroke(GUIDE_STROKE);
        Rectangle b = getBounds();
        g.drawRect(b.x, b.y, b.width, b.height);

        g.setFont(BASE_FONT);
        FontMetrics fm = g.getFontMetrics();

        for (Edge<V, E> e : getEdges()) {
            g.setStroke(BASE_STROKE);
            g.setColor(Color.GRAY);

            g.drawLine(
                    (int) e.getTail().getPosition().x,
                    (int) e.getTail().getPosition().y,
                    (int) e.getHead().getPosition().x,
                    (int) e.getHead().getPosition().y
            );
        }

        g.setStroke(VERTEX_STROKE);
        for (GVertex<V> v : getVertices()) {
            g.setColor(Color.WHITE);
            g.fillOval((int) v.getPosition().x - S0 / 2,
                    (int) v.getPosition().y - S0 / 2,
                    S0, S0);
        }

        for (Edge<V, E> e : getEdges()) {
            g.setStroke(GUIDE_STROKE);
            g.setColor(Color.WHITE);

            g.drawLine(
                    (int) e.getTail().getPosition().x,
                    (int) e.getTail().getPosition().y,
                    (int) e.getHead().getPosition().x,
                    (int) e.getHead().getPosition().y
            );
            String num = String.format ("%.2f", e.getInfo());
            g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
            g.setColor(new Color(56, 0,87));
            g.drawString(String.valueOf(num),
                    ((((int) e.getTail().getPosition().x)+(int) e.getHead().getPosition().x)/2)-10,
                    (((int) e.getTail().getPosition().y)+(int) e.getHead().getPosition().y)/2
            );
        }
        for (GVertex<V> v : getVertices()) {
            int num = (int)v.getInfo() % 5;
            switch (num){
                case 1:
                    g.drawImage(image1,
                            (int)v.getPosition().x - 25,
                            (int)v.getPosition().y - 25,
                            null);
                    break;
                case 2:
                    g.drawImage(image2,
                            (int)v.getPosition().x - 25,
                            (int)v.getPosition().y - 25,
                            null);
                    break;
                case 3:
                    g.drawImage(image3,
                            (int)v.getPosition().x - 25,
                            (int)v.getPosition().y - 25,
                            null);
                    break;
                case 4:
                    g.drawImage(image4,
                            (int)v.getPosition().x - 25,
                            (int)v.getPosition().y - 25,
                            null);
                    break;
            }
        }
        if (m != null) {
            try {

                m.paint(g);
            } catch (Exception ex) {
                System.err.printf("Excepción: '%s'%n", ex.getMessage());
            }
        }
    }


    public String getAdjacencyInfo() {
        StringBuilder r = new StringBuilder();
        for (GVertex<V> v : getVertices()) {
            r.append(String.format("%s: ", v.getInfo()));
            Iterator<GVertex<V>> j = getAdjacent(v).iterator();
            while (j.hasNext()) {
                r.append(String.format("%s, ", j.next().getInfo()));
            }
            r.append("\n");
        }
        return r.toString();
    }
    public Collection<GVertex<V>> getVertices() {
        return vertices;
    }

    public Collection<Edge<V, E>> getEdges() {
        return edges;
    }
    public boolean isActive() {
        return active;
    }

    public void setActive(int iniciaV, int finalV) {
        dVertices = dijkstra.calculate(this, vertices.get(iniciaV), vertices.get(finalV));
        this.active = true;

    }
    public void setActive(boolean active) {
        dVertices = dijkstra.calculate(this, vertices.get(0), vertices.get(21));
        this.active = active;
    }

    public Dijkstra<V, E> getDijkstra() {
        return dijkstra;
    }

    private static final float[] DASHES = {4f, 4f};
    private static final Stroke BASE_STROKE
            = new BasicStroke(32f,
            BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0f, null, 0f);
    private static final Stroke VERTEX_STROKE = new BasicStroke(2f);

    private static final Stroke GUIDE_STROKE
            = new BasicStroke(1.0f,
            BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
            0f, DASHES, 0f);
    private static final Font BASE_FONT
            = new Font(Font.SANS_SERIF, Font.PLAIN, 24);
    private static final Font INFO_FONT
            = new Font(Font.SANS_SERIF, Font.PLAIN, 16);




    private Dijkstra<V,E> dijkstra;
    private static final int S0 = 12;

    private static final int DX = 72;
    private static final int DY = 64;
    private static final int MX = 6;
    private int px = 0;
    private Point2D.Float df = new Point2D.Float(0, 0);

    private final Collection<GVertex<V>> vertices;
    private final Collection<Edge<V, E>> edges;

    private static final int MAX_WAIT = 25;
    private boolean active = false;
    private Thread runner;
    private Marker m;
    private Collection<GVertex<V>> dVertices;
    private Image image1;
    private Image image2;
    private Image image3;
    private Image image4;
}

