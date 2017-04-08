import java.util.*;
import java.lang.*;
import java.io.*;

/* Name of the class has to be "Main" only if the class is public. */
 public class codeforces implements Runnable {
    
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
		new Thread(null, new codeforces(),"codeforces",1<<26).start();
	}
	
	public void run() {
	    
	    InputReader s = new InputReader(System.in);
	    PrintWriter w = new PrintWriter(System.out);
	    
	    int n = s.nextInt();
	    int m = s.nextInt();
	    long k = s.nextLong();
	    int x = s.nextInt();
	    int y = s.nextInt();
	    
	    int cycle = (2 * n - 2) * m;
	    
	    if(n == 1)
	    	cycle = 1;
	    
	    long ans = 0;
	    
	    if(x > 1 && x < n)
	    	ans += 2 * (k / cycle);
	    else
	    	ans += k / cycle;
	    
	    long min = k / cycle;
	    long max = 2 * (k / cycle);
	    
	    int K = (int)(k % cycle);
	    
	    
	    if(K > m || max == 0) {
	    	
	    	max++;
	    }	
	    
	    
	    
	    if(K > m * n) {
	    	max++;
	    }
	    
	    if(K >= m * n) {
	    	min++;
	    }
	    
	    outerloop:
	    for(int i = 1; i < n; i++) {
	    	
	    	for(int j = 1; j <= m; j++) {
	    		
	    		K--;
	    		
	    		if(K < 0)
	    			break outerloop;
	    		
	    		if(i == x && j == y)
	    			ans++;
	    		
	    	}
	    }
	    
	    outerloop2:
	    for(int i = n; i > 1; i--) {
	    	
	    	for(int j = 1; j <= m; j++) {
	    		
	    		K--;
	    		
	    		if(K < 0)
	    			break outerloop2;
	    		
	    		if(i == x && j == y)
	    			ans++;
	    		
	    	}
	    }
	    
	    if(n == 1) {
	    	
	    	min = k / m;
	    	max = k / m;
	    	ans = k / m;
	    	
	    	if(k % m > 0)
	    		max++;
	    	
	    	if(k % m >= y)
	    		ans++;
	    	
	    }
	    
	    if(n == 2) {
	    	
	    	min = k / (m * n);
	    	max = k / (m * n);
	    	ans = k / (m * n);
	    	
	    	if(k % (m * n) > 0) {
	    		
	    		max++;
	    	}
	    	
	    	if(k % (m * n) - (m * (x - 1) + y) >= 0)
	    		ans++;
	    }
	    
	    w.println(max);
	    w.println(min);
	    w.println(ans);
	    
	    w.close();
	}
}