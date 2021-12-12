package searches;

import java.util.Comparator;

import core.*;
import core.abstractions.AbstractEdge;
import core.abstractions.AbstractTypedGraph;
import core.abstractions.AbstractValuedEdge;
import core.abstractions.AbstractVertice;

public final class VerticeComparatorInAStarSearch implements Comparator<AbstractVertice> {
    private final AbstractVertice startingPointVertice;
    private final AbstractTypedGraph typedGraph;
    private final GraphHeuristics graphHeuristics;

    public VerticeComparatorInAStarSearch(
            AbstractVertice startingPointVertice,
            AbstractTypedGraph typedGraph,
            GraphHeuristics graphHeuristics
    ) {
        this.startingPointVertice = startingPointVertice;
        this.typedGraph = typedGraph;
        this.graphHeuristics = graphHeuristics;
    }

    @Override
    public int compare(AbstractVertice arg0, AbstractVertice arg1) {
        int evaluationFunctionValueForArg0 = getEvaluationFunctionValueForVertices(startingPointVertice, arg0);
        int evaluationFunctionValueForArg1 = getEvaluationFunctionValueForVertices(startingPointVertice, arg1);

        return Integer.compare(evaluationFunctionValueForArg0, evaluationFunctionValueForArg1);
    }

    private int getEvaluationFunctionValueForVertices(
            AbstractVertice firstVertice, AbstractVertice secondVertice
    ) {
        AbstractValuedEdge edgeThatContainsTheVertices =
                (AbstractValuedEdge) typedGraph.getEdge(firstVertice, secondVertice);
        int evaluationFunctionValue = Integer.MAX_VALUE;

        if (edgeThatContainsTheVertices != null) {
            evaluationFunctionValue =
                    edgeThatContainsTheVertices.getValue() +
                    graphHeuristics.getVerticesAndTheirHeuristics().get(secondVertice);
        }

        return evaluationFunctionValue;
    }
}