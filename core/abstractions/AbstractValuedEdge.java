package core.abstractions;

public abstract class AbstractValuedEdge extends AbstractEdge {
    private final int value;

    public AbstractValuedEdge(String edgeRepresentation) {
        super(edgeRepresentation);
        value = getEdgeValueFromRepresentation();
    }

    protected int getEdgeValueFromRepresentation() {
        if (countCommasInRepresentation() == 2) {
            int indexOfLastComma = getRepresentation().lastIndexOf(',');
            int indexOfClosingParenthesis = getRepresentation().lastIndexOf(')');
            return Integer.parseInt(getRepresentation().substring(indexOfLastComma + 1, indexOfClosingParenthesis).trim());
        } else {
            return 0;
        }
    }

    private int countCommasInRepresentation() {
        int count = 0;

        for (char c : getRepresentation().toCharArray()) {
            if (c == ',')
                count++;
        }

        return count;
    }

    public int getValue() {
        return value;
    }
}
