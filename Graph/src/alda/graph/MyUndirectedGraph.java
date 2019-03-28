package alda.graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public class MyUndirectedGraph<T> implements UndirectedGraph<T> {

	// måste man direkt överskugga equals och hashcode om man använder hashMap?
	// Kommer nedan funka för andra typer av objekt?
	// eller är det säkrare att ha ett nodobjekt som nyckel och definiera en egen
	// equals? Måste bara overrida om man ska använda Vertex som nyckel kanske?

	// varför inte List direkt istf Vertex? s.381
	private Map<T, Vertex<T>> myGraph = new HashMap<>();

	private LinkedList<T> path = new LinkedList<>();

	private Stack<T> stack = new Stack<>();

	// dist defaults to -1;
	private static class Vertex <T> {

		// eller arrayList??
		List<Edge<T>> edges = new LinkedList<>();

		boolean visited = false;

		int dist = -1;

		T path = null;

		private boolean isVisited() {
			return visited;
		}

		private void setPath(T path) {
			this.path = path;
		}

	}

	private static class Edge<T> {

		T dest;

		int cost;

		Edge(T dest, int cost) {
			this.cost = cost;
			this.dest = dest;
		}

		void updateCost(int cost) {
			this.cost = cost;
		}

		public String toString() {
			return dest.toString();
		}

	}

	public int getNumberOfNodes() {

		return myGraph.size();
	}

	public int getNumberOfEdges() {

		int sum = 0;

		for (Map.Entry<T, Vertex<T>> entry : myGraph.entrySet()) {
			sum += entry.getValue().edges.size();
		}

		return sum / 2;

	}

	public void printGraph() {

		for (Map.Entry<T, Vertex<T>> entry : myGraph.entrySet()) {

			System.out.println("Nod: " + entry.getKey().toString());

			System.out.println("Edges: ");

			for (Edge<T> edge : entry.getValue().edges) {
				System.out.print(edge.toString() + ", ");
			}
			System.out.println();

		}
	}

	public boolean add(T newNode) {

		if (myGraph.containsKey(newNode)) {
			return false;
		}

		else {
			myGraph.put(newNode, new Vertex<T>());
			return true;
		}

	}

	// tillåt inte multigrafer??

	// kan göra enklare? typ skippa for-loopen?

	// true om båda noderna finns i grafen och kan kopplas ihop.
	public boolean connect(T node1, T node2, int cost) {

		if (cost <= 0 || !myGraph.containsKey(node1) || !myGraph.containsKey(node2)) {
			return false;
		}

		// if connect a node to itself
		if (node1.equals(node2)) {

			// if node already connected to itself - update cost
			for (Edge<T> node1Edge : myGraph.get(node1).edges) {
				if (node1Edge.dest.equals(node2)) {
					node1Edge.updateCost(cost);
					return true;
				}
			}

			// connect a node with itself
			connectNodes(node1, node2, cost);
			return true;
		}

		// if node1 and node2 are not the same.
		// Updates cost if already connected

		List<Edge<T>> nodeEdges = myGraph.get(node1).edges;

		for (Edge<T> node1Edge : nodeEdges) {
			if (node1Edge.dest.equals(node2)) {
				node1Edge.updateCost(cost);

				nodeEdges = myGraph.get(node2).edges;

				for (Edge<T> node2Edge : nodeEdges) {
					if (node2Edge.dest.equals(node1)) {
						node2Edge.updateCost(cost);
						return true;
					}
				}
			}
		}

		// connect nodes if node1 and node2 are not the same node and not connected
		// before
		connectNodes(node1, node2, cost);

		return true;

	}

	private void connectNodes(T node1, T node2, int cost) {

		List<Edge<T>> node1Edges = myGraph.get(node1).edges;
		node1Edges.add(new Edge<T>(node2, cost));

		if (!node1.equals(node2)) {

			List<Edge<T>> node2Edges = myGraph.get(node2).edges;
			node2Edges.add(new Edge<T>(node1, cost));
		}
	}

	public boolean isConnected(T node1, T node2) {

		for (Edge<T> node1Edge : myGraph.get(node1).edges) {
			if (node1Edge.dest.equals(node2)) {
				return true;
			}
		}

		return false;
	}

	public int getCost(T node1, T node2) {

		if (myGraph.containsKey(node1) && myGraph.containsKey(node2)) {
			for (Edge<T> node1Edge : myGraph.get(node1).edges) {
				if (node1Edge.dest.equals(node2)) {
					return node1Edge.cost;
				}
			}
		}

		return -1;
	}

	public T getUnvisitedNeighbour(T node) {

		for (Edge<T> edge : myGraph.get(node).edges) {

			Vertex<T> neighbour = myGraph.get(edge.dest);

			if (neighbour.visited == false) {
				return edge.dest;
			}
		}

		return null;

	}

	private void resetVertices() {
		for (Vertex<T> vertex : myGraph.values()) {
			vertex.visited = false;
			vertex.path = null;
			vertex.dist = -1;
		}
	}

	public List<T> depthFirstSearch(T start, T end) {

		resetVertices();

		markVisited(start);
		stack.push(start);

		if (start.equals(end)) {
			makeListFromStack();
		}

		while (!stack.empty()) {

			T checking = getUnvisitedNeighbour(stack.peek());

			while (checking != null) {

				if (checking.equals(end)) {

					stack.push(checking);
					makeListFromStack();
					return path;
				}

				else {
					markVisited(checking);
					stack.push(checking);
					checking = getUnvisitedNeighbour(checking);
				}
			}

			if (!stack.empty())
				stack.pop();
		}
		return path;
	}

	private void makeListFromStack() {

		// måste man ha i rätt ordning? Nu startar den från end om path inte är
		// linkedlist...
		while (!stack.empty()) {
			path.addFirst(stack.pop());
		}

	}

	private void markVisited(T node) {
		myGraph.get(node).visited = true;
	}

	
	
	//nu används aldrig distance, men kanske kommer i den dijkstras?
	public List<T> breadthFirstSearch(T start, T end) {

		resetVertices();

		path.clear();

		Queue<T> queue = new LinkedList<>();

		queue.add(start);
		markVisited(start);

		T checking;

		while (!queue.isEmpty()) {

			checking = queue.remove();

			if (checking.equals(end)) {

				makeListFromPaths(checking);
				return path;

			}

			else

				for (Edge<T> edge : myGraph.get(checking).edges) {

					Vertex<T> edgeVertex = myGraph.get(edge.dest);

					// gör koll för att hitta lägsta dist istället ??
					if (!edgeVertex.isVisited()) {
						queue.add(edge.dest);
						edgeVertex.setPath(checking);
						markVisited(edge.dest);
					}

				}

		}

		return null;

	}

	private void makeListFromPaths(T node) {
		T toBeAdded = node;

		while (toBeAdded != null) {
			path.addFirst(toBeAdded);

			toBeAdded = myGraph.get(toBeAdded).path;

		}

	}

	private void updateDistance(T node, int distance) {
		myGraph.get(node).dist = distance;

	}

	public UndirectedGraph<T> minimumSpanningTree() {

		return null;
	}

}
