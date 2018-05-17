import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
/* spar5h */ 

public class cf2 implements Runnable{    

	static class pair {
		
		int i, w;
		
		public pair(int i, int w) {
			this.i = i; this.w = w;
		}
	}
	
	static class comp1 implements Comparator<pair> {
		
		public int compare(pair x, pair y) {
			
			if(x.w < y.w)
				return -1;
			
			if(x.w > y.w)
				return 1;
			
			return 0;
		}
	}
	
	static class comp2 implements Comparator<pair> {
		
		public int compare(pair x, pair y) {
			
			if(x.w > y.w)
				return -1;
			
			if(x.w < y.w)
				return 1;
			
			return 0;
		}
	}
	
	
	public void run() {
	    
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		PriorityQueue<pair> pq1 = new PriorityQueue<pair>(11, new comp1());
		PriorityQueue<pair> pq2 = new PriorityQueue<pair>(11, new comp2());
		
		int n = s.nextInt();
		
		int[] intro = new int[n + 1];
		int[] extro = new int[n + 1];
		
		for(int i = 1; i <= n; i++) {
			
			int x = s.nextInt();
			
			intro[i] = x;
			extro[i] = x;
			
			pq1.add(new pair(i, intro[i]));
		}
	
		int[] res = new int[2 * n + 1];
		
		String str = s.next();
		
		for(int i = 1; i <= 2 * n; i++) {
		
			
			if(str.charAt(i - 1) == '0') {
				
				res[i] = pq1.peek().i;
				
				pq2.add(new pair(res[i], extro[res[i]]));
				
				pq1.poll();
			}
			
			else {
				
			
				res[i] = pq2.peek().i;
				
				pq2.poll();
			}
			
		}
		
		for(int i = 1; i <= 2 * n; i++) {
			
			w.print(res[i] + " ");
		}
		
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
		new Thread(null, new cf2(),"cf2",1<<26).start();
	}
}   

