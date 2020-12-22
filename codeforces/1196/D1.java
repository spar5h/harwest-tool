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
        D2RGBSubstringHardVersion solver = new D2RGBSubstringHardVersion();
        solver.solve(1, in, out);
        out.close();
    }

    static class D2RGBSubstringHardVersion {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int q = s.nextInt();
            while (q-- > 0) {
                int n = s.nextInt(), k = s.nextInt();
                String str = s.next();
                int[] a = new int[n];
                for (int i = 0; i < n; i++) {
                    if (str.charAt(i) == 'R')
                        a[i] = 0;
                    else if (str.charAt(i) == 'G')
                        a[i] = 1;
                    else
                        a[i] = 2;
                }

                int res = Integer.MAX_VALUE;

                for (int inc = 0; inc < 3; inc++) {
                    int r = 0;
                    int curr = 0;
                    while (r < k) {
                        curr += a[r] != (r + inc) % 3 ? 1 : 0;
                        r++;
                    }
                    res = Math.min(curr, res);
                    while (r < n) {
                        curr += a[r] != (r + inc) % 3 ? 1 : 0;
                        curr -= a[r - k] != (r - k + inc) % 3 ? 1 : 0;
                        res = Math.min(curr, res);
                        r++;
                    }
                }

                w.println(res);
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

        public String nextString() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            StringBuilder res = new StringBuilder();
            do {
                if (Character.isValidCodePoint(c)) {
                    res.appendCodePoint(c);
                }
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
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

        public String next() {
            return nextString();
        }

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);

        }

    }
}

