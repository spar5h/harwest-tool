import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

public class cf1 implements Runnable{    
	
	static class pair {
		
		int i, j;
		
		pair(int i, int j) {
			this.i = i; this.j = j;
		}
	}
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);

		int n = s.nextInt(), k = s.nextInt();
		
		char[][] a = new char[n][];
		
		for(int i = 0; i < n; i++)
			a[i] = s.next().toCharArray();
		
		int[][] d = new int[n][n];
		
		for(int i = 0; i < n; i++)
			Arrays.fill(d[i], -1);
		
		Queue<pair> q0 = new LinkedList<pair>(), q1 = new LinkedList<pair>();
		
		if(a[0][0] == 'a') {
			q0.add(new pair(0, 0)); d[0][0] = 0;
		}
		else if(k >= 1) {
			q0.add(new pair(0, 0)); d[0][0] = 1;
		}
		
		while(true) {
			
			while(!q0.isEmpty()) {
				
				pair x = q0.poll();
				
				if(x.i + 1 < n && d[x.i + 1][x.j] == -1) {
					
					if(a[x.i + 1][x.j] == 'a') {
						q0.add(new pair(x.i + 1, x.j)); d[x.i + 1][x.j] = d[x.i][x.j];
					}
					else if(d[x.i][x.j] + 1 <= k) { 
						q1.add(new pair(x.i + 1, x.j)); d[x.i + 1][x.j] = d[x.i][x.j] + 1;
					}
				}
				
				if(x.j + 1 < n && d[x.i][x.j + 1] == -1) {
					
					if(a[x.i][x.j + 1] == 'a') {
						q0.add(new pair(x.i, x.j + 1)); d[x.i][x.j + 1] = d[x.i][x.j];
					}
					else if(d[x.i][x.j] + 1 <= k) { 
						q1.add(new pair(x.i, x.j + 1)); d[x.i][x.j + 1] = d[x.i][x.j] + 1;
					}
				}
			}
			
			if(q1.isEmpty())
				break;
			
			q0 = q1;
			q1 = new LinkedList<pair>();
		}
		
		for(int i = 0; i < n; i++)
			for(int j = 0; j < n; j++)
				if(d[i][j] != -1)
					a[i][j] = 'a';
		
		StringBuffer res = new StringBuffer();
		
		int[][] dead = new int[n][n];
		
		for(int x = 0; x < 2 * n - 1; x++) {
			
			int i = x - max(x - (n - 1), 0), j = max(x - (n - 1), 0);
			
			int min = 26;
			
			while(i >= 0 && j < n) {
				
				boolean di = false, dj = false;
				
				if(i - 1 < 0 || dead[i - 1][j] == 1)
					di = true;
				
				if(j - 1 < 0 || dead[i][j - 1] == 1)
					dj = true;
				
				if(x > 0 && di && dj) 
					dead[i][j] = 1;
				
				if(dead[i][j] == 0)
					min = min(min, a[i][j] - 'a');
				
				i--; j++;
			}
			
			i = x - max(x - (n - 1), 0); j = max(x - (n - 1), 0);
			
			while(i >= 0 && j < n) {
				
				if(dead[i][j] == 1) {
					i--; j++; continue;
				}
				
				if(a[i][j] - 'a' != min)
					dead[i][j] = 1;
				
				i--; j++;
			}
			
			res.append((char)('a' + min));
		}
		
		w.println(res);
		
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