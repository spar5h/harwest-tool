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
        DTediousLee solver = new DTediousLee();
        solver.solve(1, in, out);
        out.close();
    }

    static class DTediousLee {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = (int) 2e6;
            long[][] dp = new long[n + 1][2];
            long mod = (long) 1e9 + 7;
            for (int i = 3; i <= n; i++) {
                dp[i][1] = 2 * dp[i - 2][0] % mod;
                dp[i][1] = (dp[i][1] + dp[i - 1][0]) % mod;
                dp[i][1] = (dp[i][1] + 1) % mod;

                long dp2 = dp[i - 2][1];
                if ((i - 2) % 3 == 2) {
                    dp2 = dp[i - 2][0];
                }
                long dp1 = dp[i - 1][1];
                if ((i - 1) % 3 == 2) {
                    dp1 = dp[i - 1][0];
                }

                dp[i][0] = 2 * dp2 % mod;
                dp[i][0] = (dp[i][0] + dp1) % mod;
            }

            int t = s.nextInt();
            while (t-- > 0) {
                int x = s.nextInt();
                if (x % 3 == 2)
                    w.println(dp[x][0] * 4 % mod);
                else
                    w.println(dp[x][1] * 4 % mod);
            }
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

