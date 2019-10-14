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
        GRunningInPairs solver = new GRunningInPairs();
        solver.solve(1, in, out);
        out.close();
    }

    static class GRunningInPairs {
        void recur(int[] a, int l, int r, long k) {
            if (r - l >= k) {
                int temp = a[r];
                a[r] = a[r - (int) k];
                a[r - (int) k] = temp;
                return;
            }
            int temp = a[l];
            a[l] = a[r];
            a[r] = temp;
            recur(a, l + 1, r - 1, k - (r - l));
        }

        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt();
            long k = s.nextLong();
            long min = (long) n * (n + 1) / 2;
            long max = 0;
            for (int i = 0; i < n / 2; i++)
                max += 2 * (n - i);
            if (n % 2 == 1)
                max += (n + 1) / 2;
            k = Math.min(max, k);
            if (k < min) {
                w.println(-1);
                return;
            }
            int[] a = new int[n];
            for (int i = 0; i < n; i++)
                a[i] = i + 1;
            recur(a, 0, n - 1, k - min);
            w.println(k);
            for (int i = 1; i <= n; i++)
                w.print(i + " ");
            w.println();
            for (int i = 0; i < n; i++)
                w.print(a[i] + " ");
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

