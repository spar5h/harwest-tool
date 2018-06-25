import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
/* spar5h */

public class cf5 implements Runnable{    
	
	final static long mod = (long)1e9 + 7; 
	
	static class tuple {
		
		ArrayList<Integer> i;
		long x, y;
		
		public tuple(ArrayList<Integer> i, long x, long y) {
			this.i = i; this.x = x; this.y = y;
		}
	}
	
	public void run() {
	    
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int t = 1;
		
		while(t-- > 0) {
			
			int n = s.nextInt();
			
			int[] x = new int[n];
			int[] y = new int[n];
			
			for(int i = 0; i < n; i++) {
				x[i] = s.nextInt(); y[i] = s.nextInt();
			}
			
			ArrayList<tuple> a = new ArrayList<tuple>();
			
			for(int i = 0; i < Math.min(3, n); i++) {
				
				ArrayList<Integer> j = new ArrayList<Integer>(); j.add(i + 1);
				a.add(new tuple(j, x[i], y[i]));
			}	
			
			int i = 3;
			
			while(a.size() > 1) {
				
				long[] vx = new long[6];
				long[] vy = new long[6];
				
				long min = Long.MAX_VALUE; int index = -1;
				
				for(int j = 0; j < a.size(); j++) {
					
					vx[2 * j] = a.get(j).x + a.get((j + 1) % a.size()).x;
					vy[2 * j] = a.get(j).y + a.get((j + 1) % a.size()).y;
					
					if(vx[2 * j] * vx[2 * j] + vy[2 * j] * vy[2 * j] < min) {
						min = vx[2 * j] * vx[2 * j] + vy[2 * j] * vy[2 * j]; index = 2 * j;
					}
					
					vx[2 * j + 1] = a.get(j).x - a.get((j + 1) % a.size()).x;
					vy[2 * j + 1] = a.get(j).y - a.get((j + 1) % a.size()).y;
					
					if(vx[2 * j + 1] * vx[2 * j + 1] + vy[2 * j + 1] * vy[2 * j + 1] < min) {
						min = vx[2 * j + 1] * vx[2 * j + 1] + vy[2 * j + 1] * vy[2 * j + 1]; index = 2 * j + 1;
					}
					
				}
				
				ArrayList<Integer> l1 = a.get(index / 2).i;
				ArrayList<Integer> l2 = a.get((index / 2 + 1) % a.size()).i;
				
				int size = a.size();
				a.remove(Math.max(index / 2, (index / 2 + 1) % size));
				a.remove(Math.min(index / 2, (index / 2 + 1) % size));
			
				int mul = 1 - 2 * (index % 2);
				
				if(l1.size() < l2.size()) { 	
					
					for(int j = 0; j < l1.size(); j++)
						l2.add(mul * l1.get(j));
					
					a.add(new tuple(l2, mul * vx[index], mul * vy[index]));
				}	
				
				else {
					
					for(int j = 0; j < l2.size(); j++)
						l1.add(mul * l2.get(j));
				
					a.add(new tuple(l1, vx[index], vy[index]));
				}

				if(i < n) {
					
					ArrayList<Integer> j = new ArrayList<Integer>(); j.add(i + 1);
					a.add(new tuple(j, x[i], y[i]));
				}
				
				i++;
			}
			
			ArrayList<Integer> list = a.get(0).i;
			
			int[] res = new int[n];
			
			for(i = 0; i < list.size(); i++) 
				res[Math.abs(list.get(i)) - 1] = (int)Math.signum(list.get(i));
			
			for(i = 0; i < n; i++)
				w.print(res[i] + " ");
			
			w.println();
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