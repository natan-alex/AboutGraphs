package core;

public enum GraphTypes {
    DIRECTED_AND_PONDERED(true, true, "directed and pondered"),
    DIRECTED_AND_UNPONDERED(true, false, "directed and unpondered"),
    UNDIRECTED_AND_PONDERED(false, true, "undirected and pondered"),
    UNDIRECTED_AND_UNPONDERED(false, false, "undirected and unpondered");

    private final boolean isDirected;
    private final boolean isPondered;
    private final String representation;

    GraphTypes(boolean isDirected, boolean isPondered, String representation) {
        this.isDirected = isDirected;
        this.isPondered = isPondered;
        this.representation = representation;
    }

    public boolean isPondered() {
        return isPondered;
    }

    public boolean isDirected() {
        return isDirected;
    }

    public String getRepresentation() {
        return representation;
    }
}
