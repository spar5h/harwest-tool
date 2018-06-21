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
		
		int n = s.nextInt(), m = s.nextInt();
		
		int[] a = new int[n + 1];
		
		for(int i = 1; i <= n; i++)
			a[i] = s.nextInt();
		
		ArrayList<Integer>[] adj = new ArrayList[m];
		
		for(int i = 0; i < m; i++)
			adj[i] = new ArrayList<Integer>();
		
		int[] c = new int[m];
		
		for(int i = 1; i <= n; i++) {
			adj[a[i] % m].add(i); c[a[i] % m]++;
		}	
		
		HashMap<Integer, Integer>[] hm = new HashMap[m];
		
		int j = 0;
		
		long res = 0;
		
		for(int i = 0; i < m; i++) {
			
			hm[i] = new HashMap<Integer, Integer>();
			
			while(c[i] > n / m) {
				
				if(j <= i || c[j % m] >= n / m) {
					j++; continue;
				}
				
				long v = (j - i) % m;
				
				if(c[i] - n / m >= n / m - c[j % m]) {
					res += v * (n / m - c[j % m]); hm[i].put((int)v, (n / m - c[j % m])); c[i] -= (n / m - c[j % m]); c[j % m] = n / m;  
				}
				else {
					res += v *(c[i] - n / m); hm[i].put((int)v, (c[i] - n / m)); c[j % m] += c[i] - n / m; c[i] = n / m; break;
				}
				
				j++;
			}
		}
		
		
		
		for(int i = 0; i < m; i++) {
			
			j = 0;
			
			for(HashMap.Entry<Integer, Integer> entry: hm[i].entrySet()) {
				
				for(int k = 0; k < entry.getValue(); k++)
					a[adj[i].get(j + k)] += entry.getKey();
				
				j += entry.getValue();
			}
		}
		
		w.println(res);
		
		for(int i = 1; i <= n; i++)
			w.print(a[i] + " ");
		
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
	new Thread(null, new cf4(),"cf4",1<<26).start();
	}
}   