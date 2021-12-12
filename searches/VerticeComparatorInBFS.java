package searches;

import java.util.Comparator;

import core.*;
import core.abstractions.AbstractEdge;
import core.abstractions.AbstractTypedGraph;
import core.abstractions.AbstractValuedEdge;
import core.abstractions.AbstractVertice;

public final class VerticeComparatorInBFS implements Comparator<AbstractVertice> {
    private final AbstractVertice startingPointVertice;
    private final AbstractTypedGraph typedGraph;

    public VerticeComparatorInBFS(
            AbstractVertice startingPointVertice, AbstractTypedGraph typedGraph
    ) {
        this.startingPointVertice = startingPointVertice;
        this.typedGraph = typedGraph;
    }

    @Override
    public int compare(AbstractVertice arg0, AbstractVertice arg1) {
        int evaluationFunctionValueForArg0 = getEvaluationFunctionValueForVertices(startingPointVertice, arg0);
        int evaluationFunctionValueForArg1 = getEvaluationFunctionValueForVertices(startingPointVertice, arg1);

        return Integer.compare(evaluationFunctionValueForArg0, evaluationFunctionValueForArg1);
    }

    private int getEvaluationFunctionValueForVertices(AbstractVertice firstVertice, AbstractVertice secondVertice) {
        AbstractValuedEdge edgeThatContainsTheVertices =
                (AbstractValuedEdge) typedGraph.getEdge(firstVertice, secondVertice);
        int evaluationFunctionValue = Integer.MAX_VALUE;

        if (edgeThatContainsTheVertices != null) {
            evaluationFunctionValue = edgeThatContainsTheVertices.getValue();
        }

        return evaluationFunctionValue;
    }
}