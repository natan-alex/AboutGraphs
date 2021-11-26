import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class FordFulkerson {
    private final Graph relatedGraph;
    private final Vertice source;
    private final Vertice sink;
    private FlowNetwork residualGraphNetwork;
    private List<List<Vertice>> disjointPaths;
    private DeepFirstSearch deepFirstSearch;
    private List<Vertice> pathFound;
    private List<FlowEdge> flowEdgesInThePath;
    private List<FlowEdge> flowEdgesInReversedDirectionInThePath;
    private int minimumCapacityBetweenEdges;

    public FordFulkerson(Graph graph, String source, String sink) throws IllegalArgumentException {
        relatedGraph = graph;

        throwExceptionIfSourceAndSinkAreEquals(source, sink);
        throwExceptionIfGraphIsInvalid();

        this.source = relatedGraph.getVerticeByName(source);
        this.sink = relatedGraph.getVerticeByName(sink);

        throwExceptionIfVerticeIsNull(this.source, source);
        throwExceptionIfVerticeIsNull(this.sink, sink);

        checkIfSourceAndSinkAreValid();
    }

    public List<List<Vertice>> computeMaximumFlowAndGetDisjointPaths() {
        residualGraphNetwork = new FlowNetwork(relatedGraph.stringRepresentation);
        disjointPaths = new ArrayList<>();
        deepFirstSearch = new DeepFirstSearch(residualGraphNetwork);
        pathFound = deepFirstSearch.getPathBetweenVertices(source, sink);

        while (pathFound != null && !pathFound.isEmpty() && !disjointPaths.contains(pathFound)) {
            flowEdgesInThePath = getFlowEdgesInThePath(pathFound);
            flowEdgesInReversedDirectionInThePath = getCorrespondingFlowEdgesInReversedDirectionAccordingToFlowEdgeList(
                    flowEdgesInThePath);

            minimumCapacityBetweenEdges = getMinimumCapacityInFlowEdgeList(flowEdgesInThePath);

            addFlowAndAdjustFlowsInFlowEdgesList(minimumCapacityBetweenEdges, flowEdgesInThePath);
            addFlowAndAdjustFlowsInFlowEdgesInReversedDirectionList(minimumCapacityBetweenEdges,
                    flowEdgesInReversedDirectionInThePath);

            disjointPaths.add(pathFound);
            removeFromResidualGraphTheEdgesInThePathThatHaveNoFlowGoing(flowEdgesInThePath);

            pathFound = deepFirstSearch.getPathBetweenVertices(source, sink);
        }

        return disjointPaths;
    }

    private void removeFromResidualGraphTheEdgesInThePathThatHaveNoFlowGoing(List<FlowEdge> path) {
        List<FlowEdge> currentFlowEdges = new ArrayList<>(residualGraphNetwork.flowEdges);

        for (FlowEdge flowEdge : currentFlowEdges) {
            if (path.contains(flowEdge) && flowEdge.howMuchFlowCanPass == 0) {
                residualGraphNetwork.flowEdges.remove(flowEdge);
            }
        }
    }

    private List<FlowEdge> getCorrespondingFlowEdgesInReversedDirectionAccordingToFlowEdgeList(List<FlowEdge> list) {
        List<FlowEdge> reversedFlowEdgesList = new ArrayList<>();

        for (FlowEdge edge : list) {
            reversedFlowEdgesList.add(residualGraphNetwork
                    .getDirectedEdgeInReversedDirectionWithThisVertices(edge.secondVertice, edge.firstVertice));
        }

        return reversedFlowEdgesList;
    }

    private void addFlowAndAdjustFlowsInFlowEdgesInReversedDirectionList(int flow, List<FlowEdge> list) {
        for (FlowEdge edge : list) {
            edge.howMuchFlowCanPass += flow;

            if (edge.howMuchFlowCanPass > edge.maximumCapacity) {
                edge.howMuchFlowCanPass = edge.maximumCapacity;
            }
        }
    }

    private void addFlowAndAdjustFlowsInFlowEdgesList(int flow, List<FlowEdge> list) {
        for (FlowEdge edge : list) {
            edge.howMuchFlowCanPass = edge.maximumCapacity - flow - edge.howMuchFlowCanPass;

            if (edge.howMuchFlowCanPass < 0) {
                edge.howMuchFlowCanPass = 0;
            }
        }
    }

    private int getMinimumCapacityInFlowEdgeList(List<FlowEdge> flowEdges) {
        return flowEdges.stream().mapToInt(edge -> edge.maximumCapacity).min().getAsInt();
    }

    private List<FlowEdge> getFlowEdgesInThePath(List<Vertice> path) {
        List<FlowEdge> flowEdges = new ArrayList<>();
        Vertice[] pathArray = new Vertice[path.size()];
        int currentVerticeIndex = 0;
        FlowEdge flowEdgeFound;

        path.toArray(pathArray);

        while (currentVerticeIndex + 1 < pathArray.length) {
            flowEdgeFound = residualGraphNetwork.getDirectedEdgeWithThisVertices(pathArray[currentVerticeIndex],
                    pathArray[currentVerticeIndex + 1]);
            flowEdges.add(flowEdgeFound);
            currentVerticeIndex++;
        }

        return flowEdges;
    }

    private void throwExceptionIfSourceAndSinkAreEquals(String first, String second) {
        if (first.compareToIgnoreCase(second) == 0) {
            throw new IllegalArgumentException("Source and sink vertices can not be the same vertice.");
        }
    }

    private void checkIfSourceAndSinkAreValid() throws IllegalArgumentException {
        if (Pattern.compile(",\\s*" + source.name).matcher(relatedGraph.stringRepresentation).find()) {
            throw new IllegalArgumentException("The source can not have edges coming to it.");
        }

        if (Pattern.compile("\\(" + sink.name).matcher(relatedGraph.stringRepresentation).find()) {
            throw new IllegalArgumentException("The sink can not have edges leaving from it.");
        }
    }

    private void throwExceptionIfGraphIsInvalid() throws IllegalArgumentException {
        if (relatedGraph.type != GraphTypes.DIRECTED_AND_PONDERED) {
            throw new IllegalArgumentException(
                    "Can not execute ford fulkerson algorithm in an unpondered or undirected graph.");
        }
    }

    private void throwExceptionIfVerticeIsNull(Vertice verticeFound, String verticeName)
            throws IllegalArgumentException {
        if (verticeFound == null) {
            throw new IllegalArgumentException(getNotFoundExceptionMessage(verticeName));
        }
    }

    private String getNotFoundExceptionMessage(String verticeName) {
        return "The vertice " + verticeName + " is not in the vertice set." + "\nThe vertice set is: "
                + relatedGraph.vertices;
    }

}
