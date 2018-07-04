import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
/* spar5h */

public class cf4 implements Runnable{    
	
	static class tuple {
		
		int i, x, y;
		
		public tuple(int i, int x, int y) {
			this.i = i; this.x = x; this.y = y;
		}
	}
	
	static class comp implements Comparator<tuple> {
		
		public int compare(tuple a, tuple b) {
			
			if(a.x < b.x)
				return -1;
			
			if(a.x > b.x)
				return 1;
			
			if(a.y < b.y)
				return -1;
			
			if(a.y > b.y)
				return 1;
				
			return 0;
		}
	}
	
	static int[][] rank;
	
	//longest common prefix
	static int lcp(int x, int y, int n, int lim) {
		
		if(x == y)
			return n - x;
		
		int res = 0;
		
		for(int i = lim - 1; i >= 0 && x < n && y < n; i--) {
			
			if(rank[x][i] == rank[y][i]) {
				x += 1 << i; y += 1 << i; res += 1 << i;
			}
		}
			
		return res;
	}
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		char[] c = s.next().toCharArray();
		
		int n = c.length;
		
		int lim = (int)Math.ceil(Math.log(n) / Math.log(2)) + 1;
		
		int[] pow2 = new int[lim + 1]; pow2[0] = 1;
		
		for(int i = 1; i <= lim; i++)
			pow2[i] = pow2[i - 1] * 2;
		
		//obtain ranks & suffix array
		rank = new int[n][lim];
		
		ArrayList<tuple> sa = new ArrayList<tuple>();
		
		for(int i = 0; i < n; i++) 
			sa.add(new tuple(i, c[i] - 'a', 0));
		
		Collections.sort(sa, new comp());
		
		int r = 0, pos = 0;
		
		while(pos < n) {
			
			int x = sa.get(pos).x;
			int y = sa.get(pos).y;
			
			while(pos < n && x == sa.get(pos).x && y == sa.get(pos).y) {
				rank[sa.get(pos).i][0] = r; pos++;
			}
			
			r++;
		}
		
		for(int i = 1; i < lim; i++) {
			
			sa = new ArrayList<tuple>();
			
			for(int j = 0; j < n; j++) 
				sa.add(new tuple(j, rank[j][i - 1], (j + pow2[i - 1] < n) ? rank[j + pow2[i - 1]][i - 1] : -1));
			
			Collections.sort(sa, new comp());
			
			r = 0; pos = 0;
			
			while(pos < n) {
				
				int x = sa.get(pos).x;
				int y = sa.get(pos).y;
				
				while(pos < n && x == sa.get(pos).x && y == sa.get(pos).y) {
					rank[sa.get(pos).i][i] = r; pos++;
				}
				
				r++;
			}
		}
		
		//solution
		
		boolean valid[] = new boolean[n];
		
		for(int i = 0; i < n; i++)
			if(lcp(i, 0, n, lim) == n - i)
				valid[i] = true;
		
		int[] res = new int[n];
		
		for(int i = 0; i < n; i++) {
			
			if(!valid[sa.get(i).i])
				continue;
			
			int L = i, R = n - 1;
			
			while(L <= R) {
				
				int mid = (L + R) / 2;
				
				if(lcp(sa.get(i).i, sa.get(mid).i, n, lim) == n - sa.get(i).i) {
					res[sa.get(i).i] = mid - i + 1; L = mid + 1;
				}
				else
					R = mid - 1;
			}
		}
		
		int count = 0;
		
		for(int i = 0; i < n; i++)
			if(valid[i])
				count++;
		
		w.println(count);
		
		for(int i = n - 1; i >= 0; i--)
			if(valid[i])
				w.println(n - i + " " + res[i]);
		
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