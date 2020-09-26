//package com.wenxin.learn.faststart.web.jwtconfig;
//
//import com.wenxin.learn.faststart.web.entity.orderUser;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.Collections;
//
///**
// * @author version
// * @version 1.0
// * @date 2020/9/5 10:20
// */
//public class JwtUser implements UserDetails {
//
//    private Integer id;
//    private String username;
//    private String password;
//    private Collection<? extends GrantedAuthority> authorities;
//
//    public JwtUser() {
//    }
//
////     写一个能直接使用user创建jwtUser的构造器
//    public JwtUser(orderUser user) {
//        id = user.getId();
//        username = user.getUsername();
//        password = user.getPassword();
//        authorities = Collections.singleton(new SimpleGrantedAuthority(user.getRole()));
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return authorities;
//    }
//    @Override
//    public String getPassword() {
//        return password;
//    }
//    @Override
//    public String getUsername() {
//        return username;
//    }
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//   @Override
//    public boolean isEnabled() {
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        return "JwtUser{" +
//                "id=" + id +
//                ", username='" + username + '\'' +
//                ", password='" + password + '\'' +
//                ", authorities=" + authorities +
//                '}';
//    }
//
//}
//
