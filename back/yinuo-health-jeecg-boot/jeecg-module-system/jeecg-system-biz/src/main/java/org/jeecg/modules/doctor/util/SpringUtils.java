package org.jeecg.modules.doctor.util;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
* 
*
* @author lihaoran 
* @date 10/13/23 1:51 AM 
*/
@Component
public class SpringUtils implements BeanFactoryPostProcessor {

    private static ConfigurableListableBeanFactory beanFactory;

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    /**
     * 通过name获取 Bean对象
     * @param name
     * @return Object 一个以所给名字注册的bean实例
     */
    public static  <T> T getBean(String name) {
        return (T) beanFactory.getBean(name);
    }

    /**
     * 通过class获取Bean对象
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz){
        return beanFactory.getBean(clazz);
    }

    /**
     * 如果BeanFactory包含一个与所给名称匹配的bean对象，则返回true
     * @param name
     * @return
     */
    public static boolean containsBean(String name){
        return beanFactory.containsBean(name);
    }

    /**
     * 判断bean对象是一个singleton还是一个prototype
     * @param name
     * @return
     * @throws NoSuchBeanDefinitionException
     */
    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException{
        return beanFactory.isSingleton(name);
    }

    /**
     * 通过 name获取 bean 的类型
     * @param name
     * @return
     * @throws NoSuchBeanDefinitionException
     */
    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException{
        return beanFactory.getType(name);
    }

    /**
     * 通过 name 获取 bean定义的别名
     * @param name
     * @return
     */
    public static String[] getAliases(String name){
        return beanFactory.getAliases(name);
    }
}
