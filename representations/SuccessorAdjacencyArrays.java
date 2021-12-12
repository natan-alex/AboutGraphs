package representations;

import core.abstractions.AbstractGraph;

public class SuccessorAdjacencyArrays extends AbstractAdjacencyArrays {
    public SuccessorAdjacencyArrays(AbstractGraph graph) {
        super(graph, InsertionSortBehaviour.singletonInstance);
    }

    @Override
    protected int[] getSortedArray() {
        return edgeStartVertices;
    }

    @Override
    protected void sortAdjacencyArraysTogether(SortBehaviour sortBehaviour) {
        sortBehaviour.sortArraysTogether(edgeStartVertices, edgeEndVertices);
    }

    @Override
    protected void reorderTheMainSortedArray(AbstractGraph relatedGraph) {
        int[] reorderedArray = new int[relatedGraph.getNumberOfVertices() + 1];
        int indexOfFirstOcurrenceOfAVerticeIndex = relatedGraph.getNumberOfEdges();
        int indexOfItemInSortedArray;

        reorderedArray[relatedGraph.getNumberOfVertices()] = relatedGraph.getNumberOfEdges();
        reorderedArray[0] = 0;

        for (int insertionIndex = relatedGraph.getNumberOfVertices() - 1; insertionIndex > 0; insertionIndex--) {
            indexOfItemInSortedArray = indexOfItemInSortedArray(insertionIndex);

            if (indexOfItemInSortedArray != -1)
                indexOfFirstOcurrenceOfAVerticeIndex = indexOfItemInSortedArray;

            reorderedArray[insertionIndex] = indexOfFirstOcurrenceOfAVerticeIndex;
        }

        edgeStartVertices = reorderedArray;
    }

    @Override
    public void showArrays() {
        System.out.println("\n\tSUCCESSOR ADJACENCY ARRAYS\n");
        super.showAdjacencyArraysIncreasingTheirValuesByOne();
    }
}
