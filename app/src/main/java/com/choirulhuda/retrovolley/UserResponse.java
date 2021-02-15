package com.choirulhuda.retrovolley;

import java.util.List;

import androidx.annotation.NonNull;

public class UserResponse {

    private int code;
    private String status;
    private List<User> user_list;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<User> getUser_list() {
        return user_list;
    }

    public void setUser_list(List<User> user_list) {
        this.user_list = user_list;
    }

    public class User {

        private int id;
        private String user_email;
        private String user_password;
        private String user_fullname;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUser_email() {
            return user_email;
        }

        public void setUser_email(String user_email) {
            this.user_email = user_email;
        }

        public String getUser_password() {
            return user_password;
        }

        public void setUser_password(String user_password) {
            this.user_password = user_password;
        }

        public String getUser_fullname() {
            return user_fullname;
        }

        public void setUser_fullname(String user_fullname) {
            this.user_fullname = user_fullname;
        }

        @NonNull
        @Override
        public String toString() {
            return "Result( " +
                    "id : "+id+
                    "email : "+user_email + "\n"+
                    ")";
        }
    }
}
