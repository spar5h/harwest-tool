import java.util.*;
import java.io.*;
import java.math.BigInteger;
import static java.lang.Math.*;
 
/* spar5h */
 
public class cf1 implements Runnable {   
	
	final long mod = (long)1e9 + 7;
	
	long modExp(long x, long y) {
		
		if(y == 0)
			return 1;
		
		if(y == 1)
			return x;
		
		long ret = modExp(x, y / 2);
		
		ret = ret * ret % mod;
		
		if(y % 2 == 1)
			ret = ret * x % mod;
		
		return ret;
	}
	
	long[] fact, invFact;
	
	long nCr(int n, int r) {
		return fact[n] * invFact[r] % mod * invFact[n - r] % mod;
	}
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		fact = new long[50 + 1]; fact[0] = 1;
		invFact = new long[50 + 1]; invFact[0] = 1;
		
		for(int i = 1; i <= 50; i++) {
			fact[i] = fact[i - 1] * i % mod;
			invFact[i] = invFact[i - 1] * modExp(i, mod - 2) % mod;
		}
		
		int n = s.nextInt(), k = s.nextInt() / 50;
		
		int[] f = new int[3];
		
		for(int i = 0; i < n; i++)
			f[s.nextInt() / 50]++;
		
		int LIM = 6 * n;
		
		long[][][] dp = new long[LIM][f[1] + 1][f[2] + 1];
		
		dp[0][f[1]][f[2]] = 1;
		
		int res = -1;
		
		for(int i = 1; i < LIM; i++) {
			
			if(i % 2 == 1) {
				for(int a = 0; a <= f[1]; a++) 
					for(int b = 0; b <= f[2]; b++) 
						for(int c = 0; c <= f[1] - a; c++)
							for(int d = 0; d <= f[2] - b; d++)
								if(c + d >= 1 && c + d * 2 <= k)
									dp[i][a][b] = (dp[i][a][b] + dp[i - 1][a + c][b + d] * nCr(a + c, c) % mod * nCr(b + d, d)) % mod;
			}
			
			else {
				for(int a = 0; a <= f[1]; a++) 
					for(int b = 0; b <= f[2]; b++) 
						for(int c = 0; c <= a; c++)
							for(int d = 0; d <= b; d++)
								if(c + d >= 1 && c + d * 2 <= k) 
									dp[i][a][b] = (dp[i][a][b] + dp[i - 1][a - c][b - d] * nCr(f[1] - (a - c), c) % mod * nCr(f[2] - (b - d), d) % mod) % mod;							
			}
			
			if(dp[i][0][0] > 0) {
				res = i; break;
			}
		}
		
		w.println(res);
		
		if(res != -1)
			w.println(dp[res][0][0]);
		else
			w.println(0);
			
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