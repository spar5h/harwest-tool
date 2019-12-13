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
        EBeautifulMirrors solver = new EBeautifulMirrors();
        solver.solve(1, in, out);
        out.close();
    }

    static class EBeautifulMirrors {
        long modExp(long x, long y, long mod) {
            if (y == 0)
                return 1;
            if (y == 1)
                return x % mod;
            long ret = modExp(x, y / 2, mod);
            ret = ret * ret % mod;
            if (y % 2 == 1)
                ret = ret * (x % mod) % mod;
            return ret;
        }

        public void solve(int testNumber, InputReader s, PrintWriter w) {
            long mod = 998244353;
            long inv100 = modExp(100, mod - 2, mod);
            int n = s.nextInt();
            long[] p = new long[n];
            for (int i = 0; i < n; i++)
                p[i] = s.nextInt() * inv100 % mod;
            long[] a = new long[n + 1];
            long[] pre = new long[n + 1];
            for (int i = 1; i <= n; i++) {
                a[i] = (modExp(p[i - 1], mod - 2, mod) * ((pre[i - 1] + 1) % mod) % mod - pre[i - 1] + mod) % mod;
                pre[i] = (pre[i - 1] + a[i]) % mod;
            }
            w.println(pre[n]);
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

