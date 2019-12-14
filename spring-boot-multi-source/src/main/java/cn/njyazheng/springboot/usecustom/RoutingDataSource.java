package cn.njyazheng.springboot.usecustom;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.Nullable;

public class RoutingDataSource extends AbstractRoutingDataSource {


    /**
     * 获取路由的数据源key
     *
     * @return
     */
    @Override
    @Nullable
    protected Object determineCurrentLookupKey() {
        return DataSourceHolder.getDatasourceKey();
    }

}
