import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
/* spar5h */
 
public class cf6 implements Runnable{    
	
	final static long mod = (long)1e9 + 7; 
	
	static class tuple{
	    
		int i;
	    long p, c;
	    
	    public tuple(int i, long p, long c) {
	    	this.i= i; this.p = p; this.c = c;
	    }
	}
	
	static class comp implements Comparator<tuple> {
		
	    public int compare(tuple x, tuple y) {
	    	
	        if (x.p < y.p)
	            return -1;
	        
	        if (x.p > y.p)
	            return 1;
	        
	        return 0;
	    }
	}
	
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
		
		int n = s.nextInt(), k = s.nextInt();
		
		long[] p = new long[n + 1];
		long[] c = new long[n + 1];
		
		for(int i = 1; i <= n; i++)
			p[i] = s.nextLong();
		
		for(int i = 1; i <= n; i++)
			c[i] = s.nextLong();
		
		PriorityQueue<Long> pq = new PriorityQueue<Long>();
		long val = 0;
		
		long[] res = new long[n + 1];
		
		ArrayList<tuple> list = new ArrayList<tuple>();
		
		for(int i = 1; i <= n; i++)
			list.add(new tuple(i, p[i], c[i]));
		
		Collections.sort(list, new comp());
		
		for(int i = 0; i < list.size(); i++) {
			
			res[list.get(i).i] = val;

			if(pq.size() == k) {
				if(k != 0 && pq.peek() < list.get(i).c) {
					long temp = pq.poll(); val -= temp; pq.add(list.get(i).c); val += list.get(i).c;
				}	
			}
			else {
				pq.add(list.get(i).c); val += list.get(i).c;
			}
		}
		
		for(int i = 1; i <= n; i++)
			w.print((res[i] + c[i]) + " ");
		
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
		new Thread(null, new cf6(),"cf6",1<<26).start();
	}
}   