package it.gb.salestaxes.util;

import java.awt.event.*;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;

// Methods in this class are used in order to execute generics method (passed by parameters)
public class ListenerProxies {    
  private static final Class<?>[] INTERFACES = { ActionListener.class };

  public static <T> ActionListener actionListener(final Object target,
                                                    String method, T ... argsM) {

    final Method proxied = method(target, method, argsM);
    InvocationHandler handler = new InvocationHandler() {
      @Override
      public Object invoke(Object proxy, Method method, Object[] args)
          throws Throwable {
    	  
        ActionEvent event = (ActionEvent) args[0];
        
        Object[] obj={event}; 
        for(T a: argsM) {
        	obj = appendValue(obj, a);
  	  	}

        return proxied.invoke(target, obj);
      }
    };
    return (ActionListener) Proxy.newProxyInstance(target.getClass()
        .getClassLoader(), INTERFACES, handler);
  }

  private static <T> Method method(Object target, String method, T ... argsM) {
    try {
    	int classes = 1+argsM.length;
    	Class[] classesArg = new Class[classes];
    	classesArg[0] = ActionEvent.class;
    	int i = 1;
    	for(T a: argsM) {
    		classesArg[i] = a.getClass();
    		i++;
  	  	}
      return target.getClass().getMethod(method, classesArg);
    } catch (NoSuchMethodException e) {
      throw new IllegalStateException(e);
    } catch (SecurityException e) {
      throw new IllegalStateException(e);
    }
  }
  
  private static Object[] appendValue(Object[] obj, Object newObj) {	
	ArrayList<Object> temp = new ArrayList<Object>(Arrays.asList(obj));
	temp.add(newObj);
	return temp.toArray();

  }
}
