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
 * @author spar5h
 */
public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        TaskC solver = new TaskC();
        solver.solve(1, in, out);
        out.close();
    }

    static class TaskC {
        int k;
        int d;
        long[][] dp;
        long mod = (long) 1e9 + 7;

        void recur(int x) {

            dp[x][0] = 0;
            dp[x][1] = 0;

            for (int j = 1; j <= k; j++) {

                if (x - j < 0)
                    break;

                if (dp[x - j][0] == -1 || dp[x - j][1] == -1)
                    recur(x - j);

                dp[x][1] = (dp[x][1] + dp[x - j][1]) % mod;

                if (j >= d)
                    dp[x][1] = (dp[x][1] + dp[x - j][0]) % mod;
                else
                    dp[x][0] = (dp[x][0] + dp[x - j][0]) % mod;
            }
        }

        public void solve(int testNumber, InputReader s, PrintWriter w) {

            int n = s.nextInt();
            k = s.nextInt();
            d = s.nextInt();

            dp = new long[n + 1][2];

            for (int i = 0; i <= n; i++)
                for (int j = 0; j < 2; j++)
                    dp[i][j] = -1;

            dp[0][0] = 1;
            dp[0][1] = 0;

            recur(n);

            w.println(dp[n][1]);
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

