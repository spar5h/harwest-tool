import java.util.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

public class cf4 implements Runnable {   
	
	static class Pair {
		
		int u, v;
		
		Pair(int u, int v) {
			this.u = u; this.v = v;
		}
	}
	
	static ArrayList<Integer>[] adj;
	static int[] col;
	
	static void join(int u, int v) {
		
		int big = col[u], small = col[v];
		
		if(adj[big].size() < adj[small].size()) {
			int temp = small;
			small = big;
			big = temp;
		}
		
		for(int x : adj[small]) { 
			adj[big].add(x);
			col[x] = big;
		}
		
		adj[small].clear();
	}
	
	public void run() {
			
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = s.nextInt(), m = s.nextInt(), d = s.nextInt();
		
		ArrayList<Pair> edge = new ArrayList<Pair>();
		
		int[] deg = new int[n];
		
		for(int i = 0; i < m; i++) {
			int u = s.nextInt() - 1, v = s.nextInt() - 1;
			edge.add(new Pair(u, v));
			deg[u]++; deg[v]++;
		}
		
		adj = new ArrayList[n];
		col = new int[n];
		
		HashSet<Integer> hs = new HashSet<Integer>();
		HashSet<Integer> hsCol = new HashSet<Integer>();
		
		for(int i = 0; i < n; i++) {
			adj[i] = new ArrayList<Integer>();
			adj[i].add(i);
			col[i] = i;
		}	
		
		for(int i = 0; i < m; i++) {
			
			if(edge.get(i).u == 0) {
				hs.add(edge.get(i).v);
				hsCol.add(col[edge.get(i).v]);
			}
			if(edge.get(i).v == 0){
				hs.add(edge.get(i).u);
				hsCol.add(col[edge.get(i).u]);
			}	
		}		
		
		ArrayList<String> res = new ArrayList<String>();
		
		for(int i = 0; i < m; i++) {
			
			if(edge.get(i).u == 0 || edge.get(i).v == 0 || (hsCol.contains(col[edge.get(i).u]) &&  hsCol.contains(col[edge.get(i).v])))
				continue;
			
			if(col[edge.get(i).u] != col[edge.get(i).v]) {
				res.add((edge.get(i).u + 1) + " " + (edge.get(i).v + 1));
				
				int oldU = -1, oldV = -1; 
				
				if(hsCol.contains(col[edge.get(i).u])) 
					oldU = col[edge.get(i).u];
				
				if(hsCol.contains(col[edge.get(i).v]))
					oldV = col[edge.get(i).v];
				
				join(edge.get(i).u, edge.get(i).v);
				
				if(oldU != -1) {
					hsCol.remove(oldU);
					hsCol.add(col[edge.get(i).u]);
				}
				
				if(oldV != -1) {
					hsCol.remove(oldV);
					hsCol.add(col[edge.get(i).v]);
				}
			}
		}
		
		int k = hs.size();
		
		for(int i = 0; i < m && k > d; i++) {
			
			if(hsCol.contains(col[edge.get(i).u]) &&  hsCol.contains(col[edge.get(i).v])) {
			
				if(col[edge.get(i).u] != col[edge.get(i).v]) {
					
					res.add((edge.get(i).u + 1) + " " + (edge.get(i).v + 1));
					
					int oldU = -1, oldV = -1; 
					
					if(hsCol.contains(col[edge.get(i).u])) 
						oldU = col[edge.get(i).u];
					
					if(hsCol.contains(col[edge.get(i).v]))
						oldV = col[edge.get(i).v];
					
					join(edge.get(i).u, edge.get(i).v);
					
					if(oldU != -1) {
						hsCol.remove(oldU);
						hsCol.add(col[edge.get(i).u]);
					}
					
					if(oldV != -1) {
						hsCol.remove(oldV);
						hsCol.add(col[edge.get(i).v]);
					}
					
					k--;
				}
			}
		}
		
		for(int i = 0; i < m; i++) {
			
			if(edge.get(i).u != 0 && edge.get(i).v != 0)
				continue;
			
			if(col[edge.get(i).u] != col[edge.get(i).v]) {
				res.add((edge.get(i).u + 1) + " " + (edge.get(i).v + 1));
				join(edge.get(i).u, edge.get(i).v);
			}
		}
		
		if(res.size() == n - 1 && k == d) {
			
			w.println("YES");
			
			for(int i = 0; i < res.size(); i++)
				w.println(res.get(i));
			
		}
		else
			w.println("NO");
		
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