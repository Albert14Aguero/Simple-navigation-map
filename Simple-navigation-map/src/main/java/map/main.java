package map;

import map.graph.Graph;

import javax.swing.*;
import java.io.FileNotFoundException;

public class main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            JFrame.setDefaultLookAndFeelDecorated(true);
        } catch (ClassNotFoundException
                 | IllegalAccessException
                 | InstantiationException | UnsupportedLookAndFeelException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
        }

        try {
            new main().init();
        } catch (FileNotFoundException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
        }
    }

    public void init() throws FileNotFoundException {
        Graph g0 = Graph.load();
        System.out.printf("%s%n%n", g0);
        System.out.println();
        System.out.println(g0.getAdjacencyInfo());
        System.out.println();

        SwingUtilities.invokeLater(() -> {
            new Ventana("Proyecto Estructuras de datos", g0).init();
        });
    }
}
