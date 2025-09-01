package com.supsp.springboot.core.utils;

import com.supsp.springboot.core.config.CoreProperties;
import com.supsp.springboot.core.enums.AccountType;
import com.supsp.springboot.core.enums.AuthMemberType;
import com.supsp.springboot.core.enums.LoginType;
import com.supsp.springboot.core.vo.auth.AuthMember;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

import static io.jsonwebtoken.Jwts.SIG;

@Component
public class JwtUtil {

    private SecretKey key;
    private long expirationMs;

    @Resource
    private HttpServletRequest request;

    @Resource
    private HttpServletResponse response;

    @Resource
    private CoreProperties coreProperties;

    @jakarta.annotation.PostConstruct
    public void init() {
        // 确保密钥至少有32个字符（256位）以满足JWT安全要求
        String secret = coreProperties.getSecret();
        if (StrUtils.isBlank(secret) || secret.length() < 32) {
            // 如果密钥太短，使用一个默认的安全密钥
            secret = "supsp_default_secret_key_for_jwt_authentication_20250901";
        }
        // 将密钥转换为Base64编码并创建HMAC-SHA256密钥
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationMs = ((long) coreProperties.getAuthExpires()) * 60 * 1000;
    }

    public String tokenKeyName(
            AuthMemberType type,
            String sid
    ) {
        return "token::" + type.getCode() + "::" + sid;
    }

    public String generateToken(AuthMember member) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                // 设置JWT的subject为userId
                .subject(member.getMemberId().toString())
                // 设置额外的claim（如用户名）
                .claim("loginType", member.getLoginType())
                .claim("memberType", member.getMemberType())
                .claim("loginAccount", member.getLoginAccount())
                .claim("accountType", member.getAccountType())
                .claim("loginPwd", CryptUtils.encrypt(member.getLoginPwd()))
                // 设置签发时间
                .issuedAt(now)
                // 设置过期时间
                .expiration(expiryDate)
                // 显式指定算法 // 使用HS256算法进行签名
                .signWith(key, SIG.HS256)
                // 返回生成的JWT令牌
                .compact();
    }

    /**
     * 验证Token
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 从JWT中提取用户ID
     *
     * @param token JWT令牌
     * @return 用户ID
     */
    public Long extractMemberId(String token) {
        // 提取userId（subject部分）
        return Long.parseLong(extractClaim(token, Claims::getSubject));
    }

    /**
     * 从JWT中提取用户名
     *
     * @param token JWT令牌
     * @return 用户名
     */
    public String extractLoginAccount(String token) {
        return extractClaim(token, claims -> claims.get("loginAccount", String.class));
    }

    public AccountType extractAccountType(String token) {
        String accountType = extractClaim(token, claims -> claims.get("accountType", String.class));
        if (StrUtils.isBlank(accountType)) {
            return null;
        }
        return AccountType.getByCode(accountType);
    }

    public LoginType extractLoginType(String token) {
        String loginType = extractClaim(token, claims -> claims.get("loginType", String.class));
        if (StrUtils.isBlank(loginType)) {
            return null;
        }
        return LoginType.getByCode(loginType);
    }

    public AuthMemberType extractAuthMemberType(String token) {
        String memberType = extractClaim(token, claims -> claims.get("memberType", String.class));
        if (StrUtils.isBlank(memberType)) {
            return null;
        }
        return AuthMemberType.getByCode(memberType);
    }

    public String extractLoginPwd(String token) {
        return extractClaim(token, claims -> claims.get("loginPwd", String.class));
//        if (StrUtils.isBlank(pwd)) {
//            return null;
//        }
//        return CryptUtils.decrypt(pwd);
    }

    /**
     * 从JWT中提取特定的claim（声明）
     *
     * @param token           JWT令牌
     * @param claimsExtractor 提取函数
     * @param <T>             提取的结果类型
     * @return 提取的claim值
     */
    public <T> T extractClaim(String token, ClaimsExtractor<T> claimsExtractor) {
        // 获取JWT中的所有claims
        final Claims claims = extractAllClaims(token);
        // 提取指定的claim
        return claimsExtractor.extract(claims);
    }

    /**
     * 解析JWT中的所有claims
     *
     * @param token JWT令牌
     * @return JWT中的所有claims
     */
    public Claims extractAllClaims(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return claims.getPayload();
        } catch (Exception e) {
            //
        }
        return null;
    }

    public Date getIssuedAt(String token){
        Claims claims = extractAllClaims(token);
        if(claims == null){
            return null;
        }
        return claims.getIssuedAt();
    }

    public Date getExpiration(String token){
        Claims claims = extractAllClaims(token);
        if(claims == null){
            return null;
        }
        return claims.getExpiration();
    }

    /**
     * 验证JWT是否过期
     *
     * @param token JWT令牌
     * @return 是否过期
     */
    public boolean isTokenExpired(String token) {
        // 判断过期时间
        return extractExpiration(token).before(new Date());
    }

    /**
     * 提取JWT的过期时间
     *
     * @param token JWT令牌
     * @return 过期时间
     */
    private Date extractExpiration(String token) {
        // 获取过期时间
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * 验证JWT的有效性
     *
     * @param token  JWT令牌
     * @param userId 用户ID
     * @return JWT是否有效
     */
    public boolean validateToken(String token, Long userId) {
        // 验证userId是否匹配并检查是否过期
        return (userId.equals(extractMemberId(token)) && !isTokenExpired(token));
    }

    /**
     * ClaimsExtractor接口，用于提取JWT中的具体claim
     *
     * @param <T> 提取的结果类型
     */
    @FunctionalInterface
    public interface ClaimsExtractor<T> {
        // 从claims中提取信息
        T extract(Claims claims);
    }

}
