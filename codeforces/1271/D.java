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
        DPortals solver = new DPortals();
        solver.solve(1, in, out);
        out.close();
    }

    static class DPortals {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt(), m = s.nextInt(), k = s.nextInt();
            int[] a = new int[n + 1];
            int[] b = new int[n + 1];
            int[] c = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                a[i] = s.nextInt();
                b[i] = s.nextInt();
                c[i] = s.nextInt();
            }
            int[] r = new int[n + 1];
            for (int i = 1; i <= n; i++)
                r[i] = i;
            for (int i = 0; i < m; i++) {
                int u = s.nextInt(), v = s.nextInt();
                r[v] = Math.max(u, r[v]);
            }
            ArrayList<Integer>[] adj = new ArrayList[n + 1];
            for (int i = 1; i <= n; i++)
                adj[i] = new ArrayList<>();
            for (int i = 1; i <= n; i++)
                adj[r[i]].add(c[i]);
            long[][] pre = new long[n + 1][];
            for (int i = 1; i <= n; i++) {
                Collections.sort(adj[i], new Comparator<Integer>() {

                    public int compare(Integer o1, Integer o2) {
                        if (o1 > o2)
                            return -1;
                        if (o1 < o2)
                            return 1;
                        return 0;
                    }
                });
                pre[i] = new long[adj[i].size() + 1];
                for (int j = 1; j <= adj[i].size(); j++)
                    pre[i][j] = adj[i].get(j - 1) + pre[i][j - 1];
            }
            long[][] dp = new long[n + 1][n + 1];
            boolean[][] valid = new boolean[n + 1][n + 1];
            valid[0][0] = true;
            long[] total = new long[n + 1];
            total[0] = k;
            for (int i = 1; i <= n; i++)
                total[i] = b[i] + total[i - 1];
        /*
        for(int i = 0; i <= n; i++)
            w.print(total[i] + " ");
        w.println();
        */
            for (int i = 1; i <= n; i++) {
            /*
            w.println(i + " //");
            for(int z = 0; z < pre[i].length; z++)
                w.print(pre[i][z] + " ");
            w.println();
            */
                for (int j = 0; j <= n; j++) {
                    for (int z = 0; z < pre[i].length; z++) {
                        if (j >= z && valid[i - 1][j - z] && total[i - 1] - (j - z) >= a[i]) {
                            dp[i][j] = Math.max(dp[i - 1][j - z] + pre[i][z], dp[i][j]);
                            valid[i][j] = true;
                        }
                    }
                }
            }
            long res = -1;
            for (int i = 0; i <= n; i++)
                if (valid[n][i])
                    res = Math.max(dp[n][i], res);
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

