package org.example.Game.Classes;

import org.example.Structures.Implementations.ArrayUnorderedList;
import org.example.Structures.Implementations.Graph;
import org.example.Structures.Implementations.Network;

public class FlagGameWGraph<T> extends Network<T> {

    public FlagGameWGraph() {
        super();
    }

    public void replaceVertex(T oldVertex, T newVertex) {
        int index = getIndex(oldVertex);
        if (indexIsValid(index)) {
            vertices[index] = newVertex;
        } else {
            throw new IllegalArgumentException("Vertex does not exist");
        }
    }

    public boolean containsVertex(T vertex) {
        for (int i = 0; i < this.numVertices; i++) {
            if (vertices[i].equals(vertex)) {
                return true;
            }
        }
        return false;
    }

    private boolean edgeExists(int indexFrom, int indexTo) {
        return networkAdjMatrix[indexFrom][indexTo] != Double.POSITIVE_INFINITY || networkAdjMatrix[indexTo][indexFrom] != Double.POSITIVE_INFINITY;
    }

    public boolean containsEdge(T from, T to) {
        int indexFrom = getIndex(from);
        int indexTo = getIndex(to);
        return indexIsValid(indexFrom) && indexIsValid(indexTo) && edgeExists(indexFrom, indexTo);
    }

    public ArrayUnorderedList<T> getAllVertices() {
        ArrayUnorderedList<T> vertexList = new ArrayUnorderedList<>();
        for (int i = 0; i < numVertices; i++) {
            vertexList.addToRear(vertices[i]);
        }
        return vertexList;
    }

    public double[][] getEdges() {
        return networkAdjMatrix;
    }

    private void checkVertexExists(T vertex) {
        if (!containsVertex(vertex)) {
            throw new IllegalArgumentException("Vertex doesn't exist");
        }
    }

    public ArrayUnorderedList<T> getNeighbors(T vertex) {
        checkVertexExists(vertex);
        ArrayUnorderedList<T> neighbors = new ArrayUnorderedList<>();
        int index = getIndex(vertex);
        for (int i = 0; i < numVertices; i++) {
            if (networkAdjMatrix[index][i] != Double.POSITIVE_INFINITY) {
                neighbors.addToRear(vertices[i]);
            }
        }
        return neighbors;
    }

    public boolean indexIsValid(int index) {
        return index >= 0 && index < numVertices;
    }

    @SuppressWarnings("unchecked")
    public void updateWithMST(Graph<Location> mst) {
        for (Object vertex : mst.getVertices()) {
            if (vertex instanceof Location) {
                this.addVertex((T) vertex);
            } else {
                System.out.println("Vertex isn't a Location");
            }
        }
    }

    private void appendNeighbors(StringBuilder result, int vertexIndex) {
        for (int j = 0; j < numVertices; j++) {
            if (networkAdjMatrix[vertexIndex][j] != Double.POSITIVE_INFINITY) {
                result.append(vertices[j]).append(" ");
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < numVertices; i++) {
            result.append(vertices[i]).append(" -> ");
            appendNeighbors(result, i);
            result.append("\n");
        }
        return result.toString();
    }

    private void validateVertexIndices(int indexFrom, int indexTo) {
        if (!indexIsValid(indexFrom) || !indexIsValid(indexTo)) {
            throw new IllegalArgumentException("Invalid vertices");
        }
    }

    public double getEdgeWeight(T from, T to) {
        int indexFrom = getIndex(from);
        int indexTo = getIndex(to);
        validateVertexIndices(indexFrom, indexTo);
        return networkAdjMatrix[indexFrom][indexTo];
    }
}


