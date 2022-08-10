package com.Autopark.StackAndQueue;

import java.util.Arrays;

public class MyStack<T> {
    private static final int DEFAULT_SIZE = 5;
    private Object[] array;
    private int size;
    private int current;

    public MyStack() {
        array = new Object[DEFAULT_SIZE];
        size = DEFAULT_SIZE;
        current = 0;
    }

    public void push(T obj) {
        if (current == size - 1) {
            size *= 2;
            array = Arrays.copyOf(array, size);
        }

        array[++current] = obj;
    }

    public T pop() {
        T element = peek();
        --current;

        return element;
    }

    public T peek() {
        if (current < 0) {
            throw new IllegalStateException("Stack is empty");
        }

        return (T) array[current];
    }

    public int size() {
        return current;
    }

    public int getSize() {
        return size;
    }
}
