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
        DSecretPasswords solver = new DSecretPasswords();
        solver.solve(1, in, out);
        out.close();
    }

    static class DSecretPasswords {
        int[] vis;
        int[][] adj;

        void dfs(int x) {
            vis[x] = 1;
            for (int y = 0; y < 26; y++) {
                if (adj[x][y] == 1 && vis[y] == 0)
                    dfs(y);
            }
        }

        public void solve(int testNumber, InputReader s, PrintWriter w) {
            adj = new int[26][26];
            for (int i = 0; i < 26; i++)
                adj[i][i] = 1;
            int[] used = new int[26];
            int n = s.nextInt();
            while (n-- > 0) {
                char[] c = s.next().toCharArray();
                int[] f = new int[26];
                for (int i = 0; i < c.length; i++)
                    f[c[i] - 'a'] = 1;
                for (int i = 0; i < 26; i++) {
                    if (f[i] == 0)
                        continue;
                    used[i] = 1;
                    for (int j = 0; j < 26; j++) {
                        if (f[j] == 0)
                            continue;
                        adj[i][j] = 1;
                    }
                }
            }
            vis = new int[26];
            int cnt = 0;
            for (int i = 0; i < 26; i++) {
                if (used[i] == 0)
                    continue;
                if (vis[i] == 0) {
                    cnt++;
                    dfs(i);
                }
            }
            w.println(cnt);
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

        public String nextString() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            StringBuilder res = new StringBuilder();
            do {
                if (Character.isValidCodePoint(c)) {
                    res.appendCodePoint(c);
                }
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
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

        public String next() {
            return nextString();
        }

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);

        }

    }
}

