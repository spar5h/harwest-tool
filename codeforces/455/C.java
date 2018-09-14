import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

public class cf3 implements Runnable{    
	
	static class pair {
		
		int i, j;
		
		pair(int i, int j) {
			this.i = i; this.j = j;
		}
	}
	
	public void run() {
	    
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = s.nextInt(), m = s.nextInt(), q = s.nextInt();
		
		ArrayList<Integer>[] adj = new ArrayList[n + 1];
		
		for(int i = 1; i <= n; i++)
			adj[i] = new ArrayList<Integer>();
		
		for(int i = 0; i < m; i++) {
			int u = s.nextInt(), v = s.nextInt();
			adj[u].add(v); adj[v].add(u);
		}
		
		Queue<Integer> que = new LinkedList<Integer>(); que.add(1);
		
		int[] color = new int[n + 1];
		int curr = 1;
		
		int[] level = new int[n + 1]; 
		Arrays.fill(level, -1); 
		
		int[] diam = new int[n + 1];
		
		for(int i = 1; i <= n; i++) {
			
			if(color[i] != 0)
				continue;
			
			que.add(i); color[i] = curr;
			int leaf = -1;
			
			while(!que.isEmpty()) {
				
				int x = que.poll();
				leaf = x;
				
				for(int j = 0; j < adj[x].size(); j++) {
					
					int y = adj[x].get(j);
					
					if(color[y] == 0) {
						color[y] = curr; que.add(y);
					}
				}
			}
			
			que.add(leaf); level[leaf] = 0;
			
			while(!que.isEmpty()) {
				
				int x = que.poll();
				diam[curr] = level[x];
				
				for(int j = 0; j < adj[x].size(); j++) {
					
					int y = adj[x].get(j);
					
					if(level[y] == -1) {
						level[y] = level[x] + 1; que.add(y);
					}
				}
			}
			
			curr++;
		}
		
		ArrayList<Integer>[] adjCol = new ArrayList[n + 1];
		
		for(int i = 1; i <= n; i++)
			adjCol[i] = new ArrayList<Integer>();
		
		for(int i = 1; i <= n; i++)
			adjCol[color[i]].add(i);
		
		while(q-- > 0) {
			
			int key = s.nextInt();
			
			if(key == 2) {
				
				int u = s.nextInt(), v = s.nextInt();
				
				int small = color[u], big = color[v];
				
				if(small == big)
					continue;
				
				if(adjCol[small].size() > adjCol[big].size()) {
					small = color[v]; big = color[u]; 
				}
				
				for(int i = 0; i < adjCol[small].size(); i++) {
					int y = adjCol[small].get(i);
					adjCol[big].add(y); color[y] = big;
				}
				
				diam[big] = max(max(diam[big], diam[small]), (diam[big] + 1) / 2 + (diam[small] + 1) / 2 + 1);
			}
			
			else
				w.println(diam[color[s.nextInt()]]);
			
		}
		
		w.close();
	}
	
	static class InputReader {
		
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
		new Thread(null, new cf3(),"cf3",1<<26).start();
	}
}   

