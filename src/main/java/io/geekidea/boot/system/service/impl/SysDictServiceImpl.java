package io.geekidea.boot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.geekidea.boot.framework.exception.BusinessException;
import io.geekidea.boot.framework.page.OrderByItem;
import io.geekidea.boot.framework.page.Paging;
import io.geekidea.boot.system.dto.SysDictDto;
import io.geekidea.boot.system.entity.SysDict;
import io.geekidea.boot.system.mapper.SysDictMapper;
import io.geekidea.boot.system.query.SysDictAppQuery;
import io.geekidea.boot.system.query.SysDictQuery;
import io.geekidea.boot.system.service.SysDictService;
import io.geekidea.boot.system.vo.SysDictAppVo;
import io.geekidea.boot.system.vo.SysDictVo;
import io.geekidea.boot.util.PagingUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 字典数据 服务实现类
 *
 * @author geekidea
 * @since 2023-11-25
 */
@Slf4j
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {

    @Autowired
    private SysDictMapper sysDictMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addSysDict(SysDictDto dto) throws Exception {
        SysDict sysDict = new SysDict();
        BeanUtils.copyProperties(dto, sysDict);
        return save(sysDict);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysDict(SysDictDto dto) throws Exception {
        Long id = dto.getId();
        SysDict sysDict = getById(id);
        if (sysDict == null) {
            throw new BusinessException("字典数据不存在");
        }
        BeanUtils.copyProperties(dto, sysDict);
        sysDict.setUpdateTime(new Date());
        return updateById(sysDict);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysDict(Long id) throws Exception {
        return removeById(id);
    }

    @Override
    public SysDictVo getSysDictById(Long id) throws Exception {
        return sysDictMapper.getSysDictById(id);
    }

    @Override
    public Paging<SysDictVo> getSysDictPage(SysDictQuery query) throws Exception {
        PagingUtil.handlePage(query, OrderByItem.orderBy("status desc,id desc"));
        List<SysDictVo> list = sysDictMapper.getSysDictPage(query);
        Paging<SysDictVo> paging = new Paging<>(list);
        return paging;
    }

    @Override
    public Map<String, List<SysDictAppVo>> getAppSysDictList(SysDictAppQuery query) throws Exception {
        List<SysDictAppVo> list = sysDictMapper.getAppSysDictList(query);
        if (CollectionUtils.isNotEmpty(list)) {
            Map<String, List<SysDictAppVo>> map = list.stream().collect(Collectors.groupingBy(SysDictAppVo::getDictCode));
            return map;
        }
        return null;
    }

    @Override
    public List<SysDict> getSysDictList(String dictCode) throws Exception {
        LambdaQueryWrapper<SysDict> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDict::getDictCode, dictCode);
        wrapper.eq(SysDict::getStatus, true);
        wrapper.orderByAsc(SysDict::getSort);
        return list(wrapper);
    }

    @Override
    public SysDict getSysDictByValue(String dictCode, Serializable value) throws Exception {
        LambdaQueryWrapper<SysDict> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDict::getDictCode, dictCode);
        wrapper.eq(SysDict::getValue, value);
        wrapper.eq(SysDict::getStatus, true);
        return getOne(wrapper);
    }

    @Override
    public String getSysDictLabelByValue(String dictCode, Serializable value) throws Exception {
        SysDict sysDict = getSysDictByValue(dictCode, value);
        if (sysDict != null) {
            return sysDict.getLabel();
        }
        return null;
    }

}
