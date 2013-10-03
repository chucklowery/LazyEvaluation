import java.util.Iterator;
import java.util.Stack;

/**
 *
 * @author Chuck Lowery <chuck.lowery @ gmail.com>
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
