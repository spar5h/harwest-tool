import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.io.IOException;
import java.util.Queue;
import java.util.LinkedList;
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
        DChristmasTrees solver = new DChristmasTrees();
        solver.solve(1, in, out);
        out.close();
    }

    static class DChristmasTrees {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt(), m = s.nextInt();
            Queue<Integer> q = new LinkedList<>();
            HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
            for (int i = 0; i < n; i++) {
                int x = s.nextInt();
                hm.put(x, 0);
                q.add(x);
            }
            int c = 0;
            long res = 0;
            ArrayList<Integer> a = new ArrayList<>();
            while (!q.isEmpty() && c < m) {
                int x = q.poll();
                if (!hm.containsKey(x - 1)) {
                    q.add(x - 1);
                    hm.put(x - 1, hm.get(x) + 1);
                    res += hm.get(x - 1);
                    a.add(x - 1);
                    c++;
                }
                if (c == m)
                    continue;
                if (!hm.containsKey(x + 1)) {
                    q.add(x + 1);
                    hm.put(x + 1, hm.get(x) + 1);
                    res += hm.get(x + 1);
                    a.add(x + 1);
                    c++;
                }
            }
            w.println(res);
            for (int i : a)
                w.print(i + " ");
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

