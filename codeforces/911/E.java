import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;

//solved with editorial as reference

public class lc1 implements Runnable{    
	
	/*
	 * Let f(l, r) be the required array for range of elements of VALUE l to r 
	 * 
	 * If arr[i] is given:
	 * 		f(l, r) = arr[i] + f(l, arr[i] - 1) + f(arr[i] + 1, r)
	 * Else, to maximize lexicographically:
	 * 		f(1, r) = r + f(1, r - 1)
	 * 
	 * This is based on the fact that all elements from [1, x - 1] need to be added
	 * to array 'b' before 'x' can be added. 
	 * */   
	
	//set each element by a recursive function
	static void recur(int i, int l, int r) {
		
		if(l > r || i > n)
			return;
		
		//if given element is not in appropriate range, permutation cannot be formed
		if(i <= k && (a[i] < l || a[i] > r)) {
			b = false; return;
		}
		
		if(i <= k) {
			
			recur(i + 1, l, a[i] - 1);
			recur(i + 1 + a[i] - l, a[i] + 1, r);
			return;
		}
		
		a[i] = r;
		recur(i + 1, l, r - 1);
	}
	
	static int a[], n, k;
	static boolean b;
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		n = s.nextInt();
		k = s.nextInt();
		
		a = new int[n + 1];
		
		for(int i = 1; i <= k; i++)
			a[i] = s.nextInt();
		
		b = true;
		
		recur(1, 1, n);
		
		if(b) {
			
			for(int i = 1; i <= n; i++)
				w.print(a[i] + " ");
			
			w.println();
		}
		
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
		new Thread(null, new lc1(),"lc1",1<<26).start();
	}
}  