import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.io.IOException;
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
        EDivisors solver = new EDivisors();
        solver.solve(1, in, out);
        out.close();
    }

    static class EDivisors {
        int count;
        long[] val;
        ArrayList<Integer>[] adj;

        void dfs(int x, long k, PrintWriter w) {
            if (count == (int) 1e5)
                return;
            if (x == 0 || k == 0) {
                count++;
                w.print(val[x] + " ");
                return;
            }
            for (int y : adj[x]) {
                dfs(y, k - 1, w);
                if (count == (int) 1e5)
                    return;
            }
        }

        public void solve(int testNumber, InputReader s, PrintWriter w) {
            long n = s.nextLong(), k = s.nextLong();
            ArrayList<Long> fact = new ArrayList<Long>();
            for (long i = 1; i * i <= n; i++) {
                if (n % i != 0)
                    continue;
                fact.add(i);
                if (n / i != i)
                    fact.add(n / i);
            }
            Collections.sort(fact);
            int m = fact.size();
            val = new long[m];
            for (int i = 0; i < m; i++)
                val[i] = fact.get(i);
            count = 0;
            adj = new ArrayList[m];
            for (int i = 0; i < m; i++)
                adj[i] = new ArrayList<>();
            for (int i = 0; i < m; i++)
                for (int j = i; j < m; j++)
                    if (fact.get(j) % fact.get(i) == 0)
                        adj[j].add(i);
            dfs(m - 1, k, w);
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

