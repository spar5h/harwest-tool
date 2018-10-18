import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

public class cf4 implements Runnable{    

	static void addMap(HashMap<Integer, Integer> hm, int x) {
		
		if(hm.get(x) != null)
			hm.put(x, hm.get(x) + 1);
		else
			hm.put(x, 1);
	}
	
	static void subMap(HashMap<Integer, Integer> hm, int x) {
		
		if(hm.get(x) != null)
			hm.put(x, hm.get(x) - 1);
		else
			hm.put(x, -1);
	}
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = s.nextInt(), k = s.nextInt();
		
		int[] a = new int[n];
		
		for(int i = 0; i < n; i++)
			a[i] = s.nextInt();
		
		int[] pre = new int[n]; pre[0] = a[0];
		
		for(int i = 1; i < n; i++)
			pre[i] = pre[i - 1] ^ a[i];
		
		HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
		
		for(int i = 0; i < n; i++)
			addMap(hm, pre[i]);
		
		long res = 0;
		
		for(Map.Entry<Integer, Integer> e : hm.entrySet()) {
			//w.println(e.getKey() + " " + e.getValue());
			
			if(e.getKey() == 0)
				res += e.getValue();
			
			res += (long)e.getValue() * (e.getValue() - 1) / 2;
		}
		
		//w.println((long)n * (n + 1) / 2 - res);
		
		int mask = ((1 << k) - 1);
		
		for(int i = n - 1; i >= 0; i--) {
			
			int c = 0;
			
			//w.println(pre[i]);
			
			if(hm.get(pre[i] ^ mask) != null)
				c = hm.get(pre[i] ^ mask);
			
			int add = 0;
			
			if(pre[i] == 0)
				add = 1;
			
			if(c + 1 <= hm.get(pre[i]) - 1 + add) {
				
				subMap(hm, pre[i]);
				
				a[i] ^= mask; pre[i] ^= mask; 
				
				addMap(hm, pre[i]);
			}
			
		}
		
		res = 0;
		
		for(Map.Entry<Integer, Integer> e : hm.entrySet()) {
			//w.println(e.getKey() + " " + e.getValue());
		
			if(e.getKey() == 0)
				res += e.getValue();
			
			res += (long)e.getValue() * (e.getValue() - 1) / 2;
		}
		
		w.println((long)n * (n + 1) / 2 - res);
		
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
		new Thread(null, new cf4(),"cf4",1<<26).start();
	}
}   