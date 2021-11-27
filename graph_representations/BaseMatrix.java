package aboutGraphs.representations;

import aboutGraphs.core.*;

public abstract class BaseMatrix {
    public final int[][] matrix;
    public final int numberOfLines;
    public final int numberOfColumns;

    protected BaseMatrix(int numberOfLines, int numberOfColumns) {
        this.numberOfLines = numberOfLines;
        this.numberOfColumns = numberOfColumns;
        matrix = new int[numberOfLines][numberOfColumns];
    }
}