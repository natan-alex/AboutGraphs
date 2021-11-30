package core.abstractions;

import core.Edge;
import core.Vertice;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public abstract class AbstractGraph {
    private String representation;
    private List<AbstractEdge> edges;
    private Set<AbstractVertice> vertices;

    protected abstract void validateGraphRepresentation();
    protected abstract void fillEdgeList();

    public AbstractGraph(String graphRepresentation) {
        representation = graphRepresentation;
        validateGraphRepresentation();
        fillEdgeList();
        fillVerticeSet();
    }

    private void fillVerticeSet() {
        vertices = new LinkedHashSet<>();

        for (AbstractEdge edge : edges) {
            vertices.add(edge.getFirstVertice());
            vertices.add(edge.getSecondVertice());
        }
    }

    public String getRepresentation() {
        return representation;
    }

    protected void setRepresentation(String newRepresentation) {
        representation = newRepresentation;
    }

    public Set<AbstractVertice> getVertices() {
        return vertices;
    }

    protected void setVertices(Set<AbstractVertice> vertices) {
        this.vertices = vertices;
    }

    public List<AbstractEdge> getEdges() {
        return edges;
    }

    protected void setEdges(List<AbstractEdge> edges) {
        this.edges = edges;
    }

    public int getNumberOfVertices() {
        return vertices.size();
    }

    public int getNumberOfEdges() {
        return edges.size();
    }


    public void showVertices() {
        System.out.print("\nVertices: { ");
        Vertice[] verticesArray = new Vertice[vertices.size()];
        vertices.toArray(verticesArray);

        for (int i = 0; i < verticesArray.length - 1; i++) {
            System.out.print(verticesArray[i].getRepresentation() + ", ");
        }

        System.out.println(verticesArray[verticesArray.length - 1].getRepresentation() + " }");
    }

    public void showEdges() {
        System.out.print("\nEdges: { ");
        Edge[] edgesArray = new Edge[edges.size()];
        edges.toArray(edgesArray);

        for (int i = 0; i < edgesArray.length - 1; i++) {
            System.out.print(edgesArray[i].getRepresentation() + ", ");
        }

        System.out.println(edgesArray[edgesArray.length - 1].getRepresentation() + " }");
    }

    public AbstractVertice getVerticeByRepresentation(String verticeRepresentation) {
        for (AbstractVertice vertice : vertices) {
            if (vertice.getRepresentation().compareTo(verticeRepresentation) == 0) {
                return vertice;
            }
        }

        return null;
    }

    public AbstractEdge getEdgeByRepresentation(String edgeRepresentation) {
        for (AbstractEdge edge : edges) {
            if (edge.getRepresentation().compareToIgnoreCase(edgeRepresentation) == 0) {
                return edge;
            }
        }

        return null;
    }

    public AbstractEdge getDirectedEdgeWithTheseVertices(AbstractVertice firstVertice, AbstractVertice secondVertice) {
        for (AbstractEdge edge : edges) {
            if (edge.getFirstVertice().equals(firstVertice) && edge.getSecondVertice().equals(secondVertice)) {
                return edge;
            }
        }

        return null;
    }
}
