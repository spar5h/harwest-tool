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
        CElectrification solver = new CElectrification();
        solver.solve(1, in, out);
        out.close();
    }

    static class CElectrification {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int t = s.nextInt();
            while (t-- > 0) {
                int n = s.nextInt(), k = s.nextInt() + 1;
                int[] a = new int[n];
                for (int i = 0; i < n; i++)
                    a[i] = s.nextInt();
                int left = 0, right = (int) 1e9;
                int ans = -1;
                while (left <= right) {
                    int mid = (left + right) / 2;
                    int can = Integer.MIN_VALUE;
                    int j = 0;
                    for (int i = 0; i < n; i++) {
                        while (j < i && (a[i] - mid) - a[j] > mid)
                            j++;
                        if (i - j + 1 >= k)
                            can = a[i] - mid;
                    }
                    j = n - 1;
                    for (int i = n - 1; i >= 0; i--) {
                        while (j > i && a[j] - (a[i] + mid) > mid)
                            j--;
                        if (j - i + 1 >= k)
                            can = a[i] + mid;
                    }
                    if (can != Integer.MIN_VALUE) {
                        right = mid - 1;
                        ans = can;
                    } else
                        left = mid + 1;
                }
                w.println(ans);
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

