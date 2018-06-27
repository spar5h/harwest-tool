import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
/* spar5h */ 

public class cf2 implements Runnable{    
	
	final static long mod = (long)1e9 + 7; 
	
	static void multiply(long[][] a, long[][] b) {
		
		long[][] c = new long[2][2];
		
		for(int i = 0; i < 2; i++)
			for(int j = 0; j < 2; j++)
				for(int k = 0; k < 2; k++)
					c[i][j] = (c[i][j] + (a[i][k] * b[k][j]) % mod) % mod;
		
		for(int i = 0; i < 2; i++)
			for(int j = 0; j < 2; j++)
				a[i][j] = c[i][j];	
	}
	
	static void pow(long[][] a, long x) {
		
		long[][] M = {{1, (-1 + mod) % mod}, {1, 0}};
		
		if(x == 1)
			return; //fib[1] * a[0][0] + fib[0] * a[0][1]
		
		pow(a, x / 2);
		
		multiply(a, a);
		
		if(x % 2 == 1)
			multiply(a, M);
	}
	
	static long res(long x, long y, long n) {
		
		if(n == 1)
			return (x + mod) % mod;
		
		if(n == 2)
			return (y + mod) % mod;
		
		long[][] a = {{1, (-1 + mod) % mod},{1, 0}};
		
		pow(a, n - 2);
		
		return ((y * a[0][0] % mod + mod) % mod + (x * a[0][1] % mod + mod) % mod + 2 * mod) % mod;
	}
	
	public void run() {
	    
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		w.println(res(s.nextLong(), s.nextLong(), s.nextLong()));
		
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
		new Thread(null, new cf2(),"cf2",1<<26).start();
	}
}   

