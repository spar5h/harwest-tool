import java.util.*;
import java.io.*;
import java.math.BigInteger;
import static java.lang.Math.*;

/* spar5h */

public class cf4 implements Runnable { 
	
	long[][][] dp;
	long mod = (long)1e9 + 7;
	
	void recur(int n, int k, int x) {
		
		dp[n][k][x] = 0;
		
		if(k == dp[n].length - 1) {
			
			if(n != 0)
				dp[n][k][x] = -2;
		
			return;
		}
		
		int c = 0;
		
		if(x == 0) {
			
			if(n > 0) {
				
				if(dp[n - 1][k + 1][1] == -1)
					recur(n - 1, k + 1, 1);
				
				if(dp[n - 1][k + 1][1] != -2) {
					dp[n][k][0] = (dp[n - 1][k + 1][1] + dp[n][k][0]) % mod;
					c++;
				}	
			}
			
			if(dp[n + 1][k + 1][1] == -1)
				recur(n + 1, k + 1, 1);
			
			if(dp[n + 1][k + 1][1] != -2) {
				dp[n][k][0] = (dp[n + 1][k + 1][1] + dp[n][k][0]) % mod;
				c++;
			}
		}
		
		else if(x == 1) {
			
			dp[n][k][x] = 1;
			
			if(n > 0) {
				
				if(dp[n - 1][k + 1][0] == -1)
					recur(n - 1, k + 1, 0);
				
				if(dp[n - 1][k + 1][0] != -2) {
					dp[n][k][1] = (dp[n - 1][k + 1][0] + dp[n][k][1]) % mod;
					c++;
				}
				
				if(dp[n + 1][k + 1][1] == -1)
					recur(n + 1, k + 1, 1);
				
				if(dp[n + 1][k + 1][1] != -2) {
					dp[n][k][1] = (dp[n + 1][k + 1][1] + dp[n][k][1]) % mod;
					c++;
				}
			}
			
			else {
				
				if(dp[n + 1][k + 1][0] == -1)
					recur(n + 1, k + 1, 0);
				
				if(dp[n + 1][k + 1][0] != -2) {
					dp[n][k][1] = (dp[n + 1][k + 1][0] + dp[n][k][1]) % mod;
					c++;
				}
			}
			
		}

		if(c == 0)
			dp[n][k][x] = -2;
	}
	
	public void run() {
			
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = s.nextInt();
		
		dp = new long[2 * n + 1][2 * n + 1][2];
		for(int i = 0; i <= 2 * n; i++)
			for(int j = 0; j <= 2 * n; j++)
				for(int k = 0; k < 2; k++)
					dp[i][j][k] = -1;
		
		recur(0, 0, 1);
		
		w.println(dp[0][0][1]);
		
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