import java.util.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

//stupidly thought i, i + 2, i + 4 had to be different but only i & i + 2 have to be

public class cf5 implements Runnable {    
	
	final static long mod = 998244353;
	
	static class Pair {
		
		int l, r;
		
		Pair(int l, int r) {
			this.l = l; this.r = r;
		}
	}
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int t = 1;
		
		while(t-- > 0) {
			
			int n = s.nextInt(), k = s.nextInt();
			
			int[] a = new int[n];
			
			for(int i = 0; i < n; i++)
				a[i] = s.nextInt();
			
			boolean valid = true;
			
			for(int i = 0; i + 2 < n; i++)
				if(a[i] != -1 && a[i] == a[i + 2])
					valid = false;
			
			if(!valid) {
				w.println(0); continue;
			}
			
			ArrayList<Pair> list = new ArrayList<Pair>();
			
			int i = 0;
			long[] dp = new long[2];
			dp[0] = 1;
			long[] dp2 = new long[2];
			
			while(i < n) {
				
				while(i < n && a[i] != -1)
					i+=2;
				
				int l = i;
				
				while(i < n && a[i] == -1)
					i+=2;
				
				int r = i - 2;
				
				if(l > r)
					continue;
				
				int left = -1;
				
				if(l - 2 >= 0)
					left = a[l - 2];
				
				int right = -1;
				
				if(r + 2 < n)
					right = a[r + 2];
				
				if(left != -1 && left == right) {
					long temp = dp[0]; dp[0] = dp[1]; dp[1] = temp;
				}
				
				for(int j = l; j <= r; j += 2) {
					
					if(j - 2 >= 0)
						dp2[0] = (dp[0] * (k - 2) % mod + dp[1] * (k - 1) % mod) % mod;
					else
						dp2[0] = (k - 1);
					
					dp2[1] = dp[0];
					
					dp[0] = dp2[0];
					dp[1] = dp2[1];
				}
				
				if(right == -1) 
					dp[0] = (dp[0] + dp[1]) % mod;
				
				dp[1] = 0;
			}
			
			long m1 = dp[0];
			
			i = 1;
			dp = new long[2];
			dp[0] = 1;
			dp2 = new long[2];
			
			while(i < n) {
				
				while(i < n && a[i] != -1)
					i+=2;
				
				int l = i;
				
				while(i < n && a[i] == -1)
					i+=2;
				
				int r = i - 2;
				
				if(l > r)
					continue;
				
				int left = -1;
				
				if(l - 2 >= 0)
					left = a[l - 2];
				
				int right = -1;
				
				if(r + 2 < n)
					right = a[r + 2];
				
				if(left != -1 && left == right) {
					long temp = dp[0]; dp[0] = dp[1]; dp[1] = temp;
				}
				
				for(int j = l; j <= r; j += 2) {
					
					if(j - 2 >= 0)
						dp2[0] = (dp[0] * (k - 2) % mod + dp[1] * (k - 1) % mod) % mod;
					else
						dp2[0] = (k - 1);
					
					dp2[1] = dp[0];
					
					dp[0] = dp2[0];
					dp[1] = dp2[1];
				}
				
				if(right == -1) 
					dp[0] = (dp[0] + dp[1]) % mod;
				
				dp[1] = 0;
			}
			
			long m2 = dp[0];
			
			w.println(m1 * m2 % mod);
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
		new Thread(null, new cf5(),"cf5",1<<26).start();
	}
}   