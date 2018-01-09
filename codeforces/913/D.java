import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
/* spar5h */
 
public class lc7 implements Runnable{    
	
	static class tuple {
		
		int i, a; long t;
		
		tuple(int i, int a, long t) {
			this.i = i; this.a = a; this.t = t;
		}
	}
	
	static class comp implements Comparator<tuple> {
		
	    public int compare(tuple x, tuple y) {
	    	
	        if (x.a > y.a)
	            return -1;
	        
	        if (x.a < y.a)
	            return 1;
	        
	        return 0;
	    }
	}
	
	
	static class comp2 implements Comparator<tuple> {
		
	    public int compare(tuple x, tuple y) {
	    	
	        if (x.t > y.t)
	            return -1;
	        
	        if (x.t < y.t)
	            return 1;
	        
	        return 0;
	    }
	}
	
	public void run() {
	    
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);

		int n = s.nextInt();
		long t = s.nextLong();
		
		ArrayList<tuple> list = new ArrayList<tuple>();
		
		for(int i = 1; i <= n; i++)
			list.add(new tuple(i, s.nextInt(), s.nextLong()));
		
		Collections.sort(list, new comp());
		
		PriorityQueue<tuple> pq = new PriorityQueue<tuple>(11, new comp2());
		
		int pos = 0;
		int k = n;
		long sum = 0;
		int res = 0;
		
		while(k >= 0) {
			
			while(pos < n && list.get(pos).a == k) {
				pq.add(list.get(pos)); sum += list.get(pos).t; pos++;
			}
			
			while(pq.size() > k) {
				sum -= pq.peek().t;
				pq.poll();
			}
			
			if(sum <= t && pq.size() == k) {
				res = k; break;
			}
			
			k--;
		}
		
		w.println(res);
		w.println(pq.size());
		
		while(!pq.isEmpty()) {
			w.print(pq.peek().i + " "); pq.poll();
		}
		
		w.println();
		
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

