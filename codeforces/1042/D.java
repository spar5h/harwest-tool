import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

public class cf4{  
	
	static void build(int n, int nL, int nR) {
		
		if(nL == nR) {
			tree[n] = 1; return;
		}
		
		build(2 * n + 1, nL, (nL + nR) / 2);
		build(2 * n + 2, (nL + nR) / 2 + 1, nR);
		
		tree[n] = tree[2 * n + 1] + tree[2 * n + 2];
	}
	
	static void update(int n, int nL, int nR, int l, int r) {
		
		if(r < nL || nR < l)
			return;
		
		if(l <= nL && nR <= r) {
			tree[n] = 0; return;
		}
		
		update(2 * n + 1, nL, (nL + nR) / 2, l, r);
		update(2 * n + 2, (nL + nR) / 2 + 1, nR, l, r);
		
		tree[n] = tree[2 * n + 1] + tree[2 * n + 2];
	}
	
	static int query(int n, int nL, int nR, int l, int r) {
		
		if(r < nL || nR < l)
			return 0;
		
		if(l <= nL && nR <= r)
			return tree[n];
		
		return query(2 * n + 1, nL, (nL + nR) / 2, l, r) + query(2 * n + 2, (nL + nR) / 2 + 1, nR, l, r);
	}
	
	static int[] tree;
	
	public static void main(String[] args) {

		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);		

		int N = s.nextInt();
		long t = s.nextLong();
		
		long[] a = new long[N];
		
		for(int i = 0; i < N; i++)
			a[i] = s.nextLong();
		
		long[] pre = new long[N]; pre[0] = a[0];
		
		for(int i = 1; i < N; i++)
			pre[i] = a[i] + pre[i - 1];
		
		ArrayList<pair> list = new ArrayList<pair>();
		
		for(int i = 0; i < N; i++)
			list.add(new pair(i, pre[i]));
		
		Collections.sort(list, new comp());
		
		int[] map = new int[N];
		
		for(int i = 0; i < N; i++)
			map[list.get(i).i] = i;
		
		int n = (int)Math.pow(2, (int)Math.ceil(Math.log(N) / Math.log(2)) + 1);
		tree = new int[n];
		
		build(0, 0, N - 1);
		
		long res = 0;
		
		for(int i = N - 1; i >= 0; i--) {
			
			update(0, 0, N - 1, map[i], map[i]);
			
			if(pre[i] < t)
				res++;
			
			int lim = -1;
			
			int l = 0, r = N - 1;
			
			while(l <= r) {
				
				int mid = (l + r) / 2;
				
				if(pre[i] - list.get(mid).w < t) {
					lim = mid; r = mid - 1;
				}
				else
					l = mid + 1;
			}
			
			if(lim != -1)
				res += query(0, 0, N - 1, lim, N - 1);
 		}
		
		w.println(res);
		
		w.close();
	}
	
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