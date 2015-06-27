# Ski Singapore

Written by [Matt Barry](mailto:mattbarry@gmail.com), June 2015

This is my solution to the Skiing in Singapore coding diversion that I found at the following URL: http://geeks.redmart.com/2015/01/07/skiing-in-singapore-a-coding-diversion/

Given a rectangular grid of map elevations, the challenge is to find the longest path down a ski hill. You can move in any of the four cardinal directions, but only to a point on the map with a lower elevation. If there are two paths down the ski hill with the same length, the one with the steepest drop should be selected.

My solution uses the following algorithm:

1. I create a [directed acyclic graph](https://en.wikipedia.org/wiki/Directed_acyclic_graph) representing the ski hill.
2. I perform a [topological ordering](https://en.wikipedia.org/wiki/Topological_sorting) of the vertices in the graph.
3. For each vertex in the topological ordering, I compute the length of the longest path ending at that vertex by looking at its incoming neighbours and adding one to the maximum length recorded for those neighbours. If there are no incoming neighbours, I set the length to zero. This value is stored in a HashMap so that I can quickly retrieve it later.
4. I find the vertices with the longest recorded values. These are the end vertices in all of the paths that I will consider.
5. For each of the end vertices, I recurse back up the graph using the paths with the longest lenths. I stop the recursion when I reach a vertex with a length of zero. This is the start vertex of a path that I will add to my collection.
6. I sort my collection of longest paths based on the steepest drop.
7. The first item in my collection of sorted paths should contain the correct solution.

