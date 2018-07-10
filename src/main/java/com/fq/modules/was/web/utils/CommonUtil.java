package com.fq.modules.was.web.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * @ClassName(类名) : CommonUtil
 * @Description(描述) : 辅助类
 * @author(作者) ：suyl
 */
public abstract class CommonUtil {

    private static Logger log = LoggerFactory.getLogger(CommonUtil.class);

    /**
     * @param pObj :
     * @return boolean true:为空
     * @throws :
     * @Description(功能描述) : 是否为空
     * @author(作者) ： suyl
     * @date (开发日期) : 2015年2月15日 上午8:59:56
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object pObj) {
        if (pObj == null) {
            return true;
        }
        if ("".equals(pObj)) {
            return true;
        }
        if (pObj instanceof String) {
            if (((String) pObj).length() == 0) {
                return true;
            }
        } else if (pObj instanceof Collection) {
            if (((Collection) pObj).size() == 0) {
                return true;
            }
        } else if (pObj instanceof Map) {
            if (((Map) pObj).size() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param pObj ：
     * @return boolean
     * @throws :
     * @Description(功能描述) : 是否不为空
     * @author(作者) ： suyl
     * @date (开发日期) : 2015年2月15日 上午8:59:14
     */
    @SuppressWarnings("rawtypes")
    public static boolean isNotEmpty(Object pObj) {
        if (pObj == null) {
            return false;
        }
        if ("".equals(pObj)) {
            return false;
        }
        if (pObj instanceof String) {
            if (((String) pObj).length() == 0) {
                return false;
            }
        } else if (pObj instanceof Collection) {
            if (((Collection) pObj).size() == 0) {
                return false;
            }
        } else if (pObj instanceof Map) {
            if (((Map) pObj).size() == 0) {
                return false;
            }
        }
        return true;
    }


    /**
     * @param ：
     * @return boolean
     * @throws :
     * @Description(功能描述) : 是否不为空
     * @author(作者) ： suyl
     * @date (开发日期) : 2015年2月15日 上午8:59:14
     */
    @SuppressWarnings("rawtypes")
    public static boolean isNotEmptys(Object... objs) {
        for (Object obj : objs) {
            if (obj == null) {
                return false;
            }
            if ("".equals(obj)) {
                return false;
            }
            if (obj instanceof String) {
                if (((String) obj).length() == 0) {
                    return false;
                }
            } else if (obj instanceof Collection) {
                if (((Collection) obj).size() == 0) {
                    return false;
                }
            } else if (obj instanceof Map) {
                if (((Map) obj).size() == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    // public static String fileNameEncode(String source) {
    // // return Base64.encodeBase64URLSafeString(source.getBytes());
    // return UUID.randomUUID().toString();
    // }
    //
    // public static String fileNameDecode(String source) {
    // return new String(Base64.decodeBase64(source));
    // }

    /**
     * @param bean   原数据
     * @param result 转换结果
     * @throws :
     * @Description(功能描述) : javaBean 转 map
     * @author(作者) ： suyl
     * @date (开发日期) : 2015年2月10日 下午3:39:42
     */
    public static void bean2Map(Object bean, Map<String, Object> result) {
        Method[] methods = bean.getClass().getDeclaredMethods();
        try {
            for (Method method : methods) {
                if (method.getName().startsWith("get")) {
                    String field = method.getName();
                    field = field.substring(field.indexOf("get") + 3);
                    field = field.toLowerCase().charAt(0) + field.substring(1);
                    Object value = method.invoke(bean, (Object[]) null);
                    result.put(field, null == value ? "" : value);
                }
            }
        } catch (Exception e) {
            log.error("数据转换失败");
            e.printStackTrace();
        }
    }

    /**
     * @param bean   原数据
     * @param result 转换结果
     * @throws :
     * @Description(功能描述) : javaBean 转 map
     * @author(作者) ： suyl
     * @date (开发日期) : 2015年2月10日 下午3:39:42
     */
    public static void bean2MapNotEmpty(Object bean, Map<String, Object> result) {
        Method[] methods = bean.getClass().getDeclaredMethods();
        try {
            for (Method method : methods) {
                if (method.getName().startsWith("get")) {
                    String field = method.getName();
                    field = field.substring(field.indexOf("get") + 3);
                    field = field.toLowerCase().charAt(0) + field.substring(1);
                    Object value = method.invoke(bean, (Object[]) null);
                    if (isNotEmpty(value)) {
                        result.put(field, null == value ? "" : value);
                    }
                }
            }
        } catch (Exception e) {
            log.error("数据转换失败");
            e.printStackTrace();
        }
    }

    /**
     * @param source 原对象
     * @param result 转换结果
     * @throws :
     * @Description(功能描述) : javaBean 转 javaBean
     * @author(作者) ： suyl
     * @date (开发日期) : 2015年2月10日 下午3:41:22
     */
    public static void bean2Bean(Object source, Object result) {
        Method[] smethods = source.getClass().getDeclaredMethods();
        Method[] dmethods = result.getClass().getDeclaredMethods();
        try {
            for (Method smethod : smethods) {
                if (smethod.getName().startsWith("get")) {
                    String sfield = smethod.getName();
                    sfield = sfield.substring(sfield.indexOf("get") + 3);
                    sfield = sfield.toLowerCase().charAt(0) + sfield.substring(1);
                    Object value = smethod.invoke(source, (Object[]) null);
                    if (!CommonUtil.isEmpty(value)) {
                        for (Method dmethod : dmethods) {
                            if (dmethod.getName().startsWith("set")) {
                                String dfield = dmethod.getName();
                                dfield = dfield.substring(dfield.indexOf("set") + 3);
                                dfield = dfield.toLowerCase().charAt(0) + dfield.substring(1);
                                if (dfield.equals(sfield)) {
                                    dmethod.invoke(result, new Object[]{value});
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("数据转换失败");
            e.printStackTrace();
        }
    }

    /**
     * 方法描述:将class2中的属性值赋值给class1，如果class1属性有值，则不覆盖   前提条件是有相同的属性名
     *
     * @param :(参数及说明)class1:被赋值对象 class2:数据的对象
     * @param class1
     * @param class2
     * @return ：(返回值说明)No return value
     * @author ：(作者) suyl
     * @exception:(异常说明)exceptions No exceptions thrown
     */
    public static void copyBean(Object class1, Object class2) {
        try {
            Class<?> clazz1 = class1.getClass();
            Class<?> clazz2 = class2.getClass();
            // 得到method方法
            Method[] method1 = clazz1.getMethods();
            Method[] method2 = clazz2.getMethods();

            int length1 = method1.length;
            int length2 = method2.length;
            if (length1 != 0 && length2 != 0) {
                // 创建一个get方法数组，专门存放class2的get方法。
                Method[] get = new Method[length2];
                for (int i = 0, j = 0; i < length2; i++) {
                    if (method2[i].getName().indexOf("get") == 0) {
                        get[j] = method2[i];
                        ++j;
                    }
                }

                for (int i = 0; i < get.length; i++) {
                    if (get[i] == null)// 数组初始化的长度多于get方法，所以数组后面的部分是null
                        continue;
                    // 得到get方法的值，判断时候为null，如果为null则进行下一个循环
                    Object value = get[i].invoke(class2, new Object[]{});
                    if (null == value)
                        continue;
                    // 得到get方法的名称 例如：getXxxx
                    String getName = get[i].getName();
                    // 得到set方法的时候传入的参数类型，就是get方法的返回类型
                    Class<?> paramType = get[i].getReturnType();
                    Method getMethod = null;
                    try {
                        // 判断在class1中时候有class2中的get方法，如果没有则抛异常继续循环
                        getMethod = clazz1.getMethod(getName, new Class[]{});
                    } catch (NoSuchMethodException e) {
                        continue;
                    }
                    // class1的get方法不为空并且class1中get方法得到的值为空，进行赋值，如果class1属性原来有值，则跳过
                    if (null == getMethod || null != getMethod.invoke(class1, new Object[]{}))
                        continue;
                    // 通过getName 例如getXxxx 截取后得到Xxxx，然后在前面加上set，就组装成set的方法名
                    String setName = "set" + getName.substring(3);
                    // 得到class1的set方法，并调用
                    Method setMethod = clazz1.getMethod(setName, paramType);
                    setMethod.invoke(class1, value);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * @param map    原对象
     * @param result 转换结果
     * @throws :
     * @Description(功能描述) : map 转换成 bean
     * @author(作者) ： suyl
     * @date (开发日期) : 2015年2月10日 下午3:42:18
     */
    public static void map2Bean(Map<String, Object> map, Object result) {
        Method[] methods = result.getClass().getDeclaredMethods();
        for (Method method : methods) {
            try {
                if (method.getName().startsWith("set")) {
                    String field = method.getName();
                    field = field.substring(field.indexOf("set") + 3);
                    field = field.toLowerCase().charAt(0) + field.substring(1);
                    method.invoke(result, new Object[]{map.get(field)});
                }
            } catch (Exception e) {
                log.error("数据转换失败");
                e.printStackTrace();
            }
        }
    }

    /**
     * @return String
     * @throws :
     * @Description(功能描述) : 生成5位随机字符
     * @author(作者) : suyl
     * @date (开发日期) : 2016年2月22日 下午4:36:22
     */
    public static String grow5num() {
        StringBuffer sb = new StringBuffer();
        String a = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char[] rands = new char[5];
        for (int i = 0; i < rands.length; i++) {
            int rand = (int) (Math.random() * a.length());
            rands[i] = a.charAt(rand);
        }
        for (int i = 0; i < rands.length; i++) {
            sb.append(rands[i]);
        }
        return sb.toString();
    }

    /**
     * 通过父节点得到所有子节点
     * @param list 需要组装的数据
     * @param parentId 父节点id
     * @param pidName 父节点名称
     * @param idName id名称
     * @return
     */
    public static List<Object> getChildenId(List<Object> objects, List<Map<String,Object>> list, Object parentId, String pidName, String idName){
        if(CommonUtil.isNotEmpty(list)){
            for(Map<String,Object> map:list){
                if(CommonUtil.isNotEmpty(map.get(pidName))){
                    Long pid = (Long)map.get(pidName);
                    if(pid.equals(parentId)){
                        Long id = (Long) map.get(idName);
                        if(!objects.contains(id)){
                            objects.add(id);
                        }
                        if(!objects.contains(parentId)){
                            objects.add(parentId);
                        }
                        getChildenId(objects,list, id,pidName,idName);
                    }
                }else {
                    if(!objects.contains(parentId)){
                        objects.add(parentId);
                    }
                }
            }
        }
        return objects;
    }
}
