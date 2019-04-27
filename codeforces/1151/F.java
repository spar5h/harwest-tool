import java.util.*;
import java.io.*;
import java.math.BigInteger;
import static java.lang.Math.*;

/* spar5h */

public class cf6 implements Runnable {    

	int x;
	long mod = (long)1e9 + 7;
	long[][] mat;
	
	long[][] multiply(long[][] a, long[][] b) {
		
		long[][] ret = new long[x + 1][x + 1];
		
		for(int i = 0; i <= x; i++)
			for(int j = 0; j <= x; j++)
				for(int k = 0; k <= x; k++)
					ret[i][j] = (ret[i][j] + a[i][k] * b[k][j] % mod) % mod;
		
		return ret;
	}
	
	long[][] matExp(int k) {
		
		if(k == 1)
			return mat;
		
		long[][] ret = matExp(k / 2);
		ret = multiply(ret, ret);
		
		if(k % 2 == 1)
			ret = multiply(ret, mat);
		
		return ret;
	}
	
	long modExp(long x, long y) {
		
		if(y == 1)
			return x;
		
		long ret = modExp(x, y / 2);
		
		ret = ret * ret % mod;
		
		if(y % 2 == 1)
			ret = ret * x % mod;
		
		return ret;
	}
	
	public void run() {
	
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = s.nextInt(), k = s.nextInt();
		
		int[] a = new int[n];
		x = 0;
		
		for(int i = 0; i < n; i++) {
			a[i] = s.nextInt();
			x += a[i];
		}	
		
		int[] l = new int[2];
		int[] r = new int[2];
		
		for(int i = 0; i < x; i++)
			r[a[n - 1 - i]]++;
		
		for(int i = x; i < n; i++)
			l[a[n - 1 - i]]++;
		
		mat = new long[x + 1][x + 1];
		
		for(int i = 0; i <= x; i++) {
			
			mat[i][i] = n * (n - 1) / 2;
			
			if(x - i > n - x)
				continue;
			
			if(i - 1 >= 0) {
				mat[i - 1][i] = (n - x - (x - i)) * i; 
				mat[i][i] -= mat[i - 1][i];
			}
			
			if(i + 1 <= x) {
				mat[i + 1][i] = (x - i) * (x - i);
				mat[i][i] -= mat[i + 1][i];
			}
		}
		
		w.println(matExp(k)[x][r[1]] * modExp(modExp(n * (n - 1) / 2, k), mod - 2) % mod);	
		
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
		new Thread(null, new cf6(),"cf6",1<<26).start();
	}
}   