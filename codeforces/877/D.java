import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
/* spar5h */
 
public class codeforces implements Runnable { 

	static class point{
		
		int i, j;
		
		point(int i, int j){
			this.i = i; this.j = j;
		}
	}
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int t = 1;
		
		while(t-- > 0) {
			
			int n = s.nextInt(), m = s.nextInt(), k = s.nextInt();
			
			char[][] c = new char[n + 1][m + 1];
			
			for(int i = 1; i <= n; i++)
				c[i] = (" " + s.next()).toCharArray();
			
			int[][] res = new int[n + 1][m + 1];
			int[][] vis = new int[n + 1][m + 1];
			
			for(int i = 1; i <= n; i++)
				Arrays.fill(res[i], Integer.MAX_VALUE);
			
			int i1 = s.nextInt();
			int j1 = s.nextInt();
			int i2 = s.nextInt();
			int j2 = s.nextInt();
			
			Queue<point> q = new LinkedList<point>();
			q.add(new point(i1, j1)); res[i1][j1] = 0; vis[i1][j1] = 1;
			
			while(!q.isEmpty()) {
				
				int xi = q.peek().i;
				int xj = q.peek().j;
				
				//w.println(xi + "//" + xj);
				
				q.poll();
				
				for(int i = xi + 1; i <= Math.min(xi + k, n); i++) {
					
					if(c[i][xj] == '#' || res[i][xj] <= res[xi][xj])
						break;
					
					if(vis[i][xj] == 0) {
						
						res[i][xj] = res[xi][xj] + 1;
						q.add(new point(i, xj));
						vis[i][xj] = 1;
					}
					
				}
				
				for(int i = xi - 1; i >= Math.max(xi - k, 1); i--) {
					
					if(c[i][xj] == '#' || res[i][xj] <= res[xi][xj])
						break;
					
					if(vis[i][xj] == 0) {
						
						res[i][xj] = res[xi][xj] + 1;
						q.add(new point(i, xj));
						vis[i][xj] = 1;
					}
				}
				
				for(int j = xj + 1; j <= Math.min(xj + k, m); j++) {
					
					if(c[xi][j] == '#' || res[xi][j] <= res[xi][xj])
						break;
					
					if(vis[xi][j] == 0) {
						
						res[xi][j] = res[xi][xj] + 1;
						q.add(new point(xi, j));
						vis[xi][j] = 1;
					}
				
				}
				
				for(int j = xj - 1; j >= Math.max(xj - k, 1); j--) {
					
					if(c[xi][j] == '#' || res[xi][j] <= res[xi][xj])
						break;
					
					if(vis[xi][j] == 0) {
						
						res[xi][j] = res[xi][xj] + 1;
						q.add(new point(xi, j));
						vis[xi][j] = 1;
					}
				}
			}
			
			/*
			for(int i = 1; i <= n; i++) {
				for(int j = 1; j <= m; j++)
					w.print(res[i][j] + " ");
				
				w.println();
			}
			*/
			
			if(vis[i2][j2] == 0) {
				w.println(-1); continue;
			}
			
			w.println(res[i2][j2]);
			
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