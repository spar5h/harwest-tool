import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;

/* Name of the class has to be "Main" only if the class is public. */
public class codeforces implements Runnable
{    
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
		
		long x = s.nextLong();
		long y = s.nextLong();
		
		long l = s.nextLong();
		long r = s.nextLong();
		
		
		long max = (long) (Math.pow(10, 18));
		
		long aVal = 1;
		long bVal = 1;
		
		ArrayList<Long> a = new ArrayList<Long>(); 
		ArrayList<Long> b = new ArrayList<Long>();
		
		while(aVal <= max / x) {
			a.add(aVal); aVal *= x;
		}
		
		a.add(aVal);
		
		while(bVal <= max / y) {
			b.add(bVal); bVal *= y;
		}
		
		b.add(bVal);
		
		ArrayList<Long> unlucky = new ArrayList<Long>();
		
		for(int i = 0; i < a.size(); i++) {
			
			for(int j = 0; j < b.size(); j++) {
				
				long sum = a.get(i) + b.get(j);
				
				if(sum >= l && sum <= r) {
					unlucky.add(sum);
				}	
			}
		}
		
		Collections.sort(unlucky);
		
		long ans = 0;
		
		for(int i = 1; i < unlucky.size(); i++) {
			
			if(unlucky.get(i) - unlucky.get(i - 1) - 1 > ans)
				ans = unlucky.get(i) - unlucky.get(i - 1) - 1;
		}
		
		if(unlucky.size() >= 1) {
			
			if(unlucky.get(0) - l > ans)
				ans = unlucky.get(0) - l;
			
			if(r - unlucky.get(unlucky.size() - 1) > ans)
				ans = r - unlucky.get(unlucky.size() - 1);
			
		}
		
		else {
			
			if(r - l + 1 > ans)
				ans = r - l + 1;
		}
		
		w.println(ans);
		
		w.close();
	}
	   
}