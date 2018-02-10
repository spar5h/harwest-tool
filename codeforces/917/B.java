import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;

/* spar5h */

public class noob implements Runnable{    
	
	static int n;
	static ArrayList<Integer>[] adj, val;
	static int dp[][][][];
	
	static void recur(int n1, int n2, int ch, int turn) {
		
		if(dp[n1][n2][ch][turn] != -1)
			return;
		
		if(turn == 0) {
			
			dp[n1][n2][ch][turn] = 0;
			
			for(int i = 0; i < adj[n1].size(); i++) {
				
				int x = adj[n1].get(i);
				int ch2 = val[n1].get(i);
				
				if(ch2 < ch)
					continue;
				
				recur(x, n2, ch2, 1 - turn);
				
				if(dp[x][n2][ch2][1 - turn] == 0)
					dp[n1][n2][ch][turn] = 1;
			}
		}
		
		else {
			
			dp[n1][n2][ch][turn] = 0;
			
			for(int i = 0; i < adj[n2].size(); i++) {
				
				int x = adj[n2].get(i);
				int ch2 = val[n2].get(i);
				
				if(ch2 < ch)
					continue;
				
				recur(n1, x, ch2, 1 - turn);
				
				if(dp[n1][x][ch2][1 - turn] == 0)
					dp[n1][n2][ch][turn] = 1;
			}
		}
		
	}
	
	public void run() {
	    
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
	
		n = s.nextInt();
		int m = s.nextInt();
		
		adj = new ArrayList[n + 1];
		val = new ArrayList[n + 1];
		
		for(int i = 1; i <= n; i++) {
			adj[i] = new ArrayList<Integer>(); val[i] = new ArrayList<Integer>();
		}
		
		while(m-- > 0) {
			
			int n1 = s.nextInt();
			int n2 = s.nextInt();
			int x = s.next().charAt(0) - 'a';
			
			adj[n1].add(n2);
			val[n1].add(x);
		}
		
		dp = new int[n + 1][n + 1][26][2];
		
		for(int i = 1; i <= n; i++)
			for(int j = 1; j <= n; j++)
				for(int k = 0; k < 26; k++)
					for(int l = 0; l < 2; l++)
						dp[i][j][k][l] = -1;
		
		for(int i = 1; i <= n; i++)
			for(int j = 1; j <= n; j++)
				recur(i, j, 0, 0);
		
		for(int i = 1; i <= n; i++) {
			
			for(int j = 1; j <= n; j++) {
				
				if(dp[i][j][0][0] == 1)
					w.print("A");
				else if(dp[i][j][0][0] == 0)
					w.print("B");
			}
			w.println();
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
		new Thread(null, new noob(),"noob",1<<26).start();
	}
}   

