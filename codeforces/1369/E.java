import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collection;
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
        EDeadLee solver = new EDeadLee();
        solver.solve(1, in, out);
        out.close();
    }

    static class EDeadLee {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt(), m = s.nextInt();
            int[] done = new int[m];
            int[] a = new int[n];
            for (int i = 0; i < n; i++)
                a[i] = s.nextInt();
            int[] f = new int[n];
            int[] vis = new int[n];
            Pair[] p = new Pair[m];
            ArrayList<Integer>[] adj = new ArrayList[n];
            for (int i = 0; i < n; i++)
                adj[i] = new ArrayList<>();
            for (int i = 0; i < m; i++) {
                p[i] = new Pair(s.nextInt() - 1, s.nextInt() - 1);
                adj[p[i].i].add(i);
                adj[p[i].j].add(i);
                f[p[i].i]++;
                f[p[i].j]++;
            }
            ArrayList<Integer> res = new ArrayList<>();
            Queue<Integer> q = new LinkedList<>();
            for (int i = 0; i < n; i++) {
                if (f[i] <= a[i]) {
                    q.add(i);
                    vis[i] = 1;
                }
            }
            while (!q.isEmpty()) {
                int x = q.poll();
                for (int i : adj[x]) {
                    if (done[i] == 1)
                        continue;
                    done[i] = 1;
                    f[x]--;
                    int y = p[i].other(x);
                    f[y]--;
                    if (vis[y] == 0 && f[y] <= a[y]) {
                        vis[y] = 1;
                        q.add(y);
                    }
                    res.add(i);
                }
            }
            if (res.size() == m) {
                w.println("ALIVE");
                for (int i = res.size() - 1; i >= 0; i--)
                    w.print((res.get(i) + 1) + " ");
                w.println();
            } else
                w.println("DEAD");
        }

        class Pair {
            int i;
            int j;

            Pair(int i, int j) {
                this.i = i;
                this.j = j;
            }

            int other(int x) {
                if (i == x)
                    return j;
                return i;
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

