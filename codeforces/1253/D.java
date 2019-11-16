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
        DHarmoniousGraph solver = new DHarmoniousGraph();
        solver.solve(1, in, out);
        out.close();
    }

    static class DHarmoniousGraph {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt(), m = s.nextInt();
            ArrayList<Integer>[] dsu = new ArrayList[n];
            int[] color = new int[n];
            for (int i = 0; i < n; i++) {
                dsu[i] = new ArrayList<>();
                dsu[i].add(i);
                color[i] = i;
            }
            for (int i = 0; i < m; i++) {
                int u = s.nextInt() - 1, v = s.nextInt() - 1;
                int small = color[u], big = color[v];
                if (small == big)
                    continue;
                if (dsu[small].size() > dsu[big].size()) {
                    int temp = small;
                    small = big;
                    big = temp;
                }
                for (int j : dsu[small]) {
                    dsu[big].add(j);
                    color[j] = big;
                }
                dsu[small].clear();
            }
            //System.out.println("///");
            int[] l = new int[n];
            Arrays.fill(l, -1);
            int[] r = new int[n];
            for (int i = 0; i < n; i++) {
                if (l[color[i]] == -1)
                    l[color[i]] = i;
                r[color[i]] = i;
            }
            int ans = 0;
            int left = 0;
            while (left < n) {
                int i = left;
                while (i <= r[color[left]]) {
                    //w.println(i + " " + color[left] + " " + r[color[left]]);
                    if (color[i] == color[left]) {
                        i++;
                        continue;
                    }
                    ans++;
                    int small = color[left], big = color[i];
                    if (dsu[small].size() > dsu[big].size()) {
                        int temp = small;
                        small = big;
                        big = temp;
                    }
                    r[big] = Math.max(r[small], r[big]);
                    for (int j : dsu[small]) {
                        dsu[big].add(j);
                        color[j] = big;
                    }
                    dsu[small].clear();
                    i++;
                }
                left = i;
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

