import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
/* spar5h */

public class cf5 implements Runnable{    

	final static long mod = (long)1e9 + 7;
	
	static class tuple {
		
		int i, j, k;
		
		tuple(int i, int j, int k) {
			this.i = i; this.j = j; this.k = k;
		}
	}
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = s.nextInt(), m = s.nextInt();
		
		char[][] a = new char[n][];
		
		for(int i = 0; i < n; i++)
			a[i] = s.next().toCharArray();
		
		ArrayList<tuple> list = new ArrayList<tuple>();
		
		int[][] u = new int[n][m];
		int[][] d = new int[n][m];
		int[][] l = new int[n][m];
		int[][] r = new int[n][m];
		
		for(int i = 1; i < n; i++)
			for(int j = 0; j < m; j++)
				if(a[i - 1][j] == '*')
					u[i][j] = u[i - 1][j] + 1;
				
		for(int i = n - 2; i >= 0; i--)
			for(int j = 0; j < m; j++)
				if(a[i + 1][j] == '*')
					d[i][j] = d[i + 1][j] + 1;
		
		for(int j = 1; j < m; j++)
			for(int i = 0; i < n; i++)
				if(a[i][j - 1] == '*')
					l[i][j] = l[i][j - 1] + 1;
		
		for(int j = m - 2; j >= 0; j--)
			for(int i = 0; i < n; i++)
				if(a[i][j + 1] == '*')
					r[i][j] = r[i][j + 1] + 1;
 		
		for(int i = 0; i < n; i++) {
			
			for(int j = 0; j < m; j++) {
				
				if(a[i][j] == '.')
					continue;
				
				int k = u[i][j];
				k = Math.min(k, d[i][j]);
				k = Math.min(k, l[i][j]);
				k = Math.min(k, r[i][j]);
				
				if(k > 0)
					list.add(new tuple(i, j, k));
			}
		}
		
		int[][] h = new int[n + 1][m + 1];
		int[][] v = new int[n + 1][m + 1];
		
		for(int x = 0; x < list.size(); x++) {
			
			int i = list.get(x).i, j = list.get(x).j, k = list.get(x).k;
			
			h[i][j - k]++; h[i][j + k + 1]--;			
			v[i - k][j]++; v[i + k + 1][j]--;
		}
		
		for(int i = 0; i < n; i++)
			for(int j = 1; j < m; j++)
				h[i][j] += h[i][j - 1];
		
		for(int j = 0; j < m; j++)
			for(int i = 1; i < n; i++)
				v[i][j] += v[i - 1][j];
		
		boolean check = true;
		
		for(int i = 0; i < n; i++)
			for(int j = 0; j < m; j++)
				if(a[i][j] == '*' && h[i][j] == 0 && v[i][j] == 0)
					check = false;
		
		if(check) {
			
			w.println(list.size());
			
			for(int i = 0; i < list.size(); i++)
				w.println((list.get(i).i + 1) + " " + (list.get(i).j + 1) + " " + list.get(i).k);
		}
		
		else
			w.println(-1);
		
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
		new Thread(null, new cf5(),"cf5",1<<26).start();
	}
}   