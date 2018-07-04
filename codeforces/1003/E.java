import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
/* spar5h */

public class cf5 implements Runnable{    
	
	static class pair {
		
		int i, j;
		
		public pair(int i, int j) {
			this.i = i; this.j = j;
		}
	}
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int t = 1;
		
		while(t-- > 0) {
			
			ArrayList<pair> list = new ArrayList<pair>();
			
			int n = s.nextInt(), d = s.nextInt() + 1, k = s.nextInt();
			
			if(d > n) {
				w.println("NO"); continue;
			}
			
			if(n == 2) {
				
				if(d == 2) {
					w.println("YES"); w.println(1 + " " + 2);
				}
				else
					w.println("NO");
				
				continue;
			}
			
			if(k == 1) {
				w.println("NO"); continue;
			}
			
			for(int i = 1; i <= d - 1; i++)
				list.add(new pair(i, i + 1));
			
			int[] level = new int[n + 1];
			
			int x = d + 1;
			
			for(int i = 2; i <= d - 1; i++) {
				
				level[i] = Math.max(i, d - i + 1);
				
				Queue<Integer> q = new LinkedList<Integer>();
				
				for(int j = 3; j <= k && x <= n; j++) {
					list.add(new pair(i, x)); level[x] = level[i] + 1; q.add(x); x++;
				}
				
				if(x > n)
					break;
				
				while(!q.isEmpty()) {
					
					if(level[q.peek()] == d)
						break;
					
					int y = q.poll();
					
					for(int j = 2; j <= k && x <= n; j++) {
						list.add(new pair(y, x)); level[x] = level[y] + 1; q.add(x); x++;
					}
					
					if(x > n)
						break;
				}
				
				if(x > n)
					break;
			}
			
			if(list.size() < n - 1) {
				w.println("NO"); continue;
			}	
			
			w.println("YES");
			
			for(int i = 0; i < n - 1; i++)
				w.println(list.get(i).i + " " + list.get(i).j);
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
		new Thread(null, new cf5(),"cf5",1<<26).start();
	}
}   