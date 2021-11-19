import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GraphHeuristics {
    private final Graph relatedGraph;
    private final String heuristicsStringRepresentation;
    public final Map<Vertice, Float> verticesAndTheirHeuristics;

    public GraphHeuristics(Graph graph, String heuristicsRepresentation) {
        relatedGraph = graph;

        verticesAndTheirHeuristics = new LinkedHashMap<>(relatedGraph.numberOfVertices);
        heuristicsStringRepresentation = heuristicsRepresentation;

        checkIfHeuristicsRepresentationIsValid(heuristicsRepresentation);

        fillHeuristicsForEachVertice();
    }

    private static Pattern PATTERN_TO_VALIDATE_THE_HEURISTICS = Pattern.compile(
            "^\\s*\\[\\s*(?:\\s*\\w+\\s*:\\s*\\d+(?:\\.\\d+)?\\s*,)*\\s*\\w+\\s*:\\s*\\d+(?:\\.\\d+)?\\s*\\]\\s*",
            Pattern.MULTILINE);

    private void checkIfHeuristicsRepresentationIsValid(String heuristicsRepresentation)
            throws IllegalArgumentException {
        Matcher matcher = PATTERN_TO_VALIDATE_THE_HEURISTICS.matcher(heuristicsRepresentation);

        if (!matcher.matches())
            throw new IllegalArgumentException("The heuristic " + heuristicsRepresentation
                    + " is not a valid heuristic. A valid heuristic is something like [ a: 1, b: 2.3, c: 3.0 ]"
                    + " where the vertice is on the left of the : and the heuristic value is on the rigth.");
    }

    private String[] splitHeuristicRepresentation() {
        String heuristicsWithoutBracket = heuristicsStringRepresentation.substring(1,
                heuristicsStringRepresentation.length() - 1);
        return heuristicsWithoutBracket.split(",");
    }

    private void fillHeuristicsForEachVertice() throws IllegalArgumentException {
        Vertice verticeFound;
        String[] partsOfHeuristics = splitHeuristicRepresentation();
        String[] verticeNameAndHeuristicValue;

        for (String part : partsOfHeuristics) {
            verticeNameAndHeuristicValue = splitPartOfHeuristics(part);

            verticeFound = relatedGraph.getTheVerticeWithThisName(verticeNameAndHeuristicValue[0]);

            throwsExceptionIfVerticeIsNull(verticeFound, verticeNameAndHeuristicValue[0]);

            verticesAndTheirHeuristics.put(verticeFound, Float.parseFloat(verticeNameAndHeuristicValue[1]));
        }

        for (Vertice vertice : relatedGraph.vertices) {
            verticesAndTheirHeuristics.putIfAbsent(vertice, 0F);
        }
    }

    private String[] splitPartOfHeuristics(String partOfHeuristic) {
        String[] verticeNameAndHeuristicValue = partOfHeuristic.trim().split(":");
        verticeNameAndHeuristicValue[0] = verticeNameAndHeuristicValue[0].trim();
        verticeNameAndHeuristicValue[1] = verticeNameAndHeuristicValue[1].trim();
        return verticeNameAndHeuristicValue;
    }

    private void throwsExceptionIfVerticeIsNull(Vertice verticeFound, String verticeName)
            throws IllegalArgumentException {
        if (verticeFound == null) {
            throw new IllegalArgumentException("The vertice " + verticeName + " is not in the vertice set."
                    + " The vertice set is: " + relatedGraph.vertices);
        }
    }
}
