import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.io.IOException;
import java.util.ArrayList;
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
        EDivideSquare solver = new EDivideSquare();
        solver.solve(1, in, out);
        out.close();
    }

    static class EDivideSquare {
        int[] tree;

        void update(int n, int nL, int nR, int idx, int v) {
            if (nR < idx || nL > idx)
                return;
            if (nL == nR) {
                tree[n] += v;
                return;
            }
            update(2 * n + 1, nL, (nL + nR) / 2, idx, v);
            update(2 * n + 2, (nL + nR) / 2 + 1, nR, idx, v);
            tree[n] = tree[2 * n + 1] + tree[2 * n + 2];
        }

        int query(int n, int nL, int nR, int l, int r) {
            if (nR < l || r < nL)
                return 0;
            if (l <= nL && nR <= r)
                return tree[n];
            return query(2 * n + 1, nL, (nL + nR) / 2, l, r) + query(2 * n + 2, (nL + nR) / 2 + 1, nR, l, r);
        }

        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt(), m = s.nextInt();
            int k = (int) 1e6;
            int[][] q = new int[k + 1][2];
            for (int i = 0; i <= k; i++)
                Arrays.fill(q[i], -1);
            for (int i = 0; i < n; i++) {
                int y = s.nextInt();
                q[y][0] = s.nextInt();
                q[y][1] = s.nextInt();
            }
            ArrayList<Integer>[] up = new ArrayList[k + 1];
            ArrayList<Integer>[] down = new ArrayList[k + 1];
            for (int i = 0; i <= k; i++) {
                up[i] = new ArrayList<>();
                down[i] = new ArrayList<>();
            }
            long res = 1;
            for (int i = 0; i < m; i++) {
                int x = s.nextInt(), d = s.nextInt(), u = s.nextInt();
                if (d == 0)
                    up[u].add(x);
                else
                    down[d].add(x);
                if (d == 0 && u == k)
                    res++;
            }
            tree = new int[4 * (k + 1)];
            for (int i = 1; i <= k; i++)
                for (int j : up[i])
                    update(0, 0, k, j, 1);
            for (int i = 1; i < k; i++) {
                for (int j : down[i]) {
                    update(0, 0, k, j, 1);
                }
                if (q[i][0] != -1) {
                    res += query(0, 0, k, q[i][0], q[i][1]);
                    if (q[i][0] == 0 && q[i][1] == k)
                        res++;
                }
                for (int j : up[i]) {
                    update(0, 0, k, j, -1);
                }
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

