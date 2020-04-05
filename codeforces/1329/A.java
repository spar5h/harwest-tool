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
        ADreamoonLikesColoring solver = new ADreamoonLikesColoring();
        solver.solve(1, in, out);
        out.close();
    }

    static class ADreamoonLikesColoring {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt(), m = s.nextInt();
            int[] l = new int[m];
            for (int i = 0; i < m; i++) {
                l[i] = s.nextInt();
            }
            boolean check = true;
            int len = 0;
            for (int i = 0; i < m; i++) {
                if (l[i] > n - i)
                    check = false;
                len = Math.max(i + l[i], len);
            }
            if (!check) {
                w.println("-1");
                return;
            }
            int[] suf = new int[m];
            suf[m - 1] = l[m - 1];
            for (int i = m - 2; i >= 0; i--) {
                suf[i] = Math.max(l[i], suf[i + 1] + 1);
            }
            int[] ex = new int[m];
            int curr = 0;
            int z = 0;
            while (len < n && curr < m - 1) {
                int c = 1;
                z++;
                while (len < n && c < l[curr]) {
                    c++;
                    z++;
                    len = Math.max(z + suf[curr + 1], len);
                    ex[curr + 1]++;
                }
                curr++;
            }

            if (len != n) {
                w.println("-1");
                return;
            }
            for (int i = 1; i < m; i++) {
                ex[i] += ex[i - 1];
            }
            int[] res = new int[m];
            for (int i = 0; i < m; i++) {
                res[i] = i + ex[i] + 1;
            }
            for (int i = 0; i < m; i++)
                w.print(res[i] + " ");
            w.println();
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

