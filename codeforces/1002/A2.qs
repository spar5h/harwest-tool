namespace Solution {
    open Microsoft.Quantum.Primitive;
    open Microsoft.Quantum.Canon;
    open Microsoft.Quantum.Convert;
    open Microsoft.Quantum.Math;
    open Microsoft.Quantum.Arrays;
 
    operation Solve (qs : Qubit[], bits : Bool[]) : () {
        body {
            let n = Length(qs);
            H(qs[0]);
            for(i in 1 .. n - 1) {
                if(bits[i]) {
                    Controlled X([qs[0]], qs[i]);
                }
            }
        }
    }
}