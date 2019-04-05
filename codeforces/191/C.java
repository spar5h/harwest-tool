import java.util.*;
import java.io.*;
import java.math.BigInteger;
import static java.lang.Math.*;
 
/* spar5h */
 
public class cf1 implements Runnable {   
	
	int time;
	int[] tin, tout;
	int[][] anc;
	ArrayList<Integer>[] adj;
	
	void init(int n) {
		
		time = 0;
		tin = new int[n];
		tout = new int[n];
		int k = (int)ceil(log(n) / log(2)) + 1;
		anc = new int[n][k];
	}
	
	void dfs(int x, int p) {
		
		tin[x] = time++;
		
		anc[x][0] = p;
		
		for(int i = 1; i < anc[x].length; i++)
			anc[x][i] = anc[anc[x][i - 1]][i - 1];
		
		for(int y : adj[x])	
			if(y != p)
				dfs(y, x);
		
		tout[x] = time++;
	}
	
	boolean isAnc(int u, int v) {
		return (tin[u] <= tin[v] && tout[v] <= tout[u]);
	}
	
	int lca(int u, int v) {
		
		if(isAnc(u, v))
			return u;
		
		if(isAnc(v, u))
			return v;
		
		for(int i = anc[u].length - 1; i >= 0; i--)
			if(!isAnc(anc[u][i], v))
				u = anc[u][i];
		
		return anc[u][0];
	}
	
	ArrayList<Integer>[] initArrayList(int n) {
		
		ArrayList<Integer>[] adj = new ArrayList[n];
		
		for(int i = 0; i < n; i++)
			adj[i] = new ArrayList<Integer>();
		
		return adj;
	}
	
	int[] dp;
	
	void dfs2(int x, int p) {
		
		for(int y : adj[x]) {
			if(y != p) {
				dfs2(y, x);
				dp[x] += dp[y];
			}
		}
	}	
	
	class Pair {
		
		int u, v;
		
		Pair(int u, int v) {
			this.u = u;
			this.v = v;	
		}
	}
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = s.nextInt();
		
		adj = initArrayList(n);
		
		Pair[] edge = new Pair[n - 1];
		
		for(int i = 0; i < n - 1; i++) {
			int u = s.nextInt() - 1, v = s.nextInt() - 1;
			edge[i] = new Pair(u, v);
			adj[u].add(v);
			adj[v].add(u);
		}
		
		init(n);
		
		dfs(0, 0);
		
		dp = new int[n];
		
		int q = s.nextInt();
		
		while(q-- > 0) {
			
			int u = s.nextInt() - 1, v = s.nextInt() - 1;
			
			dp[u]++;
			dp[v]++;
			dp[lca(u, v)]-= 2;
		}
		
		dfs2(0, 0);
		
		for(int i = 0; i < n - 1; i++)
			if(isAnc(edge[i].u, edge[i].v))
				w.print(dp[edge[i].v] + " ");
			else
				w.print(dp[edge[i].u] + " ");
		
		w.println();
		
 		w.close();
	}
	
	static class InputReader {
		
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;
		private SpaceCharFilter filter;
		
		public InputReader(InputStream stream) {
			this.stream = stream;
		}
		
		public int read() {
			
			if (numChars==-1) 
				throw new InputMismatchException();
			
			if (curChar >= numChars) {
				
				curChar = 0;
				
				try {
					numChars = stream.read(buf);
				}
				catch (IOException e) {
					throw new InputMismatchException();
				}
				
				if(numChars <= 0)				
					return -1;
			}
			
			return buf[curChar++];
		}
		 
		public String nextLine() {
			
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			String str = "";
	        
			try {
	            str = br.readLine();
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	        }
			
	        return str;
		}
		
		public int nextInt() {
		
			int c = read();
			
			while(isSpaceChar(c)) 
				c = read();
			
			int sgn = 1;
			
			if (c == '-') {
				sgn = -1;
				c = read();
			}
			
			int res = 0;
			
			do {
				if(c<'0'||c>'9') 
					throw new InputMismatchException();
				
				res *= 10;
				res += c - '0';
				c = read();
			}
			while (!isSpaceChar(c)); 
			
			return res * sgn;
		}
		
		public long nextLong() {
			
			int c = read();
			
			while (isSpaceChar(c))
				c = read();
			
			int sgn = 1;
			
			if (c == '-') {
				sgn = -1;
				c = read();
			}
			
			long res = 0;
			
			do {
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				
				res *= 10;
				res += c - '0';
				c = read();
			}
			
			while (!isSpaceChar(c));
				return res * sgn;
		}
		
		public double nextDouble() {
			
			int c = read();
			
			while (isSpaceChar(c))
				c = read();
			
			int sgn = 1;
			
			if (c == '-') {
				sgn = -1;
				c = read();
			}
			
			double res = 0;
			
			while (!isSpaceChar(c) && c != '.') {
				
				if (c == 'e' || c == 'E')
					return res * Math.pow(10, nextInt());
				
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				
				res *= 10;
				res += c - '0';
				c = read();
			}
			
			if (c == '.') {
				
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
		
		public String readString() {
			
			int c = read();
			
			while (isSpaceChar(c))
				c = read();
			
			StringBuilder res = new StringBuilder();
			
			do {
				res.appendCodePoint(c);
				c = read();
			} 
			while (!isSpaceChar(c));
			
			return res.toString();
		}
	 
		public boolean isSpaceChar(int c) {
			
			if (filter != null)
				return filter.isSpaceChar(c);
			
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}
	 
		public String next() {
			return readString();
		}
		
		public interface SpaceCharFilter {
			public boolean isSpaceChar(int ch);
		}
	
	}
 
	public static void main(String args[]) throws Exception {
		new Thread(null, new cf1(),"cf1",1<<26).start();
	}
} 