package com.example;

import java.io.Serializable;

public class Node implements Serializable {
    private static final long serialVersionUID = 1L;
    
    String key;
    String value;
    Node prev;
    Node next;

    public Node() {
    }

    public Node(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}

