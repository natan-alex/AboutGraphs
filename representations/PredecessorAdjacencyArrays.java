package representations;

import core.abstractions.AbstractGraph;

public class PredecessorAdjacencyArrays extends AbstractAdjacencyArrays {
    public PredecessorAdjacencyArrays(AbstractGraph graph) {
        super(graph, InsertionSortBehaviour.singletonInstance);
    }

    @Override
    protected int[] getSortedArray() {
        return getEdgeEndVertices();
    }

    @Override
    protected void sortAdjacencyArraysTogether(SortBehaviour sortBehaviour) {
        sortBehaviour.sortArraysTogether(getEdgeEndVertices(), getEdgeStartVertices());
    }

    @Override
    protected void reorderTheMainSortedArray(AbstractGraph graph) {
        int[] reorderedArray = new int[graph.getNumberOfVertices() + 1];
        int indexOfFirstOcurrenceOfAVerticeIndex = graph.getNumberOfEdges();
        int indexOfItemInWhereEdgesAreIncidentArray;

        reorderedArray[graph.getNumberOfVertices()] = graph.getNumberOfEdges();
        reorderedArray[0] = 0;

        for (int insertionIndex = 1; insertionIndex < graph.getNumberOfVertices(); insertionIndex++) {
            indexOfItemInWhereEdgesAreIncidentArray = indexOfItemInSortedArray(insertionIndex);

            if (indexOfItemInWhereEdgesAreIncidentArray != -1)
                indexOfFirstOcurrenceOfAVerticeIndex = indexOfItemInWhereEdgesAreIncidentArray;

            reorderedArray[insertionIndex] = indexOfFirstOcurrenceOfAVerticeIndex;
        }

        setEdgeEndVertices(reorderedArray);
    }

    public void showPredecessorAdjacencyArrays() {
        System.out.println("\n\tPREDECESSOR ADJACENCY ARRAYS\n");
        super.showAdjacencyArraysIncreasingTheirValuesByOne();
    }
}
