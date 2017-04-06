import java.util.*;
import java.lang.*;
import java.io.*;

/* Name of the class has to be "Main" only if the class is public. */
public class DFS implements Runnable
{
    
	static class InputReader
	{
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;
		private SpaceCharFilter filter;
		
	
		
		public InputReader(InputStream stream)
		{
			this.stream = stream;
		}
		
		public int read()
		{
			if (numChars==-1) 
				throw new InputMismatchException();
			
			if (curChar >= numChars)
			{
				curChar = 0;
				try 
				{
					numChars = stream.read(buf);
				}
				catch (IOException e)
				{
					throw new InputMismatchException();
				}
				
				if(numChars <= 0)				
					return -1;
			}
			return buf[curChar++];
		}
	 
		public String nextLine()
		{
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			String str = "";
            try
            {
                str = br.readLine();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return str;
		}
		public int nextInt()
		{
			int c = read();
			
			while(isSpaceChar(c)) 
				c = read();
			
			int sgn = 1;
			
			if (c == '-') 
			{
				sgn = -1;
				c = read();
			}
			
			int res = 0;
			do 
			{
				if(c<'0'||c>'9') 
					throw new InputMismatchException();
				res *= 10;
				res += c - '0';
				c = read();
			}
			while (!isSpaceChar(c)); 
			
			return res * sgn;
		}
		
		public long nextLong() 
		{
			int c = read();
			while (isSpaceChar(c))
				c = read();
			int sgn = 1;
			if (c == '-') 
			{
				sgn = -1;
				c = read();
			}
			long res = 0;
			
			do 
			{
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				res *= 10;
				res += c - '0';
				c = read();
			}
			while (!isSpaceChar(c));
				return res * sgn;
		}
		
		public double nextDouble() 
		{
			int c = read();
			while (isSpaceChar(c))
				c = read();
			int sgn = 1;
			if (c == '-') 
			{
				sgn = -1;
				c = read();
			}
			double res = 0;
			while (!isSpaceChar(c) && c != '.') 
			{
				if (c == 'e' || c == 'E')
					return res * Math.pow(10, nextInt());
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				res *= 10;
				res += c - '0';
				c = read();
			}
			if (c == '.') 
			{
				c = read();
				double m = 1;
				while (!isSpaceChar(c)) 
				{
					if (c == 'e' || c == 'E')
						return res * Math.pow(10, nextInt());
					if (c < '0' || c > '9')
						throw new InputMismatchException();
					m /= 10;
					res += (c - '0') * m;
					c = read();
				}
			}
			return res * sgn;
		}
		
		public String readString() 
		{
			int c = read();
			while (isSpaceChar(c))
				c = read();
			StringBuilder res = new StringBuilder();
			do 
			{
				res.appendCodePoint(c);
				c = read();
			} 
			while (!isSpaceChar(c));
			
			return res.toString();
		}
	 
		public boolean isSpaceChar(int c) 
		{
			if (filter != null)
				return filter.isSpaceChar(c);
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}
	 
		public String next() 
		{
			return readString();
		}
		
		public interface SpaceCharFilter 
		{
			public boolean isSpaceChar(int ch);
		}
	}
    
	public static void main(String args[]) throws Exception
	{
		new Thread(null, new DFS(),"DFS",1<<26).start();
	}
	
	static LinkedList<Integer> que;
    static ArrayList<Integer>[] adj;
    static ArrayList<Integer> set1, set2;
    static int[] a, vis;
    static int n;
    static boolean b;
    
   static void BFS(int v) {
	    
	    
	    que = new LinkedList<Integer>();

    	que.add(v);
    	set1.add(v);
    	vis[v] = 1;
    	a[v] = 1;
    	
    	outerloop:	
    	while(!que.isEmpty()) {
    			
    		int x = que.get(0);
    		
    		int d = 0;
    		
    		if(a[x] == 1)
    		    d = 2;
    		else
    		    d = 1;
    		    
    			for(int i = 0; i < adj[x].size(); i++) {
    					
    				int y = adj[x].get(i);	
    					
    				if(vis[y] == 0) {
    					
        				vis[y] = 1;
        				a[y] = d;
        				
        				if(d == 1)
        				    set1.add(y);
        				else
        				    set2.add(y);
        				
        				que.add(y);
        				
        			}
        			
        			else if(a[y] == a[x]){
        			    
        			    b = false;
        			    break outerloop;
        			}
    				
    			}
    			
    			que.remove(0);
    			
    		}
	    
	}
	
	public void run() {
	    
	    InputReader s = new InputReader(System.in);
	    PrintWriter w = new PrintWriter(System.out);
	    
	    n = s.nextInt();
		int m = s.nextInt();
		
		adj = new ArrayList[n + 1];
	    vis = new int[n + 1];
	    set1 = new ArrayList<Integer>();
	    set2 = new ArrayList<Integer>();
	
	    for(int i = 1; i <= n; i++)
	        adj[i] = new ArrayList<Integer>();
	        
	    for(int i = 0; i < m; i++) {
	        
	        int n1 = s.nextInt();
	        int n2 = s.nextInt();
	        
	        adj[n1].add(n2);
	        adj[n2].add(n1);
	    }
        
        a = new int[n + 1];
	    
	    b = true;
	    
	    for(int i = 1; i <= n; i++) {
	       
	       if(vis[i] == 0 && adj[i].size() > 0)
	            BFS(i);
	            
	        if(b == false)
	            break;
	    }
	    
	    if(b) {
	        
	        w.println(set1.size());
	        
	        for(int i = 0; i < set1.size(); i++)
	            w.print(set1.get(i) + " ");
	        
	        w.println();
	        w.println(set2.size());
	        
	        for(int i = 0; i < set2.size(); i++)
	            w.print(set2.get(i) + " ");
	            
	    }
	    
	    else {
	        
	        w.println(-1);
	    }
	    
	    w.close();
	}
}