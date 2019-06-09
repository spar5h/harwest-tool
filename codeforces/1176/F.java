import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.io.IOException;
import java.util.Collections;
import java.util.ArrayList;
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
        FDestroyIt solver = new FDestroyIt();
        solver.solve(1, in, out);
        out.close();
    }

    static class FDestroyIt {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt();
            long[][] best = new long[n][4];
            long[][] dBest = new long[n][4];
            for (int i = 0; i < n; i++) {
                ArrayList<Long>[] c = new ArrayList[4];
                for (int j = 1; j <= 3; j++)
                    c[j] = new ArrayList<>();
                int k = s.nextInt();
                while (k-- > 0)
                    c[s.nextInt()].add(s.nextLong());
                for (int j = 1; j <= 3; j++) {
                    Collections.sort(c[j]);
                    best[i][j] = Long.MIN_VALUE;
                    dBest[i][j] = Long.MIN_VALUE;
                }
                if (c[1].size() >= 1) {
                    best[i][1] = Math.max(c[1].get(c[1].size() - 1), best[i][1]);
                    dBest[i][1] = Math.max(2 * c[1].get(c[1].size() - 1), dBest[i][1]);
                }
                if (c[1].size() >= 2) {
                    best[i][2] = Math.max(c[1].get(c[1].size() - 1) + c[1].get(c[1].size() - 2), best[i][2]);
                    dBest[i][2] = Math.max(2 * c[1].get(c[1].size() - 1) + c[1].get(c[1].size() - 2), dBest[i][2]);
                }
                if (c[1].size() >= 3) {
                    best[i][3] = Math.max(c[1].get(c[1].size() - 1) + c[1].get(c[1].size() - 2) + c[1].get(c[1].size() - 3), best[i][3]);
                    dBest[i][3] = Math.max(2 * c[1].get(c[1].size() - 1) + c[1].get(c[1].size() - 2) + c[1].get(c[1].size() - 3), dBest[i][3]);
                }
                if (c[2].size() >= 1) {
                    best[i][1] = Math.max(c[2].get(c[2].size() - 1), best[i][1]);
                    dBest[i][1] = Math.max(2 * c[2].get(c[2].size() - 1), dBest[i][1]);
                }
                if (c[1].size() >= 1 && c[2].size() >= 1) {
                    best[i][2] = Math.max(c[1].get(c[1].size() - 1) + c[2].get(c[2].size() - 1), best[i][2]);
                    dBest[i][2] = Math.max(2 * c[1].get(c[1].size() - 1) + c[2].get(c[2].size() - 1), dBest[i][2]);
                    dBest[i][2] = Math.max(c[1].get(c[1].size() - 1) + 2 * c[2].get(c[2].size() - 1), dBest[i][2]);
                }
                if (c[3].size() >= 1) {
                    best[i][1] = Math.max(c[3].get(c[3].size() - 1), best[i][1]);
                    dBest[i][1] = Math.max(2 * c[3].get(c[3].size() - 1), dBest[i][1]);
                }
            }
            long[][] dp = new long[n][10];
            for (int k = 0; k <= 3; k++)
                dp[0][k] = best[0][k];
            for (int k = 4; k < 10; k++)
                dp[0][k] = Long.MIN_VALUE;
            for (int i = 1; i < n; i++) {
                for (int j = 0; j < 10; j++) {
                    dp[i][j] = Long.MIN_VALUE;
                    for (int k = 0; k <= 3; k++) {
                        if (j - k < 0) {
                            if (dp[i - 1][j - k + 10] != Long.MIN_VALUE && dBest[i][k] != Long.MIN_VALUE)
                                dp[i][j] = Math.max(dp[i - 1][j - k + 10] + dBest[i][k], dp[i][j]);
                        } else {
                            if (dp[i - 1][j - k] != Long.MIN_VALUE && best[i][k] != Long.MIN_VALUE)
                                dp[i][j] = Math.max(dp[i - 1][j - k] + best[i][k], dp[i][j]);
                        }
                    }
                }
            }

            long res = 0;
            for (int i = 0; i < 10; i++)
                res = Math.max(dp[n - 1][i], res);

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

