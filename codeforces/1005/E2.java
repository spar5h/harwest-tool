import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
/* spar5h */

public class cf5 implements Runnable{    

	static int[] tree;
	
	static void update(int n, int nL, int nR, int l, int r) {
		
		if(nR < l || nL > r)
			return;
		
		if(l <= nL && nR <= r) {
			tree[n]++; return;
		}
		
		update(2 * n, nL, (nL + nR) / 2, l, r);
		update(2 * n + 1, (nL + nR) / 2 + 1, nR, l, r);
		
		tree[n] = tree[2 * n] + tree[2 * n + 1];
	}
	
	static int query(int n, int nL, int nR, int l, int r) {
		
		if(nR < l || nL > r)
			return 0;
		
		if(l <= nL && nR <= r)
			return tree[n];
		
		return query(2 * n, nL, (nL + nR) / 2, l, r) + query(2 * n + 1, (nL + nR) / 2 + 1, nR, l, r);
	}
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int N = s.nextInt(), m = s.nextInt();
		
		int[] a = new int[N + 1];
		
		for(int i = 1; i <= N; i++)
			a[i] = s.nextInt();
		
		// -2e5 to 2e5 scaled up by 2e5 + 1
		
		int MAX = (int)4e5 + 1, n = (int)Math.pow(2, (int)Math.ceil(Math.log(MAX) / Math.log(2)) + 1) - 1, scale = (int)2e5 + 1;
			
		long res = 0;
		
		tree = new int[n + 1]; int val = 0;
		
		for(int i = 1; i <= N; i++) {
			
			if(a[i] >= m + 1)
				val++;
			else
				val--;
			
			if(val > 0)
				res--;
			
			res -= query(1, 1, MAX, (int)-2e5 + scale, val - 1 + scale);
			
			update(1, 1, MAX, val + scale, val + scale);
		}
		
		tree = new int[n + 1]; val = 0;

		for(int i = 1; i <= N; i++) {
			
			if(a[i] >= m)
				val++;
			else
				val--;
			
			if(val > 0)
				res++;
			
			res += query(1, 1, MAX, (int)-2e5 + scale, val - 1 + scale);
			
			update(1, 1, MAX, val + scale, val + scale);
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
		new Thread(null, new cf5(),"cf5",1<<26).start();
	}
}   