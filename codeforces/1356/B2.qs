namespace Solution {
    open Microsoft.Quantum.Diagnostics;
    open Microsoft.Quantum.Arrays;
    open Microsoft.Quantum.Intrinsic;
    open Microsoft.Quantum.Canon;
    open Microsoft.Quantum.Convert;
    open Microsoft.Quantum.Math;
    open Microsoft.Quantum.Arithmetic;

    operation Solve (register : LittleEndian) : Unit is Adj+Ctl {
        let n = Length(register!);
        X(register![0]);
        let pow2 = [1,2,4,8,16,32,64,128,256,512,1024,2048,4096,8192,16384,32768];
        for(i in 1 .. n - 1) {
            (ControlledOnInt(pow2[i] - 1, X))(register![0 .. i - 1], register![i]);
        }
    }
}
