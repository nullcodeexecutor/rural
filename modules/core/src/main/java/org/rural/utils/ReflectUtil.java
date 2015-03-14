package org.rural.utils;

import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuantao on 2014/8/9.
 */
public class ReflectUtil {
    private final static Log log = LogFactory.getLog(ReflectUtil.class);
    private ReflectUtil(){
    }

    /**
     * 返回一个类的公有非static方法
     * @param clazz
     * @return
     */
    public static List<Method> getPublicMethods(Class<?> clazz){
        Method[] methods = clazz.getDeclaredMethods();
        List<Method> publicMethods = new ArrayList<Method>();
        for(Method method : methods){
            if(Modifier.isPublic(method.getModifiers()) && !Modifier.isStatic(method.getModifiers())){
                publicMethods.add(method);
            }
        }
        return publicMethods;
    }

    /**
     * 通过Javaassist获取方法的参数名
     */
    public static String[] getMethodArgNames(String className, String methodName){
        List<String> names = new ArrayList<String>();
        try {
            ClassPool classPool = ClassPool.getDefault();
            classPool.insertClassPath(new ClassClassPath(ReflectUtil.class));
            CtClass ctClass = classPool.getCtClass(className);
            CtMethod method = ctClass.getDeclaredMethod(methodName);
            MethodInfo methodInfo = method.getMethodInfo();
            CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
            LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
            if (attr == null)  {
                return (String[])names.toArray();
            }
            int argNum = method.getParameterTypes().length;
            int pos = javassist.Modifier.isStatic(method.getModifiers()) ? 0 : 1;
            for (int i=0; i<argNum; i++) {
                names.add(attr.variableName(i + pos));
            }
        } catch (NotFoundException e) {
            log.error(e);
        }
        return names.toArray(new String[0]);
    }

}
