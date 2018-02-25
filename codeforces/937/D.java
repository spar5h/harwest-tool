import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
/* spar5h */
 
public class yourmomgaylol implements Runnable{    

	static ArrayList<Integer>[] adj;
	static int[][] dp, vis;
	static Stack<Integer> stk;
	
	static void DFS(int x, int turn) {
		
		//System.out.println("/start/ " + x + " " + turn);
		
		vis[x][turn] = 1;
		
		int res = 5;
		
		if(turn == 0) {
			
			res = -1;
			
			for(int i = 0; i < adj[x].size(); i++) {
				
				int y = adj[x].get(i);
				
				if(vis[y][1 - turn] == 1) {
					
					if(dp[y][1 - turn] == 0)
						res = Math.max(res, 0);
					
					if(dp[y][1 - turn] == -1)
						res = 1;
					
					continue;
				}
				
				DFS(y, 1 - turn);
				
				if(dp[y][1 - turn] == 0)
					res = Math.max(res, 0);
				
				if(dp[y][1 - turn] == -1)
					res = 1;
			}
		}
		
		else {
			
			res = 1;
			
			if(adj[x].size() == 0)
				res = -1; 

			for(int i = 0; i < adj[x].size(); i++) {
				
				int y = adj[x].get(i);
				
				if(vis[y][1 - turn] == 1) {
					
					if(dp[y][1 - turn] == 0)
						res = Math.min(res, 0);
					
					if(dp[y][1 - turn] == 1)
						res = -1;
					
					continue;
				}
				
				DFS(y, 1 - turn);
				
				if(dp[y][1 - turn] == 0)
					res = Math.min(res, 0);
				
				if(dp[y][1 - turn] == 1)
					res = -1;
			}
		}
		
		dp[x][turn] = res;
		
		//System.out.println("/end/ " + x + " " + turn + " " + dp[x][turn]);
	}
	
	static void DFS2(int x, int turn) {
		
		//System.out.println("/lol/ " + x + " " + turn + " " + dp[x][turn]);
		
		vis[x][turn] = 1;
		
		if(turn == 0) {
			
			for(int i = 0; i < adj[x].size(); i++) {
				
				int y = adj[x].get(i);
				
				if(vis[y][1 - turn] == 1)
					continue;
				
				if(dp[y][1 - turn] == -1) {
					DFS2(y, 1 - turn); break;
				}
			}
		}
		
		else {
			
			for(int i = 0; i < adj[x].size(); i++) {
				
				int y = adj[x].get(i);
				
				if(vis[y][1 - turn] == 1)
					continue;
				
				if(dp[y][1 - turn] == 1) {
					DFS2(y, 1 - turn); break; 
				}
			}
		}
		
		stk.push(x);
		
	}
	
	
	public void run() {
	    
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = s.nextInt();
		int m = s.nextInt();
		
		adj = new ArrayList[n + 1];
		
		for(int i = 1; i <= n; i++)
			adj[i] = new ArrayList<Integer>();
		
		for(int i = 1; i <= n; i++) {
			
			int x = s.nextInt();
			
			for(int j = 1; j <= x; j++) 
				adj[i].add(s.nextInt());
		}
		
		int v = s.nextInt();
		
		vis = new int[n + 1][2];
		dp = new int[n + 1][2];
		
		DFS(v, 0);
		
		/*
		for(int i = 0; i < 2; i++)
			for(int j = 1; j <= n; j++)
				w.println(i + " " + j + " " + dp[j][i]);
		*/
		
		if(dp[v][0] == 1) {
			
			stk = new Stack<Integer>();
			
			vis = new int[n + 1][2];
			
			DFS2(v, 0);
			
			w.println("Win");
			
			while(!stk.isEmpty()) {
				w.print(stk.peek() + " "); stk.pop();
			}
			
			w.println();
		}
		else if(dp[v][0] == 0) 
			w.println("Draw");
		else
			w.println("Lose");
		
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
		new Thread(null, new yourmomgaylol(),"yourmomgaylol",1<<26).start();
	}
}   

