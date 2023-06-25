package io.geekidea.boot.generator.config;

import com.baomidou.mybatisplus.annotation.IdType;
import io.geekidea.boot.framework.exception.BusinessException;
import io.geekidea.boot.framework.page.BasePageQuery;
import io.geekidea.boot.framework.page.OrderByItem;
import io.geekidea.boot.framework.page.Paging;
import io.geekidea.boot.framework.response.ApiResult;
import io.geekidea.boot.framework.service.BaseService;
import io.geekidea.boot.framework.service.impl.BaseServiceImpl;
import io.geekidea.boot.generator.enums.DefaultOrderType;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * @author geekidea
 * @date 2022/4/19
 **/
@Slf4j
@Data
@Accessors(chain = true)
public class GeneratorConfig {

    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 驱动名称
     */
    private String driver;
    /**
     * 驱动URL
     */
    private String url;
    /**
     * 生成的类路径
     */
    private String projectPackagePath;
    /**
     * 项目主包路径
     */
    private String parentPackage;
    /**
     * 包名称
     */
    private String packageController = "controller";
    /**
     * 模块名称
     */
    private String moduleName;
    /**
     * 作者
     */
    private String author;
    /**
     * 生成的表名称数组
     */
    private Set<String> tableNames;
    /**
     * 分页列表查询是否排序 true：有排序参数/false：无
     */
    private boolean pageListOrder = true;
    /**
     * 是否生成validation校验，true：生成/false：不生成
     */
    private boolean paramValidation = true;
    /**
     * 是否生成实体类
     */
    private boolean generatorEntity = true;
    /**
     * 是否生成控制器
     */
    private boolean generatorController = true;
    /**
     * 是否生成service接口
     */
    private boolean generatorService = true;
    /**
     * 是否生成service实现类
     */
    private boolean generatorServiceImpl = true;
    /**
     * 是否生成Mapper
     */
    private boolean generatorMapper = true;
    /**
     * 是否生成Mapper XML
     */
    private boolean generatorMapperXml = true;
    /**
     * 是否生成AddDto参数
     */
    private boolean generatorAddDto = true;
    /**
     * 是否生成UpdateDto参数
     */
    private boolean generatorUpdateDto = true;
    /**
     * 是否生成查询参数
     */
    private boolean generatorQuery = true;
    /**
     * 是否生成查询VO
     */
    private boolean generatorVo = true;
    /**
     * 是否生成详情VO
     */
    private boolean generatorInfoVo = true;
    /**
     * 是否生成Shiro RequiresPermissions 注解
     */
    private boolean requiresPermissions;
    /**
     * 公共父包
     */
    private String commonParentPackage;
    /**
     * 实体父类
     */
    private String superEntity;
    /**
     * 控制器父类
     */
    private String superController;
    /**
     * service父接口
     */
    private String superService;
    /**
     * service实现父类
     */
    private String superServiceImpl;
    /**
     * 查询参数父类
     */
    private String superQueryParam;
    /**
     * 实体父类实体列表
     */
    private String[] superEntityCommonColumns;
    /**
     * 公共id参数路径
     */
    private String commonIdParam;

    // 公共类包路径
    /**
     * 公共结果集路径
     */
    private String commonApiResult;
    /**
     * 公共排序枚举
     */
    private String commonOrderEnum;
    /**
     * 公共排序查询参数
     */
    private String superQuery;
    /**
     * 公共分页对象
     */
    private String commonPaging;
    /**
     * 公共业务异常
     */
    private String commonBusinessException;
    /**
     * 公共排序
     */
    private String commonOrderByItem;
    /**
     * 是否文件覆盖
     */
    private boolean fileOverride;
    /**
     * 是否生成resultMap,默认为false
     */
    private boolean baseResultMap;
    /**
     * 公共字段
     */
    private List<String> commonFields = new ArrayList<>();
    /**
     * vo排除字段
     */
    private List<String> voExcludeFields = new ArrayList<>();
    /**
     * dto包名称
     */
    private String dtoPackage;
    /**
     * query包名称
     */
    private String queryPackage;
    /**
     * vo包名称
     */
    private String voPackage;
    /**
     * 主键生成类型
     */
    private IdType idType = IdType.ASSIGN_ID;
    /**
     * 是否生成查询参数
     */
    private boolean onlyOverrideEntity;
    /**
     * 表前缀
     */
    private String[] tablePrefix;
    /**
     * 默认排序类型
     */
    private DefaultOrderType defaultOrderType = DefaultOrderType.PK_ID;
    /**
     * 创建时间列名称
     */
    private String createTimeColumn = "create_time";
    /**
     * 修改时间列名称
     */
    private String updateTimeColumn = "update_time";
    /**
     * schema
     */
    private String schema;
    /**
     * 是否生成权限编码
     */
    private boolean permission = true;
    /**
     * 是否生成简单模式，只生成对应的类，不生成方法
     */
    private boolean simple = false;

    public GeneratorConfig() throws Exception {
        String projectPath = System.getProperty("user.dir" );
        String configFilePath = projectPath + "/src/test/resources/generator.properties";
        InputStream inputStream = new FileInputStream(configFilePath);
        Properties properties = new Properties();
        properties.load(inputStream);
        String driver = properties.getProperty("driver" );
        String url = properties.getProperty("url" );
        String username = properties.getProperty("username" );
        String password = properties.getProperty("password" );
        log.info("生成代码JDBC配置信息：" );
        log.info("配置文件路径：" + configFilePath);
        log.info("driver = " + driver);
        log.info("url = " + url);
        log.info("username = " + username);
        log.info("password = " + password);
        if (StringUtils.isBlank(driver) ||
                StringUtils.isBlank(url) ||
                StringUtils.isBlank(username) ||
                StringUtils.isBlank(password)
        ) {
            throw new RuntimeException("JDBC配置异常，请检查generator.properties配置文件" );
        }
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * 初始化变量
     */
    public void init() {
        if (StringUtils.isBlank(this.parentPackage)) {
            throw new BusinessException("parentPackage不能为空" );
        }
        this.projectPackagePath = this.parentPackage.replaceAll("\\.", "/" );
        this.commonParentPackage = parentPackage + ".framework";
        this.superService = BaseService.class.getName();
        this.superServiceImpl = BaseServiceImpl.class.getName();
        this.superQueryParam = commonParentPackage + ".param.QueryParam";
        this.superEntityCommonColumns = new String[]{};

        this.commonIdParam = commonParentPackage + ".param.IdParam";
        this.commonApiResult = ApiResult.class.getName();
        this.commonOrderEnum = commonParentPackage + ".enums.OrderEnum";
        this.superQuery = BasePageQuery.class.getName();
        this.commonPaging = Paging.class.getName();
        this.commonBusinessException = BusinessException.class.getName();
        this.commonOrderByItem = OrderByItem.class.getName();
        this.commonFields = Arrays.asList("remark", "version", "deleted", "createTime", "updateTime" );
        this.voExcludeFields = Arrays.asList("password", "version", "deleted" );

        this.dtoPackage = parentPackage + "." + moduleName + ".dto";
        this.queryPackage = parentPackage + "." + moduleName + ".query";
        this.voPackage = parentPackage + "." + moduleName + ".vo";

        if (onlyOverrideEntity) {
            this.generatorEntity = true;
            this.generatorAddDto = false;
            this.generatorUpdateDto = false;
            this.generatorQuery = false;
            this.generatorVo = false;
            this.generatorController = false;
            this.generatorService = false;
            this.generatorServiceImpl = false;
            this.generatorMapper = false;
            this.generatorMapperXml = false;
        }
        if (simple) {
            this.generatorAddDto = false;
            this.generatorUpdateDto = false;
            this.generatorQuery = false;
            this.generatorVo = false;
        }
    }

    public GeneratorConfig setTableNames(String... tableNames) {
        if (ArrayUtils.isNotEmpty(tableNames)) {
            Set<String> set = new LinkedHashSet<>();
            for (String tableName : tableNames) {
                if (StringUtils.isNotBlank(tableName)) {
                    tableName = handlerTableName(tableName);
                    set.add(tableName);
                }
            }
            this.tableNames = set;
        }
        return this;
    }

    private String handlerTableName(String tableName) {
        return tableName;
    }

    public GeneratorConfig setTablePrefix(String... tablePrefix) {
        this.tablePrefix = tablePrefix;
        return this;
    }

}
