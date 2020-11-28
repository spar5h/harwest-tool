import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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
        ENikitaAndOrderStatistics solver = new ENikitaAndOrderStatistics();
        solver.solve(1, in, out);
        out.close();
    }

    static class ENikitaAndOrderStatistics {
        void fft(double[] real, double[] imag, boolean invert) {
            int n = real.length;
            int log_n = 0;
            while ((1 << log_n) < n)
                log_n++;
            for (int i = 1, j = 0; i < n; i++) {
                int bit = log_n - 1;
                while (((j >> bit) & 1) == 1) {
                    j ^= 1 << bit;
                    bit--;
                }
                j ^= 1 << bit;
                if (i < j) {
                    double tempReal = real[i];
                    double tempImag = imag[i];
                    real[i] = real[j];
                    imag[i] = imag[j];
                    real[j] = tempReal;
                    imag[j] = tempImag;
                }
            }
            for (int len = 2; len <= n; len <<= 1) {
                int halfLen = len >> 1;
                double angle = 2 * Math.PI / len;
                if (invert) {
                    angle = -angle;
                }
                double wLenReal = Math.cos(angle);
                double wLenImag = Math.sin(angle);
                for (int i = 0; i < n; i += len) {
                    double wReal = 1;
                    double wImag = 0;
                    for (int j = 0; j < halfLen; j++) {
                        double uReal = real[i + j];
                        double uImag = imag[i + j];
                        double vReal = wReal * real[i + j + halfLen] - wImag * imag[i + j + halfLen];
                        double vImag = wReal * imag[i + j + halfLen] + wImag * real[i + j + halfLen];
                        real[i + j] = uReal + vReal;
                        imag[i + j] = uImag + vImag;
                        real[i + j + halfLen] = uReal - vReal;
                        imag[i + j + halfLen] = uImag - vImag;
                        double nextWReal = wReal * wLenReal - wImag * wLenImag;
                        double nextWImag = wReal * wLenImag + wImag * wLenReal;
                        wReal = nextWReal;
                        wImag = nextWImag;
                    }
                }
            }
            if (invert) {
                for (int i = 0; i < n; i++) {
                    real[i] /= n;
                    imag[i] /= n;
                }
            }
        }

        long[] multiply(long[] a, long[] b) {
            int n = Integer.highestOneBit(Math.max(a.length, b.length) - 1) << 2;
            if (n == 0)
                n = 1;
            double[] aReal = new double[n];
            double[] aImag = new double[n];
            double[] bReal = new double[n];
            double[] bImag = new double[n];
            for (int i = 0; i < a.length; i++)
                aReal[i] = a[i];
            for (int i = 0; i < b.length; i++)
                bReal[i] = b[i];
            fft(aReal, aImag, false);
            fft(bReal, bImag, false);
            for (int i = 0; i < n; i++) {
                double real = aReal[i] * bReal[i] - aImag[i] * bImag[i];
                double imag = aReal[i] * bImag[i] + aImag[i] * bReal[i];
                aReal[i] = real;
                aImag[i] = imag;
            }
            fft(aReal, aImag, true);
            long[] c = new long[n];
            for (int i = 0; i < n; i++) {
                c[i] = Math.round(aReal[i]);
            }
            return c;
        }

        long[] rev(long[] a) {
            long[] b = new long[a.length];
            for (int i = 0; i < a.length; i++)
                b[i] = a[a.length - 1 - i];
            return b;
        }

        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt(), x = s.nextInt();
            long[] f = new long[n + 1];
            f[0] = 1;
            int[] a = new int[n];
            int pre = 0;
            for (int i = 0; i < n; i++) {
                a[i] = s.nextInt();
                a[i] = a[i] < x ? 1 : 0;
                pre += a[i];
                f[pre]++;
            }
            long[] res = multiply(f, rev(f));
            long res0 = 0;
            for (int i = 0; i < n; i++) {
                if (a[i] == 1) {
                    continue;
                }
                int j = i;
                while (j < n && a[j] == 0)
                    j++;
                long k = j - i;
                res0 += k * (k + 1) / 2;
                i = j - 1;
            }
            w.print(res0 + " ");
            for (int i = 1; i <= n; i++)
                w.print(res[n - i] + " ");
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

