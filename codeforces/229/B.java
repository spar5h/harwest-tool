import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
/* spar5h */

public class cf4 implements Runnable{    
	
	static class pair {
		
		int i; long w;
		
		pair(int i, long w) {
			this.i = i; this.w = w;
		}
	}
	
	static class comp implements Comparator<pair> {
		
		public int compare(pair x, pair y) {
			
			if(x.w < y.w)
				return -1;
			
			if(x.w > y.w)
				return 1;
			
			return 0;
		}
	}
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = s.nextInt(), m = s.nextInt();
		
		ArrayList<pair>[] adj = new ArrayList[n + 1];
		
		for(int i = 1; i <= n; i++)
			adj[i] = new ArrayList<pair>();
			
		while(m-- > 0) {
			
			int u = s.nextInt(), v = s.nextInt(); long wt = s.nextInt();
			adj[u].add(new pair(v, wt)); adj[v].add(new pair(u, wt));
		}
		
		HashMap<Long, Long>[] hm = new HashMap[n + 1];
		
		for(int i = 1; i <= n; i++) {
			
			hm[i] = new HashMap<Long, Long>();
			
			Stack<Long> stk = new Stack<Long>();
			
			int k = s.nextInt();
			
			while(k-- > 0)
				stk.push(s.nextLong());
			
			while(!stk.isEmpty()) {
				
				if(hm[i].get(stk.peek() + 1) != null)
					hm[i].put(stk.peek(), hm[i].get(stk.peek() + 1) + 1);
				else
					hm[i].put(stk.peek(), (long)1);
				
				stk.pop();
			}
		}
		
		PriorityQueue<pair> pq = new PriorityQueue<pair>(new comp());
		long[] dist = new long[n + 1]; Arrays.fill(dist, Long.MAX_VALUE);
		
		if(hm[1].get(0) != null) {
			pq.add(new pair(1, hm[1].get(0))); dist[1] = hm[1].get(0);
		}	
		else {
			pq.add(new pair(1, 0)); dist[1] = 0;
		}
		
		int[] vis = new int[n + 1];
		
		while(!pq.isEmpty()) {
			
			int x = pq.peek().i;
			pq.poll();
			
			if(vis[x] == 1)
				continue;
			
			vis[x] = 1; 
			
			long add = hm[x].get(dist[x]) != null ? hm[x].get(dist[x]) : 0;
			
			for(int i = 0; i < adj[x].size(); i++) {
				
				int y = adj[x].get(i).i;
				
				if(vis[y] == 1)
					continue;
				
				long wt = adj[x].get(i).w;
				
				if(dist[y] > dist[x] + add + wt) {
					dist[y] = dist[x] + add + wt; pq.add(new pair(y, dist[y]));
				}
			}
		}
		
		if(dist[n] != Long.MAX_VALUE)
			w.println(dist[n]);
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
	new Thread(null, new cf4(),"cf4",1<<26).start();
	}
}   