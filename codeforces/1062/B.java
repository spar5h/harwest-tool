import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

public class cf2 implements Runnable {   
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int t = 1;
		
		while(t-- > 0) {
			
			int n = s.nextInt();
			
			if(n == 1) {
				w.println(1 + " " + 0); continue;
			}
			
			int N = (int)1e6 + 5;
			
			int[] spf = new int[N];
			
			spf[0] = -1; spf[1] = -1;
			
			for(int i = 2; i < N; i++)
				spf[i] = i;
		
			for(int j = 2; (long)j * 2 < N; j++)
				spf[j * 2] = 2;
			
			for(int j = 3; (long)j * 3 < N; j+=2)
				spf[j * 3] = 3;
			
			int di = 4;
			
			for(int i = 5; i < N; i+=di) {
				
				int dj = di;
				
				for(int j = i; (long)i * j < N; j+=dj) {
					
					if(spf[i * j] > i)
						spf[i * j] = i;
					
					dj = 6 - dj;
				}		
				
				di = 6 - di;
			}
			
			int[] f = new int[N];
			
			int max = 0;
			
			while(n > 1) {
				
				int x = spf[n];
				
				while(n % x == 0) {
					f[x]++; n /= x;
				}
				
				//w.println(x + " " + f[x]);
				
				max = max(f[x], max);
			}
			
			int val = (int)ceil(log(max) / log(2));
			
			boolean check = true;
			
			for(int i = 2; i < N; i++) {
				
				if(f[i] > 0 && f[i] != max) {
					check = false; break;
				}
			}
			
			//w.println(check);
			
			int[] pow2 = new int[N];
			
			for(int i = 1; i < N; i *= 2) {
				pow2[i] = 1;
			}
			
			long x = 1;
			
			for(int i = 2; i < N; i++)
				if(f[i] > 0)
					x *= i;
			
			//w.println(val);
			
			int res = 1 + val;
			
			if(check && pow2[max] == 1)
				res--;
			
			w.println(x + " " + res);
		}

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
		new Thread(null, new cf2(),"cf2",1<<26).start();
	}
}   