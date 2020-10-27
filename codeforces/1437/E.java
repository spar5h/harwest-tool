import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.io.IOException;
import java.util.Comparator;
import java.util.Collections;
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
        EMakeItIncreasing solver = new EMakeItIncreasing();
        solver.solve(1, in, out);
        out.close();
    }

    static class EMakeItIncreasing {
        int[] tree;

        void update(int n, int nL, int nR, int idx, int v) {
            if (nR < idx || idx < nL)
                return;
            if (nL == idx && nR == idx) {
                tree[n] = v;
                return;
            }
            update(2 * n + 1, nL, (nL + nR) / 2, idx, v);
            update(2 * n + 2, (nL + nR) / 2 + 1, nR, idx, v);
            tree[n] = Math.max(tree[2 * n + 1], tree[2 * n + 2]);
        }

        int query(int n, int nL, int nR, int l, int r) {
            if (nR < l || r < nL)
                return 0;
            if (l <= nL && nR <= r)
                return tree[n];
            return Math.max(query(2 * n + 1, nL, (nL + nR) / 2, l, r), query(2 * n + 2, (nL + nR) / 2 + 1, nR, l, r));
        }

        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt(), k = s.nextInt();
            int[] dead = new int[n];
            int[] a = new int[n];
            for (int i = 0; i < n; i++)
                a[i] = s.nextInt() - i;
            int[] b = new int[k];
            for (int i = 0; i < k; i++) {
                b[i] = s.nextInt() - 1;
                dead[b[i]] = 1;
                if (i >= 1 && a[b[i - 1]] > a[b[i]]) {
                    w.println(-1);
                    return;
                }
            }
            tree = new int[4 * n];
            ArrayList<Pair> p = new ArrayList<>();
            int l = 0;
            while (l < n) {
                if (dead[l] == 1) {
                    l++;
                    continue;
                }
                int r = l;
                while (r < n && dead[r] == 0)
                    r++;
                r--;
                int min = Integer.MIN_VALUE, max = Integer.MAX_VALUE;
                if (l - 1 >= 0)
                    min = a[l - 1];
                if (r + 1 < n)
                    max = a[r + 1];
                p.add(new Pair(l, r, min, max));
                l = r + 1;
            }
            int res = 0;
            for (Pair x : p) {
                ArrayList<Integer> f = new ArrayList<>();
                for (int i = x.l; i <= x.r; i++) {
                    if (a[i] >= x.min && a[i] <= x.max) {
                        f.add(i);
                    } else
                        res++;
                }
                Collections.sort(f, new Comparator<Integer>() {
                    public int compare(Integer i1, Integer i2) {
                        if (a[i1] < a[i2])
                            return -1;
                        if (a[i1] > a[i2])
                            return 1;
                        if (i1 < i2)
                            return -1;
                        if (i1 > i2)
                            return 1;
                        return 0;
                    }
                });
                int m = f.size();
                int best = 0;
                for (int j = 0; j < m; j++) {
                    int idx = f.get(j);
                    int val = query(0, 0, n - 1, 0, idx) + 1;
                    best = Math.max(val, best);
                    update(0, 0, n - 1, idx, val);
                }
                res += m - best;
                for (int j = 0; j < m; j++) {
                    int idx = f.get(j);
                    update(0, 0, n - 1, idx, 0);
                }
            }
            w.println(res);
        }

        class Pair {
            int l;
            int r;
            int min;
            int max;

            Pair(int l, int r, int min, int max) {
                this.l = l;
                this.r = r;
                this.min = min;
                this.max = max;
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

