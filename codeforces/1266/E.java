import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
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
        ESpaceshipSolitaire solver = new ESpaceshipSolitaire();
        solver.solve(1, in, out);
        out.close();
    }

    static class ESpaceshipSolitaire {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt();
            int[] a = new int[n];
            long sum = 0;
            for (int i = 0; i < n; i++) {
                a[i] = s.nextInt();
                sum += a[i];
            }
            HashMap<Integer, Integer>[] hm = new HashMap[n];
            for (int i = 0; i < n; i++)
                hm[i] = new HashMap<>();
            int[] ex = new int[n];
            int q = s.nextInt();
            long sub = 0;
            while (q-- > 0) {
                int u = s.nextInt() - 1, sz = s.nextInt(), v = s.nextInt() - 1;
                if (hm[u].get(sz) != null) {
                    ex[hm[u].get(sz)]--;
                    if (ex[hm[u].get(sz)] < a[hm[u].get(sz)])
                        sub--;
                    hm[u].remove(sz);
                }
                if (v != -1) {
                    hm[u].put(sz, v);
                    ex[v]++;
                    if (ex[v] - 1 < a[v])
                        sub++;
                }
                w.println(sum - sub);
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

