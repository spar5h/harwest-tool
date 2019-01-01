import java.util.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

public class cf1 implements Runnable {   
	
	static class edge {
		
		int u, v;
		long flow, cap;
	
		edge(int u, int v, long cap) {
			this.u = u; this.v = v; this.cap = cap; this.flow = 0;
		}
		
		void pushFlow(int x, long val) {
			
			if(u == x) 
				flow += val;
			else
				flow -= val;
		}
		
		long possibleFlow(int x) {
			
			if(u == x) 
				return cap - flow;
			else
				return flow;
		}
		
		int other(int x) {
			
			if(x == u)
				return v;
			else
				return u;
		}
		
		void ini() {
			flow = 0;
		}
		
		void changeCap(long val) {
			cap = val;
		}
	}
	
	static int n;
	static int[] level, listIndex;
	static ArrayList<Integer>[] adj;
	static edge[] edgeList;
	
	static boolean getLevelGraph(int s, int t) {
		
		Queue<Integer> q = new LinkedList<Integer>();
		q.add(s); level[s] = 0;
		
		while(!q.isEmpty()) {
			
			int x = q.poll();
			
			for(int i : adj[x]) {
				
				edge e = edgeList[i];
				
				int y = e.other(x);
				
				if(e.possibleFlow(x) > 0 && level[y] == -1) {
					level[y] = level[x] + 1; q.add(y);
				}
			}
		}
		
		if(level[t] == -1)
			return false;
		else
			return true;
	}
	
	static long sendFlow(int u, int t, long currFlow) {
		
		if(u == t)
			return currFlow;
		
		long sentFlow = 0;
		
		while(listIndex[u] < adj[u].size()) {
			
			edge e = edgeList[adj[u].get(listIndex[u])]; 
			int v = e.other(u);
			
			if(level[v] == level[u] + 1 && e.possibleFlow(u) > 0) {
			
				long possFlow = min(e.possibleFlow(u), currFlow - sentFlow);
				long flowSentNow = sendFlow(v, t, possFlow);
				
				sentFlow += flowSentNow;
				e.pushFlow(u, flowSentNow);
			}
			
			if(sentFlow == currFlow)
				return sentFlow;	
			
			listIndex[u]++;
		}
		
		return sentFlow;
	}
	
	static long dinic(int s, int t) {
		
		if(s == t)
			return 0;
		
		level = new int[n];	
		listIndex = new int[n];
		
		long maxFlow = 0;
		
		while(getLevelGraph(s, t)) {

			maxFlow += sendFlow(s, t, Long.MAX_VALUE);	
			
			Arrays.fill(level, -1);
			Arrays.fill(listIndex, 0);
		}
		
		return maxFlow;
	}
	
	static void addEdge(int i) {
		adj[edgeList[i].u].add(i); adj[edgeList[i].v].add(i);
	}
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int N = s.nextInt(), M = s.nextInt();
		
		int src = N + M, snk = N + M + 1;
		
		n = N + M + 2;
		
		adj = new ArrayList[n];
		
		for(int i = 0; i < n; i++)
			adj[i] = new ArrayList<Integer>();
		
		int edgeCount = N + 2 * M + M;
		
		edgeList = new edge[edgeCount];
		
		long nodeWt = 0, edgeWt = 0;
		
		for(int i = 0; i < N; i++) {
			long wt = s.nextLong(); 
			nodeWt += wt;
			edgeList[i] = new edge(src, i, wt);
		}
		
		for(int i = 0; i < M; i++) {
			edgeList[N + 3 * i] = new edge(s.nextInt() - 1, N + i, Long.MAX_VALUE);
			edgeList[N + 3 * i + 1] = new edge(s.nextInt() - 1, N + i, Long.MAX_VALUE);
			long wt = s.nextLong();
			edgeWt += wt;
			edgeList[N + 3 * i + 2] = new edge(N + i, snk, wt);
		}
		
		for(int i = 0; i < N + 3 * M; i++)
			addEdge(i);
		
		w.println(edgeWt - dinic(src, snk));
		
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