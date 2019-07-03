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
        FTwoPizzas solver = new FTwoPizzas();
        solver.solve(1, in, out);
        out.close();
    }

    static class FTwoPizzas {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt(), m = s.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                int k = s.nextInt();
                while (k-- > 0)
                    a[i] += (1 << (s.nextInt() - 1));
            }
            int[] b = new int[m];
            long[] c = new long[m];
            for (int i = 0; i < m; i++) {
                c[i] = s.nextLong();
                int k = s.nextInt();
                while (k-- > 0)
                    b[i] += (1 << (s.nextInt() - 1));
            }
            long[][] cost = new long[1 << 9][2];
            int[][] idx = new int[1 << 9][2];
            for (int i = 0; i < (1 << 9); i++) {
                for (int j = 0; j < 2; j++) {
                    cost[i][j] = Long.MAX_VALUE;
                    idx[i][j] = -1;
                }
            }
            for (int i = 0; i < m; i++) {
                if (cost[b[i]][0] > c[i]) {
                    cost[b[i]][1] = cost[b[i]][0];
                    idx[b[i]][1] = idx[b[i]][0];
                    cost[b[i]][0] = c[i];
                    idx[b[i]][0] = i;
                } else if (cost[b[i]][1] > c[i]) {
                    cost[b[i]][1] = c[i];
                    idx[b[i]][1] = i;
                }
            }
            long[] count = new long[1 << 9];
            for (int i = 0; i < n; i++)
                for (int j = 0; j < (1 << 9); j++)
                    if ((a[i] & j) == a[i])
                        count[j]++;
            long best = 0, bestCost = Long.MAX_VALUE;
            int idx0 = -1, idx1 = -1;
            for (int i = 0; i < (1 << 9); i++) {
                if (idx[i][0] == -1)
                    continue;
                if (idx[i][1] != -1 && (best < count[i] || (best == count[i] && bestCost > cost[i][0] + cost[i][1]))) {
                    best = count[i];
                    bestCost = cost[i][0] + cost[i][1];
                    idx0 = idx[i][0];
                    idx1 = idx[i][1];
                }
                for (int j = i + 1; j < (1 << 9); j++) {
                    if (idx[j][0] == -1)
                        continue;
                    if (best < count[i | j] || (best == count[i | j] && bestCost > cost[i][0] + cost[j][0])) {
                        best = count[i | j];
                        bestCost = cost[i][0] + cost[j][0];
                        idx0 = idx[i][0];
                        idx1 = idx[j][0];
                    }
                }
            }
            w.println((idx0 + 1) + " " + (idx1 + 1));
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

