import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

public class cf1 implements Runnable{  

	public void run() {

		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);		
		
		char[] a = (" " + s.next()).toCharArray();
		char[] b = (" " + s.next()).toCharArray();
		char[] c = (" " + s.next()).toCharArray();
		
		int l = a.length - 1, m = b.length - 1, n = c.length - 1;
		
		int[][] newK = new int[n][26]; newK[0][c[1] - 'A'] = 1; 
		
		for(int end = 1; end < n; end++) {
			
			for(int ch = 0; ch < 26; ch++) {
				
				if(ch + 'A' == c[1])
					newK[end][ch] = 1;
				
				outer:
				for(int start = 1; start <= end; start++) {
					
					if(ch + 'A' != c[(end - start + 1) + 1])
						continue;
					
					for(int i = 0; i < end - start + 1; i++)
						if(c[1 + i] != c[start + i])
							continue outer;
					
					newK[end][ch] = end - start + 2; break;
				}
			}
		}
		
		/*
		for(int i = 0; i < n; i++) {
			
			for(int j = 0; j < 26; j++)
				w.print(newK[i][j] + " ");
			
			w.println();
		}
		*/
		
		int[][][] dp = new int[l + 1][m + 1][n + 1]; 
		
		ArrayList<Integer>[][][] adj = new ArrayList[l + 1][m + 1][n + 1];
		
		for(int i = 1; i <= l; i++)
			for(int j = 1; j <= m; j++)
				for(int k = 0; k <= n; k++)
					adj[i][j][k] = new ArrayList<Integer>();
		
		for(int i = 0; i <= l; i++) {
			for(int j = 0; j <= m; j++) {
				for(int k = 0; k < n; k++) {
					
					if(i + 1 <= l && j + 1 <= m && a[i + 1] == b[j + 1]) {
						dp[i + 1][j + 1][newK[k][a[i + 1] - 'A']] = max(1 + dp[i][j][k], dp[i + 1][j + 1][newK[k][a[i + 1] - 'A']]);
						adj[i + 1][j + 1][newK[k][a[i + 1] - 'A']].add(k);
					}
					
					if(i + 1 <= l)
						dp[i + 1][j][k] = max(dp[i][j][k], dp[i + 1][j][k]);
					
					if(j + 1 <= m)
						dp[i][j + 1][k] = max(dp[i][j][k], dp[i][j + 1][k]);
				}
			}		
		}			
		
		int res = 0;
		
		for(int k = 0; k < n; k++)
			res = max(dp[l][m][k], res);
		
		/*
		for(int i = 0; i <= l; i++) {
			
			for(int j = 0; j <= m; j++) {
				
				w.println(i + " " + j + " // ");
				
				for(int k = 0; k < n; k++)
					w.print(dp[i][j][k] + " ");
				
				w.println();
			}
		}
		
		w.println(res);
		*/
		
		StringBuffer sb = new StringBuffer("");
		
		for(int t = 0; t < n; t++) {
			
			if(res != dp[l][m][t])
				continue;
			
			int i = l, j = m, k = t;
			
			outer2:
			while(res > 0) {
				
				if(a[i] == b[j]) {
					
					for(int x = 0; x < adj[i][j][k].size(); x++) {
						
						int y = adj[i][j][k].get(x);
				
						if(y == n)
							continue;
						
						if(dp[i - 1][j - 1][y] == dp[i][j][k] - 1) {
							sb.append(a[i]); k = y; i--; j--; res--; continue outer2;
						}
					}
				}
		
				if(dp[i - 1][j][k] == dp[i][j][k])
					i--;
				else
					j--;
			}

			break;
		}
		
		sb.reverse();
		
		if(sb.length() == 0)
			w.println(0);
		else
			w.println(sb);
		
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