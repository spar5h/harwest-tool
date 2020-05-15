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
        COracAndGameOfLife solver = new COracAndGameOfLife();
        solver.solve(1, in, out);
        out.close();
    }

    static class COracAndGameOfLife {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt(), m = s.nextInt(), t = s.nextInt();
            int[][] start = new int[n][m];
            for (int i = 0; i < n; i++)
                Arrays.fill(start[i], -1);
            int[][] a = new int[n][m];
            for (int i = 0; i < n; i++) {
                char[] c = s.next().toCharArray();
                for (int j = 0; j < m; j++)
                    a[i][j] = c[j] - '0';
            }
            Queue<Pair> q = new LinkedList<>();
            int[] di = {-1, 1, 0, 0};
            int[] dj = {0, 0, -1, 1};
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    boolean check = false;
                    for (int k = 0; k < 4; k++) {
                        if (i + di[k] >= 0 && i + di[k] < n && j + dj[k] >= 0 && j + dj[k] < m && a[i + di[k]][j + dj[k]] == a[i][j]) {
                            start[i][j] = 0;
                            q.add(new Pair(i, j));
                        }
                    }
                }
            }
            while (!q.isEmpty()) {
                Pair x = q.poll();
                int i = x.i, j = x.j;
                for (int k = 0; k < 4; k++) {
                    if (i + di[k] >= 0 && i + di[k] < n && j + dj[k] >= 0 && j + dj[k] < m && start[i + di[k]][j + dj[k]] == -1 && a[i + di[k]][j + dj[k]] != a[i][j]) {
                        start[i + di[k]][j + dj[k]] = start[i][j] + 1;
                        q.add(new Pair(i + di[k], j + dj[k]));
                    }
                }
            }
            while (t-- > 0) {
                int i = s.nextInt() - 1, j = s.nextInt() - 1;
                long p = s.nextLong();
                if (start[i][j] == -1 || p <= start[i][j]) {
                    w.println(a[i][j]);
                } else {
                    w.println((a[i][j] + (p - start[i][j]) % 2) % 2);
                }
            }
        }

        class Pair {
            int i;
            int j;

            Pair(int i, int j) {
                this.i = i;
                this.j = j;
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

