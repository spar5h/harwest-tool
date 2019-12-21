import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 *
 * @author Sparsh Sanchorawala
 */
public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        DFuzzySearch solver = new DFuzzySearch();
        solver.solve(1, in, out);
        out.close();
    }

    static class DFuzzySearch {
        //https://github.com/EgorKulikov/yaal/blob/master/lib/main/net/egork/numbers/FastFourierTransform.java
        void fft(double[] a, double[] b, boolean invert) {
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

        long[] multiply(long[] a, long[] b) {
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

        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt(), m = s.nextInt(), p = s.nextInt();
            char[] str = s.next().toCharArray();
            char[] str2 = s.next().toCharArray();
            long[] res = new long[n];
            char[] ch = {'A', 'C', 'G', 'T'};
            for (char k : ch) {
                long[] a = new long[n];
                long[] b = new long[m];
                for (int i = 0; i < n; i++) {
                    if (str[i] == k) {
                        a[Math.max(0, i - p)] += 1;
                        if (i + p + 1 < n)
                            a[i + p + 1] -= 1;
                    }
                }
                for (int i = 1; i < n; i++)
                    a[i] += a[i - 1];
                for (int i = 0; i < n; i++)
                    a[i] = Math.min(1, a[i]);
                for (int i = 0; i < m; i++)
                    if (str2[i] == k)
                        b[m - 1 - i] = 1;
                long[] c = multiply(a, b);
                for (int i = m - 1; i < n + m - 1; i++) {
                    res[i - (m - 1)] += c[i];
                }
            }
            int ans = 0;
            for (int i = 0; i < n; i++)
                if (res[i] == m)
                    ans++;
            w.println(ans);
        }

    }

    static class InputReader {
        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;
        private InputReader.SpaceCharFilter filter;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int read() {
            if (numChars == -1) {
                throw new InputMismatchException();
            }
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0) {
                    return -1;
                }
            }
            return buf[curChar++];
        }

        public int nextInt() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            int res = 0;
            do {
                if (c < '0' || c > '9') {
                    throw new InputMismatchException();
                }
                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public String nextString() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            StringBuilder res = new StringBuilder();
            do {
                if (Character.isValidCodePoint(c)) {
                    res.appendCodePoint(c);
                }
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        public boolean isSpaceChar(int c) {
            if (filter != null) {
                return filter.isSpaceChar(c);
            }
            return isWhitespace(c);
        }

        public static boolean isWhitespace(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        public String next() {
            return nextString();
        }

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);

        }

    }
}

