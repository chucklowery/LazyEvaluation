
import java.util.Iterator;

/*
 * Copyright (C) 2013 Payment Alliance International. All Rights Reserved.
 * 
 * This software is the proprietary information of Payment Alliance International.
 * Use is subject to license terms.
 * 
 * Name: LazyChain.java 
 * Created: Oct 3, 2013 3:18:34 PM
 * Author: Chuck Lowery <chuck.lowery @ gopai.com>
 */

/**
 *
 * @author Chuck Lowery <chuck.lowery @ gopai.com>
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
