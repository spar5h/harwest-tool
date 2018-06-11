import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
/* spar5h */
 
public class cf3 implements Runnable{    
	
	final static long mod = (long)1e9 + 7; 
	
	public void run() {
	    
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int a = s.nextInt(), b = s.nextInt(), c = s.nextInt(), d = s.nextInt();
		
		int n = 50, m = 48;
		
		char[][] ch = new char[n][m];
		
		w.println(n + " " + m);
		
		int pos = 0;
		
		for(int i = pos; i < n; i+=2) {
			
			if(b <= 0)
				break;
			
			for(int j = 0; j < m; j+=2) {
				
				if(b > 0) {
					ch[i][j] = 'B'; b--;
				}
				else {
					ch[i][j] = 'A';
				}
				
				ch[i][j + 1] = 'A';	
			}
			
			for(int j = 0; j < m; j++)
				ch[i + 1][j] = 'A';
			
			pos+=2;
		}
		
		for(int i = pos; i < n; i+=2) {
			
			if(c <= 0)
				break;
			
			for(int j = 0; j < m; j+=2) {
				
				if(c > 0) {
					ch[i][j] = 'C'; c--;
				}
				else {
					ch[i][j] = 'A';
				}
				
				ch[i][j + 1] = 'A';	
			}
			
			for(int j = 0; j < m; j++)
				ch[i + 1][j] = 'A';
			
			pos+=2;
		}
		
		for(int i = pos; i < n; i+=2) {
			
			if(d <= 1)
				break;
			
			for(int j = 0; j < m; j+=2) {
				
				if(d > 1) {
					ch[i][j] = 'D'; d--;
				}
				else {
					ch[i][j] = 'A';
				}
				
				ch[i][j + 1] = 'A';	
			}
			
			for(int j = 0; j < m; j++)
				ch[i + 1][j] = 'A';
			
			pos+= 2;
		}
		
		for(int j = 0; j < m; j++)
			ch[pos][j] = 'A';
		
		for(int j = 0; j < m; j++)
			ch[pos + 1][j] = 'D';
		
		a--; pos+=2;
		
	for(int i = pos; i < n; i+=2) {
			
			if(a <= 0)
				break;
			
			for(int j = 0; j < m; j+=2) {
				
				if(a > 0) {
					ch[i][j] = 'A'; a--;
				}
				else {
					ch[i][j] = 'D';
				}
				
				ch[i][j + 1] = 'D';	
			}
			
			for(int j = 0; j < m; j++)
				ch[i + 1][j] = 'D';
			
			pos+= 2;
		}
	
		for(int i = pos; i < n; i++)
			for(int j = 0; j < m; j++)
				ch[i][j] = 'D';
		
		for(int i = 0; i < n; i++)
			w.println(ch[i]);
		
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
		new Thread(null, new cf3(),"cf3",1<<26).start();
	}
}   

