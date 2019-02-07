import java.util.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

public class cf4 implements Runnable {   
	
	public void run() {
			
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int t = 1;
		
		while(t-- > 0) {
			
			int n = s.nextInt(), m = s.nextInt();
			
			int[] f = new int[m];
			
			for(int i = 0; i < n; i++)
				f[s.nextInt() - 1]++;
			
			if(m == 1) {
				w.println(f[0] / 3);
				continue;
			}
			
			long[][][] dp = new long[5][5][m];
			
			for(int j = 0; j < 5; j++) {
				for(int k = 0; k < 5; k++) {
					
					if(f[0] >= j && f[1] >= k)
						dp[j][k][1] = (f[0] - j) / 3 + (f[1] - k) / 3;
					else
						dp[j][k][1] = -1;
				}
			}
			
			for(int i = 2; i < m; i++) {
				for(int j = 0; j < 5; j++) {
					for(int k = 0; k < 5; k++) {
						
						dp[j][k][i] = -1;
						
						if(f[i] >= k && dp[0][j][i - 1] != -1) {
							dp[j][k][i] = max((f[i] - k) / 3 + dp[0][j][i - 1], dp[j][k][i]);
						}
						
						if(j + 1 < 5 && f[i] >= k + 1 && dp[1][j + 1][i - 1] != -1) {
							dp[j][k][i] = max((f[i] - k - 1) / 3 + dp[1][j + 1][i - 1] + 1, dp[j][k][i]);
						}
						
						if(j + 2 < 5 && f[i] >= k + 2 && dp[2][j + 2][i - 1] != -1) {
							dp[j][k][i] = max((f[i] - k - 2) / 3 + dp[2][j + 2][i - 1] + 2, dp[j][k][i]);
						}

					}
				}
			}
			
			long res = 0;
			
			for(int j = 0; j < 5; j++)
				for(int k = 0; k < 5; k++)
					res = max(dp[j][k][m - 1], res);
			
			w.println(res);
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
		new Thread(null, new cf4(),"cf4",1<<26).start();
	}
}