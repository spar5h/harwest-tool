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
        AExercisingWalk solver = new AExercisingWalk();
        solver.solve(1, in, out);
        out.close();
    }

    static class AExercisingWalk {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int t = s.nextInt();
            while (t-- > 0) {
                long a = s.nextLong(), b = s.nextLong(), c = s.nextLong(), d = s.nextLong();
                long x = s.nextLong(), y = s.nextLong(), x1 = s.nextLong(), y1 = s.nextLong(), x2 = s.nextLong(), y2 = s.nextLong();
                boolean xb = true, yb = true;
                if (x2 - x1 + 1 == 1) {
                    if (a > 0 || b > 0)
                        xb = false;
                } else {
                    long z = Math.min(a, b);
                    a -= z;
                    b -= z;
                    if (x - x1 < a)
                        xb = false;
                    if (x2 - x < b)
                        xb = false;
                }
                if (y2 - y1 + 1 == 1) {
                    if (c > 0 || d > 0)
                        yb = false;
                } else {
                    long z = Math.min(c, d);
                    c -= z;
                    d -= z;
                    if (y - y1 < c)
                        yb = false;
                    if (y2 - y < d)
                        yb = false;
                }
                if (xb && yb)
                    w.println("Yes");
                else
                    w.println("No");
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

