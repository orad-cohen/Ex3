package dataStructure;

import java.util.*;

public class DGraph implements graph{

	private int _id = 0;
	private int _mc = 0;

	HashMap<Integer,node_data> NodeMap = new HashMap<>();
	HashMap<Integer,HashMap<Integer,edge_data>> EdgeMap = new HashMap<>();
	HashMap<Integer, LinkedList<Integer>> Connected = new HashMap<>();

	@Override
	public node_data getNode(int key) {
		try{
			return NodeMap.get(key);
		}
		catch (Exception e){
			return null;
		}

	}//returns the hashmap

	@Override
	public edge_data getEdge(int src, int dest) {//returns the edge if exist.
		try {
			return EdgeMap.get(src).get(dest);
		}
		catch(Exception e){
			return null;
		}



	}

	@Override
	public void addNode(node_data n) {
		_mc++;
		Node _node;
		if(n.getKey()==Integer.MIN_VALUE){//if node doesnt have an id, assign one.
			_node = new Node(_id++,n);
		}
		else{
			_node = new Node(n);//else add with current id.
		}

		NodeMap.put(_node.getKey(), _node);
		
	}

	@Override
	public void connect(int src, int dest, double w) {
		_mc++;
		if(Connected.get(src)==null){//if its first connection, create linked list
			Connected.put(src, new LinkedList<>());
		}
		if(Connected.get(dest)==null){//if its first connection, create linked list
			Connected.put(dest, new LinkedList<>());
		}
		if(EdgeMap.get(src)==null){//if its first connection, create hashmap
			EdgeMap.put(src, new HashMap<>());
		}
		Edge x = new Edge(src,dest,w);//create the new edge
		Connected.get(src).add(dest);//add them to connect list
		Connected.get(dest).add(src);
		EdgeMap.get(src).put(dest, x);//add to connect hashmap.
	}

	@Override
	public Collection<node_data> getV() {


		return NodeMap.values();//return the values of hashmap
	}

	@Override
	public Collection<edge_data> getE(int node_id) {
		if (EdgeMap.containsKey(node_id)){//return the values of hashmap if it has any
			return EdgeMap.get(node_id).values();
		}
		else{
			return null;
		}

	}

	@Override
	public node_data removeNode(int key) {//remove nodes using the connected list
		_mc++;
		Connected.get(key);
		Iterator<Integer> Ite = Connected.get(key).iterator();
		while (Ite.hasNext()){
			int next = Ite.next();
			if(removeEdge(key, next)==null){
				removeEdge(next,key);
			}


		}
		return NodeMap.remove(key);

	}

	@Override
	public edge_data removeEdge(int src, int dest) {//remove edges from hashmap
		Connected.get(src).remove(dest);
		Connected.get(dest).remove(src);
		edge_data tmp = EdgeMap.get(src).remove(dest);

		return tmp;
	}


	@Override
	public int nodeSize() {

		return NodeMap.size();//return node size
	}

	@Override
	public int edgeSize() {

		return Connected.size()/2;//return Connected Size size;
	}

	@Override
	public int getMC() {
		// TODO Auto-generated method stub
		return _mc;
	}

}
