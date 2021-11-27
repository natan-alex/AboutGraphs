package aboutGraphs.representations;

import aboutGraphs.core.*;

public class SuccessorAdjacencyArrays extends BaseAdjacencyArrays {
    public SuccessorAdjacencyArrays(Graph graph) {
        super(graph);

        orderAdjacencyArrays(verticesWhereTheEdgeComesFrom, verticesWhereTheEdgeIsIncident);
        verticesWhereTheEdgeComesFrom = getReorderedSortedArray(graph);
    }

    private int[] getReorderedSortedArray(Graph graph) {
        int[] reorderedArray = new int[graph.numberOfEdges + 1];
        int indexOfFirstOcurrenceOfAVerticeIndex = graph.numberOfEdges;
        int indexOfItemInStartWhereEdgesComesFromArray;

        reorderedArray[graph.numberOfVertices] = graph.numberOfEdges;
        reorderedArray[0] = 0;

        for (int insertionIndex = graph.numberOfVertices - 1; insertionIndex > 0; insertionIndex--) {
            indexOfItemInStartWhereEdgesComesFromArray = getIndexOfItemInToBeSortedArray(insertionIndex,
                    verticesWhereTheEdgeComesFrom);

            if (indexOfItemInStartWhereEdgesComesFromArray != -1) {
                indexOfFirstOcurrenceOfAVerticeIndex = indexOfItemInStartWhereEdgesComesFromArray;
            }

            reorderedArray[insertionIndex] = indexOfFirstOcurrenceOfAVerticeIndex;
        }

        return reorderedArray;
    }

    public void showSuccessorAdjacencyArrays() {
        System.out.println("\n\tSUCCESSOR ADJACENCY ARRAYS\n");
        super.showAdjacencyArraysIncreasingTheirValuesByOne();
    }
}