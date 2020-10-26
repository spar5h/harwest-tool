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
        BBoboniuWalksOnGraph solver = new BBoboniuWalksOnGraph();
        solver.solve(1, in, out);
        out.close();
    }

    static class BBoboniuWalksOnGraph {
        int[] offset;
        int[][] mat;

        int recur(int idx, int[] a) {
            if (idx == 0) {
                return 1;
            }
            int ret = 0;
            for (int i = 0; i < idx; i++) {
                int x = offset[idx] + i;
                if (a[x] > 0 || mat[x][x] == 1)
                    continue;
                a[x]++;
                for (int j = 0; j < mat[x].length; j++)
                    a[j] += mat[x][j];
                ret += recur(idx - 1, a);
                a[x]--;
                for (int j = 0; j < mat[x].length; j++)
                    a[j] -= mat[x][j];
            }
            return ret;
        }

        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt(), m = s.nextInt(), k = s.nextInt();
            ArrayList<Integer>[] adj = new ArrayList[n];
            for (int i = 0; i < n; i++)
                adj[i] = new ArrayList<>();
            Edge[] edge = new Edge[m];
            for (int i = 0; i < m; i++) {
                edge[i] = new Edge(s.nextInt() - 1, s.nextInt() - 1, s.nextInt());
                adj[edge[i].u].add(i);
            }
            for (int i = 0; i < n; i++) {
                Collections.sort(adj[i], new Comparator<Integer>() {
                    public int compare(Integer i1, Integer i2) {
                        if (edge[i1].w < edge[i2].w)
                            return -1;
                        if (edge[i1].w > edge[i2].w)
                            return 1;
                        return 0;
                    }
                });
            }
            offset = new int[k + 1];
            for (int i = 2; i <= k; i++)
                offset[i] = offset[i - 1] + i - 1;
            int[][] flag = new int[n][k * (k + 1) / 2];
            ArrayList<Integer>[] col = new ArrayList[n];
            for (int i = 0; i < n; i++)
                col[i] = new ArrayList<>();
            mat = new int[k * (k + 1) / 2][k * (k + 1) / 2];
            for (int u = 0; u < n; u++) {
                for (int i = 0; i < adj[u].size(); i++) {
                    int j = adj[u].get(i);
                    int v = edge[j].v;
                    if (flag[v][offset[adj[u].size()] + i] == 0) {
                        flag[v][offset[adj[u].size()] + i] = 1;
                        col[v].add(offset[adj[u].size()] + i);
                    } else {
                        mat[offset[adj[u].size()] + i][offset[adj[u].size()] + i] = 1;
                    }
                }
            }
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < col[i].size(); j++) {
                    for (int l = j + 1; l < col[i].size(); l++) {
                        mat[col[i].get(j)][col[i].get(l)] = 1;
                        mat[col[i].get(l)][col[i].get(j)] = 1;
                    }
                }
            }
            w.println(recur(k, new int[k * (k + 1) / 2]));
        }

        class Edge {
            int u;
            int v;
            int w;

            Edge(int u, int v, int w) {
                this.u = u;
                this.v = v;
                this.w = w;
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

