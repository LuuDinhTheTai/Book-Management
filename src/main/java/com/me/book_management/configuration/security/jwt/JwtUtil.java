package com.me.book_management.configuration.security.jwt;

import com.me.book_management.entity.account.Account;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;


@Component
@Slf4j
public class JwtUtil {

  @NonFinal
  @Value("${jwt.signerKey}")
  protected String SIGNER_KEY;
  @NonFinal
  @Value("${jwt.valid-duration}")
  protected long VALID_DURATION;

  public String generateToken(Account account) {
    JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

    JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                                        .subject(account.getUsername())
                                        .issuer("tts.taildt")
                                        .issueTime(new Date())
                                        .expirationTime(new Date(
                                                Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
                                        .jwtID(UUID.randomUUID().toString())
                                        .claim("scope", buildScope(account))
                                        .build();

    Payload payload = new Payload(jwtClaimsSet.toJSONObject());

    JWSObject jwsObject = new JWSObject(header, payload);

    try {
      jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
      return jwsObject.serialize();

    } catch (JOSEException e) {
      log.error("Cannot create token", e);
      throw new RuntimeException(e);
    }
  }

  private String buildScope(Account account) {
    StringJoiner stringJoiner = new StringJoiner(" ");

    if (!CollectionUtils.isEmpty(account.getRoles()))
      account.getRoles().forEach(role -> {
        stringJoiner.add("ROLE_" + role.getName());
        if (!CollectionUtils.isEmpty(role.getPermissions()))
          role.getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));
      });

    System.out.println(stringJoiner.toString());
    return stringJoiner.toString();
  }
}
