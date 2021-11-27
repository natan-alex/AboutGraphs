import java.util.regex.*;
import java.util.ArrayList;
import java.util.List;

public class Graph {
    public final String stringRepresentation;
    public final GraphTypes type;
    public List<Edge> edges;
    public List<Vertice> vertices;
    public int numberOfVertices;
    public int numberOfEdges;

    public Graph(String graphRepresentation) throws IllegalArgumentException {
        GraphTypes graphType = GraphValidator.whichTypeOfGraphIs(graphRepresentation);

        type = graphType;
        stringRepresentation = graphRepresentation;
        edges = new ArrayList<>();
        vertices = new ArrayList<>();

        fillEdgeList();
        fillVerticeSet();

        numberOfEdges = edges.size();
        numberOfVertices = vertices.size();
    }

    private void fillEdgeList() {
        Matcher matcher = EdgeValidator.PATTERN_TO_VALIDATE_AN_EDGE.matcher(stringRepresentation);

        while (matcher.find()) {
            edges.add(new Edge(matcher.group()));
        }
    }

    private void fillVerticeSet() {
        for (Edge edge : edges) {
            if (!vertices.contains(edge.firstVertice)) {
                vertices.add(edge.firstVertice);
            }
            if (!vertices.contains(edge.secondVertice)) {
                vertices.add(edge.secondVertice);
            }
        }
    }

    public void showVertices() {
        System.out.print("\nVertices: { ");
        Vertice[] verticesArray = new Vertice[numberOfVertices];
        vertices.toArray(verticesArray);

        for (int i = 0; i < verticesArray.length - 1; i++) {
            System.out.print(verticesArray[i].name + ", ");
        }

        System.out.println(verticesArray[verticesArray.length - 1].name + " }");
    }

    public void showEdges() {
        System.out.print("\nEdges: { ");

        Edge[] edgesArray = new Edge[numberOfEdges];
        edges.toArray(edgesArray);

        for (int i = 0; i < edgesArray.length - 1; i++) {
            System.out.print(edgesArray[i].stringRepresentation + ", ");
        }

        System.out.println(edgesArray[edgesArray.length - 1].stringRepresentation + " }");
    }

    public Vertice getVerticeByName(String name) {
        for (Vertice vertice : vertices) {
            if (vertice.name.compareTo(name) == 0) {
                return vertice;
            }
        }

        return null;
    }

    public Edge getEdgeByRepresentation(String representation) {
        for (Edge edge : edges) {
            if (edge.stringRepresentation.compareToIgnoreCase(representation) == 0) {
                return edge;
            }
        }

        return null;
    }

    public Edge getDirectedEdgeWithThisVertices(Vertice firstVertice, Vertice secondVertice) {
        for (Edge edge : edges) {
            if (edge.firstVertice.equals(firstVertice) && edge.secondVertice.equals(secondVertice)) {
                return edge;
            }
        }

        return null;
    }

}