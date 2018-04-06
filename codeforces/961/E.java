import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
/* spar5h */ 

public class cf2 implements Runnable{    
	
	static int[] tree;
	static int l, r;
	
	static class pair{
	    
	    int i, w;
	    
	    pair(int i, int w) {
	    	
	    	this.i = i;
	    	this.w = w;
	    }
	}
	
	static class comp implements Comparator<pair> {
		
	    public int compare(pair x, pair y) {
	    	
	        if (x.w < y.w)
	            return -1;
	        
	        if (x.w > y.w)
	            return 1;
	        
	        return 0;
	    }
	}
	
	static void update(int n, int nL, int nR) {
		
		if(nL > r || nR < l)
			return;
		
		if(l <= nL && nR <= r) {
			tree[n]++; return;
		}
		
		update(2 * n, nL, (nL + nR) / 2);
		update(2 * n + 1, (nL + nR) / 2 + 1, nR);
		
		tree[n] = tree[2 * n] + tree[2 * n + 1];
	}
	
	static long query(int n, int nL, int nR) {
		
		if(nL > r || nR < l)
			return 0;
		
		if(l <= nL && nR <= r)
			return tree[n];
		
		return query(2 * n, nL, (nL + nR) / 2) + query(2 * n + 1, (nL + nR) / 2 + 1, nR);
	}
	
	public void run() {
	    
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int N = s.nextInt();
		
		ArrayList<pair> list = new ArrayList<pair>();
		
		int[] a = new int[N + 1];
		
		for(int i = 1; i <= N; i++) { 	
			a[i] = Math.min(N, s.nextInt());
			list.add(new pair(i, a[i]));
		}
		
		int n = (int)Math.pow(2, (int)Math.ceil(Math.log(N) / Math.log(2)) + 1) - 1;
		tree = new int[n + 1];
		
		Collections.sort(list, new comp());
		
		long res = 0;
		
		int curr = 1;
		
		for(int i = 0; i < list.size(); i++) {
			
			int wt = list.get(i).w;
			
			while(curr <= wt) {
				l = a[curr]; r = a[curr]; update(1, 1, N); curr++; 
			}
			
			l = list.get(i).i; r = N;
			res += query(1, 1, N);
		}
		
		//w.println(res);
		
		for(int i = 1; i <= N; i++)
			if(a[i] >= i)
				res--;
		
		res /= 2;
		
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
		new Thread(null, new cf2(),"cf2",1<<26).start();
	}
}   

