import java.util.regex.*;
import java.util.ArrayList;
import java.util.List;

public class Graph {
    public final String stringRepresentation;
    public final List<Edge> edges;
    public final List<Vertice> vertices;
    public final boolean isPondered;
    public final boolean isDirected;
    public final int numberOfVertices;
    public final int numberOfEdges;

    public Graph(String graphRepresentation) throws IllegalArgumentException {
        GraphValidator.validateStringRepresentationAndFillGraphProperties(graphRepresentation);

        stringRepresentation = graphRepresentation;
        edges = new ArrayList<>();
        vertices = new ArrayList<>();
        numberOfEdges = fillEdgeListAndReturnTheNumberOfEdges();
        numberOfVertices = fillVerticeListAndReturnTheNumberOfVertices();

        isDirected = GraphValidator.isGraphDirected();
        isPondered = GraphValidator.isGraphPondered();
    }

    private int fillEdgeListAndReturnTheNumberOfEdges() {
        Matcher matcher = EdgeValidator.PATTERN_TO_VALIDATE_AN_EDGE.matcher(stringRepresentation);

        while (matcher.find()) {
            edges.add(new Edge(matcher.group()));
        }

        return edges.size();
    }

    private int fillVerticeListAndReturnTheNumberOfVertices() {
        for (Edge edge : edges) {
            if (!vertices.contains(edge.firstVertice)) {
                vertices.add(edge.firstVertice);
            }
            if (!vertices.contains(edge.secondVertice)) {
                vertices.add(edge.secondVertice);
            }
        }

        return vertices.size();
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

    public Vertice getTheVerticeWithThisName(String name) {
        for (Vertice vertice : vertices) {
            if (vertice.name.compareTo(name) == 0) {
                return vertice;
            }
        }

        return null;
    }
}