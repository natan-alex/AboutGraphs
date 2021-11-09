import java.util.Map;

public abstract class BaseAdjacencyArrays {
    public int[] edgeStartPoints;
    public int[] edgeEndPoints;
    private int[] arrayToBeSorted;
    private int[] otherArray;

    protected enum WhichArrayToSort {
        START_ARRAY, END_ARRAY
    }

    protected BaseAdjacencyArrays(Graph graph) {
        int currentIndex = 0;
        int indexOfFirstVertice, indexOfSecondVertice;

        edgeStartPoints = new int[graph.numberOfEdges];
        edgeEndPoints = new int[graph.numberOfEdges];

        for (Map.Entry<Edge, Integer> entry : graph.edgesAndTheirIndices.entrySet()) {
            indexOfFirstVertice = graph.verticesAndTheirIndices.get(entry.getKey().firstVertice);
            indexOfSecondVertice = graph.verticesAndTheirIndices.get(entry.getKey().secondVertice);

            edgeStartPoints[currentIndex] = indexOfFirstVertice;
            edgeEndPoints[currentIndex] = indexOfSecondVertice;

            currentIndex++;
        }
    }

    protected void orderAdjacencyArrays(WhichArrayToSort whichArrayToSort) {
        int currentItemOfWhichToSortArray;
        int currentItemOfOtherArray;
        int auxiliarVariable;

        fillArrayToBeSortedAttributeAccordingToWhichToSort(whichArrayToSort);

        for (int minimumItemIndex = 1; minimumItemIndex < arrayToBeSorted.length; minimumItemIndex++) {
            currentItemOfWhichToSortArray = arrayToBeSorted[minimumItemIndex];
            currentItemOfOtherArray = otherArray[minimumItemIndex];
            auxiliarVariable = minimumItemIndex - 1;

            while (auxiliarVariable >= 0 && arrayToBeSorted[auxiliarVariable] > currentItemOfWhichToSortArray) {
                arrayToBeSorted[auxiliarVariable + 1] = arrayToBeSorted[auxiliarVariable];
                otherArray[auxiliarVariable + 1] = otherArray[auxiliarVariable];
                auxiliarVariable--;
            }

            arrayToBeSorted[auxiliarVariable + 1] = currentItemOfWhichToSortArray;
            otherArray[auxiliarVariable + 1] = currentItemOfOtherArray;
        }
    }

    private void fillArrayToBeSortedAttributeAccordingToWhichToSort(WhichArrayToSort whichArrayToSort) {
        if (whichArrayToSort == WhichArrayToSort.START_ARRAY) {
            arrayToBeSorted = edgeStartPoints;
            otherArray = edgeEndPoints;
        } else {
            arrayToBeSorted = edgeEndPoints;
            otherArray = edgeStartPoints;
        }
    }

    protected int getIndexOfItemInToBeSortedArray(int item) {
        for (int i = 0; i < arrayToBeSorted.length; i++) {
            if (arrayToBeSorted[i] == item) {
                return i;
            }
        }

        return -1;
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
