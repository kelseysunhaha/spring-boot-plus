package io.geekidea.boot.system.controller;

import io.geekidea.boot.system.vo.SysLogVo;
import io.geekidea.boot.auth.annotation.Permission;
import io.geekidea.boot.framework.page.Paging;
import io.geekidea.boot.framework.response.ApiResult;
import io.geekidea.boot.system.query.SysLogQuery;
import io.geekidea.boot.system.service.SysLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 系统日志 控制器
 *
 * @author geekidea
 * @since 2023-02-16
 */
@Slf4j
@RestController
@Tag(name = "系统日志")
@RequestMapping("/admin/sysLog")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    /**
     * 获取系统日志详情
     *
     * @param id
     * @return
     * @throws Exception
     */
    @PostMapping("/getSysLog/{id}")
    @Operation(summary = "系统日志详情")
    @Permission("sys:log:info")
    public ApiResult<SysLogVo> getSysLog(@PathVariable Long id) throws Exception {
        SysLogVo sysLogVo = sysLogService.getSysLogById(id);
        return ApiResult.success(sysLogVo);
    }

    /**
     * 系统日志分页列表
     *
     * @param query
     * @return
     * @throws Exception
     */
    @PostMapping("/getSysLogPage")
    @Operation(summary = "系统日志分页列表")
    @Permission("sys:log:page")
    public ApiResult<SysLogVo> getSysLogPage(@Valid @RequestBody SysLogQuery query) throws Exception {
        Paging<SysLogVo> paging = sysLogService.getSysLogPage(query);
        return ApiResult.success(paging);
    }

}
