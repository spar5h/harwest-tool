import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;

/* spar5h */

public class cf1 implements Runnable{    
	
	static class pair {
		
		int i, j;
		
		pair(int i, int j) {
			this.i = i; this.j = j;
		}
	}
	
	public void run() {

		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = s.nextInt(), m = s.nextInt();
		
		char[][] a = new char[n][];
		
		for(int i = 0; i < n; i++)
			a[i] = s.next().toCharArray();
		
		int ei = 0, ej = 0;
		
		for(int i = 0; i < n; i++)
			for(int j = 0; j < m; j++)
				if(a[i][j] == 'E') {
					ei = i; ej = j; }
		
		Queue<pair> q = new LinkedList<pair>(); q.add(new pair(ei, ej));
		int[][] level = new int[n][m]; 
		
		for(int i = 0; i < n; i++)
			for(int j = 0; j < m; j++)
				level[i][j] = -1;
		
		level[ei][ej] = 0;
		
		while(!q.isEmpty()) {
			
			int i = q.peek().i;
			int j = q.peek().j;
			
			q.poll();
			
			if(i - 1 >= 0 && level[i - 1][j] == -1 && a[i - 1][j] != 'T') {
				level[i - 1][j] = level[i][j] + 1; q.add(new pair(i - 1, j));
			}
			
			if(i + 1 < n && level[i + 1][j] == -1 && a[i + 1][j] != 'T') {
				level[i + 1][j] = level[i][j] + 1; q.add(new pair(i + 1, j));
			}
			
			if(j - 1 >= 0 && level[i][j - 1] == -1 && a[i][j - 1] != 'T') {
				level[i][j - 1] = level[i][j] + 1; q.add(new pair(i, j - 1));
			}
			
			if(j + 1 < m && level[i][j + 1] == -1 && a[i][j + 1] != 'T') {
				level[i][j + 1] = level[i][j] + 1; q.add(new pair(i, j + 1));
			}
		}
		
		int si = 0, sj = 0;
		
		for(int i = 0; i < n; i++)
			for(int j = 0; j < m; j++)
				if(a[i][j] == 'S') {
					si = i; sj = j; }
		
		long res = 0;
		
		for(int i = 0; i < n; i++)
			for(int j = 0; j < m; j++)
				if('0' <= a[i][j] && a[i][j] <= '9' && level[i][j] != -1 && level[i][j] <= level[si][sj])
					res += a[i][j] - '0';
					
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
		new Thread(null, new cf1(),"cf1",1<<26).start();
	}
}   