import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

public class cf1{  
	
	//fft code: https://github.com/EgorKulikov/yaal/blob/master/lib/main/net/egork/numbers/FastFourierTransform.java
    static void fft(double[] a, double[] b, boolean invert) {
        int count = a.length;
        for (int i = 1, j = 0; i < count; i++) {
            int bit = count >> 1;
            for (; j >= bit; bit >>= 1) {
                j -= bit;
            }
            j += bit;
            if (i < j) {
                double temp = a[i];
                a[i] = a[j];
                a[j] = temp;
                temp = b[i];
                b[i] = b[j];
                b[j] = temp;
            }
        }
        for (int len = 2; len <= count; len <<= 1) {
            int halfLen = len >> 1;
            double angle = 2 * Math.PI / len;
            if (invert) {
                angle = -angle;
            }
            double wLenA = Math.cos(angle);
            double wLenB = Math.sin(angle);
            for (int i = 0; i < count; i += len) {
                double wA = 1;
                double wB = 0;
                for (int j = 0; j < halfLen; j++) {
                    double uA = a[i + j];
                    double uB = b[i + j];
                    double vA = a[i + j + halfLen] * wA - b[i + j + halfLen] * wB;
                    double vB = a[i + j + halfLen] * wB + b[i + j + halfLen] * wA;
                    a[i + j] = uA + vA;
                    b[i + j] = uB + vB;
                    a[i + j + halfLen] = uA - vA;
                    b[i + j + halfLen] = uB - vB;
                    double nextWA = wA * wLenA - wB * wLenB;
                    wB = wA * wLenB + wB * wLenA;
                    wA = nextWA;
                }
            }
        }
        if (invert) {
            for (int i = 0; i < count; i++) {
                a[i] /= count;
                b[i] /= count;
            }
        }
    }

    static long[] multiply(long[] a, long[] b) {
        int resultSize = Integer.highestOneBit(Math.max(a.length, b.length) - 1) << 2;
        resultSize = Math.max(resultSize, 1);
        double[] aReal = new double[resultSize];
        double[] aImaginary = new double[resultSize];
        double[] bReal = new double[resultSize];
        double[] bImaginary = new double[resultSize];
        for (int i = 0; i < a.length; i++) {
            aReal[i] = a[i];
        }
        for (int i = 0; i < b.length; i++) {
            bReal[i] = b[i];
        }
        fft(aReal, aImaginary, false);
        if (a == b) {
            System.arraycopy(aReal, 0, bReal, 0, aReal.length);
            System.arraycopy(aImaginary, 0, bImaginary, 0, aImaginary.length);
        } else {
            fft(bReal, bImaginary, false);
        }
        for (int i = 0; i < resultSize; i++) {
            double real = aReal[i] * bReal[i] - aImaginary[i] * bImaginary[i];
            aImaginary[i] = aImaginary[i] * bReal[i] + bImaginary[i] * aReal[i];
            aReal[i] = real;
        }
        fft(aReal, aImaginary, true);
        long[] result = new long[resultSize];
        for (int i = 0; i < resultSize; i++) {
            result[i] = Math.round(aReal[i]);
        }
        return result;
    }

    static long[] multiply(int[] a, int[] b, int aLength, int bLength) {
        int resultSize = Integer.highestOneBit(Math.max(aLength, bLength) - 1) << 2;
        resultSize = Math.max(resultSize, 1);
        double[] aReal = new double[resultSize];
        double[] aImaginary = new double[resultSize];
        double[] bReal = new double[resultSize];
        double[] bImaginary = new double[resultSize];
        for (int i = 0; i < aLength; i++) {
            aReal[i] = a[i];
        }
        for (int i = 0; i < bLength; i++) {
            bReal[i] = b[i];
        }
        fft(aReal, aImaginary, false);
        if (a == b && aLength == bLength) {
            System.arraycopy(aReal, 0, bReal, 0, aReal.length);
            System.arraycopy(aImaginary, 0, bImaginary, 0, aImaginary.length);
        } else {
            fft(bReal, bImaginary, false);
        }
        for (int i = 0; i < resultSize; i++) {
            double real = aReal[i] * bReal[i] - aImaginary[i] * bImaginary[i];
            aImaginary[i] = aImaginary[i] * bReal[i] + bImaginary[i] * aReal[i];
            aReal[i] = real;
        }
        fft(aReal, aImaginary, true);
        long[] result = new long[resultSize];
        for (int i = 0; i < resultSize; i++) {
            result[i] = Math.round(aReal[i]);
        }
        return result;
    }

    static long[] multiply(int[] a, int[] b) {
        return multiply(a, b, a.length, b.length);
    }
	
    static long[] rev(long[] a) {
    	
    	long[] r = new long[a.length];
    	
    	for(int i = 0; i < a.length; i++)
    		r[i] = a[a.length - i - 1];
    	
    	return r;
    }
    
	public static void main(String[] args) {

		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);		
		
		int n = s.nextInt(), x = s.nextInt();
		
		int[] a = new int[n];
		
		for(int i = 0; i < n; i++) 
			a[i] = s.nextInt() < x ? 1 : 0;
		
		int[] pre = new int[n]; pre[0] = a[0];
		
		for(int i = 1; i < n; i++)
			pre[i] = a[i] + pre[i - 1];
		
		int[] f = new int[n + 1]; f[0]++;
		
		for(int i = 0; i < n; i++)
			f[pre[i]]++;
		
		int[] r = new int[n + 1];
		
		for(int i = 0; i <= n; i++)
			r[i] = f[n - i];
		
		long[] res = multiply(f, r);
		
		long zc = 0;
		int i = 0;
		
		while(i < n) {
			
			if(a[i] == 1) {
				i++; continue;
			}
			
			long c = i;
			
			while(i < n && a[i] == 0) 
				i++;
			
			c = i - c;
			zc += c * (c + 1) / 2;
		}
		
		w.print(zc + " ");
		
		for(i = 1; i <= n; i++)
			w.print(res[n + i] + " ");
		
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
    
}