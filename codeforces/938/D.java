 import java.util.*;
    import java.io.*;
     
    /* spar5h */
     
    public class swagdaddy implements Runnable { 
    
    	static class pair {
    		
    		int i; long wt;
    		
    		pair(int i, long wt) {
    			this.i = i; this.wt = wt;
    		}
    	}
    	
    	static class comp implements Comparator<pair> {
    		
    		public int compare(pair x, pair y) {
    			
    			if(x.wt < y.wt)
    				return -1;
    			
    			if(x.wt > y.wt)
    				return 1;
    			
    			return 0;
    		}
    	}
    	
    	public void run() {
     
    		InputReader s = new InputReader(System.in);
    		PrintWriter w = new PrintWriter(System.out);
    	
    		int n = s.nextInt(), m = s.nextInt();
    		
    		ArrayList<Integer>[] adj = new ArrayList[n + 1];
    		ArrayList<Long>[] wt = new ArrayList[n + 1];
    		
    		for(int i = 1; i <= n; i++) {
    			adj[i] = new ArrayList<Integer>();
    			wt[i] = new ArrayList<Long>();
    		}
    		
    		while(m-- > 0) {
    			
    			int u = s.nextInt(), v = s.nextInt(); long dist = s.nextLong();
    			adj[u].add(v); adj[v].add(u);
    			wt[u].add(dist); wt[v].add(dist);
    		}
    		
    		long[] cost = new long[n + 1];
    		
    		for(int i = 1; i <= n; i++)
    			cost[i] = s.nextLong();
    		
    		int[] vis = new int[n + 1];
    		
    		PriorityQueue<pair> pq = new PriorityQueue<pair>(11, new comp());
    		
    		for(int i = 1; i <= n; i++)
    			pq.add(new pair(i, cost[i]));
    		
    		while(!pq.isEmpty()) {
    			
    			int x = pq.peek().i;
    			pq.poll();
    			
    			if(vis[x] == 1)
    				continue;
    			
    			vis[x] = 1;
    			
    			for(int i = 0; i < adj[x].size(); i++) {
    				
    				int y = adj[x].get(i);
    				long edge = wt[x].get(i);
    				
    				if(vis[y] == 0 && cost[y] > 2 * edge + cost[x]) {
    					
    					cost[y] = 2 * edge + cost[x];
    					pq.add(new pair(y, cost[y]));
    				}
    			}
    		}
    		
    		for(int i = 1; i <= n; i++)
    			w.print(cost[i] + " ");
    		
    		w.println();
    		
    		w.close();
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
    		new Thread(null, new swagdaddy(),"swagdaddy",1<<26).start();
    	}
    	   
    }  
 