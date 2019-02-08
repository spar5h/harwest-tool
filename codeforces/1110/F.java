import java.util.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

public class cf6 implements Runnable {    
	
	static ArrayList<Integer>[] adj, adjWt, query;
	static TreeMap<Integer, Integer> tm;
	static int leafCount, eulerCount;
	static int[] l, r, map, queryL, queryR;
	static long[] d, leafD, tree, lazy, res;

	static void iniDfs(int x) {
		
		map[eulerCount] = x;
		eulerCount++;
		
		if(adj[x].size() == 0) {
			tm.put(eulerCount - 1, leafCount);
			leafD[leafCount] = d[x];
			l[x] = leafCount;
			r[x] = leafCount;
			leafCount++;
			return;
		}
		
		for(int i = 0; i < adj[x].size(); i++) {
			d[adj[x].get(i)] = d[x] + adjWt[x].get(i);
			iniDfs(adj[x].get(i));
		}
		
		l[x] = l[adj[x].get(0)];
		r[x] = r[adj[x].get(adj[x].size() - 1)];
	}
	
	static void build(int n, int nL, int nR) {
		
		if(nL == nR) {
			tree[n] = leafD[nL];
			return;
		}
		
		build(2 * n + 1, nL, (nL + nR) / 2);
		build(2 * n + 2, (nL + nR) / 2 + 1, nR);
		
		tree[n] = min(tree[2 * n + 1], tree[2 * n + 2]);
	}
	
	static void update(int n, int nL, int nR, int l, int r, int wt) {
		
		if(lazy[n] != 0) {
			tree[n] += lazy[n];
			if(nL != nR) {
				lazy[2 * n + 1] += lazy[n];
				lazy[2 * n + 2] += lazy[n];
			}
			lazy[n] = 0;
		}
		
		if(r < nL || nR < l)
			return;
		
		if(l <= nL && nR <= r) {
			tree[n] += wt;
			if(nL != nR) {
				lazy[2 * n + 1] += wt;
				lazy[2 * n + 2] += wt;
			}
			return;
		}
		
		update(2 * n + 1, nL, (nL + nR) / 2, l, r, wt);
		update(2 * n + 2, (nL + nR) / 2 + 1, nR, l, r, wt);
		
		tree[n] = min(tree[2 * n + 1], tree[2 * n + 2]);
	}
	
	static long query(int n, int nL, int nR, int l, int r) {
		
		if(lazy[n] != 0) {
			tree[n] += lazy[n];
			if(nL != nR) {
				lazy[2 * n + 1] += lazy[n];
				lazy[2 * n + 2] += lazy[n];
			}
			lazy[n] = 0;
		}
		
		if(r < nL || nR < l)
			return Long.MAX_VALUE;
		
		if(l <= nL && nR <= r)
			return tree[n];
		
		long ret = min(query(2 * n + 1, nL, (nL + nR) / 2, l, r), query(2 * n + 2, (nL + nR) / 2 + 1, nR, l, r));
		tree[n] = min(tree[2 * n + 1], tree[2 * n + 2]);
		return ret;
	}

	static void iniSegTree() {
		
		int size = (int)pow(2, ceil(log(leafCount) / log(2)) + 1);
		tree = new long[size];
		lazy = new long[size];
		
		build(0, 0, leafCount - 1);
	}
	
	static void dfs(int x) {
		
		for(int q : query[x]) {
			res[q] = query(0, 0, leafCount - 1, queryL[q], queryR[q]);
		}
		
		for(int i = 0; i < adj[x].size(); i++) {
			
			int y = adj[x].get(i);
			int wt = adjWt[x].get(i);
			
			if(l[y] > 0)
				update(0, 0, leafCount - 1, 0, l[y] - 1, wt);
			
			update(0, 0, leafCount - 1, l[y], r[y], -wt);
			
			if(r[y] + 1 < leafCount)
				update(0, 0, leafCount - 1, r[y] + 1, leafCount - 1, wt);
			
			dfs(y);
			
			if(l[y] > 0)
				update(0, 0, leafCount - 1, 0, l[y] - 1, -wt);
			
			update(0, 0, leafCount - 1, l[y], r[y], wt);
			
			if(r[y] + 1 < leafCount)
				update(0, 0, leafCount - 1, r[y] + 1, leafCount - 1, -wt);
		}
	}
	
	public void run() {
	
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = s.nextInt(), q = s.nextInt();
		
		adj = new ArrayList[n];
		adjWt = new ArrayList[n];
		query = new ArrayList[n];
		
		for(int i = 0; i < n; i++) {
			adj[i] = new ArrayList<Integer>();
			adjWt[i] = new ArrayList<Integer>();
			query[i] = new ArrayList<Integer>();
		}
		
		for(int i = 1; i < n; i++) {
			int p = s.nextInt() - 1;
			adj[p].add(i);
			adjWt[p].add(s.nextInt());
		}
		
		l = new int[n];
		r = new int[n];
		map = new int[n];
		tm = new TreeMap<Integer, Integer>();
		eulerCount = 0;
		leafCount = 0;
		d = new long[n];
		leafD = new long[n];
		
		iniDfs(0);
		
		iniSegTree();
		
		queryL = new int[q];
		queryR = new int[q];
		res = new long[q];
		
		for(int i = 0; i < q; i++) {
			query[map[s.nextInt() - 1]].add(i);
			queryL[i] = tm.ceilingEntry(s.nextInt() - 1).getValue();
			queryR[i] = tm.floorEntry(s.nextInt() - 1).getValue();
		}
		
		dfs(0);
		
		for(int i = 0; i < q; i++)
			w.println(res[i]);
		
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
		new Thread(null, new cf6(),"cf6",1<<28).start();
	}
}   