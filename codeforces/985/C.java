import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
/* spar5h */
 
public class cf3 implements Runnable{    
	
	static void mergeSort(int[] a, int l, int r) {
			
		if(l == r)
			return;
		
		int m = (l + r) / 2;		
			
		int[] L = new int[m + 1 - l];
		for(int i = 0; i < m + 1 - l; i++)
			L[i] = a[i];
		
		int[] R = new int[r - m];
		for(int i = 0; i < r - m; i++)
			R[i] = a[m + 1 - l + i];
		
		mergeSort(L, l, m);
		mergeSort(R, m + 1, r);
			
		merge(L, R, a);
	}
	
	static void merge(int[] a, int[] b, int[] c) {
		
		int i = 0, j = 0, k = 0;
		
		while(k < c.length) {
			
			if(i < a.length && j < b.length) {
				
				if(a[i] < b[j]) {
					c[k] = a[i]; i++;
				}
				else {
					c[k] = b[j]; j++;
				}
			}
			
			else if(i < a.length) {
				c[k] = a[i]; i++;
			}
			else {
				c[k] = b[j]; j++;
			}
			
			k++;
		}
	}
	
	public void run() {
	    
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int t = 1;
		
		while(t-- > 0) {
			
			int n = s.nextInt();
			int k = s.nextInt();
			int l = s.nextInt();
			
			int m = n * k;
			
			int[] a = new int[m];
			
			for(int i = 0; i < m; i++)
				a[i] = s.nextInt();
			
			mergeSort(a, 0, m - 1);
			
			int min = a[0];
			
			boolean b1 = false;
			
			for(int i = 0; i < n; i++)
				if(Math.abs(a[i] - min) > l)
					b1 = true;
			
			if(b1) {
				w.println(0); continue;
			}
			
			int[] res = new int[n];
			
			for(int i = 0; i < n; i++)
				res[i] = a[i];
			
			int maxIndex = n - 1;
			
			for(int j = n; j < m - k + 1; j++) {
				
				if(Math.abs(a[j] - min) > l)
					break;
				
				maxIndex = j;
			}
			
			int[] check = new int[m];
			
			int pos = m - 1;
			
			long vol = 0;
			
			for(int i = maxIndex; i > maxIndex - n; i--) {
				
				int val = (int)1e9 + 1;
				
				int count = 0;
				
				if(pos > i) {
					check[i] = 1; count++; val = a[i];
				}
				
				while(count < k) {
					
					if(check[pos] == 0)  {
						count++; val = Math.min(a[pos], val);
					}
					
					pos--;
				}
				
				vol += val;
			}
			
			w.println(vol);
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
		new Thread(null, new cf3(),"cf3",1<<26).start();
	}
}   

