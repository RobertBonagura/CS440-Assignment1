
// Java program to print BFS traversal from a given source vertex. 
// BFS(int s) traverses vertices reachable from s. 
import java.io.*; 
import java.util.*; 
  

class Graph 
{ 
   int n;// 
   LinkedList<Integer> adj[]; //Adjacency Lists  
   // want it to have the row, col instead of integer or simply concatenate the two!
   /*        // Convert both the integers to string 
        String s1 = Integer.toString(a); 
        String s2 = Integer.toString(b); 
  
        // Concatenate both strings 
        String s = s1 + s2; 
  
        // Convert the concatenated string 
        // to integer 
        int c = Integer.parseInt(s);
    * */
  
    // Constructor 
    Graph(int n) 
    { 
        this.n = n; 
        adj = new LinkedList[n]; 
        for (int i= 0; i< n; ++i) 
            adj[i] = new LinkedList(); 
    } 
  
    // Function to add an edge into the graph 
    void addEdge(int v,int w) 
    { 
        adj[v].add(w); 
    }
    
    
    
    
    
    
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	       Graph g = new Graph(4);  // would be n 
	       
	        g.addEdge(0, 1); 
	        g.addEdge(0, 2); 
	        g.addEdge(1, 2); 
	        g.addEdge(2, 0); 
	        g.addEdge(2, 3); 
	        g.addEdge(3, 3); 
	        System.out.println("We added all nodes and edges");
	        System.out.println(01==1);
	        
	        /*Have a method that adds the 4 edges for each cell. i.e:
	         * 
	         * public addFourEdges(int cellNumber, int row, int col, int val, int maxRow, int maxCol){
	           		// let concatenated rowcol be the node value in the graph
	           		// let concatenated nextrowcol be the node pointed to
	           		
	           		// DOWN
	           		 if((row+val  >= 0) && (row+val  < maxRow)){
	           		  	nextrowcol = concat(row+val , col)
	           		 	g.addEdge(rowcol, nextrowcol)
	           		 }UP
	           		 //
	           		 if((row-val  >= 0) && (row-val  < maxRow)){
	           		  	nextrowcol = concat(row-val , col)
	           		 	g.addEdge(rowcol, nextrowcol)
	           		 }
	           		 //Right
	           		  if((col+val  >= 0) && (col+val < maxCol)){
	           		  nextrowcol = concat(row, col+val )
	           		  g.addEdge(rowcol, nextrowcol)
	           		 }
	           		//Left
	           		  if((col-val  >= 0) && (col-val < maxCol)){
	           		  nextrowcol = concat(row, col-val )
	           		  g.addEdge(rowcol, nextrowcol)
	           		 }
	         * }
	         * NOTE: GOAL CELL is = to maxRowmaxCol concatenated!! OR...USE CELL NUMBER FROM GUI???
	         * 
	         * */
	}

}


