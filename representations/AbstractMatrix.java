package representations;

public abstract class AbstractMatrix {
    protected final int[][] matrix;
    protected final int numberOfLines;
    protected final int numberOfColumns;

    protected AbstractMatrix(int numberOfLines, int numberOfColumns) {
        this.numberOfLines = numberOfLines;
        this.numberOfColumns = numberOfColumns;
        matrix = new int[numberOfLines][numberOfColumns];
    }
}