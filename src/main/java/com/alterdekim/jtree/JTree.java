package com.alterdekim.jtree;

import java.util.Iterator;
import java.util.List;

public abstract class JTree<E> {

    abstract void setParent( JTree<E> p );

    public abstract boolean hasParent();

    public abstract E getKey();

    public abstract JTree<E> getParent();

    public abstract List<E> getNonEndPoints( boolean ignoreFirst );

    public abstract List<E> getEndPoints();

    public abstract JTree<E> getChild( int index );

    public abstract int size();

    public abstract int recursiveSize();

    public abstract boolean isEmpty();

    public abstract boolean contains(JTree<E> o);

    public abstract boolean containsRecursive(JTree<E> o);

    public abstract boolean containsKey(E key);

    public abstract boolean containsKeyRecursive(E key);

    public abstract Iterator<JTree<E>> iterator();

    public abstract boolean addChild( JTree<E> child );

    public abstract boolean removeChild( int index );

    public abstract boolean containsAll(List<JTree<E>> c);

    public abstract boolean containsAllRecursive(List<JTree<E>> c);

    public abstract boolean addAllChildren(List<JTree<E>> c);

    public abstract boolean removeAll(List<JTree<E>> c);

    public abstract boolean retainAll(List<JTree<E>> c);

    public abstract boolean removeAllRecursive(List<JTree<E>> c);

    public abstract boolean retainAllRecursive(List<JTree<E>> c);

    public abstract void clear();

    public abstract boolean isStrict();
}
