import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.InputMismatchException;
import java.io.IOException;
import java.util.ArrayList;
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
        F1TheHiddenPairEasyVersion solver = new F1TheHiddenPairEasyVersion();
        solver.solve(1, in, out);
        out.close();
    }

    static class F1TheHiddenPairEasyVersion {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int t = s.nextInt();
            while (t-- > 0) {
                int n = s.nextInt();
                ArrayList<Integer>[] adj = new ArrayList[n];
                for (int i = 0; i < n; i++)
                    adj[i] = new ArrayList<>();
                for (int i = 0; i < n - 1; i++) {
                    int u = s.nextInt() - 1, v = s.nextInt() - 1;
                    adj[u].add(v);
                    adj[v].add(u);
                }
                //root
                w.print("? " + n + " ");
                for (int i = 1; i <= n; i++)
                    w.print(i + " ");
                w.println();
                w.flush();
                int root = s.nextInt() - 1;
                int dist = s.nextInt();
                int[] level = new int[n];
                int[] parent = new int[n];
                Arrays.fill(level, -1);
                Arrays.fill(parent, -1);
                Queue<Integer> q = new LinkedList<>();
                level[root] = 0;
                q.add(root);
                int depth = 0;
                while (!q.isEmpty()) {
                    int x = q.poll();
                    depth = level[x];
                    for (int y : adj[x]) {
                        if (level[y] == -1) {
                            level[y] = level[x] + 1;
                            parent[y] = x;
                            q.add(y);
                        }
                    }
                }
                int n1 = -1;
                int left = dist / 2, right = Math.min(depth, dist);
                while (left <= right) {
                    int mid = (left + right) / 2;
                    int count = 0;
                    for (int i = 0; i < n; i++)
                        if (level[i] >= mid)
                            count++;
                    w.print("? " + count + " ");
                    for (int i = 0; i < n; i++) {
                        if (level[i] >= mid)
                            w.print((i + 1) + " ");
                    }
                    w.println();
                    w.flush();
                    int v = s.nextInt() - 1;
                    int d = s.nextInt();
                    if (d == dist) {
                        n1 = v;
                        left = mid + 1;
                    } else {
                        right = mid - 1;
                    }
                }
                ArrayList<Integer> l = new ArrayList<>();
                int x = n1;
                while (x != root) {
                    l.add(x);
                    x = parent[x];
                }
                l.add(root);
                int diff = dist - (l.size() - 1);
                if (diff == 0) {
                    w.println("! " + (n1 + 1) + " " + (root + 1));
                    w.flush();
                    String str = s.next();
                    if (!str.equals("Correct")) {
                        return;
                    }
                    continue;
                }
                int count = 0;
                for (int i = 0; i < n; i++) {
                    if (l.size() - 1 >= diff && l.get(l.size() - 1 - diff).equals(i))
                        continue;
                    if (level[i] == diff)
                        count++;
                }
                w.print("? " + count + " ");
                for (int i = 0; i < n; i++) {
                    if (l.size() - 1 >= diff && l.get(l.size() - 1 - diff).equals(i))
                        continue;
                    if (level[i] == diff) {
                        w.print((i + 1) + " ");
                    }
                }
                w.println();
                w.flush();
                int n2 = s.nextInt() - 1;
                s.nextInt();
                w.println("! " + (n1 + 1) + " " + (n2 + 1) + " ");
                w.flush();
                String str = s.next();
                if (!str.equals("Correct")) {
                    return;
                }
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

