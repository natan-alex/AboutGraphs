package AboutGraphs.representations;

import AboutGraphs.core.*;

public class PredecessorAdjacencyArrays extends BaseAdjacencyArrays {
    public PredecessorAdjacencyArrays(Graph graph) {
        super(graph);

        orderAdjacencyArrays(verticesWhereTheEdgeIsIncident, verticesWhereTheEdgeComesFrom);
        verticesWhereTheEdgeIsIncident = getReorderedSortedArray(graph);
    }

    private int[] getReorderedSortedArray(Graph graph) {
        int[] reorderedArray = new int[graph.numberOfVertices + 1];
        int indexOfFirstOcurrenceOfAVerticeIndex = graph.numberOfEdges;
        int indexOfItemInWhereEdgesAreIncidentArray;

        reorderedArray[graph.numberOfVertices] = graph.numberOfEdges;
        reorderedArray[0] = 0;

        for (int insertionIndex = 1; insertionIndex < graph.numberOfVertices; insertionIndex++) {
            indexOfItemInWhereEdgesAreIncidentArray = getIndexOfItemInToBeSortedArray(insertionIndex,
                    verticesWhereTheEdgeIsIncident);

            if (indexOfItemInWhereEdgesAreIncidentArray != -1) {
                indexOfFirstOcurrenceOfAVerticeIndex = indexOfItemInWhereEdgesAreIncidentArray;
            }

            reorderedArray[insertionIndex] = indexOfFirstOcurrenceOfAVerticeIndex;
        }

        return reorderedArray;
    }

    public void showPredecessorAdjacencyArrays() {
        System.out.println("\n\tPREDECESSOR ADJACENCY ARRAYS\n");
        super.showAdjacencyArraysIncreasingTheirValuesByOne();
    }
}
