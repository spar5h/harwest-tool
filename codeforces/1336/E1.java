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
        E1ChioriAndDollPickingEasyVersion solver = new E1ChioriAndDollPickingEasyVersion();
        solver.solve(1, in, out);
        out.close();
    }

    static class E1ChioriAndDollPickingEasyVersion {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            long mod = 998244353;
            int n = s.nextInt(), m = s.nextInt();
            long[] a = new long[n];
            for (int i = 0; i < n; i++)
                a[i] = s.nextLong();

            long[] basis = new long[m];
            for (int i = 0; i < n; i++) {
                long mask = a[i];
                for (int j = 0; j < m; j++) {
                    if (((mask >> (m - 1 - j)) & 1) == 0)
                        continue;
                    if (basis[j] == 0) {
                        basis[j] = mask;
                        break;
                    }
                    mask ^= basis[j];
                }
            }
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < i; j++)
                    if (((basis[j] >> (m - 1 - i)) & 1) == 1)
                        basis[j] ^= basis[i];
            }
            int sz = 0;
            for (int i = 0; i < m; i++)
                if (basis[i] != 0)
                    sz++;
            long val = 1;
            for (int i = 0; i < n - sz; i++)
                val = val * 2 % mod;

            long[] res = new long[m + 1];
            if (sz <= 20) {
                int c = 0;
                long[] cbasis = new long[sz];
                for (int i = 0; i < m; i++) {
                    if (basis[i] == 0)
                        continue;
                    cbasis[c] = basis[i];
                    c++;
                }
                for (int mask = 0; mask < (1 << sz); mask++) {
                    long x = 0;
                    for (int j = 0; j < sz; j++)
                        if (((mask >> j) & 1) == 1)
                            x ^= cbasis[j];
                    res[Long.bitCount(x)] = (res[Long.bitCount(x)] + val) % mod;
                }
            } else {
                int[] mask = new int[sz];
                int c = 0;
                for (int i = 0; i < m; i++) {
                    if (basis[i] != 0)
                        continue;
                    int d = 0;
                    for (int j = 0; j < m; j++) {
                        if (basis[j] == 0)
                            continue;
                        if (((basis[j] >> (m - 1 - i)) & 1) == 1)
                            mask[d] ^= (1 << c);
                        d++;
                    }
                    c++;
                }
                long[][][] dp = new long[sz + 1][sz + 1][1 << (m - sz)];
                dp[0][0][0] = 1;
                for (int i = 1; i <= sz; i++) {
                    for (int j = 0; j <= sz; j++) {
                        for (int k = 0; k < 1 << (m - sz); k++) {
                            if (j - 1 >= 0)
                                dp[i][j][k] = dp[i - 1][j - 1][k ^ mask[i - 1]];
                            dp[i][j][k] = (dp[i][j][k] + dp[i - 1][j][k]) % mod;
                        }
                    }
                }
                for (int j = 0; j <= sz; j++) {
                    for (int k = 0; k < 1 << (m - sz); k++)
                        res[j + Integer.bitCount(k)] = (res[j + Integer.bitCount(k)] + dp[sz][j][k] * val % mod) % mod;
                }
            }
            for (int i = 0; i <= m; i++)
                w.print(res[i] + " ");
            w.println();
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

