import java.util.*;
import java.lang.*;
import java.math.*;
import java.awt.List;
import java.io.*;
 
/* spar5h */

public class cf4 implements Runnable{    
	
  public static long gcd (long a, long b) {
    	
    	if(b == 0)
    		return a;
    	
    	return(gcd(b, a % b));
    }

	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int t = 1;
		
		outerloop:
		while(t-- > 0) {
			
			int n = s.nextInt(), m = s.nextInt();
			
			if(m < n - 1) {
				w.println("Impossible"); continue;
			}
			
			if(n <= 5000) {
				
				ArrayList<Integer> u =  new ArrayList<Integer>();
				ArrayList<Integer> v = new ArrayList<Integer>();
				
				outer:
				for(int i = 1; i <= n; i++) {
					
					for(int j = i + 1; j <= n; j++) {
						
						if(m == 0)
							break outer;
						
						if(gcd(i, j) == 1) {
							u.add(i); v.add(j); m--;
						}
							
					}
				}
				
				if(m == 0) {
					
					w.println("Possible");
					
					for(int i = 0; i < u.size(); i++) 
						w.println(u.get(i) + " " + v.get(i));
				}
				
				else
					w.println("Impossible");
				
				continue;
			}
			
			w.println("Possible");
			
			int[] prime = new int[n + 1]; Arrays.fill(prime, 1); prime[0] = 0; prime[1] = 0;
	 		
			for(int i = 2; i <= n; i++) {
				
				if(prime[i] == 0)
					continue;
				
				for(int j = 2; (long)i * j <= n; j++)
					prime[i * j] = 0;
			}
			
			ArrayList<Integer> primes = new ArrayList<Integer>();
			
			for(int i = 2; i <= n; i++)
				if(prime[i] == 1)
					primes.add(i);
			
			for(int i = 2; i <= n; i++) {
				
				if(m == 0) 
					continue outerloop;
				
				w.println(1 + " " + i); m--;
			}
			
			for(int i = 0; i < primes.size(); i++) {
				
				for(int j = i + 1; j < primes.size(); j++) {
					
					if(m == 0)
						continue outerloop;
					
					w.println(primes.get(i) + " " + primes.get(j)); m--;
				}
			}
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