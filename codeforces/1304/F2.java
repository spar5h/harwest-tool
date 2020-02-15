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
        F2AnimalObservationHardVersion solver = new F2AnimalObservationHardVersion();
        solver.solve(1, in, out);
        out.close();
    }

    static class F2AnimalObservationHardVersion {
        long[] tree;
        long[] lazy;
        int m;
        int k;

        void pushLazy(int n, int nL, int nR) {
            tree[n] += lazy[n];
            if (nL != nR) {
                lazy[2 * n + 1] += lazy[n];
                lazy[2 * n + 2] += lazy[n];
            }
            lazy[n] = 0;
        }

        void update(int l, int r, long val) {
            updateUtil(0, 0, m - k, l, r, val);
        }

        void updateUtil(int n, int nL, int nR, int l, int r, long x) {
            pushLazy(n, nL, nR);
            if (nR < l || r < nL)
                return;
            if (l <= nL && nR <= r) {
                lazy[n] += x;
                pushLazy(n, nL, nR);
                return;
            }
            updateUtil(2 * n + 1, nL, (nL + nR) / 2, l, r, x);
            updateUtil(2 * n + 2, (nL + nR) / 2 + 1, nR, l, r, x);
            tree[n] = Math.max(tree[2 * n + 1], tree[2 * n + 2]);
        }

        long query(int l, int r) {
            return queryUtil(0, 0, m - k, l, r);
        }

        long queryUtil(int n, int nL, int nR, int l, int r) {
            pushLazy(n, nL, nR);
            if (nR < l || r < nL)
                return 0;
            if (l <= nL && nR <= r) {
                return tree[n];
            }
            long ret = Math.max(queryUtil(2 * n + 1, nL, (nL + nR) / 2, l, r),
                    queryUtil(2 * n + 2, (nL + nR) / 2 + 1, nR, l, r));
            tree[n] = Math.max(tree[2 * n + 1], tree[2 * n + 2]);
            return ret;
        }

        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt();
            m = s.nextInt();
            k = s.nextInt();
            long[][] a = new long[n + 2][m];
            long[][] pre = new long[n + 2][m];
            for (int i = n; i >= 1; i--) {
                for (int j = 0; j < m; j++) {
                    a[i][j] = s.nextInt();
                    pre[i][j] = a[i][j];
                    if (j - 1 >= 0)
                        pre[i][j] += pre[i][j - 1];
                }
            }
            //pad first and last layer for convenience
            long[][] dp = new long[n + 2][m - k + 1];
            for (int j = 0; j < m - k + 1; j++) {
                dp[0][j] = pre[1][j + k - 1];
                if (j - 1 >= 0)
                    dp[0][j] -= pre[1][j - 1];
            }
        /*
        for(int j = 0; j < m - k + 1; j++)
            w.print(dp[0][j] + " ");
        w.println();
        */
            int sz = (int) Math.pow(2, (int) Math.ceil(Math.log(m - k + 1) / Math.log(2)) + 1);
            tree = new long[sz];
            lazy = new long[sz];
            for (int i = 1; i <= n; i++) {
                Arrays.fill(tree, 0);
                Arrays.fill(lazy, 0);
                for (int j = 0; j < m - k + 1; j++)
                    update(j, j, dp[i - 1][j]);
                for (int j = 0; j < k; j++)
                    update(0, j, -a[i][j]);
            /*
            w.println("query: " + 0);
            for(int j = 0; j < m - k + 1; j++)
                w.print(query(j, j) + " ");
            w.println();
            */
                dp[i][0] = query(0, m - k) + pre[i][k - 1] + pre[i + 1][k - 1];
                for (int j = 1; j < m - k + 1; j++) {
                    int l = Math.max((j - 1) - (k - 1), 0);
                    int r = (j - 1);
                    update(l, r, a[i][j - 1]);
                    l = j;
                    r = j + k - 1;
                    update(l, r, -a[i][j + k - 1]);
                    dp[i][j] = query(0, m - k) + pre[i][j + k - 1] + pre[i + 1][j + k - 1] - pre[i][j - 1] - pre[i + 1][j - 1];
                /*
                w.println("query: " + j);
                for(int z = 0; z < m - k + 1; z++)
                    w.print(query(z, z) + " ");
                w.println();
                */
                }
            /*
            for(int j = 0; j < m - k + 1; j++)
                w.print(dp[i][j] + " ");
            w.println();
            */
            }
            long res = 0;
            for (int j = 0; j < m - k + 1; j++)
                res = Math.max(dp[n - 1][j], res);
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

