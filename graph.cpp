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
        Graph *result = NULL;
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

            if (current_edge != NULL)
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

        for (list<Edge *>::iterator iterator = edges.begin(); iterator != edges.end(); iterator++)
        {
            if ((*iterator)->is_edge_directed())
            {
                statistics.number_of_directed_edges++;
            }
            else
            {
                statistics.number_of_undirected_edges++;
            }

            if ((*iterator)->is_edge_pondered())
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
            error_message.append("' is not valid.");
            throw invalid_argument(error_message);
        }
    }

    void Graph::check_if_there_are_pondered_and_unpondered_edges_at_the_same_time()
    {
        if (statistics.number_of_pondered_edges != 0 && statistics.number_of_unpondered_edges != 0)
        {
            string error_message = "A graph can not have pondered an unpondered edges at the same time, so the graph '";
            error_message.append(string_representation);
            error_message.append("' is not valid.");
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

        for (set<string>::iterator vertice = vertices.begin(); vertice != vertices.end(); vertice++)
        {
            items_list = list<string>();
            items_list.push_front(*vertice);

            for (list<Edge *>::iterator edge = edges.begin(); edge != edges.end(); edge++)
            {
                if ((*vertice) == (*edge)->get_second_vertice())
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
        for (auto list = adjacency_list.begin(); list != adjacency_list.end(); list++)
        {
            vertice = (*list).front();
            (*list).pop_front();
            cout << vertice << " --> ";
            for (auto item = (*list).begin(); item != (*list).end(); item++)
            {
                cout << (*item) << " ; ";
            }
            (*list).push_front(vertice);
            cout << endl;
        }
    }

    void Graph::fill_adjacency_matrix()
    {
        const int number_of_vertices = vertices.size();
        representations.adjacency_matrix = new int *;
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
        const int number_of_vertices = vertices.size();
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

}

int main()
{
    auto start = std::chrono::high_resolution_clock::now();

    string s = "{  (hi, hello), ( say, hellooo ), (fooo, baaar), (baaars, fooos) }";
    AboutGraphs::Graph *g1 = AboutGraphs::Graph::from_string(s);
    g1->show_successor_adjacency_list();
    cout << endl;
    g1->show_adjacency_matrix();
    cout << endl;
    cout << endl;

    s = "{  (hey, ola, 10), ( diga, oi, 5 ), ( falaaa, oi, 3)}";
    g1 = AboutGraphs::Graph::from_string(s);
    g1->show_successor_adjacency_list();
    cout << endl;
    g1->show_adjacency_matrix();
    cout << endl;
    cout << endl;

    auto end = std::chrono::high_resolution_clock::now();
    std::chrono::duration<double> duration = end - start;
    cout << "duration: " << duration.count() * 1000 << "ms\n";

    return EXIT_SUCCESS;
}