package Main;

import Class.Usuario;

public class Main {
    public static void main(String[] args) {
        Usuario s1 = new Usuario("Jose", "jose@gmail.com", "123456");
        Usuario s2 = new Usuario("Choque", "choque@gmail.com", "123456");
        Usuario s3 = new Usuario("Navarro", "navarro@gmail.com", "123456");

        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);
    }
}