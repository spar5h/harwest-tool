import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
/* spar5h */
 
public class cf3 implements Runnable{    
	
	public void run() {
	    
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
	
		int t = 1;
		
		while(t-- > 0) {
			
			int n = s.nextInt();
			
			long[] S = new long[n + 1];
			
			for(int i = 1; i <= n; i++)
				S[i] = s.nextInt();
			
			long[] c = new long[n + 1];
			
			for(int i = 1; i <= n; i++)
				c[i] = s.nextInt();
			
			long[] dp2 = new long[n + 1];
			Arrays.fill(dp2, Long.MAX_VALUE);
			
			long[] dp3 = new long[n + 1];
			Arrays.fill(dp3, Long.MAX_VALUE);
			
			for(int i = 1; i <= n; i++) {
				
				for(int j = 1; j < i; j++) {
					
					if(S[j] < S[i] && c[j] < dp2[i])
						dp2[i] = c[j];
				}
			}
			
			/*
			for(int i = 1; i <= n; i++)
				w.print(dp2[i] + " ");
			
			w.println();
			*/
			
			for(int i = 1; i <= n; i++) {
				
				for(int j = 1; j < i; j++) {
					
					if(S[j] < S[i] && dp2[j] != Long.MAX_VALUE && dp2[j] + c[j] < dp3[i])
						dp3[i] = dp2[j] + c[j];
				}
			}
			
			/*
			
			for(int i = 1; i <= n; i++)
				w.print(dp3[i] + " ");
			
			w.println();
			
			*/
			
			long ans = Long.MAX_VALUE;
			
			for(int i = 1; i <= n; i++)
				if(dp3[i] != Long.MAX_VALUE)
					ans = Math.min(dp3[i] + c[i], ans);
			
			if(ans == Long.MAX_VALUE)
				w.println(-1);
			else
				w.println(ans);
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
		new Thread(null, new cf3(),"cf3",1<<26).start();
	}
}   

