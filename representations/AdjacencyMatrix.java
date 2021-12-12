package representations;

import core.abstractions.AbstractEdge;
import core.abstractions.AbstractGraph;
import core.abstractions.AbstractVertice;

public class AdjacencyMatrix extends AbstractMatrix {

    public AdjacencyMatrix(AbstractGraph graph) {
        super(graph);
    }

    @Override
    protected int getNumberOfColumns() {
        return relatedGraph.getNumberOfVertices();
    }

    @Override
    protected int getNumberOfLines() {
        return relatedGraph.getNumberOfVertices();
    }

    @Override
    protected void fillMatrix() {
        int currentLine = 0;

        for (AbstractVertice vertice : relatedGraph.getVertices()) {
            initializeTheItemsOfTheMatrixLine(currentLine);

            fillMatrixLineGivenTheCurrentVertice(currentLine, vertice);

            currentLine++;
        }
    }

    private void initializeTheItemsOfTheMatrixLine(int line) {
        for (int currentColumn = 0; currentColumn < getNumberOfColumns(); currentColumn++)
            matrix[line][currentColumn] = 0;
    }

    private void fillMatrixLineGivenTheCurrentVertice(int currentLine, AbstractVertice currentVertice) {
        int indexOfSecondVertice = 0;

        for (AbstractEdge valuedEdge : relatedGraph.getEdges()) {
            if (currentVertice.equals(valuedEdge.getFirstVertice())) {
                indexOfSecondVertice = relatedGraph.indexOfVertice(valuedEdge.getSecondVertice());
                matrix[currentLine][indexOfSecondVertice] = 1;
            }
        }
    }

    public void showAdjacencyMatrix() {
        System.out.println("\n\tADJACENCY MATRIX\n");
        int currentLine = 0;

        showVerticesSeparatedByTabs();

        for (AbstractVertice vertice : relatedGraph.getVertices()) {
            System.out.print(vertice.getRepresentation() + "\t");

            showMatrixLineItemsSeparatedByTabs(currentLine);

            System.out.println();
            currentLine++;
        }
    }

    private void showVerticesSeparatedByTabs() {
        System.out.print("\t");

        for (AbstractVertice vertice : relatedGraph.getVertices()) {
            System.out.print(vertice.getRepresentation() + "\t");
        }

        System.out.println();
    }

    private void showMatrixLineItemsSeparatedByTabs(int currentLine) {
        for (int currentColumn = 0; currentColumn < getNumberOfColumns(); currentColumn++) {
            System.out.print(matrix[currentLine][currentColumn] + "\t");
        }
    }
}