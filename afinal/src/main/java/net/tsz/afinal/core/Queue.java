package net.tsz.afinal.core;

/**
 * Created by LiuWeiJie on 2015/7/25 0025.
 * Email:1031066280@qq.com
 */
import java.util.Collection;

public interface Queue<E> extends Collection<E> {
    boolean add(E var1);

    boolean offer(E var1);

    E remove();

    E poll();

    E element();

    E peek();
}

