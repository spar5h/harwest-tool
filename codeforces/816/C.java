import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;

/* spar5h */

public class codeforces implements Runnable
{    
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = s.nextInt();
		int m = s.nextInt();
		
		int[][] a = new int[n + 1][m + 1];
		int[][] a2 = new int[n + 1][m + 1];
		
		int[] r = new int[n + 1];
		int[] c = new int[m + 1];
		int[] r2 = new int[n + 1];
		int[] c2 = new int[m + 1];
		
		boolean b = true;
		
		int sum = 0;
		int sum2 = 0;
		
		for(int i = 1; i <= n; i++) {
			
			for(int j = 1; j <= m; j++) {
				
				a[i][j] = s.nextInt(); a2[i][j] = a[i][j];
			}	
		}
		
		for(int i = 1; i <= n; i++) {
			
			int min = 501;
			
			for(int j = 1; j <= m; j++) {
				
				if(min > a[i][j])
					min = a[i][j];
			}
			
			for(int j = 1; j <= m; j++)
				a[i][j] -= min;
			
			r[i] = min;
			sum += min;
		}		
		
		outerloop:
		for(int j = 1; j <= m; j++) {
			
			int key = a[1][j];
			
			for(int i = 2; i <= n; i++) {
				
				if(a[i][j] != key) {
					
					b = false;
					break outerloop;
				}
				
			}

			c[j] = key;
			sum += key;
		}
		
		
		for(int j = 1; j <= m; j++) {
			
			int min = 501;
			
			for(int i = 1; i <= n; i++) {
				
				if(min > a2[i][j])
					min = a2[i][j];
			}
			
			for(int i = 1; i <= n; i++)
				a2[i][j] -= min;
			
			c2[j] = min;
			sum2 += min;
		}		
		
		outerloop2:
		for(int i = 1; i <= n; i++) {
			
			int key = a2[i][1];
			
			for(int j = 2; j <= m; j++) {
				
				if(a2[i][j] != key) {
					
					b = false;
					break outerloop2;
				}
				
			}
		
			r2[i] = key; 
			sum2 += key;
		}
		
		if(b) {
			
			
			if(sum < sum2) {
				
				w.println(sum);
				
				for(int i = 1; i <= n; i++) {
					
					for(int k = 0; k < r[i]; k++)
						w.println("row " + i);
				}
				
				for(int j = 1; j <= m; j++) {
					
					for(int k = 0; k < c[j]; k++)
						w.println("col " + j);
				}
				
			}
			
			else {
				
				w.println(sum2);
				
				for(int i = 1; i <= n; i++) {
					
					for(int k = 0; k < r2[i]; k++)
						w.println("row " + i);
				}
				
				for(int j = 1; j <= m; j++) {
					
					for(int k = 0; k < c2[j]; k++)
						w.println("col " + j);
				}
			}
			
			
		}
		
		else {
			
			w.println(-1);
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