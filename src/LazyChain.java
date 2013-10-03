import java.util.Iterator;

/**
 *
 * @author Chuck Lowery <chuck.lowery @ gmail.com>
 */
public class LazyChain<Type> implements Iterable<Type> {

    private Iterator<?> root;

    public LazyChain(Iterator<?> root) {
        this.root = root;
    }

    public LazyChain(Iterable<?> root) {
        this(root.iterator());
    }
    
    protected Iterator<?> getRoot() {
        return root;
    }

    public Iterator<Type> iterator() {
        return new LazyChainIterator(root);
    }
}
