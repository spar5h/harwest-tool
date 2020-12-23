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
	
	static Stack<Integer> stack;
    static ArrayList<Integer>[] adj;
    static int[] vis;
    static int n, m;
    
    static void DFS(int start) {
	    
	    int[] listCount = new int[n + 1];
	    
	    stack = new Stack<Integer>();
	    
	    vis[start] = 1;
	    stack.push(start);
	    
	    outerloop:
	    while(!stack.isEmpty()) {
	        
	        int x = stack.peek();
	        
	        while(listCount[x] < adj[x].size()) {
	            
	            int y = adj[x].get(listCount[x]);
	            listCount[x]++;
	            
	            if(vis[y] == 0) {
	                
	                stack.push(y);
	                vis[y] = 1;
	                x = y;
	                
	                if(y == start)
	                    break outerloop;
	                    
	                continue;         
	            }
	        }
	        
	        stack.pop();
	    }
	    
	}
	
	public void run() {
	    
	    InputReader s = new InputReader(System.in);
	    PrintWriter w = new PrintWriter(System.out);
	    
	    n = s.nextInt();
		m = s.nextInt();
		
		adj = new ArrayList[n];
	
	    for(int i = 0; i < n; i++)
	        adj[i] = new ArrayList<Integer>();
	        
	    int a[][] = new int[n][m + 1];     
	     
	    boolean b = true; 
	        
	    for(int i = 0; i < n; i++) {
	        
	        int k = s.nextInt();
	        
	        for(int j = 0; j < k; j++) {
	            
	            int x = s.nextInt();
	            a[i][x] = 1;
	        }
	        
	        if(k != 0)
	            b = false;

	    }
	    
	    for(int i = 0; i < n; i++) {

	        int added[] = new int[n]; 
	        
	        for(int j = 1; j <= m; j++) {

                if(a[i][j] == 1) {
                    
                    for(int k = 0; k < n; k++) {
                        
                        if(a[k][j] == 1 && i != k && added[k] == 0) {
                            
                            added[k] = 1;
                            adj[i].add(k);
                        } 
                    }
                }
	        }

	    }
	   
	    vis = new int[n];     
	    int cost = -1;      
	        
	    for(int i = 0; i < n; i++) {
	        
	        if(vis[i] == 0) {
	           
	           DFS(i);
	           cost++;
	        }
	        
	    }
	    
	    if(b)
	        cost++;
	    
	    w.println(cost);
	    
	    w.close();
	}
}