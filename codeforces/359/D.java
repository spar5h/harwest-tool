import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
/* spar5h */
 
public class SparseTable implements Runnable{    
	
	static int gcd (int a, int b) {
    	
    	if(b == 0)
    		return a;
    	
    	return(gcd(b, a % b));
	}
	
	public void run() {
	    
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int[] pow2 = new int[33]; pow2[0] = 1;
		
		for(int i = 1; i <= 32; i++)
			pow2[i] = 2 * pow2[i - 1];
		
		int[] log2 = new int[(int)1e6 + 1]; 
		
		for(int i = 1; i <= 23; i++) 	
			for(int j = pow2[i]; j <= Math.min((int)1e6, pow2[i + 1] - 1); j++)
				log2[j] = i;
		
		int N = s.nextInt();
		
		int[] a = new int[N + 1];
		
		for(int i = 1; i <= N; i++)
			a[i] = s.nextInt();
		
		int[][] sparse = new int[N + 1][log2[N] + 1];
		
		for(int i = 1; i <= N; i++)
			sparse[i][0] = a[i];
		
		for(int i = 1; i <= log2[N]; i++)
			for(int j = 1; j + pow2[i - 1] <= N; j++)
				sparse[j][i] = gcd(sparse[j][i - 1], sparse[j + pow2[i - 1]][i - 1]);
		
		int[] res = new int[N + 1]; int max = 0;
		
		for(int i = 1; i <= N; i++) {
			
			int limL = i, limR = i;
			
			int l = 1, r = i;
			
			while(l <= r) {
				
				int mid = (l + r) / 2;
				
				if(gcd(sparse[mid][log2[i - mid + 1]], sparse[i - pow2[log2[i - mid + 1]] + 1][log2[i - mid + 1]]) == a[i]) {
					limL = mid; r = mid - 1;
				}
				else
					l = mid + 1;
			}
			
			l = i; r = N;
			
			while(l <= r) {
				
				int mid = (l + r) / 2;
				
				if(gcd(sparse[i][log2[mid - i + 1]], sparse[mid - pow2[log2[mid - i + 1]] + 1][log2[mid - i + 1]]) == a[i]) {
					limR = mid; l = mid + 1;
				}
				else
					r = mid - 1;
			}
			
			res[limL] = Math.max(res[limL], limR - limL);
			max = Math.max(max, limR - limL);
		}
		
		int count = 0;
		
		for(int i = 1; i <= N; i++)
			if(res[i] == max)
				count++;
		
		w.println(count + " " + max);
		
		for(int i = 1; i <= N; i++)
			if(res[i] == max)
				w.print(i + " ");
		
		w.println();
		
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
		new Thread(null, new SparseTable(),"SparseTable",1<<26).start();
	}
}   