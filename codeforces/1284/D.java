import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Arrays;
import java.util.AbstractMap;
import java.util.TreeMap;
import java.util.InputMismatchException;
import java.io.IOException;
import java.util.Comparator;
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
        DNewYearAndConference solver = new DNewYearAndConference();
        solver.solve(1, in, out);
        out.close();
    }

    static class DNewYearAndConference {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            //y m i such a noob
            int n = s.nextInt();
            int[] sa = new int[n];
            int[] ea = new int[n];
            int[] sb = new int[n];
            int[] eb = new int[n];
            for (int i = 0; i < n; i++) {
                sa[i] = s.nextInt();
                ea[i] = s.nextInt();
                sb[i] = s.nextInt();
                eb[i] = s.nextInt();
            }
            boolean res = true;
            TreeMap<Integer, HashSet<Integer>> sm = new TreeMap<>();
            TreeMap<Integer, HashSet<Integer>> em = new TreeMap<>();
            Foo[] a = new Foo[2 * n];
            for (int i = 0; i < n; i++) {
                a[2 * i] = new Foo(i, sa[i], 1);
                a[2 * i + 1] = new Foo(i, ea[i] + 1, -1);
            }
            Arrays.sort(a, new Comparator<Foo>() {

                public int compare(Foo o1, Foo o2) {
                    if (o1.x < o2.x)
                        return -1;
                    if (o1.x > o2.x)
                        return 1;
                    if (o1.d < o2.d)
                        return -1;
                    if (o1.d > o2.d)
                        return 1;
                    return 0;
                }
            });
            for (Foo f : a) {
                if (f.d == 1) {
                    if ((!sm.isEmpty() && sm.lastKey() > eb[f.i]) || (!em.isEmpty() && em.firstKey() < sb[f.i]))
                        res = false;
                    if (sm.get(sb[f.i]) == null)
                        sm.put(sb[f.i], new HashSet<>());
                    sm.get(sb[f.i]).add(f.i);
                    if (em.get(eb[f.i]) == null)
                        em.put(eb[f.i], new HashSet<>());
                    em.get(eb[f.i]).add(f.i);
                } else {
                    sm.get(sb[f.i]).remove(f.i);
                    if (sm.get(sb[f.i]).isEmpty())
                        sm.remove(sb[f.i]);
                    em.get(eb[f.i]).remove(f.i);
                    if (em.get(eb[f.i]).isEmpty())
                        em.remove(eb[f.i]);
                }
            }
            Foo[] b = new Foo[2 * n];
            for (int i = 0; i < n; i++) {
                b[2 * i] = new Foo(i, sb[i], 1);
                b[2 * i + 1] = new Foo(i, eb[i] + 1, -1);
            }
            Arrays.sort(b, new Comparator<Foo>() {

                public int compare(Foo o1, Foo o2) {
                    if (o1.x < o2.x)
                        return -1;
                    if (o1.x > o2.x)
                        return 1;
                    if (o1.d < o2.d)
                        return -1;
                    if (o1.d > o2.d)
                        return 1;
                    return 0;
                }
            });
            for (Foo f : b) {
                if (f.d == 1) {
                    if ((!sm.isEmpty() && sm.lastKey() > ea[f.i]) || (!em.isEmpty() && em.firstKey() < sa[f.i]))
                        res = false;
                    if (sm.get(sa[f.i]) == null)
                        sm.put(sa[f.i], new HashSet<>());
                    sm.get(sa[f.i]).add(f.i);
                    if (em.get(ea[f.i]) == null)
                        em.put(ea[f.i], new HashSet<>());
                    em.get(ea[f.i]).add(f.i);
                } else {
                    sm.get(sa[f.i]).remove(f.i);
                    if (sm.get(sa[f.i]).isEmpty())
                        sm.remove(sa[f.i]);
                    em.get(ea[f.i]).remove(f.i);
                    if (em.get(ea[f.i]).isEmpty())
                        em.remove(ea[f.i]);
                }
            }
            if (res)
                w.println("YES");
            else
                w.println("NO");
        }

        class Foo {
            int i;
            int x;
            int d;

            Foo(int i, int x, int d) {
                this.i = i;
                this.x = x;
                this.d = d;
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

