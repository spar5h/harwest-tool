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
        DSalaryChanging solver = new DSalaryChanging();
        solver.solve(1, in, out);
        out.close();
    }

    static class DSalaryChanging {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int t = s.nextInt();
            while (t-- > 0) {
                int n = s.nextInt();
                long sal = s.nextLong();
                long[] l = new long[n];
                long[] r = new long[n];
                long minTotal = 0;
                for (int i = 0; i < n; i++) {
                    l[i] = s.nextLong();
                    minTotal += l[i];
                    r[i] = s.nextLong();
                }
                Integer[] a = new Integer[n];
                for (int i = 0; i < n; i++)
                    a[i] = i;
                Arrays.sort(a, new Comparator<Integer>() {

                    public int compare(Integer o1, Integer o2) {
                        if (l[o1] < l[o2])
                            return -1;
                        if (l[o1] > l[o2])
                            return 1;
                        return 0;
                    }
                });

                long left = l[a[n / 2]], right = (long) 1e15;
                long ans = left;

                while (left <= right) {

                    long mid = (left + right) / 2;

                    int[] asg = new int[n];
                    int rightCount = 0;

                    int lim = n - 1;
                    while (lim >= 0 && l[a[lim]] > mid) {
                        asg[lim] = 1;
                        lim--;
                        rightCount++;
                    }

                    for (int i = 0; i <= lim; i++) {
                        if (r[a[i]] < mid) {
                            asg[i] = -1;
                        }
                    }

                    long used = minTotal;

                    for (int i = n - 1; rightCount < n / 2 + 1 && i >= 0; i--) {
                        if (asg[i] != 0)
                            continue;
                        asg[i] = 1;
                        used += mid - l[a[i]];
                        rightCount++;
                    }

                    if (rightCount == n / 2 + 1 && used <= sal) {
                        ans = mid;
                        left = mid + 1;
                    } else {
                        right = mid - 1;
                    }
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

