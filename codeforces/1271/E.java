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
        ECommonNumber solver = new ECommonNumber();
        solver.solve(1, in, out);
        out.close();
    }

    static class ECommonNumber {
        int log2(long x) {
            int c = 0;
            while (x > 1) {
                x /= 2;
                c++;
            }
            return c;
        }

        long getValue(long d, long n) {
            long l2d = log2(d);
            long l2n = log2(n);
            long val = (long) Math.pow(2, (l2n - 1) - l2d + 1) - 1;
            long temp = d * (long) Math.pow(2, l2n - l2d);
            String str1 = Long.toBinaryString(temp);
            String str2 = Long.toBinaryString(n);
            boolean check = false;
            for (int i = 0; i < l2d + 1; i++) {
                if (str1.charAt(i) == '1' && str2.charAt(i) == '0') {
                    check = true;
                    break;
                }
                if (str1.charAt(i) == '0' && str2.charAt(i) == '1') {
                    val += (long) Math.pow(2, (l2n + 1) - (l2d + 1));
                    check = true;
                    break;
                }
            }
            if (!check)
                val += n - temp + 1;
            return val;
        }

        public void solve(int testNumber, InputReader s, PrintWriter w) {
            long n = s.nextLong(), k = s.nextLong();
            long l2n = log2(n);
            long r1 = -1;
            long left = 1, right = n / 2;
            //even
            while (left <= right) {
                long mid = (left + right) / 2;
                long d = mid * 2;
                long val = getValue(d, n) + getValue(d + 1, n);
                if (val >= k) {
                    r1 = d;
                    left = mid + 1;
                } else
                    right = mid - 1;
            }
            long r2 = -1;
            left = 1;
            right = (n + 1) / 2;
            //odd
            while (left <= right) {
                long mid = (left + right) / 2;
                long d = ((left + right) / 2) * 2 - 1;
                long val = getValue(d, n);
                if (val >= k) {
                    r2 = d;
                    left = mid + 1;
                } else
                    right = mid - 1;
            }
            w.println(Math.max(r1, r2));
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

        public long nextLong() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            long res = 0;
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

        public boolean isSpaceChar(int c) {
            if (filter != null) {
                return filter.isSpaceChar(c);
            }
            return isWhitespace(c);
        }

        public static boolean isWhitespace(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);

        }

    }
}

