package aboutGraphs.searches;

import java.util.Comparator;

import aboutGraphs.core.*;

public final class VerticeComparatorInAStarSearch implements Comparator<Vertice> {
    Vertice startingPointVertice;
    Graph graph;
    GraphHeuristics graphHeuristics;

    public VerticeComparatorInAStarSearch(Vertice startingPointVertice, Graph graph, GraphHeuristics graphHeuristics) {
        this.startingPointVertice = startingPointVertice;
        this.graph = graph;
        this.graphHeuristics = graphHeuristics;
    }

    @Override
    public int compare(Vertice arg0, Vertice arg1) {
        int evaluationFunctionValueForArg0 = getEvaluationFunctionValueForVertices(startingPointVertice, arg0);
        int evaluationFunctionValueForArg1 = getEvaluationFunctionValueForVertices(startingPointVertice, arg1);

        return Integer.compare(evaluationFunctionValueForArg0, evaluationFunctionValueForArg1);
    }

    private int getEvaluationFunctionValueForVertices(Vertice firstVertice, Vertice secondVertice) {
        Edge edgeThatContainsTheVertices = graph.getDirectedEdgeWithThisVertices(firstVertice, secondVertice);
        int evaluationFunctionValue = Integer.MAX_VALUE;

        if (edgeThatContainsTheVertices != null) {
            evaluationFunctionValue = edgeThatContainsTheVertices.value
                    + graphHeuristics.verticesAndTheirHeuristics.get(secondVertice);
        }

        return evaluationFunctionValue;
    }
}