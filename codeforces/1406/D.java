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
        DThreeSequences solver = new DThreeSequences();
        solver.solve(1, in, out);
        out.close();
    }

    static class DThreeSequences {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt();
            long[] a = new long[n];
            for (int i = 0; i < n; i++)
                a[i] = s.nextLong();
            long m = 0;
            long[] d = new long[n - 1];
            for (int i = 0; i < n - 1; i++) {
                d[i] = a[i + 1] - a[i];
                if (d[i] > 0)
                    m += d[i];
            }
            w.println(((long) 1e16 + m + a[0] + 1) / 2 - (long) 1e16 / 2);
            int q = s.nextInt();
            while (q-- > 0) {
                int l = s.nextInt() - 1, r = s.nextInt() - 1, x = s.nextInt();
                if (l == 0)
                    a[0] += x;
                if (l - 1 >= 0) {
                    if (d[l - 1] > 0)
                        m -= d[l - 1];
                    d[l - 1] += x;
                    if (d[l - 1] > 0)
                        m += d[l - 1];
                }
                if (r <= n - 2) {
                    if (d[r] > 0)
                        m -= d[r];
                    d[r] -= x;
                    if (d[r] > 0)
                        m += d[r];
                }
                w.println(((long) 1e16 + m + a[0] + 1) / 2 - (long) 1e16 / 2);
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

