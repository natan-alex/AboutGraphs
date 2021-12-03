package core;

import core.abstractions.AbstractEdge;
import core.abstractions.AbstractTypedGraph;
import core.abstractions.AbstractVertice;
import core.validators.EdgeValidator;
import core.validators.GraphValidator;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.*;
import java.util.ArrayList;
import java.util.List;

public class TypedGraph extends AbstractTypedGraph {
    @Override
    protected void validateGraphRepresentation() throws IllegalArgumentException {
        GraphValidator.validateGraphRepresentation(getRepresentation());
    }

    @Override
    protected void checkIfTypeMatchesTheRepresentation() throws IllegalArgumentException {
        GraphTypes type = GraphValidator.whichTypeOfGraphIs(getRepresentation());

        if (type != getType()) {
            throw new IllegalArgumentException("The representation seems to represent a " + type.getRepresentation() +
                    "that is different from a " + getType().getRepresentation() + " typedGraph");
        }
    }

    @Override
    protected AbstractEdge[] getEdgesFromRepresentation() {
        Matcher matcher = EdgeValidator.PATTERN_TO_VALIDATE_AN_EDGE.matcher(getRepresentation());
        List<AbstractEdge> edges = new ArrayList<>();

        while (matcher.find()) {
            edges.add(new ValuedEdge(matcher.group()));
        }

        AbstractEdge[] edgesArray = new AbstractEdge[edges.size()];
        return edges.toArray(edgesArray);
    }

    public TypedGraph(String graphRepresentation, GraphTypes type) throws IllegalArgumentException {
        super(graphRepresentation, type);
    }
}