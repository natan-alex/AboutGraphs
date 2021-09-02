#ifndef EDGE_H
#define EDGE_H

#include <iostream>
#include <string>
#include <regex>

using namespace std;

namespace AboutGraphs
{

    class Edge
    {
    private:
        string string_representation;
        string first_vertice;
        string second_vertice;
        int value;
        bool is_directed;
        bool is_pondered;

        bool is_edge_enclosed_correctly();

        void fill_properties_of_the_edge();

        void do_inplace_trim(string &s);
        void do_inplace_left_trim(string &s);
        void do_inplace_right_trim(string &s);

    public:
        static Edge *const EMPTY_EDGE;
        static const regex PATTERN_TO_VALIDATE_AN_EDGE;

        static Edge *from_string(string &edge_representation);

        bool is_edge_pondered();
        bool is_edge_directed();
        string &get_string_representation();
        string &get_first_vertice();
        string &get_second_vertice();
        int get_edge_value();
    };

}

#endif