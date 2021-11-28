<<<<<<< HEAD:representations/BaseMatrix.java
package AboutGraphs.representations;

import AboutGraphs.core.*;

public abstract class BaseMatrix {
    public final int[][] matrix;
    public final int numberOfLines;
    public final int numberOfColumns;

    protected BaseMatrix(int numberOfLines, int numberOfColumns) {
        this.numberOfLines = numberOfLines;
        this.numberOfColumns = numberOfColumns;
        matrix = new int[numberOfLines][numberOfColumns];
    }
=======
package AboutGraphs.representations;

import AboutGraphs.core.*;

public abstract class BaseMatrix {
    public final int[][] matrix;
    public final int numberOfLines;
    public final int numberOfColumns;

    protected BaseMatrix(int numberOfLines, int numberOfColumns) {
        this.numberOfLines = numberOfLines;
        this.numberOfColumns = numberOfColumns;
        matrix = new int[numberOfLines][numberOfColumns];
    }
>>>>>>> 1b387950559dd2ef737390340f12853d43947502:graph_representations/BaseMatrix.java
}