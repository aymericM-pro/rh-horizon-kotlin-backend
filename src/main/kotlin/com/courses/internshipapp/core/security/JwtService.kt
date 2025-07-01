package com.courses.internshipapp.core.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.Date

@Service
class JwtService {

    private val secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    fun extractUsername(token: String): String =
        Jwts.parserBuilder().setSigningKey(secretKey).build()
            .parseClaimsJws(token).body.subject

    fun generateToken(userDetails: UserDetails): String =
        Jwts.builder()
            .setSubject(userDetails.username)
            .claim("roles", userDetails.authorities.map { it.authority })
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
            .signWith(secretKey)
            .compact()

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean =
        extractUsername(token) == userDetails.username
}
