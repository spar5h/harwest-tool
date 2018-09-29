import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.io.IOException;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Comparator;
import java.util.Collections;
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
        TaskG solver = new TaskG();
        solver.solve(1, in, out);
        out.close();
    }

    static class TaskG {
        static int[][] tree;
        static ArrayList<TaskG.pair>[] adj;

        static void build(int i, int n, int nL, int nR) {

            if (nL == nR) {
                tree[i][n] = adj[i].get(nL).i;
                return;
            }

            build(i, 2 * n + 1, nL, (nL + nR) / 2);
            build(i, 2 * n + 2, (nL + nR) / 2 + 1, nR);

            tree[i][n] = tree[i][2 * n + 1] + tree[i][2 * n + 2];
        }

        static int query(int i, int n, int nL, int nR, int l, int r) {

            if (nR < l || r < nL)
                return 0;

            if (l <= nL && nR <= r)
                return tree[i][n];

            return query(i, 2 * n + 1, nL, (nL + nR) / 2, l, r) + query(i, 2 * n + 2, (nL + nR) / 2 + 1, nR, l, r);
        }

        static void update(int i, int n, int nL, int nR, int l, int r) {

            if (nR < l || r < nL)
                return;

            if (l <= nL && nR <= r) {
                tree[i][n]--;
                return;
            }

            update(i, 2 * n + 1, nL, (nL + nR) / 2, l, r);
            update(i, 2 * n + 2, (nL + nR) / 2 + 1, nR, l, r);

            tree[i][n] = tree[i][2 * n + 1] + tree[i][2 * n + 2];
        }

        static int leftIndex(int index, long val) {

            int bs_left = 0, bs_right = adj[index].size() - 1;
            int leftIndex = -1;

            while (bs_left <= bs_right) {

                int mid = (bs_left + bs_right) / 2;

                if (adj[index].get(mid).j >= val) {
                    leftIndex = mid;
                    bs_right = mid - 1;
                } else
                    bs_left = mid + 1;
            }

            return leftIndex;
        }

        static int rightIndex(int index, long val) {

            int bs_left = 0, bs_right = adj[index].size() - 1;
            int rightIndex = -1;

            while (bs_left <= bs_right) {

                int mid = (bs_left + bs_right) / 2;

                if (adj[index].get(mid).j <= val) {
                    rightIndex = mid;
                    bs_left = mid + 1;
                } else
                    bs_right = mid - 1;
            }

            return rightIndex;
        }

        public void solve(int testNumber, InputReader s, PrintWriter w) {

            int n = s.nextInt(), k = s.nextInt();

            TreeSet<Long> ts = new TreeSet<Long>();

            long[] x = new long[n];
            long[] r = new long[n];
            long[] q = new long[n];

            for (int i = 0; i < n; i++) {
                x[i] = s.nextLong();
                r[i] = s.nextLong();
                q[i] = s.nextLong();
                ts.add(q[i]);
            }

            int m = ts.size();

            long[] val = new long[m];

            int c = 0;

            for (long i : ts)
                val[c++] = i;

            HashMap<Long, Integer> map = new HashMap<Long, Integer>();

            for (int i = 0; i < m; i++)
                map.put(val[i], i);

            TreeMap<Long, Integer>[] tm = new TreeMap[m];

            for (int i = 0; i < m; i++)
                tm[i] = new TreeMap<Long, Integer>();

            for (int i = 0; i < n; i++) {

                int index = map.get(q[i]);

                if (tm[index].get(x[i]) == null)
                    tm[index].put(x[i], 1);
                else
                    tm[index].put(x[i], tm[index].get(x[i]) + 1);
            }

            adj = new ArrayList[m];

            for (int i = 0; i < m; i++) {

                adj[i] = new ArrayList<TaskG.pair>();

                for (Map.Entry<Long, Integer> e : tm[i].entrySet())
                    adj[i].add(new TaskG.pair(e.getValue(), e.getKey()));
            }

            tree = new int[m][];

            for (int i = 0; i < m; i++) {

                int temp = (int) Math.pow(2, (int) Math.ceil(Math.log(adj[i].size()) / Math.log(2)) + 1);
                tree[i] = new int[temp];

                build(i, 0, 0, adj[i].size() - 1);
            }

            ArrayList<TaskG.pair> list = new ArrayList<TaskG.pair>();

            for (int i = 0; i < n; i++)
                list.add(new TaskG.pair(i, r[i]));

            Collections.sort(list, new TaskG.comp());

            long res = 0;

            for (int i = 0; i < list.size(); i++) {

                int personIndex = list.get(i).i;

                long iq = q[personIndex];
                int index = map.get(iq);

                int pos = leftIndex(index, x[personIndex]);
                update(index, 0, 0, adj[index].size() - 1, pos, pos);

                for (int j = index; j < m; j++) {

                    if (val[j] > iq + k)
                        break;

                    int leftIndex = leftIndex(j, x[personIndex] - r[personIndex]);
                    int rightIndex = rightIndex(j, x[personIndex] + r[personIndex]);

                    if (leftIndex != -1 && rightIndex != -1 && leftIndex <= rightIndex)
                        res += query(j, 0, 0, adj[j].size() - 1, leftIndex, rightIndex);
                }

                for (int j = index - 1; j >= 0; j--) {

                    if (val[j] < iq - k)
                        break;

                    int leftIndex = leftIndex(j, x[personIndex] - r[personIndex]);
                    int rightIndex = rightIndex(j, x[personIndex] + r[personIndex]);

                    if (leftIndex != -1 && rightIndex != -1 && leftIndex <= rightIndex)
                        res += query(j, 0, 0, adj[j].size() - 1, leftIndex, rightIndex);
                }

            }

            w.println(res);
        }

        static class pair {
            int i;
            long j;

            pair(int i, long j) {
                this.i = i;
                this.j = j;
            }

        }

        static class comp implements Comparator<TaskG.pair> {
            public int compare(TaskG.pair x, TaskG.pair y) {

                if (x.j < y.j)
                    return -1;

                if (x.j > y.j)
                    return 1;

                return 0;
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

