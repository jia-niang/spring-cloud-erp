package com.kabunx.erp.util;

import com.kabunx.erp.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.HibernateValidator;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Pattern;

@Slf4j
public class ObjectUtils {

    /**
     * hibernate注解验证
     */
    private static Validator validator = null;

    /**
     * 对象是否为空
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof CharSequence) {
            return isEmpty((CharSequence) obj);
        } else if (obj instanceof Collection) {
            return isEmpty((Collection<?>) obj);
        } else if (obj instanceof Map) {
            return isEmpty((Map<?, ?>) obj);
        } else if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        }
        return false;
    }

    /**
     * 字符串是否为空
     */
    public static boolean isEmpty(CharSequence value) {
        return StringPlusUtils.isBlank(value);
    }

    /**
     * 字符串是否为空
     */
    public static boolean isEmpty(String value) {
        return StringPlusUtils.isBlank(value);
    }

    /**
     * 集合为空
     */
    public static <T> boolean isEmpty(Collection<T> list) {
        return list == null || list.isEmpty();
    }

    /**
     * 字符串数组是否不为空
     */
    public static boolean isEmpty(String[] values) {
        return values == null || values.length == 0;
    }

    /**
     * Map为空
     */
    public static <T, F> boolean isEmpty(Map<T, F> obj) {
        return obj == null || obj.isEmpty();
    }

    public static boolean isEmpty(boolean[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(byte[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(char[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(double[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(float[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(int[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(long[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(short[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }


    /**
     * 字符串不为空，或者不是所有字符都为whitespace字符
     */
    public static boolean notEmpty(CharSequence value) {
        return StringPlusUtils.isNotBlank(value);
    }

    /**
     * 字符串不为空，或者不是所有字符都为whitespace字符
     */
    public static boolean notEmpty(String value) {
        return StringPlusUtils.isNotBlank(value);
    }

    /**
     * 字符串数组是否不为空
     */
    public static boolean notEmpty(String[] values) {
        return values != null && values.length > 0;
    }

    /**
     * 集合不为空
     */
    public static <T> boolean notEmpty(Collection<T> list) {
        return list != null && !list.isEmpty();
    }

    /**
     * Map不为空
     */
    public static <T, F> boolean notEmpty(Map<T, F> obj) {
        return obj != null && !obj.isEmpty();
    }

    /**
     * 对象不为空且不为0
     */
    public static boolean notEmptyOrZero(Long longObj) {
        return longObj != null && longObj != 0;
    }

    /**
     * 对象不为空且不为0
     */
    public static boolean notEmptyOrZero(Integer intObj) {
        return intObj != null && intObj != 0;
    }

    /**
     * 集合中是否包含指定元素
     *
     * @param collection 集合
     * @param target     查找元素
     * @return 集合为空或者不包含元素，则返回false
     */
    public static <T> boolean contains(Collection<T> collection, T target) {
        return collection != null && collection.contains(target);
    }

    /**
     * 集合中是否不包含指定元素
     *
     * @param collection 集合
     * @param target     查找元素
     * @return 集合为空或者包含元素，则返回false
     */
    public static <T> boolean notContains(Collection<T> collection, T target) {
        return collection != null && !collection.contains(target);
    }

    /**
     * 判断是否为数字（允许小数点）
     *
     * @return true Or false
     */
    public static boolean isNumber(String str) {
        String regex = "^(-?[1-9]\\d*\\.?\\d*)|(-?0\\.\\d*[1-9])|(-?[0])|(-?[0]\\.\\d*)$";
        return str.matches(regex);
    }

    /**
     * 判断是否为正确的邮件格式
     *
     * @return boolean
     */
    public static boolean isEmail(String str) {
        if (isEmpty(str)) {
            return false;
        }
        return str.matches("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$");
    }

    /**
     * 判断字符串是否为电话号码
     *
     * @return boolean
     */
    public static boolean isPhone(String str) {
        if (isEmpty(str)) {
            return false;
        }
        boolean valid = str.matches("^1\\d{10}$");
        if (!valid) {
            valid = str.matches("^[0|4]\\d{2,3}-?\\d{7,8}$");
        }
        return valid;
    }

    /**
     * 对参数值做安全检查
     */
    public static void securityCheck(String... paramValues) throws BizException {
        if (isEmpty(paramValues)) {
            return;
        }
        for (String param : paramValues) {
            if (!ObjectUtils.isValidSqlParam(param)) {
                throw new BizException("非法的参数: " + param);
            }
        }
    }

    /**
     * 是否为合法的数据库列参数（orderBy等参数安全检查）
     */
    private static final Pattern PATTERN = Pattern.compile("^[A-Za-z_][\\w.:]*$");

    public static boolean isValidSqlParam(String columnStr) {
        if (isEmpty(columnStr)) {
            return true;
        }
        return PATTERN.matcher(columnStr).matches();
    }

    /**
     * 是否boolean值范围
     */
    private static final Set<String> TRUE_SET = new HashSet<>(Arrays.asList(
            "true", "是", "y", "yes", "1"
    ));

    private static final Set<String> FALSE_SET = new HashSet<>(Arrays.asList(
            "false", "否", "n", "no", "0"
    ));

    /**
     * 是否为boolean类型
     */
    public static boolean isValidBoolean(String value) {
        if (value == null) {
            return false;
        }
        value = StringPlusUtils.trim(value).toLowerCase();
        return TRUE_SET.contains(value) || FALSE_SET.contains(value);
    }

    /**
     * 转换为boolean类型, 并判定是否为true
     */
    public static boolean isTrue(String value) {
        if (value == null) {
            return false;
        }
        value = StringPlusUtils.trim(value).toLowerCase();
        return TRUE_SET.contains(value);
    }

    /**
     * 判定两个对象是否不同类型或不同值
     */
    public static boolean notEquals(Object source, Object target) {
        return !equals(source, target);
    }

    /**
     * 判定两个对象是否类型相同值相等
     */
    public static <T> boolean equals(T source, T target) {
        if (source == target) {
            return true;
        } else if (source == null || target == null) {
            return false;
        } else if (source instanceof Class && target instanceof Class) {
            return ((Class<?>) source).getName().equals(((Class<?>) target).getName());
        }
        // 不为空，调用equals比较
        else if (source instanceof Comparable) {
            return (source).equals(target);
        } else if (source instanceof Collection) {
            Collection<?> sourceList = (Collection<?>) source, targetList = (Collection<?>) target;
            // size不等
            if (sourceList.size() != targetList.size()) {
                return false;
            }
            // 已经确定两个集合的数量相等，如果某个值不存在，则必定不相等
            for (Object obj : sourceList) {
                if (!targetList.contains(obj)) {
                    return false;
                }
            }
            return true;
        } else if (source instanceof Map) {
            Map<?, ?> sourceMap = (Map<?, ?>) source, targetMap = (Map<?, ?>) target;
            if (ObjectUtils.isEmpty(sourceMap) && ObjectUtils.isEmpty(targetMap)) {
                return true;
            }
            if (sourceMap.size() != targetMap.size()) {
                return false;
            }
            // entry遍历效率更高
            for (Map.Entry<?, ?> entry : sourceMap.entrySet()) {
                if (notEquals(entry.getValue(), targetMap.get(entry.getKey()))) {
                    return false;
                }
            }
            return true;
        } else {
            log.warn("暂未实现类型 " + source.getClass().getName() + "-" + target.getClass().getName() + " 的比对！");
            return false;
        }
    }

    /**
     * 列表others中是否包含source
     *
     * @param source 查询对象
     * @param others 可能值列表
     * @param <T>    类型
     * @return others列表为空或者不包含source，则返回false
     */
    @SafeVarargs
    public static <T> boolean anyEquals(T source, T... others) {
        if (isEmpty(others)) {
            return false;
        }
        for (T other : others) {
            if (equals(source, other)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 模糊对比是否相等（类型不同的转成String对比）
     */
    public static boolean fuzzyEqual(Object source, Object target) {
        if (equals(source, target)) {
            return true;
        }
        // Boolean-String类型
        if (source instanceof Boolean && target instanceof String) {
            return (boolean) source == ObjectUtils.isTrue((String) target);
        }
        if (target instanceof Boolean && source instanceof String) {
            return (boolean) target == ObjectUtils.isTrue((String) source);
        }
        // Date-String类型
        else if (source instanceof Date && target instanceof String) {
            return DatePlusUtils.getDateTime((Date) source).equals(target) || DatePlusUtils.getDate((Date) source).equals(target);
        } else if (target instanceof Date && source instanceof String) {
            return DatePlusUtils.getDateTime((Date) target).equals(source) || DatePlusUtils.getDate((Date) target).equals(source);
        } else {
            return String.valueOf(source).equals(String.valueOf(target));
        }
    }

    /**
     * 错误信息收集
     */
    public static HashMap<String, ArrayList<String>> getBindingErrors(BindingResult bindingResult) {
        HashMap<String, ArrayList<String>> errors = new HashMap<>();
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                ArrayList<String> fErrors = errors.computeIfAbsent(fieldError.getField(), k -> new ArrayList<>());
                fErrors.add(fieldError.getDefaultMessage());
            }
        }
        return errors;
    }


    /**
     * 基于Bean中的validator注解校验
     *
     * @return 违法约束的集合
     */
    public static <T> Set<ConstraintViolation<T>> validateBean(T obj, Class<?>... groups) {
        if (validator == null) {
            validator = Validation.byProvider(HibernateValidator.class).configure().failFast(false).buildValidatorFactory().getValidator();
        }
        return validator.validate(obj, groups);
    }

    /**
     * 基于Bean中的validator注解校验
     *
     * @return 违法约束的消息内容
     */
    public static <T> String validateBeanErrMsg(T obj, Class<?>... groups) {
        Set<ConstraintViolation<T>> errors = validateBean(obj, groups);
        if (isEmpty(errors)) {
            return null;
        }
        List<String> allErrors = new ArrayList<>(errors.size());
        for (ConstraintViolation<T> err : errors) {
            allErrors.add(err.getMessage());
        }
        return StringPlusUtils.join(allErrors);
    }
}
