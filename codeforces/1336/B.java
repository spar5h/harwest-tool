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
        BXeniaAndColorfulGems solver = new BXeniaAndColorfulGems();
        solver.solve(1, in, out);
        out.close();
    }

    static class BXeniaAndColorfulGems {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int t = s.nextInt();
            while (t-- > 0) {
                int[] n = new int[3];
                for (int i = 0; i < 3; i++)
                    n[i] = s.nextInt();
                Long[][] a = new Long[3][];
                for (int i = 0; i < 3; i++) {
                    a[i] = new Long[n[i]];
                    for (int j = 0; j < n[i]; j++)
                        a[i][j] = s.nextLong();
                    Arrays.sort(a[i]);
                }
                long res = Long.MAX_VALUE;
                for (int i = 0; i < 3; i++) {
                    int l = 0, r = 0;
                    int il = (i + 1) % 3;
                    int ir = (i + 2) % 3;
                    for (int j = 0; j < n[i]; j++) {
                        while (l < n[il] && a[il][l] <= a[i][j])
                            l++;
                        l--;
                        while (r < n[ir] && a[ir][r] < a[i][j])
                            r++;
                        if (l >= 0 && r < n[ir]) {
                            long x = a[il][l];
                            long y = a[ir][r];
                            long z = a[i][j];
                            res = Math.min((x - y) * (x - y) + (y - z) * (y - z) + (z - x) * (z - x), res);
                        }
                        l++;
                    }
                    l = 0;
                    r = 0;
                    il = (i + 2) % 3;
                    ir = (i + 1) % 3;
                    for (int j = 0; j < n[i]; j++) {
                        while (l < n[il] && a[il][l] <= a[i][j])
                            l++;
                        l--;
                        while (r < n[ir] && a[ir][r] < a[i][j])
                            r++;
                        if (l >= 0 && r < n[ir]) {
                            long x = a[il][l];
                            long y = a[ir][r];
                            long z = a[i][j];
                            res = Math.min((x - y) * (x - y) + (y - z) * (y - z) + (z - x) * (z - x), res);
                        }
                        l++;
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

