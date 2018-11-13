import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

public class cf4 implements Runnable{    
	
	static class pair {
		
		int i;
		long w;
		
		pair(int i, long w) {
			this.i = i; this.w = w; 
		}
	}
	
	static class comp implements Comparator<pair> {
		
		public int compare(pair x, pair y) {
			
			if(x.w < y.w)
				return -1;
			
			if(x.w > y.w)
				return 1;
			
			return 0;
		}
	}
	
	static class edge {
		
		int u, v;
		long w;
		
		edge(int u, int v, long w) {
			this.u = u; this.v = v; this.w = w;
		}
		
		int other(int x) {
			
			if(x == u)
				return v;
			
			return u;
		}
	}
	
	static ArrayList<Integer>[] adj2;
	static int[] edge;
	
	static int dfs(int x, int c) {
		
		int ret = 0;
		
		for(int y : adj2[x]) {
			
			if(c - ret > 0)
				ret += dfs(y, c - ret);
		}
		
		if(c - ret > 0) {
			edge[x] = -1; ret++;
		}	
		
		return ret;
	}
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = s.nextInt(), m = s.nextInt(), k = s.nextInt();
		
		ArrayList<Integer>[] adj = new ArrayList[n];
		
		for(int i = 0; i < n; i++)
			adj[i] = new ArrayList<Integer>();
		
		ArrayList<edge> eList = new ArrayList<edge>();
		
		for(int i = 0; i < m; i++) {
			
			int u = s.nextInt() - 1, v = s.nextInt() - 1;
			eList.add(new edge(u, v, s.nextLong()));
			adj[u].add(i); adj[v].add(i);
		}
		
		int[] vis = new int[n];
		
		long[] d = new long[n];
		Arrays.fill(d,  Long.MAX_VALUE);
		d[0] = 0;
		
		PriorityQueue<pair> pq = new PriorityQueue<pair>(new comp());
		pq.add(new pair(0, 0));
		
		int[] parent = new int[n];
		parent[0] = -1; 
		
		edge = new int[n];
		edge[0] = -1;
		
		while(!pq.isEmpty()) {
			
			int x = pq.poll().i;
			
			if(vis[x] == 1)
				continue;
			
			vis[x] = 1;
			
			for(int i : adj[x]) {
				
				int y = eList.get(i).other(x); 
				long wt = eList.get(i).w;
				
				if(d[y] == Long.MAX_VALUE || d[y] > d[x] + wt) {
					edge[y] = i; parent[y] = x; d[y] = d[x] + wt; pq.add(new pair(y, d[y]));
				}
			}
		}
		
		if(k < n - 1) {
			
			adj2 = new ArrayList[n];
			
			for(int i = 0; i < n; i++)
				adj2[i] = new ArrayList<Integer>();
			
			for(int i = 0; i < n; i++)
				if(parent[i] != -1)
					adj2[parent[i]].add(i);
			
			dfs(0, n - 1 - k);
		}
		
		w.println(min(k, n - 1));
		
		for(int i = 0; i < n; i++) {
			
			if(edge[i] == -1)
				continue;
			
			w.print(edge[i] + 1 + " ");
		}
		
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
		new Thread(null, new cf4(),"cf4",1<<26).start();
	}
}   