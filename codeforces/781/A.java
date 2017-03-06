/* package codechef; // don't place package name! */

import java.util.*;
import java.lang.*;
import java.io.*;

/* Name of the class has to be "Main" only if the class is public. */
public class Solution
{
    public static int n;
    public static ArrayList<Integer>[] adj;
    public static int[] color, gp, p;
    
    public static void addEdge(int n1, int n2) {
        
        adj[n1].add(n2);
        adj[n2].add(n1);
    }
    
    
	public static void main (String[] args) throws java.lang.Exception {
	    
	    Scanner s = new Scanner(System.in);
	    
	    n = s.nextInt();
	    
	    adj = new ArrayList[n + 1];
	    
	     for(int i = 1; i <= n; i++)
            adj[i] = new ArrayList<Integer>();
	    
	    for(int i = 0; i < n - 1; i++)
	        addEdge(s.nextInt(), s.nextInt());
	        
	    color = new int[n + 1];     
	    gp = new int[n + 1];
	    p = new int[n + 1];
	    
	    boolean[] vis = new boolean[n + 1];
	    vis[1] = true;
	    
	    Queue<Integer> que = new LinkedList<Integer>();
    
    	que.add(1);
    	color[1] = 1;	   
	    int colorMax = 1;
    		
    	while(!que.isEmpty()) {
    			
    		int x = que.poll();
    		
    		int colorVal = 1;
    			
	        for(int i = 0; i < adj[x].size(); i++) {
	        
	            int y = adj[x].get(i);
	            
	            if(vis[y] == false) {
	                
	                que.add(y);
                    p[y] = x;
                    
	                while(colorVal == color[x] || colorVal == color[p[x]]) {
	                    colorVal++;
	                }
	                
	                color[y] = colorVal;
	                colorVal++;
	                vis[y] = true;
	            }
	        
	        }
    			
    		if(colorVal - 1 > colorMax)
	            colorMax = colorVal - 1;	
    	}
	 
	    System.out.println(colorMax);
	    
	    for(int i = 1; i <=n; i++)
	        System.out.print(color[i] + " ");
	}
}
