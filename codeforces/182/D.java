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
		
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		
		String str = s.next(); int n = str.length();
	
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		for(int i = 1; i * i <= n; i++) {
			
			if(n % i != 0)
				continue;
			
			list.add(i);
			
			if(i != n / i)
				list.add(n / i);
		}
		
		for(int i = 0; i < list.size(); i++) {
			
			int k = list.get(i);
			
			String key = str.substring(0, k);
			
			boolean b = true;
			
			for(int j = k; j < n; j += k) {
				
				if(!key.equals(str.substring(j, j + k))) {
					b = false; break;
				}
			}
			
			if(b)
				hm.put(key, hm.get(key) != null ? hm.get(key) + 1 : 1);
		}
		
		str = s.next(); n = str.length();
	
		list = new ArrayList<Integer>();
		
		for(int i = 1; i * i <= n; i++) {
			
			if(n % i != 0)
				continue;
			
			list.add(i);
			
			if(i != n / i)
				list.add(n / i);
		}
		
		for(int i = 0; i < list.size(); i++) {
			
			int k = list.get(i);
			
			String key = str.substring(0, k);
			
			boolean b = true;
			
			for(int j = k; j < n; j += k) {
				
				if(!key.equals(str.substring(j, j + k))) {
					b = false; break;
				}
			}
			
			if(b)
				hm.put(key, hm.get(key) != null ? hm.get(key) + 1 : 1);
		}
		
		int res = 0;
		
		for(int i : hm.values())
			if(i == 2)
				res++;
		
		
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