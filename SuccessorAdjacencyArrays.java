public class SuccessorAdjacencyArrays extends AdjacencyArrays {
    public SuccessorAdjacencyArrays(Graph graph) {
        super(graph);
        orderAdjacencyArrays(WhichArrayToSort.START_ARRAY);
        edgeStartPoints = reorderAndReturnSuccessorAdjacencyArrayForGraph(graph);
    }

    private int[] reorderAndReturnSuccessorAdjacencyArrayForGraph(Graph graph) {
        int[] reorderedArray = new int[graph.numberOfVertices + 1];
        int indexOfWhereInsert = 0, indexOfFirstOcurrenceOfAVerticeIndex = graph.numberOfEdges;

        reorderedArray[graph.numberOfVertices] = graph.numberOfEdges;
        reorderedArray[0] = 0;

        for (int whereInsertInReorderedArray = graph.numberOfVertices
                - 1; whereInsertInReorderedArray > 0; whereInsertInReorderedArray--) {

            for (int i = 0; i < edgeStartPoints.length; i++) {
                if (edgeStartPoints[i] == whereInsertInReorderedArray) {
                    indexOfWhereInsert = i;
                    i = edgeStartPoints.length;
                }
            }

            if (indexOfWhereInsert != -1) {
                indexOfFirstOcurrenceOfAVerticeIndex = indexOfWhereInsert;
            }

            reorderedArray[whereInsertInReorderedArray] = indexOfFirstOcurrenceOfAVerticeIndex;
        }

        return reorderedArray;
    }
}
