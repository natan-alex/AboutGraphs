package representations;

import core.abstractions.AbstractEdge;
import core.abstractions.AbstractGraph;

public abstract class AbstractAdjacencyArrays {
    protected int[] edgeStartVertices;
    protected int[] edgeEndVertices;

    protected AbstractAdjacencyArrays(AbstractGraph graph) {
        int insertionIndex = 0;
        int indexOfFirstVertice, indexOfSecondVertice;

        edgeStartVertices = new int[graph.getNumberOfEdges()];
        edgeEndVertices = new int[graph.getNumberOfEdges()];

        for (AbstractEdge edge : graph.getEdges()) {
            indexOfFirstVertice = graph.indexOfVertice(edge.getFirstVertice());
            indexOfSecondVertice = graph.indexOfVertice(edge.getSecondVertice());

            edgeStartVertices[insertionIndex] = indexOfFirstVertice;
            edgeEndVertices[insertionIndex] = indexOfSecondVertice;

            insertionIndex++;
        }
    }

    protected void orderAdjacencyArrays(int[] arrayToBeSorted, int[] otherArray) {
        int currentItemOfWhichToSortArray;
        int currentItemOfOtherArray;
        int auxiliarVariable;

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

    protected int getIndexOfItemInToBeSortedArray(int item, int[] arrayToBeSorted) {
        for (int i = 0; i < arrayToBeSorted.length; i++) {
            if (arrayToBeSorted[i] == item) {
                return i;
            }
        }

        return -1;
    }

    protected void showAdjacencyArraysIncreasingTheirValuesByOne() {
        System.out.print("First array of indices: [ ");

        for (int index : edgeStartVertices)
            System.out.print((index + 1) + " ");

        System.out.println("]");

        System.out.print("Second array of indices: [ ");

        for (int index : edgeEndVertices)
            System.out.print((index + 1) + " ");

        System.out.println("]");
    }
}
