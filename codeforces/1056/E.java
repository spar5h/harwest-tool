import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

public class cf1 implements Runnable{    
	
	final static long[] mod = {1000000093, 1000050001};
	final static long p = 26;
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		String str = s.next();
		int n = str.length();
		
		int[] a = new int[n];
		
		for(int i = 0; i < n; i++)
			a[i] = str.charAt(i) - '0';
		
		str = s.next();
		int m = str.length();
		
		int[] b = new int[m];
		
		for(int i = 0; i < m; i++)
			b[i] = str.charAt(i) - 'a';
		
		long[][] pre = new long[2][m];
		pre[0][0] = b[0]; pre[1][0] = b[0];
		
		long[][] pow = new long[2][m];
		pow[0][0] = 1; pow[1][0] = 1;
		
		for(int i = 0; i < 2; i++) {
			
			for(int j = 1; j < m; j++) {
				pre[i][j] = (pre[i][j - 1] * p % mod[i] + b[j]) % mod[i];
				pow[i][j] = pow[i][j - 1] * p % mod[i];
			}
		}
		
		int[] freq = new int[2];
		int[] first = new int[2]; Arrays.fill(first, -1);
		
		for(int i = 0; i < n; i++) {
			
			freq[a[i]]++;
			
			if(first[a[i]] == -1)
				first[a[i]] = i;
		}
		
		int x = freq[0] >= freq[1] ? 0 : 1;
		
		long ans = 0;
		
		for(int i = 1; i <= m / freq[x]; i++) {
			
			int[] len = new int[2];
			len[x] = i;
			
			if((m - len[x] * freq[x]) % freq[x ^ 1] != 0 || (m - len[x] * freq[x]) / freq[x ^ 1] <= 0)
				continue;
			
			len[x ^ 1] = (m - len[x] * freq[x]) / freq[x ^ 1];
			
			long[][] hash = new long[2][2];
			
			//w.println(len[0] + " " + len[1]);
			
			for(int j = 0; j < 2; j++) {
				for(int k = 0; k < 2; k++) {
					
					hash[j][k] = pre[k][first[j] * len[j ^ 1] + len[j] - 1];
					
					if(first[j] * len[j ^ 1] - 1 >= 0)
						hash[j][k] = (hash[j][k] - pre[k][first[j] * len[j ^ 1] - 1] * pow[k][len[j]] % mod[k] + mod[k]) % mod[k];
					
					//w.println(j + " " + k + " " + hash[j][k]);
				}
			}	
			
			if(len[0] == len[1] && hash[0][0] == hash[1][0] && hash[0][1] == hash[1][1])
				continue;
			
			long[] res = new long[2];
			
			for(int j = 0; j < n; j++) {
				for(int k = 0; k < 2; k++) {
					res[k] = (res[k] * pow[k][len[a[j]]] % mod[k] + hash[a[j]][k]) % mod[k];
				}
			}
			
			//w.println(res[0] + " " + pre[0][m - 1] + " // " + res[1] + " " + pre[1][m - 1]);
			
			if(res[0] == pre[0][m - 1] && res[1] == pre[1][m - 1])
				ans++;
		}
		
		w.println(ans);
		
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
		new Thread(null, new cf1(),"cf1",1<<26).start();
	}
}   