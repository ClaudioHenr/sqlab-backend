package br.com.net.sqlab_backend;

public class RollbackException extends Exception {
    public RollbackException(String message) {
        System.out.println(message);
    }
}
