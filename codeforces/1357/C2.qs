namespace Solution {
    open Microsoft.Quantum.Diagnostics;
    open Microsoft.Quantum.Arrays;
    open Microsoft.Quantum.Intrinsic;
    open Microsoft.Quantum.Canon;
    open Microsoft.Quantum.Convert;
    open Microsoft.Quantum.Math;
    open Microsoft.Quantum.Bitwise;
    
    operation Solve (qs : Qubit[], parity : Int) : Unit {
        let n = Length(qs);
        if(n == 1 and parity == 1) {
            X(qs[0]);
        }
        let pow2 = [1,2,4,8,16,32,64,128,256,512,1024,2048,4096,8192,16384,32768];
        for(i in 0 .. n - 2) {
            H(qs[i]);
        }
        for(i in 0 .. pow2[n - 1] - 1) {
            let x = Parity(i);
            if(x != parity) {
                (ControlledOnInt(i, X))(qs[0 .. n - 2], qs[n - 1]);
            }
        }
    }
}