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
        CGeraldAndGiantChess solver = new CGeraldAndGiantChess();
        solver.solve(1, in, out);
        out.close();
    }

    static class CGeraldAndGiantChess {
        long mod = (long) 1e9 + 7;

        long modExp(long x, long y) {
            if (y == 1)
                return x;
            long ret = modExp(x, y / 2);
            ret = ret * ret % mod;
            if (y % 2 == 1)
                ret = ret * x % mod;
            return ret;
        }

        public void solve(int testNumber, InputReader s, PrintWriter w) {
            long[] fact = new long[(int) 2e5 + 1];
            long[] invFact = new long[(int) 2e5 + 1];
            fact[0] = 1;
            invFact[0] = 1;
            for (int i = 1; i <= (int) 2e5; i++) {
                fact[i] = fact[i - 1] * i % mod;
                invFact[i] = modExp(fact[i], mod - 2);
            }
            int n = s.nextInt(), m = s.nextInt(), k = s.nextInt();
            int[] xi = new int[k];
            int[] xj = new int[k];
            for (int i = 0; i < k; i++) {
                xi[i] = s.nextInt();
                xj[i] = s.nextInt();
            }
            int[] indeg = new int[k];
            for (int i = 0; i < k; i++) {
                for (int j = 0; j < k; j++) {
                    if (i != j && xi[i] <= xi[j] && xj[i] <= xj[j]) {
                        indeg[j]++;
                    }
                }
            }
            Queue<Integer> q = new LinkedList<>();
            for (int i = 0; i < k; i++)
                if (indeg[i] == 0)
                    q.add(i);
            long[] dp = new long[k];
            Arrays.fill(dp, -1);
            while (!q.isEmpty()) {
                int x = q.poll();
                for (int i = 0; i < k; i++) {
                    if (dp[i] == -1 && xi[x] <= xi[i] && xj[x] <= xj[i]) {
                        indeg[i]--;
                        if (indeg[i] == 0) {
                            q.add(i);
                        }
                    }
                }
                dp[x] = fact[xi[x] - 1 + xj[x] - 1] * invFact[xi[x] - 1] % mod * invFact[xj[x] - 1] % mod;
                for (int i = 0; i < k; i++) {
                    if (i != x && dp[i] != -1 && xi[x] >= xi[i] && xj[x] >= xj[i]) {
                        dp[x] = (dp[x] - dp[i] * fact[xi[x] - xi[i] + xj[x] - xj[i]] % mod * invFact[xi[x] - xi[i]] % mod * invFact[xj[x] - xj[i]] % mod + mod) % mod;
                    }
                }
            }
            long res = fact[n - 1 + m - 1] * invFact[n - 1] % mod * invFact[m - 1] % mod;
            for (int i = 0; i < k; i++) {
                res = (res - dp[i] * fact[n - xi[i] + m - xj[i]] % mod * invFact[n - xi[i]] % mod * invFact[m - xj[i]] % mod + mod) % mod;
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

