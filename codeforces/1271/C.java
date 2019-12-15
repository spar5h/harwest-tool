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
        CShawarmaTent solver = new CShawarmaTent();
        solver.solve(1, in, out);
        out.close();
    }

    static class CShawarmaTent {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt(), sx = s.nextInt(), sy = s.nextInt();
            int[] a = new int[4]; //u, d, l, r
            for (int i = 0; i < n; i++) {
                int x = s.nextInt(), y = s.nextInt();
                if (y > sy)
                    a[0]++;
                if (y < sy)
                    a[1]++;
                if (x < sx)
                    a[2]++;
                if (x > sx)
                    a[3]++;
            }
            int dx = -1, dy = -1;
            int res = -1;
            if (res < a[0] && sy + 1 <= (int) 1e9) {
                res = a[0];
                dx = sx;
                dy = sy + 1;
            }
            if (res < a[1] && sy - 1 >= 0) {
                res = a[1];
                dx = sx;
                dy = sy - 1;
            }
            if (res < a[2] && sx - 1 >= 0) {
                res = a[2];
                dx = sx - 1;
                dy = sy;
            }
            if (res < a[3] && sx + 1 <= (int) 1e9) {
                res = a[3];
                dx = sx + 1;
                dy = sy;
            }
            w.println(res);
            w.println(dx + " " + dy);
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

