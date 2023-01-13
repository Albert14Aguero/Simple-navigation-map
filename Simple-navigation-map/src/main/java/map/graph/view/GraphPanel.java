package map.graph.view;


import map.graph.Graph;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class GraphPanel <V, E> extends JPanel {
    public GraphPanel(Graph<V, E> g) {
        this.g = g;
        setup();
    }

    private void setup() {
        setBackground(new Color(80, 200, 45));
    }

    public void init() {
        runner = new Thread() {
            @Override
            public void run() {
                while (runner == Thread.currentThread()) {
                    repaint();
                    try {
                        TimeUnit.MILLISECONDS.sleep(MAX_WAIT);
                    } catch (InterruptedException ex) {
                    }
                }
            }

        };
        runner.start();
    }

    @Override
    public void paintComponent(Graphics bg) {
        super.paintComponent(bg);

        g.paint(bg);
    }

    public void setActive(int inicialV, int finalV) {
        g.setActive(inicialV, finalV);
    }

    private static final int MAX_WAIT = 50;
    private Thread runner;
    private final Graph<V, E> g;
}

