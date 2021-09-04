#include <iostream>
#include <string>
#include <regex>
#include <list>
#include <set>

#include <chrono>

#include "edge.h"
#include "graph.h"

using namespace std;

namespace AboutGraphs
{

    Graph *const Graph::EMPTY_GRAPH = new Graph();

    Graph::~Graph()
    {
        for (Edge *edge : edges)
        {
            delete edge;
        }

        for (size_t i = 0; i < vertices.size(); i++)
        {
            delete representations.adjacency_matrix[i];
            delete representations.incidency_matrix[i];
        }
    }

    Graph *Graph::from_string(string &s)
    {
        Graph *result = Graph::EMPTY_GRAPH;
        regex pattern_to_validate_a_complete_graph("^\\s*\\{\\s*([(|{]\\s*\\w+\\s*,(\\s*\\w+\\s*,)?\\s*\\w+\\s*[)|}]\\s*,\\s*)*[(|{]\\s*\\w+\\s*,(\\s*\\w+\\s*,)?\\s*\\w+\\s*[)|}]\\s*\\}\\s*$");

        if (regex_search(s, pattern_to_validate_a_complete_graph) == true)
        {
            result = new Graph();
            result->string_representation = s;
            result->fill_edge_list_and_vertice_set();
            result->fill_graph_properties();
            result->fill_representations();
        }

        return result;
    }

    void Graph::fill_edge_list_and_vertice_set()
    {
        sregex_iterator iterator = sregex_iterator(string_representation.begin(), string_representation.end(), Edge::PATTERN_TO_VALIDATE_AN_EDGE);
        sregex_iterator end = sregex_iterator();
        smatch matches;
        string match_str;
        edges = list<Edge *>();
        edge_values = list<int>();
        vertices = set<string>();
        Edge *current_edge;

        while (iterator != end)
        {
            matches = *iterator;
            match_str = matches.str();
            current_edge = Edge::from_string(match_str);

            if (current_edge != Edge::EMPTY_EDGE)
            {
                edges.push_back(current_edge);
                if (current_edge->is_edge_pondered())
                    edge_values.push_back(current_edge->get_edge_value());
                vertices.insert(current_edge->get_first_vertice());
                vertices.insert(current_edge->get_second_vertice());
            }
            else
                cout << "The edge '" << match_str << "' in graph '" << string_representation << "' is not valid, so it will not be considered.\n";
 
            iterator++;
        }
    }

    void Graph::fill_graph_properties()
    {
        statistics.number_of_directed_edges = 0;
        statistics.number_of_undirected_edges = 0;
        statistics.number_of_pondered_edges = 0;
        statistics.number_of_unpondered_edges = 0;

        for (list<Edge *>::iterator edge_iterator = edges.begin(); edge_iterator != edges.end(); edge_iterator++)
        {
            if ((*edge_iterator)->is_edge_directed())
            {
                statistics.number_of_directed_edges++;
            }
            else
            {
                statistics.number_of_undirected_edges++;
            }

            if ((*edge_iterator)->is_edge_pondered())
            {
                statistics.number_of_pondered_edges++;
            }
            else
            {
                statistics.number_of_unpondered_edges++;
            }

            check_if_there_are_directed_and_undirected_edges_at_the_same_time();
            check_if_there_are_pondered_and_unpondered_edges_at_the_same_time();
        }

        check_if_the_graph_is_directed_based_on_the_statistics();
        check_if_the_graph_is_pondered_based_on_the_statistics();
    }

    void Graph::check_if_there_are_directed_and_undirected_edges_at_the_same_time()
    {
        if (statistics.number_of_directed_edges != 0 && statistics.number_of_undirected_edges != 0)
        {
            string error_message = "A graph can not have directed an undirected edges at the same time, so the graph '";
            error_message.append(string_representation);
            error_message.append("' is isvalid.");
            throw invalid_argument(error_message);
        }
    }

    void Graph::check_if_there_are_pondered_and_unpondered_edges_at_the_same_time()
    {
        if (statistics.number_of_pondered_edges != 0 && statistics.number_of_unpondered_edges != 0)
        {
            string error_message = "A graph can not have pondered an unpondered edges at the same time, so the graph '";
            error_message.append(string_representation);
            error_message.append("' is invalid.");
            throw invalid_argument(error_message);
        }
    }

    void Graph::check_if_the_graph_is_directed_based_on_the_statistics()
    {
        if (statistics.number_of_undirected_edges == 0)
        {
            is_graph_directed = true;
        }
        else if (statistics.number_of_directed_edges == 0)
        {
            is_graph_directed = false;
        }
    }

    void Graph::check_if_the_graph_is_pondered_based_on_the_statistics()
    {
        if (statistics.number_of_unpondered_edges == 0)
        {
            is_graph_pondered = true;
        }
        else if (statistics.number_of_pondered_edges == 0)
        {
            is_graph_pondered = false;
        }
    }

    void Graph::fill_representations()
    {
        fill_successor_adjacency_list();
        fill_predecessor_adjacency_list();
        fill_adjacency_matrix();
        fill_incidency_matrix();
        fill_adjacency_arrays_increasing_their_indices_by_one();
        reorder_successor_adjacency_arrays();
        reorder_predecessor_adjacency_arrays();
    }

    void Graph::fill_successor_adjacency_list()
    {
        representations.successor_adjacency_list = list<list<string>>();
        list<string> items_list;

        for (set<string>::iterator vertice = vertices.begin(); vertice != vertices.end(); vertice++)
        {
            items_list = list<string>();
            items_list.push_front(*vertice);

            for (list<Edge *>::iterator edge = edges.begin(); edge != edges.end(); edge++)
            {
                if ((*vertice) == (*edge)->get_first_vertice())
                {
                    items_list.push_back((*edge)->get_second_vertice());
                }
            }

            representations.successor_adjacency_list.push_back(items_list);
        }
    }

    void Graph::fill_predecessor_adjacency_list()
    {
        representations.predecessor_adjacency_list = list<list<string>>();
        list<string> items_list;

        for (set<string>::iterator vertice_iterator = vertices.begin(); vertice_iterator != vertices.end(); vertice_iterator++)
        {
            items_list = list<string>();
            items_list.push_front(*vertice_iterator);

            for (list<Edge *>::iterator edge = edges.begin(); edge != edges.end(); edge++)
            {
                if ((*vertice_iterator) == (*edge)->get_second_vertice())
                {
                    items_list.push_back((*edge)->get_first_vertice());
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

        for (auto list_iterator = adjacency_list.begin(); list_iterator != adjacency_list.end(); list_iterator++)
        {
            vertice = (*list_iterator).front();
            (*list_iterator).pop_front();

            cout << vertice << " --> ";

            for (auto item = (*list_iterator).begin(); item != (*list_iterator).end(); item++)
            {
                cout << (*item) << " ; ";
            }

            (*list_iterator).push_front(vertice);

            cout << endl;
        }
    }

    void Graph::fill_adjacency_matrix()
    {
        int number_of_vertices = vertices.size();
        representations.adjacency_matrix = (int **)malloc(sizeof(int *) * number_of_vertices);
        set<string>::iterator vertice_iterator = vertices.begin();
        int index_of_second_vertice;

        for (int i = 0; i < number_of_vertices; i++)
        {
            representations.adjacency_matrix[i] = new int[number_of_vertices];

            for (int j = 0; j < number_of_vertices; j++)
                representations.adjacency_matrix[i][j] = 0;

            for (list<Edge *>::iterator edge = edges.begin(); edge != edges.end(); edge++)
            {
                if ((*vertice_iterator) == (*edge)->get_first_vertice())
                {
                    index_of_second_vertice = find_the_index_of_the_vertice((*edge)->get_second_vertice());
                    representations.adjacency_matrix[i][index_of_second_vertice] = 1;
                }
            }

            vertice_iterator++;
        }
    }

    int Graph::find_the_index_of_the_vertice(string &vertice)
    {
        int index = 0;

        for (set<string>::iterator vertice_iterator = vertices.begin(); vertice_iterator != vertices.end(); vertice_iterator++)
        {
            if ((*vertice_iterator) == vertice)
            {
                return index;
            }

            index++;
        }

        return -1;
    }

    void Graph::show_adjacency_matrix()
    {
        int number_of_vertices = vertices.size();
        set<string>::iterator vertice_iterator = vertices.begin();

        cout << "\t";

        for (set<string>::iterator vertice = vertices.begin(); vertice != vertices.end(); vertice++)
            cout << (*vertice) << "\t";

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
        int number_of_vertices = vertices.size();
        representations.incidency_matrix = (int **)malloc(sizeof(int *) * number_of_vertices);
        set<string>::iterator vertice_iterator = vertices.begin();
        list<Edge *>::iterator edge_iterator = edges.begin();
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
                if ((*vertice_iterator) == (*edge_iterator)->get_first_vertice())
                {
                    index_of_second_vertice = find_the_index_of_the_vertice((*edge_iterator)->get_second_vertice());
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
        list<Edge *>::iterator edge_iterator;
        int current_item;

        cout << "\t";

        for (list<Edge *>::iterator edge = edges.begin(); edge != edges.end(); edge++)
            cout << (*edge)->get_first_vertice() << " -- " << (*edge)->get_second_vertice() << "\t";

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

                if ((*edge_iterator)->is_edge_pondered())
                    cout << " | " << (*edge_iterator)->get_edge_value();

                cout << "\t";
                edge_iterator++;
            }

            cout << endl;
            vertice_iterator++;
        }
    }

    void Graph::fill_adjacency_arrays_increasing_their_indices_by_one()
    {
        size_t number_of_edges = edges.size();
        int current_index = 0;
        int index_of_first_vertice, index_of_second_vertice;

        representations.predecessor_adjacency_array_start = new int[number_of_edges];
        representations.predecessor_adjacency_array_end = new int[number_of_edges];
        representations.successor_adjacency_array_start = new int[number_of_edges];
        representations.successor_adjacency_array_end = new int[number_of_edges];

        for (list<Edge *>::iterator edge = edges.begin(); edge != edges.end(); edge++)
        {
            index_of_first_vertice = find_the_index_of_the_vertice((*edge)->get_first_vertice());
            index_of_second_vertice = find_the_index_of_the_vertice((*edge)->get_second_vertice());
            representations.predecessor_adjacency_array_start[current_index] = index_of_first_vertice + 1;
            representations.successor_adjacency_array_start[current_index] = index_of_first_vertice + 1;
            representations.predecessor_adjacency_array_end[current_index] = index_of_second_vertice + 1;
            representations.successor_adjacency_array_end[current_index] = index_of_second_vertice + 1;
            current_index++;
        }
    }

    void Graph::reorder_successor_adjacency_arrays()
    {
        order_adjacency_arrays(representations.successor_adjacency_array_start, representations.successor_adjacency_array_end, edges.size());
        representations.successor_adjacency_array_start = get_reordered_sorted_adjacency_array(representations.successor_adjacency_array_start);
    }

    void Graph::reorder_predecessor_adjacency_arrays()
    {
        order_adjacency_arrays(representations.predecessor_adjacency_array_end, representations.predecessor_adjacency_array_start, edges.size());
        representations.predecessor_adjacency_array_end = get_reordered_sorted_adjacency_array(representations.predecessor_adjacency_array_end);
    }

    void Graph::order_adjacency_arrays(int which_to_sort[], int other_array[], size_t size)
    {
        int current_item_of_which_to_sort_array, current_item_of_other_array;
        int j;

        for (size_t i = 1; i < size; i++)
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

    int * Graph::get_reordered_sorted_adjacency_array(int sorted_array[])
    {
        size_t number_of_edges = edges.size();
        size_t number_of_vertices = vertices.size();
        int current_value_of_sorted_array, index_from_where_to_read_from_sorted_array = number_of_edges - 1;
        int *new_array_containing_the_indices_of_the_vertices = new int[number_of_vertices + 1];
        int index_where_to_insert_in_the_new_array = number_of_vertices - 1;
        bool was_index_where_to_insert_in_new_array_found_on_sorted_array;

        new_array_containing_the_indices_of_the_vertices[number_of_vertices] = number_of_edges + 1;

        while (index_where_to_insert_in_the_new_array >= 0)
        {
            current_value_of_sorted_array = sorted_array[index_from_where_to_read_from_sorted_array];

            while (sorted_array[index_from_where_to_read_from_sorted_array - 1] == current_value_of_sorted_array)
                index_from_where_to_read_from_sorted_array--;

            was_index_where_to_insert_in_new_array_found_on_sorted_array= false;
            for (size_t i = 0; i < number_of_edges; i++)
                if (sorted_array[i] == index_where_to_insert_in_the_new_array + 1)
                {
                    was_index_where_to_insert_in_new_array_found_on_sorted_array = true;
                    i = number_of_edges;
                }

            if (was_index_where_to_insert_in_new_array_found_on_sorted_array)
                new_array_containing_the_indices_of_the_vertices[index_where_to_insert_in_the_new_array] = index_from_where_to_read_from_sorted_array-- + 1;
            else
                new_array_containing_the_indices_of_the_vertices[index_where_to_insert_in_the_new_array] = new_array_containing_the_indices_of_the_vertices[index_where_to_insert_in_the_new_array + 1];

            index_where_to_insert_in_the_new_array--;
        }

        return new_array_containing_the_indices_of_the_vertices;
    }

    void Graph::show_successor_adjacency_arrays()
    {
        show_adjacency_arrays(representations.successor_adjacency_array_start, representations.successor_adjacency_array_end);
    }

    void Graph::show_predecessor_adjacency_arrays()
    {
        show_adjacency_arrays(representations.predecessor_adjacency_array_end, representations.predecessor_adjacency_array_start);
    }

    void Graph::show_adjacency_arrays(int sorted_array[], int other_array[]) 
    {
        show_vertices_set();

        cout << "Start array indices: [ ";

        for (size_t i = 0; i < vertices.size() + 1; i++) {
            cout << sorted_array[i] << " ";
        }

        cout << "]\n";

        cout << "End array indices: [ ";

        for (size_t i = 0; i < edges.size(); i++) {
            cout << other_array[i] << " ";
        }

        cout << "]\n";
    }

    void Graph::show_vertices_set() {
        cout << "Vertice set: { ";
        set<string>::iterator vertices_iterator = vertices.begin();

        for (size_t i = 0; i < vertices.size() - 1; i++)
        {
            cout << (*vertices_iterator) << ", ";
            vertices_iterator++;
        }

        cout << (*vertices_iterator) << " }\n";
    }
}

int main()
{
    auto start = std::chrono::high_resolution_clock::now();

    // string s = "{  (hi, hello), ( say, hellooo ), (fooo, baaar), (baaars, fooos) }";
    string s = "{(1,3),(1,5),(2,3),(2,6),(3,4),(4,5),(4,6)}";
    AboutGraphs::Graph *g1 = AboutGraphs::Graph::from_string(s);
    // g1->show_successor_adjacency_list();
    // cout << endl;
    // g1->show_predecessor_adjacency_list();
    // cout << endl;
    // g1->show_adjacency_matrix();
    // cout << endl;
    // g1->show_incidency_matrix();
    // cout << endl;
    g1->show_predecessor_adjacency_arrays();
    cout << endl;
    g1->show_successor_adjacency_arrays();
    cout << endl;
    cout << endl;

    s = "{(1,3),(2,1),(2,3),(3,4),(4,2),(4,5),(5,3)}";
    g1 = AboutGraphs::Graph::from_string(s);
    // g1->show_successor_adjacency_list();
    // cout << endl;
    // g1->show_predecessor_adjacency_list();
    // cout << endl;
    // g1->show_adjacency_matrix();
    // cout << endl;
    // g1->show_incidency_matrix();
    // cout << endl;
    g1->show_predecessor_adjacency_arrays();
    cout << endl;
    g1->show_successor_adjacency_arrays();
    cout << endl;
    cout << endl;

    auto end = std::chrono::high_resolution_clock::now();
    std::chrono::duration<double> duration = end - start;
    cout << "duration: " << duration.count() * 1000 << "ms\n";

    delete g1;

    return EXIT_SUCCESS;
}