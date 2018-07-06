import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
/* spar5h */

public class cf4 implements Runnable{    
	
	static void bfc(int x) {
		
		char[] ch = Integer.toBinaryString(x).toCharArray();
		int n = ch.length;
		
		char[] a = new char[n];
		
		for(int i = 0; i < n; i++)
			a[i] = ch[i];
		
		int res2 = 0;
		
		while(true) {
			
			int count = 0;
			
			for(int i = 0; i < n; i++)
				if(a[i] == '1')
					System.out.print('M');
				else
					System.out.print('F');
			
			System.out.println();
			
			for(int i = 1; i < n; i++) {
				
				if(a[i] == '0' && a[i - 1] == '1') {
					a[i] = '1'; a[i - 1] = '0'; i++; count++;
				}
			}
			
			if(count == 0)
				break;
			
			res2++;
		}
		
		int[] mc = new int[n];
		
		if(ch[0] == '1')
			mc[0] = 1;
		
		int[] wait = new int[n];
		int[] dp = new int[n];
		
		int j = 0, res = 0;
		
		for(int i = 1; i < n; i++) {
			
			mc[i] += mc[i - 1];
			
			if(ch[i] == '1') {
				mc[i]++; continue;
			}
			
			if(dp[j] > 0)
				wait[i] = Math.max(wait[j] - (i - j - 1) + 1, 0);
			
			dp[i] = wait[i] + mc[i];
			res = Math.max(dp[i], res);
		
			j = i;
		}
		
		System.out.println(x + " " + res + " " + res2);
	}
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		/*
		for(int i = 0; i < (1 << 7); i++)
			bfc(i);
		*/
		
		char[] ch = s.next().toCharArray(); int n = ch.length;
		
		int[] mc = new int[n];
		
		if(ch[0] == 'M')
			mc[0] = 1;
		
		int[] wait = new int[n];
		int[] dp = new int[n];
		
		int j = 0, res = 0;
		
		for(int i = 1; i < n; i++) {
			
			mc[i] += mc[i - 1];
			
			if(ch[i] == 'M') {
				mc[i]++; continue;
			}
			
			if(dp[j] > 0)
				wait[i] = Math.max(wait[j] - (i - j - 1) + 1, 0);
			
			dp[i] = wait[i] + mc[i];
			res = Math.max(dp[i], res);
		
			j = i;
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