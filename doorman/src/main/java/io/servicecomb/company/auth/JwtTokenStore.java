/*
 * Copyright 2017 Huawei Technologies Co., Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.servicecomb.company.auth;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * {@link JwtTokenStore} implements {@link TokenStore} with JWT token specifications.
 */
class JwtTokenStore implements TokenStore {

  private final static String DEFAULT_SECRETKEY = "someSecretKey";
  private final String secretKey;
  private final int secondsToExpire;

  /**
   * Constructor
   * @param secretKey the signing key to encrypt the token.
   * @param secondsToExpire the expire time in seconds.
   */
  JwtTokenStore(String secretKey, int secondsToExpire) {
    if (null == secretKey || secretKey.isEmpty()) {
      this.secretKey = DEFAULT_SECRETKEY;
    } else {
      this.secretKey = secretKey;
    }
    this.secondsToExpire = secondsToExpire;
  }

  @Override
  public String generate(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setExpiration(Date.from(ZonedDateTime.now().plusSeconds(secondsToExpire).toInstant()))
        .signWith(HS512, secretKey)
        .compact();
  }

  @Override
  public String parse(String token) {
    try {
      return Jwts.parser()
          .setSigningKey(secretKey)
          .parseClaimsJws(token)
          .getBody()
          .getSubject();
    } catch (JwtException | IllegalArgumentException e) {
      throw new TokenException(e);
    }
  }
}
