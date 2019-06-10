#include <iostream>
#include <vector>
#include <thread>
using namespace std;
int f()
{
    static int i = 0;
    synchronized {
            cout << i << " -> ";
            ++i;
            cout << i << '\n';
            return i;
    }
}
int main()
{
    vector<thread> v(10);
    for(auto& t: v)
        t = thread([]{ for(int n = 0; n < 10; ++n) f(); });
    for(auto& t: v)
        t.join();
}