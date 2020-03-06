package com.jy.jiandao.data.entity;


import com.mr.k.libmvp.base.IUser;

import org.jetbrains.annotations.NotNull;

/*
 * created by Cherry on 2019-08-26
 **/
public class User implements IUser {

    private Token token;
    private UserInfo user_info;


    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public UserInfo getUserInfo() {
        return user_info;
    }

    public void setUserInfo(UserInfo user_info) {
        this.user_info  = user_info;
    }






    @Override
    public String toString() {
        return "User{" +
                "token=" + token +
                ", user_info=" + user_info +
                '}';
    }

    @Override
    public String getTokenValue() {
        if(token != null){
            if(token.expire_time > System.currentTimeMillis() /1000){
                return token.value;
            }
        }
        return null;

    }


    public class Token {

        private String value;
        private Long expire_time;


        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public Long getExpireTime() {
            return expire_time;
        }

        public void setExpireTime(Long expire_time) {
            this.expire_time = expire_time;
        }


        @Override
        public String toString() {
            return "Token{" +
                    "value='" + value + '\'' +
                    ", expire_time=" + expire_time +
                    '}';
        }
    }


    public class UserInfo {

        private String head_url;
        private String nickname;
        private String mobile;
        private String email;

        private String qq_openid;

        private String sina_openid;
        private int sina_bind;

        private String wechat_openid;
        private String wechat_unionid;
        private int wechat_bind;

        private int check_in_status;
        private int qq_bind;


        private int notice_count;
        private int my_integral;






        public UserInfo(int sina_bind) {
            this.sina_bind = sina_bind;
        }



        public UserInfo(String head_url, String nickname, String mobile, String email, int check_in_status) {
            this.head_url = head_url;
            this.nickname = nickname;
            this.mobile = mobile;
            this.email = email;
            this.check_in_status = check_in_status;
        }

        @Override
        public String toString() {
            return "UserInfo{" +
                    "head_url='" + head_url + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", email='" + email + '\'' +
                    ", check_in_status=" + check_in_status +
                    '}';
        }

        public String getHeadUrl() {
            return head_url;
        }

        public void setHeadUrl(String head_url) {
            this.head_url = head_url;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getCheckInStatus() {
            return check_in_status;
        }

        public void setCheckInStatus(int check_in_status) {
            this.check_in_status = check_in_status;
        }

        public String getQqOpenid() {
            return qq_openid;
        }

        public void setQqOpenid(String qq_openid) {
            this.qq_openid = qq_openid;
        }

        public String getSinaOpenid() {
            return sina_openid;
        }

        public void setSinaOpenid(String sina_openid) {
            this.sina_openid = sina_openid;
        }

        public String getWechatOpenidd() {
            return wechat_openid;
        }

        public void setWechatOpenid(String wechat_openid) {
            this.wechat_openid = wechat_openid;
        }

        public String getWechatUnionid() {
            return wechat_unionid;
        }

        public void setWechatUnionid(String wechat_unionid) {
            this.wechat_unionid = wechat_unionid;
        }

        public int getQqBind() {
            return qq_bind;
        }

        public void setQqBind(int qq_bind) {
            this.qq_bind = qq_bind;
        }

        public int getSinaBind() {
            return sina_bind;
        }

        public void setSinaBind(int sina_bind) {
            this.sina_bind = sina_bind;
        }

        public int getWechatBind() {
            return wechat_bind;
        }

        public void setWechatBind(int wechat_bind) {
            this.wechat_bind = wechat_bind;
        }

        public int getNoticeCount() {
            return notice_count;
        }

        public void setNoticeCount(int notice_count) {
            this.notice_count = notice_count;
        }

        public int getMyIntegral() {
            return my_integral;
        }

        public void setMyIntegral(int my_integral) {
            this.my_integral = my_integral;
        }
    }

}
