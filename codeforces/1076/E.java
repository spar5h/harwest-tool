import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

public class cf5 implements Runnable{    
	
	static void dfs(int x) {
		
		left[x] = time++;
		right[x] = left[x];
		
		tour.add(x);
		
		for(int y : adj[x]) {
			
			if(level[y] == -1) {
				
				level[y] = level[x] + 1;
				dfs(y);
				right[x] = right[y];
			}
		}
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
	
	static ArrayList<Integer>[] height;
	static int[] level, vis;
	static int[] left, right;
	static int time;
	static long[] val;
	static long[][] tree, lazy;
	static ArrayList<Integer>[] adj;
	static ArrayList<Integer> tour;
	
	static void dfs2(int x) {
		
		vis[x] = 1;
		
		for(int y : adj[x]) {
			
			if(vis[y] == 1)
				continue;
			
			val[y] += val[x];
			dfs2(y);
		}
	}
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = s.nextInt();
		
		adj = new ArrayList[n];
		
		for(int i = 0; i < n; i++)
			adj[i] = new ArrayList<Integer>();
		
		for(int i = 0; i < n - 1; i++) {
			int u = s.nextInt() - 1, v = s.nextInt() - 1;
			adj[u].add(v); adj[v].add(u);
		}
		
		tour = new ArrayList<Integer>();
		
		level = new int[n];
		Arrays.fill(level, -1); level[0] = 0;
		
		left = new int[n];
		right = new int[n];
		
		dfs(0);
		
		height = new ArrayList[n];
		int[] map2 = new int[n];
		
		for(int i = 0; i < n; i++) {
			height[i] = new ArrayList<Integer>();
		}
		
		for(int i = 0; i < tour.size(); i++) {
			map2[tour.get(i)] = height[level[tour.get(i)]].size();
			height[level[tour.get(i)]].add(i);
		}
		
		tree = new long[n][];
		lazy = new long[n][];
		
		int[] size = new int[n];
		
		for(int i = 0; i < n; i++) {
			
			if(height[i].size() == 0)
				continue;
			
			size[i] = (int)pow(2, (int)ceil(log(height[i].size()) / log(2)) + 1);
			tree[i] = new long[size[i]];
			lazy[i] = new long[size[i]];
		}
		
		/*
		for(int i = 0; i < tour.size(); i++)
			w.print((tour.get(i) + 1) + " ");
		
		w.println();
		
		for(int i = 0; i < n; i++) {
			
			w.println(i + " " + height[i].size() + " //");
			
			for(int j = 0; j < height[i].size(); j++)
				w.print(tour.get(height[i].get(j)) + 1 + " ");
			
			w.println();
		}
		*/
		
		int m = s.nextInt();
		
		while(m-- > 0) {
			
			int v = s.nextInt() - 1, d = s.nextInt() + 1;
			long x = s.nextLong();
			
			int lvl = level[v];
			
			update(0, 0, height[lvl].size() - 1, map2[v], map2[v], x, lvl);
			
			if(lvl + d >= n || height[lvl + d].size() == 0)
				continue;
			
			//w.println(m +  " " + height[lvl + d].size());
			
			int L = 0, R = height[lvl + d].size() - 1;
			
			int l = -1, r = -1;
			
			while(L <= R) {
				
				int mid = (L + R) / 2;
				
				if(height[lvl + d].get(mid) >= left[v]) {
					R = mid - 1; l = mid;
				}
				else 
					L = mid + 1;
			}
			
			L = 0; R =  height[lvl + d].size() - 1;
			
			while(L <= R) {
				
				int mid = (L + R) / 2;
				
				if(height[lvl + d].get(mid) <= right[v]) {
					L = mid + 1; r = mid;
				}
				else 
					R = mid - 1;
			}

			if(l == -1 || r == - 1 || l > r)
				continue;
			
			update(0, 0, height[lvl + d].size() - 1, l, r, -x, lvl + d);
		}
		
		/*
		for(int i = 0; i < n; i++)
			w.println(i + " " + map2[i]);
		*/
		
		val = new long[n];
		
		for(int i = 0; i < tour.size(); i++) {
			int x = tour.get(i);
			val[x] = query(0, 0, height[level[x]].size() - 1, map2[x], level[x]);
		}
		
		//for(int i = 0; i < n; i++)
			//w.println((i + 1) + " " + val[i]);
		
		vis = new int[n];
		
		dfs2(0);
		
		for(int i = 0; i < n; i++)
			w.print(val[i] + " ");
		
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
		new Thread(null, new cf5(),"cf5",1<<26).start();
	}
}   