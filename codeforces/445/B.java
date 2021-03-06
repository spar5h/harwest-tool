import java.util.*;
import java.lang.*;
import java.io.*;

/* Name of the class has to be "Main" only if the class is public. */
 public class codeforces implements Runnable
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
		new Thread(null, new codeforces(),"codeforces",1<<26).start();
	}
	
	 static Stack<Integer> stack;
	    static ArrayList<Integer>[] adj;
	    static int[] a;
	    static int[] vis;
	    static int n;
	    
	    static int DFS(int v) {
		    
	    	int count = 0;
		    int[] listCount = new int[n + 1];
		    
		    stack = new Stack<Integer>();
		    
		    vis[v] = 1;
		    stack.push(v);
		    
		    while(!stack.isEmpty()) {
		        
		        int x = stack.peek();
		        
		        while(listCount[x] < adj[x].size()) {
		            
		            int y = adj[x].get(listCount[x]);
		            listCount[x]++;
		            
		            if(vis[y] == 0) {
		                
		                stack.push(y);
		                vis[y] = 1;
		                x = y;
		                count++;
		            }
		        }
		        
		        stack.pop();
		    }
		    
		    return count;
		    
		}
	
	public void run() {
	    
	    InputReader s = new InputReader(System.in);
	    PrintWriter w = new PrintWriter(System.out);
	    
	    n = s.nextInt();
		int m = s.nextInt();
		
		adj = new ArrayList[n + 1];
		vis = new int[n + 1];
	
	    for(int i = 1; i <= n; i++)
	        adj[i] = new ArrayList<Integer>();
	        
	    for(int i = 0; i < m; i++) {
	        
	        int n1 = s.nextInt();
	        int n2 = s.nextInt();
	        
	        adj[n1].add(n2);
	        adj[n2].add(n1);
	    }
	    
	    
	    int ans = 0;
	    
	    for(int i = 1; i <= n; i++) {
	    	
	    	if(vis[i] == 0)
	    		ans += DFS(i);
	    }
	    
	    w.println((long)Math.pow(2, ans));
	    
	    w.close();
	}
}