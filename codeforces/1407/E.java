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
        EEgorInTheRepublicOfDagestan solver = new EEgorInTheRepublicOfDagestan();
        solver.solve(1, in, out);
        out.close();
    }

    static class EEgorInTheRepublicOfDagestan {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt(), m = s.nextInt();
            ArrayList<Integer>[] adj = new ArrayList[n];
            for (int i = 0; i < n; i++)
                adj[i] = new ArrayList<>();
            Edge[] edge = new Edge[m];
            for (int i = 0; i < m; i++) {
                edge[i] = new Edge(s.nextInt() - 1, s.nextInt() - 1, s.nextInt());
                adj[edge[i].v].add(i);
            }
            Queue<Integer> q = new LinkedList<>();
            q.add(n - 1);
            int[] color = new int[n];
            Arrays.fill(color, -1);
            int[] d = new int[n];
            Arrays.fill(d, -1);
            d[n - 1] = 0;
            while (!q.isEmpty()) {
                int y = q.poll();
                for (int i : adj[y]) {
                    int x = edge[i].u;
                    if (color[x] == edge[i].c && d[x] == -1) {
                        d[x] = d[y] + 1;
                        q.add(x);
                    }
                    if (color[x] == -1) {
                        color[x] = 1 ^ edge[i].c;
                    }
                }
            }
            w.println(d[0]);
            for (int i = 0; i < n; i++) {
                if (color[i] == -1)
                    w.print(0);
                else
                    w.print(color[i]);
            }
            w.println();
        }

        class Edge {
            int u;
            int v;
            int c;

            Edge(int u, int v, int c) {
                this.u = u;
                this.v = v;
                this.c = c;
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

