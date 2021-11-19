import java.util.Comparator;

public final class VerticeComparatorInBFS implements Comparator<Vertice> {
    Vertice startingPointVertice;
    Graph graph;

    public VerticeComparatorInBFS(Vertice startingPointVertice, Graph graph) {
        this.startingPointVertice = startingPointVertice;
        this.graph = graph;
    }

    @Override
    public int compare(Vertice arg0, Vertice arg1) {
        float evaluationFunctionValueForArg0 = getEvaluationFunctionValueForVertices(startingPointVertice, arg0);
        float evaluationFunctionValueForArg1 = getEvaluationFunctionValueForVertices(startingPointVertice, arg1);

        return Float.compare(evaluationFunctionValueForArg0, evaluationFunctionValueForArg1);
    }

    private float getEvaluationFunctionValueForVertices(Vertice firstVertice, Vertice secondVertice) {
        Edge edgeThatContainsTheVertices = graph.getEdgeThatContainsThisVertices(firstVertice, secondVertice);
        float evaluationFunctionValue = Float.MAX_VALUE;

        if (edgeThatContainsTheVertices != null) {
            evaluationFunctionValue = edgeThatContainsTheVertices.value;
        }

        return evaluationFunctionValue;
    }
}