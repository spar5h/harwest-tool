namespace Solution {
    open Microsoft.Quantum.Primitive;
    open Microsoft.Quantum.Canon;
    open Microsoft.Quantum.Convert;
    open Microsoft.Quantum.Math;
    open Microsoft.Quantum.Arrays;
 
    operation Solve (qs : Qubit[], bits0 : Bool[], bits1 : Bool[]) : () {
        body {
            using(q = Qubit()) {
                H(q);
                let n = Length(qs);
                for(i in 0 .. n - 1) {
                    if(bits0[i]) {
                        (ControlledOnInt(0, X))([q], qs[i]);
                    }
                    if(bits1[i]) {
                        (ControlledOnInt(1, X))([q], qs[i]);
                    }
                }
                (ControlledOnBitString(bits1, X))(qs, q);
            }
        }
    }
}