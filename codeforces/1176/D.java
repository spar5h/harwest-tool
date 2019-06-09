import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.AbstractMap;
import java.util.TreeMap;
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
        DRecoverIt solver = new DRecoverIt();
        solver.solve(1, in, out);
        out.close();
    }

    static class DRecoverIt {
        void addMap(TreeMap<Integer, Integer> hm, int x) {
            if (hm.get(x) != null)
                hm.put(x, hm.get(x) + 1);
            else
                hm.put(x, 1);
        }

        void subMap(TreeMap<Integer, Integer> hm, int x) {

            if (hm.get(x) != 1)
                hm.put(x, hm.get(x) - 1);
            else
                hm.remove(x);
        }

        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt();
            int k = 2750131;
            int[] spf = new int[k + 1];
            for (int i = 1; i <= k; i++)
                spf[i] = i;
            for (int i = 2; i <= k; i++) {
                if (spf[i] != i)
                    continue;
                for (int j = 2; (long) i * j <= k; j++)
                    if (spf[i * j] == i * j)
                        spf[i * j] = i;
            }
            int[] primes = new int[200000 + 1];
            int[] map = new int[k + 1];
            int c = 1;
            for (int i = 2; i <= k; i++) {
                if (spf[i] == i) {
                    primes[c] = i;
                    map[i] = c;
                    c++;
                } else
                    map[i] = i / spf[i];
            }

            TreeMap<Integer, Integer> tm = new TreeMap<Integer, Integer>();
            for (int i = 0; i < 2 * n; i++)
                addMap(tm, s.nextInt());

            while (!tm.isEmpty()) {
                int x = tm.lastKey();
                if (spf[x] == x)
                    w.print(map[x] + " ");
                else
                    w.print(x + " ");
                subMap(tm, x);
                subMap(tm, map[x]);
            }
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

