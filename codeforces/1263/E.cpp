
#include <bits/stdc++.h>
using namespace std;
 
#define pb push_back
#define long long long int
#define sz (1 << (int)(ceil(log(1e6) / log(2)) + 1))

int treemin[sz], treemax[sz], lazy[sz];
void pushLazy(int n, int nL, int nR) {
    treemin[n] += lazy[n];
    treemax[n] += lazy[n];
    if(nL != nR) {
        lazy[2 * n + 1] += lazy[n];
        lazy[2 * n + 2] += lazy[n];
    }
    lazy[n] = 0;
}

void update(int n, int nL, int nR, int l, int r, int val) {
    pushLazy(n, nL, nR);
    if(nR < l || r < nL)
        return;
    if(l <= nL && nR <= r) {
        lazy[n] += val;
        pushLazy(n, nL, nR);
        return;
    }
    update(2 * n + 1, nL, (nL + nR) / 2, l, r, val);
    update(2 * n + 2, (nL + nR) / 2 + 1, nR, l, r, val);
    treemin[n] = min(treemin[2 * n + 1], treemin[2 * n + 2]);
    treemax[n] = max(treemax[2 * n + 1], treemax[2 * n + 2]);
}
int query(int n, int nL, int nR, int l, int r, int d) {
    pushLazy(n, nL, nR);
    if(nR < l || r < nL) {
        if(d == 0)
            return (int)1e9;
        else
            return (int)-1e9;
    }
    if(l <= nL && nR <= r) {
        if(d == 0)
            return treemin[n];
        else
            return treemax[n];
    }
    int r1 = query(2 * n + 1, nL , (nL + nR) / 2, l, r, d);
    int r2 = query(2 * n + 2, (nL + nR) / 2 + 1, nR, l, r, d);
    treemin[n] = min(treemin[2 * n + 1], treemin[2 * n + 2]);
    treemax[n] = max(treemax[2 * n + 1], treemax[2 * n + 2]);
    if(d == 0)
        return min(r1, r2);
    else
        return max(r1, r2);
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    
    int n;
    cin>>n;
    char a[n];
    for(int i = 0; i < n; i++)
        a[i] = ' ';
    for(int i = 0; i < sz; i++) {
        treemin[i] = 0;
        treemax[i] = 0;
        lazy[i] = 0;
    }
    int curr = 0;
    char c[n];
    int m = 1;
    for(int i = 0; i < n; i++) {
        cin>>c[i];
        if(c[i] == 'R')
            m++;
    }
    for(int i = 0; i < n; i++) {
        if(c[i] == 'R') {
            curr++;
        }
       else if(c[i] == 'L') {
           curr = max(0, curr - 1);
       }
       else {
           int val = 0;
           if(a[curr] == '(')
               val--;
           else if(a[curr] == ')')
               val++;
           if(c[i] == '(')
               val++;
           else if(c[i] == ')')
               val--;
           update(0, 0, m - 1, curr, m - 1, val);
           a[curr] = c[i];
       }
       if(query(0, 0, m - 1, 0, m - 1, 0) >= 0 && query(0, 0, m - 1, m - 1, m - 1, 0) == 0) {
           cout<<query(0, 0, m - 1, 0, m - 1, 1)<<" ";
       }
       else
           cout<<-1<<" ";
    }
}
    

    