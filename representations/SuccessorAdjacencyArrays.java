package representations;

import core.abstractions.AbstractGraph;

public class SuccessorAdjacencyArrays extends AbstractAdjacencyArrays {
    public SuccessorAdjacencyArrays(AbstractGraph graph) {
        super(graph);

        orderAdjacencyArrays(edgeStartVertices, edgeEndVertices);
        edgeStartVertices = getReorderedSortedArray(graph);
    }

    private int[] getReorderedSortedArray(AbstractGraph graph) {
        int[] reorderedArray = new int[graph.getNumberOfVertices() + 1];
        int indexOfFirstOcurrenceOfAVerticeIndex = graph.getNumberOfEdges();
        int indexOfItemInStartWhereEdgesComesFromArray;

        reorderedArray[graph.getNumberOfVertices()] = graph.getNumberOfEdges();
        reorderedArray[0] = 0;

        for (int insertionIndex = graph.getNumberOfVertices() - 1; insertionIndex > 0; insertionIndex--) {
            indexOfItemInStartWhereEdgesComesFromArray = getIndexOfItemInToBeSortedArray(insertionIndex,
                    edgeStartVertices);

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
