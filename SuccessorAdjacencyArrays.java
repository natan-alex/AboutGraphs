public class SuccessorAdjacencyArrays extends BaseAdjacencyArrays {
    private int[] reorderedArray;
    private int indexOfItemInStartPointsArray;
    private int indexOfFirstOcurrenceOfAVerticeIndex;

    public SuccessorAdjacencyArrays(Graph graph) {
        super(graph);
        reorderedArray = new int[graph.numberOfVertices + 1];

        indexOfFirstOcurrenceOfAVerticeIndex = graph.numberOfEdges;

        orderAdjacencyArrays(WhichArrayToSort.START_ARRAY);
        reorderSortedArrayAndFillCorrespondingAttribute(graph);

        edgeStartPoints = reorderedArray;
    }

    private void reorderSortedArrayAndFillCorrespondingAttribute(Graph graph) {
        reorderedArray[graph.numberOfVertices] = graph.numberOfEdges;
        reorderedArray[0] = 0;

        for (int insertionIndex = graph.numberOfVertices - 1; insertionIndex > 0; insertionIndex--) {
            indexOfItemInStartPointsArray = getIndexOfItemInToBeSortedArray(insertionIndex);

            if (indexOfItemInStartPointsArray != -1) {
                indexOfFirstOcurrenceOfAVerticeIndex = indexOfItemInStartPointsArray;
            }

            reorderedArray[insertionIndex] = indexOfFirstOcurrenceOfAVerticeIndex;
        }
    }
}
