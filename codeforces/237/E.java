import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.AbstractQueue;
import java.util.InputMismatchException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.AbstractCollection;
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
        EBuildString solver = new EBuildString();
        solver.solve(1, in, out);
        out.close();
    }

    static class EBuildString {
        List<Edge>[] createGraph(int n) {
            List<Edge>[] graph = new List[n];
            for (int i = 0; i < n; i++)
                graph[i] = new ArrayList<>();
            return graph;
        }

        void addEdge(List<Edge>[] graph, int s, int t, int cap, int cost) {
            graph[s].add(new Edge(t, cap, cost, graph[t].size()));
            graph[t].add(new Edge(s, 0, -cost, graph[s].size() - 1));
        }

        int[] minCostFlow(List<Edge>[] graph, int s, int t, int maxf) {
            int n = graph.length;
            int[] prio = new int[n];
            int[] curflow = new int[n];
            int[] prevedge = new int[n];
            int[] prevnode = new int[n];
            int[] pot = new int[n];
            // bellmanFord invocation can be skipped if edges costs are non-negative
            //bellmanFord(graph, s, pot);
            int flow = 0;
            int flowCost = 0;
            while (flow < maxf) {
                PriorityQueue<Long> q = new PriorityQueue<>();
                q.add((long) s);
                Arrays.fill(prio, Integer.MAX_VALUE);
                prio[s] = 0;
                boolean[] finished = new boolean[n];
                curflow[s] = Integer.MAX_VALUE;
                while (!finished[t] && !q.isEmpty()) {
                    long cur = q.remove();
                    int u = (int) (cur & 0xFFFF_FFFFL);
                    int priou = (int) (cur >>> 32);
                    if (priou != prio[u])
                        continue;
                    finished[u] = true;
                    for (int i = 0; i < graph[u].size(); i++) {
                        Edge e = graph[u].get(i);
                        if (e.f >= e.cap)
                            continue;
                        int v = e.to;
                        int nprio = prio[u] + e.cost + pot[u] - pot[v];
                        if (prio[v] > nprio) {
                            prio[v] = nprio;
                            q.add(((long) nprio << 32) + v);
                            prevnode[v] = u;
                            prevedge[v] = i;
                            curflow[v] = Math.min(curflow[u], e.cap - e.f);
                        }
                    }
                }
                if (prio[t] == Integer.MAX_VALUE)
                    break;
                for (int i = 0; i < n; i++)
                    if (finished[i])
                        pot[i] += prio[i] - prio[t];
                int df = Math.min(curflow[t], maxf - flow);
                flow += df;
                for (int v = t; v != s; v = prevnode[v]) {
                    Edge e = graph[prevnode[v]].get(prevedge[v]);
                    e.f += df;
                    graph[v].get(e.rev).f -= df;
                    flowCost += df * e.cost;
                }
            }
            return new int[]{flow, flowCost};
        }

        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int[] fr = new int[26];
            char[] str = s.next().toCharArray();
            int flow = str.length;
            for (char i : str)
                fr[i - 'a']++;
            int n = s.nextInt();
            int[][] f = new int[n][26];
            int[] c = new int[n];
            for (int i = 0; i < n; i++) {
                str = s.next().toCharArray();
                for (char j : str)
                    f[i][j - 'a']++;
                c[i] = s.nextInt();
            }
            int src = 0;
            int sink = (1 + n + 26 * n + 26 + 1) - 1;
            List<Edge>[] graph = createGraph(1 + n + 26 * n + 26 + 1);
            for (int i = 0; i < n; i++)
                addEdge(graph, src, 1 + i, c[i], 0);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < 26; j++) {
                    if (f[i][j] > 0) {
                        addEdge(graph, 1 + i, 1 + n + 26 * i + j, f[i][j], 1 + i);
                        addEdge(graph, 1 + n + 26 * i + j, 1 + n + 26 * n + j, Integer.MAX_VALUE, 0);
                    }
                }
            }
            for (int i = 0; i < 26; i++) {
                if (fr[i] > 0)
                    addEdge(graph, 1 + n + 26 * n + i, sink, fr[i], 0);
            }
            int[] res = minCostFlow(graph, src, sink, flow);
            if (flow == res[0])
                w.println(res[1]);
            else
                w.println(-1);
        }

        class Edge {
            int to;
            int f;
            int cap;
            int cost;
            int rev;

            Edge(int v, int cap, int cost, int rev) {
                this.to = v;
                this.cap = cap;
                this.cost = cost;
                this.rev = rev;
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

