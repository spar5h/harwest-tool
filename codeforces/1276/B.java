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
        BTwoFairs solver = new BTwoFairs();
        solver.solve(1, in, out);
        out.close();
    }

    static class BTwoFairs {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int t = s.nextInt();
            while (t-- > 0) {
                int n = s.nextInt(), m = s.nextInt(), a = s.nextInt() - 1, b = s.nextInt() - 1;
                ArrayList<Integer>[] adj = new ArrayList[n];
                for (int i = 0; i < n; i++)
                    adj[i] = new ArrayList<>();
                for (int i = 0; i < m; i++) {
                    int u = s.nextInt() - 1, v = s.nextInt() - 1;
                    adj[u].add(v);
                    adj[v].add(u);
                }
                Queue<Integer> q = new LinkedList<>();
                q.add(a);
                int[] an = new int[n];
                an[a] = 1;
                while (!q.isEmpty()) {
                    int x = q.poll();
                    for (int y : adj[x]) {
                        if (an[y] == 0 && y != b) {
                            q.add(y);
                            an[y] = 1;
                        }
                    }
                }
                q.clear();
                q.add(b);
                int[] bn = new int[n];
                bn[b] = 1;
                while (!q.isEmpty()) {
                    int x = q.poll();
                    for (int y : adj[x]) {
                        if (bn[y] == 0 && y != a) {
                            q.add(y);
                            bn[y] = 1;
                        }
                    }
                }
                long ac = -1, bc = -1;
                for (int i = 0; i < n; i++) {
                    if (an[i] == 1 && bn[i] == 0)
                        ac++;
                    if (an[i] == 0 && bn[i] == 1)
                        bc++;
                }
                w.println(ac * bc);
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

