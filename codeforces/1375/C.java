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
        CElementExtermination solver = new CElementExtermination();
        solver.solve(1, in, out);
        out.close();
    }

    static class CElementExtermination {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int t = s.nextInt();
            while (t-- > 0) {
                int n = s.nextInt();
                int[] a = new int[n];
                for (int i = 0; i < n; i++)
                    a[i] = s.nextInt();
                int[] pre = new int[n];
                int[] suf = new int[n];
                int max = 0;
                int min = n + 1;
                for (int i = 0; i < n; i++) {
                    if (max < a[i])
                        pre[i] = 1;
                    if (min > a[i]) {
                        min = a[i];
                        max = Math.max(a[i], max);
                    }
                }
                max = 0;
                min = n + 1;
                for (int i = n - 1; i >= 0; i--) {
                    if (min > a[i]) {
                        suf[i] = 1;
                    }
                    if (max < a[i]) {
                        max = a[i];
                        min = Math.min(a[i], min);
                    }
                }
                boolean check = false;
                for (int i = 0; i < n; i++) {
                    if (pre[i] == 1 && suf[i] == 1)
                        check = true;
                }
                if (check)
                    w.println("YES");
                else
                    w.println("NO");
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

