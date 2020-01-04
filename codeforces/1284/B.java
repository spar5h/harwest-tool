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
        BNewYearAndAscentSequence solver = new BNewYearAndAscentSequence();
        solver.solve(1, in, out);
        out.close();
    }

    static class BNewYearAndAscentSequence {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt();
            int[][] a = new int[n][];
            int[] asc = new int[n];
            int m = 0;
            for (int i = 0; i < n; i++) {
                int l = s.nextInt();
                a[i] = new int[l];
                for (int j = 0; j < l; j++)
                    a[i][j] = s.nextInt();
                for (int j = 0; j < l - 1; j++)
                    if (a[i][j] < a[i][j + 1])
                        asc[i] = 1;
                m += 1 - asc[i];
            }
            long res = (long) n * n - (long) m * m;
            Integer[] min = new Integer[m];
            Integer[] max = new Integer[m];
            int c = 0;
            for (int i = 0; i < n; i++) {
                if (asc[i] == 1)
                    continue;
                min[c] = a[i][a[i].length - 1];
                max[c] = a[i][0];
                c++;
            }
            Arrays.sort(max);
            for (int i = 0; i < m; i++) {
                int x = min[i];
                int left = 0, right = m - 1;
                int ans = 0;
                while (left <= right) {
                    int mid = (left + right) / 2;
                    if (x >= max[mid].intValue()) {
                        left = mid + 1;
                        ans = mid + 1;
                    } else
                        right = mid - 1;
                }
                res += m - ans;
            }
            w.println(res);
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

