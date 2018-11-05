import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

public class cf3 implements Runnable {    
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = s.nextInt();
		
		String str = s.next();
		
		int[] a = new int[n];
		
		for(int i = 0; i < n; i++) {
			
			if(str.charAt(i) == 'R')
				a[i] = 0;
			else if(str.charAt(i) == 'L')
				a[i] = 1;
			else if(str.charAt(i) == 'U')
				a[i] = 2;
			else
				a[i] = 3;
		}
		
		int x = s.nextInt(), y = s.nextInt();
		
		int left = 0, right = n;
		
		int ans = -1;
		
		while(left <= right) {
			
			int k = (left + right) / 2;
			
			int currX = 0, currY = 0;
			
			for(int i = k; i < n; i++) {
				
				if(a[i] == 0)
					currX++;
				else if(a[i] == 1)
					currX--;
				else if(a[i] == 2)
					currY++;
				else
					currY--;
			}
			
			if(k == 0) {
				
				if(currX == x && currY == y) {
					ans = 0; right = k - 1;
				}
				else
					left = k + 1;
				
				continue;
			}
			
			boolean check = false;
			
			int val = abs(currX - x) + abs(currY - y);
			
			if(val <= k && (k - val) % 2 == 0)
				check = true; 
			
			for(int i = 0; i + k < n && !check; i++) {
				
				if(a[i] == 0)
					currX++;
				else if(a[i] == 1)
					currX--;
				else if(a[i] == 2)
					currY++;
				else
					currY--;
				
				if(a[i + k] == 0)
					currX--;
				else if(a[i + k] == 1)
					currX++;
				else if(a[i + k] == 2)
					currY--;
				else
					currY++;
				
				val = abs(currX - x) + abs(currY - y);
				
				if(val <= k && (k - val) % 2 == 0) 
					check = true;
			}
			
			if(check) {
				right = k - 1; ans = k;
			}
			else
				left = k + 1;
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
		new Thread(null, new cf3(),"cf3",1<<26).start();
	}
}   