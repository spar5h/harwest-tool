import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.InputMismatchException;
import java.io.IOException;
import java.util.Queue;
import java.util.LinkedList;
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
        DPaintTheTree solver = new DPaintTheTree();
        solver.solve(1, in, out);
        out.close();
    }

    static class DPaintTheTree {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt();
            long[][] a = new long[3][n];
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < n; j++)
                    a[i][j] = s.nextLong();
            int[] level = new int[n];
            Arrays.fill(level, -1);
            ArrayList<Integer>[] adj = new ArrayList[n];
            for (int i = 0; i < n; i++)
                adj[i] = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) {
                int u = s.nextInt() - 1, v = s.nextInt() - 1;
                adj[u].add(v);
                adj[v].add(u);
            }
            Queue<Integer> q = new LinkedList<>();
            int root = -1;
            for (int i = 0; i < n; i++)
                if (adj[i].size() == 1)
                    root = i;
            q.add(root);
            level[root] = 0;
            boolean res = true;
            while (!q.isEmpty()) {
                int count = 0;
                int x = q.poll();
                for (int y : adj[x]) {
                    if (level[y] == -1) {
                        level[y] = (level[x] + 1) % 3;
                        count++;
                        q.add(y);
                    }
                }
                if (count > 1)
                    res = false;
            }
            if (!res) {
                w.println(-1);
                return;
            }
            long[][] value = new long[3][3];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < 3; j++)
                    value[level[i]][j] += a[j][i];
            }
            long best = Long.MAX_VALUE;
            int[] x = new int[3];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (i == j)
                        continue;
                    for (int k = 0; k < 3; k++) {
                        if (i == k || j == k)
                            continue;
                        if (best > value[0][i] + value[1][j] + value[2][k]) {
                            best = value[0][i] + value[1][j] + value[2][k];
                            x[0] = i;
                            x[1] = j;
                            x[2] = k;
                        }
                    }
                }
            }
            w.println(best);
            for (int i = 0; i < n; i++)
                w.print((x[level[i]] + 1) + " ");
            w.println();
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

