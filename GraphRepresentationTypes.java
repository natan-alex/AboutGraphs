public enum GraphRepresentationTypes {
    DIRECTED_AND_PONDERED(true, true), DIRECTED_AND_UNPONDERED(true, false), UNDIRECTED_AND_PONDERED(false, true),
    UNDIRECTED_AND_UNPONDERED(false, false);

    boolean isPondered;
    boolean isDirected;

    private GraphRepresentationTypes(boolean isDirected, boolean isPondered) {
        this.isDirected = isDirected;
        this.isPondered = isPondered;
    }

    public boolean isGraphPondered() {
        return isPondered;
    }

    public boolean isGraphDirected() {
        return isDirected;
    }
}
