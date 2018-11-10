import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

public class cf1 implements Runnable{    
	
	static void addMap(HashMap<Integer, Integer> hm, int x) {
		
		if(hm.get(x) == null)
			hm.put(x, 1);
		else
			hm.put(x, hm.get(x) + 1);
	}
	
	static int[] a, pow2;
	static long[] invCount, sameCount;
	
	static long recur(int[] a, int k) {
		
		if(k == 0)
			return 0;
	
		int n = pow2[k];
		
		HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
		
		for(int i = 0; i < n; i++)
			addMap(hm, a[i]);
		
		for(Map.Entry<Integer, Integer> e : hm.entrySet())
			sameCount[k] += (long)e.getValue() * (e.getValue() - 1) / 2;

		int[] left = new int[n / 2];
		
		for(int i = 0; i < n / 2; i++)
			left[i] = a[i];
		
		int[] right = new int[n / 2];
		
		for(int i = 0; i < n / 2; i++)
			right[i] = a[i + n / 2];
		
		long inv = 0;
		
		inv += recur(left, k - 1);
		inv += recur(right, k - 1);
		
		int i = 0, j = 0;
		
		while(i < n / 2 && j < n / 2) {
			
			if(left[i] <= right[j]) {
				a[i + j] = left[i]; i++;
			}
			else {
				inv += n / 2 - i; a[i + j] = right[j]; j++;
			}
		
		}
		
		while(i < n / 2) {
			a[i + j] = left[i]; i++;
		}
		
		while(j < n / 2) {
			a[i + j] = right[j]; j++;
		}
		
		invCount[k] += inv; 
		
		return inv;
	}
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = s.nextInt();
		
		pow2 = new int[n + 1]; pow2[0] = 1;
		
		for(int i = 1; i <= n; i++)
			pow2[i] = pow2[i - 1] * 2;
		
		invCount = new long[n + 1];
		sameCount = new long[n + 1];
		
		int[] a = new int[1 << n];
		
		for(int i = 0; i < (1 << n); i++)
			a[i] = s.nextInt();
		
		recur(a.clone(), n);
		
		int m = s.nextInt();
		
		while(m-- > 0) {
			
			int k = s.nextInt();
			
			for(int i = 0; i <= k; i++)
				invCount[i] = (long)pow2[n - i] * pow2[i] * (pow2[i] - 1) / 2 - sameCount[i] - invCount[i];
			
			for(int i = k + 1; i <= n; i++)
				invCount[i] += invCount[k] - ((long)pow2[n - k] * pow2[k] * (pow2[k] - 1) / 2  - sameCount[k] - invCount[k]);
			
			w.println(invCount[n]);
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
		new Thread(null, new cf1(),"cf1",1<<26).start();
	}
}   