import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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
        FDIYGarland solver = new FDIYGarland();
        solver.solve(1, in, out);
        out.close();
    }

    static class FDIYGarland {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt();
            int[] a = new int[n - 1];
            for (int i = 0; i < n - 1; i++)
                a[i] = s.nextInt() - 1;
            int k = a[0];
            ArrayList<Edge> res = new ArrayList<>();
            int[] f = new int[n];
            f[k] = 1;
            int curr = n - 1;
            while (curr >= 0 && f[curr] == 1)
                curr--;
            int prev = k;
            for (int i = 1; i < n - 1; i++) {
                if (f[a[i]] == 1) {
                    res.add(new Edge(prev, curr));
                    f[curr] = 1;
                } else {
                    res.add(new Edge(prev, a[i]));
                    f[a[i]] = 1;
                }
                while (curr >= 0 && f[curr] == 1)
                    curr--;
                prev = a[i];
            }
            res.add(new Edge(prev, curr));
            w.println(k + 1);
            for (Edge e : res) {
                w.println((e.u + 1) + " " + (e.v + 1));
            }
        }

        class Edge {
            int u;
            int v;

            Edge(int u, int v) {
                this.u = u;
                this.v = v;
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

