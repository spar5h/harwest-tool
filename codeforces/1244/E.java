import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
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
        EMinimizingDifference solver = new EMinimizingDifference();
        solver.solve(1, in, out);
        out.close();
    }

    static class EMinimizingDifference {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt();
            long k = s.nextLong();
            Long[] a = new Long[n];
            for (int i = 0; i < n; i++)
                a[i] = s.nextLong();
            Arrays.sort(a);
            int ans = (int) 1e9;
            int left = 0, right = (int) 1e9 - 1;
            while (left <= right) {
                int mid = (left + right) / 2;
                int l = 0, r = 0;
                while (r < n && a[r] <= a[l] + mid)
                    r++;
                long bestCost = Long.MAX_VALUE;
                long cost = 0;
                for (int i = r; i < n; i++) {
                    cost += a[i] - (a[l] + mid);
                }
                bestCost = Math.min(cost, bestCost);
                while (l + 1 < n) {
                    l++;
                    cost += l * (a[l] - a[l - 1]);
                    while (r < n && a[r] <= a[l] + mid) {
                        cost -= a[r] - (a[l - 1] + mid);
                        r++;
                    }
                    cost -= (n - r) * (a[l] - a[l - 1]);
                    bestCost = Math.min(cost, bestCost);
                }
                l = n - 1;
                r = n - 1;
                while (l >= 0 && a[l] >= a[r] - mid)
                    l--;
                cost = 0;
                for (int i = l; i >= 0; i--) {
                    cost += (a[r] - mid) - a[i];
                }
                bestCost = Math.min(cost, bestCost);
                while (r - 1 >= 0) {
                    r--;
                    cost += (n - 1 - r) * (a[r + 1] - a[r]);
                    while (l >= 0 && a[l] >= a[r] - mid) {
                        cost -= (a[r + 1] - mid) - a[l];
                        l--;
                    }
                    cost -= (l + 1) * (a[r + 1] - a[r]);
                    bestCost = Math.min(cost, bestCost);
                }
                if (bestCost <= k) {
                    ans = mid;
                    right = mid - 1;
                } else
                    left = mid + 1;
            }
            w.println(ans);
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

