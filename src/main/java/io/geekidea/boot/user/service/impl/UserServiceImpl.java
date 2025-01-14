package io.geekidea.boot.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.geekidea.boot.framework.exception.BusinessException;
import io.geekidea.boot.framework.page.OrderByItem;
import io.geekidea.boot.framework.page.Paging;
import io.geekidea.boot.user.dto.UserDto;
import io.geekidea.boot.user.entity.User;
import io.geekidea.boot.user.mapper.UserMapper;
import io.geekidea.boot.user.query.UserAppQuery;
import io.geekidea.boot.user.query.UserQuery;
import io.geekidea.boot.user.service.UserService;
import io.geekidea.boot.user.vo.UserAppVo;
import io.geekidea.boot.user.vo.UserVo;
import io.geekidea.boot.util.PagingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 用户信息 服务实现类
 *
 * @author geekidea
 * @since 2023-11-25
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByOpenid(String openid) throws Exception {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getOpenid, openid);
        return getOne(wrapper);
    }

    @Override
    public User getUserByUsername(String username) throws Exception {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        return getOne(wrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addUser(UserDto userDto) throws Exception {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        return save(user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateUser(UserDto userDto) throws Exception {
        Long id = userDto.getId();
        User user = getById(id);
        if (user == null) {
            throw new BusinessException("用户信息不存在");
        }
        BeanUtils.copyProperties(userDto, user);
        user.setUpdateTime(new Date());
        return updateById(user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteUser(Long id) throws Exception {
        return removeById(id);
    }

    @Override
    public UserVo getUserById(Long id) throws Exception {
        return userMapper.getUserById(id);
    }

    @Override
    public Paging<UserVo> getUserPage(UserQuery query) throws Exception {
        PagingUtil.handlePage(query, OrderByItem.desc("id"));
        List<UserVo> list = userMapper.getUserPage(query);
        Paging<UserVo> paging = new Paging<>(list);
        return paging;
    }

    @Override
    public UserAppVo getAppUserById(Long id) throws Exception {
        return userMapper.getAppUserById(id);
    }

    @Override
    public Paging<UserAppVo> getAppUserPage(UserAppQuery query) throws Exception {
        PagingUtil.handlePage(query, OrderByItem.desc("id"));
        List<UserAppVo> list = userMapper.getAppUserPage(query);
        Paging<UserAppVo> paging = new Paging<>(list);
        return paging;
    }

}
