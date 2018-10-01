import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

public class cf4{  
	
	final static long mod = 998244353;
	
	public static void main(String[] args) {

		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);		

		int n = s.nextInt(), k = s.nextInt();
		
		long[][] pre = new long[4 + 1][k + 1];
		long[][] dp = new long[4 + 1][k + 1];
		
		//ww wb bw bb
		
		dp[1][1] = 1; 
		
		if(k >= 2) {
			dp[2][2] = 1; dp[3][2] = 1;
		}
		 
		dp[4][1] = 1;
		
		for(int i = 2; i <= n; i++) {
			
			for(int j = 0; j <= k; j++)
				for(int l = 1; l <= 4; l++) {
					pre[l][j] = dp[l][j]; dp[l][j] = 0;}
			
			for(int j = 0; j <= k; j++) {
				
				dp[1][j] = (dp[1][j] + pre[1][j]) % mod;
				
				if(j + 1 <= k) {
					dp[2][j + 1] = (dp[2][j + 1] + pre[1][j]) % mod;
					dp[3][j + 1] = (dp[3][j + 1] + pre[1][j]) % mod;
					dp[4][j + 1] = (dp[4][j + 1] + pre[1][j]) % mod;
				}
				
				dp[1][j] = (dp[1][j] + pre[2][j]) % mod;
				dp[2][j] = (dp[2][j] + pre[2][j]) % mod;
				dp[4][j] = (dp[4][j] + pre[2][j]) % mod;
				
				if(j + 2 <= k) {
					dp[3][j + 2] = (dp[3][j + 2] + pre[2][j]) % mod;
				}
				
				dp[1][j] = (dp[1][j] + pre[2][j]) % mod;
				dp[3][j] = (dp[3][j] + pre[2][j]) % mod;
				dp[4][j] = (dp[4][j] + pre[2][j]) % mod;
				
				if(j + 2 <= k) {
					dp[2][j + 2] = (dp[2][j + 2] + pre[2][j]) % mod;
				}
				
				dp[4][j] = (dp[4][j] + pre[1][j]) % mod;
				
				if(j + 1 <= k) {
					dp[2][j + 1] = (dp[2][j + 1] + pre[1][j]) % mod;
					dp[3][j + 1] = (dp[3][j + 1] + pre[1][j]) % mod;
					dp[1][j + 1] = (dp[1][j + 1] + pre[1][j]) % mod;
				}
			}
			
		}
		
		long res = 0;
		
		for(int i = 1; i <= 4; i++)
			res = (res + dp[i][k]) % mod;
		
		w.println(res);
		
		w.close();
	}
	
	static class InputReader {
		
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;
		private SpaceCharFilter filter;
		
		public InputReader(InputStream stream)
		{
			this.stream = stream;
		}
		
		public int read()
		{
			if (numChars==-1) 
				throw new InputMismatchException();
			
			if (curChar >= numChars)
			{
				curChar = 0;
				try 
				{
					numChars = stream.read(buf);
				}
				catch (IOException e)
				{
					throw new InputMismatchException();
				}
				
				if(numChars <= 0)				
					return -1;
			}
			return buf[curChar++];
		}
	 
		public String nextLine()
		{
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			String str = "";
            try
            {
                str = br.readLine();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return str;
		}
		public int nextInt()
		{
			int c = read();
			
			while(isSpaceChar(c)) 
				c = read();
			
			int sgn = 1;
			
			if (c == '-') 
			{
				sgn = -1;
				c = read();
			}
			
			int res = 0;
			do 
			{
				if(c<'0'||c>'9') 
					throw new InputMismatchException();
				res *= 10;
				res += c - '0';
				c = read();
			}
			while (!isSpaceChar(c)); 
			
			return res * sgn;
		}
		
		public long nextLong() 
		{
			int c = read();
			while (isSpaceChar(c))
				c = read();
			int sgn = 1;
			if (c == '-') 
			{
				sgn = -1;
				c = read();
			}
			long res = 0;
			
			do 
			{
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				res *= 10;
				res += c - '0';
				c = read();
			}
			while (!isSpaceChar(c));
				return res * sgn;
		}
		
		public double nextDouble() 
		{
			int c = read();
			while (isSpaceChar(c))
				c = read();
			int sgn = 1;
			if (c == '-') 
			{
				sgn = -1;
				c = read();
			}
			double res = 0;
			while (!isSpaceChar(c) && c != '.') 
			{
				if (c == 'e' || c == 'E')
					return res * Math.pow(10, nextInt());
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				res *= 10;
				res += c - '0';
				c = read();
			}
			if (c == '.') 
			{
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
		
		public String readString() 
		{
			int c = read();
			while (isSpaceChar(c))
				c = read();
			StringBuilder res = new StringBuilder();
			do 
			{
				res.appendCodePoint(c);
				c = read();
			} 
			while (!isSpaceChar(c));
			
			return res.toString();
		}
	 
		public boolean isSpaceChar(int c) 
		{
			if (filter != null)
				return filter.isSpaceChar(c);
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}
	 
		public String next() 
		{
			return readString();
		}
		
		public interface SpaceCharFilter 
		{
			public boolean isSpaceChar(int ch);
		}
	}
    
}