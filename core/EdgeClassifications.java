package AboutGraphs.core;

public enum EdgeClassifications {
    TREE("Tree"), CROSSING("Crossing"), RETURN("Return"), ADVANCE("Advance");

    String name;

    private EdgeClassifications(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
