package heist.test;

class Teste
{
    public static void main(String[] args)
    {
        SharedZone shared = new SharedZone();
        new A(shared).start();
        new B(shared).start();
    }
}