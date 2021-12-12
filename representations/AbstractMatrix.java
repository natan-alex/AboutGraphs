package representations;

import core.abstractions.AbstractGraph;

public abstract class AbstractMatrix {
    protected final int[][] matrix;
    protected final AbstractGraph relatedGraph;

    protected abstract int getNumberOfColumns();
    protected abstract int getNumberOfLines();
    protected abstract void fillMatrix();

    protected AbstractMatrix(AbstractGraph relatedGraph) {
        this.relatedGraph = relatedGraph;
        matrix = new int[getNumberOfLines()][getNumberOfColumns()];

        fillMatrix();
    }
}