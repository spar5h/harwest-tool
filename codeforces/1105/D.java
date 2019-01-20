import java.util.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

public class cf4 implements Runnable {   
	
	static class Pair {
		
		int i, j;
		
		Pair(int i, int j) {
			this.i = i; this.j = j;
		}
	}
	
	public void run() {
			
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = s.nextInt(), m = s.nextInt(), p = s.nextInt();
		
		long[] spd = new long[p];
		
		for(int i = 0; i < p; i++)
			spd[i] = s.nextLong();
		
		char[][] a = new char[n][];
		int[][] vis = new int[n][m];
		int[][] level = new int[n][m];
		
		Queue<Pair>[] q = new Queue[p];
		
		for(int i = 0; i < p; i++)
			q[i] = new LinkedList<Pair>();	
		
		for(int i = 0; i < n; i++) {
			
			a[i] = s.next().toCharArray();
			
			for(int j = 0; j < m; j++) {
				
				vis[i][j] = -1;
				level[i][j] = -1;
				
				if(a[i][j] != '#' && a[i][j] != '.') {
					q[a[i][j] - '1'].add(new Pair(i, j));
					vis[i][j] = a[i][j] - '1';
					level[i][j] = 0;
				}
			}	
		}
		
		int[] done = new int[p];
		
		long curr = 1;
		int doneCount = 0;
		
		while(doneCount < p) {
			
			for(int i = 0; i < p; i++) {
				
				if(done[i] == 1)
					 continue;
				
				int added = 0;
				
				while(!q[i].isEmpty()) {
					
					Pair x = q[i].peek();
					
					if(level[x.i][x.j] >= curr * spd[i])
						break;
					
					q[i].poll();
					
					if(x.i - 1 >= 0 && a[x.i - 1][x.j] == '.' && vis[x.i - 1][x.j] == -1) {
						added++;
						q[i].add(new Pair(x.i - 1, x.j));
						level[x.i - 1][x.j] = level[x.i][x.j] + 1;
						vis[x.i - 1][x.j] = i;
					}
					
					if(x.i + 1 < n && a[x.i + 1][x.j] == '.' && vis[x.i + 1][x.j] == -1) {
						added++;
						q[i].add(new Pair(x.i + 1, x.j));
						level[x.i + 1][x.j] = level[x.i][x.j] + 1;
						vis[x.i + 1][x.j] = i;
					}
					
					if(x.j - 1 >= 0 && a[x.i][x.j - 1] == '.' && vis[x.i][x.j - 1] == -1) {
						added++;
						q[i].add(new Pair(x.i, x.j - 1));
						level[x.i][x.j - 1] = level[x.i][x.j] + 1;
						vis[x.i][x.j - 1] = i;
					}
					
					if(x.j + 1 < m && a[x.i][x.j + 1] == '.' && vis[x.i][x.j + 1] == -1) {
						added++;
						q[i].add(new Pair(x.i, x.j + 1));
						level[x.i][x.j + 1] = level[x.i][x.j] + 1;
						vis[x.i][x.j + 1] = i;
					}
				}
				
				if(added == 0) {
					done[i] = 1; doneCount++;
				}	
			}
		
			curr++;
		}
		
		int[] res = new int[p];
		
		for(int i = 0; i < n; i++)
			for(int j = 0; j < m; j++)
				if(vis[i][j] != -1)
					res[vis[i][j]]++;
		
		for(int i = 0; i < p; i++)
			w.print(res[i] + " ");
		
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