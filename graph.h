#ifndef GRAPH_H
#define GRAPH_H

#include <list>
#include <set>

#include "edge.h"

using namespace std;

namespace AboutGraphs
{
    class Graph
    {
    public:
        struct GraphStatistics
        {
            int number_of_pondered_edges;
            int number_of_unpondered_edges;
            int number_of_directed_edges;
            int number_of_undirected_edges;
        };

        struct GraphRepresentations
        {
            int **adjacency_matrix;
            int **incidency_matrix;
            list<list<string>> successor_adjacency_list;
            list<list<string>> predecessor_adjacency_list;
            int *successor_adjacency_array_start;
            int *successor_adjacency_array_end;
            int *predecessor_adjacency_array_start;
            int *predecessor_adjacency_array_end;
        };

        struct DeepSearchStructures
        {
            int *discovery_times;
            int *end_times;
        };

        ~Graph();

        static optional<Graph> from_string(string &s);

        void show_predecessor_adjacency_list();
        void show_successor_adjacency_list();
        void show_adjacency_matrix();
        void show_incidency_matrix();
        void show_predecessor_adjacency_arrays();
        void show_successor_adjacency_arrays();
        void show_all_representations();
        void show_deep_search_structures();

        void make_deep_search_and_compute_times_in_arrays();

    private:
        string string_representation;
        list<Edge> edges;
        int number_of_edges;
        set<string> vertices;
        int number_of_vertices;
        list<int> edge_values;
        bool is_graph_pondered;
        bool is_graph_directed;
        GraphStatistics statistics;
        GraphRepresentations representations;
        DeepSearchStructures deepSearchStructures;

        const static regex PATTERN_TO_VALIDATE_A_DIRECTED_UNPONDERED_GRAPH;
        const static regex PATTERN_TO_VALIDATE_AN_UNDIRECTED_UNPONDERED_GRAPH;
        const static regex PATTERN_TO_VALIDATE_A_DIRECTED_PONDERED_GRAPH;
        const static regex PATTERN_TO_VALIDATE_AN_UNDIRECTED_PONDERED_GRAPH;

        void fill_edge_list_and_vertice_set();
        void fill_representations();
        void fill_successor_adjacency_list();
        void fill_predecessor_adjacency_list();
        void show_adjacency_list(list<list<string>> adjacency_list);

        void fill_adjacency_matrix();
        int find_the_index_of_the_vertice(string &vertice);
        void fill_incidency_matrix();

        int first_index_of_item_in_array(int item, int array[], int array_size);

        void fill_adjacency_arrays();
        void reorder_successor_adjacency_arrays();
        void reorder_predecessor_adjacency_arrays();
        void order_adjacency_arrays(int which_to_sort[], int other_array[], size_t size);
        int* reorder_and_return_predecessor_adjacency_array();
        int* reorder_and_return_successor_adjacency_array();
        void show_adjacency_arrays_increasing_their_values_by_one(int start_array[], int start_array_size, int end_array[], int end_array_size);
        void show_vertices_set();

        void initialize_deep_search_structures();
    };
}

#endif
