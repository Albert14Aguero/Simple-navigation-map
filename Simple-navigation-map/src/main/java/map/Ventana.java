package map;

import map.graph.GVertex;
import map.graph.Graph;
import map.graph.view.GraphPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Ventana extends JFrame {
    public Ventana(String titulo, Graph<Integer, Double> g)
            throws HeadlessException {
        super(titulo);
        this.g = g;
        this.elegido = false;
        this.cant = "0";

        configurar();
    }

    private void configurar() {
        ajustarComponentes(getContentPane());
        setResizable(false);
        setSize(800, 600);
        // setMinimumSize(new Dimension(640, 480));
        setLocationRelativeTo(null);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                g.setActive(false);
                System.exit(0);
            }

        });
    }

    private void ajustarComponentes(Container c) {

        JPanel buttomPanel = new JPanel();
        JLabel tiempo = new JLabel();

        buttomPanel.setLayout(null);
        c.setLayout(new BorderLayout());
        JPanel panelControl = new JPanel();
        panelControl.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelControl.setBorder(BorderFactory.createEmptyBorder(4, 4, 25, 4));
        panelControl.add(tiempo);
        c.add(BorderLayout.PAGE_START, panelControl);
        c.add(BorderLayout.CENTER, panelPrincipal = new GraphPanel(g));
        JButton vertexButton;
        buttomPanel.setMinimumSize(new Dimension((int)panelPrincipal.getSize().getWidth(), 500));
        int i = 0;
        for(GVertex vert: g.getVertices()){

            vertexButton = new JButton();
            vertexButton.putClientProperty("Numero", i);
            vertexButton.setSize(100,100);
            vertexButton.setText(String.valueOf(i));
            vertexButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (elegido){
                        finalV = (int)((JButton)e.getSource()).getClientProperty("Numero");
                        cambiarActivo(inicioV,finalV);
                        elegido = false;
                        cant =String.format ("%.2f", g.getDijkstra().getCalculatedTime()*10);
                        g.getDijkstra().setCalculatedTime(0.0);
                        tiempo.setText("Tiempo total: " + cant + " min");
                        tiempo.updateUI();
                    }
                    else{
                        inicioV = (int)((JButton)e.getSource()).getClientProperty("Numero");
                        elegido = true;
                    }
                }
            });
            tiempo.setText("Tiempo total: " + cant + " min");
            panelControl.add(vertexButton);
            i++;
        }
    }

    public void init() {
        setVisible(true);
        panelPrincipal.init();
        g.init();
    }

    public void cambiarActivo(int inicioVec, int finalVec) {
        panelPrincipal.setActive(inicioVec, finalVec);
    }

    private GraphPanel panelPrincipal;
    private Boolean elegido;
    private int inicioV;
    private int finalV;
    private final Graph<Integer, Double> g;
    private String cant;
}
