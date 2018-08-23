import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
/* spar5h */ 

public class cf2 implements Runnable{    
	
	public static long gcd (long a, long b) {
    	
    	if(b == 0)
    		return a;
    	
    	return(gcd(b, a % b));
    }
	
	public void run() {
	    
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int[] prime = new int[(int)1e5 + 1]; 
		Arrays.fill(prime, 1); prime[0] = 0; prime[1] = 0;
		
		for(int i = 2; i <= (int)1e5; i++) {
			
			if(prime[i] == 0)
				continue;
			
			for(int j = 2; (long)i * j <= (int)1e5; j++)
				prime[i * j] = 0;
		}
		
		int t = 1;
		
		while(t-- > 0) {
			
			int n = s.nextInt();
			
			long[] a = new long[n + 1];
			long[] b = new long[n + 1];
			
			for(int i = 1; i <= n; i++) {
				a[i] = s.nextLong(); b[i] = s.nextLong();
			}
			
			if(n == 1) {
				w.println(a[1]); continue;
			}
			
			long val = a[1] * b[1];
			
			for(int i = 2; i <= n - 1; i++)
				val = gcd(val, a[i] * b[i]);
			
			long x = gcd(val, a[n]);
			long y = gcd(val, b[n]);
			
			if(x == 1 && y == 1) {
				w.println(-1); continue;
			}
			
			long res = -1;
			
			if(x > 1) {
				
				res = x;
				
				for(int i = 1; (long)i * i <= x; i++) {
					
					if(prime[i] == 0)
						continue;
					
					if(x % i == 0) {
						res = i; break;
					}
				}
			
			}
			
			else {
				
				res = y;
				
				for(int i = 1; (long)i * i <= y; i++) {
					
					if(prime[i] == 0)
						continue;
					
					if(y % i == 0) {
						res = i; break;
					}
				}
			}
			
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

