import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.io.IOException;
import java.util.Comparator;
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
        EAntennaCoverage solver = new EAntennaCoverage();
        solver.solve(1, in, out);
        out.close();
    }

    static class EAntennaCoverage {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt(), m = s.nextInt();
            int[] pos = new int[n];
            int[] scope = new int[n];
            for (int i = 0; i < n; i++) {
                pos[i] = s.nextInt();
                scope[i] = s.nextInt();
            }
            Integer[] a = new Integer[n];
            for (int i = 0; i < n; i++)
                a[i] = i;
            Arrays.sort(a, new Comparator<Integer>() {

                public int compare(Integer o1, Integer o2) {
                    if (pos[o1] < pos[o2])
                        return -1;
                    if (pos[o1] > pos[o2])
                        return 1;
                    return 0;
                }
            });
            long[] dp = new long[m + 1];
            Arrays.fill(dp, Integer.MAX_VALUE);
            dp[0] = 0;
            for (int i = 0; i < n; i++) {
                int p = pos[a[i]];
                int sc = scope[a[i]];
                long[] dp0 = new long[m + 1];
                for (int j = 0; j <= m; j++) {
                    dp0[j] = dp[j];
                }
                Arrays.fill(dp, Integer.MAX_VALUE);
                dp[0] = 0;
                for (int j = m; j >= 1; j--) {
                    int left = Math.max(Math.min(p - sc - 1, p - (j - p) - 1), 0);
                    dp[j] = dp0[left] + Math.max(0, (j - (p + sc)));
                    dp[j] = Math.min(dp0[j], dp[j]);
                    dp[j] = Math.min(Math.max(0, Math.max(p - sc - 1, j - (p + sc))), dp[j]);
                }
            }
            w.println(dp[m]);
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

