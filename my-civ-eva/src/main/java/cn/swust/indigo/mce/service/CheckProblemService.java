package cn.swust.indigo.mce.service;

import cn.swust.indigo.mce.entity.po.CheckProblem;
import cn.swust.indigo.mce.entity.vo.RepairInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * (CheckProblem)表服务接口
 *
 * @author makejava
 * @since 2023-03-25 16:03:25
 */
public interface CheckProblemService extends IService<CheckProblem> {

    void repairCheckProblemsByList(List<CheckProblem> checkProblems);

    IPage<CheckProblem> getCheckProblemPage(@Param("page") Page<CheckProblem> page,@Param("address")String address,@Param("name")String name,@Param("type")Integer type);

    CheckProblem allInfo(Integer checkProblemId);

    boolean addRepairInfo(List<RepairInfo> repairInfos);
}

