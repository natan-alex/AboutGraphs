package core;

import core.abstractions.AbstractEdge;
import core.abstractions.AbstractGraph;
import core.abstractions.AbstractVertice;
import core.validators.EdgeValidator;
import core.validators.GraphValidator;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.*;
import java.util.ArrayList;
import java.util.List;

public class Graph extends AbstractGraph {
    public GraphTypes type;

    @Override
    protected void validateGraphRepresentation() {
        type = GraphValidator.whichTypeOfGraphIs(getRepresentation());
    }

    public Graph(String graphRepresentation) throws IllegalArgumentException {
        super(graphRepresentation);
    }

    @Override
    protected void fillEdgeList() {
        Matcher matcher = EdgeValidator.PATTERN_TO_VALIDATE_AN_EDGE.matcher(getRepresentation());
        List<AbstractEdge> edges = new ArrayList<>();

        while (matcher.find()) {
            edges.add(new Edge(matcher.group()));
        }

        setEdges(edges);
    }
}