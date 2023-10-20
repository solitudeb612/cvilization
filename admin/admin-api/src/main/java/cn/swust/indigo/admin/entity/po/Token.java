package cn.swust.indigo.admin.entity.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class Token implements Serializable {
    String accessToken;
    //    String tokenType="JWT";
    String refreshToken;
    int expiresIn = 24 * 60 * 60;
    String license = "西南科技大学-软件开发平台课程管理";
    Integer userId;
    String username;

    public Token(int userId, String username, String accessToken, String refreshToken) {
        this.userId = userId;
        this.username = username;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
