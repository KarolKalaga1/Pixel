
package com.project.algorithms;

import java.util.Stack;

/**
 *
 * @author Karol
 * @param <T>
 */
public class SizedStack<T> extends Stack<T> {

private final int maxSize;

public SizedStack(int size) {
    super();
    this.maxSize = size;
}

@Override
public Object push(Object object) {
    while (this.size() > maxSize) {
        this.remove(0);
    }
    return super.push((T) object);
}
}