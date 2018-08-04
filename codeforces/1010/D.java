import java.util.*;
import java.lang.*;
import java.math.*;
import java.awt.List;
import java.io.*;
 
/* spar5h */

public class cf4 implements Runnable{    

	final static long mod = (long)1e9 + 7; 
	
	static class node {
		
		int op, bit, left, right;
		
		node(int op, int bit, int left, int right) {
			this.op = op; this.bit = bit; this.left = left; this.right = right;
		}
	}
	
	static node[] tree;
	static boolean[] changesParent, changesOutput;
	
	static void dfs(int x) {
		
		if(tree[x].op == 0)
			return;
		
		int left = tree[x].left, right = tree[x].right;
		
		dfs(left);
		
		if(right != -1)
			dfs(right);
		
		if(tree[x].op == 1) {
			
			if(tree[left].bit == 0 && tree[right].bit == 1)
				changesParent[left] = true;
			
			else if(tree[left].bit == 1 && tree[right].bit == 0)
				changesParent[right] = true;
			
			else if(tree[left].bit == 1 && tree[right].bit == 1) {
				changesParent[left] = true; changesParent[right] = true;
			}
			
			tree[x].bit = tree[left].bit & tree[right].bit;
		}
		
		else if(tree[x].op == 2) {
			
			if(tree[left].bit == 0 && tree[right].bit == 1)
				changesParent[right] = true;
			
			else if(tree[left].bit == 1 && tree[right].bit == 0)
				changesParent[left] = true;
			
			else if(tree[left].bit == 0 && tree[right].bit == 0) {
				changesParent[left] = true; changesParent[right] = true;
			}
			
			tree[x].bit = tree[left].bit | tree[right].bit;
		}
		
		else if(tree[x].op == 3) {
			
			changesParent[left] = true; changesParent[right] = true;
			
			tree[x].bit = tree[left].bit ^ tree[right].bit;
		}
		
		else if(tree[x].op == 4) {
			
			changesParent[left] = true;
			
			tree[x].bit = 1 ^ tree[left].bit;
		}
		
	}
	
	static void dfs2(int x) {
		
		changesOutput[x] = true;
		
		int left = tree[x].left, right = tree[x].right;
		
		if(left != -1 && changesParent[left])
			dfs2(left);
		
		if(right != -1 && changesParent[right])
			dfs2(right);
	}
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = s.nextInt();
		
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		hm.put("IN", 0); hm.put("AND", 1); hm.put("OR", 2); hm.put("XOR", 3); hm.put("NOT", 4);
		
		tree = new node[n + 1];
		
		for(int i = 1; i <= n; i++) {
			
			int op = hm.get(s.next());
			
			if(op == 0)
				tree[i] = new node(op, s.nextInt(), -1, -1);
			else if(op == 4)
				tree[i] = new node(op, -1, s.nextInt(), -1);
			else
				tree[i] = new node(op, -1, s.nextInt(), s.nextInt());
		}
		
		changesParent = new boolean[n + 1];
		
		dfs(1);
		
		changesOutput = new boolean[n + 1]; 
		
		dfs2(1);
		
		for(int i = 1; i <= n; i++) {
			
			if(tree[i].op != 0)
				continue;
			
			if(changesOutput[i])
				w.print(tree[1].bit ^ 1);
			else
				w.print(tree[1].bit);
		}
		
		w.println();
		
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

public static void main(String args[]) throws Exception
{
	new Thread(null, new cf4(),"cf4",1<<26).start();
	}
}   