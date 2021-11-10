public class PredecessorAdjacencyArrays extends BaseAdjacencyArrays implements GraphRepresentation {
    private int[] reorderedArray;
    private int indexOfItemInEndPointsArray;
    private int indexOfFirstOcurrenceOfAVerticeIndex;

    public PredecessorAdjacencyArrays(Graph graph) {
        super(graph);
        reorderedArray = new int[graph.numberOfVertices + 1];

        orderAdjacencyArrays(WhichArrayToSort.END_ARRAY);
        reorderSortedArrayAndFillCorrespondingAttribute(graph);

        edgeEndPoints = reorderedArray;
    }

    private void reorderSortedArrayAndFillCorrespondingAttribute(Graph graph) {
        reorderedArray[graph.numberOfVertices] = graph.numberOfEdges;
        reorderedArray[0] = 0;

        for (int insertionIndex = 1; insertionIndex < graph.numberOfVertices; insertionIndex++) {
            indexOfItemInEndPointsArray = getIndexOfItemInToBeSortedArray(insertionIndex);

            if (indexOfItemInEndPointsArray != -1) {
                indexOfFirstOcurrenceOfAVerticeIndex = indexOfItemInEndPointsArray;
            }

            reorderedArray[insertionIndex] = indexOfFirstOcurrenceOfAVerticeIndex;
        }
    }

    @Override
    public void show() {
        System.out.println("\n\tPREDECESSOR ADJACENCY ARRAYS\n");
        super.showAdjacencyArraysIncreasingTheirValuesByOne();
    }
}
