import java.util.*;
import java.lang.*;
import java.io.*;

/* Name of the class has to be "Main" only if the class is public. */
 public class DFS implements Runnable
{
    static Queue<Integer> queue;
    static ArrayList<Integer>[] adj;
    static int[] a;
    static int[] vis;
    static long countE, countV;
    static int n;
    
    static void BFS(int end) {
	    
	    queue = new LinkedList<Integer>();
	    
	    vis[end] = 1;
	    queue.add(end);
	    countV = 0; 
	    countE = 0;
	    
	    outerloop:
	    while(!queue.isEmpty()) {
	        
	        int x = queue.poll();
	        countV++;
	        
	        for(int i = 0; i < adj[x].size(); i++) {
	            
	            countE++;
	            int y = adj[x].get(i);
	            
	            if(vis[y] == 0) {
	                
	                queue.add(y);
	                vis[y] = 1;
	            }
	            
	        }
	        
	    }
	    
	    countE /= 2;
	    
	}
    
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
	
	public void run() {
	    
	    InputReader s = new InputReader(System.in);
	    PrintWriter w = new PrintWriter(System.out);
	    
	    n = s.nextInt();
		int e = s.nextInt();
		
		adj = new ArrayList[n + 1];
	
	    for(int i = 1; i <= n; i++)
	        adj[i] = new ArrayList<Integer>();
	        
	    for(int i = 0; i < e; i++) {
	        
	        int n1 = s.nextInt();
	        int n2 = s.nextInt();
	        
	        adj[n1].add(n2);
	        adj[n2].add(n1);
	    }
	    
	    vis = new int[n + 1];
	    
	    String ans = "YES";
	    
	    outerloop:
	    for(int i = 1; i <= n; i++) {
	        
	        if(vis[i] == 0) {
	            
	            BFS(i);
	           
	           if(countE != (countV * (countV - 1)) / 2) {
	               
	               ans = "NO";
	               break outerloop;
	           }
	        }
	        
	    }
	    
	    w.println(ans);
	    
	    w.close();
	}
}