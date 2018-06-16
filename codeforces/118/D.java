import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
/* spar5h */
 
public class cf7 implements Runnable{    
	
	final static long mod = (long)1e8; 
	
	public void run() {
	    
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n1 = s.nextInt(), n2 = s.nextInt(), k1 = s.nextInt(), k2 = s.nextInt();
		
		//dp[length][footmen][suffix]
		
		long[][][] dp = new long[n1 + n2 + 1][n1 + 1][k1 + 1];
		long[][][] dp2 = new long[n1 + n2 + 1][n2 + 1][k2 + 1];
		dp[0][0][0] = 1; dp2[0][0][0] = 1;
		
		for(int i = 1; i <= n1 + n2; i++) {
			
			for(int j = 1; j <= n1; j++) {
				
				for(int k = 1; k <= k1; k++) {
					
					if(i - k < 0 || i - j < 0 || i - j > n2) 
						 continue;
					
					for(int m = 0; m <= k2; m++)
						dp[i][j][k] = (dp[i][j][k] + dp2[i - k][i - j][m]) % mod;
				}
			}
			
			for(int j = 1; j <= n2; j++) {
				
				for(int k = 1; k <= k2; k++) {
					
					if(i - k < 0 || i - j < 0 || i - j > n1) 
						continue;
					
					
					for(int m = 0; m <= k1; m++)
						dp2[i][j][k] = (dp2[i][j][k] + dp[i - k][i - j][m]) % mod;
				}
			}
		}
		
		long res = 0;
		
		for(int i = 1; i <= k1; i++)
			res = (res + dp[n1 + n2][n1][i]) % mod;
		
		for(int i = 1; i <= k2; i++)
			res = (res + dp2[n1 + n2][n2][i]) % mod;
		
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
    
	public static void main(String args[]) throws Exception
	{
		new Thread(null, new cf7(),"cf7",1<<26).start();
	}
}   