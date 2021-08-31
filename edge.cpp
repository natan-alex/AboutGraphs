#include <iostream>
#include <string>
#include <regex>

#include "edge.h"

using namespace std;

namespace AboutGraphs
{

    Edge *const Edge::EMPTY_EDGE = new Edge();

    const regex Edge::PATTERN_TO_VALIDATE_AN_EDGE = regex("[(|{]\\s*\\w+\\s*,(\\s*\\w+\\s*,)?\\s*\\w+\\s*[)|}]");

    bool Edge::is_edge_directed()
    {
        return is_directed;
    }

    bool Edge::is_edge_pondered()
    {
        return is_pondered;
    }

    string &Edge::get_string_representation()
    {
        return string_representation;
    }

    int Edge::get_edge_value()
    {
        return value;
    }

    string &Edge::get_first_vertice()
    {
        return first_vertice;
    }

    string &Edge::get_second_vertice()
    {
        return second_vertice;
    }

    Edge *Edge::from_string(string &edge_representation)
    {
        if (regex_search(edge_representation, PATTERN_TO_VALIDATE_AN_EDGE) == false)
        {
            return EMPTY_EDGE;
        }

        Edge *edge = new Edge();
        edge->string_representation = edge_representation;

        if (!edge->is_edge_enclosed_correctly())
        {
            return EMPTY_EDGE;
        }

        edge->fill_properties_of_the_edge();
        return edge;
    }

    bool Edge::is_edge_enclosed_correctly()
    {
        char opening_char = string_representation[0];
        char closing_char = string_representation[string_representation.length() - 1];

        return ((opening_char == '(' && closing_char == ')') || (opening_char == '{' && closing_char == '}'));
    }

    void Edge::fill_properties_of_the_edge()
    {
        int first_comma_index = string_representation.find_first_of(',');
        int second_comma_index;

        first_vertice = string_representation.substr(1, first_comma_index - 1);

        if (count(string_representation.begin(), string_representation.end(), ',') == 2)
        {
            is_pondered = true;
            second_comma_index = string_representation.find_last_of(',');
            second_vertice = string_representation.substr(first_comma_index + 1, second_comma_index - first_comma_index - 1);
            value = stoi(string_representation.substr(second_comma_index + 1, string_representation.length() - second_comma_index - 2));
        }
        else
        {
            is_pondered = false;
            second_vertice = string_representation.substr(first_comma_index + 1, string_representation.length() - first_comma_index - 2);
        }

        do_inplace_trim(first_vertice);
        do_inplace_trim(second_vertice);

        if (string_representation[0] == '(')
            is_directed = true;
        else
            is_directed = false;
    }

    void Edge::do_inplace_trim(string &s)
    {
        do_inplace_left_trim(s);
        do_inplace_right_trim(s);
    }

    void Edge::do_inplace_left_trim(string &s)
    {
        int index = 0;

        while (s[index] == ' ')
        {
            index++;
        }

        s.erase(0, index);
    }

    void Edge::do_inplace_right_trim(string &s)
    {
        int index = s.length() - 1;

        while (s[index] == ' ')
        {
            index--;
        }

        s.erase(index + 1, s.length() - index);
    }

}