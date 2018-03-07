import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
/* spar5h */
 
public class swagdaddy implements Runnable {    

	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = s.nextInt();
		int m = s.nextInt();
		int b = s.nextInt();
		
		long[][] dp = new long[n + 1][b + 1];
		
		for(int i = 1; i <= n; i++) {
			
			String str = " " + s.next();
		
			//sum of lectures upto hour 'i'
			int[] pre = new int[m + 1];
			
			for(int j = 1; j <= m; j++)
				pre[j] = pre[j - 1] + str.charAt(j) - '0';
			
			int[] min = new int[b + 1]; Arrays.fill(min, Integer.MAX_VALUE);
			if(pre[m] <= b) min[pre[m]] = 0;
			
			//minimum number of hours to attend for given number of bunks on the day
			for(int j = 1; j <= m; j++)
				for(int k = j; k <= m; k++)
					if(pre[m] - pre[k] + pre[j - 1] <= b)
						min[pre[m] - pre[k] + pre[j - 1]] = Math.min(min[pre[m] - pre[k] + pre[j - 1]], k - j + 1);
			
			/*
			
			for(int j = 0; j <= b; j++)
				w.print(min[j] + " ");
			
			w.println();
			
			*/
			
			//find minimum hours required to attend corresponding to 'j' bunks using days upto 'i'
			for(int j = 0; j <= b; j++) {
				
				dp[i][j] = dp[i - 1][j] + min[0];
				
				for(int k = 1; k <= pre[m] && j - k >= 0; k++)
					dp[i][j] = Math.min(dp[i - 1][j - k] + min[k], dp[i][j]);
			}
			
		}
		
		long res = Integer.MAX_VALUE;
		
		/*
		for(int i = 1; i <= n; i++) {
			
			for(int j = 0; j <= b; j++)
				w.print(dp[i][j] + " ");
			
			w.println();
		}
		*/
		
		for(int i = 0; i <= b; i++)
			res = Math.min(dp[n][i], res);
		
		w.println(res);
		
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
		new Thread(null, new swagdaddy(),"swagdaddy",1<<26).start();
	}
	   
} 