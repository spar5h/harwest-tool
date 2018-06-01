import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
/* spar5h */

public class cf4 implements Runnable{    
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		long[] pow2 = new long[35]; pow2[0] = 1;
		
		for(int i = 1; i <= 34; i++)
			pow2[i] = pow2[i - 1] * 2;
		
		int t = 1;
		
		while(t-- > 0) {
			
			int n = s.nextInt();
			
			ArrayList<Long> list = new ArrayList<Long>();
				
			for(int i = 0; i < n; i++) 
				list.add(s.nextLong());
			
			int res = 1;
			long r1 = list.get(0), r2 = 0, r3 = 0;
			
			Collections.sort(list);
			
			int[][] dp = new int[n][35];
			
			for(int i = 0; i < n; i++) {
				
				for(int j = 0; j < 35; j++) {
					
					dp[i][j] = -1;
					
					long x = list.get(i) - pow2[j];
					
					int l = 0, r = i - 1;
					
					while(l <= r) {
						
						int mid = (l + r) / 2;
						
						if(list.get(mid) == x) {
							//w.println(list.get(i) + " " + list.get(mid) + " " + pow2[j]);
							dp[i][j] = mid; res = 2; r1 = list.get(i); r2 = list.get(mid); break;
						}
						else if(list.get(mid) < x)
							l = mid + 1;
						else 
							r = mid - 1;
					}
				}
			}
			
			if(res == 1) {
				w.println(res);
				w.println(r1); continue;
			}
			
			outerloop:
			for(int i = 0; i < n; i++) {
				
				for(int j = 0; j < 35; j++) {
					
					long x = list.get(i) - pow2[j];
					
					int l = 0, r = i - 1;
					
					while(l <= r) {
						
						int mid = (l + r) / 2;
						
						if(list.get(mid) == x) {
							
							if(dp[mid][j] != -1) {
								res = 3; r1 = list.get(dp[mid][j]); r2 = list.get(mid); r3 = list.get(i); break outerloop;
							}
							else
								break;
						}
						else if(list.get(mid) < x)
							l = mid + 1;
						else 
							r = mid - 1;
					}
				}
			}
			
			w.println(res);
			
			if(res == 2) 
				w.println(r1 + " " + r2);
			else 
				w.println(r1 + " " + r2 + " " + r3);
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
	new Thread(null, new cf4(),"cf4",1<<26).start();
	}
}   