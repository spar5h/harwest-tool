import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
/* spar5h */

public class cf4 implements Runnable{    
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = s.nextInt(), m = s.nextInt();
		
		int[][] a = new int[n + 1][m + 1];
		
		for(int i = 1; i <= n; i++)
			for(int j = 1; j <= m; j++)
				a[i][j] = s.nextInt();
		
		long[][][] dp = new long[4][n + 2][m + 2];
		
		for(int i = 1; i <= n; i++)
			for(int j = 1; j <= m; j++)
				dp[0][i][j] = Math.max(dp[0][i - 1][j], dp[0][i][j - 1]) + a[i][j];
		
		for(int i = 1; i <= n; i++)
			for(int j = m; j >= 1; j--)
				dp[1][i][j] = Math.max(dp[1][i - 1][j], dp[1][i][j + 1]) + a[i][j];
		
		for(int i = n; i >= 1; i--)
			for(int j = 1; j <= m; j++)
				dp[2][i][j] = Math.max(dp[2][i + 1][j], dp[2][i][j - 1]) + a[i][j];
				
		for(int i = n; i >= 1; i--)
			for(int j = m; j >= 1; j--)
				dp[3][i][j] = Math.max(dp[3][i + 1][j], dp[3][i][j + 1]) + a[i][j];
			
		long res = 0;
		
		for(int i = 2; i <= n - 1; i++) {
			
			for(int j = 2; j <= m - 1; j++) {
				
				long v = dp[0][i][j - 1] + dp[1][i - 1][j] + dp[2][i + 1][j] + dp[3][i][j + 1];
				res = Math.max(res, v);
				v = dp[0][i - 1][j] + dp[1][i][j + 1] + dp[2][i][j - 1] + dp[3][i + 1][j];
				res = Math.max(res, v);
			}
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
	new Thread(null, new cf4(),"cf4",1<<26).start();
	}
}   