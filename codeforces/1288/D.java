import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
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
        DMinimaxProblem solver = new DMinimaxProblem();
        solver.solve(1, in, out);
        out.close();
    }

    static class DMinimaxProblem {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt(), m = s.nextInt();
            int[][] a = new int[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++)
                    a[i][j] = s.nextInt();
            }
            int[] best = new int[1 << m];
            Arrays.fill(best, -1);
            int[] idx = new int[1 << m];
            Arrays.fill(idx, -1);
            for (int mask = 0; mask < (1 << m); mask++) {
                for (int i = 0; i < n; i++) {
                    int can = Integer.MAX_VALUE;
                    for (int j = 0; j < m; j++) {
                        if (((mask >> j) & 1) == 1)
                            can = Math.min(a[i][j], can);
                    }
                    if (can > best[mask]) {
                        best[mask] = can;
                        idx[mask] = i;
                    }
                }
            }
            int res = -1;
            int i1 = -1, i2 = -1;
            for (int mask = 0; mask < (1 << m); mask++) {
                if (Math.min(best[mask], best[mask ^ ((1 << m) - 1)]) > res) {
                    res = Math.min(best[mask], best[mask ^ ((1 << m) - 1)]);
                    i1 = idx[mask];
                    i2 = idx[mask ^ ((1 << m) - 1)];
                }
            }
            w.println((i1 + 1) + " " + (i2 + 1));
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

