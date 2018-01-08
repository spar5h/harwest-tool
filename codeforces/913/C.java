import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
/* spar5h */
 
public class lc7 implements Runnable{    
	
	static ArrayList<Integer>[] adj;
	static int vis[];
	
	static class comp2 implements Comparator<Long> {
		
	    public int compare(Long x, Long y) {
	    	
	        if (x < y)
	            return -1;
	        
	        if (x > y)
	            return 1;
	        
	        return 0;
	    }
	}
	
	public void run() {
	    
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);

		long[] pow2 = new long[31];
		pow2[0] = 1;
		
		for(int i = 1; i <= 30; i++)
			pow2[i] = pow2[i - 1] * 2;
		
		int n = s.nextInt();
	
		long l = s.nextLong();
		
		long[] c = new long[n + 1];
		
		for(int i = 1; i <= n; i++)
			c[i] = s.nextLong();
		
		PriorityQueue<Long> pq = new PriorityQueue<Long>(11);
		
		long ans = Long.MAX_VALUE;
		long res = 0;
		
		while(l > 0) {
			
			pq = new PriorityQueue<Long>(11, new comp2());
			
			int log2 = 0; long temp = l;
			
			while(temp > 0) {
				temp /= 2; log2++;
			}
			
			for(int i = 1; i <= Math.min(log2, n); i++) {
				
				pq.add(pow2[log2 - i] * c[i]);
			}
			
			long min = Long.MAX_VALUE;
			
			for(int i = log2 + 1; i <= n; i++) {
				min = Math.min(c[i], min);
			}
			
			if(min != Long.MAX_VALUE)
				ans = Math.min(ans, res + min);
			
			res += pq.peek();
			l -= pow2[log2 - 1];
		}
		
		ans = Math.min(res, ans);
		
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
		new Thread(null, new lc7(),"lc7",1<<26).start();
	}
}   

