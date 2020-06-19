namespace Solution {
    open Microsoft.Quantum.Diagnostics;
    open Microsoft.Quantum.Arrays;
    open Microsoft.Quantum.Intrinsic;
    open Microsoft.Quantum.Canon;
    open Microsoft.Quantum.Convert;
    open Microsoft.Quantum.Math;
    
    operation Solve (qs : Qubit[]) : Unit {
        let n = Length(qs);
        let pow2 = [1,2,4,8,16,32,64,128,256,512,1024,2048,4096,8192,16384,32768];
        using (q = Qubit()) {
            repeat {
                for(i in 0 .. n - 1) {
                    H(qs[i]);
                }
                (ControlledOnInt(pow2[n] - 1, X))(qs, q);
                let res = M(q);
                if(res == One) {
                    X(q);
                    for(i in 0 .. n - 1) {
                        X(qs[i]);
                    }
                }
            }
            until (res == Zero);
        } 
    }
}