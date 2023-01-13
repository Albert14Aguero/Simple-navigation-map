package map.graph;

import util.LinkedList;

public class Dijkstra<V, E> {
    public Dijkstra(){
        this.calculatedTime = 0;
    }
    public LinkedList calculate(Graph<V,E> graph, GVertex vInicial, GVertex vFinal) {
        LinkedList<GVertex<V>> Dvertex = new LinkedList<>();
        LinkedList<GVertex<V>> outList = new LinkedList<>();
        Edge<V, E> edgeAux = null;
        GVertex<V> vertexAux = vInicial;
        Dvertex.add(vInicial);
        boolean noexiste = true;
        boolean head = false;
        while (true) {
            for (Edge<V, E> edge : graph.getEdges()) {
                if ((int) edge.getTail().getInfo() == (int) vertexAux.getInfo()) {
                    if ((int) edge.getHead().getInfo() == (int) vFinal.getInfo()) {
                        Dvertex.add(edge.getHead());
                        return Dvertex;
                    }
                    for (GVertex vertex : Dvertex) {
                        if ((int) vertex.getInfo() == (int) edge.getHead().getInfo()) {
                            noexiste = false;
                            break;
                        }
                    }
                    for (GVertex vertex : outList) {
                        if ((int) vertex.getInfo() == (int) edge.getHead().getInfo()) {
                            noexiste = false;
                            break;
                        }
                    }
                    if (noexiste) {
                        if (edgeAux == null) {
                            edgeAux = edge;
                        }
                        if ((double) edge.getInfo() <= (double) edgeAux.getInfo()) {
                            edgeAux = edge;
                            head = true;
                        }

                    }


                } else if ((int) edge.getHead().getInfo() == (int) vertexAux.getInfo()) {
                    if ((int) edge.getTail().getInfo() == (int) vFinal.getInfo()) {
                        Dvertex.add(edge.getTail());
                        return Dvertex;
                    }
                    for (GVertex vertex : Dvertex) {
                        if ((int) vertex.getInfo() == (int) edge.getTail().getInfo()) {
                            noexiste = false;
                            break;
                        }
                    }
                    for (GVertex vertex : outList) {
                        if ((int) vertex.getInfo() == (int) edge.getTail().getInfo()) {
                            noexiste = false;
                            break;
                        }
                    }
                    if (noexiste) {
                        if (edgeAux == null) {
                            edgeAux = edge;
                        }
                        if ((double) edge.getInfo() <= (double) edgeAux.getInfo()) {
                            edgeAux = edge;
                            head = false;
                        }

                    }
                }

                noexiste = true;
            }
            if (edgeAux != null){
                if (head) {
                    calculatedTime += (double)edgeAux.getInfo();
                    Dvertex.add(edgeAux.getHead());
                    vertexAux = edgeAux.getHead();
                } else {
                    calculatedTime += (double)edgeAux.getInfo();
                    Dvertex.add(edgeAux.getTail());
                    vertexAux = edgeAux.getTail();
                }
                edgeAux = null;
            }
            else{
                outList.add(vertexAux);
                Dvertex.remove(Dvertex.size()-1);
            }


        }

    }
    public double getCalculatedTime() {
        return calculatedTime;
    }

    public void setCalculatedTime(double calculatedTime) {
        this.calculatedTime = calculatedTime;
    }
    private double calculatedTime;


}
