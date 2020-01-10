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
        DDrEvilUnderscores solver = new DDrEvilUnderscores();
        solver.solve(1, in, out);
        out.close();
    }

    static class DDrEvilUnderscores {
        int[][] adj;
        int next = 1;

        int dfs(int x, int idx, int val) {
            if (idx == -1)
                return val;
            if (adj[0][x] != -1 && adj[1][x] != -1)
                val += (1 << idx);
            int ret = Integer.MAX_VALUE;
            if (adj[0][x] != -1)
                ret = Math.min(dfs(adj[0][x], idx - 1, val), ret);
            if (adj[1][x] != -1)
                ret = Math.min(dfs(adj[1][x], idx - 1, val), ret);
            return ret;
        }

        void insert(int val) {
            int x = 0;
            int idx = 29;
            while (idx >= 0) {
                int v = (val >> idx) & 1;
                if (adj[v][x] == -1) {
                    adj[v][x] = next;
                    next++;
                }
                x = adj[v][x];
                idx--;
            }
        }

        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt();
            adj = new int[2][n * 30 + 1];
            Arrays.fill(adj[0], -1);
            Arrays.fill(adj[1], -1);
            for (int i = 0; i < n; i++)
                insert(s.nextInt());
            w.println(dfs(0, 29, 0));
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

