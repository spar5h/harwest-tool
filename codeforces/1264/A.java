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
        ABeautifulRegionalContest solver = new ABeautifulRegionalContest();
        solver.solve(1, in, out);
        out.close();
    }

    static class ABeautifulRegionalContest {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int t = s.nextInt();
            while (t-- > 0) {
                int n = s.nextInt();
                int[] p = new int[n];
                for (int i = 0; i < n; i++)
                    p[i] = s.nextInt();
                int[] a = new int[3];
                int c = 0;
                for (int i = 0; i < n / 2; i++) {
                    if (i - 1 >= 0 && p[i] != p[i - 1]) {
                        if (c < 2 && (c == 0 || a[c] > a[0]))
                            c++;
                    }
                    a[c]++;
                }
                int i = n / 2 - 1;
                while (i >= 0 && p[i] == p[n / 2]) {
                    a[c]--;
                    i--;
                }
                if (a[0] >= a[1] || a[0] >= a[2] || a[0] == 0 || a[1] == 0 || a[2] == 0) {
                    w.println("0 0 0");
                } else {
                    w.println(a[0] + " " + a[1] + " " + a[2]);
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

