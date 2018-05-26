import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
/* spar5h */
 
public class cf1 implements Runnable{    
	
	public void run() {

		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = s.nextInt();
		
		long[] a = new long[n + 1];
		
		for(int i = 1; i <= n; i++)
			a[i] = s.nextLong();
		
		for(int i = 1; i <= n; i++)
			a[i] += a[i - 1];
		
		int start = s.nextInt();
		int fin = s.nextInt();
		
		long max = -1;
		int ans = n + 1;
		
		for(int i = 1; i <= n; i++) {
			
			long res = 0;
			
			if(i + fin - start - 1 <= n) {
				
				res += a[i + fin - start - 1];
				res -= a[i - 1];
			}
			
			else {
				
				res += a[n];
				res -= a[i - 1];
				
				int y = fin - start - (n - i + 1);
				
				res += a[y];
			}
			
			//System.out.println(i + " " + res + " " + ((start - 1 - (i - 1) + n) % n + 1));
			
			if(res > max || (res == max && (start - 1 - (i - 1) + n) % n + 1 < ans) ) {
				
				ans = (start - 1 - (i - 1) + n) % n + 1;
				max = res;	
			}

		}
		
		w.println(ans);
		
		/*
		int[] a = new int[n + 1];
		int[] sum = new int[n + 1];
		
		for(int i = 0; i < n; i++)
			a[i] = s.nextInt();
		
		sum[0] = a[0];
		
		for(int i = 1; i < n; i++)
			sum[i] = a[i] + sum[i - 1];
		
		int start = s.nextInt();
		int fin = s.nextInt();
		
		int x = fin - start;
		
		int max = -1;
		int res = -1;
		
		for(int i = 0; i < n; i++) {
			
			int val = 0;
			
			if(i + x - 1 < n) {
				
				if(i > 0)
					val -= sum[i - 1];
				
				val += sum[i + x - 1];
			}
			
			else {
				
				val -= sum[i - 1];
				val += sum[n - 1];
				
				int y = n - i;
				
				val += sum[x - y - 1];
			}
			
			//System.out.println(i + " " + val);
			
			if(val > max) {
				res = (start - 1 - i + n) % n + 1; max = val;
			}
		}
		
		w.println(res);
		*/
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
		new Thread(null, new cf1(),"cf1",1<<26).start();
	}
}   

 