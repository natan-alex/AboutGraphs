package aboutGraphs.core;

public enum GraphTypes {
    DIRECTED_AND_PONDERED(true, true), DIRECTED_AND_UNPONDERED(true, false), UNDIRECTED_AND_PONDERED(false, true),
    UNDIRECTED_AND_UNPONDERED(false, false);

    public boolean isPondered;
    public boolean isDirected;

    private GraphTypes(boolean isDirected, boolean isPondered) {
        this.isDirected = isDirected;
        this.isPondered = isPondered;
    }
}
