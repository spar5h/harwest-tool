import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;

/* spar5h */

public class noob implements Runnable{    
	
	static int n, a[], vis[], inStack[], max[][], res;
	static ArrayList<Integer>[] adj;
	static boolean b;
	
	static void DFS(int x) {
		
		vis[x] = 1;
		
		for(int i = 0; i < adj[x].size(); i++) {
	
			if(vis[adj[x].get(i)] == 0)
				DFS(adj[x].get(i));
			
			for(int j = 0; j < 26; j++)
				max[x][j] = Math.max(max[x][j], max[adj[x].get(i)][j]);
		}
		
		max[x][a[x]]++;
		
		for(int j = 0; j < 26; j++)
			res = Math.max(max[x][j], res);
	}
	
	static void cycleDFS(int x) {
		
		vis[x] = 1;
		inStack[x] = 1;
		
		for(int i = 0; i < adj[x].size(); i++) {
			
			if(vis[adj[x].get(i)] == 0)
				cycleDFS(adj[x].get(i));
			
			else if(inStack[adj[x].get(i)] == 1) {
				b = true; return;
			}	
		}
		
		inStack[x] = 0;
	}
	
	public void run() {
	    
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
	
		int t = 1;
		
		while(t-- > 0) {
			
			n = s.nextInt();
			int m = s.nextInt();
			
			char[] ch = (" " + s.next()).toCharArray();
			
			a = new int[n + 1];
			
			for(int i = 1; i <= n; i++)
				a[i] = ch[i] - 'a';
			
			adj = new ArrayList[n + 1];
			
			for(int i = 1; i <= n; i++)
				adj[i] = new ArrayList<Integer>();
			
			for(int i = 0; i < m; i++)
				adj[s.nextInt()].add(s.nextInt());
			
			inStack = new int[n + 1];
			vis = new int[n + 1];
			b = false;
			
			for(int i = 1; i <= n; i++)
				if(vis[i] == 0)
					cycleDFS(i);
			
			if(b){
				w.println(-1); continue;
			}
			
			vis = new int[n + 1];
			max = new int[n + 1][26];
			res = 0;
			
			for(int i = 1; i <= n; i++)
				if(vis[i] == 0)
					DFS(i);
			
			w.println(res);
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

