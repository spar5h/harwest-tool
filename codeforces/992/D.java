import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
/* spar5h */

public class cf4 implements Runnable{    
	
	final static long mod = (long)1e9 + 7; 
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = s.nextInt(); long k = s.nextLong();
		
		long[] a = new long[n + 1];
		
		for(int i = 1; i <= n; i++)
			a[i] = s.nextInt();
		
		long ones = 0;
		
		ArrayList<Long> oneCheck = new ArrayList<Long>();
		ArrayList<Long> list = new ArrayList<Long>();
		
		int pos = 1;
		
		while(pos <= n) {
			
			if(a[pos] != 1) {
				list.add(a[pos]); oneCheck.add((long)0); pos++; continue;
			}
			
			long res = 0;
			
			while(pos <= n && a[pos] == 1) {
				res++; pos++; ones++;
			}
			
			list.add(res); oneCheck.add((long)1);
		}
		
		BigInteger check = BigInteger.valueOf(3 * (long)1e18);
		
		long ans = 0;
		
		for(int i = 0; i < list.size(); i++) {
			
			if(oneCheck.get(i) == 1) {
				
				for(long val = 1; val <= list.get(i); val++) {
					
					BigInteger b = BigInteger.valueOf(1);
					long sum = val;
					
					for(int j = i + 1; j < list.size(); j++) {
						
						if(oneCheck.get(j) == 0) {
							
							b = b.multiply(BigInteger.valueOf(list.get(j)));
							
							if(b.compareTo(check) == 1)
								break;
							
							sum += list.get(j);
							
							long prod = b.longValue();
							
							//w.println(prod + " " + sum + " " + ans);
							
							if(prod % sum == 0 && prod / sum == k)
								ans++;
						}
						
						else {
							
							long prod = b.longValue();
							
							if(prod % k == 0 && prod / k - sum >= 1 && prod / k - sum <= list.get(j))
								ans++;
							
							//w.println(prod + " " + sum + " " + ans);
							
							sum += list.get(j);
						}
					}
				}
			}
			
			else {
				
				BigInteger b = BigInteger.valueOf(1);
				long sum = 0;
				
				for(int j = i; j < list.size(); j++) {
					
					if(oneCheck.get(j) == 0) {
						
						b = b.multiply(BigInteger.valueOf(list.get(j)));
						
						if(b.compareTo(check) == 1)
							break;
						
						sum += list.get(j);
						
						long prod = b.longValue();
						
						//w.println(prod + " " + sum + " " + ans);
						
						if(prod % sum == 0 && prod / sum == k)
							ans++;
					}
					
					else {
						
						long prod = b.longValue();
						
						if(prod % k == 0 && prod / k - sum >= 1 && prod / k - sum <= list.get(j))
							ans++;
						
						//w.println(prod + " " + sum + " " + ans);
						
						sum += list.get(j);
					}
					
				}
			}
		}
		
		if(k == 1)
			ans += ones;
		
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
	new Thread(null, new cf4(),"cf4",1<<26).start();
	}
}   