import cn.irving.zhao.util.poi.POIUtil;
import cn.irving.zhao.util.poi.enums.OutputType;
import entity.DemoWorkbook;
import entity.Sheet1;
import entity.Sheet2;

import java.util.Arrays;

public class Main<T, A> {
    public static void main(String[] args) throws Exception {
//        Entity entity = new Entity();
//        entity.setS1("测试内容");
//        entity.setListStr(Arrays.asList("1", "2", "3", "4", "5"));
//        entity.setvListStr(Arrays.asList("v1", "v2", "v3", "v4", "v5"));
//        entity.setRegion("region");
//        entity.setMergeds(Arrays.asList("re1", "re2", "re3", "re4", "re5"));
////        new POIUtil().writeExcel(entity, "E:\\basepath\\a.xlsx", OutputType.XLSX);
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
//        System.out.println(String.class.isInstance(123));
//        List<String> stringList=new ArrayList<>();
//        TypeVariable[] types = stringList.getClass().getTypeParameters();
//        for (TypeVariable type : types) {
//            System.out.println(type);
//            System.out.println(type.getClass());
//        }
//        Field field = Main.class.getDeclaredField("list");
//        Type mapMainType = field.getGenericType();
//        if (mapMainType instanceof ParameterizedType) {
//            // 执行强制类型转换
//            ParameterizedType parameterizedType = (ParameterizedType) mapMainType;
//            // 获取基本类型信息，即Map
//            Type basicType = parameterizedType.getRawType();
//            System.out.println("基本类型为：" + basicType);
//            // 获取泛型类型的泛型参数
//            Type[] types = parameterizedType.getActualTypeArguments();
//            for (int i = 0; i < types.length; i++) {
//                System.out.println("第" + (i + 1) + "个泛型类型是：" + types[i]);
//            }
//        }

        //TODO 构建数据
        DemoWorkbook demoWorkbook = new DemoWorkbook();
        Sheet1 sheet1 = new Sheet1();
        Sheet2 sheet2 = new Sheet2();

        sheet1.setStr("sheet1-str");
        sheet1.setListStr1(Arrays.asList("sheet-1-list1-1", "sheet-1-list1-2", "sheet-1-list1-3"));
        sheet1.setListStr2(Arrays.asList("sheet-1-list2-1", "sheet-1-list2-2", "sheet-1-list2-3"));
        sheet1.setSheet2(sheet2);

        sheet2.setStr("sheet2-str");
        sheet2.setListStr1(Arrays.asList("sheet-2-list1-1", "sheet-2-list1-2", "sheet-2-list1-3"));
        sheet2.setListStr2(Arrays.asList("sheet-2-list2-1", "sheet-2-list2-2", "sheet-2-list2-3"));

        demoWorkbook.setSheet1(sheet1);
        demoWorkbook.setSheet2(sheet2);
        long time = System.currentTimeMillis();
        new POIUtil().writeExcel(demoWorkbook, "E:\\basepath\\a.xlsx", OutputType.XLSX);
        //TODO 设置输出地址
        System.out.println("time1：" + (System.currentTimeMillis() - time));

        time = System.currentTimeMillis();

        new POIUtil().writeExcel(demoWorkbook, "E:\\basepath\\b.xlsx", OutputType.XLSX);
        System.out.println("time2：" + (System.currentTimeMillis() - time));

    }

//    public List<String> list;
}
