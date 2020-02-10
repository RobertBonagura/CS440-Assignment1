
/*Inspired by Graph Chapter from previous Data Structure course (CS112)
 * */

import java.util.ArrayList;
public class DirGraph<int>(

 Arraylist<Vertex<int>> adjlists; // array of Vertex objects

pubhc 0irGraph() { adjlists .., new Arraylist<:Vertex,T>.,.O;
public DirGraph(int vertexCap) {
adjli sts = new ArrayL i stc...Vertex<T ..> .. (vertexCap);
I
public int number0fVertices() i
return adjlists.size();
}
public int addVert:ex(T vertex)
l
if (!containsVertex(vertex)) adjlists.add(new Vertex(vertex));
return adjlists.size()-1;
public boolean containsVertex(T vertex) {
return adjlists.index0f(new Vertex(vertex)) I= -1;
}
public int vertexNumberOf(T vertex)
return adjlists.index0f(new Vertex(vertex));
I
publ1c T vertexinfo0f(int vertexNumber) I
Vertex<T v = adjlists.get(vertexNumber); return v.info;
I
public boolean containsEdge(1nt vertexNumber, Ne19hbor nbr)
Vertex T v = adjlists.get(vert:exNumber);
return v.neighbors.contains(nbr);
I
public void addEdge(int vertexNumber, Ne1ghbor nbr) {
Vertex<T􀄺 fromVertex • adjlists.get(vertexNumber);
if (lfromVertex.neighbors.contains(nbr)) {
fromVertex.neighbors.add(nbr);
}
public Neighbor firstNeighbor(int vertexNumber)
Vertex T · v = adjlists.get(vert:exNumber);
return v. neighbors.first() ;
I
public Neighbor nextNeighbor(1nt vert:exNumber)
Vertex T · v "' adjl i sts. get (vertexNumber);
return v.neighbors.next();
public void clear() I adjlists.clear(); }


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
