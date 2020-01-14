package algorithms;

import dataStructure.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import utils.Point3D;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;


/**
 * This empty class represents the set of graph-theory algorithms
 * which should be implemented as part of Ex2 - Do edit this class.
 * @author 
 *
 */
public class Graph_Algo implements graph_algorithms{

	public graph _graph;


	public Graph_Algo(){

	}
	public Graph_Algo(graph g){
		init(g);
	}


	@Override
	public void init(graph g) {
		this._graph=g;
		
	}

	@Override
	public void init(String file_name) {
		graph g = new DGraph();
		JSONParser parser = new JSONParser();
		try{
			Reader reader = new FileReader(file_name);//get file into reader And set up all the JSON var
			JSONObject jsonObject = (JSONObject) parser.parse(reader);
			JSONArray NodeKey = (JSONArray)jsonObject.get("NodeKey");
			JSONArray NodeLoc =  (JSONArray)jsonObject.get("NodeLoc");
			JSONArray NodeWeight =  (JSONArray)jsonObject.get("NodeWeight");
			JSONArray NodeTag = (JSONArray)jsonObject.get("NodeTag");
			JSONArray NodeInfo = (JSONArray)jsonObject.get("NodeInfo");
			JSONArray EdgeSrc =  (JSONArray)jsonObject.get("EdgeSrc");
			JSONArray EdgeDest = (JSONArray)jsonObject.get("EdgeDest");
			JSONArray EdgeWeight = (JSONArray)jsonObject.get("EdgeWeight");
			JSONArray EdgeTag = (JSONArray)jsonObject.get("EdgeTag");
			JSONArray EdgeInfo = (JSONArray)jsonObject.get("EdgeInfo");
			Iterator<Integer> NodeIte= NodeKey.iterator();

			int i = 0;
			double weight;
			int tag;
			int key;
			while (NodeIte.hasNext()){//add the nodes one by one.
				NodeIte.next();
				weight = Double.parseDouble(NodeWeight.get(i).toString());
				tag = Integer.parseInt(NodeTag.get(i).toString());
				key = Integer.parseInt(NodeKey.get(i).toString());
				Node N = new Node(key,(String)NodeLoc.get(i),weight,tag,(String)NodeInfo.get(i));
				g.addNode(N);
				i++;
			}
			i=0;
			int src;
			int dest;
			int nextsrc=Integer.parseInt(EdgeSrc.get(i).toString());
			int CurEdge = 0;
			while (i<EdgeSrc.size()){//get the edges

				src =Integer.parseInt(EdgeSrc.get(i).toString());
				if(nextsrc==src){//starts connecting, when new src is found set the tag and info of previous edges/
					weight = Double.parseDouble(EdgeWeight.get(i).toString());
					dest = Integer.parseInt(EdgeDest.get(i).toString());
					g.connect(src,dest,weight);
					CurEdge++;
					i++;
				}
				else{
					Iterator<edge_data> Ite = g.getE(nextsrc).iterator();
					while(CurEdge>0){
						edge_data Current = Ite.next();
						tag = Integer.parseInt(EdgeTag.get(i-CurEdge).toString());
						Current.setTag(tag);
						Current.setInfo((String)EdgeInfo.get(i-CurEdge));
						CurEdge--;
					}

				}

				nextsrc=src;
			}

			init(g);



		}
		catch (Exception e){
			e.printStackTrace();

		}
		
	}

	@Override
	public void save(String file_name) {
		//init the json objects
		JSONObject Obj = new JSONObject();
		JSONArray NodeKey = new JSONArray();
		JSONArray NodeLoc = new JSONArray();
		JSONArray NodeWeight = new JSONArray();
		JSONArray NodeTag = new JSONArray();
		JSONArray NodeInfo = new JSONArray();
		JSONArray EdgeSrc = new JSONArray();
		JSONArray EdgeDest = new JSONArray();
		JSONArray EdgeWeight = new JSONArray();
		JSONArray EdgeTag = new JSONArray();
		JSONArray EdgeInfo = new JSONArray();
		Iterator<node_data> IteNode = _graph.getV().iterator();

		while(IteNode.hasNext()){//for ever nodes, add details to a JSON array
			node_data next = IteNode.next();
			NodeKey.add(next.getKey());
			NodeLoc.add(next.getLocation().toString());
			NodeWeight.add(next.getWeight());
			NodeTag.add(next.getTag());
			NodeInfo.add(next.getInfo());
			if(_graph.getE(next.getKey())!=null){// if there are edges, add those too.
				Iterator<edge_data> IteEdge = _graph.getE(next.getKey()).iterator();
				while (IteEdge.hasNext()){
					edge_data nextedge = IteEdge.next();
					EdgeSrc.add(nextedge.getSrc());
					EdgeDest.add(nextedge.getDest());
					EdgeWeight.add(nextedge.getWeight());
					EdgeTag.add(nextedge.getTag());
					EdgeInfo.add(nextedge.getInfo());

				}
			}
		}
		Obj.put("NodeKey", NodeKey);//put in object and save.
		Obj.put("NodeLoc",NodeLoc);
		Obj.put("NodeWeight",NodeWeight);
		Obj.put("NodeTag",NodeTag);
		Obj.put("NodeInfo",NodeInfo);
		Obj.put("EdgeSrc",EdgeSrc);
		Obj.put("EdgeDest",EdgeDest);
		Obj.put("EdgeWeight",EdgeWeight);
		Obj.put("EdgeTag",EdgeTag);
		Obj.put("EdgeInfo", EdgeInfo);

		try (FileWriter file = new FileWriter(file_name)) {
			file.write(Obj.toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}

	@Override
	public boolean isConnected() {
		Iterator<node_data> SrcIte = _graph.getV().iterator();

		while(SrcIte.hasNext()){//Iterate through every source
			if(!isConnected(SrcIte.next().getKey())){
				return false;	}

		}


		return true;
	}

	public boolean isConnected(int src){
		try{
				Stack<node_data> NodeStack = new Stack<>();
				node_data source=_graph.getNode(src);
				NodeStack.push(source);

				source.setTag(0);
				String srcchar = "isConnected:"+src;//prepare the  info it use so it would know on which src it works on
				source.setInfo(srcchar);
				try{
					Iterator<edge_data> iterator= _graph.getE(NodeStack.peek().getKey()).iterator();//if there is a only one node, return true;
					}
				catch (Exception e){
					return _graph.getV().size()==1;
				}

				Iterator<edge_data> edges = _graph.getE(NodeStack.peek().getKey()).iterator();
				edge_data nextedge= edges.next();
					int t = 1;
					while(!NodeStack.empty()){

						if(_graph.getE(NodeStack.peek().getKey())==null){//if we reached a node without edges, by defenition false.
							return false;
							//get iterator for current edges
						}
						else if(!edges.hasNext()){//if we reached the end of the edge list, pop (go to the previous node) and try again
							NodeStack.pop();
							if(!NodeStack.isEmpty()){
								edges = _graph.getE(NodeStack.peek().getKey()).iterator();
							}


							//get iterator for current edges
						}


						if(_graph.getNode(nextedge.getDest()).getInfo()=="CTL"){// if we reached a node who has been checked before, by defenition its true,
							return true;
						}

						else if(_graph.getNode(nextedge.getDest()).getInfo()!=srcchar){//node that hasn't been visited
							NodeStack.push(_graph.getNode(nextedge.getDest()));//add it to stack
							_graph.getNode(NodeStack.peek().getKey()).setInfo(srcchar);//change info
							source.setTag(source.getTag()+1);//increase the tag on source node to mark it has been visited.
							nextedge.setInfo(srcchar);//change info of edge to. to mark it has been visited.
							edges = _graph.getE(NodeStack.peek().getKey()).iterator();
							if(edges.hasNext()){
								nextedge = edges.next();
							}


						}
						else if(!nextedge.getInfo().equals(srcchar)){//if we didnt go through that path yet, check it for new nodes.
							NodeStack.push(_graph.getNode(nextedge.getDest()));//push to track node.
							_graph.getNode(NodeStack.peek().getKey()).setInfo(srcchar);

							nextedge.setInfo(srcchar);
							edges = _graph.getE(NodeStack.peek().getKey()).iterator();
							if(edges.hasNext()){
								nextedge = edges.next();
							}

						}
						else if(edges.hasNext()){
							nextedge = edges.next();
						}

						}




			if(_graph.getV().size()-1==source.getTag()){//if we visited all the node tag true.
				source.setInfo("CTL");//set info as connected to all.
				return true;
			}
			else{
				return false;
			}

				}
				catch (Exception e){
					e.printStackTrace();
					return false;
				}

	}

	public double shortestPathDist(int src, int dest) {
		try {
			Stack<node_data> nodeStack = new Stack<>();

			node_data start = _graph.getNode(src);
			start.setWeight(0);
			nodeStack.push(start);
			Iterator<edge_data> edge = _graph.getE(nodeStack.peek().getKey()).iterator();
			edge_data nextedge;
			node_data p1;
			node_data p2;
			while (!nodeStack.empty()) {

				if(_graph.getE(nodeStack.peek().getKey())==null||nodeStack.peek().getKey()==dest){
					nodeStack.pop();//if the top node has no edges or an edge that leads to the destination node
					edge = _graph.getE(nodeStack.peek().getKey()).iterator();//pop it and ignore, then go to to the next
					continue;//edge
				}
				nextedge = edge.next();//starts with an (possibly next one) edge from the node
				p1 = nodeStack.peek();//p1 is the current top node
				p2 = _graph.getNode(nextedge.getDest());//p2 is the node that the p1 edge leads to

				if(p2.getWeight()>(p1.getWeight()+Distance(p1.getLocation(),p2.getLocation()))){//if p2 weight current weight is bigger then the weight of p1+distance of edge
					p2.setWeight(p1.getWeight()+Distance(p1.getLocation(),p2.getLocation()));//set p1+distance as the new weight of p2
					nodeStack.push(p2);//then push that node to the node stack
					if(_graph.getE(nodeStack.peek().getKey())==null){//if the new top node of stack, p2 has no edges...
						nodeStack.pop();//kill it from the stack... it has no further service!
						edge = _graph.getE(nodeStack.peek().getKey()).iterator();//return back to the edges of the previous node in stack
					}
					else{//if p2 has edges then set iterator to iterate through the new edges of the new top node p2
						edge = _graph.getE(nodeStack.peek().getKey()).iterator();
					}


				}
				else if(edge.hasNext()){//if the weight of p2 is smaller or the same then carry on as normal and move to the next edge
					continue;//IF IT HAS A NEXT EDGE
				}

				else{//if it's out of edges then proceed to POP a cap in that B**** A** and carry on to the next node in the stack
					nodeStack.pop();
					if(nodeStack.empty()){//if node stack is empty then carry on, the loop requirements shall deal with it...
						continue;
					}
					else{
						edge = _graph.getE(nodeStack.peek().getKey()).iterator();
					}


				}


			}
			return _graph.getNode(dest).getWeight();
		}
		catch (Exception e){
			e.printStackTrace();
			return Double.POSITIVE_INFINITY;}
	}

	@Override
	public List<node_data> shortestPath(int src, int dest)  {
		try {
			RemoveTags();
			Stack<node_data> nodeStack = new Stack<>();//creates stack of nodes

			node_data start = _graph.getNode(src);//sets src start as the firsy node
			start.setWeight(0);//gives it personal weight of 0
			nodeStack.push(start); //pushes it to the stack
			Iterator<edge_data> edge = _graph.getE(nodeStack.peek().getKey()).iterator();//creates iterator for all the
			edge_data nextedge;//edges of the current top node in stack
			node_data p1;
			node_data p2;
			LinkedList<node_data> list = new LinkedList<node_data>();
			Boolean reached = false;//in case of reaching dest
			int saved = -1;//saves the node at 'crossroad' and sets reached to false until the node is returned to it in stack
			while (!nodeStack.empty()) {//while the stack is not empty

				if(_graph.getE(nodeStack.peek().getKey())==null||nodeStack.peek().getKey()==dest){
					nodeStack.pop();//if the top node has no edges or an edge that leads to the destination node
					edge = _graph.getE(nodeStack.peek().getKey()).iterator();//pop it and ignore, then go to to the next
					continue;//edge
				}
				nextedge = edge.next();//starts with an (possibly next one) edge from the node
				p1 = nodeStack.peek();//p1 is the current top node
				p2 = _graph.getNode(nextedge.getDest());//p2 is the node that the p1 edge leads to

				if(p2.getWeight()>(p1.getWeight()+Distance(p1.getLocation(),p2.getLocation()))){
					//if p2 weight current weight is bigger then the weight of p1+distance of edge
					p2.setWeight(p1.getWeight()+Distance(p1.getLocation(),p2.getLocation()));//set p1+distance as the new weight of p2
					nodeStack.push(p2);//then push that node to the node stack
					if(p2.getKey() == dest){//if the node at end of edge is edge AND is the shortest one
						reached = true;//set reached true
						list.clear();//clear the list
						list.add(p2);//add dest to list
						}
					if(_graph.getE(nodeStack.peek().getKey())==null){//if the new top node of stack, p2 has no edges...
						nodeStack.pop();//kill it from the stack... it has no further service!
						edge = _graph.getE(nodeStack.peek().getKey()).iterator();//return back to the edges of the previous node in stack
					}
					else{//if p2 has edges then set iterator to iterate through the new edges of the new top node p2
						edge = _graph.getE(nodeStack.peek().getKey()).iterator();
					}


				}
				else if(edge.hasNext()){//if the weight of p2 is smaller or the same then carry on as normal and move to the next edge
					if(reached == true) {//crossroad, if this node connects with an ongoing list then...
						reached = false;//save the node and set reached to false until returned to the node when it is out of edges
						saved = p1.getKey();
					}
					continue;//IF IT HAS A NEXT EDGE
				}

				else{//if it's out of edges then proceed to POP a cap in that B**** A** and carry on to the next node in the stack
					if(reached == false && saved > -1 && p1.getKey()==saved){//if reached is false and there is a "saved node" with no more edges
						saved = -1;//unset save
						reached = true;//and return reached to true to continue the adding of nodes
					}
					if(reached == true) {//if reached is true with no more edges then add it to the list before poping the stack
						list.addFirst(p1);
					}
					nodeStack.pop();
					if(nodeStack.empty()){//if node stack is empty then carry on, the loop requirements shall deal with it...
						continue;
					}
					else{//if the stack still has nodes then set the iterator to work for the new toppest node
						edge = _graph.getE(nodeStack.peek().getKey()).iterator();
					}
				}
			}
			if(list.size() == 0){return null;}
			else {
				return list;//return the list
			}
		}
		catch (Exception e){
			e.printStackTrace();
			return null;}
	}

	@Override
	public List<node_data> TSP(List<Integer> targets) {
		Stack<node_data> NodeStack = new Stack<>();//set up stack and src codes
		String sourcekey = "TSP:"+ targets.get(0);
		NodeStack.push(_graph.getNode( targets.get(0)));
		NodeStack.peek().setInfo("TV");//set info as visited target
		targets.remove(0);
		Iterator<edge_data> EdgIte = _graph.getE(NodeStack.peek().getKey()).iterator();
		edge_data CurEdge = EdgIte.next();
		while(!targets.isEmpty()){//while we havn't found it all

			if(targets.contains(NodeStack.peek().getKey())){
				if(_graph.getE(NodeStack.peek().getKey())==null){//if we got to a target but the target has no edges, null by default
					return null;
				}
				NodeStack.peek().setInfo("TV");//mark as Target Visited
				targets.remove(Integer.valueOf(NodeStack.peek().getKey()));//remove from target list;

			}
			String s =_graph.getNode(CurEdge.getDest()).getInfo();//for athsetitcs
			if(s.equals(sourcekey)){//if visited/
				if(EdgIte.hasNext()){//if it has more edge, go to that edge
					CurEdge = EdgIte.next();
				}
				else{
					if(NodeStack.peek().getInfo()=="TV"){//if it has no more edges, but we are in a TV, than null
						return null;
					}

					NodeStack.pop();//else go back.
					EdgIte = _graph.getE(NodeStack.peek().getKey()).iterator();
				}
			}
			else if(s.equals("TV")){//if we reached a target
				if(EdgIte.hasNext()){//go to different edge or go back.
					CurEdge = EdgIte.next();
				}
				else{
					NodeStack.pop();
					EdgIte = _graph.getE(NodeStack.peek().getKey()).iterator();
				}

			}
			else{//else its unvisited node, addjust info accordingly.
				NodeStack.push(_graph.getNode(CurEdge.getDest()));
				NodeStack.peek().setInfo(""+sourcekey);
				if(_graph.getE(NodeStack.peek().getKey())==null){
					NodeStack.pop();
				}
				else{
					EdgIte = _graph.getE(NodeStack.peek().getKey()).iterator();
				}

			}}
		return NodeStack;}



	@Override
	public graph copy() {//uses the save function for hard copy.
		save("forCopy");
		Graph_Algo Copy = new Graph_Algo();
		Copy.init("forCopy");


		return Copy._graph;
	}

	public void RemoveTags(){
		Iterator<node_data> Nodes = _graph.getV().iterator();
		while(Nodes.hasNext()){
			node_data next = Nodes.next();
			next.setTag(0);
			next.setWeight(Double.POSITIVE_INFINITY);
		}

	}
	public void RemoveEdgeTags(int x){
		Iterator<edge_data> Edges = _graph.getE(x).iterator();
		while(Edges.hasNext()){
			Edges.next().setTag(0);
		}

	}
	public double Distance(Point3D p1, Point3D p2){
		return  p1.distance3D(p2);
	}


}
