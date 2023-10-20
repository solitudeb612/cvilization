package cn.swust.indigo.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.swust.indigo.admin.entity.po.SysDept;
import cn.swust.indigo.admin.entity.po.SysDeptRelation;
import cn.swust.indigo.admin.mapper.SysDeptRelationMapper;
import cn.swust.indigo.admin.service.SysDeptRelationService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 */
@Service
@AllArgsConstructor
public class SysDeptRelationServiceImpl extends ServiceImpl<SysDeptRelationMapper, SysDeptRelation>
        implements SysDeptRelationService {
    private final SysDeptRelationMapper sysDeptRelationMapper;

    /**
     * 维护部门关系
     *
     * @param sysDept 部门
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertDeptRelation(SysDept sysDept) {
        //增加部门关系表
        SysDeptRelation condition = new SysDeptRelation();
        condition.setDescendant(sysDept.getParentId());

        List<SysDeptRelation> relationList = sysDeptRelationMapper
                .selectList(Wrappers.<SysDeptRelation>query().lambda()
                        // 选出关系表中后代节点是 当前节点的
                        .eq(SysDeptRelation::getDescendant, sysDept.getParentId()))
                .stream().map(relation -> {
                    relation.setDescendant(sysDept.getDeptId());
                    return relation;
                }).collect(Collectors.toList());

        if (CollUtil.isNotEmpty(relationList)) {
            this.saveBatch(relationList);
        }

        //自己也要维护到关系表中
        SysDeptRelation own = new SysDeptRelation();
        own.setDescendant(sysDept.getDeptId());
        own.setAncestor(sysDept.getDeptId());
        sysDeptRelationMapper.insert(own);
    }

    /**
     * 通过ID删除部门关系
     *
     * @param id
     */
    @Override
    public void deleteAllDeptRealtion(Integer id) {
        sysDeptRelationMapper.deleteDeptRelationsById(id);
    }

    /**
     * 更新部门关系
     *
     * @param relation
     */
    @Override
    public void updateDeptRealtion(SysDeptRelation relation) {
        sysDeptRelationMapper.updateDeptRelations(relation);
    }

}
