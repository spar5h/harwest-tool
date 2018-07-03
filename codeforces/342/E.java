import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
/* spar5h */
 
public class CentroidDecomposition implements Runnable {    

	static ArrayList<Integer> adj[];
	static int time, h, tin[], tout[], anc[][], level[], cenCheck[], cenPar[], sub[], res[];
	
	static void dfs(int x, int p) {
		
		tin[x] = time++;
		
		anc[x][0] = p;
		
		for(int i = 1; i <= h; i++) 
			anc[x][i] = anc[anc[x][i - 1]][i - 1];
		
		for(int i = 0; i < adj[x].size(); i++) 
			if(adj[x].get(i) != p) {
				level[adj[x].get(i)] = level[x] + 1; dfs(adj[x].get(i), x); }
	
		tout[x] = time++;
	}
	
	static boolean isAnc(int u, int v) {
		
		if(tin[u] <= tin[v] && tout[v] <= tout[u])
			return true;
		
		return false;
	}
	
	static int lca(int u, int v) {
		
		if(isAnc(u, v))
			return u;
		
		if(isAnc(v, u))
			return v;
		
		for(int i = h; i >= 0; i--) 
			if(!isAnc(anc[u][i], v)) 
				u = anc[u][i];
			
		return anc[u][0];
	}
	
	static int dist(int u, int v) {
		
		return level[u] + level[v] - 2 * level[lca(u, v)];
	}
	
	static int dfs2(int x, int p) {
		
		sub[x] = 1;;
		
		for(int i = 0; i < adj[x].size(); i++)
			if(cenCheck[adj[x].get(i)] == 0 && adj[x].get(i) != p)
				sub[x] += dfs2(adj[x].get(i), x);
		
		return sub[x];
	}
	
	static int getCentroid(int x) {
		
		int p = -1, isCentroid = 0;
		
		int n = dfs2(x, -1);
		
		while(isCentroid == 0) {
			
			isCentroid = 1;
			
			for(int i = 0; i < adj[x].size(); i++) {
				
				if(cenCheck[adj[x].get(i)] == 0 && adj[x].get(i) != p && sub[adj[x].get(i)] > n / 2)
					isCentroid = 0;
			
				if(isCentroid == 0) {
					p = x; x = adj[x].get(i); break;
				}
			}
		}
		
		return x;	
	}
	
	static void decomp(int x, int cp) {
		
		x = getCentroid(x); cenCheck[x] = 1; cenPar[x] = cp;
		
		for(int i = 0; i < adj[x].size(); i++)
			if(cenCheck[adj[x].get(i)] == 0)
				decomp(adj[x].get(i), x);
	}
	
	static void update(int x) {
		
		int p = x;
		
		while(p != -1) {
			res[p] = Math.min(dist(p, x), res[p]); p = cenPar[p];
		}
	}
	
	static int query(int x) {
		
		int ans = Integer.MAX_VALUE, p = x;
		
		while(p != -1) {
			
			if(res[p] != Integer.MAX_VALUE) 
				ans = Math.min(dist(p, x) + res[p], ans);
			
			p = cenPar[p];
			
		}
		
		return ans;
	}
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = s.nextInt(), q = s.nextInt();
		
		h = (int)(Math.log(n) / Math.log(2));
		
		tin = new int[n + 1];
		tout = new int[n + 1];
		
		anc = new int[n + 1][h + 1];
		
		time = 0;
		
		adj = new ArrayList[n + 1];
		
		for(int i = 1; i <= n; i++)
			adj[i] = new ArrayList<Integer>();
		
		for(int i = 0; i < n - 1; i++) {
			
			int u = s.nextInt(), v = s.nextInt();
			adj[u].add(v); adj[v].add(u);
		}
		
		level = new int[n + 1];
		
		dfs(1, 1);
		
		cenCheck = new int[n + 1];
		cenPar = new int[n + 1];
		sub = new int[n + 1];
		
		decomp(1, -1);
		
		res = new int[n + 1]; Arrays.fill(res, Integer.MAX_VALUE);
		
		update(1);
		
		while(q-- > 0) {
			
			int k = s.nextInt();
			
			if(k == 1)
				update(s.nextInt());
			else
				w.println(query(s.nextInt()));
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
		new Thread(null, new CentroidDecomposition(),"CentroidDecomposition",1<<26).start();
	}
	   
} 