import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

public class cf2 implements Runnable {    
	
	static ArrayList<Integer>[] adj;
	static int[] isThere, vis;
	static int y;
	
	
	static void dfs(int x) {
		
		vis[x] = 1;
		
		if(isThere[x] == 1) {
			y = x; return;
		}
		
		for(int v : adj[x]) {
			
			if(vis[v] == 1)
				continue;
			
			dfs(v);
		}
	}
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int t = s.nextInt();
		
		while(t-- > 0) {
			
			int n = s.nextInt();
			
			adj = new ArrayList[n];
			
			for(int i = 0; i < n; i++)
				adj[i] = new ArrayList<Integer>();
			
			for(int i = 0; i < n - 1; i++) {
				int u = s.nextInt() - 1, v = s.nextInt() - 1;
				adj[u].add(v); adj[v].add(u);
			}
			
			isThere = new int[n];
			
			int k1 = s.nextInt();
			
			for(int i = 0; i < k1; i++)
				isThere[s.nextInt() - 1] = 1;
			
			int k2 = s.nextInt();
			
			int[] a = new int[k2];
			
			int[] isThere2 = new int[n];
			
			for(int i = 0; i < k2; i++) {
				a[i] = s.nextInt() - 1; isThere2[a[i]] = 1;
			}	
			
			System.out.println("B " + (a[0] + 1));
			System.out.flush();
			
			int x = s.nextInt() - 1;
			y = -1;
			
			vis = new int[n];
			
			dfs(x);
			
			System.out.println("A " + (y + 1));
			System.out.flush();
			
			int z = s.nextInt() - 1;
			
			if(isThere2[z] == 1) 
				System.out.println("C " + (y + 1));
			else
				System.out.println("C -1");
			
			System.out.flush();
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
		new Thread(null, new cf2(),"cf2",1<<26).start();
	}
}   