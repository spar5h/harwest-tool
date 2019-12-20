import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.io.IOException;
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
        ETestsForProblemD solver = new ETestsForProblemD();
        solver.solve(1, in, out);
        out.close();
    }

    static class ETestsForProblemD {
        ArrayList<Integer>[] adj;
        int[] resl;
        int[] resr;
        int[] sub;
        int[] child;

        void fillsub(int x, int p) {
            sub[x]++;
            for (int y : adj[x]) {
                if (y != p) {
                    child[x]++;
                    fillsub(y, x);
                    sub[x] += sub[y];
                }
            }
        }

        void recur(int x, int l, int m, int p) {
            resl[x] = l;
            resr[x] = m + child[x];
            int c = 1;
            int s = 0;
            for (int y : adj[x]) {
                if (y != p) {
                    recur(y, resr[x] - c, resr[x] - c + 2 * s + 2, x);
                    c++;
                    s += sub[y];
                }
            }
        }

        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt();
            adj = new ArrayList[n];
            for (int i = 0; i < n; i++)
                adj[i] = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) {
                int u = s.nextInt() - 1, v = s.nextInt() - 1;
                adj[u].add(v);
                adj[v].add(u);
            }
            child = new int[n];
            sub = new int[n];
            fillsub(0, -1);
            resl = new int[n];
            resr = new int[n];
            recur(0, 0, 1, -1);
            for (int i = 0; i < n; i++)
                w.println((resl[i] + 1) + " " + (resr[i] + 1));
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

