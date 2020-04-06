import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
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
        BCompositeColoring solver = new BCompositeColoring();
        solver.solve(1, in, out);
        out.close();
    }

    static class BCompositeColoring {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int[] sieve = new int[1001];
            Arrays.fill(sieve, 1);
            for (int i = 2; i <= 1000; i++) {
                if (sieve[i] == 0)
                    continue;
                for (int j = i; i * j <= 1000; j++)
                    sieve[i * j] = 0;
            }
            ArrayList<Integer> b = new ArrayList<>();
            for (int i = 2; i * i <= 1000; i++) {
                if (sieve[i] == 1)
                    b.add(i);
            }
            int t = s.nextInt();
            while (t-- > 0) {
                int[] map = new int[35];
                int n = s.nextInt();
                int[] a = new int[n];
                for (int i = 0; i < n; i++)
                    a[i] = s.nextInt();
                int[] res = new int[n];
                int m = 0;
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < b.size(); j++) {
                        if (a[i] % b.get(j) == 0) {
                            if (map[j] == 0) {
                                m++;
                                map[j] = m;
                            }
                            res[i] = map[j];
                            break;
                        }
                    }
                }
                w.println(m);
                for (int i = 0; i < n; i++)
                    w.print(res[i] + " ");
                w.println();
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

