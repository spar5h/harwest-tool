import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

public class cf1{  
	
	public static void main(String[] args) {

		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);		
		
		int t = s.nextInt();
		
		outer:
		while(t-- > 0) {
			
			char[] ch = s.next().toCharArray();
			int n = ch.length;
			
			int[] f = new int[3];
			
			int[] a = new int[n];
			
			for(int i = 0; i < n; i++) {
				
				int x = -1;
				
				if('a' <= ch[i] && ch[i] <= 'z')
					x = 0;
				else if('A' <= ch[i] && ch[i] <= 'Z')
					x = 1;
				else
					x = 2;
				
				a[i] = x; f[x]++;
			}
			
			if(f[0] > 0 && f[1] > 0 && f[2] > 0) {
				w.println(ch); continue;
			}
			
			if(f[0] == 0 && f[1] > 0 && f[2] > 0) {
				
				for(int j = 0; j < n; j++)
					if(f[a[j]] > 1) {
						ch[j] = 'a'; break;
					}
				
				w.println(ch); continue;
			}
			
			if(f[1] == 0 && f[2] > 0 && f[0] > 0) {
				
				for(int j = 0; j < n; j++)
					if(f[a[j]] > 1) {
						ch[j] = 'A'; break;
					}
				
				w.println(ch); continue;
			}
			
			if(f[2] == 0 && f[1] > 0 && f[0] > 0) {
				
				for(int j = 0; j < n; j++)
					if(f[a[j]] > 1) {
						ch[j] = '1'; break;
					}
				
				w.println(ch); continue;
			}
			
			if(f[0] == 0 && f[1] == 0 && f[2] > 0) {
				
				ch[0] = 'a'; ch[1] = 'A';
				w.println(ch); continue;
			}
			
			if(f[1] == 0 && f[2] == 0 && f[0] > 0) {
				
				ch[0] = 'A'; ch[1] = '1';
				w.println(ch); continue;
			}

			if(f[2] == 0 && f[0] == 0 && f[1] > 0) {
	
				ch[0] = '1'; ch[1] = 'a';
				w.println(ch); continue;
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
    
}