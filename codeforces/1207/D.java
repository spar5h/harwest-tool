import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.io.IOException;
import java.util.Comparator;
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
        DNumberOfPermutations solver = new DNumberOfPermutations();
        solver.solve(1, in, out);
        out.close();
    }

    static class DNumberOfPermutations {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt();
            long mod = 998244353;
            long[] fact = new long[n + 1];
            fact[0] = 1;
            for (int i = 1; i <= n; i++)
                fact[i] = fact[i - 1] * i % mod;
            Integer[] a = new Integer[n];
            Integer[] b = new Integer[n];
            Pair[] pair = new Pair[n];
            for (int i = 0; i < n; i++) {
                a[i] = s.nextInt();
                b[i] = s.nextInt();
                pair[i] = new Pair(a[i], b[i]);
            }
            Arrays.sort(a);
            Arrays.sort(b);
            Arrays.sort(pair, new Comparator<Pair>() {

                public int compare(Pair p1, Pair p2) {
                    if (p1.a < p2.a)
                        return -1;
                    if (p1.a > p2.a)
                        return 1;
                    if (p1.b < p2.b)
                        return -1;
                    if (p1.b > p2.b)
                        return 1;
                    return 0;
                }
            });
            long res = 0;
            long av = 1;
            int i = 0;
            while (i < n) {
                int j = i;
                while (j < n && a[i].equals(a[j]))
                    j++;
                av = av * fact[j - i] % mod;
                i = j;
            }
            i = 0;
            long bv = 1;
            while (i < n) {
                int j = i;
                while (j < n && b[i].equals(b[j]))
                    j++;
                bv = bv * fact[j - i] % mod;
                i = j;
            }
            long pv = 1;
            for (int j = 0; j < n - 1; j++) {
                if (pair[j].b > pair[j + 1].b)
                    pv = 0;
            }
            i = 0;
            while (i < n) {
                int j = i;
                while (j < n && pair[i].a == pair[j].a && pair[i].b == pair[j].b)
                    j++;
                pv = pv * fact[j - i] % mod;
                i = j;
            }
            res = (res + av) % mod;
            res = (res + bv) % mod;
            res = (res - pv + mod) % mod;
            w.println((fact[n] - res + mod) % mod);
        }

        class Pair {
            int a;
            int b;

            Pair(int a, int b) {
                this.a = a;
                this.b = b;
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

