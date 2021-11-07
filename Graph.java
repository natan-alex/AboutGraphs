import java.util.regex.*;
import java.util.Map;
import java.util.LinkedHashMap;

public class Graph {
    public final String stringRepresentation;
    public final Map<Edge, Integer> edgesAndTheirIndices;
    public final Map<Vertice, Integer> verticesAndTheirIndices;
    public final Map<Edge, Float> edgesAndTheirValues;
    public final boolean isPondered;
    public final boolean isDirected;
    public final int numberOfVertices;
    public final int numberOfEdges;

    public Graph(String graphRepresentation) throws IllegalArgumentException {
        GraphValidator.validateStringRepresentationAndFillGraphProperties(graphRepresentation);

        stringRepresentation = graphRepresentation;
        edgesAndTheirIndices = new LinkedHashMap<>();
        edgesAndTheirValues = new LinkedHashMap<>();
        verticesAndTheirIndices = new LinkedHashMap<>();
        numberOfEdges = fillEdgeMapsAndReturnTheNumberOfEdges();
        numberOfVertices = fillVerticeMapAndReturnTheNumberOfVertices();
        isDirected = GraphValidator.isGraphDirected();
        isPondered = GraphValidator.isGraphPondered();
    }

    private int fillEdgeMapsAndReturnTheNumberOfEdges() {
        int edgeIndex = 0;
        Matcher matcher = EdgeValidator.PATTERN_TO_VALIDATE_AN_EDGE.matcher(stringRepresentation);
        Edge newEdge;

        while (matcher.find()) {
            newEdge = new Edge(matcher.group());

            edgesAndTheirIndices.put(newEdge, edgeIndex);
            edgeIndex++;

            if (newEdge.isPondered)
                edgesAndTheirValues.put(newEdge, newEdge.value);
            else
                edgesAndTheirValues.put(newEdge, 0F);
        }

        return edgesAndTheirIndices.size();
    }

    private int fillVerticeMapAndReturnTheNumberOfVertices() {
        int verticeIndex = 0;
        Edge currentEdge;

        for (Map.Entry<Edge, Integer> entry : edgesAndTheirIndices.entrySet()) {
            currentEdge = entry.getKey();

            if (verticesAndTheirIndices.putIfAbsent(currentEdge.firstVertice, verticeIndex) == null)
                verticeIndex++;

            if (verticesAndTheirIndices.putIfAbsent(currentEdge.secondVertice, verticeIndex) == null)
                verticeIndex++;
        }

        System.out.println(verticesAndTheirIndices);
        return verticesAndTheirIndices.size();
    }

    public void showVertices() {
        System.out.print("\nVertices: { ");
        Vertice[] verticesArray = new Vertice[numberOfVertices];
        verticesAndTheirIndices.keySet().toArray(verticesArray);

        for (int i = 0; i < verticesArray.length - 1; i++) {
            System.out.print(verticesArray[i].name + ", ");
        }

        System.out.println(verticesArray[verticesArray.length - 1].name + " }");
    }

    public void showEdges() {
        System.out.print("\nEdges: { ");

        Edge[] edgesArray = new Edge[numberOfEdges];
        edgesAndTheirIndices.keySet().toArray(edgesArray);

        for (int i = 0; i < edgesArray.length - 1; i++) {
            System.out.print(edgesArray[i].stringRepresentation + ", ");
        }

        System.out.println(edgesArray[edgesArray.length - 1].stringRepresentation + " }");
    }
}