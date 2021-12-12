package representations;

import core.abstractions.AbstractGraph;

public class PredecessorAdjacencyArrays extends AbstractAdjacencyArrays {
    public PredecessorAdjacencyArrays(AbstractGraph graph) {
        super(graph, InsertionSortBehaviour.singletonInstance);
    }

    @Override
    protected int[] getSortedArray() {
        return edgeEndVertices;
    }

    @Override
    protected void sortAdjacencyArraysTogether(SortBehaviour sortBehaviour) {
        sortBehaviour.sortArraysTogether(edgeEndVertices, edgeStartVertices);
    }

    @Override
    protected void reorderTheMainSortedArray(AbstractGraph relatedGraph) {
        int[] reorderedArray = new int[relatedGraph.getNumberOfVertices() + 1];
        int indexOfFirstOcurrenceOfAVerticeIndex = relatedGraph.getNumberOfEdges();
        int indexOfItemInWhereEdgesAreIncidentArray;

        reorderedArray[relatedGraph.getNumberOfVertices()] = relatedGraph.getNumberOfEdges();
        reorderedArray[0] = 0;

        for (int insertionIndex = 1; insertionIndex < relatedGraph.getNumberOfVertices(); insertionIndex++) {
            indexOfItemInWhereEdgesAreIncidentArray = indexOfItemInSortedArray(insertionIndex);

            if (indexOfItemInWhereEdgesAreIncidentArray != -1)
                indexOfFirstOcurrenceOfAVerticeIndex = indexOfItemInWhereEdgesAreIncidentArray;

            reorderedArray[insertionIndex] = indexOfFirstOcurrenceOfAVerticeIndex;
        }

        edgeEndVertices = reorderedArray;
    }

    @Override
    public void showArrays() {
        System.out.println("\n\tPREDECESSOR ADJACENCY ARRAYS\n");
        super.showAdjacencyArraysIncreasingTheirValuesByOne();
    }
}
