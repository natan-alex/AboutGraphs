import java.util.regex.*;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
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
    private int numberOfVertices;
    private int numberOfEdges;
    private static boolean isEmpty;

    public static final Graph EMPTY_GRAPH = new Graph();

    public static Graph fromString(String s) throws IllegalArgumentException {
        Graph result = EMPTY_GRAPH;
        Pattern patternToValidateACompleteGraph = Pattern.compile(
                "^\\s*\\{\\s*([(|{]\\s*\\w+\\s*,(\\s*\\w+\\s*,)?\\s*\\w+\\s*[)|}]\\s*,\\s*)*[(|{]\\s*\\w+\\s*,(\\s*\\w+\\s*,)?\\s*\\w+\\s*[)|}]\\s*\\}\\s*$");
        Matcher matcher = patternToValidateACompleteGraph.matcher(s);

        if (matcher.matches()) {
            isEmpty = false;
            result = new Graph();
            result.stringRepresentation = s;
            result.fillEdgeListAndVerticeSet();
            result.fillGraphProperties();
            result.fillRepresentations();
        } else {
            isEmpty = true;
            throw new IllegalArgumentException("Invalid graph representation -> " + s);
        }

        return result;
    }

    private void fillEdgeListAndVerticeSet() {
        edges = new ArrayList<Edge>();
        edgeValues = new ArrayList<Integer>();
        vertices = new HashSet<String>();
        Edge currentEdge;
        Matcher matcher = Edge.PATTERN_TO_VALIDATE_AN_EDGE.matcher(stringRepresentation.trim());

        while (matcher.find()) {
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

        numberOfEdges = edges.size();
        numberOfVertices = vertices.size();
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

    private void checkIfThereAreDirectedAndUndirectedEdgesAtTheSameTime() throws IllegalArgumentException {
        if (statistics.numberOfDirectedEdges != 0 && statistics.numberOfUndirectedEdges != 0) {
            throw new IllegalArgumentException(
                    "A graph can not have directed an undirected edges at the same time, so the graph '"
                            + stringRepresentation + "' is invalid.");
        }
    }

    private void checkIfThereArePonderedAndUnponderedEdgesAtTheSameTime() throws IllegalArgumentException {
        if (statistics.numberOfPonderedEdges != 0 && statistics.numberOfUnponderedEdges != 0) {
            throw new IllegalArgumentException(
                    "A graph can not have pondered an unpondered edges at the same time, so the graph '"
                            + stringRepresentation + "' is invalid.");
        }
    }

    private void checkIfTheGraphIsDirectedBasedOnTheStatistics() {
        if (statistics.numberOfUndirectedEdges == 0) {
            isDirected = true;
        } else if (statistics.numberOfDirectedEdges == 0) {
            isDirected = false;
        }
    }

    private void checkIfTheGraphIsPonderedBasedOnTheStatistics() {
        if (statistics.numberOfUnponderedEdges == 0) {
            isPondered = true;
        } else if (statistics.numberOfPonderedEdges == 0) {
            isPondered = false;
        }
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

    public void showPredecessorAdjacencyList() throws IllegalAccessException {
        if (isEmpty)
            throw new IllegalAccessException("Can not show any graph representation for empty graph.");

        showAdjacencyList(representations.predecessorAdjacencyList);
    }

    public void showSuccessorAdjacencyList() throws IllegalAccessException {
        if (isEmpty)
            throw new IllegalAccessException("Can not show any graph representation for empty graph.");

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
                    indexOfSecondVertice = findTheIndexOfTheVertice(edge.getSecondVertice());
                    representations.adjacencyMatrix[currentLine][indexOfSecondVertice] = 1;
                }
            }

            currentLine++;
        }
    }

    private int findTheIndexOfTheVertice(String verticeToFind) {
        int index = 0;

        for (String vertice : vertices) {
            if (vertice.compareTo(verticeToFind) == 0) {
                return index;
            }

            index++;
        }

        return -1;
    }

    public void showAdjacencyMatrix() throws IllegalAccessException {
        if (isEmpty)
            throw new IllegalAccessException("Can not show any graph representation for empty graph.");

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
                    indexOfSecondVertice = findTheIndexOfTheVertice(edge.getSecondVertice());
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

    public void showIncidencyMatrix() throws IllegalAccessException {
        if (isEmpty)
            throw new IllegalAccessException("Can not show any graph representation for empty graph.");

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
            indexOfFirstVertice = findTheIndexOfTheVertice(edge.getFirstVertice());
            indexOfSecondVertice = findTheIndexOfTheVertice(edge.getSecondVertice());
            representations.predecessorAdjacencyArrayStart[currentIndex] = indexOfFirstVertice;
            representations.successorAdjacencyArrayStart[currentIndex] = indexOfFirstVertice;
            representations.predecessorAdjacencyArrayEnd[currentIndex] = indexOfSecondVertice;
            representations.successorAdjacencyArrayEnd[currentIndex] = indexOfSecondVertice;
            currentIndex++;
        }
    }

    private void reorderSuccessorAdjacencyArrays() {
        orderAdjacencyArrays(representations.successorAdjacencyArrayStart, representations.successorAdjacencyArrayEnd);
        representations.successorAdjacencyArrayStart = reorderAndReturnTheSortedAdjacencyArray(
                representations.successorAdjacencyArrayStart);
    }

    private void reorderPredecessorAdjacencyArrays() {
        orderAdjacencyArrays(representations.predecessorAdjacencyArrayEnd,
                representations.predecessorAdjacencyArrayStart);

        representations.predecessorAdjacencyArrayEnd = reorderAndReturnTheSortedAdjacencyArray(
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

    private int[] reorderAndReturnTheSortedAdjacencyArray(int[] sortedArray) {
        int[] reorderedArray = new int[numberOfVertices + 1];
        int indexOfWhereInsert, indexOfFirstOcurrenceOfAVerticeIndex = 0;

        reorderedArray[numberOfVertices] = numberOfEdges;
        reorderedArray[0] = 0;

        for (int whereInsertInReorderedArray = 1; whereInsertInReorderedArray < numberOfVertices; whereInsertInReorderedArray++) {
            indexOfWhereInsert = firstIndexOfItemInArray(whereInsertInReorderedArray, sortedArray);

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

    public void showAllRepresentations() throws IllegalAccessException {
        if (isEmpty)
            throw new IllegalAccessException("Can not show any graph representation for empty graph.");

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

        for (int timeCounter = 1; timeCounter <= 2 * numberOfVertices; timeCounter++) {

        }
    }

    private void initializeDeepSearchStructures() {
        deepSearchStructures = new DeepSearchStructures();
        deepSearchStructures.discoveryTimes = new int[numberOfVertices];
        deepSearchStructures.endTimes = new int[numberOfVertices];

        for (int i = 0; i < numberOfVertices; i++) {
            deepSearchStructures.discoveryTimes[i] = -1;
            deepSearchStructures.endTimes[i] = -1;
        }
    }

    public void showDeepSearchStructures() throws IllegalAccessException {
        if (isEmpty)
            throw new IllegalAccessException("Can not show any graph representation for empty graph.");

        System.out.println("\n[ ");

        for (int time = 0; time < numberOfVertices; time++)
            System.out.print(deepSearchStructures.discoveryTimes[time] + " ");

        System.out.println("]");

        System.out.println("\n[ ");

        for (int time = 0; time < numberOfVertices; time++)
            System.out.print(deepSearchStructures.endTimes[time] + " ");

        System.out.println("]");
    }

    public static void main(String[] args) throws Exception {
        double start = System.currentTimeMillis();
        // Scanner scanner = new Scanner(System.in);

        BufferedReader bufferedReader = new BufferedReader(new FileReader("graphs.txt"));
        String fileLine;
        Graph graph;
        // graph = Graph.fromString(scanner.nextLine());

        // graph.showAllRepresentations();

        while ((fileLine = bufferedReader.readLine()) != null) {
            graph = Graph.fromString(fileLine);
            System.out.println();
            System.out.println("\n\tPREDECESSOR ADJACENCY ARRAYS\n");
            graph.showPredecessorAdjacencyArrays();

            System.out.println("\n\tSUCCESSOR ADJACENCY ARRAYS\n");
            graph.showSuccessorAdjacencyArrays();
            System.out.println();
            // graph.showAllRepresentations();
        }

        // graph.showSuccessorAdjacencyList();
        // graph.makeDeepSearchAndComputeTimesInArrays();
        // graph.showDeepSearchStructures();

        bufferedReader.close();
        // scanner.close();
        double end = System.currentTimeMillis();

        System.out.println("Duration: " + (end - start) + "ms\n");
    }
}
