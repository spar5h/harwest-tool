namespace Solution {
    open Microsoft.Quantum.Primitive;
    open Microsoft.Quantum.Canon;

    operation Solve (qs : Qubit[]) : Int {
        body {
            mutable count = 0;
            let n = Length(qs);
            for(i in 0 .. n - 1) {
                if(M(qs[i]) == One) {
                    set count += 1;
                }
            }
            if(count == 0 or count == n) {
                return 0;
            }
            return 1;
        }
    }
}