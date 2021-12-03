package core.abstractions;

public abstract class AbstractGraph {
    private final String representation;
    private final AbstractEdge[] edges;
    private final AbstractVertice[] vertices;

    protected abstract void validateGraphRepresentation();
    protected abstract AbstractEdge[] getEdgesFromRepresentation();
    protected abstract AbstractVertice[] getVerticesFromEdgesArray();

    public AbstractGraph(String graphRepresentation) {
        representation = graphRepresentation.trim();
        validateGraphRepresentation();
        edges = getEdgesFromRepresentation();
        vertices = getVerticesFromEdgesArray();
    }

    public String getRepresentation() {
        return representation;
    }

    public AbstractVertice[] getVertices() {
        return vertices;
    }

    public AbstractEdge[] getEdges() {
        return edges;
    }

    public int getNumberOfVertices() {
        return vertices.length;
    }

    public int getNumberOfEdges() {
        return edges.length;
    }

    public void showVertices() {
        System.out.print("\nVertices: { ");

        for (int i = 0; i < vertices.length - 1; i++) {
            System.out.print(vertices[i].getRepresentation() + ", ");
        }

        System.out.println(vertices[vertices.length - 1].getRepresentation() + " }");
    }

    public void showEdges() {
        System.out.print("\nEdges: { ");

        for (int i = 0; i < edges.length - 1; i++) {
            System.out.print(edges[i].getRepresentation() + ", ");
        }

        System.out.println(edges[edges.length - 1].getRepresentation() + " }");
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

    public AbstractEdge getEdge(AbstractVertice firstVertice, AbstractVertice secondVertice) {
        for (AbstractEdge edge : edges) {
            if (edge.getFirstVertice().equals(firstVertice) && edge.getSecondVertice().equals(secondVertice)) {
                return edge;
            }
        }

        return null;
    }

    public int indexOfEdge(AbstractEdge edge) {
        for (int i = 0; i < edges.length; i++) {
            if (edges[i].equals(edge))
                return i;
        }

        return -1;
    }

    public int indexOfVertice(AbstractVertice vertice) {
        for (int i = 0; i < vertices.length; i++) {
            if (vertices[i].equals(vertice))
                return i;
        }

        return -1;
    }
}
