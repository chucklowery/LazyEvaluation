/*
 * Copyright (C) 2013 Payment Alliance International. All Rights Reserved.
 * 
 * This software is the proprietary information of Payment Alliance International.
 * Use is subject to license terms.
 * 
 * Name: LazyChainIterator.java 
 * Created: Oct 3, 2013 3:19:06 PM
 * Author: Chuck Lowery <chuck.lowery @ gopai.com>
 */

/**
 *
 * @author Chuck Lowery <chuck.lowery @ gopai.com>
 */
/*
 * Copyright (C) 2013 Payment Alliance International. All Rights Reserved.
 * 
 * This software is the proprietary information of Payment Alliance International.
 * Use is subject to license terms.
 * 
 * Name: LazyChainIterator.java 
 * Created: Sep 18, 2013 10:30:17 AM
 * Author: Chuck Lowery <chuck.lowery @ gopai.com>
 */

import java.util.Iterator;
import java.util.Stack;

/**
 *
 * @author Chuck Lowery <chuck.lowery @ gopai.com>
 */
class LazyChainIterator<Type> implements Iterator<Type> {

    private static final Object EMPTY = new Object();
    private Object lookAheadValue;
    private Stack<Iterator<?>> stack;

    public LazyChainIterator(Iterator<?> root) {
        stack = new Stack<Iterator<?>>();
        stack.push(root);
        init();
    }

    public LazyChainIterator(Iterator<?> root, boolean willCallInit) {
        stack = new Stack<Iterator<?>>();
        stack.push(root);
        if (!willCallInit) {
            init();
        }
    }

    protected void init() {
        _next();
    }

    @Override
    public boolean hasNext() {
        return lookAheadValue != EMPTY;
    }

    @Override
    public Type next() {
        Object o = lookAheadValue;
        _next();
        return (Type) o;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    public Iterator<?> currentIterator() {
        while (!stack.isEmpty() && !stack.peek().hasNext()) {
            stack.pop();
        }
        if (stack.isEmpty()) {
            return null;
        } else {
            return stack.peek();
        }
    }

    private void _next() {
        Iterator<?> currentIter = currentIterator();
        if (currentIter == null) {
            lookAheadValue = EMPTY;
            return;
        }
        int initialSize = stack.size();
        lookAheadValue = evaluate(currentIter.next(), stack);
        if (initialSize != stack.size()) {
            _next();
        }

    }

    /**
     * Converts the value object into the appropriate type or returns
     * null and manipulates the stack.
     * 
     * @param value
     * @return 
     */
    protected Object evaluate(Object value, Stack<Iterator<?>> stack) {
        if (value instanceof Iterator) {
            stack.push((Iterator) value);
            return null;
        }
        if (value instanceof Iterable) {
            stack.push(((Iterable) value).iterator());
            return null;
        }
        return value;
    }
}
