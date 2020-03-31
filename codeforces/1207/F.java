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
        FRemainderProblem solver = new FRemainderProblem();
        solver.solve(1, in, out);
        out.close();
    }

    static class FRemainderProblem {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int q = s.nextInt();
            int n = 500000;
            int[] a = new int[n + 1];
            int lim = 1;
            while ((long) lim * lim <= n)
                lim++;
            int[][] res = new int[lim + 1][];
            for (int i = 1; i <= lim; i++)
                res[i] = new int[i];
            while (q-- > 0) {
                int t = s.nextInt(), x = s.nextInt(), y = s.nextInt();
                if (t == 1) {
                    for (int i = 1; i <= lim; i++)
                        res[i][x % i] += y;
                    a[x] += y;
                } else {
                    if (x > lim) {
                        long ans = 0;
                        for (int j = y; j <= n; j += x)
                            ans += a[j];
                        w.println(ans);
                    } else
                        w.println(res[x][y]);
                }
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

