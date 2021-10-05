#include <iostream>
#include <string>
#include <regex>
#include <list>
#include <set>
#include <stack>
#include <fstream>
#include <optional>

#include <chrono>

#include "edge.h"
#include "graph.h"

using namespace std;

namespace AboutGraphs
{
    const regex Graph::PATTERN_TO_VALIDATE_A_DIRECTED_UNPONDERED_GRAPH = regex(
        "^\\s*\\{\\s*(?:\\{\\s*\\w+\\s*,\\s*\\w+\\s*\\}\\s*,\\s*)*\\{\\s*\\w+\\s*,\\s*\\w+\\s*\\}\\s*\\}\\s*$");
    const regex Graph::PATTERN_TO_VALIDATE_AN_UNDIRECTED_UNPONDERED_GRAPH = regex(
        "^\\s*\\{\\s*(?:\\(\\s*\\w+\\s*,\\s*\\w+\\s*\\)\\s*,\\s*)*\\(\\s*\\w+\\s*,\\s*\\w+\\s*\\)\\s*\\}\\s*$");
    const regex Graph::PATTERN_TO_VALIDATE_A_DIRECTED_PONDERED_GRAPH = regex(
        "^\\s*\\{\\s*(?:\\(\\s*\\w+\\s*,\\s*\\w+\\s*,\\s*(?:\\+|\\-)?\\d+\\s*\\)\\s*,\\s*)*\\(\\s*\\w+\\s*,\\s*\\w+\\s*,\\s*(?:\\+|\\-)?\\d+\\s*\\)\\s*\\}\\s*$");
    const regex Graph::PATTERN_TO_VALIDATE_AN_UNDIRECTED_PONDERED_GRAPH = regex(
        "^\\s*\\{\\s*(?:\\{\\s*\\w+\\s*,\\s*\\w+\\s*,\\s*(?:\\+|\\-)?\\d+\\s*\\}\\s*,\\s*)*\\{\\s*\\w+\\s*,\\s*\\w+\\s*,\\s*(?:\\+|\\-)?\\d+\\s*\\}\\s*\\}\\s*$");

    Graph::~Graph()
    {
        // for (int i = 0; i < number_of_vertices; i++)
        // {
        //     free(representations.adjacency_matrix[i]);
        //     free(representations.incidency_matrix[i]);
        // }
    }

    optional<Graph> Graph::from_string(string &s)
    {
        bool isGraphValid = true;
        Graph newGraph = Graph();

        if (regex_search(s, PATTERN_TO_VALIDATE_A_DIRECTED_UNPONDERED_GRAPH) == true)
        {
            newGraph.is_graph_directed = true;
            newGraph.is_graph_pondered = false;
        }
        else if (regex_search(s, PATTERN_TO_VALIDATE_A_DIRECTED_PONDERED_GRAPH) == true)
        {
            newGraph.is_graph_directed = true;
            newGraph.is_graph_pondered = true;
        }
        else if (regex_search(s, PATTERN_TO_VALIDATE_AN_UNDIRECTED_UNPONDERED_GRAPH) == true)
        {
            newGraph.is_graph_directed = false;
            newGraph.is_graph_pondered = false;
        }
        else if (regex_search(s, PATTERN_TO_VALIDATE_AN_UNDIRECTED_PONDERED_GRAPH) == true)
        {
            newGraph.is_graph_directed = false;
            newGraph.is_graph_pondered = true;
        }
        else
        {
            isGraphValid = false;
        }

        if (isGraphValid)
        {
            newGraph.string_representation = s;
            newGraph.fill_edge_list_and_vertice_set();
            newGraph.fill_representations();
            return optional<Graph>{newGraph};
        }

        return nullopt;
    }

    void Graph::fill_edge_list_and_vertice_set()
    {
        sregex_iterator iterator = sregex_iterator(string_representation.begin(), string_representation.end(), Edge::PATTERN_TO_VALIDATE_AN_EDGE);
        sregex_iterator end = sregex_iterator();
        edges = list<Edge>();
        edge_values = list<int>();
        vertices = set<string>();
        optional<Edge> current_edge;

        while (iterator != end)
        {
            current_edge = Edge::from_string((*iterator).str());

            if (current_edge.has_value())
            {
                edges.push_back(*current_edge);

                if (current_edge->is_edge_pondered())
                    edge_values.push_back(current_edge->get_edge_value());

                vertices.insert(current_edge->get_first_vertice());
                vertices.insert(current_edge->get_second_vertice());
            }

            iterator++;
        }

        number_of_vertices = vertices.size();
        number_of_edges = edges.size();
    }

    void Graph::fill_representations()
    {
        fill_successor_adjacency_list();
        fill_predecessor_adjacency_list();
        fill_adjacency_matrix();
        fill_incidency_matrix();
        fill_adjacency_arrays();
        reorder_successor_adjacency_arrays();
        reorder_predecessor_adjacency_arrays();
    }

    void Graph::fill_successor_adjacency_list()
    {
        representations.successor_adjacency_list = list<list<string>>();
        list<string> items_list;

        for (const string &vertice : vertices)
        {
            items_list = list<string>();
            items_list.push_front(vertice);

            for (Edge edge : edges)
            {
                if (vertice == edge.get_first_vertice())
                {
                    items_list.push_back(edge.get_second_vertice());
                }
            }

            representations.successor_adjacency_list.push_back(items_list);
        }
    }

    void Graph::fill_predecessor_adjacency_list()
    {
        representations.predecessor_adjacency_list = list<list<string>>();
        list<string> items_list;

        for (const string &vertice : vertices)
        {
            items_list = list<string>();
            items_list.push_front(vertice);

            for (Edge edge : edges)
            {
                if (vertice == edge.get_second_vertice())
                {
                    items_list.push_back(edge.get_first_vertice());
                }
            }

            representations.predecessor_adjacency_list.push_back(items_list);
        }
    }

    void Graph::show_predecessor_adjacency_list()
    {
        show_adjacency_list(representations.predecessor_adjacency_list);
    }

    void Graph::show_successor_adjacency_list()
    {
        show_adjacency_list(representations.successor_adjacency_list);
    }

    void Graph::show_adjacency_list(list<list<string>> adjacency_list)
    {
        string vertice;

        for (list<string> list : adjacency_list)
        {
            vertice = list.front();
            list.pop_front();

            cout << vertice << " --> ";

            for (const string &item : list)
            {
                cout << item << " ; ";
            }

            list.push_front(vertice);

            cout << endl;
        }
    }

    void Graph::fill_adjacency_matrix()
    {
        representations.adjacency_matrix = (int **)malloc(sizeof(int *) * number_of_vertices);
        set<string>::iterator vertice_iterator = vertices.begin();
        int index_of_second_vertice;

        for (int i = 0; i < number_of_vertices; i++)
        {
            representations.adjacency_matrix[i] = new int[number_of_vertices];

            for (int j = 0; j < number_of_vertices; j++)
                representations.adjacency_matrix[i][j] = 0;

            for (Edge edge : edges)
            {
                if ((*vertice_iterator) == edge.get_first_vertice())
                {
                    index_of_second_vertice = find_the_index_of_the_vertice(edge.get_second_vertice());
                    representations.adjacency_matrix[i][index_of_second_vertice] = 1;
                }
            }

            vertice_iterator++;
        }
    }

    int Graph::find_the_index_of_the_vertice(string &vertice)
    {
        int index = 0;

        for (const string &v : vertices)
        {
            if (v == vertice)
            {
                return index;
            }

            index++;
        }

        return -1;
    }

    void Graph::show_adjacency_matrix()
    {
        set<string>::iterator vertice_iterator = vertices.begin();

        cout << "\t";

        for (const string &vertice : vertices)
            cout << vertice << "\t";

        cout << endl;

        for (int i = 0; i < number_of_vertices; i++)
        {
            cout << (*vertice_iterator) << "\t";

            for (int j = 0; j < number_of_vertices; j++)
            {
                cout << representations.adjacency_matrix[i][j] << "\t";
            }

            cout << endl;
            vertice_iterator++;
        }
    }

    void Graph::fill_incidency_matrix()
    {
        representations.incidency_matrix = (int **)malloc(sizeof(int *) * number_of_vertices);
        set<string>::iterator vertice_iterator = vertices.begin();
        list<Edge>::iterator edge_iterator = edges.begin();
        int index_of_second_vertice;

        for (int i = 0; i < number_of_vertices; i++)
        {
            representations.incidency_matrix[i] = new int[edges.size()];
        }

        for (int i = 0; i < number_of_vertices; i++)
        {
            edge_iterator = edges.begin();

            for (size_t j = 0; j < edges.size(); j++)
            {
                if ((*vertice_iterator) == edge_iterator->get_first_vertice())
                {
                    index_of_second_vertice = find_the_index_of_the_vertice(edge_iterator->get_second_vertice());
                    representations.incidency_matrix[i][j] = 1;
                    representations.incidency_matrix[index_of_second_vertice][j] = -1;
                }
                else if (representations.incidency_matrix[i][j] != -1)
                {
                    representations.incidency_matrix[i][j] = 0;
                }

                edge_iterator++;
            }

            vertice_iterator++;
        }
    }

    void Graph::show_incidency_matrix()
    {
        set<string>::iterator vertice_iterator = vertices.begin();
        list<Edge>::iterator edge_iterator;
        int current_item;

        cout << "\t";

        for (Edge edge : edges)
            cout << edge.get_first_vertice() << " -- " << edge.get_second_vertice() << "\t";

        cout << endl;

        for (size_t i = 0; i < vertices.size(); i++)
        {
            cout << (*vertice_iterator) << "\t";
            edge_iterator = edges.begin();

            for (size_t j = 0; j < edges.size(); j++)
            {
                current_item = representations.incidency_matrix[i][j];

                if (current_item == 1)
                    cout << "+";
                else if (current_item == 0)
                    cout << " ";

                cout << current_item;

                if (edge_iterator->is_edge_pondered())
                    cout << " | " << edge_iterator->get_edge_value();

                cout << "\t";
                edge_iterator++;
            }

            cout << endl;
            vertice_iterator++;
        }
    }

    void Graph::fill_adjacency_arrays()
    {
        int current_index = 0;
        int index_of_first_vertice, index_of_second_vertice;

        representations.predecessor_adjacency_array_start = new int[number_of_edges];
        representations.predecessor_adjacency_array_end = new int[number_of_edges];
        representations.successor_adjacency_array_start = new int[number_of_edges];
        representations.successor_adjacency_array_end = new int[number_of_edges];

        for (Edge edge : edges)
        {
            index_of_first_vertice = find_the_index_of_the_vertice(edge.get_first_vertice());
            index_of_second_vertice = find_the_index_of_the_vertice(edge.get_second_vertice());
            representations.predecessor_adjacency_array_start[current_index] = index_of_first_vertice;
            representations.successor_adjacency_array_start[current_index] = index_of_first_vertice;
            representations.predecessor_adjacency_array_end[current_index] = index_of_second_vertice;
            representations.successor_adjacency_array_end[current_index] = index_of_second_vertice;
            current_index++;
        }
    }

    void Graph::reorder_successor_adjacency_arrays() {
        order_adjacency_arrays(representations.successor_adjacency_array_start,
            representations.successor_adjacency_array_end, number_of_edges);

        representations.successor_adjacency_array_start = reorder_and_return_successor_adjacency_array();
    }

    void Graph::reorder_predecessor_adjacency_arrays() {
        order_adjacency_arrays(representations.predecessor_adjacency_array_end,
                representations.predecessor_adjacency_array_start, number_of_edges);

        representations.predecessor_adjacency_array_end = reorder_and_return_predecessor_adjacency_array();
    }

    void Graph::order_adjacency_arrays(int which_to_sort[], int other_array[], size_t array_size)
    {
        int current_item_of_which_to_sort_array, current_item_of_other_array;
        int j;

        for (size_t i = 1; i < array_size; i++)
        {
            current_item_of_which_to_sort_array = which_to_sort[i];
            current_item_of_other_array = other_array[i];
            j = i - 1;

            while (j >= 0 && which_to_sort[j] > current_item_of_which_to_sort_array)
            {
                which_to_sort[j + 1] = which_to_sort[j];
                other_array[j + 1] = other_array[j];
                j--;
            }

            which_to_sort[j + 1] = current_item_of_which_to_sort_array;
            other_array[j + 1] = current_item_of_other_array;
        }
    }

    int* Graph::reorder_and_return_predecessor_adjacency_array() {
        int * reordered_array = (int*) malloc(sizeof(int) * (number_of_vertices + 1));
        int index_of_where_insert, index_of_first_ocurrence_of_a_vertice_index = 0;

        reordered_array[number_of_vertices] = number_of_edges;
        reordered_array[0] = 0;

        for (int where_insert_in_reordered_array = 1; where_insert_in_reordered_array < number_of_vertices; where_insert_in_reordered_array++) {
            index_of_where_insert = first_index_of_item_in_array(where_insert_in_reordered_array,
                    representations.predecessor_adjacency_array_end, number_of_edges);

            if (index_of_where_insert != -1) {
                index_of_first_ocurrence_of_a_vertice_index = index_of_where_insert;
            }

            reordered_array[where_insert_in_reordered_array] = index_of_first_ocurrence_of_a_vertice_index;
        }

        return reordered_array;
    }

    int* Graph::reorder_and_return_successor_adjacency_array() {
        int * reordered_array = (int*) malloc(sizeof(int) * (number_of_vertices + 1));
        int index_of_where_insert, index_of_first_ocurrence_of_a_vertice_index = number_of_edges;

        reordered_array[number_of_vertices] = number_of_edges;
        reordered_array[0] = 0;

        for (int where_insert_in_reordered_array = number_of_vertices
                - 1; where_insert_in_reordered_array > 0; where_insert_in_reordered_array--) {
            index_of_where_insert = first_index_of_item_in_array(where_insert_in_reordered_array,
                    representations.successor_adjacency_array_start, number_of_edges);

            if (index_of_where_insert != -1) {
                index_of_first_ocurrence_of_a_vertice_index = index_of_where_insert;
            }
            reordered_array[where_insert_in_reordered_array] = index_of_first_ocurrence_of_a_vertice_index;
        }

        return reordered_array;
    }

    int Graph::first_index_of_item_in_array(int item, int array[], int array_size)
    {
        for (int i = 0; i < array_size; i++)
        {
            if (item == array[i])
            {
                return i;
            }
        }

        return -1;
    }

    void Graph::show_successor_adjacency_arrays()
    {
        show_adjacency_arrays_increasing_their_values_by_one(
            representations.successor_adjacency_array_start, number_of_vertices + 1,
            representations.successor_adjacency_array_end, number_of_edges);
    }

    void Graph::show_predecessor_adjacency_arrays()
    {
        show_adjacency_arrays_increasing_their_values_by_one(
            representations.predecessor_adjacency_array_start, number_of_edges,
            representations.predecessor_adjacency_array_end, number_of_vertices + 1);
    }

    void Graph::show_adjacency_arrays_increasing_their_values_by_one(int start_array[], int start_array_size,
        int end_array[], int end_array_size)
    {
        show_vertices_set();

        cout << "Start array indices: [ ";

        for (int i = 0; i < start_array_size; i++)
        {
            cout << (start_array[i] + 1) << " ";
        }

        cout << "]\n";

        cout << "End array indices: [ ";

        for (int i = 0; i < end_array_size; i++)
        {
            cout << (end_array[i] + 1) << " ";
        }

        cout << "]\n";
    }

    void Graph::show_vertices_set()
    {
        cout << "Vertice set: { ";
        set<string>::iterator vertices_iterator = vertices.begin();

        for (int i = 0; i < number_of_vertices - 1; i++)
        {
            cout << (*vertices_iterator) << ", ";
            vertices_iterator++;
        }

        cout << (*vertices_iterator) << " }\n";
    }

    void Graph::show_all_representations()
    {
        cout << "\n  REPRESENTATIONS FOR GRAPH: " << string_representation << endl;
        cout << "\n\tADJACENCY MATRIX\n";
        show_adjacency_matrix();

        cout << "\n\tINCIDENCY MATRIX\n";
        show_incidency_matrix();

        cout << "\n\tPREDECESSOR ADJACENCY LIST\n";
        show_predecessor_adjacency_list();

        cout << "\n\tSUCCESSOR ADJACENCY LIST\n";
        show_successor_adjacency_list();

        cout << "\n\tPREDECESSOR ADJACENCY ARRAYS\n";
        show_predecessor_adjacency_arrays();

        cout << "\n\tSUCCESSOR ADJACENCY ARRAYS\n";
        show_successor_adjacency_arrays();
        cout << endl;
    }

    void Graph::make_deep_search_and_compute_times_in_arrays()
    {
    }

    void Graph::initialize_deep_search_structures()
    {
        deepSearchStructures.discovery_times = new int[number_of_vertices];
        deepSearchStructures.end_times = new int[number_of_vertices];

        for (int i = 0; i < number_of_vertices; i++)
        {
            deepSearchStructures.discovery_times[i] = -1;
            deepSearchStructures.end_times[i] = -1;
        }
    }

    void Graph::show_deep_search_structures()
    {
        cout << "\n[ ";
        for (size_t time = 0; time < vertices.size(); time++)
        {
            cout << deepSearchStructures.discovery_times[time] << " ";
        }
        cout << "]";

        cout << "\n[ ";
        for (size_t time = 0; time < vertices.size(); time++)
        {
            cout << deepSearchStructures.end_times[time] << " ";
        }
        cout << "]\n";
    }
}

int main()
{
    auto start = std::chrono::high_resolution_clock::now();

    std::ifstream file("graphs.txt");
    string file_line;
    optional<AboutGraphs::Graph> graph;

    while (std::getline(file, file_line))
    {
        graph = AboutGraphs::Graph::from_string(file_line);
        // graph->show_successor_adjacency_arrays();
        // graph->show_predecessor_adjacency_arrays();
        graph->show_all_representations();
    }

    file.close();

    auto end = std::chrono::high_resolution_clock::now();
    std::chrono::duration<double> duration = end - start;
    cout << "Duration: " << duration.count() * 1000 << "ms\n";

    return EXIT_SUCCESS;
}
