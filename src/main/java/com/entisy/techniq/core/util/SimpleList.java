package com.entisy.techniq.core.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SimpleList<T>
{
	private final List<T> list = new ArrayList<>();

    @SuppressWarnings("unchecked")
	public SimpleList(T... objects)
    {
        list.addAll(Arrays.asList(objects));
    }

    public SimpleList(Supplier<T> sup)
    {
        //TODO:
    }

    @SuppressWarnings("unchecked")
	public void append(T... objects) 
    {
        list.addAll(Arrays.asList(objects));
    }

    @SuppressWarnings("unchecked")
	public void remove(T... objects) 
    {
    	list.removeAll(Arrays.asList(objects)); 
    }

    public boolean contains(T object)
    {
        return list.contains(object);
    }
    
    @SuppressWarnings("unchecked")
	public T[] get()
    {
    	return (T[]) Array.newInstance(list.get(0).getClass(), list.size());
    }
    
    public List<T> list()
    {
    	return list;
    }
    
    public boolean isEmpty()
    {
    	return list.size() <= 0;
    }
    
    public int size()
    {
    	return list.size();
    }
	
	public boolean matches(Object x)
	{
		return list.stream().anyMatch(p -> p.equals(x));
	}

    public T get(int index) {
        return list.get(index);
    }

	public void forEach(Consumer<? super T> e) {
		for (T t : list)
		{
			e.accept(t);
		}
	}

    public static SimpleList createNew() {
        return new SimpleList<>();
    }
}
