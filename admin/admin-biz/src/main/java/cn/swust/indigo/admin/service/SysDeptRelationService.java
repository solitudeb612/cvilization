package cn.swust.indigo.admin.service;

import cn.swust.indigo.admin.entity.po.SysDept;
import cn.swust.indigo.admin.entity.po.SysDeptRelation;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 */
public interface SysDeptRelationService extends IService<SysDeptRelation> {

    /**
     * 新建部门关系
     *
     * @param sysDept 部门
     */
    void insertDeptRelation(SysDept sysDept);

    /**
     * 通过ID删除部门关系
     *
     * @param id
     */
    void deleteAllDeptRealtion(Integer id);

    /**
     * 更新部门关系
     *
     * @param relation
     */
    void updateDeptRealtion(SysDeptRelation relation);
}
