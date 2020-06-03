namespace Solution {
    open Microsoft.Quantum.Primitive;
    open Microsoft.Quantum.Canon;
 
    operation Solve (qs : Qubit[]) : Int {
        body {
            H(qs[0]);
            H(qs[1]);
            mutable val = 0;
            if(M(qs[0]) == One) {
                set val += 2;
            }
            if(M(qs[1]) == One) {
                set val += 1;
            }
            return val;
        }
    }
}