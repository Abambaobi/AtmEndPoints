package com.example.service;
import com.example.model.AtmUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {
    //    secrete key so the front end can verify that the jwt is from the right server
//    secrete key helps the server to verify that the jwt is it is receiving is the exact one he generated and has not been tempered with
    public final String secKey = "33743677397A24432646294A404E635266556A576E5A7234753778214125442A472D4B6150645367566B59703273357638792F423F4528482B4D625165546857";

//    @SneakyThrows
//    public String randomKey() {
//        SecureRandom secureRandom = new SecureRandom();
//        Base64.Encoder base64Encoder = Base64.getEncoder().withoutPadding();
//        Base64.Decoder base64Decoder = Base64.getUrlDecoder();
//        byte[] keyBytes = new byte[64];
//        secureRandom.nextBytes(keyBytes);
//        return base64Encoder.encodeToString(keyBytes);
//    }

//    public final String secKey = randomKey();

//    generating the jwt and setting claims and extraClaims

    public String generateJwt (AtmUser atmUser, Map<String, Object> extraClaims) {
//        System.out.println(atmUser.getUsername());

        return Jwts.builder()
//                .setClaims(extraClaims)
                .addClaims(extraClaims)
                .setSubject(atmUser.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date((System.currentTimeMillis() + 1000*60*10000)))
                .signWith(getKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    //    converting the key to a 64 byte then converting it to a key
    public Key getKey() {
        byte[] key = Decoders.BASE64.decode(secKey);
        return Keys.hmacShaKeyFor(key);

    }

    //    validating the jwt
    public boolean validateJwt(AtmUser atmUser, String jwt) {
        String subj = extSubject(jwt);
        String jwt_bank = getExtraClaim(jwt, "bank");
        String db_bank = String.valueOf( atmUser.getBank() );
        return subj.equals(atmUser.getUsername()) && jwtNotExpired(jwt) && jwt_bank.equals(db_bank);
    }

    public String getExtraClaim(String jwt, String extrClaimKey) {
        return  (String) claims(jwt).get(extrClaimKey);
    }
    //    getting the expiration date of the jwt
    public Date getExpiration(String jwt) {
        return claim(jwt, Claims::getExpiration);
    }

    //    checking to see if the jwt has expired
    public boolean jwtNotExpired(String jwt) {
        return getExpiration(jwt).after(new Date(System.currentTimeMillis()));
    }

    //    getting the most important claim "the Subject claim"
    public String extSubject(String jwt){
        return claim(jwt, Claims::getSubject);
    }

    //    extracting specific claim from the claims
    public <T> T claim (String jwt, Function<Claims, T> claimsResolver) {
        final Claims allClaims = claims(jwt);
        return claimsResolver.apply(allClaims);
    }

    //    extracting all claims from the jwt
    public Claims claims(String jwt) {
        return  Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

        public String expireToken(String jwt) throws Exception {

            String expired_token = Jwts.builder().setClaims( claims(jwt).setExpiration(new Date(0L)))
                    .signWith (getKey(), SignatureAlgorithm.HS512)
                    .compact();
            return(expired_token);
        }



}
