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
        FChips solver = new FChips();
        solver.solve(1, in, out);
        out.close();
    }

    static class FChips {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt(), k = s.nextInt();
            String str = s.next();
            int[] a = new int[n];
            for (int i = 0; i < n; i++)
                if (str.charAt(i) == 'B')
                    a[i] = 1;
            Queue<Integer> q = new LinkedList<>();
            int[] color = new int[n];
            Arrays.fill(color, -1);
            int[] level = new int[n];
            Arrays.fill(level, -1);
            for (int i = 0; i < n; i++) {
                if (a[(i - 1 + n) % n] == a[i] || a[(i + 1) % n] == a[i]) {
                    q.add(i);
                    color[i] = a[i];
                    level[i] = 0;
                }
            }
            while (!q.isEmpty()) {
                int x = q.poll();
                if (level[x] == k)
                    break;
                if (level[(x - 1 + n) % n] == -1) {
                    q.add((x - 1 + n) % n);
                    color[(x - 1 + n) % n] = color[x];
                    level[(x - 1 + n) % n] = level[x] + 1;
                }
                if (level[(x + 1) % n] == -1) {
                    q.add((x + 1) % n);
                    color[(x + 1) % n] = color[x];
                    level[(x + 1) % n] = level[x] + 1;
                }
            }
            for (int i = 0; i < n; i++) {
                if (color[i] != -1) {
                    if (color[i] == 0)
                        w.print("W");
                    else
                        w.print("B");
                } else {
                    if ((a[i] + k) % 2 == 0)
                        w.print("W");
                    else
                        w.print("B");
                }
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

        public String nextString() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            StringBuilder res = new StringBuilder();
            do {
                if (Character.isValidCodePoint(c)) {
                    res.appendCodePoint(c);
                }
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
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

        public String next() {
            return nextString();
        }

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);

        }

    }
}

