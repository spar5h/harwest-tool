import java.util.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

public class cf2 implements Runnable {   
	
	static class Edge {
		
		int u, v;
		
		Edge(int u, int v) {
			this.u = u; this.v = v;
		}
		
		int other(int x) {
			
			if(x == u)
				return v;
			
			return u;
		}
	}
	
	static class Pair {
		
		int i, w;
		
		Pair(int i, int w) {
			this.i = i; this.w = w;
		}
	}
	
	static class Comp implements Comparator<Pair> {
		
		public int compare(Pair x, Pair y) {
			
			if(x.w > y.w)
				return -1;
			
			if(x.w < y.w)
				return 1;
			
			return 0;
		}
	}
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = s.nextInt(), k = s.nextInt();
		
		Edge[] edge = new Edge[n - 1];
		int[] edgeCol = new int[n - 1];
		
		ArrayList<Integer>[] adj = new ArrayList[n];
		
		for(int i = 0; i < n; i++)
			adj[i] = new ArrayList<Integer>();
		
		for(int i = 0; i < n - 1; i++) {
			Edge e = new Edge(s.nextInt() - 1, s.nextInt() - 1);
			edge[i] = e;
			adj[e.u].add(i);
			adj[e.v].add(i);
		}	
		
		int[] dead = new int[n];
		
		ArrayList<Pair> a = new ArrayList<Pair>();
		
		for(int i = 0; i < n; i++)
			a.add(new Pair(i, adj[i].size()));
		
		Collections.sort(a, new Comp());
		
		for(int i = 0; i < k; i++)
			dead[a.get(i).i] = 1;
		
		Queue<Integer> q = new LinkedList<Integer>();
		int[] parentEdge = new int[n];
		Arrays.fill(parentEdge, -1);
		
		int col = 0;
		
		for(int i : adj[0]) {
			
			if(dead[0] == 0) 
				edgeCol[i] = ++col;
			else 
				edgeCol[i] = 1;
			
			parentEdge[edge[i].other(0)] = i;
			q.add(edge[i].other(0));
		}
		
		while(!q.isEmpty()) {
			
			int x = q.poll();
			col = 0;
			
			for(int i : adj[x]) {
				
				if(i == parentEdge[x])
					continue;
				
				if(dead[x] == 0) {
					
					if(col + 1 == edgeCol[parentEdge[x]])
						col++;
					
					edgeCol[i] = ++col;
				}
				else
					edgeCol[i] = edgeCol[parentEdge[x]];
				
				parentEdge[edge[i].other(x)] = i;
				q.add(edge[i].other(x));
			}
		}
		
		col = 0;
		
		for(int i = 0; i < n - 1; i++)
			col = max(edgeCol[i], col);
		
		w.println(col);
		
		for(int i = 0; i < n - 1; i++)
			w.print(edgeCol[i] + " ");
		
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
		new Thread(null, new cf2(),"cf2",1<<26).start();
	}
}