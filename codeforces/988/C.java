import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
/* spar5h */
 
public class cf3 implements Runnable{    
	
	public void run() {
	    
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
	
		int t = 1;

		while(t-- > 0) {
			
			int k = s.nextInt();
			
			HashMap<Integer, Integer>[] hm = new HashMap[k + 1];
			HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
			
			int[][] a = new int[k + 1][];
			int[] sum = new int[k + 1];
			
			for(int i = 1; i <= k; i++) {
				
				hm[i] = new HashMap<Integer, Integer>();
				
				int n = s.nextInt();
				
				a[i] = new int[n + 1];
				
				for(int j = 1; j <= n; j++) {
					a[i][j] = s.nextInt(); sum[i] += a[i][j]; 
				}
			}
			
			for(int i = 1; i <= k; i++) {
				
				for(int j = 1; j <= a[i].length - 1; j++) {
					
					int key = sum[i] - a[i][j];
					
					if(hm[i].get(key) == null)
						hm[i].put(key, 1);
					else
						hm[i].put(key, hm[i].get(key) + 1);
					
					if(map.get(key) == null)
						map.put(key, 1);
					else
						map.put(key, map.get(key) + 1);
				}
			}
			
			int i1 = 0, j1 = 0;
			
			outerloop:
			for(int i = 1; i <= k; i++) {
				
				for(int j = 1; j <= a[i].length - 1; j++) {
					
					int x = sum[i] - a[i][j];
					
					int v1 = map.get(x) != null ? map.get(x) : 0;
					int v2 = hm[i].get(x) != null ? hm[i].get(x) : 0;
					
					if(v1 - v2 > 0) {
						i1 = i; j1 = j; break outerloop;
					}
				}
			}
			
			if(i1 == 0 && j1 == 0) {
				w.println("NO"); continue;
			}	
			
			int i2 = 0, j2 = 0;
			
			outerloop2:
			for(int i = 1; i <= k; i++) {
				
				if(i == i1)
					continue;
				
				for(int j = 1; j <= a[i].length - 1; j++) {
					
					if(sum[i] - a[i][j] == sum[i1] - a[i1][j1]) {
						i2 = i; j2 = j; break outerloop2;
					}
				}
			}
			
			w.println("YES");
			w.println(i1 + " " + j1);
			w.println(i2 + " " + j2);
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

