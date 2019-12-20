import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.io.IOException;
import java.util.TreeSet;
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
        DSegmentTree solver = new DSegmentTree();
        solver.solve(1, in, out);
        out.close();
    }

    static class DSegmentTree {
        int[] vis;
        ArrayList<Integer>[] adj;

        void dfs(int x) {
            vis[x] = 1;
            for (int y : adj[x])
                if (vis[y] == 0)
                    dfs(y);
        }

        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt();
            int[] map = new int[2 * n];
            int[] idx = new int[2 * n];
            Arrays.fill(map, -1);
            Arrays.fill(idx, -1);
            for (int i = 0; i < n; i++) {
                int l = s.nextInt() - 1, r = s.nextInt() - 1;
                map[l] = r;
                idx[l] = i;
                idx[r] = i;
            }
            int edgecount = 0;
            TreeSet<Integer> ts = new TreeSet<>();
            adj = new ArrayList[n];
            for (int i = 0; i < n; i++)
                adj[i] = new ArrayList<>();
            for (int i = 0; i < 2 * n; i++) {
                if (map[i] == -1)
                    continue;
                while (!ts.isEmpty() && ts.first() < i)
                    ts.remove(ts.first());
                ts.add(map[i]);
                int x = map[i] - 1;
                while (ts.floor(x) != null) {
                    x = ts.floor(x);
                    adj[idx[i]].add(idx[x]);
                    adj[idx[x]].add(idx[i]);
                    edgecount++;
                    x = x - 1;
                    if (edgecount >= n) {
                        w.println("NO");
                        return;
                    }
                }
            }
            if (edgecount < n - 1) {
                w.println("NO");
                return;
            }
            vis = new int[n];
            int count = 0;
            for (int i = 0; i < n; i++) {
                if (vis[i] == 0) {
                    dfs(i);
                    count++;
                }
            }
            if (count == 1)
                w.println("YES");
            else
                w.println("NO");
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

