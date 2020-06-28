import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashSet;
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
        ESkiAccidents solver = new ESkiAccidents();
        solver.solve(1, in, out);
        out.close();
    }

    static class ESkiAccidents {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int t = s.nextInt();
            while (t-- > 0) {
                int n = s.nextInt(), m = s.nextInt();
                HashSet<Integer>[] adj = new HashSet[n];
                for (int i = 0; i < n; i++)
                    adj[i] = new HashSet<>();
                for (int i = 0; i < m; i++) {
                    int u = s.nextInt() - 1, v = s.nextInt() - 1;
                    adj[v].add(u);
                }
                int c = 0;
                int[] dead = new int[n];
                int[] lvl = new int[n];
                for (int i = 0; i < n; i++) {
                    for (int j : adj[i]) {
                        if (dead[j] == 0) {
                            lvl[i] = Math.max(lvl[j] + 1, lvl[i]);
                        }
                    }
                    if (lvl[i] == 2) {
                        dead[i] = 1;
                        c++;
                    }
                }
                w.println(c);
                for (int i = 0; i < n; i++) {
                    if (dead[i] == 1)
                        w.print(i + 1 + " ");
                }
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

