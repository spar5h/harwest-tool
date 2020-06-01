namespace Solution {
    open Microsoft.Quantum.Primitive;
    open Microsoft.Quantum.Canon;

    operation Solve (qs : Qubit[], bits : Bool[][]) : Unit {
        using (q = Qubit[2]) {
            H(q[0]);
            H(q[1]);
            let n = Length(qs);
            for(i in 0 .. n - 1) {
                for(j in 0 .. 3) {
                    if(bits[j][i]) {
                        (ControlledOnInt(j, X))(q, qs[i]);
                    }
                }
            }
            (ControlledOnBitString(bits[1], X))(qs, q[0]);
            (ControlledOnBitString(bits[3], X))(qs, q[0]);
            (ControlledOnBitString(bits[2], X))(qs, q[1]);
            (ControlledOnBitString(bits[3], X))(qs, q[1]);
        }
    }
}