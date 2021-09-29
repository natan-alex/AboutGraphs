import java.util.regex.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.IllegalArgumentException;
import java.util.Iterator;
import java.util.Stack;

public class Graph {
    private class GraphStatistics {
        public int number_of_pondered_edges;
        public int number_of_unpondered_edges;
        public int number_of_directed_edges;
        public int number_of_undirected_edges;
    }

    private class GraphRepresentations {
        public int[][] adjacency_matrix;
        public int[][] incidency_matrix;
        public List<List<String>> successor_adjacency_list;
        public List<List<String>> predecessor_adjacency_list;
        public int[] successor_adjacency_array_start;
        public int[] successor_adjacency_array_end;
        public int[] predecessor_adjacency_array_start;
        public int[] predecessor_adjacency_array_end;
    }

    private class DeepSearchStructures {
        public int[] discovery_times;
        public int[] end_times;
    }

    private String string_representation;
    private List<Edge> edges;
    private Set<String> vertices;
    private List<Integer> edge_values;
    private boolean is_pondered;
    private boolean is_directed;
    private GraphStatistics statistics;
    private GraphRepresentations representations;
    private DeepSearchStructures deepSearchStructures;

    public static final Graph EMPTY_GRAPH = new Graph();

    public static Graph fromString(String s) throws IllegalArgumentException {
        Graph result = EMPTY_GRAPH;
        Pattern pattern_to_validate_a_complete_graph = Pattern.compile(
                "^\\s*\\{\\s*([(|{]\\s*\\w+\\s*,(\\s*\\w+\\s*,)?\\s*\\w+\\s*[)|}]\\s*,\\s*)*[(|{]\\s*\\w+\\s*,(\\s*\\w+\\s*,)?\\s*\\w+\\s*[)|}]\\s*\\}\\s*$");
        Matcher matcher = pattern_to_validate_a_complete_graph.matcher(s);

        if (matcher.matches()) {
            result = new Graph();
            result.string_representation = s;
            result.fill_edge_list_and_vertice_set();
            result.fill_graph_properties();
            result.fill_representations();
        }

        return result;
    }

    private void fill_edge_list_and_vertice_set() {
        edges = new ArrayList<Edge>();
        edge_values = new ArrayList<Integer>();
        vertices = new HashSet<String>();
        Edge current_edge;
        Matcher matcher = Edge.PATTERN_TO_VALIDATE_AN_EDGE.matcher(string_representation.trim());
        System.out.println("string representation: " + string_representation);

        while (matcher.find()) {
            System.out.println("match: " + matcher.group().toString());
            current_edge = Edge.from_string(matcher.group());

            if (current_edge != Edge.EMPTY_EDGE) {
                edges.add(current_edge);

                if (current_edge.is_pondered())
                    edge_values.add(current_edge.get_value());

                vertices.add(current_edge.get_first_vertice());
                vertices.add(current_edge.get_second_vertice());
            } else
                System.out.println("The edge '" + matcher.group() + "' in graph '" + string_representation
                        + "' is not valid, so it will not be considered.\n");
        }
    }

    private void fill_graph_properties() throws IllegalArgumentException {
        statistics = new GraphStatistics();
        statistics.number_of_directed_edges = 0;
        statistics.number_of_undirected_edges = 0;
        statistics.number_of_pondered_edges = 0;
        statistics.number_of_unpondered_edges = 0;

        for (Edge edge : edges) {
            if (edge.is_directed()) {
                statistics.number_of_directed_edges++;
            } else {
                statistics.number_of_undirected_edges++;
            }

            if (edge.is_pondered()) {
                statistics.number_of_pondered_edges++;
            } else {
                statistics.number_of_unpondered_edges++;
            }

            check_if_there_are_directed_and_undirected_edges_at_the_same_time();
            check_if_there_are_pondered_and_unpondered_edges_at_the_same_time();
        }

        check_if_the_graph_is_directed_based_on_the_statistics();
        check_if_the_graph_is_pondered_based_on_the_statistics();
    }

    void check_if_there_are_directed_and_undirected_edges_at_the_same_time() throws IllegalArgumentException {
        if (statistics.number_of_directed_edges != 0 && statistics.number_of_undirected_edges != 0) {
            throw new IllegalArgumentException(
                    "A graph can not have directed an undirected edges at the same time, so the graph '"
                            + string_representation + "' is invalid.");
        }
    }

    void check_if_there_are_pondered_and_unpondered_edges_at_the_same_time() throws IllegalArgumentException {
        if (statistics.number_of_pondered_edges != 0 && statistics.number_of_unpondered_edges != 0) {
            throw new IllegalArgumentException(
                    "A graph can not have pondered an unpondered edges at the same time, so the graph '"
                            + string_representation + "' is invalid.");
        }
    }

    void check_if_the_graph_is_directed_based_on_the_statistics() {
        if (statistics.number_of_undirected_edges == 0) {
            is_directed = true;
        } else if (statistics.number_of_directed_edges == 0) {
            is_directed = false;
        }
    }

    void check_if_the_graph_is_pondered_based_on_the_statistics() {
        if (statistics.number_of_unpondered_edges == 0) {
            is_pondered = true;
        } else if (statistics.number_of_pondered_edges == 0) {
            is_pondered = false;
        }
    }

    void fill_representations() {
        representations = new GraphRepresentations();
        fill_successor_adjacency_list();
        fill_predecessor_adjacency_list();
        fill_adjacency_matrix();
        fill_incidency_matrix();
        fill_adjacency_arrays_increasing_their_indices_by_one();
        // reorder_successor_adjacency_arrays();
        // reorder_predecessor_adjacency_arrays();
    }

    void fill_successor_adjacency_list() {
        representations.successor_adjacency_list = new ArrayList<>();
        List<String> items_list;

        for (String vertice : vertices) {
            items_list = new ArrayList<>();
            items_list.add(vertice);

            for (Edge edge : edges) {
                if (vertice.compareTo(edge.get_first_vertice()) == 0)
                    items_list.add(edge.get_second_vertice());
            }

            representations.successor_adjacency_list.add(items_list);
        }
    }

    void fill_predecessor_adjacency_list() {
        representations.predecessor_adjacency_list = new ArrayList<>();
        List<String> items_list;

        for (String vertice : vertices) {
            items_list = new ArrayList<>();
            items_list.add(vertice);

            for (Edge edge : edges) {
                if (vertice.compareTo(edge.get_second_vertice()) == 0) {
                    items_list.add(edge.get_first_vertice());
                }
            }

            representations.predecessor_adjacency_list.add(items_list);
        }
    }

    void show_predecessor_adjacency_list() {
        show_adjacency_list(representations.predecessor_adjacency_list);
    }

    void show_successor_adjacency_list() {
        show_adjacency_list(representations.successor_adjacency_list);
    }

    void show_adjacency_list(List<List<String>> adjacency_list) {
        for (List<String> list : adjacency_list) {
            System.out.print(list.get(0) + " -> ");

            for (String item : list) {
                System.out.print(item + " ; ");
            }
        }
    }

    void fill_adjacency_matrix() {
        int number_of_vertices = vertices.size();
        representations.adjacency_matrix = new int[number_of_vertices][number_of_vertices];
        Iterator<String> vertice_iterator = vertices.iterator();
        String current_vertice;
        int index_of_second_vertice;

        for (int i = 0; i < number_of_vertices; i++) {
            current_vertice = vertice_iterator.next();

            for (int j = 0; j < number_of_vertices; j++)
                representations.adjacency_matrix[i][j] = 0;

            for (Edge edge : edges) {
                if (current_vertice.compareTo(edge.get_first_vertice()) == 0) {
                    index_of_second_vertice = find_the_index_of_the_vertice(edge.get_second_vertice());
                    representations.adjacency_matrix[i][index_of_second_vertice] = 1;
                }
            }
        }
    }

    int find_the_index_of_the_vertice(String verticeToFind) {
        int index = 0;

        for (String vertice : vertices) {
            if (vertice.compareTo(verticeToFind) == 0) {
                return index;
            }

            index++;
        }

        return -1;
    }

    void show_adjacency_matrix() {
        int number_of_vertices = vertices.size();
        Iterator<String> vertice_iterator = vertices.iterator();

        System.out.print("\t");

        for (String vertice : vertices)
            System.out.print(vertice + "\t");

        System.out.println();

        for (int i = 0; i < number_of_vertices; i++) {
            System.out.print(vertice_iterator.next() + "\t");

            for (int j = 0; j < number_of_vertices; j++) {
                System.out.print(representations.adjacency_matrix[i][j] + "\t");
            }

            System.out.println();
        }
    }

    void fill_incidency_matrix() {
        int number_of_vertices = vertices.size();
        int number_of_edges = edges.size();
        int index_of_second_vertice;
        Iterator<String> vertice_iterator = vertices.iterator();
        Iterator<Edge> edge_iterator;
        String current_vertice;
        Edge current_edge;

        representations.incidency_matrix = new int[number_of_vertices][number_of_edges];

        for (int i = 0; i < number_of_vertices; i++) {
            edge_iterator = edges.iterator();
            current_vertice = vertice_iterator.next();

            for (int j = 0; j < number_of_edges; j++) {
                current_edge = edge_iterator.next();

                if (current_vertice.compareTo(current_edge.get_first_vertice()) == 0) {
                    index_of_second_vertice = find_the_index_of_the_vertice(current_edge.get_second_vertice());
                    representations.incidency_matrix[i][j] = 1;
                    representations.incidency_matrix[index_of_second_vertice][j] = -1;
                } else if (representations.incidency_matrix[i][j] != -1) {
                    representations.incidency_matrix[i][j] = 0;
                }
            }
        }
    }

    void show_incidency_matrix() {
        Iterator<String> vertice_iterator = vertices.iterator();
        Iterator<Edge> edge_iterator;
        int current_item;
        Edge current_edge;

        System.out.print("\t");

        for (Edge edge : edges)
            System.out.print(edge.get_first_vertice() + " -- " + edge.get_second_vertice() + "\t");

        System.out.println();

        for (int i = 0; i < vertices.size(); i++) {
            System.out.print(vertice_iterator.next() + "\t");
            edge_iterator = edges.iterator();

            for (int j = 0; j < edges.size(); j++) {
                current_item = representations.incidency_matrix[i][j];
                current_edge = edge_iterator.next();

                if (current_item == 1)
                    System.out.print("+");
                else if (current_item == 0)
                    System.out.print(" ");

                System.out.print(current_item);

                if (current_edge.is_pondered())
                    System.out.print(" | " + current_edge.get_value());

                System.out.print("\t");
            }

            System.out.println();
        }
    }

    private void fill_adjacency_arrays_increasing_their_indices_by_one() {
        int number_of_edges = edges.size();
        int current_index = 0;
        int index_of_first_vertice, index_of_second_vertice;

        representations.predecessor_adjacency_array_start = new int[number_of_edges];
        representations.predecessor_adjacency_array_end = new int[number_of_edges];
        representations.successor_adjacency_array_start = new int[number_of_edges];
        representations.successor_adjacency_array_end = new int[number_of_edges];

        for (Edge edge : edges) {
            index_of_first_vertice = find_the_index_of_the_vertice(edge.get_first_vertice());
            index_of_second_vertice = find_the_index_of_the_vertice(edge.get_second_vertice());
            representations.predecessor_adjacency_array_start[current_index] = index_of_first_vertice + 1;
            representations.successor_adjacency_array_start[current_index] = index_of_first_vertice + 1;
            representations.predecessor_adjacency_array_end[current_index] = index_of_second_vertice + 1;
            representations.successor_adjacency_array_end[current_index] = index_of_second_vertice + 1;
            current_index++;
        }
    }

    private void reorder_successor_adjacency_arrays() {
        order_adjacency_arrays(representations.successor_adjacency_array_start,
                representations.successor_adjacency_array_end);
        representations.successor_adjacency_array_start = get_reordered_sorted_adjacency_array(
                representations.successor_adjacency_array_start);
    }

    private void reorder_predecessor_adjacency_arrays() {
        order_adjacency_arrays(representations.predecessor_adjacency_array_end,
                representations.predecessor_adjacency_array_start);
        representations.predecessor_adjacency_array_end = get_reordered_sorted_adjacency_array(
                representations.predecessor_adjacency_array_end);
    }

    private void order_adjacency_arrays(int[] which_to_sort, int[] other_array) {
        int current_item_of_which_to_sort_array, current_item_of_other_array;
        int j;

        for (int i = 1; i < which_to_sort.length; i++) {
            current_item_of_which_to_sort_array = which_to_sort[i];
            current_item_of_other_array = other_array[i];
            j = i - 1;

            while (j >= 0 && which_to_sort[j] > current_item_of_which_to_sort_array) {
                which_to_sort[j + 1] = which_to_sort[j];
                other_array[j + 1] = other_array[j];
                j--;
            }

            which_to_sort[j + 1] = current_item_of_which_to_sort_array;
            other_array[j + 1] = current_item_of_other_array;
        }
    }

    private int[] get_reordered_sorted_adjacency_array(int[] sorted_array) {
        int number_of_edges = edges.size();
        int number_of_vertices = vertices.size();
        int current_value_of_sorted_array, index_from_where_to_read = number_of_edges - 1;
        int[] reordered_array = new int[number_of_vertices + 1];
        int index_where_to_insert = number_of_vertices - 1;
        boolean has_index_in_sorted_array;

        reordered_array[number_of_vertices] = number_of_edges + 1;

        while (index_where_to_insert >= 0) {
            current_value_of_sorted_array = sorted_array[index_from_where_to_read];

            while (sorted_array[index_from_where_to_read - 1] == current_value_of_sorted_array)
                index_from_where_to_read--;

            has_index_in_sorted_array = false;
            for (int i = 0; i < number_of_edges; i++)
                if (sorted_array[i] == index_where_to_insert + 1) {
                    has_index_in_sorted_array = true;
                    i = number_of_edges;
                }

            if (has_index_in_sorted_array)
                reordered_array[index_where_to_insert] = index_from_where_to_read-- + 1;
            else
                reordered_array[index_where_to_insert] = reordered_array[index_where_to_insert + 1];

            index_where_to_insert--;
        }

        return reordered_array;
    }

    void show_successor_adjacency_arrays() {
        show_adjacency_arrays(representations.successor_adjacency_array_start,
                representations.successor_adjacency_array_end);
    }

    void show_predecessor_adjacency_arrays() {
        show_adjacency_arrays(representations.predecessor_adjacency_array_end,
                representations.predecessor_adjacency_array_start);
    }

    void show_adjacency_arrays(int sorted_array[], int other_array[]) {
        show_vertices_set();

        System.out.print("Start array indices: [ ");

        for (int i = 0; i < vertices.size() + 1; i++)
            System.out.print(sorted_array[i] + " ");

        System.out.println("]");

        System.out.print("End array indices: [ ");

        for (int i = 0; i < edges.size(); i++)
            System.out.print(other_array[i] + " ");

        System.out.println();
    }

    void show_vertices_set() {
        System.out.print("Vertice set: {");
        String[] verticesArray = (String[]) vertices.toArray();

        for (int i = 0; i < verticesArray.length - 1; i++)
            System.out.print(verticesArray[i] + ", ");

        System.out.println(verticesArray[verticesArray.length] + " }");
    }

    void show_all_representations() {
        System.out.println("\n  REPRESENTATIONS FOR GRAPH: " + string_representation);
        System.out.println("\n\tADJACENCY MATRIX\n");
        show_adjacency_matrix();

        System.out.println("\n\tINCIDENCY MATRIX\n");
        show_incidency_matrix();

        System.out.println("\n\tPREDECESSOR ADJACENCY LIST\n");
        show_predecessor_adjacency_list();

        System.out.println("\n\tSUCCESSOR ADJACENCY LIST\n");
        show_successor_adjacency_list();

        System.out.println("\n\tPREDECESSOR ADJACENCY ARRAYS\n");
        show_predecessor_adjacency_arrays();

        System.out.println("\n\tSUCCESSOR ADJACENCY ARRAYS\n");
        show_successor_adjacency_arrays();
        System.out.println();
    }

    public void make_deep_search_and_compute_times_in_arrays() {
        // Stack<List<String>> discovered_vertices;
        // Stack<List<List<String>>> discovered_lists;
        // Iterator<List<String>> current_list =
        // representations.successor_adjacency_list.iterator();
        // int index_of_current_list = 0;
        // Iterator<String> current_vertice;

        initialize_deep_search_structures();

        for (int time_counter = 1; time_counter <= 2 * vertices.size(); time_counter++) {

        }
    }

    void initialize_deep_search_structures() {
        deepSearchStructures = new DeepSearchStructures();
        int number_of_vertices = vertices.size();
        deepSearchStructures.discovery_times = new int[number_of_vertices];
        deepSearchStructures.end_times = new int[number_of_vertices];

        for (int i = 0; i < number_of_vertices; i++) {
            deepSearchStructures.discovery_times[i] = -1;
            deepSearchStructures.end_times[i] = -1;
        }
    }

    public void show_deep_search_structures() {
        System.out.println("\n[ ");

        for (int time = 0; time < vertices.size(); time++)
            System.out.print(deepSearchStructures.discovery_times[time] + " ");

        System.out.println("]");

        System.out.println("\n[ ");

        for (int time = 0; time < vertices.size(); time++)
            System.out.print(deepSearchStructures.end_times[time] + " ");

        System.out.println("]");
    }

    public static void main(String[] args) throws Exception {
        double start = System.currentTimeMillis();

        BufferedReader bufferedReader = new BufferedReader(new FileReader("graphs.txt"));
        String file_line;
        Graph graph;

        while ((file_line = bufferedReader.readLine()) != null) {
            graph = Graph.fromString(file_line);
            graph.show_incidency_matrix();
            // graph.show_adjacency_matrix();
        }

        // graph.show_successor_adjacency_list();
        // graph.make_deep_search_and_compute_times_in_arrays();
        // graph.show_deep_search_structures();

        bufferedReader.close();
        double end = System.currentTimeMillis();

        System.out.println("Duration: " + (end - start) + "ms\n");
    }
}