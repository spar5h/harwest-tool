import java.util.*;
import java.lang.*;
import java.math.*;
import java.text.DecimalFormat;
import java.io.*;

/* spar5h */

public class codeforces implements Runnable
{    
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = Integer.parseInt(s.next());
		int t = Integer.parseInt(s.next());
		
		String str = s.next();
		
		int[] a = new int[n];
		
		for(int i = 0; i < n; i++)
			a[i] = str.charAt(i);
		
		int c = 1;
		
		while(c > 0) {
			
		c--;
		
		int decimal = -1;
		
		for(int i = 0; i < n; i++) {
			
			if(a[i] == '.')
				decimal = i;
		}
		
		if(decimal == -1) {
			w.println(str); continue;
		}
		
		int end = n - 1;
		
		for(int i = decimal + 1; i < n; i++) {
			
			if(a[i] >= '5') {
				end = i - 1; break;
			}	
		}
		
		if(end == n - 1) {
			w.println(str); continue;
		}
		

		for(int i = end; i >= decimal + 1; i--) {
			
			a[i]++;
			t--;
			
			if(a[i] < '5' || t == 0)
				break;
			
			end = i - 1;
		}
		
		if(t == 0) {
			
			if(end == decimal)
				end = decimal - 1;
			
			for(int i = 0; i <= end; i++)
				w.print((char)a[i]);
			
			continue;
		}
		
		if(end == decimal) {
			
			end = decimal - 1;
			
			boolean b = true;
			
			for(int i = end; i >= 0; i--) {
				
				a[i]++;
				
				if(a[i] <= '9') {
					b = false; break;
				}
				
				a[i] = '0';
			}
			
			if(b)
				w.print("1");
		}
		
		for(int i = 0; i <= end; i++)
			w.print((char)a[i]);
		
		}
	
		w.close();
	}
	
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
	   
}