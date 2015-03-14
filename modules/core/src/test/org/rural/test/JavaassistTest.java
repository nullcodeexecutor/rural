package org.rural.test;

import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.rural.ui.Model;

import java.lang.reflect.Method;

/**
 * Created by yuantao on 2014/8/9.
 */
public class JavaassistTest {
    public static void main(String[] args) throws Exception {
        System.out.println(Model.class.getName());
    }

    static void test() throws ClassNotFoundException {
        Class<?> clazz = Class.forName("org.rural.test.Test");
        Method[] methods = clazz.getDeclaredMethods();
        for(Method method : methods){
            System.out.println(method.getReturnType().getName());
        }
    }

    static void tes() throws NotFoundException {
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.get("org.rural.test.Test");
        CtMethod[] methods = ctClass.getDeclaredMethods();
        for(CtMethod method : methods){
            MethodInfo methodInfo = method.getMethodInfo();
            CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
            LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
            if (attr == null)  {
                return;
            }
            String[] paramNames = new String[method.getParameterTypes().length];
            int pos = Modifier.isStatic(method.getModifiers()) ? 0 : 1;
            for (int i = 0; i < paramNames.length; i++)
                paramNames[i] = attr.variableName(i + pos);
        }
    }
}

class Test{
    public void haha(String key, Integer num, boolean f, Boolean dd){
        System.out.println(key+":"+num);
    }

    public int dd(){
        return 0;
    }

    public Integer ddds(){
        return 0;
    }
}
