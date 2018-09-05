import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

public class cf4 implements Runnable{    
	
	static class pair {
		
		int i, j;
		
		pair(int i, int j) {
			this.i = i; this.j = j;
		}
	}
	
	static class comp implements Comparator<pair> {
		
		public int compare(pair x, pair y) {
			
			if(x.i < y.i)
				return -1;
			
			if(x.i > y.i)
				return 1;
			
			return 0;
		}
	}
	
	static long modPow(long x, long y, long mod) {
		
		if(y == 0)
			return 1;
		
		if(y == 1)
			return x;
		
		long ret = modPow(x, y / 2, mod);
		ret = (ret * ret) % mod;
		
		if(y % 2 == 1)
			ret = (ret * x) % mod;
		
		return ret;
	}
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = s.nextInt();
		
		ArrayList<pair> list = new ArrayList<pair>();
		
		for(int i = 1; i <= n; i++)
			list.add(new pair(s.nextInt(), i));
		
		for(int i = 1; i <= n; i++)
			list.add(new pair(s.nextInt(), i));
		
		Collections.sort(list, new comp());
		
		long m = s.nextLong(), res = 1;
		
		int i = 0;
		
		while(i < 2 * n) {
			
			int key = list.get(i).i, c = 0;
			
			HashSet<Integer> hs = new HashSet<Integer>();
			
			while(i < 2 * n && list.get(i).i == key) {
				hs.add(list.get(i).j); i++; c++;
			}
			
			long y = 0;
			
			for(int j = 1; j <= c; j++) {
				
				long x = j;
				
				while(x % 2 == 0) {
					y++; x /= 2;
				}
				
				res = res * (x % m) % m;
			}
		
			y -= c - hs.size();
			
			res = res * modPow(2 % m, y, m) % m;
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
	new Thread(null, new cf4(),"cf4",1<<26).start();
	}
}   