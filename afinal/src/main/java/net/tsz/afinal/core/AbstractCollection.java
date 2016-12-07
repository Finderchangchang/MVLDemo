package net.tsz.afinal.core;

/**
 * Created by LiuWeiJie on 2015/7/25 0025.
 * Email:1031066280@qq.com
 */
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;

public abstract class AbstractCollection<E> implements Collection<E> {
    protected AbstractCollection() {
    }

    public boolean add(E object) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(Collection<? extends E> collection) {
        boolean result = false;
        Iterator it = collection.iterator();

        while(it.hasNext()) {
            if(this.add((E) it.next())) {
                result = true;
            }
        }

        return result;
    }

    public void clear() {
        Iterator it = this.iterator();

        while(it.hasNext()) {
            it.next();
            it.remove();
        }

    }

    public boolean contains(Object object) {
        Iterator it = this.iterator();
        if(object != null) {
            while(it.hasNext()) {
                if(object.equals(it.next())) {
                    return true;
                }
            }
        } else {
            while(it.hasNext()) {
                if(it.next() == null) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean containsAll(Collection<?> collection) {
        Iterator it = collection.iterator();

        while(it.hasNext()) {
            if(!this.contains(it.next())) {
                return false;
            }
        }

        return true;
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

    public abstract Iterator<E> iterator();

    public boolean remove(Object object) {
        Iterator it = this.iterator();
        if(object != null) {
            while(it.hasNext()) {
                if(object.equals(it.next())) {
                    it.remove();
                    return true;
                }
            }
        } else {
            while(it.hasNext()) {
                if(it.next() == null) {
                    it.remove();
                    return true;
                }
            }
        }

        return false;
    }

    public boolean removeAll(Collection<?> collection) {
        boolean result = false;
        Iterator it = this.iterator();

        while(it.hasNext()) {
            if(collection.contains(it.next())) {
                it.remove();
                result = true;
            }
        }

        return result;
    }

    public boolean retainAll(Collection<?> collection) {
        boolean result = false;
        Iterator it = this.iterator();

        while(it.hasNext()) {
            if(!collection.contains(it.next())) {
                it.remove();
                result = true;
            }
        }

        return result;
    }

    public abstract int size();

    public Object[] toArray() {
        int size = this.size();
        int index = 0;
        Iterator it = this.iterator();

        Object[] array;
        for(array = new Object[size]; index < size; array[index++] = it.next()) {
            ;
        }

        return array;
    }

    public <T> T[] toArray(T[] contents) {
        int size = this.size();
        int index = 0;
        if(size > contents.length) {
            Class entry = contents.getClass().getComponentType();
            contents = (T[]) Array.newInstance(entry, size);
        }

        Object var6;
        for(Iterator var5 = this.iterator(); var5.hasNext(); contents[index++] = (T) var6) {
            var6 = (Object)var5.next();
        }

        if(index < contents.length) {
            contents[index] = null;
        }

        return contents;
    }

    public String toString() {
        if(this.isEmpty()) {
            return "[]";
        } else {
            StringBuilder buffer = new StringBuilder(this.size() * 16);
            buffer.append('[');
            Iterator it = this.iterator();

            while(it.hasNext()) {
                Object next = it.next();
                if(next != this) {
                    buffer.append(next);
                } else {
                    buffer.append("(this Collection)");
                }

                if(it.hasNext()) {
                    buffer.append(", ");
                }
            }

            buffer.append(']');
            return buffer.toString();
        }
    }
}

