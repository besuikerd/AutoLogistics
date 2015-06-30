package com.besuikerd.autologistics.common.lib.util.functional;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.besuikerd.autologistics.common.BLogger;
import com.google.common.base.Function;

public class FunctionalUtils {
	/**
	 * function instance that returns the Class of the Object given as an argument(i.e. obj.getClass())
	 */
	public static final Function<Object, Class> functionGetClass = new Function<Object, Class>() {
		@Override
		public Class apply(Object input) {
			return input.getClass();
		}
	};
	
	/**
	 * Applies a function that exists in the given object and returns the result of executing that function
	 * 
	 * precondition: (Class c), (Function f) => [Ec Ax, y, c(x) == c(y)] && [Ec.f, f.getName() == name ] (all classes are equal in the given array) and (there exists a function f within c with the given name)
	 */
	public static final <A,B> Function<A, B> functionApplyFunction(final String name, Class<A> from, Class<B> to, final Object... params){
				
		return new Function<A, B>(){
			private Method m;
			
			@Override
			public B apply(A input) {
				B result = null;
				
				if(m == null){
					try{
						m = input.getClass().getDeclaredMethod(name);
					} catch (NoSuchMethodException e) {
						BLogger.error("Method %s does not exist in class %s", name, input.getClass().getName());
					} catch (SecurityException e) {
						BLogger.error("not allowed to obtain fields from classes");
						e.printStackTrace();
					}
				}
				
				try {
					result = (B) m.invoke(input, params);
				} catch (SecurityException e) {
					BLogger.error("not allowed to obtain fields from classes");
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				
				return result;
			}
		};
	};
	
	/**
	 * all function that can be folded over an array (check if all elements fulfill the given condition)
	 */
	public static final <A> ABAFunction<Boolean, A> functionAll(final Function<A, Boolean> condition){
		return new ABAFunction<Boolean, A>() {
			@Override
			public Boolean apply(Boolean a, A b) {
				return a && condition.apply(b);
			}
		};
	}
	
	/**
	 * any function that can be folded over an array (check if any element fulfills the given condition)
	 */
	public static final <A> ABAFunction<Boolean, A> functionAny(final Function<A, Boolean> condition){
		return new ABAFunction<Boolean, A>() {
			@Override
			public Boolean apply(Boolean a, A b) {
				return a || condition.apply(b);
			}
		};
	}
	
	
	public static <A, B> B[] map(Class<B> b, A[] input, Function<A,B> f){
		B[] result = (B[]) Array.newInstance(b, input.length);
		for(int i = 0 ; i < result.length ; i ++){
			result[i] = f.apply(input[i]);
		}
		return result;
	}
	
	/**
	 * foldl :: a -> [b] -> (a -> b -> a) -> a
	 */
	public static <A,B> A foldl (A first, B[] input, ABAFunction<A,B> f){
		for(B b : input){
			first = f.apply(first, b);
		}
		return first;
	}
	
	/**
	 * foldl1 :: [a] -> (a -> a -> a) -> a
	 */
	public static <A> A foldl1 (A[] input, ABAFunction<A,A> f){
		A first = null;
		for(A a : input){
			if(first == null){
				first = a; 
			} else{
				first = f.apply(first, a);
			}
		}
		return first;
	}
	
	/**
	 * foldr :: b -> [a] -> (a -> b -> b) -> b
	 */
	public static <A, B> B foldr(B first, A[] input, ABBFunction<A,B> f){
		for(int i = input.length - 1 ; i >= 0 ; i--){
			first = f.apply(first, input[i]);
		}
		return first;
	}
	
	/**
	 * ABAFunction :: a -> b -> a
	 */
	public static interface ABAFunction<A,B>{
		public A apply(A a, B b);
	}
	
	/**
	 * ABBFunction :: a -> b -> b
	 */
	public static interface ABBFunction<A,B>{
		public B apply(B b, A a);
	}
}
