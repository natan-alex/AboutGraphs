package aboutGraphs.representations;

import aboutGraphs.core.*;

public abstract class BaseAdjacencyArrays {
    public int[] verticesWhereTheEdgeComesFrom;
    public int[] verticesWhereTheEdgeIsIncident;

    protected BaseAdjacencyArrays(Graph graph) {
        int insertionIndex = 0;
        int indexOfFirstVertice, indexOfSecondVertice;

        verticesWhereTheEdgeComesFrom = new int[graph.numberOfEdges];
        verticesWhereTheEdgeIsIncident = new int[graph.numberOfEdges];

        for (Edge edge : graph.edges) {
            indexOfFirstVertice = graph.vertices.indexOf(edge.firstVertice);
            indexOfSecondVertice = graph.vertices.indexOf(edge.secondVertice);

            verticesWhereTheEdgeComesFrom[insertionIndex] = indexOfFirstVertice;
            verticesWhereTheEdgeIsIncident[insertionIndex] = indexOfSecondVertice;

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

        for (int index : verticesWhereTheEdgeComesFrom)
            System.out.print((index + 1) + " ");

        System.out.println("]");

        System.out.print("Second array of indices: [ ");

        for (int index : verticesWhereTheEdgeIsIncident)
            System.out.print((index + 1) + " ");

        System.out.println("]");
    }
}
