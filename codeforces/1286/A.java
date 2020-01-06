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
        AGarland solver = new AGarland();
        solver.solve(1, in, out);
        out.close();
    }

    static class AGarland {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt();
            int[] a = new int[n + 1];
            int[] f = new int[2];
            f[0] = n / 2;
            f[1] = (n + 1) / 2;
            for (int i = 1; i <= n; i++) {
                a[i] = s.nextInt();
                if (a[i] == 0)
                    a[i] = -1;
                else
                    a[i] %= 2;
                if (a[i] != -1)
                    f[a[i]]--;
            }
            int[][][][] dp = new int[n + 1][f[0] + 1][f[1] + 1][2];
            for (int i = 0; i <= n; i++)
                for (int j = 0; j <= f[0]; j++)
                    for (int k = 0; k <= f[1]; k++)
                        for (int l = 0; l < 2; l++)
                            dp[i][j][k][l] = 1000;
            dp[0][0][0][0] = 0;
            dp[0][0][0][1] = 0;
            for (int i = 1; i <= n; i++) {
                for (int j = 0; j <= f[0]; j++) {
                    for (int k = 0; k <= f[1]; k++) {
                        if (a[i] == -1) {
                            if (j - 1 >= 0)
                                dp[i][j][k][0] = Math.min(dp[i - 1][j - 1][k][0], dp[i - 1][j - 1][k][1] + 1);
                            if (k - 1 >= 0)
                                dp[i][j][k][1] = Math.min(dp[i - 1][j][k - 1][1], dp[i - 1][j][k - 1][0] + 1);
                        } else {
                            dp[i][j][k][a[i]] = Math.min(dp[i - 1][j][k][a[i]], 1 + dp[i - 1][j][k][1 ^ a[i]]);
                        }
                    }
                }
            }
            w.println(Math.min(dp[n][f[0]][f[1]][0], dp[n][f[0]][f[1]][1]));
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

