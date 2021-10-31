import java.util.regex.*;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class Graph {
    public final String stringRepresentation;
    public final Map<Edge, Integer> edges;
    public final Map<Vertice, Integer> vertices;
    public final List<Integer> edgeValues;
    public final boolean isPondered;
    public final boolean isDirected;
    public final int numberOfVertices;
    public final int numberOfEdges;

    private static Pattern PATTERN_TO_VALIDATE_A_DIRECTED_UNPONDERED_GRAPH = Pattern.compile(
            "^\\s*\\{\\s*(?:\\{\\s*\\w+\\s*,\\s*\\w+\\s*\\}\\s*,\\s*)*\\{\\s*\\w+\\s*,\\s*\\w+\\s*\\}\\s*\\}\\s*$",
            Pattern.MULTILINE);
    private static Pattern PATTERN_TO_VALIDATE_AN_UNDIRECTED_UNPONDERED_GRAPH = Pattern.compile(
            "^\\s*\\{\\s*(?:\\(\\s*\\w+\\s*,\\s*\\w+\\s*\\)\\s*,\\s*)*\\(\\s*\\w+\\s*,\\s*\\w+\\s*\\)\\s*\\}\\s*$",
            Pattern.MULTILINE);
    private static Pattern PATTERN_TO_VALIDATE_A_DIRECTED_PONDERED_GRAPH = Pattern.compile(
            "^\\s*\\{\\s*(?:\\(\\s*\\w+\\s*,\\s*\\w+\\s*,\\s*(?:\\+|\\-)?\\d+\\s*\\)\\s*,\\s*)*\\(\\s*\\w+\\s*,\\s*\\w+\\s*,\\s*(?:\\+|\\-)?\\d+\\s*\\)\\s*\\}\\s*$",
            Pattern.MULTILINE);
    private static Pattern PATTERN_TO_VALIDATE_AN_UNDIRECTED_PONDERED_GRAPH = Pattern.compile(
            "^\\s*\\{\\s*(?:\\{\\s*\\w+\\s*,\\s*\\w+\\s*,\\s*(?:\\+|\\-)?\\d+\\s*\\}\\s*,\\s*)*\\{\\s*\\w+\\s*,\\s*\\w+\\s*,\\s*(?:\\+|\\-)?\\d+\\s*\\}\\s*\\}\\s*$",
            Pattern.MULTILINE);

    public Graph(String graphRepresentation) throws IllegalArgumentException {
        Matcher matcherForDirectedAndUnponderedPattern = PATTERN_TO_VALIDATE_A_DIRECTED_UNPONDERED_GRAPH
                .matcher(graphRepresentation);
        Matcher matcherForDirectedAndPonderedPattern = PATTERN_TO_VALIDATE_A_DIRECTED_PONDERED_GRAPH
                .matcher(graphRepresentation);
        Matcher matcherForUndirectedAndPonderedPattern = PATTERN_TO_VALIDATE_AN_UNDIRECTED_PONDERED_GRAPH
                .matcher(graphRepresentation);
        Matcher matcherForUndirectedAndUnponderedPattern = PATTERN_TO_VALIDATE_AN_UNDIRECTED_UNPONDERED_GRAPH
                .matcher(graphRepresentation);

        if (matcherForDirectedAndUnponderedPattern.matches()) {
            isDirected = true;
            isPondered = false;
        } else if (matcherForDirectedAndPonderedPattern.matches()) {
            isDirected = true;
            isPondered = true;
        } else if (matcherForUndirectedAndPonderedPattern.matches()) {
            isDirected = false;
            isPondered = true;
        } else if (matcherForUndirectedAndUnponderedPattern.matches()) {
            isDirected = false;
            isPondered = false;
        } else {
            throw new IllegalArgumentException("Invalid graph " + graphRepresentation
                    + "\nA valid graph must be enclosed with {} and contains multiple edges inside curly braces."
                    + "\nAn edge must be enclosed with () if it is part of a directed graph or {} if it is part of an undirected graph."
                    + "\nExample of valid graph: { (a, b), (b, c) } or { {hello, world}, {foo, bar} }");
        }

        stringRepresentation = graphRepresentation;
        edges = new LinkedHashMap<>();
        edgeValues = new ArrayList<>();
        vertices = new LinkedHashMap<>();
        numberOfEdges = fillEdgeListAndReturnTheNumberOfEdges();
        numberOfVertices = fillVerticeSetAndReturnTheNumberOfVertices();
    }

    private int fillEdgeListAndReturnTheNumberOfEdges() {
        int edgeIndex = 0;
        Edge newEdge;
        Matcher matcher = Edge.PATTERN_TO_VALIDATE_AN_EDGE.matcher(stringRepresentation.trim());

        while (matcher.find()) {
            newEdge = new Edge(matcher.group());

            edges.put(newEdge, edgeIndex);
            edgeIndex++;

            if (newEdge.isPondered)
                edgeValues.add(newEdge.value);
        }

        return edges.size();
    }

    private int fillVerticeSetAndReturnTheNumberOfVertices() {
        int verticeIndex = 0;
        Set<Vertice> verticeSet = new HashSet<>();

        for (Map.Entry<Edge, Integer> entry : edges.entrySet()) {
            verticeSet.add(entry.getKey().firstVertice);
            verticeSet.add(entry.getKey().secondVertice);
        }

        for (Vertice vertice : verticeSet) {
            vertices.put(vertice, verticeIndex);
            verticeIndex++;
        }

        return vertices.size();
    }

    public void showVertices() {
        System.out.print("\nVertices: { ");
        Vertice[] verticesArray = new Vertice[numberOfVertices];
        vertices.keySet().toArray(verticesArray);

        for (int i = 0; i < verticesArray.length - 1; i++) {
            System.out.print(verticesArray[i].name + ", ");
        }

        System.out.println(verticesArray[verticesArray.length - 1].name + " }");
    }

    public void showEdges() {
        System.out.print("\nEdges: { ");

        Edge[] edgesArray = new Edge[numberOfEdges];
        edges.keySet().toArray(edgesArray);

        for (int i = 0; i < edgesArray.length - 1; i++) {
            System.out.print(edgesArray[i].stringRepresentation + ", ");
        }

        System.out.println(edgesArray[edgesArray.length - 1].stringRepresentation + " }");
    }

    // public boolean containsCycle() {
    // for (EdgeClassifications classification :
    // deepSearchStructures.edgeClassifications) {
    // if (classification == EdgeClassifications.RETURN) {
    // return true;
    // }
    // }

    // return false;
    // }

    // public int getNumberOfCycles() {
    // int numberOfCycles = 0;

    // for (EdgeClassifications classification :
    // deepSearchStructures.edgeClassifications) {
    // if (classification == EdgeClassifications.RETURN) {
    // numberOfCycles++;
    // }
    // }

    // return numberOfCycles;
    // }
}