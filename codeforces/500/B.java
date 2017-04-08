import java.util.*;
import java.lang.*;
import java.io.*;

/* Name of the class has to be "Main" only if the class is public. */
 public class codeforces implements Runnable {
    
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
	
	static int[] ans;
	static int[] a;
	static int n;
	static ArrayList<Integer>[] adj;
	
	static void DFS(int v) {
	    
	    int[] vis = new int[n];
	    int[] listCount = new int[n];
	    ArrayList<Integer> spicy = new ArrayList<Integer>();
	    ArrayList<Integer> dank = new ArrayList<Integer>();
	    Stack<Integer> stack = new Stack<Integer>();
	    
	    vis[v] = 1;
	    stack.push(v);
	    dank.add(a[v]);

	    while(!stack.isEmpty()) {
	        
	        int x = stack.peek();
	        
	        while(listCount[x] < adj[x].size()) {
	            
	            int y = adj[x].get(listCount[x]);
	            listCount[x]++;
	            
	            if(vis[y] == 0) {
	                
	                stack.push(y);
	                dank.add(a[y]);
	                vis[y] = 1;
	                x = y;
	                    
	                continue;         
	            }
	        }
	        
	        stack.pop();
	    }
	    
	    Collections.sort(dank);
	    
	    int dankCount = 0;
	    
	    for(int i = 0; i < n; i++) {
	    	
	    	if(vis[i] == 1) {
	    		
	    		ans[i] = dank.get(dankCount);
	    		dankCount++;
	    	}
	    }
	    
	}
    
	public void run() {
	    
	    InputReader s = new InputReader(System.in);
	    PrintWriter w = new PrintWriter(System.out);
	    
	    n = Integer.parseInt(s.next());
		
		a = new int[n];
		ans = new int[n];
		Arrays.fill(ans, -1);
		
		adj = new ArrayList[n];
		
		for(int i = 0; i < n; i++)
		    a[i] = Integer.parseInt(s.next());
		    
		for(int i = 0; i < n; i++)
		    adj[i] = new ArrayList<Integer>();
		    
		for(int i = 0; i < n; i++) {
		    
		    String str = s.next();
		    
		    for(int j = 0; j < n; j++) {
		        
		          if(str.charAt(j) == '1')
		            adj[i].add(j);
		    }
		        
		} 
   
		for(int i = 0; i < n; i++) {
		    
		    if(ans[i] == -1)
		        DFS(i);
		}    
	
		
		for(int i = 0; i < n; i++)
	        w.print(ans[i] + " ");
	    
	    w.close();
	}
}