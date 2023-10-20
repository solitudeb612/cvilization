package cn.swust.indigo.mce.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.swust.indigo.mce.entity.po.CheckProblem;
import cn.swust.indigo.mce.entity.vo.RepairInfo;
import cn.swust.indigo.mce.mapper.CheckProblemMapper;
import cn.swust.indigo.mce.service.CheckProblemService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * (CheckProblem)表服务实现类
 *
 * @author makejava
 * @since 2023-03-25 16:03:27
 */
@Service("checkProblemService")
public class CheckProblemServiceImpl extends ServiceImpl<CheckProblemMapper, CheckProblem> implements CheckProblemService {
    @Autowired
    private CheckProblemMapper checkProblemMapper;

    @Override
    public void repairCheckProblemsByList(List<CheckProblem> checkProblems) {
        checkProblemMapper.updateByList(checkProblems);
    }

    @Override
    public IPage<CheckProblem> getCheckProblemPage(Page<CheckProblem> page, String address, String name, Integer type) {
        return checkProblemMapper.getCheckProblemPage(page, address, name, type);
    }

    @Override
    public CheckProblem allInfo(Integer checkProblemId) {
        return checkProblemMapper.allInfo(checkProblemId);
    }

    @Override
    public boolean addRepairInfo(List<RepairInfo> repairInfos) {
        if (repairInfos == null || repairInfos.isEmpty()){
            return false;
        }
        Map<Integer, RepairInfo> infos = repairInfos.stream().collect(Collectors.toMap(RepairInfo::getCheckProblemId, repairInfo -> repairInfo));
        List<CheckProblem> checkProblems = checkProblemMapper.selectBatchIds(infos.keySet());
        if (checkProblems == null || checkProblems.isEmpty()) {
            return false;
        }
        for (CheckProblem checkProblem : checkProblems) {
            Integer id = checkProblem.getCheckProblemId();
            checkProblem.setRepairerName(infos.get(id).getRepairerName());
            checkProblem.setCommitDevice(infos.get(id).getCommitDevice());
            checkProblem.setRepairerTime(infos.get(id).getRepairedTime());
            checkProblem.setRepairedTime(new Date());
        }
        this.updateBatchById(checkProblems);
        return true;
    }
}

