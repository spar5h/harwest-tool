import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
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
        DStepsToOne solver = new DStepsToOne();
        solver.solve(1, in, out);
        out.close();
    }

    static class DStepsToOne {
        long modExp(long x, long y, long mod) {
            if (y == 0)
                return 1;
            if (y == 1)
                return x % mod;
            long ret = modExp(x, y / 2, mod);
            ret = ret * ret % mod;
            if (y % 2 == 1)
                ret = ret * (x % mod) % mod;
            return ret;
        }

        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt();
            if (n == 1) {
                w.println(1);
                return;
            }
            ArrayList<Integer>[] adj = new ArrayList[n + 1];
            for (int i = 1; i <= n; i++)
                adj[i] = new ArrayList<>();
            for (int i = 1; i <= n; i++) {
                for (int j = 1; (long) i * j <= n; j++) {
                    adj[i * j].add(i);
                }
            }
            long mod = (long) 1e9 + 7;
            long invn = modExp(n, mod - 2, mod);
            long res = 1;
            long[] e = new long[n + 1];
            for (int i = 2; i <= n; i++) {
                long[] c = new long[adj[i].size()];
                HashMap<Integer, Integer> hm = new HashMap<>();
                for (int j : adj[i]) {
                    c[hm.size()] = n / j;
                    hm.put(j, hm.size());
                }
                e[i] = 1;
                for (int iter = adj[i].size() - 1; iter >= 0; iter--) {
                    int j = adj[i].get(iter);
                    for (int k : adj[j]) {
                        if (k != j)
                            c[hm.get(k)] -= c[iter];
                    }
                    if (iter != adj[i].size() - 1)
                        e[i] = (e[i] + e[j] * c[hm.get(j)] % mod * invn % mod) % mod;
                }
                e[i] = n * modExp(n - c[adj[i].size() - 1], mod - 2, mod) % mod * e[i] % mod;
                res = (res + e[i] * invn % mod) % mod;
            }
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

