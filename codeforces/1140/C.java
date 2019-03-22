import java.util.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

public class cf3 implements Runnable {    
	
	class Pair {
		
		long t, b;
		
		Pair(long t, long b) {
			this.t = t; this.b = b;
		}
	}
	
	class Comp implements Comparator<Pair> {
		
		public int compare(Pair x, Pair y) {
			
			if(x.b > y.b)
				return -1;
			
			if(x.b < y.b)
				return 1;
			
			return 0;
		}
	}
	
	class CompPq implements Comparator<Pair> {
		
		public int compare(Pair x, Pair y) {
			
			if(x.t < y.t)
				return -1;
			
			if(x.t > y.t)
				return 1;
			
			return 0;
		}
	}
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = s.nextInt(), k = s.nextInt();
		
		long res = 0;
		
		ArrayList<Pair> a = new ArrayList<Pair>();
		
		for(int i = 0; i < n; i++)
			a.add(new Pair(s.nextLong(), s.nextLong()));
		
		Collections.sort(a, new Comp());
		
		PriorityQueue<Pair> pq = new PriorityQueue<Pair>(new CompPq());
		
		int i = 0;
		long sum = 0;
		
		while(i < n) {
			
			long min = a.get(i).b;
			
			while(i < n && a.get(i).b == min) {
				
				if(pq.size() == k) {
					
					if(pq.peek().t < a.get(i).t) {
						sum -= pq.peek().t;
						sum += a.get(i).t;
						pq.poll();
						pq.add(new Pair(a.get(i).t, a.get(i).b));
					}
					
				}
				else {
					pq.add(new Pair(a.get(i).t, a.get(i).b));
					sum += a.get(i).t;
				}
				
				i++;
			}
			
			res = max(sum * min, res);
		}
		
		w.println(res);
		
		w.close();
	}

	static class InputReader {
		
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;
		private SpaceCharFilter filter;
		
		public InputReader(InputStream stream) {
			this.stream = stream;
		}
		
		public int read() {
			
			if (numChars==-1) 
				throw new InputMismatchException();
			
			if (curChar >= numChars) {
				
				curChar = 0;
				
				try {
					numChars = stream.read(buf);
				}
				catch (IOException e) {
					throw new InputMismatchException();
				}
				
				if(numChars <= 0)				
					return -1;
			}
			
			return buf[curChar++];
		}
		 
		public String nextLine() {
			
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			String str = "";
	        
			try {
	            str = br.readLine();
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	        }
			
	        return str;
		}
		
		public int nextInt() {
		
			int c = read();
			
			while(isSpaceChar(c)) 
				c = read();
			
			int sgn = 1;
			
			if (c == '-') {
				sgn = -1;
				c = read();
			}
			
			int res = 0;
			
			do {
				if(c<'0'||c>'9') 
					throw new InputMismatchException();
				
				res *= 10;
				res += c - '0';
				c = read();
			}
			while (!isSpaceChar(c)); 
			
			return res * sgn;
		}
		
		public long nextLong() {
			
			int c = read();
			
			while (isSpaceChar(c))
				c = read();
			
			int sgn = 1;
			
			if (c == '-') {
				sgn = -1;
				c = read();
			}
			
			long res = 0;
			
			do {
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				
				res *= 10;
				res += c - '0';
				c = read();
			}
			
			while (!isSpaceChar(c));
				return res * sgn;
		}
		
		public double nextDouble() {
			
			int c = read();
			
			while (isSpaceChar(c))
				c = read();
			
			int sgn = 1;
			
			if (c == '-') {
				sgn = -1;
				c = read();
			}
			
			double res = 0;
			
			while (!isSpaceChar(c) && c != '.') {
				
				if (c == 'e' || c == 'E')
					return res * Math.pow(10, nextInt());
				
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				
				res *= 10;
				res += c - '0';
				c = read();
			}
			
			if (c == '.') {
				
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
		
		public String readString() {
			
			int c = read();
			
			while (isSpaceChar(c))
				c = read();
			
			StringBuilder res = new StringBuilder();
			
			do {
				res.appendCodePoint(c);
				c = read();
			} 
			while (!isSpaceChar(c));
			
			return res.toString();
		}
	 
		public boolean isSpaceChar(int c) {
			
			if (filter != null)
				return filter.isSpaceChar(c);
			
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}
	 
		public String next() {
			return readString();
		}
		
		public interface SpaceCharFilter {
			public boolean isSpaceChar(int ch);
		}
	
	}

	public static void main(String args[]) throws Exception {
		new Thread(null, new cf3(),"cf3",1<<26).start();
	}
}   