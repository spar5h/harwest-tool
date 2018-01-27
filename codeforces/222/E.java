import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
/* spar5h */
 
public class codechef implements Runnable {    
    
	final static long mod = (long)1e9 + 7;
	static int m;
	static long init[][];
	
	static void multiply(long[][] a, long[][] b) {
		
		long[][] c = new long[m][m];
		
		for(int i = 0; i < m; i++)
			for(int j = 0; j < m; j++)
				for(int k = 0; k < m; k++)
					c[i][j] = (c[i][j] + (a[i][k] * b[k][j]) % mod) % mod;
		
		for(int i = 0; i < m; i++)
			for(int j = 0; j < m; j++)
				a[i][j] = c[i][j];
	}
	
	static void pow(long[][] a, long n) {
		
		if(n == 1)
			return;
		
		pow(a, n / 2);
		
		multiply(a, a);
		
		if(n % 2 == 1)
			multiply(a, init);
	}
	
	static long res(long[][] a, long n) {
		
		if(n == 1) 
			return m;
		
		pow(a, n - 1);
		
		long res = 0;
		
		for(int i = 0; i < m; i++)
			for(int j = 0 ; j < m; j++)
				res = (res + a[i][j]) % mod;
		
		return res;
	}
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		long n = s.nextLong();
		m = s.nextInt();
		int k = s.nextInt();
		
		long a[][] = new long[m][m];
		
		for(int i = 0; i < m; i++)
			Arrays.fill(a[i], 1);
		
		while(k-- > 0) {
			
			String str = s.next();
			
			int c1 = ('a' <= str.charAt(0) && str.charAt(0) <= 'z') ? str.charAt(0) - 'a' : str.charAt(0) - 'A' + 26;
			int c2 = ('a' <= str.charAt(1) && str.charAt(1) <= 'z') ? str.charAt(1) - 'a' : str.charAt(1) - 'A' + 26;
			
			a[c1][c2] = 0;
 		}
			
		init = new long[m][m];
		
		for(int i = 0; i < m; i++)
			for(int j = 0; j < m; j++)
				init[i][j] = a[i][j];
		
		w.println(res(a, n));
		
		w.close();
		
	}
	
	static class InputReader
	{
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
		new Thread(null, new codechef(),"codechef",1<<26).start();
	}
	   
} 