package io.geekidea.boot.config.properties;

import io.geekidea.boot.common.enums.FileServerType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 上传文件属性配置
 *
 * @author geekidea
 * @date 2023/06/18
 **/
@Data
@ConfigurationProperties(prefix = "file")
public class FileProperties {

    /**
     * 文件服务类型
     */
    private FileServerType fileServerType;

}
