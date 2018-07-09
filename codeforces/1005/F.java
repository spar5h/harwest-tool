import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
/* spar5h */

public class cf5 implements Runnable{    

	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = s.nextInt(), m = s.nextInt(), k = s.nextInt();
		
		ArrayList<Integer>[] adj = new ArrayList[n + 1], edge = new ArrayList[n + 1], opt = new ArrayList[n + 1];
		
		for(int i = 1; i <= n; i++) {
			adj[i] = new ArrayList<Integer>(); edge[i] = new ArrayList<Integer>(); opt[i] = new ArrayList<Integer>();
		}
		
		for(int i = 0; i < m; i++) {
			
			int u = s.nextInt(), v = s.nextInt();
			adj[u].add(v); adj[v].add(u);
			edge[u].add(i); edge[v].add(i);
		}
		
		int[] level = new int[n + 1]; Arrays.fill(level, -1); level[1] = 0;
		int[] vis = new int[n + 1];
		
		Queue<Integer> q = new LinkedList<Integer>(); q.add(1);
		
		while(!q.isEmpty()) {
			
			int x = q.poll();
			
			vis[x] = 1;
			
			for(int i = 0; i < adj[x].size(); i++) {
				
				int y = adj[x].get(i), e = edge[x].get(i);
				
				if(level[y] == -1) {
					level[y] = level[x] + 1; q.add(y);
				}
				
				if(vis[y] == 0 && level[y] == level[x] + 1)
					opt[y].add(e);
			}
		}
		
		int[][] res = new int[k][m];
		
		int[] permute = new int[n + 1];
		int count = 0;
		
		outerloop:
		while(count < k) {
			
			for(int i = 2; i <= n; i++)
				res[count][opt[i].get(permute[i])] = 1;
			
			count++;
			
			for(int i = n; i >= 2; i--) {
				
				if(permute[i] + 1 < opt[i].size()) {
					permute[i]++; break;
				}
				else if(i == 2) 
					break outerloop;
				else
					permute[i] = 0;
			}
		}
		
		w.println(count);
		
		for(int i = 0; i < count; i++) {
			
			for(int j = 0; j < m; j++)
				w.print(res[i][j]);
			
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
		new Thread(null, new cf5(),"cf5",1<<26).start();
	}
}   