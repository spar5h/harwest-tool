import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

public class cf2{  
	
	static int[][] dp, dp2, d, d2;
	static int[] ai, aj;
	
	static void update(int s_i, int f_i, int s_j, int f_j, int val, int c_i, int c_j, int rep) {
		
		for(int i = s_i; i <= f_i; i++) {
			
			for(int j = s_j; j <= f_j; j++) {
				
				int rpl = rep;
				
				if(i != c_i)
					rpl++;
				
				if(j != c_j)
					rpl++;
				
				if(d[i][j] > val + rpl) {
					d[i][j] = val + rpl; d2[i][j] = rpl;
				}
				else if(d[i][j] == val + rpl) 
					d2[i][j] = min(rpl, d2[i][j]);
				
			}
		}
	}
	
	static boolean rook(int si, int sj, int ti, int tj) {
		
		if(si == ti || sj == tj)
			return true;
		
		return false;
	}
	
	static boolean bishop(int si, int sj, int ti, int tj) {
			
		if(abs(si - ti) == abs(sj - tj))
			return true;
		
		return false;
	}
	
	static boolean knight(int si, int sj, int ti, int tj) {
		
		if((abs(si - ti) == 1 && abs(sj - tj) == 2) || (abs(si - ti) == 2 && abs(sj - tj) == 1))
			return true;
		
		return false;
	}
	
	static void recur(int x, int n) {
		
		//rook: 0, bishop; 1, knight: 2
		
		if(x + 1 < n * n - 1)
			recur(x + 1, n);
		
		int si = ai[x], sj = aj[x], ti = ai[x + 1], tj = aj[x + 1];
		
		d = new int[3][3]; d2 = new int[3][3];
		
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++)
				d[i][j] = Integer.MAX_VALUE;
		
		update(0, 2, 0, 2, 2, 0, 0, 0);

		//1
		if(rook(si, sj, ti, tj)) 
			update(0, 2, 0, 2, 1, 0, 0, 0);
		
		if(bishop(si, sj, ti, tj)) 
			update(0, 2, 0, 2, 1, 1, 1, 0);
			
		if(knight(si, sj, ti, tj)) 
			update(0, 2, 0, 2, 1, 2, 2, 0);	
		
		//2
		for(int xi = 0; xi < n; xi++) {
			
			for(int xj = 0; xj < n; xj++) {
				
				if(bishop(si, sj, xi, xj) && bishop(xi, xj, ti, tj))	
					update(1, 2, 1, 2, 2, 1, 1, 0);
				
				if(knight(si, sj, xi, xj) && knight(xi, xj, ti, tj))	
					update(1, 2, 1, 2, 2, 2, 2, 0);
				
				if(bishop(si, sj, xi, xj) && knight(xi, xj, ti, tj))
					update(1, 1, 2, 2, 2, 1, 2, 1);
				
				if(knight(si, sj, xi, xj) && bishop(xi, xj, ti, tj))
					update(2, 2, 1, 1, 2, 2, 1, 1);
			}
		}
		
		//3
		for(int xi = 0; xi < n; xi++) {
			
			for(int xj = 0; xj < n; xj++) {
				
				for(int yi = 0; yi < n; yi++) {
					
					for(int yj = 0; yj < n; yj++) {
						
						if(bishop(si, sj, xi, xj) && bishop(xi, xj, yi, yj) && bishop(yi, yj, ti, tj)) 
							update(1, 1, 1, 1, 3, 1, 1, 0);
						
						if(knight(si, sj, xi, xj) && knight(xi, xj, yi, yj) && knight(yi, yj, ti, tj)) {
							update(2, 2, 2, 2, 3, 2, 2, 0); 
						}	
					}
				}
			}
		}
		
		for(int i = 0; i < 3; i++) 
			dp[x][i] = Integer.MAX_VALUE;
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				
				if(dp[x][i] > d[i][j] + dp[x + 1][j]) {
					dp[x][i] = d[i][j] + dp[x + 1][j]; dp2[x][i] = d2[i][j] + dp2[x + 1][j];
				}
				else if(dp[x][i] == d[i][j] + dp[x + 1][j])
					dp2[x][i] = min(d2[i][j] + dp2[x + 1][j], dp2[x][i]);
			}	
		}
	}
	
	public static void main(String[] args) {

		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);		
		
		int n = s.nextInt();
		
		ai = new int[n * n];
		aj = new int[n * n];
		
		for(int i = 0; i < n; i++) {
			
			for(int j = 0; j < n; j++) {
				
				int x = s.nextInt() - 1;
				ai[x] = i; aj[x] = j; 
			}
		}
		
		dp = new int[n * n][3];
		dp2 = new int[n * n][3];
		
		recur(0, n);
		
		int v1 = Integer.MAX_VALUE, v2 = 0;
		
		for(int i = 0; i < 3; i++) {
			
			if(dp[0][i] < v1) {
				v1 = dp[0][i]; v2 = dp2[0][i];
			}
			else if(dp[0][i] == v1)
				v2 = min(dp2[0][i], v2);
		}		
		
		w.println(v1 + " " + v2);
		
		w.close();
	}
	
	static class InputReader {
		
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;
		private SpaceCharFilter filter;
		
		public InputReader(InputStream stream)
		{
			this.stream = stream;
		}
		
		public int read()
		{
			if (numChars==-1) 
				throw new InputMismatchException();
			
			if (curChar >= numChars)
			{
				curChar = 0;
				try 
				{
					numChars = stream.read(buf);
				}
				catch (IOException e)
				{
					throw new InputMismatchException();
				}
				
				if(numChars <= 0)				
					return -1;
			}
			return buf[curChar++];
		}
	 
		public String nextLine()
		{
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			String str = "";
            try
            {
                str = br.readLine();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return str;
		}
		public int nextInt()
		{
			int c = read();
			
			while(isSpaceChar(c)) 
				c = read();
			
			int sgn = 1;
			
			if (c == '-') 
			{
				sgn = -1;
				c = read();
			}
			
			int res = 0;
			do 
			{
				if(c<'0'||c>'9') 
					throw new InputMismatchException();
				res *= 10;
				res += c - '0';
				c = read();
			}
			while (!isSpaceChar(c)); 
			
			return res * sgn;
		}
		
		public long nextLong() 
		{
			int c = read();
			while (isSpaceChar(c))
				c = read();
			int sgn = 1;
			if (c == '-') 
			{
				sgn = -1;
				c = read();
			}
			long res = 0;
			
			do 
			{
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				res *= 10;
				res += c - '0';
				c = read();
			}
			while (!isSpaceChar(c));
				return res * sgn;
		}
		
		public double nextDouble() 
		{
			int c = read();
			while (isSpaceChar(c))
				c = read();
			int sgn = 1;
			if (c == '-') 
			{
				sgn = -1;
				c = read();
			}
			double res = 0;
			while (!isSpaceChar(c) && c != '.') 
			{
				if (c == 'e' || c == 'E')
					return res * Math.pow(10, nextInt());
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				res *= 10;
				res += c - '0';
				c = read();
			}
			if (c == '.') 
			{
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
		
		public String readString() 
		{
			int c = read();
			while (isSpaceChar(c))
				c = read();
			StringBuilder res = new StringBuilder();
			do 
			{
				res.appendCodePoint(c);
				c = read();
			} 
			while (!isSpaceChar(c));
			
			return res.toString();
		}
	 
		public boolean isSpaceChar(int c) 
		{
			if (filter != null)
				return filter.isSpaceChar(c);
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}
	 
		public String next() 
		{
			return readString();
		}
		
		public interface SpaceCharFilter 
		{
			public boolean isSpaceChar(int ch);
		}
	}
    
}