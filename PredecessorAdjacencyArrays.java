public class PredecessorAdjacencyArrays extends AdjacencyArrays {
    public PredecessorAdjacencyArrays(Graph graph) {
        super(graph);
        orderAdjacencyArrays(WhichArrayToSort.END_ARRAY);
        edgeEndPoints = reorderAndReturnPredecessorAdjacencyArrayForGraph(graph);
    }

    private int[] reorderAndReturnPredecessorAdjacencyArrayForGraph(Graph graph) {
        int[] reorderedArray = new int[graph.numberOfVertices + 1];
        int indexOfWhereInsert = 0, indexOfFirstOcurrenceOfAVerticeIndex = 0;

        reorderedArray[graph.numberOfVertices] = graph.numberOfEdges;
        reorderedArray[0] = 0;

        for (int whereInsertInReorderedArray = 1; whereInsertInReorderedArray < graph.numberOfVertices; whereInsertInReorderedArray++) {

            for (int i = 0; i < edgeEndPoints.length; i++) {
                if (edgeEndPoints[i] == whereInsertInReorderedArray) {
                    indexOfWhereInsert = i;
                    i = edgeEndPoints.length;
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
