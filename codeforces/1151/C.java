import java.util.*;
import java.io.*;
import java.math.BigInteger;
import static java.lang.Math.*;

/* spar5h */

public class cf4 implements Runnable { 
	
	public void run() {
			
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		long mod = (long)1e9 + 7;
		
		long[] first = new long[60];
		
		first[0] = 1;
		first[1] = 2;
		
		long fact = 1;
		
		for(int i = 2; i < 60; i++) {
			first[i] = first[i - 2] + 2 * fact;
			fact *= 2;
		}
		
		long sub = 0;
		
		long l = s.nextLong() - 1;
		
		long c = 0;
		fact = 1;
		
		for(int i = 0; i < 60; i++) {
			
			if(c + fact > l) {
				
				long n = l - c;
				sub = (sub + (first[i] % mod) * (n % mod) % mod) % mod;;
				sub = (sub + (n % mod) * ((n - 1 + mod) % mod) % mod) % mod;
				break;
			}
			
			sub = (sub + (first[i] % mod) * (fact % mod) % mod) % mod;
			sub = (sub + (fact % mod) * ((fact - 1 + mod) % mod) % mod) % mod;
			
			c += fact;
			fact *= 2;
		}
		
		long r = s.nextLong();
		
		long add = 0;
		
		c = 0;
		fact = 1;
		
		for(int i = 0; i < 60; i++) {
			
			if(c + fact > r) {
				
				long n = r - c;
				add = (add + (first[i] % mod) * (n % mod) % mod) % mod;
				add = (add + (n % mod) * ((n - 1 + mod) % mod) % mod) % mod;
				break;
			}
			
			add = (add + (first[i] % mod) * (fact % mod) % mod) % mod;
			add = (add + (fact % mod) * ((fact - 1 + mod) % mod) % mod) % mod;
			
			c += fact;
			fact *= 2;
		}
		
		w.println((add - sub + mod) % mod);
		
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