import java.util.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

public class cf4 implements Runnable {   
	
	public void run() {
			
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = s.nextInt();
		
		int[] a = new int[n];
		
		for(int i = 0; i < n; i++)
			a[i] = s.nextInt();
		
		int[] leaf = new int[2];
		Arrays.fill(leaf, -1);
		
		for(int i = 0; i < n; i++) {
			if(a[i] == 1) {
				leaf[0] = i; break;
			}
		}
		
		for(int i = 0; i < n; i++) {
			
			if(a[i] == 1 && leaf[0] != i) {
				leaf[1] = i; break;
			}
		}
		
		if(leaf[0] == -1) {
			leaf[0] = 0; leaf[1] = 1;
		}
		
		else if(leaf[1] == -1) {
			
			for(int i = 0; i < n; i++) {
				
				if(leaf[0] != i) {
					leaf[1] = i; break;
				}	
			}		
		}
		
		int[] deg = new int[n];
		
		int curr = leaf[0];
		
		int leafCount = 0;
		
		for(int i = 0; i < n; i++)
			if(a[i] == 1 && leaf[0] != i && leaf[1] != i)
				leafCount++;
		
		int notLeaf = n - 2 - leafCount;
		
		ArrayList<Integer> u = new ArrayList<Integer>();
		ArrayList<Integer> v = new ArrayList<Integer>();
		
		int[] vis = new int[n];
		
		for(int i = 0; i < notLeaf; i++) {
			
			for(int j = 0; j < n; j++) {
				
				if(a[j] > 1 && vis[j] == 0 && leaf[0] != j && leaf[1] != j) {
					
					vis[j] = 1;
					u.add(curr + 1); v.add(j + 1);
					deg[curr]++; deg[j]++;
					curr = j;
					break;
				}
			}
		}
		
		u.add(curr + 1); v.add(leaf[1] + 1);
		deg[curr]++; deg[leaf[1]]++;
		
		for(int i = 0; i < leafCount; i++) {
			
			for(int j = 0; j < n; j++) {
				
				if(a[j] == 1 && vis[j] == 0 && leaf[0] != j && leaf[1] != j) {
					vis[j] = 1; curr = j; break;
				}
			}
			
			for(int j = 0; j < n; j++) {
				
				if(a[j] > 1 && deg[j] < a[j]) {
					u.add(curr + 1); v.add(j + 1);
					deg[curr]++; deg[j]++;
					break;
				}
			}
		}
		
		if(u.size() == n - 1) {
			
			w.println("YES " + (notLeaf + 2 - 1));
			
			w.println(n - 1);
			
			for(int i = 0; i < n - 1; i++)
				w.println(u.get(i) + " " + v.get(i));
			
		}
		else
			w.println("NO");
		
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