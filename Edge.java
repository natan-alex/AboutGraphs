import java.util.regex.*;

public class Edge {
    private String string_representation;
    private String first_vertice;
    private String second_vertice;
    private int value;
    private boolean is_directed;
    private boolean is_pondered;

    public static final Edge EMPTY_EDGE = new Edge();
    public static final Pattern PATTERN_TO_VALIDATE_AN_EDGE = Pattern
            .compile("[(|{]\\s*\\w+\\s*,(\\s*\\w+\\s*,)?\\s*\\w+\\s*[)|}]");

    public boolean is_directed() {
        return is_directed;
    }

    public boolean is_pondered() {
        return is_pondered;
    }

    public String get_string_representation() {
        return string_representation;
    }

    public int get_value() {
        return value;
    }

    public String get_first_vertice() {
        return first_vertice;
    }

    public String get_second_vertice() {
        return second_vertice;
    }

    public static Edge from_string(String edge_representation) {
        Matcher matcher = PATTERN_TO_VALIDATE_AN_EDGE.matcher(edge_representation);

        if (!matcher.matches()) {
            return EMPTY_EDGE;
        }

        Edge edge = new Edge();
        edge.string_representation = edge_representation.trim();

        if (!edge.is_edge_enclosed_correctly()) {
            return EMPTY_EDGE;
        }

        edge.fill_properties_of_the_edge();
        return edge;
    }

    private boolean is_edge_enclosed_correctly() {
        char opening_char = string_representation.charAt(0);
        char closing_char = string_representation.charAt(string_representation.length() - 1);

        return ((opening_char == '(' && closing_char == ')') || (opening_char == '{' && closing_char == '}'));
    }

    private void fill_properties_of_the_edge() {
        String[] parts_of_string_representation = string_representation.split(",");

        if (parts_of_string_representation.length == 3) {
            is_pondered = true;
            value = Integer.parseInt(parts_of_string_representation[2].trim());
        } else {
            is_pondered = false;
        }

        first_vertice = parts_of_string_representation[0].trim();
        second_vertice = parts_of_string_representation[1].trim();

        if (string_representation.charAt(0) == '(')
            is_directed = true;
        else
            is_directed = false;
    }
}