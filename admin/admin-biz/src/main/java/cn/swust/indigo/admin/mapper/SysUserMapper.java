package cn.swust.indigo.admin.mapper;


import cn.swust.indigo.admin.entity.dto.UserDto;
import cn.swust.indigo.admin.entity.po.SysUser;
import cn.swust.indigo.admin.entity.vo.UserVO;
import cn.swust.indigo.common.data.mybatis.datascope.DataScope;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser findSysUserByUserName(String username);


    /**
     * 通过用户名查询用户信息（含有角色信息）
     *
     * @param username 用户名
     * @return userVo
     */
    UserVO getUserVoByUsername(String username);

    UserVO getUserVoByWxOpenId(String wxOpenId);

    /**
     * 分页查询用户信息（含角色）
     *
     * @param userDTO   查询参数
     * @param dataScope
     * @return list
     */
    List<UserVO> getUserVosPage(@Param("beforeSize") long beforeSize,
                                       @Param("current") long current,
                                       @Param("query") UserDto userDTO, DataScope dataScope);

    /**
     * 不分页查询用户信息（含角色）
     *
     * @param userDTO   查询参数
     * @param dataScope
     * @return list
     */
    List<UserVO> getUserVosList(@Param("query") UserDto userDTO, DataScope dataScope);

    /**
     * 通过ID查询用户信息
     *
     * @param id 用户ID
     * @return userVo
     */
    UserVO getUserVoById(Integer id);

    /**
     * 通过用户ID修改用户密码
     *
     * @param userId   用户ID
     * @param password 密码
     * @return 整数
     */
    @Update("update sys_user set password = #{password} where user_id = #{userId}")
    int changePassword(@Param("userId") Integer userId, @Param("password") String password);


    int selectTotal(@Param("beforeSize") long beforeSize,@Param("current") long size
            ,@Param("query") UserDto userDTO, DataScope dataScope);

    List<UserVO> getNotLeaderUser(long beforeSize, long current, UserDto userDTO, DataScope dataScope);

}
