import java.util.regex.*;
import java.util.ArrayList;
import java.util.List;

public class Graph {
    public final String stringRepresentation;
    public final boolean isPondered;
    public final boolean isDirected;
    public List<Edge> edges;
    public List<Vertice> vertices;
    public int numberOfVertices;
    public int numberOfEdges;

    public static final String EXAMPLES_OF_VALID_GRAPHS_MESSAGE = "Examples of valid graphs:"
            + "\n  { (a, b) } for a directed and unpondered graph"
            + "\n  { {hello, world} } for a undirected and unpondered graph"
            + "\n  { (foo, bar, 10) } for a directed and pondered graph"
            + "\n  { {hey, man, 1} } for a undirected and pondered graph";

    public static final String INFORMATIVE_EXCEPTION_MESSAGE = "A valid graph must be enclosed with {} and contains multiple edges inside curly braces."
            + "\nAn edge must be enclosed with () if it is part of a directed graph or {} if it is part of an undirected graph."
            + "\n" + EXAMPLES_OF_VALID_GRAPHS_MESSAGE;

    public Graph(String graphRepresentation) throws IllegalArgumentException {
        GraphRepresentationTypes graphType = GraphValidator.whichTypeOfGraphRepresentationIs(graphRepresentation);

        throwExceptionIfGraphIsInvalid(graphType);

        isPondered = graphType.isGraphPondered();
        isDirected = graphType.isGraphDirected();

        stringRepresentation = graphRepresentation;
        edges = new ArrayList<>();
        vertices = new ArrayList<>();

        fillEdgeList();
        fillVerticeSet();

        numberOfEdges = edges.size();
        numberOfVertices = vertices.size();
    }

    private void throwExceptionIfGraphIsInvalid(GraphRepresentationTypes graphType) throws IllegalArgumentException {
        if (graphType == null) {
            throw new IllegalArgumentException(INFORMATIVE_EXCEPTION_MESSAGE);
        }
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