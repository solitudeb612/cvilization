package cn.swust.indigo.mce.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.swust.indigo.mce.entity.vo.QCheckGroup;
import cn.swust.indigo.mce.mapper.CheckGroupMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.swust.indigo.mce.entity.po.CheckGroup;
import cn.swust.indigo.mce.service.CheckGroupService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 *
 */
@Service
public class CheckGroupServiceImpl extends ServiceImpl<CheckGroupMapper, CheckGroup>
    implements CheckGroupService{

    @Override
    public List<CheckGroup> getGroupChecksByPy(String py) {
        if(StringUtils.isEmpty(py)){
            return this.list();
        }
        LambdaQueryWrapper<CheckGroup> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(CheckGroup::getGroupPy,py);
        List<CheckGroup> list = this.list(queryWrapper);
        return list;
    }

    @Override
    public Boolean updateCheckGroup(QCheckGroup qCheckGroup) {
        CheckGroup checkGroup = new CheckGroup();
        BeanUtil.copyProperties(qCheckGroup, checkGroup,false);
        boolean update = this.updateById(checkGroup);
        return BooleanUtil.isTrue(update);
    }
}




