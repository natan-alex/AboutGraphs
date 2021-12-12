package searches;

public enum EdgeClassifications {
    TREE("Tree"), CROSSING("Crossing"), RETURN("Return"), ADVANCE("Advance");

    String name;

    EdgeClassifications(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
