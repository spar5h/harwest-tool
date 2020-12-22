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
        DGrid00100 solver = new DGrid00100();
        solver.solve(1, in, out);
        out.close();
    }

    static class DGrid00100 {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int t = s.nextInt();
            while (t-- > 0) {
                int n = s.nextInt(), k = s.nextInt();
                int[][] a = new int[n][n];
                for (int i = 0; i < n && k > 0; i++) {
                    for (int j = 0; j < n && k > 0; j++) {
                        a[(i + j) % n][j] = 1;
                        k--;
                    }
                }
                long rmax = -1, rmin = n + 1;
                for (int i = 0; i < n; i++) {
                    long can = 0;
                    for (int j = 0; j < n; j++) {
                        can += a[i][j];
                    }
                    rmax = Math.max(can, rmax);
                    rmin = Math.min(can, rmin);
                }
                long cmax = -1, cmin = n + 1;
                for (int j = 0; j < n; j++) {
                    long can = 0;
                    for (int i = 0; i < n; i++) {
                        can += a[i][j];
                    }
                    cmax = Math.max(can, cmax);
                    cmin = Math.min(can, cmin);
                }
                long f = (rmax - rmin) * (rmax - rmin) + (cmax - cmin) * (cmax - cmin);
                w.println(f);
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++)
                        w.print(a[i][j]);
                    w.println();
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

