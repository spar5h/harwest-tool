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
        FCards solver = new FCards();
        solver.solve(1, in, out);
        out.close();
    }

    static class FCards {
        long mod = 998244353;

        long modExp(long x, long y) {
            if (y == 0)
                return 1;
            if (y == 1)
                return x;
            long ret = modExp(x, y >> 1);
            ret = ret * ret % mod;
            if ((y & 1) == 1)
                ret = ret * x % mod;
            return ret;
        }

        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt(), m = s.nextInt(), k = s.nextInt();
            //dp[tuple size][number of distinct elements] = number of such tuples
            long[][] dp = new long[k + 1][Math.min(n, k) + 1];
            dp[0][0] = 1;
            for (int i = 1; i <= k; i++) {
                for (int j = 1; j <= Math.min(n, k); j++) {
                    dp[i][j] = (dp[i][j] + dp[i - 1][j] * j % mod) % mod;
                    dp[i][j] = (dp[i][j] + dp[i - 1][j - 1] * (n - j + 1) % mod) % mod;
                }
            }
            long invm = modExp(m, mod - 2);
            long res = 0;
            long prob = 1;
            for (int i = 0; i <= Math.min(n, k); i++) {
                res = (res + dp[k][i] * prob % mod) % mod;
                prob = prob * invm % mod;
            }
            w.println(res);
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

