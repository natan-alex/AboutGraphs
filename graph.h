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
            string *successor_adjacency_vector_start;
            string *successor_adjacency_vector_end;
            string *predecessor_adjacency_vector_start;
            string *predecessor_adjacency_vector_end;
        };

        ~Graph();

        static Graph *from_string(string &s);

        void show_predecessor_adjacency_list();
        void show_successor_adjacency_list();
        void show_adjacency_matrix();
        void show_incidency_matrix();
        void show_predecessor_adjacency_vectors();
        void show_successor_adjacency_vectors();

    private:
        string string_representation;
        list<Edge *> edges;
        set<string> vertices;
        list<int> edge_values;
        bool is_graph_pondered;
        bool is_graph_directed;
        GraphStatistics statistics;
        GraphRepresentations representations;

        void fill_edge_list_and_vertice_set();
        void fill_graph_properties();
        void fill_representations();
        void fill_successor_adjacency_list();
        void fill_predecessor_adjacency_list();
        void fill_adjacency_matrix();
        void fill_incidency_matrix();
        void fill_successor_adjacency_vectors();
        void fill_predecessor_adjacency_vectors();
        void fill_adjacency_vectors(string start_vector[], string end_vector[]);
        void order_adjacency_vectors(string which_to_sort[], string other_vector[], size_t size);

        void check_if_the_graph_is_directed_based_on_the_statistics();
        void check_if_the_graph_is_pondered_based_on_the_statistics();
        void check_if_there_are_directed_and_undirected_edges_at_the_same_time();
        void check_if_there_are_pondered_and_unpondered_edges_at_the_same_time();

        void show_adjacency_list(list<list<string>> adjacency_list);
        int find_the_index_of_the_vertice(string &vertice);
    };
}

#endif