import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
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
        CArrayAndOperations solver = new CArrayAndOperations();
        solver.solve(1, in, out);
        out.close();
    }

    static class CArrayAndOperations {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt(), m = s.nextInt();
            Dinic dinic = new Dinic(30 * n + 2, 900 * m + 30 * n);
            int[] a = new int[n];
            for (int i = 0; i < n; i++)
                a[i] = s.nextInt();
            HashMap<Integer, HashMap<Integer, Integer>> hm = new HashMap<>();
            int nodePtr = 2;
            HashMap<Integer, Integer> spf = new HashMap<>();
            for (int i = 0; i < n; i++) {
                int x = a[i];
                for (int j = 2; (long) j * j <= a[i] && x > 1; j++) {
                    int curr = 1;
                    while (x % j == 0) {
                        curr *= j;
                        x /= j;
                        if (spf.get(curr) == null)
                            spf.put(curr, j);
                        if (hm.get(curr) == null)
                            hm.put(curr, new HashMap<>());
                        hm.get(curr).put(i, nodePtr);
                        if (i % 2 == 0) {
                            dinic.addEdge(0, nodePtr, 1);
                        } else {
                            dinic.addEdge(nodePtr, 1, 1);
                        }
                        nodePtr++;
                    }
                }
                if (x == 1)
                    continue;
                if (spf.get(x) == null)
                    spf.put(x, x);
                if (hm.get(x) == null)
                    hm.put(x, new HashMap<>());
                hm.get(x).put(i, nodePtr);
                if (i % 2 == 0) {
                    dinic.addEdge(0, nodePtr, 1);
                } else {
                    dinic.addEdge(nodePtr, 1, 1);
                }
                nodePtr++;
            }
            for (int i = 0; i < m; i++) {
                int u = s.nextInt() - 1, v = s.nextInt() - 1;
                if (u % 2 == 1) {
                    int temp = u;
                    u = v;
                    v = temp;
                }
                for (Map.Entry<Integer, HashMap<Integer, Integer>> e : hm.entrySet()) {
                    int key = e.getKey();
                    int mul = spf.get(key);
                    for (long j = mul; j <= (int) 1e9 && hm.containsKey((int) j); j *= mul) {
                        if (e.getValue().get(u) != null && hm.get((int) j).get(v) != null) {
                            dinic.addEdge(e.getValue().get(u), hm.get((int) j).get(v), Long.MAX_VALUE);
                        }
                    }
                }
            }
            w.println(dinic.dinic(0, 1));
        }

        class Dinic {
            int n;
            int[] level;
            int[] listIndex;
            ArrayList<Integer>[] adj;
            Edge[] edgeList;
            int edgePtr = 0;

            Dinic(int n, int m) {
                this.n = n;
                adj = new ArrayList[n];
                for (int i = 0; i < n; i++)
                    adj[i] = new ArrayList<>();
                edgeList = new Edge[m];
            }

            boolean getLevelGraph(int s, int t) {

                Queue<Integer> q = new LinkedList<Integer>();
                q.add(s);
                level[s] = 0;

                while (!q.isEmpty()) {

                    int x = q.poll();

                    for (int i : adj[x]) {

                        Edge e = edgeList[i];

                        int y = e.other(x);

                        if (e.possibleFlow(x) > 0 && level[y] == -1) {
                            level[y] = level[x] + 1;
                            q.add(y);
                        }
                    }
                }

                if (level[t] == -1)
                    return false;
                else
                    return true;
            }

            long sendFlow(int u, int t, long currFlow) {

                if (u == t)
                    return currFlow;

                long sentFlow = 0;

                while (listIndex[u] < adj[u].size()) {

                    Edge e = edgeList[adj[u].get(listIndex[u])];
                    int v = e.other(u);

                    if (level[v] == level[u] + 1 && e.possibleFlow(u) > 0) {

                        long possFlow = Math.min(e.possibleFlow(u), currFlow - sentFlow);
                        long flowSentNow = sendFlow(v, t, possFlow);

                        sentFlow += flowSentNow;
                        e.pushFlow(u, flowSentNow);
                    }

                    if (sentFlow == currFlow)
                        return sentFlow;

                    listIndex[u]++;
                }

                return sentFlow;
            }

            long dinic(int s, int t) {

                if (s == t)
                    return 0;

                level = new int[n];
                listIndex = new int[n];

                long maxFlow = 0;

                while (getLevelGraph(s, t)) {

                    maxFlow += sendFlow(s, t, Long.MAX_VALUE);

                    Arrays.fill(level, -1);
                    Arrays.fill(listIndex, 0);
                }

                return maxFlow;
            }

            void addEdge(int u, int v, long cap) {
                adj[u].add(edgePtr);
                adj[v].add(edgePtr);
                edgeList[edgePtr] = new Edge(u, v, cap);
                edgePtr++;
            }

            class Edge {
                int u;
                int v;
                long flow;
                long cap;

                Edge(int u, int v, long cap) {
                    this.u = u;
                    this.v = v;
                    this.cap = cap;
                    this.flow = 0;
                }

                void pushFlow(int x, long val) {
                    if (u == x)
                        flow += val;
                    else
                        flow -= val;
                }

                long possibleFlow(int x) {

                    if (u == x)
                        return cap - flow;
                    else
                        return flow;
                }

                int other(int x) {
                    if (x == u)
                        return v;
                    else
                        return u;
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

