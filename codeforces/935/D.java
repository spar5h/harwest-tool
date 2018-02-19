import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
/* spar5h */
 
public class cf4 implements Runnable{    
	
	final static long mod = (long)1e9 + 7;
	
	static long power(long x, long y)
	{
	    if (y == 0)
	        return 1;
	    
	    long p = power(x, y/2) % mod;
	    p = p * p % mod;
	 
	    return y % 2 == 0 ? p : x * p % mod;
	}
	
	public void run() {
	    
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = s.nextInt();
		int m = s.nextInt();
		
		int[] a = new int[n + 1];
		int[] b = new int[n + 1];
		
		for(int i = 1; i <= n; i++)
			a[i] = s.nextInt();
		
		for(int i = 1; i <= n; i++)
			b[i] = s.nextInt();	
		
		long ans = 0;
		long currInv = 1;
		
		for(int i = 1; i <= n; i++) {
			
			if(a[i] == 0 && b[i] == 0) {
				
				long res = currInv;
				res = res * (m - 1) % mod;
				res = res * power(2, mod - 2) % mod;
				res = res * power(m, mod - 2) % mod;
				ans = (ans + res) % mod;
				currInv = currInv * power(m, mod - 2) % mod;
			}
			
			else if(a[i] == 0) {
				
				long res = currInv;
				res = (res * (m - b[i])) % mod;
				res = res * power(m, mod - 2) % mod;
				ans = (ans + res) % mod;
				currInv = currInv * power(m, mod - 2) % mod;
			}
			
			else if(b[i] == 0) {
				
				
				long res = currInv;
				res = (res * (a[i] - 1)) % mod;
				res = res * power(m, mod - 2) % mod;
				ans = (ans + res) % mod;
				currInv = currInv * power(m, mod - 2) % mod;
			}
			
			else {
				
				if(a[i] < b[i])
					break;
				
				if(a[i] > b[i]) {
					
					ans = (ans + currInv) % mod;
					currInv = currInv * power(m, mod - 2) % mod;
					break;
				}
			}
			
			
		}
		
		w.println(ans);
		
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

