import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

public class cf6{  
	
	static void recur(int i, int d, int m) {
		
		if(i == m) {
			 dp[i][d] = 0; return;
		}
		
		dp[i][d] = Long.MAX_VALUE;
	
		long x = adj[i].get((adj[i].size() - 1) * d).x, y = adj[i].get((adj[i].size() - 1) * d).y;
		
		long x1 = adj[i + 1].get(0).x, y1 = adj[i + 1].get(0).y;
		long x2 = adj[i + 1].get(adj[i + 1].size() - 1).x, y2 = adj[i + 1].get(adj[i + 1].size() - 1).y;
		
		long dist = abs(x1 - x2) + abs(y1 - y2);
		
		if(dp[i + 1][0] == -1)
			recur(i + 1, 0, m);
		
		dp[i][d] = min(abs(x - x2) + abs(y - y2) + dist + dp[i + 1][0], dp[i][d]);
		
		if(dp[i + 1][1] == -1)
			recur(i + 1, 1, m);
		
		dp[i][d] = min(abs(x - x1) + abs(y - y1) + dist + dp[i + 1][1], dp[i][d]);
		
		//System.out.println(x + " " + y + " " + i + " " + d + " " + dp[i][d]);
	}
	
	static class pair {
		
		int x, y;
		
		pair(int x, int y) {
			this.x = x; this.y = y;
		}
	}
	
	static class comp implements Comparator<pair> {
		
		public int compare(pair a, pair b) {
			
			if(a.x < b.x)
				return -1;
			
			if(a.x > b.x)
				return 1;
			
			if(a.y > b.y)
				return -1;
			
			if(a.y < b.y)
				return 1;
			
			return 0;

		}
	}
	
	static int[] val;
	static ArrayList<pair>[] adj;
	static long[][] dp;
	
	public static void main(String[] args) {

		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);		
		
		int n = s.nextInt();
		
		int[] x = new int[n];
		int[] y = new int[n];
		
		TreeSet<Integer> ts = new TreeSet<Integer>();
		
		for(int i = 0; i < n; i++) {
		
			x[i] = s.nextInt();
			y[i] = s.nextInt();
			
			ts.add(max(x[i], y[i]));
		}	
		
		int m = 0;
		
		HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
		hm.put(0, 0);
		
		for(int i : ts) {
			m = m + 1; hm.put(i, m);
		}
		
		adj = new ArrayList[m + 1];
		
		for(int i = 0; i <= m; i++)
			adj[i] = new ArrayList<pair>();
		
		adj[0].add(new pair(0, 0));	
			
		for(int i = 0; i < n; i++) 	
			adj[hm.get(max(x[i], y[i]))].add(new pair(x[i], y[i]));
		
		for(int i = 0; i <= m; i++)
			Collections.sort(adj[i], new comp());
		
		dp = new long[m + 1][2];
		
		for(int i = 0; i <= m; i++)
			for(int j = 0; j < 2; j++)
				dp[i][j] = -1;
		
		recur(0, 0, m);
		
		w.println(dp[0][0]);
		
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



   