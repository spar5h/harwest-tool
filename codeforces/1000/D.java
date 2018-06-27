import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
/* spar5h */

public class cf4 implements Runnable{    
	
	static long modularInverse(long a, long m) {
		
		  return power(a, m - 2, m);
		}
		 
		static long power(long x, long y, long m) {
			
		    if (y == 0)
		        return 1;
		    
		    long p = power(x, y / 2, m) % m;
		    p = (p * p) % m;
		 
		    return (y % 2 == 0) ? p : (x * p) % m;
		}
		
		static long modFact(long n, long m) {
		 
		    if (m <= n)
		        return 0;
		 
		    long res = (m - 1);
		 
		    
		    for (long i = n + 1; i < m; i++)
		       res  = res * modularInverse(i, m) % m;
		    
		    return res;
		}
		 
		//combination
		static long nCr(int n, int r, long m) {
			
			return factorial[n] * modularInverse(factorial[r], m) % m * modularInverse(factorial[n - r], m) % m;  
		}
		
		static long[] factorial;
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		long mod = 998244353;
		
		factorial = new long[5001];
		factorial[0] = 1;
		
		for(int i = 1; i <= 5000; i++)
			factorial[i] = (factorial[i - 1] * (long)i) % mod;
		
		int n = s.nextInt();
		
		long[] a = new long[n + 1];
		
		for(int i = 1; i <= n; i++)
			a[i] = s.nextLong();
		
		long[] val = new long[n + 1];
		
		for(int i = n; i >= 1; i--) {
			
			if(a[i] <= 0 || a[i] > n - i)
				continue;
			
			val[i] = nCr(n - i, (int)a[i], mod);
			
			for(int j = i + 1; j <= n; j++) {
				
				if(j - i  - 1 < a[i])
					continue;
				
				if(val[j] == 0)
					continue;
				
				val[i] = (nCr(j - i - 1, (int)a[i], mod) * val[j] % mod + val[i]) % mod; 
			}
		}
		
		long res = 0;
		
		for(int i = 1; i <= n; i++)
			res = (res + val[i]) % mod;
		
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