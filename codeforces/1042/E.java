import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

//hm.get((long)0) != hm.get(0)

public class cf5{  
	
	static class ob {
		
		int i, j;
		long w;
		
		ob(int i, int j, long w) {
			this.i = i; this.j = j; this.w = w;
		}
	}
	
	static class comp implements Comparator<ob> {
		
		public int compare(ob x, ob y) {
			
			if(x.w < y.w)
				return -1;
			
			if(x.w > y.w)
				return 1;
			
			return 0;
		}
	}
	
	static long modExp(long x, long y, long m) {
		
		if(y == 0)
			return 1;
		
		if(y == 1)
			return x;
		
		long res = modExp(x, y / 2, m);
		
		res = (res * res) % m;
		
		if(y % 2 == 1)
			res = (res * x) % m;
		
		return res;
	}
	
	public static void main(String[] args) {

		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);		
		
		long mod = 998244353;
		
		int n = s.nextInt(), m = s.nextInt();
		
		ArrayList<ob> a = new ArrayList<ob>();
		
		for(int i = 0; i < n; i++)
			for(int j = 0; j < m; j++)
				a.add(new ob(i, j, s.nextLong()));
		
		Collections.sort(a, new comp());
		
		long[][] dp = new long[n][m];
		
		long r = 0, r2 = 0, c = 0, c2 = 0, dpSum = 0;
		
		int i = 0;
		
		while(i < n * m) {
			
			long key = a.get(i).w;
			
			int temp = i;
			
			while(i < n * m && key == a.get(i).w) {
				
				int xi = a.get(i).i, xj = a.get(i).j;
				
				long inv = modExp(temp, mod - 2, mod);
				
				dp[xi][xj] = (dpSum * inv % mod + dp[xi][xj]) % mod;
				dp[xi][xj] = (r2 * inv % mod + dp[xi][xj]) % mod;
				dp[xi][xj] = (-(2 * r % mod * xi % mod * inv % mod) + mod + dp[xi][xj]) % mod;
				dp[xi][xj] = ((long)xi * xi % mod * temp % mod * inv % mod + dp[xi][xj]) % mod;
				dp[xi][xj] = (c2 * inv % mod + dp[xi][xj]) % mod;
				dp[xi][xj] = (-(2 * c % mod * xj % mod * inv % mod) + mod + dp[xi][xj]) % mod;
				dp[xi][xj] = ((long)xj * xj % mod * temp % mod * inv % mod + dp[xi][xj]) % mod;
				
				i++;
			}
			
			i = temp;
			
			while(i < n * m && key == a.get(i).w) {
				
				int xi = a.get(i).i, xj = a.get(i).j;
				
				dpSum = (dp[xi][xj] + dpSum) % mod;
				r = (xi + r) % mod;
				r2 = (xi * xi + r2) % mod;
				c = (xj + c) % mod;
				c2 = (xj * xj + c2) % mod;
				
				i++;
			}
			
		}
		
		w.println(dp[s.nextInt() - 1][s.nextInt() - 1]);
		
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



   