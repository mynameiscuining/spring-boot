package cn.njyazheng.springboot.usecustom;

public class DataSourceHolder {
    private static final ThreadLocal<String> datasourceholder = new ThreadLocal<>();

    public static void setDatasourceKey(String dsKey) {
        datasourceholder.set(dsKey);
    }

    public static String getDatasourceKey() {
        return datasourceholder.get();
    }

    public static void clearDatasourceKey() {
        datasourceholder.remove();
    }

}
