package representations;

import core.abstractions.AbstractEdge;
import core.abstractions.AbstractGraph;

public abstract class AbstractAdjacencyArrays {
    private int[] edgeStartVertices;
    private int[] edgeEndVertices;

    protected abstract void sortAdjacencyArraysTogether(SortBehaviour sortBehaviour);
    protected abstract void reorderTheMainSortedArray(AbstractGraph relatedGraph);
    protected abstract int[] getSortedArray();

    protected AbstractAdjacencyArrays(AbstractGraph graph, SortBehaviour sortBehaviour) {
        edgeStartVertices = new int[graph.getNumberOfEdges()];
        edgeEndVertices = new int[graph.getNumberOfEdges()];

        initializeArrays(graph);
        sortAdjacencyArraysTogether(sortBehaviour);
        reorderTheMainSortedArray(graph);
    }

    protected int[] getEdgeStartVertices() {
        return edgeStartVertices;
    }

    protected void setEdgeStartVertices(int[] edgeStartVertices) {
        this.edgeStartVertices = edgeStartVertices;
    }

    protected int[] getEdgeEndVertices() {
        return edgeEndVertices;
    }

    protected void setEdgeEndVertices(int[] edgeEndVertices) {
        this.edgeEndVertices = edgeEndVertices;
    }

    private void initializeArrays(AbstractGraph relatedGraph) {
        int insertionIndex = 0;
        int indexOfFirstVertice, indexOfSecondVertice;

        for (AbstractEdge edge : relatedGraph.getEdges()) {
            indexOfFirstVertice = relatedGraph.indexOfVertice(edge.getFirstVertice());
            indexOfSecondVertice = relatedGraph.indexOfVertice(edge.getSecondVertice());

            edgeStartVertices[insertionIndex] = indexOfFirstVertice;
            edgeEndVertices[insertionIndex] = indexOfSecondVertice;

            insertionIndex++;
        }
    }

    protected int indexOfItemInSortedArray(int item) {
        int[] sortedArray = getSortedArray();

        for (int i = 0; i < sortedArray.length; i++) {
            if (sortedArray[i] == item) {
                return i;
            }
        }

        return -1;
    }

    protected void showAdjacencyArraysIncreasingTheirValuesByOne() {
        System.out.print("First array of indices: [ ");

        for (int index : edgeStartVertices)
            System.out.print((index + 1) + " ");

        System.out.println("]");

        System.out.print("Second array of indices: [ ");

        for (int index : edgeEndVertices)
            System.out.print((index + 1) + " ");

        System.out.println("]");
    }
}
