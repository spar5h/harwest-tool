import java.util.*;
import java.lang.*;
import java.math.*;
import java.awt.List;
import java.io.*;
 
/* spar5h */

public class cf4 implements Runnable{    

	final static long mod = (long)1e9 + 7; 
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		char[] txt = s.next().toCharArray(); int n = txt.length;
		char[] pat = s.next().toCharArray(); int m = pat.length;
		
		int[] lps = new int[m]; 
		int len = 0, i = 1;
		
		while(i < m) {
			
			if(pat[i] == pat[len]) {
				len++; lps[i] = len; i++;
			}
			
			else if(len != 0)
				len = lps[len - 1];
			
			else
				i++;
		}
		
		int[] patternEnd = new int[n + 1];
		
		i = 0; int j = 0;
		
		while(i < n) {
			
			if(txt[i] == pat[j]) {
				i++; j++;
			}
			
			else if(j != 0)
				j = lps[j - 1];
			
			else
				i++;
			
			if(j == m)  {
				j = lps[j - 1]; patternEnd[i] = 1;
			}
		}
		
		long[] dp = new long[n + 1], dp2 = new long[n + 1], dp3 = new long[n + 1];
		
		int lim = 1;
		
		for(i = 1; i <= n; i++) {
			
			dp[i] = dp[i - 1];
			
			if(patternEnd[i] == 1) {
				
				dp[i] = (dp[i] + dp3[i - m]) % mod;
				
				if(lim - 2 >= 1)
					dp[i] = (dp[i] - dp3[lim - 2] + mod) % mod;
				
				dp[i] = (dp[i] + i - m + 1 - lim + 1) % mod;
			
				lim = i - m + 2;
			}	
			
			dp2[i] = (dp2[i - 1] + dp[i]) % mod;
			dp3[i] = (dp3[i - 1] + dp2[i]) % mod;
		}
		
		w.println(dp2[n]);
		
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