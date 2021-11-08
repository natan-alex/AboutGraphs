import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GraphHeuristics {
    private final Graph relatedGraph;
    public final Map<Vertice, Float> verticesAndTheirHeuristics;

    public GraphHeuristics(Graph graph, String heuristicsRepresentation) {
        relatedGraph = graph;

        verticesAndTheirHeuristics = new LinkedHashMap<>(relatedGraph.numberOfVertices);

        checkIfHeuristicsRepresentationIsValid(heuristicsRepresentation);

        String[] partsOfHeuristics = heuristicsRepresentation.substring(1, heuristicsRepresentation.length() - 1)
                .split(",");
        
        iterateOverPartsOfHeuristicsRepresentationAndFillCorrespondingAttribute(partsOfHeuristics);
    }

    private static Pattern PATTERN_TO_VALIDATE_THE_HEURISTICS = Pattern.compile(
            "^\\s*\\[\\s*(?:\\s*\\w+\\s*:\\s*\\d+(?:\\.\\d+)?\\s*,)*\\s*\\w+\\s*:\\s*\\d+(?:\\.\\d+)?\\s*\\]\\s*",
            Pattern.MULTILINE);

    private void checkIfHeuristicsRepresentationIsValid(String heuristicsRepresentation) throws IllegalArgumentException {
        if (heuristicsRepresentation == null || heuristicsRepresentation.isEmpty())
            return;

        Matcher matcher = PATTERN_TO_VALIDATE_THE_HEURISTICS.matcher(heuristicsRepresentation);

        if (!matcher.matches()) 
            throw new IllegalArgumentException("The heuristic " + heuristicsRepresentation
            + " is not a valid heuristic. A valid heuristic is something like [ a: 1, b: 2.3, c: 3.0 ]" 
            + " where the vertice is on the left of the : and the heuristic value is on the rigth.");
    }

    private void iterateOverPartsOfHeuristicsRepresentationAndFillCorrespondingAttribute(String[] partsOfHeuristics) 
        throws IllegalArgumentException {
        String[] verticeNameAndHeuristicValue;
        Optional<Vertice> verticeFound;
        int currentVerticeIndex;

        for (Vertice vertice : relatedGraph.verticesAndTheirIndices.keySet()) {
            currentVerticeIndex = relatedGraph.verticesAndTheirIndices.get(vertice);
            if (currentVerticeIndex >= partsOfHeuristics.length) {
                verticesAndTheirHeuristics.put(vertice, 0F);
            } else {
                verticeNameAndHeuristicValue = partsOfHeuristics[currentVerticeIndex].trim().split(":");
                verticeNameAndHeuristicValue[0] = verticeNameAndHeuristicValue[0].trim();
                verticeNameAndHeuristicValue[1] = verticeNameAndHeuristicValue[1].trim();
                verticeFound = getTheVerticeWithThisName(verticeNameAndHeuristicValue[0]);

                if (verticeFound.isEmpty()) {
                    throw new IllegalArgumentException("The vertice " + verticeNameAndHeuristicValue[0]
                            + " is not in the vertice set." + " The vertice set is: " + relatedGraph.verticesAndTheirIndices.keySet());
                }

                verticesAndTheirHeuristics.put(verticeFound.get(), Float.parseFloat(verticeNameAndHeuristicValue[1]));
            }
        }
    }

    private Optional<Vertice> getTheVerticeWithThisName(String name) {
        return relatedGraph.verticesAndTheirIndices.keySet().stream()
                .filter(vertice -> vertice.name.compareTo(name) == 0).findFirst();
    }
}
