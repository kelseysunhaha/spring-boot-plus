package io.geekidea.boot.demo.query;

import io.geekidea.boot.framework.page.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * App用户端数据权限测试查询参数
 *
 * @author geekidea
 * @since 2023-12-02
 */
@Data
@Schema(description = "App用户端数据权限测试查询参数")
public class DataRangeAppTestAppQuery extends BasePageQuery {
    private static final long serialVersionUID = 1L;

}
