import java.io.*;
import java.util.*;



public class Graph {
    
    static class point {
    
        int i = 0;
        int j = 0;
    }

	public static ArrayList<point>[][] adj;
	public static int[][] l;
	public static String[][] direct;
	public static String[][] path;
	
    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
 
    	Scanner s = new Scanner(System.in);
    	
    	String start = s.next();
    	String end = s.next();
    	
    	point p1 = new point();
    	point p2 = new point();
    	
    	p1.i = ('8' - start.charAt(1));
        p1.j = start.charAt(0) - 'a';
        
        p2.i = ('8' - end.charAt(1));
        p2.j = end.charAt(0) - 'a';
        
    	Graph g = new Graph(8);
    	
    	for(int i = 0; i < 8; i++) {
    		
    		for(int j = 0; j < 8; j++) {
    		    
    		    point p;
    		    
    		    if(i > 0 && j > 0) {
    		        
    		        p = new point();
    		        p.i = i - 1;
    		        p.j = j - 1;
    		        adj[i][j].add(p);
    		    }
    		    
    		    if(i > 0) {
    		        
    		        p = new point();
    		        p.i = i - 1;
    		        p.j = j;
    		        adj[i][j].add(p);
    		    }
    		    
    		    if(i > 0 && j < 7) {
    		        
    		        p = new point();
    		        p.i = i - 1;
    		        p.j = j + 1;
    		        adj[i][j].add(p);
    		    }
    		    
    		    if(i < 7 && j > 0) {
    		        
    		        p = new point();
    		        p.i = i + 1;
    		        p.j = j - 1;
    		        adj[i][j].add(p);
    		    }
    		    
    		    if(i < 7) {
    		        
    		        p = new point();
    		        p.i = i + 1;
    		        p.j = j;
    		        adj[i][j].add(p);
    		    }
    		    
    		    if(i < 7 && j < 7) {
    		        
    		        p = new point();
    		        p.i = i + 1;
    		        p.j = j + 1;
    		        adj[i][j].add(p);
    		    }
    		    
    		    if(j > 0) {
    		        
    		        p = new point();
    		        p.i = i;
    		        p.j = j - 1;
    		        adj[i][j].add(p);
    		    }
    		    
    		    if(j < 7) {
    		        
    		        p = new point();
    		        p.i = i;
    		        p.j = j + 1;
    		        adj[i][j].add(p);
    		    }
    		        
    		}
    	}
    	
    	direct = new String[3][3];
    	direct[0][0] = "LU ";
    	direct[0][1] = "U ";
    	direct[0][2] = "RU ";
    	direct[1][0] = "L ";
    	direct[1][2] = "R ";
    	direct[2][0] = "LD ";
    	direct[2][1] = "D ";
    	direct[2][2] = "RD ";
    	
    	BFS(p1, p2);
    	
    	
    }
    
    Graph(int n) {
    	
    	adj = new ArrayList[n][n];
        
        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++)
    		    adj[i][j] = new ArrayList<point>();
    }
    
    public static void BFS(point p1, point p2) {
    
        l = new int[8][8];
        path = new String[8][8];
        
        ArrayList<point> que = new ArrayList<point>();
    		
    		l[p1.i][p1.j] = 1;
    		path[p1.i][p1.j] = "";
    		que.add(p1);
    		
    		outerloop:
    		while(!que.isEmpty()) {
    			
    			point p = que.get(0);
    			
    			for(int k = 0; k < adj[p.i][p.j].size(); k++) {
    					
    					point q = adj[p.i][p.j].get(k);
    					
    					
    					if(l[q.i][q.j] == 0) {
    						
        					l[q.i][q.j] = 1 + l[p.i][p.j];
        					path[q.i][q.j] = path[p.i][p.j] + direct[q.i - p.i + 1][q.j - p.j + 1];
        					que.add(q);
        				}
        				
        				if(q.i == p2.i && q.j == p2.j)
    				    break outerloop;
    				}
    				
    				
    				
    				que.remove(0);
    			}
    			
    		    System.out.println(l[p2.i][p2.j] - 1);
    		    System.out.println(path[p2.i][p2.j]);
    			
    		}
    		
    	
    
   
}