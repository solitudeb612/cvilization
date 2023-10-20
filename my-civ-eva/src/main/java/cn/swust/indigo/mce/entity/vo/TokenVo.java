package cn.swust.indigo.mce.entity.vo;

import cn.swust.indigo.admin.entity.po.Token;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class TokenVo extends Token {
   private String roleName;
   public TokenVo(int userId, String username, String accessToken, String refreshToken, String roleName) {
      super(userId, username, accessToken, refreshToken);
      this.roleName = roleName;
   }
   public String getRoleName() {
      return roleName;
   }

   public void setRoleName(String roleName) {
      this.roleName = roleName;
   }
}
