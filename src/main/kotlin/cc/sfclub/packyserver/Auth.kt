package cc.sfclub.packyserver

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

object Auth {
    private const val SECRET_KEY = "c2ZjbHViX3Byb2plY3RfcGFja3kKIA=="
    private val algorithm = Algorithm.HMAC512(SECRET_KEY)
    private const val issuer = "pkg.sfclub.cc"
    private const val validityInMs = 3600*1000 //1h

    fun makeJwtVerifier(): JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .build()

    fun sign(name: String): String {
        return makeToken(name)
    }

    private fun makeToken(name: String): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(issuer)
        .withClaim("user_name", name)
        .withExpiresAt(getExpiration())
        .sign(algorithm)

    private fun getExpiration() = Date(System.currentTimeMillis() + validityInMs)

}