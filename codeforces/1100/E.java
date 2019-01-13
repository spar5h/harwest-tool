import java.util.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

public class cf1 implements Runnable {   
	
	static PrintWriter w;
	
	class Graph {
		
		ArrayList<Edge> edgeList;
		ArrayList<Integer>[] adj;
		int n;
		
		Graph(int n) {
			
			this.n = n;
			
			adj = new ArrayList[n];
			
			for(int i = 0; i < n; i++)
				adj[i] = new ArrayList<Integer>();
			
			edgeList = new ArrayList<Edge>();
		}
		
		class Edge {
			
			int i, u, v, wt;
			
			Edge(int i, int u, int v, int wt) {
				this.i = i;
				this.u = u;
				this.v = v;
				this.wt = wt;
			}
		}
		
		void addEdge(int i, int u, int v, int wt) {
			Edge e = new Edge(i, u, v, wt);
			adj[u].add(edgeList.size());
			edgeList.add(e);
		}
		
		int getU(int i) {
			return edgeList.get(i).u;
		}
		
		int getV(int i) {
			return edgeList.get(i).v;
		}
		
		int getWeight(int i) {
			return edgeList.get(i).wt;
		}
		
		int edgeCount() {
			return edgeList.size();
		}
		
		int[] vis;
		
		boolean hasCycle() {
			
			vis = new int[n];
			
			for(int i = 0; i < n; i++)
				if(vis[i] == 0 && dfs(i))
					return true;
					
			return false;
		}
		
		boolean dfs(int x) {
			
			vis[x] = 2;
			
			for(int i : adj[x]) {
				
				Edge e = edgeList.get(i);
				int y = e.v;
				
				if(vis[y] == 0 && dfs(y)) 
					return true;
				else if(vis[y] == 2)
					return true;
			}
			
			vis[x] = 1;
			
			return false;
		}
		
		int[] topoSort() {
			
			int[] in = new int[n];
			
			for(int i = 0; i < edgeList.size(); i++)
				in[edgeList.get(i).v]++;
			
			Queue<Integer> q = new LinkedList<Integer>();
			
			for(int i = 0; i < n; i++)
				if(in[i] == 0)
					q.add(i);
			
			int[] order = new int[n];
			int c = 0;
			
			while(!q.isEmpty()) {
				
				int x = q.poll();
				order[x] = c++;
				
				for(int i : adj[x]) {
					int y = edgeList.get(i).v;
					in[y]--;
					if(in[y] == 0)
						q.add(y);
				}
			}
			
			return order;
		}
		
		void printAnswer(int[] order) {
			
			ArrayList<Integer> idx = new ArrayList<Integer>();
			
			for(int i = 0; i < edgeList.size(); i++)
				if(order[edgeList.get(i).u] > order[edgeList.get(i).v])
					idx.add(edgeList.get(i).i);
			
			w.println(idx.size());
			
			for(int i = 0; i < idx.size(); i++)
				w.print((idx.get(i) + 1) + " ");
			
			w.println();
			
		}
	}
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		w = new PrintWriter(System.out);
		
		int n = s.nextInt(), m = s.nextInt();
		
		Graph ini = new Graph(n);
		
		for(int i = 0; i < m; i++)
			ini.addEdge(i, s.nextInt() - 1, s.nextInt() - 1, s.nextInt());
		
		int left = 0, right = (int)1e9;
		int ans = (int)1e9;
		
		while(left <= right) {
			
			int mid = (left + right) / 2;
			
			Graph g = new Graph(n);
			
			for(int i = 0; i < ini.edgeCount(); i++)
				if(ini.getWeight(i) > mid)
					g.addEdge(i, ini.getU(i), ini.getV(i), ini.getWeight(i));
			
			if(g.hasCycle()) {
				left = mid + 1;
			}
			else {
				right = mid - 1;
				ans = mid;
			}
		}
		
		Graph fin = new Graph(n);
		
		for(int i = 0; i < ini.edgeCount(); i++)
			if(ini.getWeight(i) > ans)
				fin.addEdge(i, ini.getU(i), ini.getV(i), ini.getWeight(i));
			
		w.print(ans + " ");
		ini.printAnswer(fin.topoSort());
		
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