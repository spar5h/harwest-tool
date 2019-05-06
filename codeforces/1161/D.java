import java.util.*;
import java.io.*;
import java.math.BigInteger;
import static java.lang.Math.*;

/* spar5h */

public class cf4 implements Runnable { 
	
	final long mod = 998244353;
	
	int n, k;
	long[][][] dp;
	int[][] vis;
	char[] a;
	
	void recur(int i, int j) {
		
		//i -> which string
		//j -> index
		//k -> second string starting point
		
		vis[i][j] = 1;
		
		if(i == 0) {
			
			if(j != n - j - 1 && vis[0][n - j - 1] == 0) {
				
				recur(0, n - j - 1);
				
				dp[0][j][0] = (dp[0][j][0] + dp[0][n - j - 1][0]) % mod;
				dp[0][j][1] = (dp[0][j][1] + dp[0][n - j - 1][1]) % mod;
			}
			
			else if(j >= k && vis[1][j] == 0) {
				
				recur(1, j);
				
				if(a[j] != '1') {
		
					if(j != k)
						dp[0][j][0] = (dp[0][j][0] + dp[1][j][0]) % mod;
	
					dp[0][j][1] = (dp[0][j][1] + dp[1][j][1]) % mod;
				}
				
				if(a[j] != '0') {
					
					dp[0][j][0] = (dp[0][j][0] + dp[1][j][1]) % mod;
					
					if(j != k)
						dp[0][j][1] = (dp[0][j][1] + dp[1][j][0]) % mod;
				}
			}
			
			else if(j < k) {
				
				if(a[j] != '1') {
					dp[0][j][0] = 1;
				}
				
				if(a[j] != '0') {
					dp[0][j][1] = 1;
				}	
			}
			
			else {
				
				dp[0][j][0] = 1;
				dp[0][j][1] = 1;	
			}
		}
		
		else {
			
			if(j != n - 1 - (j - k) && vis[1][n - 1 - (j - k)] == 0) {
				
				recur(1, n - 1 - (j - k));
				
				dp[1][j][0] = (dp[1][j][0] + dp[1][n - 1 - (j - k)][0]) % mod;
				dp[1][j][1] = (dp[1][j][1] + dp[1][n - 1 - (j - k)][1]) % mod;
			}
			
			else if(vis[0][j] == 0) {
				
				recur(0, j);
				
				if(a[j] != '1') {
					
					if(j != k)
						dp[1][j][0] = (dp[1][j][0] + dp[0][j][0]) % mod;
		
					dp[1][j][1] = (dp[1][j][1] + dp[0][j][1]) % mod;
				}
				
				if(a[j] != '0') {
					
					if(j != k)
						dp[1][j][0] = (dp[1][j][0] + dp[0][j][1]) % mod;
					
					dp[1][j][1] = (dp[1][j][1] + dp[0][j][0]) % mod;
				}
			}
			
			else {
				
				dp[1][j][0] = 1;
				dp[1][j][1] = 1;	
			}
		}
		
	}
	
	public void run() {
			
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		long res = 0;
	
		a = s.next().toCharArray();
		n = a.length;
		
		//0 case

		for(k = 1; k < n; k++) {
			
			dp = new long[2][n][2];
			vis = new int[2][n];
			
			long add = 1;
			
			for(int i = 0; i < k; i++) {
				
				if(vis[0][i] == 1)
					continue;
				
				recur(0, i);
				
				long mul = 0;
				
				if(a[i] != '1')
					mul = (mul + dp[0][i][0]) % mod;
				
				if(a[i] != '0')
					mul = (mul + dp[0][i][1]) % mod;
				
				add = add * mul % mod;
			}
			
			res = (res + add) % mod;
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
		new Thread(null, new cf4(),"cf4",1<<26).start();
	}
}