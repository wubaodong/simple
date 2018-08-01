package tk.mybatis.simple.plugin;

import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Statement;
import java.util.*;

@Intercepts(
        @Signature(
                type = ResultSetHandler.class,
                method = "handleResultSets",
                args = {Statement.class}
        )
)
@SuppressWarnings({"unchecked", "rawtypes"})
public class CamelHumpInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        List<Object> list = (List<Object>) invocation.proceed();
        for (Object object :
                list) {
            if (object instanceof Map) {
                processMap((Map)object);
            } else {
                break;
            }
        }
        return list;
    }

    private void processMap(Map<String, Object> map) {
        Set<String> keySet = new HashSet<String>(map.keySet());
        for (String key :
                keySet) {
            if (key.charAt(0) >= 'A'
            && key.charAt(0) <= 'Z'
            || key.contains("_")) {
                Object value = map.get(key);
                map.remove(key);
                map.put(underlineToCamelhump(key), value);
            }
        }
    }

    private String underlineToCamelhump(String inputString) {
        StringBuilder sb = new StringBuilder();

        boolean nextUpperCase = false;
        for (int i = 0; i < inputString.length(); i++) {
            char c = inputString.charAt(i);
            if (c == '_') {
                //处理下划线
                if (sb.length() > 0) {
                    //不是第一个字符
                    //标记下一个字符应该改为大写
                    nextUpperCase = true;
                }
            } else {
                //处理非下划线
                if (nextUpperCase) {
                    sb.append(Character.toUpperCase(c));
                    nextUpperCase = false;
                } else {
                    //将首字母的大写改为小写
                    sb.append(Character.toLowerCase(c));
                }
            }
        }
        return sb.toString();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
