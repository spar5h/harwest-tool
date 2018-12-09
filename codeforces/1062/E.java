import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

public class cf1 implements Runnable{    
	
	static ArrayList<Integer> adj[];
	static int time, h, tin[], tout[], anc[][], tree[], level[];
	
	static void dfs(int x, int p) {
		
		tin[x] = time++;
		
		anc[x][0] = p;
		
		for(int i = 1; i < h; i++) 
			anc[x][i] = anc[anc[x][i - 1]][i - 1];
		
		for(int i = 0; i < adj[x].size(); i++) {
			level[adj[x].get(i)] = level[x] + 1; dfs(adj[x].get(i), x);
		}
		
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
		
		for(int i = h - 1; i >= 0; i--) 
			if(!isAnc(anc[u][i], v)) 
				u = anc[u][i];
			
		return anc[u][0];
	}
	
	static void build(int n, int nL, int nR) {
		
		if(nL == nR) {
			tree[n] = nL; return;
		}
		
		build(2 * n + 1, nL, (nL + nR) / 2);
		build(2 * n + 2, (nL + nR) / 2 + 1, nR);
		
		tree[n] = lca(tree[2 * n + 1], tree[2 * n + 2]);
		
		//System.out.println((nL + 1) + " " + (nR + 1) + " " + (tree[n] + 1));
	}
	
	static int query(int n, int nL, int nR, int l, int r) {
		
		if(r < nL || nR < l || l > r)
			return -1;
		
		if(l <= nL && nR <= r)
			return tree[n];
		
		int r1 = query(2 * n + 1, nL, (nL + nR) / 2, l, r);
		int r2 = query(2 * n + 2, (nL + nR) / 2 + 1, nR, l, r);
		
		if(r1 == -1)
			return r2;
		
		if(r2 == -1)
			return r1;
		
		return lca(r1, r2);
		
	}
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = s.nextInt(), q = s.nextInt();
		
		h = (int)(log(n) / log(2)) + 1;
		
		tin = new int[n];
		tout = new int[n];
		
		anc = new int[n][h];
		
		time = 0;
		
		adj = new ArrayList[n];
		
		for(int i = 0; i < n; i++)
			adj[i] = new ArrayList<Integer>();
		
		for(int i = 1; i < n; i++)
			adj[s.nextInt() - 1].add(i);
		
		level = new int[n];
		
		dfs(0, 0);
		
		int segN = (int)pow(2, (int)ceil(log(n) / log(2)) + 1);
		tree = new int[segN];
		
		build(0, 0, n - 1);
		
		TreeSet<Integer>[] ts = new TreeSet[n];
		
		for(int i = 0; i < n; i++)
			ts[i] = new TreeSet<Integer>();
		
		for(int i = 0; i < n - 1; i++)
			ts[level[lca(i, i + 1)]].add(i);
		
		while(q-- > 0) {
			
			int l = s.nextInt() - 1, r = s.nextInt() - 1;
			
			int lca = query(0, 0, n - 1, l, r);
			
			if(ts[level[lca]].lower(r) == null || ts[level[lca]].lower(r) < l) {
				w.println((l + 1) + " " + level[lca]); continue;
			}
			
			int x = ts[level[lca]].lower(r);
			
			int c1;
			
			if(l == x)
				c1 = query(0, 0, n - 1, x + 1, r);
			else
				c1 = lca(query(0, 0, n - 1, l, x - 1), query(0, 0, n - 1, x + 1, r));
			
			int c2;
			
			if(r == x + 1)
				c2 = query(0, 0, n - 1, l, x);
			else
				c2 = lca(query(0, 0, n - 1, l, x), query(0, 0, n - 1, x + 2, r));
			
			if(level[c1] > level[c2])
				w.println((x + 1) + " " + level[c1]);
			else
				w.println((x + 2) + " " + level[c2]);
		}
		
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