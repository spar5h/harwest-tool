import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

//hm.get((long)0) != hm.get(0)

public class cf5 implements Runnable{    
	
	static class pair {
		
		int i, j;
		
		pair(int i, int j) {
			this.i = i; this.j = j;
		}
	}
	
	static class comp implements Comparator<pair> {
		
		public int compare(pair x, pair y) {
			
			if(x.i < y.i)
				return -1;
			
			if(x.i > y.i)
				return 1;
			
			return 0;
		}
	}
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int t = 1;
		
		while(t-- > 0) {
			
			int n = s.nextInt();
			
			ArrayList<pair> list = new ArrayList<pair>();
			
			for(int i = 0; i < n - 1; i++) {
				
				int u = s.nextInt(), v = s.nextInt();
				list.add(new pair(min(u, v), max(u, v)));
			}	
			
			boolean res = true;
			
			for(int i = 0; i < n - 1; i++)
				if(list.get(i).j != n)
					res = false;
			
			if(!res) {
				w.println("NO"); continue;
			}
			
			Collections.sort(list, new comp());
			
			int i = 0;
			
			int[] dead = new int[n + 1];
			
			ArrayList<pair> edge = new ArrayList<pair>();
			
			while(i < n - 1) {
				
				int key = list.get(i).i, count = 0;
				
				while(i < n - 1 && key == list.get(i).i) {
					count++; i++;
				}
				
				int prev = key; count--;
				dead[prev] = 1;
				
				for(int j = 1; j < key; j++) {
					
					if(count == 0)
						break;
					
					if(dead[j] == 0) {
						edge.add(new pair(prev, j)); dead[j] = 1; prev = j; count--; 
					}
				}
				
				if(count > 0) {
					res = false; break;
				}
				
				edge.add(new pair(prev, n));
			}
			
			if(res) {
				
				w.println("YES");
			
				for(int j = 0; j < edge.size(); j++)
					w.println(edge.get(j).i + " " + edge.get(j).j);
			}
			
			else
				w.println("NO");
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