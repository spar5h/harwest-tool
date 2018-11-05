import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

public class cf1 implements Runnable{    
	
	static int[] a, level, map, time = new int[2];
	static int[][] left, right;
	static long[][] tree = new long[2][], lazy = new long[2][];
	static ArrayList<Integer>[] adj, tour = new ArrayList[2];
	
	static void dfs(int x) {
		
		map[x] = tour[level[x]].size();
		tour[level[x]].add(x);
		left[level[x]][x] = time[level[x]];
		right[level[x]][x] = time[level[x]];
		
		time[level[x]]++;
		
		for(int y : adj[x]) {
			
			if(level[y] == -1) {
				
				level[y] = 1 ^ level[x];
				
				if(left[level[y]][x] == -1)
					left[level[y]][x] = time[level[y]];
				
				dfs(y);
				
				right[level[y]][x] = time[level[y]] - 1;
				right[level[x]][x] = time[level[x]] - 1;
			}
		}
	}
	
	static void build(int n, int nL, int nR, int c) {
		
		if(nL == nR) {
			tree[c][n] = a[tour[c].get(nL)]; return;
		}
		
		build(2 * n + 1, nL, (nL + nR) / 2, c);
		build(2 * n + 2, (nL + nR) / 2 + 1, nR, c);
		
		tree[c][n] = tree[c][2 * n + 1] + tree[c][2 * n + 2];
	}
	
	static void update(int n, int nL, int nR, int l, int r, long x, int c) {
		
		if(lazy[c][n] != 0) {
			
			tree[c][n] += lazy[c][n] * (nR - nL + 1);
			
			if(nL != nR) {
				lazy[c][2 * n + 1] += lazy[c][n];
				lazy[c][2 * n + 2] += lazy[c][n];
			}
			
			lazy[c][n] = 0;
		}
		
		if(nR < l || r < nL)
			return;
		
		if(l <= nL && nR <= r) {
			
			tree[c][n] += x * (nR - nL + 1);
			
			if(nL != nR) {
				lazy[c][2 * n + 1] += x;
				lazy[c][2 * n + 2] += x;
			}
			
			return;
		}
		
		update(2 * n + 1, nL, (nL + nR) / 2, l, r, x, c);
		update(2 * n + 2, (nL + nR) / 2 + 1, nR, l, r, x, c);
		
		tree[c][n] = tree[c][2 * n + 1] + tree[c][2 * n + 2];
	}
	
	static long query(int n, int nL, int nR, int x, int c) {
		
		if(lazy[c][n] != 0) {
			
			tree[c][n] += lazy[c][n] * (nR - nL + 1);
			
			if(nL != nR) {
				lazy[c][2 * n + 1] += lazy[c][n];
				lazy[c][2 * n + 2] += lazy[c][n];
			}
			
			lazy[c][n] = 0;
		}
		
		if(nR < x || x < nL)
			return 0;
		
		if(nL == x && nR == x) 
			return tree[c][n];
		
		long ret = query(2 * n + 1, nL, (nL + nR) / 2, x, c) + query(2 * n + 2, (nL + nR) / 2 + 1, nR, x, c);
		
		tree[c][n] = tree[c][2 * n + 1] + tree[c][2 * n + 2];
		
		return ret;
	}
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);

		int n = s.nextInt(), q = s.nextInt();
		
		a = new int[n];
		
		for(int i = 0; i < n; i++)
			a[i] = s.nextInt();
		
		adj = new ArrayList[n];
		
		for(int i = 0; i < n; i++)
			adj[i] = new ArrayList<Integer>();
		
		for(int i = 0; i < n - 1; i++) {
			int u = s.nextInt() - 1, v = s.nextInt() - 1;
			adj[u].add(v); adj[v].add(u);
		}
		
		tour[0] = new ArrayList<Integer>();
		tour[1] = new ArrayList<Integer>();
		
		level = new int[n];
		Arrays.fill(level, -1); level[0] = 0;
		
		left = new int[2][n];
		right = new int[2][n];
		
		for(int i = 0; i < 2; i++) {
			Arrays.fill(left[i], -1);
			Arrays.fill(right[i], -1);
		}
		
		map = new int[n];
		
		dfs(0);
		
		/*
		for(int i = 0; i < tour[0].size(); i++)
			w.print(tour[0].get(i) + " ");
		
		w.println();
		
		for(int i = 0; i < tour[1].size(); i++)
			w.print(tour[1].get(i) + " ");
		
		w.println();
		
		for(int i = 0; i < n; i++)
			w.println((i + 1) + " : " + left[i][0] + " " + right[i][0] + " : " + left[i][1] + " " + right[i][1]);
		*/
		
		int N0 = tour[0].size(), N1 = tour[1].size();
		
		int n0 = N0 > 0 ? (int)pow(2, (int)ceil(log(N0) / log(2)) + 1) : 1;
		int n1 = N1 > 0 ? (int)pow(2, (int)ceil(log(N1) / log(2)) + 1) : 1;
		
		tree[0] = new long[n0];
		tree[1] = new long[n1];
		lazy[0] = new long[n0];
		lazy[1] = new long[n1];
		
		if(N0 > 0)
			build(0, 0, N0 - 1, 0);
		
		if(N1 > 0)
			build(0, 0, N1 - 1, 1);
		
		while(q-- > 0) {
			
			if(s.nextInt() == 1) {
				
				int x = s.nextInt() - 1;
				long val = s.nextInt();
				
				if(level[x] == 0) {
					
					if(left[level[x]][x] != -1)
						update(0, 0, N0 - 1, left[level[x]][x], right[level[x]][x], val, level[x]);
					
					if(left[1 ^ level[x]][x] != -1)
						update(0, 0, N1 - 1, left[1 ^ level[x]][x], right[1 ^ level[x]][x], -val, 1 ^ level[x]);
				}
				else {
					
					if(left[level[x]][x] != -1)
						update(0, 0, N1 - 1, left[level[x]][x], right[level[x]][x], val, level[x]);
					
					if(left[1 ^ level[x]][x] != -1)
						update(0, 0, N0 - 1, left[1 ^ level[x]][x], right[1 ^ level[x]][x], -val, 1 ^ level[x]);
				}
			}
			
			else {
				
				int x = s.nextInt() - 1;
				
				if(level[x] == 0)
					w.println(query(0, 0, N0 - 1, map[x], level[x]));
				else
					w.println(query(0, 0, N1 - 1, map[x], level[x]));
			}	
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