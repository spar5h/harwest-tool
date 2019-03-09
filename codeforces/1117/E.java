import java.util.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

public class cf5 implements Runnable {    
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int[] N = new int[3 + 1];
		
		N[0] = 10000;
		
		for(int i = 1; i <= 3; i++)
			N[i] = (int)ceil((double)N[i - 1] / 26);
		
		char[] in = s.next().toCharArray(), a;
		int n = in.length;
		
		int[] pos = new int[n];
		
		ArrayList<Integer> pts = new ArrayList<Integer>();
		pts.add(0); pts.add(n);
		
		for(int k = 1; k <= 3; k++) {
			
			ArrayList<Integer> temp = new ArrayList<>();
			
			w.print("? ");
			
			for(int i = 0; i < pts.size() - 1; i++) {
				
				for(int j = 0; pts.get(i) + j * N[k] < pts.get(i + 1); j++) {
					
					for(int l = 0; l < N[k] && pts.get(i) + j * N[k] + l < pts.get(i + 1); l++)
						w.print((char)('a' + j));
					
					temp.add(pts.get(i) + j * N[k]);
				}
			}
			
			w.println();
			
			w.flush();
			
			a = s.next().toCharArray();
			
			for(int i = 0; i < n; i++)
				pos[i] += (a[i] - 'a') * N[k];
			
			temp.add(n);
			pts = temp;
		}
	
		char[] out = new char[n];
		
		for(int i = 0; i < n; i++)
			out[pos[i]] = in[i];
		
		w.print("! ");
		
		for(int i = 0; i < n; i++)
			w.print(out[i]);
		
		w.println();
		
		w.flush();
		
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