import java.util.regex.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.IllegalArgumentException;
import java.util.Iterator;
import java.util.Stack;

public class Graph {
    private class GraphStatistics {
        public int numberOfPonderedEdges;
        public int numberOfUnponderedEdges;
        public int numberOfDirectedEdges;
        public int numberOfUndirectedEdges;
    }

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
    }

    private String stringRepresentation;
    private List<Edge> edges;
    private Set<String> vertices;
    private List<Integer> edgeValues;
    private boolean isPondered;
    private boolean isDirected;
    private GraphStatistics statistics;
    private GraphRepresentations representations;
    private DeepSearchStructures deepSearchStructures;

    public static final Graph EMPTY_GRAPH = new Graph();

    public static Graph fromString(String s) throws IllegalArgumentException {
        Graph result = EMPTY_GRAPH;
        Pattern patternToValidateACompleteGraph = Pattern.compile(
                "^\\s*\\{\\s*([(|{]\\s*\\w+\\s*,(\\s*\\w+\\s*,)?\\s*\\w+\\s*[)|}]\\s*,\\s*)*[(|{]\\s*\\w+\\s*,(\\s*\\w+\\s*,)?\\s*\\w+\\s*[)|}]\\s*\\}\\s*$");
        Matcher matcher = patternToValidateACompleteGraph.matcher(s);

        if (matcher.matches()) {
            result = new Graph();
            result.stringRepresentation = s;
            result.fillEdgeListAndVerticeSet();
            result.fillGraphProperties();
            result.fillRepresentations();
        }

        return result;
    }

    private void fillEdgeListAndVerticeSet() {
        edges = new ArrayList<Edge>();
        edgeValues = new ArrayList<Integer>();
        vertices = new HashSet<String>();
        Edge currentEdge;
        Matcher matcher = Edge.PATTERN_TO_VALIDATE_AN_EDGE.matcher(stringRepresentation.trim());
        System.out.println("string representation: " + stringRepresentation);

        while (matcher.find()) {
            System.out.println("match: " + matcher.group().toString());
            currentEdge = Edge.fromString(matcher.group());

            if (currentEdge != Edge.EMPTY_EDGE) {
                edges.add(currentEdge);

                if (currentEdge.isPondered())
                    edgeValues.add(currentEdge.getValue());

                vertices.add(currentEdge.getFirstVertice());
                vertices.add(currentEdge.getSecondVertice());
            } else
                System.out.println("The edge '" + matcher.group() + "' in graph '" + stringRepresentation
                        + "' is not valid, so it will not be considered.\n");
        }
    }

    private void fillGraphProperties() throws IllegalArgumentException {
        statistics = new GraphStatistics();
        statistics.numberOfDirectedEdges = 0;
        statistics.numberOfUndirectedEdges = 0;
        statistics.numberOfPonderedEdges = 0;
        statistics.numberOfUnponderedEdges = 0;

        for (Edge edge : edges) {
            if (edge.isDirected()) {
                statistics.numberOfDirectedEdges++;
            } else {
                statistics.numberOfUndirectedEdges++;
            }

            if (edge.isPondered()) {
                statistics.numberOfPonderedEdges++;
            } else {
                statistics.numberOfUnponderedEdges++;
            }

            checkIfThereAreDirectedAndUndirectedEdgesAtTheSameTime();
            checkIfThereArePonderedAndUnponderedEdgesAtTheSameTime();
        }

        checkIfTheGraphIsDirectedBasedOnTheStatistics();
        checkIfTheGraphIsPonderedBasedOnTheStatistics();
    }

    void checkIfThereAreDirectedAndUndirectedEdgesAtTheSameTime() throws IllegalArgumentException {
        if (statistics.numberOfDirectedEdges != 0 && statistics.numberOfUndirectedEdges != 0) {
            throw new IllegalArgumentException(
                    "A graph can not have directed an undirected edges at the same time, so the graph '"
                            + stringRepresentation + "' is invalid.");
        }
    }

    void checkIfThereArePonderedAndUnponderedEdgesAtTheSameTime() throws IllegalArgumentException {
        if (statistics.numberOfPonderedEdges != 0 && statistics.numberOfUnponderedEdges != 0) {
            throw new IllegalArgumentException(
                    "A graph can not have pondered an unpondered edges at the same time, so the graph '"
                            + stringRepresentation + "' is invalid.");
        }
    }

    void checkIfTheGraphIsDirectedBasedOnTheStatistics() {
        if (statistics.numberOfUndirectedEdges == 0) {
            isDirected = true;
        } else if (statistics.numberOfDirectedEdges == 0) {
            isDirected = false;
        }
    }

    void checkIfTheGraphIsPonderedBasedOnTheStatistics() {
        if (statistics.numberOfUnponderedEdges == 0) {
            isPondered = true;
        } else if (statistics.numberOfPonderedEdges == 0) {
            isPondered = false;
        }
    }

    void fillRepresentations() {
        representations = new GraphRepresentations();
        fillSuccessorAdjacencyList();
        fillPredecessorAdjacencyList();
        fillAdjacencyMatrix();
        fillIncidencyMatrix();
        fillAdjacencyArraysIncreasingTheirIndicesByOne();
        reorderSuccessorAdjacencyArrays();
        reorderPredecessorAdjacencyArrays();
    }

    void fillSuccessorAdjacencyList() {
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

    void fillPredecessorAdjacencyList() {
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

    void showPredecessorAdjacencyList() {
        showAdjacencyList(representations.predecessorAdjacencyList);
    }

    void showSuccessorAdjacencyList() {
        showAdjacencyList(representations.successorAdjacencyList);
    }

    void showAdjacencyList(List<List<String>> adjacencyList) {
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

    void fillAdjacencyMatrix() {
        int numberOfVertices = vertices.size();
        representations.adjacencyMatrix = new int[numberOfVertices][numberOfVertices];
        Iterator<String> verticeIterator = vertices.iterator();
        String currentVertice;
        int indexOfSecondVertice;

        for (int i = 0; i < numberOfVertices; i++) {
            currentVertice = verticeIterator.next();

            for (int j = 0; j < numberOfVertices; j++)
                representations.adjacencyMatrix[i][j] = 0;

            for (Edge edge : edges) {
                if (currentVertice.compareTo(edge.getFirstVertice()) == 0) {
                    indexOfSecondVertice = findTheIndexOfTheVertice(edge.getSecondVertice());
                    representations.adjacencyMatrix[i][indexOfSecondVertice] = 1;
                }
            }
        }
    }

    int findTheIndexOfTheVertice(String verticeToFind) {
        int index = 0;

        for (String vertice : vertices) {
            if (vertice.compareTo(verticeToFind) == 0) {
                return index;
            }

            index++;
        }

        return -1;
    }

    void showAdjacencyMatrix() {
        int numberOfVertices = vertices.size();
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

    void fillIncidencyMatrix() {
        int numberOfVertices = vertices.size();
        int numberOfEdges = edges.size();
        int indexOfSecondVertice;
        Iterator<String> verticeIterator = vertices.iterator();
        Iterator<Edge> edgeIterator;
        String currentVertice;
        Edge currentEdge;

        representations.incidencyMatrix = new int[numberOfVertices][numberOfEdges];

        for (int i = 0; i < numberOfVertices; i++) {
            edgeIterator = edges.iterator();
            currentVertice = verticeIterator.next();

            for (int j = 0; j < numberOfEdges; j++) {
                currentEdge = edgeIterator.next();

                if (currentVertice.compareTo(currentEdge.getFirstVertice()) == 0) {
                    indexOfSecondVertice = findTheIndexOfTheVertice(currentEdge.getSecondVertice());
                    representations.incidencyMatrix[i][j] = 1;
                    representations.incidencyMatrix[indexOfSecondVertice][j] = -1;
                } else if (representations.incidencyMatrix[i][j] != -1) {
                    representations.incidencyMatrix[i][j] = 0;
                }
            }
        }
    }

    void showIncidencyMatrix() {
        Iterator<String> verticeIterator = vertices.iterator();
        Iterator<Edge> edgeIterator;
        int currentItem;
        Edge currentEdge;

        System.out.print("\t");

        for (Edge edge : edges)
            System.out.print(edge.getFirstVertice() + " -- " + edge.getSecondVertice() + "\t");

        System.out.println();

        for (int i = 0; i < vertices.size(); i++) {
            System.out.print(verticeIterator.next() + "\t");
            edgeIterator = edges.iterator();

            for (int j = 0; j < edges.size(); j++) {
                currentItem = representations.incidencyMatrix[i][j];
                currentEdge = edgeIterator.next();

                if (currentItem == 1)
                    System.out.print("+");
                else if (currentItem == 0)
                    System.out.print(" ");

                System.out.print(currentItem);

                if (currentEdge.isPondered())
                    System.out.print(" | " + currentEdge.getValue());

                System.out.print("\t");
            }

            System.out.println();
        }
    }

    private void fillAdjacencyArraysIncreasingTheirIndicesByOne() {
        int numberOfEdges = edges.size();
        int currentIndex = 0;
        int indexOfFirstVertice, indexOfSecondVertice;

        representations.predecessorAdjacencyArrayStart = new int[numberOfEdges];
        representations.predecessorAdjacencyArrayEnd = new int[numberOfEdges];
        representations.successorAdjacencyArrayStart = new int[numberOfEdges];
        representations.successorAdjacencyArrayEnd = new int[numberOfEdges];

        for (Edge edge : edges) {
            indexOfFirstVertice = findTheIndexOfTheVertice(edge.getFirstVertice());
            indexOfSecondVertice = findTheIndexOfTheVertice(edge.getSecondVertice());
            representations.predecessorAdjacencyArrayStart[currentIndex] = indexOfFirstVertice + 1;
            representations.successorAdjacencyArrayStart[currentIndex] = indexOfFirstVertice + 1;
            representations.predecessorAdjacencyArrayEnd[currentIndex] = indexOfSecondVertice + 1;
            representations.successorAdjacencyArrayEnd[currentIndex] = indexOfSecondVertice + 1;
            currentIndex++;
        }
    }

    private void reorderSuccessorAdjacencyArrays() {
        orderAdjacencyArrays(representations.successorAdjacencyArrayStart, representations.successorAdjacencyArrayEnd);
        representations.successorAdjacencyArrayStart = getReorderedSortedAdjacencyArray(
                representations.successorAdjacencyArrayStart);
    }

    private void reorderPredecessorAdjacencyArrays() {
        orderAdjacencyArrays(representations.predecessorAdjacencyArrayEnd,
                representations.predecessorAdjacencyArrayStart);
        representations.predecessorAdjacencyArrayEnd = getReorderedSortedAdjacencyArray(
                representations.predecessorAdjacencyArrayEnd);
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

    private int[] getReorderedSortedAdjacencyArray(int[] sortedArray) {
        int numberOfEdges = edges.size();
        int numberOfVertices = vertices.size();
        int currentValueOfSortedArray, indexFromWhereToRead = numberOfEdges - 1;
        int[] reorderedArray = new int[numberOfVertices + 1];
        int indexWhereToInsert = numberOfVertices - 1;
        boolean hasIndexWhereInsertInSortedArray;

        reorderedArray[numberOfVertices] = numberOfEdges + 1;

        while (indexWhereToInsert >= 0) {
            currentValueOfSortedArray = sortedArray[indexFromWhereToRead];

            while (indexFromWhereToRead > 0 && sortedArray[indexFromWhereToRead - 1] == currentValueOfSortedArray)
                indexFromWhereToRead--;

            hasIndexWhereInsertInSortedArray = false;
            for (int i = 0; i < numberOfEdges; i++)
                if (sortedArray[i] == indexWhereToInsert + 1) {
                    hasIndexWhereInsertInSortedArray = true;
                    i = numberOfEdges;
                }

            if (hasIndexWhereInsertInSortedArray)
                reorderedArray[indexWhereToInsert] = indexFromWhereToRead-- + 1;
            else
                reorderedArray[indexWhereToInsert] = reorderedArray[indexWhereToInsert + 1];

            indexWhereToInsert--;
        }

        return reorderedArray;
    }

    void showSuccessorAdjacencyArrays() {
        showAdjacencyArrays(representations.successorAdjacencyArrayStart, representations.successorAdjacencyArrayEnd);
    }

    void showPredecessorAdjacencyArrays() {
        showAdjacencyArrays(representations.predecessorAdjacencyArrayEnd,
                representations.predecessorAdjacencyArrayStart);
    }

    void showAdjacencyArrays(int sortedArray[], int otherArray[]) {
        showVerticesSet();

        System.out.print("Start array indices: [ ");

        for (int i = 0; i < vertices.size() + 1; i++)
            System.out.print(sortedArray[i] + " ");

        System.out.println("]");

        System.out.print("End array indices: [ ");

        for (int i = 0; i < edges.size(); i++)
            System.out.print(otherArray[i] + " ");

        System.out.println("]");
    }

    void showVerticesSet() {
        System.out.print("Vertice set: {");
        String[] verticesArray = new String[vertices.size()];
        vertices.toArray(verticesArray);

        for (int i = 0; i < verticesArray.length - 1; i++)
            System.out.print(verticesArray[i] + ", ");

        System.out.println(verticesArray[verticesArray.length - 1] + " }");
    }

    void showAllRepresentations() {
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

    public void makeDeepSearchAndComputeTimesInArrays() {
        // Stack<List<String>> discoveredVertices;
        // Stack<List<List<String>>> discoveredLists;
        // Iterator<List<String>> currentList =
        // representations.successorAdjacencyList.iterator();
        // int indexOfCurrentList = 0;
        // Iterator<String> currentVertice;

        initializeDeepSearchStructures();

        for (int timeCounter = 1; timeCounter <= 2 * vertices.size(); timeCounter++) {

        }
    }

    void initializeDeepSearchStructures() {
        deepSearchStructures = new DeepSearchStructures();
        int numberOfVertices = vertices.size();
        deepSearchStructures.discoveryTimes = new int[numberOfVertices];
        deepSearchStructures.endTimes = new int[numberOfVertices];

        for (int i = 0; i < numberOfVertices; i++) {
            deepSearchStructures.discoveryTimes[i] = -1;
            deepSearchStructures.endTimes[i] = -1;
        }
    }

    public void showDeepSearchStructures() {
        System.out.println("\n[ ");

        for (int time = 0; time < vertices.size(); time++)
            System.out.print(deepSearchStructures.discoveryTimes[time] + " ");

        System.out.println("]");

        System.out.println("\n[ ");

        for (int time = 0; time < vertices.size(); time++)
            System.out.print(deepSearchStructures.endTimes[time] + " ");

        System.out.println("]");
    }

    public static void main(String[] args) throws Exception {
        double start = System.currentTimeMillis();

        BufferedReader bufferedReader = new BufferedReader(new FileReader("graphs.txt"));
        String fileLine;
        Graph graph;

        while ((fileLine = bufferedReader.readLine()) != null) {
            graph = Graph.fromString(fileLine);
            graph.showAllRepresentations();
        }

        // graph.showSuccessorAdjacencyList();
        // graph.makeDeepSearchAndComputeTimesInArrays();
        // graph.showDeepSearchStructures();

        bufferedReader.close();
        double end = System.currentTimeMillis();

        System.out.println("Duration: " + (end - start) + "ms\n");
    }
}
