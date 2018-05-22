import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
public class cf5 implements Runnable{    
	
	static int[] tree;
	
	static void update(int n, int nL, int nR, int l, int r) {
		
		if(l > nR || r < nL)
			return;
		
		if(l <= nL && nR <= r) {
			tree[n] = 1; return;
		}
		
		update(2 * n, nL, (nL + nR) / 2, l, r);
		update(2 * n + 1, (nL + nR) / 2 + 1, nR, l, r);
		
		tree[n] = tree[2 * n] | tree[2 * n + 1];
	}
	
	static int query(int n, int nL, int nR, int l, int r) {
		
		if(l > nR || r < nL)
			return 0;
		
		if(l <= nL && nR <= r) 
			return tree[n];
		
		return query(2 * n, nL, (nL + nR) / 2, l, r) | query(2 * n + 1, (nL + nR) / 2 + 1, nR, l, r);
	}
	
	public void run() {
	    
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int N = s.nextInt();
		int k = s.nextInt();
		int d = s.nextInt();
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(0);
		
		for(int i = 0; i < N; i++)
			list.add(s.nextInt());
		
		Collections.sort(list);
		
		/*
		for(int i = 0; i < list.size(); i++)
			w.print(list.get(i) + " ");
		
		w.println();
		*/
		
		int n = (int)Math.pow(2, (int)Math.ceil(Math.log(N + 1) / Math.log(2)) + 1) - 1;
		tree = new int[n + 1];
		
		int[] dp = new int[N + 1];
		dp[0] = 1; update(1, 1, N + 1, 1, 1);
		
		for(int i = 1; i <= N; i++) {
			
			int left = 1, right = i;
			int L = -1, R = i - k;
			
			while(left <= right) {
				
				int mid = (left + right) / 2;
				
				if(list.get(i) - list.get(mid) <= d) {
					L = mid - 1; right = mid - 1;
				}
				else
					left = mid + 1;
			}
			
			if(L < 0 || R < 0 || R < L)
				continue;
			
			dp[i] = query(1, 1, N + 1, L + 1, R + 1);
			
			if(dp[i] == 1) 
				update(1, 1, N + 1, i + 1, i + 1);
			
			//w.println("//" + i + " " + dp[i] + " " + L + " " + R);
		}
		
		/*
		
		for(int i = 0; i <= N; i++)
			w.print(dp[i] + " ");
		
		w.println("/");
		*/
		
		if(dp[N] == 1)
			w.println("YES");
		else
			w.println("NO");
		
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