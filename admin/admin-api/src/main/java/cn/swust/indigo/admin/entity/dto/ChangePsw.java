package cn.swust.indigo.admin.entity.dto;

import lombok.Data;

@Data
public class ChangePsw {
    /*
    用户id
     */
    Integer userId;
    /*
    旧密码
     */
    String oldPsw;
    /*
    新密码
     */
    String newPsw;
}
