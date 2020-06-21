
namespace Solution {
    open Microsoft.Quantum.Diagnostics;
    open Microsoft.Quantum.Arrays;
    open Microsoft.Quantum.Arithmetic;
    open Microsoft.Quantum.Intrinsic;
    open Microsoft.Quantum.Canon;
    open Microsoft.Quantum.Convert;
    open Microsoft.Quantum.Math;
    open Microsoft.Quantum.Bitwise;
    
    operation QuantumFourierTransform (register : Qubit[]) : Unit is Adj+Ctl {
        let n = Length(register);
        for (i in 0 .. n - 1) {
            H(register[i]);
            for(j in i + 1 .. n - 1) {
                Controlled R1Frac([register[j]], (2, j + 1 - i, register[i]));
            }
        }
        for (i in 0 .. n / 2 - 1) {
            SWAP(register[i], register[n - 1 - i]);
        }
    }
    
    operation Solve (p : Int, inputRegister : LittleEndian) : Unit is Adj+Ctl {
        let x = PowI(2, Length(inputRegister!));
        let y = p % 8;
        for(i in 0 .. y - 1) {
            QFTLE(inputRegister);
        }
    }
 
}