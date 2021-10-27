import java.util.Map;

public class AdjacencyArrays {
    public int[] edgeStartPoints;
    public int[] edgeEndPoints;

    protected enum WhichArrayToSort {
        START_ARRAY, END_ARRAY
    }

    protected AdjacencyArrays(Graph graph) {
        int currentIndex = 0;
        int indexOfFirstVertice, indexOfSecondVertice;

        edgeStartPoints = new int[graph.numberOfEdges];
        edgeEndPoints = new int[graph.numberOfEdges];

        for (Map.Entry<Edge, Integer> entry : graph.edges.entrySet()) {
            indexOfFirstVertice = graph.vertices.get(entry.getKey().firstVertice);
            indexOfSecondVertice = graph.vertices.get(entry.getKey().secondVertice);

            edgeStartPoints[currentIndex] = indexOfFirstVertice;
            edgeEndPoints[currentIndex] = indexOfSecondVertice;

            currentIndex++;
        }
    }

    protected void orderAdjacencyArrays(WhichArrayToSort whichArrayToSort) {
        int currentItemOfWhichToSortArray, currentItemOfOtherArray;
        int j;
        int[] arrayToBeSorted, otherArray;

        if (whichArrayToSort == WhichArrayToSort.START_ARRAY) {
            arrayToBeSorted = edgeStartPoints;
            otherArray = edgeEndPoints;
        } else {
            arrayToBeSorted = edgeEndPoints;
            otherArray = edgeStartPoints;
        }

        for (int i = 1; i < arrayToBeSorted.length; i++) {
            currentItemOfWhichToSortArray = arrayToBeSorted[i];
            currentItemOfOtherArray = otherArray[i];
            j = i - 1;

            while (j >= 0 && arrayToBeSorted[j] > currentItemOfWhichToSortArray) {
                arrayToBeSorted[j + 1] = arrayToBeSorted[j];
                otherArray[j + 1] = otherArray[j];
                j--;
            }

            arrayToBeSorted[j + 1] = currentItemOfWhichToSortArray;
            otherArray[j + 1] = currentItemOfOtherArray;
        }
    }

    public void showAdjacencyArraysIncreasingTheirValuesByOne() {
        System.out.print("First array of indices: [ ");

        for (int index : edgeStartPoints)
            System.out.print((index + 1) + " ");

        System.out.println("]");

        System.out.print("Second array of indices: [ ");

        for (int index : edgeEndPoints)
            System.out.print((index + 1) + " ");

        System.out.println("]");
    }
}
