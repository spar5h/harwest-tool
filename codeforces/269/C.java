import java.util.*;
import java.io.*;
import java.math.BigInteger;
import static java.lang.Math.*;
 
/* spar5h */
 
public class cf2 implements Runnable {   
	
	class Edge {
		
		int u, v, w, state;
		
		Edge(int u, int v, int w) {
			this.u = u;
			this.v = v;
			this.w = w;
		}
				
		int other(int x) {
			return (x == u) ? v : u;
		}
		
		void setState(int x) {
			if(x == u)
				state = 0;
			else
				state = 1;
		}
	}
	
	Edge[] edge;
	ArrayList<Integer>[] adj;
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = s.nextInt(), m = s.nextInt();
		
		adj = new ArrayList[n];
		
		for(int i = 0; i < n; i++)
			adj[i] = new ArrayList<Integer>();
		
		int[] wt = new int[n];
		
		edge = new Edge[m];
		
		for(int i = 0; i < m; i++) {
			edge[i] = new Edge(s.nextInt() - 1, s.nextInt() - 1, s.nextInt());
			adj[edge[i].u].add(i);
			adj[edge[i].v].add(i);
			wt[edge[i].u] += edge[i].w;
			wt[edge[i].v] += edge[i].w;
		}
		
		wt[0] = 0;
		
		for(int i = 1; i < n - 1; i++)
			wt[i] /= 2;
		
		Queue<Integer> q = new LinkedList<Integer>();
		q.add(0);
		
		int[] vis = new int[n];
		
		while(!q.isEmpty()) {
			
			int x = q.poll();
			
			if(vis[x] == 1)
				continue;
			
			vis[x] = 1;
			
			for(int i : adj[x]) {
				
				int y = edge[i].other(x);
				
				if(vis[y] == 1)
					continue;
				
				edge[i].setState(x);
				wt[y] -= edge[i].w;
				
				if(wt[y] == 0) 
					q.add(y);
			}
		}
		
		for(int i = 0; i < m; i++)
			w.println(edge[i].state);
		
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