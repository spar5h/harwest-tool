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
        TaskC solver = new TaskC();
        solver.solve(1, in, out);
        out.close();
    }

    static class TaskC {
        static char[] a;
        static TaskC.node[] tree;

        static void build(int n, int nL, int nR) {

            tree[n] = new TaskC.node();

            if (nL == nR) {

                if (a[nL] == '(')
                    tree[n].left++;
                else
                    tree[n].right++;

                return;
            }

            build(2 * n, nL, (nL + nR) / 2);
            build(2 * n + 1, (nL + nR) / 2 + 1, nR);

            int val = Math.min(tree[2 * n].left, tree[2 * n + 1].right);
            tree[n].max = tree[2 * n].max + tree[2 * n + 1].max + 2 * val;
            tree[n].left = tree[2 * n].left + tree[2 * n + 1].left - val;
            tree[n].right = tree[2 * n].right + tree[2 * n + 1].right - val;
        }

        static TaskC.node query(int n, int nL, int nR, int l, int r) {

            if (nR < l || r < nL)
                return new TaskC.node();

            if (l <= nL && nR <= r)
                return tree[n];

            TaskC.node left = query(2 * n, nL, (nL + nR) / 2, l, r), right = query(2 * n + 1, (nL + nR) / 2 + 1, nR, l, r);

            TaskC.node res = new TaskC.node();
            int val = Math.min(left.left, right.right);
            res.max = left.max + right.max + 2 * val;
            res.left = left.left + right.left - val;
            res.right = left.right + right.right - val;

            return res;
        }

        public void solve(int testNumber, InputReader s, PrintWriter w) {

            a = (" " + s.next()).toCharArray();
            int N = a.length - 1;

            int n = (int) Math.pow(2, (int) Math.ceil(Math.log(N) / Math.log(2)) + 1) - 1;
            tree = new TaskC.node[n + 1];

            build(1, 1, N);

            int q = s.nextInt();

            while (q-- > 0)
                w.println(query(1, 1, N, s.nextInt(), s.nextInt()).max);
        }

        static class node {
            int max = 0;
            int left = 0;
            int right = 0;

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

