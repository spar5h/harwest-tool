import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

//should have solved this during the contest :(

public class cf1 implements Runnable{    
	
public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
	
		int q = s.nextInt();
		
		HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
		int[] xor = new int[2 * q];
		
		ArrayList<Integer>[] adj = new ArrayList[2 * q];
		
		int[] color = new int[2 * q];
		
		for(int i = 0; i < 2 * q; i++) {
			adj[i] = new ArrayList<Integer>(); adj[i].add(i); color[i] = i;
		}
		
		int last = 0;
		
		while(q-- > 0) {
			
			int key = s.nextInt();
			int l = (s.nextInt() ^ last) + 1, r = (s.nextInt() ^ last) + 1;
			
			if(l > r) {
				int temp = l; l = r; r = temp;
			}
			
			if(key == 1) {
				
				int x = s.nextInt() ^ last;
				
				if(hm.get(l - 1) == null) 
					hm.put(l - 1, hm.size());
				
				if(hm.get(r) == null)
					hm.put(r, hm.size());
				
				int u = hm.get(l - 1), v = hm.get(r);
				
				if(color[u] == color[v])
					continue;
				
				if(adj[color[u]].size() < adj[color[v]].size()) {
					int temp = u; u = v; v = temp;
				}
				
				int small = color[v], big = color[u];
				
				int bigXor = xor[adj[big].get(adj[big].size() - 1)];
				
				int edge = bigXor ^ xor[u] ^ xor[v] ^ x;
				
				for(int y : adj[small]) {
					
					color[y] = big;
					adj[big].add(y);
					xor[y] ^= bigXor ^ edge;
				}
				
				adj[small].clear();
			}
			
			else {
				
				if(hm.get(l - 1) == null || hm.get(r) == null) {
					w.println(-1); last = 1; continue;
				}
				
				int u = hm.get(l - 1), v = hm.get(r);
				
				if(color[u] != color[v]) {
					w.println(-1); last = 1; continue;
				}
				
				last = xor[u] ^ xor[v];
				w.println(last);
			}
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
		new Thread(null, new cf1(),"cf1",1<<26).start();
	}
}   