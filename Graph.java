import java.util.regex.*;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Stack;
import java.util.Optional;

public class Graph {
    private class GraphRepresentations {
        public int[][] adjacencyMatrix;
        public int[][] incidencyMatrix;
        public List<List<String>> successorAdjacencyList;
        public List<List<String>> predecessorAdjacencyList;
        public int[] successorAdjacencyArrayStart;
        public int[] successorAdjacencyArrayEnd;
        public int[] predecessorAdjacencyArrayStart;
        public int[] predecessorAdjacencyArrayEnd;
    }

    private class DeepSearchStructures {
        public int[] discoveryTimes;
        public int[] endTimes;
        public EdgeClassifications[] edgeClassifications;
    }

    private enum EdgeClassifications {
        TREE("Tree"), CROSSING("Crossing"), RETURN("Return"), ADVANCE("Advance");

        String name;

        private EdgeClassifications(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private String stringRepresentation;
    private List<Edge> edges;
    private Set<String> vertices;
    private List<Integer> edgeValues;
    private boolean isPondered;
    private boolean isDirected;
    private GraphRepresentations representations;
    private DeepSearchStructures deepSearchStructures;
    private int numberOfVertices;
    private int numberOfEdges;

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

    public static Optional<Graph> fromString(String s) {
        Optional<Graph> result = Optional.empty();
        Graph newGraph = new Graph();
        boolean isGraphValid = true;
        Matcher matcherForDirectedAndUnponderedPattern = PATTERN_TO_VALIDATE_A_DIRECTED_UNPONDERED_GRAPH.matcher(s);
        Matcher matcherForDirectedAndPonderedPattern = PATTERN_TO_VALIDATE_A_DIRECTED_PONDERED_GRAPH.matcher(s);
        Matcher matcherForUndirectedAndPonderedPattern = PATTERN_TO_VALIDATE_AN_UNDIRECTED_PONDERED_GRAPH.matcher(s);
        Matcher matcherForUndirectedAndUnponderedPattern = PATTERN_TO_VALIDATE_AN_UNDIRECTED_UNPONDERED_GRAPH
                .matcher(s);

        if (matcherForDirectedAndUnponderedPattern.matches()) {
            newGraph.isDirected = true;
            newGraph.isPondered = false;
        } else if (matcherForDirectedAndPonderedPattern.matches()) {
            newGraph.isDirected = true;
            newGraph.isPondered = true;
        } else if (matcherForUndirectedAndPonderedPattern.matches()) {
            newGraph.isDirected = false;
            newGraph.isPondered = true;
        } else if (matcherForUndirectedAndUnponderedPattern.matches()) {
            newGraph.isDirected = false;
            newGraph.isPondered = false;
        } else {
            isGraphValid = false;
        }

        if (isGraphValid) {
            newGraph.stringRepresentation = s;
            newGraph.fillEdgeListAndVerticeSet();
            newGraph.fillRepresentations();
            result = Optional.of(newGraph);
        }

        return result;
    }

    private void fillEdgeListAndVerticeSet() {
        edges = new ArrayList<Edge>();
        edgeValues = new ArrayList<Integer>();
        vertices = new HashSet<String>();
        Optional<Edge> fromStringResult;
        Edge edgeResult;
        Matcher matcher = Edge.PATTERN_TO_VALIDATE_AN_EDGE.matcher(stringRepresentation.trim());

        while (matcher.find()) {
            fromStringResult = Edge.fromString(matcher.group());

            if (fromStringResult.isPresent()) {
                edgeResult = fromStringResult.get();
                edges.add(edgeResult);

                if (edgeResult.isPondered())
                    edgeValues.add(edgeResult.getValue());

                vertices.add(edgeResult.getFirstVertice());
                vertices.add(edgeResult.getSecondVertice());
            }
        }

        numberOfEdges = edges.size();
        numberOfVertices = vertices.size();
    }

    private void fillRepresentations() {
        representations = new GraphRepresentations();
        fillSuccessorAdjacencyList();
        fillPredecessorAdjacencyList();
        fillAdjacencyMatrix();
        fillIncidencyMatrix();
        fillAdjacencyArrays();
        reorderSuccessorAdjacencyArrays();
        reorderPredecessorAdjacencyArrays();
    }

    private void fillSuccessorAdjacencyList() {
        representations.successorAdjacencyList = new ArrayList<>();
        List<String> itemsList;

        for (String vertice : vertices) {
            itemsList = new ArrayList<>();
            itemsList.add(vertice);

            for (Edge edge : edges) {
                if (vertice.compareTo(edge.getFirstVertice()) == 0)
                    itemsList.add(edge.getSecondVertice());
            }

            representations.successorAdjacencyList.add(itemsList);
        }
    }

    private void fillPredecessorAdjacencyList() {
        representations.predecessorAdjacencyList = new ArrayList<>();
        List<String> itemsList;

        for (String vertice : vertices) {
            itemsList = new ArrayList<>();
            itemsList.add(vertice);

            for (Edge edge : edges) {
                if (vertice.compareTo(edge.getSecondVertice()) == 0) {
                    itemsList.add(edge.getFirstVertice());
                }
            }

            representations.predecessorAdjacencyList.add(itemsList);
        }
    }

    public void showPredecessorAdjacencyList() {
        showAdjacencyList(representations.predecessorAdjacencyList);
    }

    public void showSuccessorAdjacencyList() {
        showAdjacencyList(representations.successorAdjacencyList);
    }

    private void showAdjacencyList(List<List<String>> adjacencyList) {
        Iterator<String> listIterator;

        for (List<String> list : adjacencyList) {
            listIterator = list.iterator();
            System.out.print(listIterator.next() + " -> ");

            while (listIterator.hasNext()) {
                System.out.print(listIterator.next() + " ; ");
            }

            System.out.println();
        }
    }

    private void fillAdjacencyMatrix() {
        representations.adjacencyMatrix = new int[numberOfVertices][numberOfVertices];
        int indexOfSecondVertice;
        int currentLine = 0;

        for (String vertice : vertices) {
            for (int j = 0; j < numberOfVertices; j++)
                representations.adjacencyMatrix[currentLine][j] = 0;

            for (Edge edge : edges) {
                if (vertice.compareTo(edge.getFirstVertice()) == 0) {
                    indexOfSecondVertice = getTheIndexOfTheVertice(edge.getSecondVertice());
                    representations.adjacencyMatrix[currentLine][indexOfSecondVertice] = 1;
                }
            }

            currentLine++;
        }
    }

    private int getTheIndexOfTheVertice(String vertice) {
        int index = 0;

        for (String v : vertices) {
            if (v.compareTo(vertice) == 0) {
                return index;
            }

            index++;
        }

        return -1;
    }

    private int getTheIndexOfTheEdge(Edge edge) {
        int index = 0;

        for (Edge e : edges) {
            if (e.getStringRepresentation().compareTo(edge.getStringRepresentation()) == 0) {
                return index;
            }

            index++;
        }

        return -1;
    }

    public void showAdjacencyMatrix() {
        Iterator<String> verticeIterator = vertices.iterator();

        System.out.print("\t");

        for (String vertice : vertices)
            System.out.print(vertice + "\t");

        System.out.println();

        for (int i = 0; i < numberOfVertices; i++) {
            System.out.print(verticeIterator.next() + "\t");

            for (int j = 0; j < numberOfVertices; j++) {
                System.out.print(representations.adjacencyMatrix[i][j] + "\t");
            }

            System.out.println();
        }
    }

    private void fillIncidencyMatrix() {
        int indexOfSecondVertice;
        int currentColumn = 0, currentLine = 0;

        representations.incidencyMatrix = new int[numberOfVertices][numberOfEdges];

        for (String vertice : vertices) {
            currentColumn = 0;

            for (Edge edge : edges) {
                if (vertice.compareTo(edge.getFirstVertice()) == 0) {
                    indexOfSecondVertice = getTheIndexOfTheVertice(edge.getSecondVertice());
                    representations.incidencyMatrix[currentLine][currentColumn] = 1;
                    representations.incidencyMatrix[indexOfSecondVertice][currentColumn] = -1;
                } else if (representations.incidencyMatrix[currentLine][currentColumn] != -1) {
                    representations.incidencyMatrix[currentLine][currentColumn] = 0;
                }

                currentColumn++;
            }

            currentLine++;
        }
    }

    public void showIncidencyMatrix() {
        int currentLine = 0, currentColumn = 0;
        int currentItem;

        System.out.print("\t");

        for (Edge edge : edges)
            System.out.print(edge.getFirstVertice() + " -- " + edge.getSecondVertice() + "\t");

        System.out.println();

        for (String vertice : vertices) {
            currentColumn = 0;
            System.out.print(vertice + "\t");

            for (Edge edge : edges) {
                currentItem = representations.incidencyMatrix[currentLine][currentColumn];

                if (currentItem == 1)
                    System.out.print("+");
                else if (currentItem == 0)
                    System.out.print(" ");

                System.out.print(currentItem);

                if (edge.isPondered())
                    System.out.print(" | " + edge.getValue());

                System.out.print("\t");
                currentColumn++;
            }

            currentLine++;
            System.out.println();
        }
    }

    private void fillAdjacencyArrays() {
        int currentIndex = 0;
        int indexOfFirstVertice, indexOfSecondVertice;

        representations.predecessorAdjacencyArrayStart = new int[numberOfEdges];
        representations.predecessorAdjacencyArrayEnd = new int[numberOfEdges];
        representations.successorAdjacencyArrayStart = new int[numberOfEdges];
        representations.successorAdjacencyArrayEnd = new int[numberOfEdges];

        for (Edge edge : edges) {
            indexOfFirstVertice = getTheIndexOfTheVertice(edge.getFirstVertice());
            indexOfSecondVertice = getTheIndexOfTheVertice(edge.getSecondVertice());
            representations.predecessorAdjacencyArrayStart[currentIndex] = indexOfFirstVertice;
            representations.successorAdjacencyArrayStart[currentIndex] = indexOfFirstVertice;
            representations.predecessorAdjacencyArrayEnd[currentIndex] = indexOfSecondVertice;
            representations.successorAdjacencyArrayEnd[currentIndex] = indexOfSecondVertice;
            currentIndex++;
        }
    }

    private void reorderSuccessorAdjacencyArrays() {
        orderAdjacencyArrays(representations.successorAdjacencyArrayStart, representations.successorAdjacencyArrayEnd);
        representations.successorAdjacencyArrayStart = reorderAndReturnSuccessorAdjacencyArray();
    }

    private void reorderPredecessorAdjacencyArrays() {
        orderAdjacencyArrays(representations.predecessorAdjacencyArrayEnd,
                representations.predecessorAdjacencyArrayStart);
        representations.predecessorAdjacencyArrayEnd = reorderAndReturnPredecessorAdjacencyArray();
    }

    private void orderAdjacencyArrays(int[] whichToSort, int[] otherArray) {
        int currentItemOfWhichToSortArray, currentItemOfOtherArray;
        int j;
        for (int i = 1; i < whichToSort.length; i++) {
            currentItemOfWhichToSortArray = whichToSort[i];
            currentItemOfOtherArray = otherArray[i];
            j = i - 1;
            while (j >= 0 && whichToSort[j] > currentItemOfWhichToSortArray) {
                whichToSort[j + 1] = whichToSort[j];
                otherArray[j + 1] = otherArray[j];
                j--;
            }
            whichToSort[j + 1] = currentItemOfWhichToSortArray;
            otherArray[j + 1] = currentItemOfOtherArray;
        }
    }

    private int[] reorderAndReturnPredecessorAdjacencyArray() {
        int[] reorderedArray = new int[numberOfVertices + 1];
        int indexOfWhereInsert, indexOfFirstOcurrenceOfAVerticeIndex = 0;

        reorderedArray[numberOfVertices] = numberOfEdges;
        reorderedArray[0] = 0;

        for (int whereInsertInReorderedArray = 1; whereInsertInReorderedArray < numberOfVertices; whereInsertInReorderedArray++) {
            indexOfWhereInsert = firstIndexOfItemInArray(whereInsertInReorderedArray,
                    representations.predecessorAdjacencyArrayEnd);

            if (indexOfWhereInsert != -1) {
                indexOfFirstOcurrenceOfAVerticeIndex = indexOfWhereInsert;
            }

            reorderedArray[whereInsertInReorderedArray] = indexOfFirstOcurrenceOfAVerticeIndex;
        }

        return reorderedArray;
    }

    private int[] reorderAndReturnSuccessorAdjacencyArray() {
        int[] reorderedArray = new int[numberOfVertices + 1];
        int indexOfWhereInsert, indexOfFirstOcurrenceOfAVerticeIndex = numberOfEdges;

        reorderedArray[numberOfVertices] = numberOfEdges;
        reorderedArray[0] = 0;

        for (int whereInsertInReorderedArray = numberOfVertices
                - 1; whereInsertInReorderedArray > 0; whereInsertInReorderedArray--) {
            indexOfWhereInsert = firstIndexOfItemInArray(whereInsertInReorderedArray,
                    representations.successorAdjacencyArrayStart);

            if (indexOfWhereInsert != -1) {
                indexOfFirstOcurrenceOfAVerticeIndex = indexOfWhereInsert;
            }
            reorderedArray[whereInsertInReorderedArray] = indexOfFirstOcurrenceOfAVerticeIndex;
        }

        return reorderedArray;
    }

    private int firstIndexOfItemInArray(int item, int[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == item) {
                return i;
            }
        }

        return -1;
    }

    public void showSuccessorAdjacencyArrays() {
        showAdjacencyArraysIncreasingTheirValuesByOne(representations.successorAdjacencyArrayStart,
                representations.successorAdjacencyArrayEnd);
    }

    public void showPredecessorAdjacencyArrays() {
        showAdjacencyArraysIncreasingTheirValuesByOne(representations.predecessorAdjacencyArrayStart,
                representations.predecessorAdjacencyArrayEnd);
    }

    private void showAdjacencyArraysIncreasingTheirValuesByOne(int firstArray[], int secondArray[]) {
        showVerticesSet();

        System.out.print("Start array indices: [ ");

        for (int index : firstArray)
            System.out.print((index + 1) + " ");

        System.out.println("]");

        System.out.print("End array indices: [ ");

        for (int index : secondArray)
            System.out.print((index + 1) + " ");

        System.out.println("]");
    }

    public void showVerticesSet() {
        System.out.print("Vertice set: { ");
        String[] verticesArray = new String[numberOfVertices];
        vertices.toArray(verticesArray);

        for (int i = 0; i < verticesArray.length - 1; i++)
            System.out.print(verticesArray[i] + ", ");

        System.out.println(verticesArray[verticesArray.length - 1] + " }");
    }

    public void showAllRepresentations() {
        System.out.println("\n  REPRESENTATIONS FOR GRAPH: " + stringRepresentation);
        System.out.println("\n\tADJACENCY MATRIX\n");
        showAdjacencyMatrix();

        System.out.println("\n\tINCIDENCY MATRIX\n");
        showIncidencyMatrix();

        System.out.println("\n\tPREDECESSOR ADJACENCY LIST\n");
        showPredecessorAdjacencyList();

        System.out.println("\n\tSUCCESSOR ADJACENCY LIST\n");
        showSuccessorAdjacencyList();

        System.out.println("\n\tPREDECESSOR ADJACENCY ARRAYS\n");
        showPredecessorAdjacencyArrays();

        System.out.println("\n\tSUCCESSOR ADJACENCY ARRAYS\n");
        showSuccessorAdjacencyArrays();
        System.out.println();
    }

    private void makeDeepSearchAndComputeTimesInArrays() {
        initializeDeepSearchStructures();
        String currentVertice = representations.successorAdjacencyList.get(0).get(0);
        int verticeIndexInVerticeSet = getTheIndexOfTheVertice(currentVertice);
        Stack<Iterator<String>> discoveredVertices = new Stack<>();
        Stack<String> listHeads = new Stack<>();
        Edge edgeToBeClassified = null;

        for (int timeNumber = 1; timeNumber <= 2 * numberOfVertices; timeNumber++) {
            if (deepSearchStructures.discoveryTimes[verticeIndexInVerticeSet] == -1) {
                deepSearchStructures.discoveryTimes[verticeIndexInVerticeSet] = timeNumber;

                discoveredVertices.push(getAdjacencyListWithThisHead(currentVertice).iterator());
                listHeads.push(discoveredVertices.lastElement().next());
            } else if (!discoveredVertices.lastElement().hasNext()) {
                verticeIndexInVerticeSet = getTheIndexOfTheVertice(listHeads.lastElement());

                if (deepSearchStructures.endTimes[verticeIndexInVerticeSet] == -1) {
                    deepSearchStructures.endTimes[verticeIndexInVerticeSet] = timeNumber;
                }

                if (!discoveredVertices.isEmpty()) {
                    discoveredVertices.pop();
                    listHeads.pop();
                }
            }

            if (discoveredVertices.isEmpty()) {
                currentVertice = getNextNotDiscoveredVerticeBasedOnVerticeSet();
                verticeIndexInVerticeSet = currentVertice == null ? -1 : getTheIndexOfTheVertice(currentVertice);
            } else {
                while (discoveredVertices.lastElement().hasNext()
                        && deepSearchStructures.discoveryTimes[verticeIndexInVerticeSet] != -1) {
                    currentVertice = discoveredVertices.lastElement().next();
                    verticeIndexInVerticeSet = getTheIndexOfTheVertice(currentVertice);
                    edgeToBeClassified = getEdgeThatContainsThisVertices(listHeads.lastElement(), currentVertice);

                    if (edgeToBeClassified != null)
                        classifyTheEdge(edgeToBeClassified);
                }
            }
        }
    }

    private void initializeDeepSearchStructures() {
        deepSearchStructures = new DeepSearchStructures();
        deepSearchStructures.discoveryTimes = new int[numberOfVertices];
        deepSearchStructures.endTimes = new int[numberOfVertices];
        deepSearchStructures.edgeClassifications = new EdgeClassifications[numberOfEdges];

        for (int i = 0; i < numberOfVertices; i++) {
            deepSearchStructures.discoveryTimes[i] = -1;
            deepSearchStructures.endTimes[i] = -1;
        }
    }

    private List<String> getAdjacencyListWithThisHead(String listHead) {
        if (listHead == null || listHead.isEmpty())
            return null;

        for (List<String> list : representations.successorAdjacencyList) {
            if (list.get(0).compareTo(listHead) == 0) {
                return list;
            }
        }

        return null;
    }

    private Edge getEdgeThatContainsThisVertices(String firstVertice, String secondVertice) {
        for (Edge edge : edges) {
            if (edge.getFirstVertice().compareTo(firstVertice) == 0
                    && edge.getSecondVertice().compareTo(secondVertice) == 0) {
                return edge;
            }
        }

        return null;
    }

    private void classifyTheEdge(Edge edge) {
        int edgeIndex = getTheIndexOfTheEdge(edge);
        int indexOfFirstVerticeInVerticeSet = getTheIndexOfTheVertice(edge.getFirstVertice());
        int indexOfSecondVerticeInVerticeSet = getTheIndexOfTheVertice(edge.getSecondVertice());

        if (edge.isDirected()) {
            if (deepSearchStructures.discoveryTimes[indexOfFirstVerticeInVerticeSet] != -1
                    && deepSearchStructures.discoveryTimes[indexOfSecondVerticeInVerticeSet] == -1) {
                deepSearchStructures.edgeClassifications[edgeIndex] = EdgeClassifications.TREE;
            } else if (deepSearchStructures.discoveryTimes[indexOfFirstVerticeInVerticeSet] < deepSearchStructures.discoveryTimes[indexOfSecondVerticeInVerticeSet]
                    && deepSearchStructures.endTimes[indexOfSecondVerticeInVerticeSet] != -1) {
                deepSearchStructures.edgeClassifications[edgeIndex] = EdgeClassifications.ADVANCE;
            } else if (deepSearchStructures.discoveryTimes[indexOfFirstVerticeInVerticeSet] > deepSearchStructures.discoveryTimes[indexOfSecondVerticeInVerticeSet]
                    && deepSearchStructures.endTimes[indexOfSecondVerticeInVerticeSet] == -1) {
                deepSearchStructures.edgeClassifications[edgeIndex] = EdgeClassifications.RETURN;
            } else {
                deepSearchStructures.edgeClassifications[edgeIndex] = EdgeClassifications.CROSSING;
            }
        } else {
            if (deepSearchStructures.discoveryTimes[indexOfFirstVerticeInVerticeSet] != -1
                    && deepSearchStructures.discoveryTimes[indexOfSecondVerticeInVerticeSet] == -1) {
                deepSearchStructures.edgeClassifications[edgeIndex] = EdgeClassifications.TREE;
            } else {
                deepSearchStructures.edgeClassifications[edgeIndex] = EdgeClassifications.RETURN;
            }
        }
    }

    private String getNextNotDiscoveredVerticeBasedOnVerticeSet() {
        Iterator<String> verticeIterator = vertices.iterator();

        for (int time : deepSearchStructures.discoveryTimes) {
            if (time == -1) {
                return verticeIterator.next();
            }
            verticeIterator.next();
        }

        return null;
    }

    public void showDeepSearchStructures() {
        makeDeepSearchAndComputeTimesInArrays();
        showVerticesSet();

        System.out.print("Discovery times: [ ");

        for (int discoveryTime : deepSearchStructures.discoveryTimes)
            System.out.print(discoveryTime + " ");

        System.out.println("]");

        System.out.print("End times: [ ");

        for (int endTime : deepSearchStructures.endTimes)
            System.out.print(endTime + " ");

        System.out.println("]");

        System.out.println("Edge classifications: ");
        Iterator<Edge> edgesIterator = edges.iterator();

        for (EdgeClassifications classification : deepSearchStructures.edgeClassifications) {
            System.out
                    .println("\t" + edgesIterator.next().getStringRepresentation() + " -> " + classification.getName());
        }

        System.out.println();
    }

    public static void main(String[] args) throws Exception {
        double start = System.currentTimeMillis();
        Scanner scanner = new Scanner(System.in);

        BufferedReader bufferedReader = new BufferedReader(new FileReader("graphs.txt"));
        String fileLine;
        Optional<Graph> graph = Optional.empty();

        while ((fileLine = bufferedReader.readLine()) != null) {
            graph = Graph.fromString(fileLine);
            graph.ifPresent(g -> {
                g.showDeepSearchStructures();
            });
        }

        bufferedReader.close();
        scanner.close();
        double end = System.currentTimeMillis();

        System.out.println("Duration: " + (end - start) + "ms\n");
    }
}