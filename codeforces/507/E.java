import java.util.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

public class cf3 implements Runnable {    
	
	class Edge {
		
		int u, v, w;
		
		Edge(int u, int v, int w) {
			this.u = u;
			this.v = v;
			this.w = w;
		}
				
		int other(int x) {
			return (x == u) ? v : u;
		}
	}
	
	ArrayList<Integer>[] initArrayList(int n) {
		
		ArrayList<Integer>[] adj = new ArrayList[n];
		
		for(int i = 0; i < n; i++)
			adj[i] = new ArrayList<Integer>();
		
		return adj;
	}
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = s.nextInt(), m = s.nextInt();
		
		ArrayList<Integer>[] adj = initArrayList(n);
		
		Edge[] edge = new Edge[m];
		
		for(int i = 0; i < m; i++) {
			edge[i] = new Edge(s.nextInt() - 1, s.nextInt() - 1, s.nextInt());
			adj[edge[i].u].add(i);
			adj[edge[i].v].add(i);
		}
		
		Queue<Integer> q = new LinkedList<Integer>();
		
		int[] d = new int[n];
		Arrays.fill(d, Integer.MAX_VALUE);
		
		int[] dp = new int[n];
		Arrays.fill(dp, Integer.MAX_VALUE);
		
		int[] backEdge = new int[n];
		
		q.add(n - 1);
		d[n - 1] = 0;
		dp[n - 1] = 0;
		backEdge[n - 1] = -1;
		
		while(!q.isEmpty()) {
			
			int x = q.poll();
			
			for(int i : adj[x]) {
				
				Edge e = edge[i];
				int y = e.other(x);
				
				if(d[y] > d[x] + 1) {
					d[y] = d[x] + 1;
					q.add(y);
				}
				
				if(d[y] == d[x] + 1 && dp[y] > dp[x] + 1 - e.w) {
					dp[y] = dp[x] + 1 - e.w;
					backEdge[y] = i;
				}
			}
		}
		
		int curr = 0;
		
		HashSet<Integer> hs = new HashSet<Integer>();
		ArrayList<Integer> res = new ArrayList<Integer>();
		
		while(curr != n - 1) {
			
			hs.add(backEdge[curr]);
			
			if(edge[backEdge[curr]].w == 0)
				res.add(backEdge[curr]);
			
			curr = edge[backEdge[curr]].other(curr);
		}
		
		for(int i = 0; i < m; i++)
			if(!hs.contains(i) && edge[i].w == 1)
				res.add(i);
		
		w.println(res.size());
		
		for(int i : res)
			w.println((edge[i].u + 1) + " " + (edge[i].v + 1) + " " + (1 - edge[i].w));
		
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
		new Thread(null, new cf3(),"cf3",1<<26).start();
	}
}   