package com.landmark.android;

/**
 * To find the missing currency
 */
public class BellmanFord {
    private double distances[];
    //as of now there are only 3 units(INR,AER,SAR)
    private int numberofvertices = 3;
    private static final int MAX_VALUE = 999;
    private int source;
    private double adjacencymatrix[][];

    public BellmanFord(int source, double[][] adjacencymatrix) {
        distances = new double[numberofvertices + 1];
        this.adjacencymatrix = adjacencymatrix;
        this.source = source;

        for (int sourcenode = 1; sourcenode <= numberofvertices; sourcenode++) {
            for (int destinationnode = 1; destinationnode <= numberofvertices; destinationnode++) {
                if (sourcenode == destinationnode) {
                    this.adjacencymatrix[sourcenode][destinationnode] = 0;
                    continue;
                }
                if (this.adjacencymatrix[sourcenode][destinationnode] == 0) {
                    this.adjacencymatrix[sourcenode][destinationnode] = MAX_VALUE;
                }
            }
        }
    }
    //Gives me the converted value
    public double[] getConversion(){
        return BellmanFordEvaluation(source, adjacencymatrix);
    }

    private double[] BellmanFordEvaluation(int source, double adjacencymatrix[][]) {
        for (int node = 1; node <= numberofvertices; node++) {
            distances[node] = MAX_VALUE;
        }

        distances[source] = 0;
        for (int node = 1; node <= numberofvertices - 1; node++) {
            for (int sourcenode = 1; sourcenode <= numberofvertices; sourcenode++) {
                for (int destinationnode = 1; destinationnode <= numberofvertices; destinationnode++) {
                    if (adjacencymatrix[sourcenode][destinationnode] != MAX_VALUE) {
                        if (distances[destinationnode] > distances[sourcenode]
                                + adjacencymatrix[sourcenode][destinationnode])
                            distances[destinationnode] = distances[sourcenode]
                                    + adjacencymatrix[sourcenode][destinationnode];
                    }
                }
            }
        }

        //print the matrix of units
        for (int sourcenode = 1; sourcenode <= numberofvertices; sourcenode++) {
            for (int destinationnode = 1; destinationnode <= numberofvertices; destinationnode++) {
                if (adjacencymatrix[sourcenode][destinationnode] != MAX_VALUE) {
                    if (distances[destinationnode] > distances[sourcenode] + adjacencymatrix[sourcenode][destinationnode])
                        System.out.println("The Graph contains negative egde cycle");
                }
            }
        }

        for (int vertex = 1; vertex <= numberofvertices; vertex++) {
            System.out.println("distance of source  " + source + " to "
                    + vertex + " is " + distances[vertex]);
        }

        return distances;
    }
}