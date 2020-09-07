#ifndef __clang__
#pragma GCC optimize ("O3")
#endif
void solve(
#ifdef GCJ_CASE
	long long case_id
#endif
);
#include<algorithm>
#include<bitset>
#include<functional>
#include<iomanip>
#include<iostream>
#include<limits>
#include<map>
#include<numeric>
#include<queue>
#include<set>
#include<sstream>
#include<type_traits>
#include<unordered_map>
#include<unordered_set>
#include<vector>
#include<cassert>
#include<climits>
#include<cmath>
#include<cstdio>
#include<cstdlib>
using namespace std;
using LL = long long;
using ULL = unsigned long long;
#define int LL
using unit = tuple<>;
using LD = long double;
template<class T> using vec = vector<T>;
#pragma rab:destructively_golf
#ifndef MOD
#define MOD 1000000007
#endif
#if !defined(FORCE_MOD) && MOD != 1000000007 && MOD != 1000000009 && MOD != 998244353
#error mod
#endif
// #include "power.hpp"
inline constexpr int modulo(int a, int b) {
 a %= b;
 return a && (a>0) != (b>0) ? a + b : a;
}
inline constexpr int divide(int a, int b) {
 return (a - modulo(a, b)) / b;
}
/**
 int with modulo.
 `d` must be a prime for `log`.
 `d` must be coprime to `v` for `inv` and to `m.v` for `operator/` and `operator/=`.
*/
template<int d = MOD> struct MInt {
 /*! https://ei1333.github.io/luzhiled/snippets/other/mod-int.html */
 int v;
 constexpr MInt() : v(0) {}
 explicit constexpr MInt(int i) : v(modulo(i, d)) {}
 MInt &operator+=(const MInt &m) {
	v += m.v;
	if(v >= d) v -= d;
	return *this;
 }
 MInt &operator-=(const MInt &m) {
	v -= m.v;
	if(v < 0) v += d;
	return *this;
 }
 MInt &operator*=(const MInt &m) {
	v = v * m.v % d;
	return *this;
 }
 MInt &operator/=(const MInt &m) {
	return *this *= m.inv();
 }
 BINOPa(MInt, MInt, +)
 BINOPa(MInt, MInt, -)
 BINOPa(MInt, MInt, *)
 BINOPa(MInt, MInt, /)
 MInt operator-() const {
	return MInt() -= *this;
 }
 CMPOP(MInt, ==, v, m.v, m)
 CMPOP(MInt, !=, v, m.v, m)
 MInt pow(int n) const {
	return power(*this, n);
 }
 MInt inv() const {
	int a = v, b = d, x = 1, y = 0;
	while(b) {
	 swap(y, x -= a / b * y);
	 swap(b, a %= b);
	}
	return (MInt)x;
 }
 friend ostream &operator<<(ostream &o, const MInt &m){ return o << m.v; }
 friend istream &operator>>(istream &i, MInt &m) { i >> m.v; m.v %= d; return i; }
};
using mint = MInt<>;
constexpr mint operator"" _m(ULL n){ return mint(n); }
constexpr MInt<998244353>operator"" _m998244353(ULL n){ return MInt<998244353>(n); }
constexpr MInt<1000000007>operator"" _m1e9_7(ULL n){ return MInt<1000000007>(n); }
constexpr MInt<1000000009>operator"" _m1e9_9(ULL n){ return MInt<1000000009>(n); }
#pragma rab:gsub \b(\d+)m\b mint(\1)

signed main() {
 mint i;

 return 0;
}
