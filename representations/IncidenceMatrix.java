package representations;

import core.abstractions.AbstractEdge;
import core.abstractions.AbstractGraph;
import core.abstractions.AbstractVertice;

public class IncidenceMatrix extends AbstractMatrix {
    public IncidenceMatrix(AbstractGraph graph) {
        super(graph);
    }

    @Override
    protected int getNumberOfColumns() {
        return relatedGraph.getNumberOfEdges();
    }

    @Override
    protected int getNumberOfLines() {
        return relatedGraph.getNumberOfVertices();
    }

    @Override
    protected void fillMatrix() {
        int currentLine = 0;

        for (AbstractVertice vertice : relatedGraph.getVertices()) {
            fillMatrixLineGivenTheCurrentVertice(currentLine, vertice);

            currentLine++;
        }
    }

    private void fillMatrixLineGivenTheCurrentVertice(int currentLine, AbstractVertice currentVertice) {
        int currentColumn = 0;
        int indexOfSecondVertice;

        for (AbstractEdge edge : relatedGraph.getEdges()) {
            if (currentVertice.equals(edge.getFirstVertice())) {
                indexOfSecondVertice = relatedGraph.indexOfVertice(edge.getSecondVertice());
                matrix[currentLine][currentColumn] = 1;
                matrix[indexOfSecondVertice][currentColumn] = -1;
            } else if (matrix[currentLine][currentColumn] != -1) {
                matrix[currentLine][currentColumn] = 0;
            }

            currentColumn++;
        }
    }

    public void showIncidenceMatrix() {
        System.out.println("\n\tINCIDENCE MATRIX\n");
        int currentLine = 0;

        showEdgeNumbersSeparatedByTabs();

        for (AbstractVertice vertice : relatedGraph.getVertices()) {
            System.out.print(vertice.getRepresentation() + "\t");

            showCurrentLineItems(currentLine);

            System.out.println();

            currentLine++;
        }
    }

    private void showEdgeNumbersSeparatedByTabs() {
        System.out.print("\t");

        for (int i = 1; i <= relatedGraph.getNumberOfEdges(); i++)
            System.out.print("edge" + i + "\t");

        System.out.println();
    }

    private void showCurrentLineItems(int currentLine) {
        int item;

        for (int currentColumn = 0; currentColumn < getNumberOfColumns(); currentColumn++) {
            item = matrix[currentLine][currentColumn];

            if (item == 1)
                System.out.print("+");
            else if (item == 0)
                System.out.print(" ");

            System.out.print(item + "\t\t");
        }
    }
}