import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

//hm.get((long)0) != hm.get(0)

public class cf5 implements Runnable{    
	
	static class pair {
		
		int i, j;
		
		pair(int i, int j) {
			this.i = i; this.j = j;
		}
	}
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = s.nextInt(), m = s.nextInt(), k = s.nextInt();
		
		HashSet<Integer>[] adj = new HashSet[n + 1];
		
		for(int i = 1; i <= n; i++)
			adj[i] = new HashSet<Integer>();
		
		ArrayList<pair> edge = new ArrayList<pair>();
		
		for(int i = 0; i < m; i++) {
			
			int u = s.nextInt(), v = s.nextInt();
			adj[u].add(v); adj[v].add(u);
			edge.add(new pair(u, v));
		}
		
		int[] dead = new int[n + 1];
		
		for(int i = 1; i <= n; i++) {
			
			if(dead[i] == 1 || adj[i].size() >= k)
				continue;
			
			dead[i] = 1;
			
			Queue<Integer> q = new LinkedList<Integer>(); q.add(i);
			
			while(!q.isEmpty()) {
				
				int x = q.poll();
				
				for(int y : adj[x]) {
					
					adj[y].remove(x);
					
					if(dead[y] == 0 && adj[y].size() < k) {
						dead[y] = 1; q.add(y);
					}
						
				}
				
				adj[x].clear();
			}
		}
		
		int[] res = new int[m]; int count = 0;
		
		for(int i = 1; i <= n; i++)
			if(dead[i] == 0)
				count++;
		
		for(int i = m - 1; i >= 0; i--) {
			
			res[i] = count;
			
			int u = edge.get(i).i, v = edge.get(i).j;
			
			if(dead[u] == 1 || dead[v] == 1)
				continue;
			
			Queue<Integer> q = new LinkedList<Integer>();
			
			adj[u].remove(v);
			
			if(adj[u].size() < k) {
				dead[u] = 1; q.add(u); count--;
			}
			
			adj[v].remove(u);
			
			if(adj[v].size() < k) {
				dead[v] = 1; q.add(v); count--;
			}
			
			while(!q.isEmpty()) {
				
				int x = q.poll();
				
				for(int y : adj[x]) {
					
					adj[y].remove(x);
					
					if(dead[y] == 0 && adj[y].size() < k) {
						dead[y] = 1; q.add(y); count--;
					}
						
				}
				
				adj[x].clear();
			}
			
		}
		
		for(int i = 0; i < m; i++)
			w.print(res[i] + " ");
		
		w.println();
		
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
		new Thread(null, new cf5(),"cf5",1<<26).start();
	}
}   