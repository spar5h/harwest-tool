import java.util.*;
import java.io.*;

/* spar5h */

public class swagdaddy implements Runnable { 
	 
	static class pair{
		
		int i, j;
		
		pair(int i, int j) {
			this.i = i; this.j = j;
		}
	}
	
	static ArrayList<pair>[][] adj;
	static char[][] a;
	static pair[][] parent;
	static int[][] vis, low, disc, res;
	static int count;
	
	static void DFS(int xi, int xj) {
		
		vis[xi][xj] = 1;
		low[xi][xj] = disc[xi][xj] = count++;
		int subCount = 0;
		
		for(int i = 0; i < adj[xi][xj].size(); i++) {
			
			int yi = adj[xi][xj].get(i).i;
			int yj = adj[xi][xj].get(i).j;
			
			if(vis[yi][yj] == 0) {
				
				subCount++;
				parent[yi][yj] = new pair(xi, xj);
				
				DFS(yi, yj);
				
				low[xi][xj] = Math.min(low[xi][xj], low[yi][yj]);
				
				if(!(parent[xi][xj].i == 0 && parent[xi][xj].j == 0) && low[xi][xj] >= disc[xi][xj])
					res[xi][xj] = 1;
			}
			
			else if(!(parent[xi][xj].i == yi && parent[xi][xj].j == yj))
				low[xi][xj] = Math.min(low[xi][xj], disc[yi][yj]);
		}
		
		if(parent[xi][xj].i == 0 && parent[xi][xj].j == 0 && subCount > 1)
			res[xi][xj] = 1;	
		
		//System.out.println(xi + " " + xj);
		//System.out.println(disc[xi][xj] + " " + low[xi][xj] + " " + res[xi][xj]);
	}
	
	public void run() {

		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);

		int t = 1;
		
		while(t-- > 0) {
			
			int n = s.nextInt();
			int m = s.nextInt();
			
			int areaCount = 0;
			int si = -1, sj = -1;
			
			a = new char[n + 1][m + 1];
			
			for(int i = 1; i <= n; i++)
				a[i] = (" " + s.next()).toCharArray();
			
			adj = new ArrayList[n + 1][m + 1];
			
			for(int i = 1; i <= n; i++) {
				
				for(int j = 1; j <= m; j++) {
					
					if(a[i][j] == '.')
						continue;
					
					adj[i][j] = new ArrayList<pair>();
					
					if(j - 1 >= 1 && a[i][j - 1] == '#')
						adj[i][j].add(new pair(i, j - 1));
					
					if(j + 1 <= m && a[i][j + 1] == '#')
						adj[i][j].add(new pair(i, j + 1));
					
					if(i - 1 >= 1 && a[i - 1][j] == '#')
						adj[i][j].add(new pair(i - 1, j));
					
					if(i + 1 <= n && a[i + 1][j] == '#')
						adj[i][j].add(new pair(i + 1, j));
					
					areaCount++;
					si = i; sj = j;
				}
			}
			
			if(areaCount <= 2) {
				w.println(-1); continue;
			}
			
			vis = new int[n + 1][m + 1];
			parent = new pair[n + 1][m + 1];
			low = new int[n + 1][m + 1];
			disc = new int[n + 1][m + 1];
			res = new int[n + 1][m + 1];
			count = 1;
			
			parent[si][sj] = new pair(0, 0);
			DFS(si, sj);
			
			int ans = 2;
			
			for(int i = 1; i <= n; i++)
				for(int j = 1; j <= m; j++)
					if(res[i][j] == 1)
						ans = 1;
			
			w.println(ans);
			
		}
		
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