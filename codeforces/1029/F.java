import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
/* spar5h */
 
public class cf6 implements Runnable{    
	
	public void run() {
	    
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		long a = s.nextLong(), b = s.nextLong();
		
		ArrayList<Long> aFact = new ArrayList<Long>();
		ArrayList<Long> bFact = new ArrayList<Long>();
		
		ArrayList<Long> can = new ArrayList<Long>();
		
		for(long i = 1; i * i <= a; i++) 	
			if(a % i == 0)
				aFact.add(i);
		
		for(long i = 1; i * i <= b; i++) 	
			if(b % i == 0)
				bFact.add(i);
		
		for(long i = 1; i * i <= a + b; i++)
			if((a + b) % i == 0)
				can.add(i);
		
		long res = 2 * (a + b + 1);
		
		for(int i = 0; i < can.size(); i++) {
			
			long breadth = can.get(i);
			long length = (a + b) / can.get(i);
			
			int l = 0, r = aFact.size() - 1;
			long aBreadth = -1;
			
			while(l <= r) {
				
				int mid = (l + r) / 2;
				
				if(aFact.get(mid) <= breadth) {
					aBreadth = aFact.get(mid); l = mid + 1;
				}
				else
					r = mid - 1;
			}
			
			if(aBreadth != -1) {
				
				long aLength = a / aBreadth;
				
				if(aLength <= length) {
					res = Math.min(2 * (length + breadth), res);
				}
			}
			
			l = 0; r = bFact.size() - 1;
			long bBreadth = -1;
			
			while(l <= r) {
				
				int mid = (l + r) / 2;
				
				if(bFact.get(mid) <= breadth) {
					bBreadth = bFact.get(mid); l = mid + 1;
				}
				else
					r = mid - 1;
			}
			
			if(bBreadth != -1) {
				
				long bLength = b / bBreadth;
				
				if(bLength <= length) {
					res = Math.min(2 * (length + breadth), res);
				}
			}
		}
		
		w.println(res);
		
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