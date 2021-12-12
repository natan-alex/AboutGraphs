package representations;

import core.abstractions.AbstractGraph;

public class SuccessorAdjacencyArrays extends AbstractAdjacencyArrays {
    public SuccessorAdjacencyArrays(AbstractGraph graph) {
        super(graph, InsertionSortBehaviour.singletonInstance);
    }

    @Override
    protected int[] getSortedArray() {
        return getEdgeStartVertices();
    }

    @Override
    protected void sortAdjacencyArraysTogether(SortBehaviour sortBehaviour) {
        sortBehaviour.sortArraysTogether(getEdgeStartVertices(), getEdgeEndVertices());
    }

    @Override
    protected void reorderTheMainSortedArray(AbstractGraph graph) {
        int[] reorderedArray = new int[graph.getNumberOfVertices() + 1];
        int indexOfFirstOcurrenceOfAVerticeIndex = graph.getNumberOfEdges();
        int indexOfItemInSortedArray;

        reorderedArray[graph.getNumberOfVertices()] = graph.getNumberOfEdges();
        reorderedArray[0] = 0;

        for (int insertionIndex = graph.getNumberOfVertices() - 1; insertionIndex > 0; insertionIndex--) {
            indexOfItemInSortedArray = indexOfItemInSortedArray(insertionIndex);

            if (indexOfItemInSortedArray != -1)
                indexOfFirstOcurrenceOfAVerticeIndex = indexOfItemInSortedArray;

            reorderedArray[insertionIndex] = indexOfFirstOcurrenceOfAVerticeIndex;
        }

        setEdgeStartVertices(reorderedArray);
    }

    public void showSuccessorAdjacencyArrays() {
        System.out.println("\n\tSUCCESSOR ADJACENCY ARRAYS\n");
        super.showAdjacencyArraysIncreasingTheirValuesByOne();
    }
}
