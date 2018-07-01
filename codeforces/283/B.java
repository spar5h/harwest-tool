import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;

/* spar5h */

public class cf1 implements Runnable{    
	
	static int n, a[], vis[][];
	static long dp[][];
	
	static void DFS(int d, int x) {
		
		vis[d][x] = 1;
		
		int z = x;
		
		if(d == 1)
			z += a[x];
		else
			z -= a[x]; 
		
		if(z <= 0 || z > n) {
			dp[d][x] = a[x]; vis[d][x] = 0; return;
		}	
		
		if(vis[3 - d][z] == 1) {
			dp[d][x] = -1; vis[d][x] = 0; return;
		}
		
		if(dp[3 - d][z] == 0)
			DFS(3 - d, z);
		
		if(dp[3 - d][z] == -1)
			dp[d][x] = -1;
		else
			dp[d][x] = dp[3 - d][z] + a[x];
	
		vis[d][x] = 0;
	}
	
	public void run() {

		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		n = s.nextInt();
		
		a = new int[n + 1];
		
		for(int i = 2; i <= n; i++)
			a[i] = s.nextInt();
		
		dp = new long[3][];
		dp[1] = new long[n + 1]; dp[2] = new long[n + 1];
		
		vis = new int[3][];
		vis[1] = new int[n + 1]; vis[2] = new int[n + 1];
		
		vis[1][1] = 1;
		
		for(int i = 2; i <= n; i++) {
			
			if(vis[2][i] == 0)
				DFS(2, i);
			
			if(dp[2][i] == -1)
				w.println(-1);
			else
				w.println(i - 1 + dp[2][i]);
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
		new Thread(null, new cf1(),"cf1",1<<26).start();
	}
}   