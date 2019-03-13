import java.util.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

public class cf6 implements Runnable {    
	
	static boolean[][][] dp;
	static boolean[] valid, vis;
	static int p;
	
	static void kill(int mask, int i, int j) {
		
		dp[mask][i][j] = false;
		
		for(int k = 0; k < p; k++)
			if(k != i && k != j && (mask & (1 << k)) > 0 && dp[mask - (1 << k)][i][j])
				kill(mask - (1 << k), i, j);
	}
	
	static void dfs(int mask) {
		
		vis[mask] = true;
		
		for(int k = 0; k < p; k++)
			if((mask & (1 << k)) > 0 && valid[mask - (1 << k)] && !vis[mask - (1 << k)])
				dfs(mask - (1 << k));
	}
	
	public void run() {
	
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = s.nextInt();
		p = s.nextInt();
		
		String temp = s.next();
		
		int[] c = new int[n + 1];
		
		for(int i = 1; i <= n; i++)
			c[i] = temp.charAt(i - 1) - 'a';
		
		int[][] a = new int[p][p];
		
		for(int i = 0; i < p; i++)
			for(int j = 0; j < p; j++)
				a[i][j] = s.nextInt();
		
		int[][] f = new int[n + 1][p];
		
		int[] curr = new int[p];
		int[][] left = new int[n + 1][p];
		
		for(int i = 1; i <= n; i++) {
			
			for(int j = 0; j < p; j++) {
				f[i][j] = f[i - 1][j];
				left[i][j] = curr[j];
			}	
			
			f[i][c[i]]++;
			curr[c[i]] = i;
		}
		
		dp = new boolean[1 << p][p][];
		
		for(int i = 0; i < (1 << p); i++) {
			for(int j = 0; j < p; j++) {
				dp[i][j] = new boolean[j + 1];
				Arrays.fill(dp[i][j], true);
			}
		}	
			
		for(int i = 1; i <= n; i++) {
			
			for(int j = 0; j < p; j++) {
				
				if(a[c[i]][j] == 1)
					continue;
				
				if(left[i][j] > left[i][c[i]] || (c[i] == j && left[i][j] > 0)) {
					
					int mask = (1 << p) - 1;
					
					for(int k = 0; k < p; k++)
						if(f[i - 1][k] - f[left[i][j]][k] > 0)
							mask -= 1 << k;
					
					if(dp[mask][max(c[i], j)][min(c[i], j)])
						kill(mask, max(c[i], j), min(c[i], j));
				}
			}
		}
		
		valid = new boolean[1 << p];
		vis = new boolean[1 << p];
		
		outer:
		for(int i = 0; i < (1 << p); i++) {
			
			for(int j = 0; j < p; j++)
				for(int k = 0; k <= j; k++)
					if(!dp[i][j][k])
						continue outer;
			
			valid[i] = true;
		}
		
		dfs((1 << p) - 1);
		
		int res = n;
		
		for(int i = 0; i < (1 << p); i++) {
			
			if(!valid[i] || !vis[i])
				continue;
			
			int can = 0;
			
			for(int j = 0; j < p; j++)
				if((i & (1 << j)) > 0)
					can += f[n][j];
			
			res = min(can, res);
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
		new Thread(null, new cf6(),"cf6",1<<26).start();
	}
}   