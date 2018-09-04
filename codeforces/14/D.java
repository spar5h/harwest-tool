import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

public class cf4 implements Runnable{    

	static ArrayList<Integer>[] adj;
	static int[] pathOne;
	
	static boolean dfs(int x, int p, int v) {
		
		if(x == v) {
			pathOne[x] = 1; return true;
		}
		
		for(int i = 0; i < adj[x].size(); i++) {
			
			int y = adj[x].get(i);
			
			if(y == p)
				continue;
			
			if(dfs(y, x, v)) {
				pathOne[x] = 1; return true;
			}	
				
		}
		
		return false;
	}
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = s.nextInt();
		
		adj = new ArrayList[n + 1];
		
		for(int i = 1; i <= n; i++)
			adj[i] = new ArrayList<Integer>();
		
		for(int i = 0; i < n - 1; i++) {
			
			int u = s.nextInt(), v = s.nextInt();
			adj[u].add(v); adj[v].add(u);
		}
		
		long res = 0;
		
		for(int u = 1; u <= n; u++) {
			
			for(int v = u + 1; v <= n; v++) {
				
				pathOne = new int[n + 1];
				
				dfs(u, -1, v);
				
				long v1 = -1;
				
				for(int i = 1; i <= n; i++)
					if(pathOne[i] == 1)
						v1++;
				
				int[] level = new int[n + 1]; Arrays.fill(level, -1);
				int[] level2 = new int[n + 1]; Arrays.fill(level2, -1);
				
				for(int i = 1; i <= n; i++) {
					
					if(pathOne[i] == 1 || level[i] != -1)
						continue;
					
					int count = 0;
					
					for(int j = 0; j < adj[i].size(); j++) 
						if(pathOne[adj[i].get(j)] == 0)
							count++;
					
					if(count >= 2)
						continue;
					
					Queue<Integer> q = new LinkedList<Integer>(); q.add(i); level[i] = 0; 
					
					int x = i;
					
					while(!q.isEmpty()) {
						
						x = q.poll();
						
						for(int j = 0; j < adj[x].size(); j++) {
							
							int y = adj[x].get(j);
							
							if(pathOne[y] == 0 && level[y] == -1) {
								level[y] = level[x] + 1; q.add(y);
							}
						}
					}
					
					q.add(x); level2[x] = 0;
					
					long v2 = 0;
					
					while(!q.isEmpty()) {
						
						x = q.poll(); v2 = level2[x];
						
						for(int j = 0; j < adj[x].size(); j++) {
							
							int y = adj[x].get(j);
							
							if(pathOne[y] == 0 && level2[y] == -1) {
								level2[y] = level2[x] + 1; q.add(y);
							}
						}
					}
					
					res = Math.max(res, v1 * v2);
				}
			}
			
		}
		
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
	new Thread(null, new cf4(),"cf4",1<<26).start();
	}
}   