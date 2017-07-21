import cn.irving.zhao.util.poi.annotation.sheet.Sheet;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;


public class Main {
    public static void main(String[] args) throws Exception {
//        Entity entity = new Entity();
//        entity.setS1("测试内容");
//        entity.setListStr(Arrays.asList("1", "2", "3", "4", "5"));
//        entity.setvListStr(Arrays.asList("v1", "v2", "v3", "v4", "v5"));
//        entity.setRegion("region");
//        entity.setMergeds(Arrays.asList("re1", "re2", "re3", "re4", "re5"));
////        new POIUtil().writeExcel(entity, "E:\\basepath\\a.xlsx", OutputType.DOCX);
//
//        System.out.println(entity.getClass().getAnnotation(Sheet.class).name());
//        System.out.println(Entity2.class.getAnnotation(Sheet.class).name());
//
//        Field field = entity.getClass().getDeclaredField("listStr");
//
//        Type type = field.getGenericType();
//        if (ParameterizedType.class.isAssignableFrom(type.getClass())) {
//            ParameterizedType pType = (ParameterizedType) type;
//            System.out.println(pType.getActualTypeArguments()[0]==String.class);
//        }
//        long time=System.currentTimeMillis();
//        PropertyUtils.getProperty(entity,"mergeds");
//        System.out.println(System.currentTimeMillis()-time);
//        for(int i=0;i<10000;i++) {
//            MethodUtils.getAccessibleMethod(Entity.class, "getMergeds", new Class[]{});
//        }
//        System.out.println(System.currentTimeMillis()-time);
//        Logger logger= LoggerFactory.getLogger(Main.class);
//        logger.error("测试格式化{}：{}","123","456","456");
//        logger.info("测试");
        System.out.println(String.class.isInstance(123));
    }
}
