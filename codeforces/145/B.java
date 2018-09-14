import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

public class cf2 implements Runnable{    
	
	static class pair {
		
		int i, j;
		
		pair(int i, int j) {
			this.i = i; this.j = j;
		}
	}
	
	public void run() {
	    
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int t = 1;
		
		while(t-- > 0) {
			
			long[] a = new long[4 + 1];
			
			for(int i = 1; i <= 4; i++)
				a[i] = s.nextLong();
			
			if(Math.abs(a[3] - a[4]) >= 2) {
				w.println(-1); continue;
			}
			
			if(a[1] < a[3] || a[1] < a[4] || a[2] < a[3] || a[2] < a[4]) {
				w.println(-1); continue;
			}
			
			StringBuffer sb = new StringBuffer("");
			
			if(a[3] == a[4] + 1) {
				
				for(int i = 1; i <= a[1] - a[3]; i++)
					sb.append("4");
				
				for(int i = 1; i <= a[3]; i++)
					sb.append("47");
				
				for(int i = 1; i <= a[2] - a[3]; i++)
					sb.append("7");
			}
			
			else if(a[3] + 1 == a[4]) {
				
				sb.append("74");
				
				for(int i = 1; i <= a[1] - a[4]; i++)
					sb.append("4");
				
				for(int i = 1; i <= a[4] - 2; i++)
					sb.append("74");
				
				for(int i = 1; i <= a[2] - a[4]; i++)
					sb.append("7");
				
				sb.append("74");
			}
			
			else if (a[1] >= a[3] + 1){
				
				for(int i = 1; i <= a[1] - a[3] - 1; i++)
					sb.append("4");
				
				for(int i = 1; i <= a[3]; i++)
					sb.append("47");
				
				for(int i = 1; i <= a[2] - a[3]; i++)
					sb.append("7");
				
				sb.append("4");
			}
			
			else if(a[2] >= a[4] + 1) {
				
				sb.append("74");
				
				for(int i = 1; i <= a[1] - a[4]; i++)
					sb.append("4");
				
				for(int i = 1; i <= a[4] - 1; i++)
					sb.append("74");
				
				for(int i = 1; i <= a[2] - a[4]; i++)
					sb.append("7");
			}
			
			else
				sb = new StringBuffer("-1");
			
			w.println(sb);
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
		new Thread(null, new cf2(),"cf2",1<<26).start();
	}
}   

