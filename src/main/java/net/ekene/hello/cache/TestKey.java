package net.ekene.hello.cache;

public class TestKey {
    public static void main(String[] args) {
        Key key = new Key("hgfghj");
        Key key2 = new Key("hgfghj");
        Key key1 = new Key();
        System.out.println(key1);
        System.out.println(key);
        System.out.println(key2);
        System.out.println(key.hashCode());
    }
}
