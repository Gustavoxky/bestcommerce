package com.example.bestcommerce1.controller

import com.example.bestcommerce1.dtos.Message
import com.example.bestcommerce1.dtos.RegisterDTO
import com.example.bestcommerce1.dtos.LoginDTO
import com.example.bestcommerce1.model.User
import com.example.bestcommerce1.service.UserService
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

object JwtConfig {
    const val TOKEN_EXPIRATION_TIME = 60 * 24 * 1000 // 1 day
    const val YOUR_SECRET_KEY = "yourSecretKey"
}

@RestController
@RequestMapping("/api")
class UserController(private val userService: UserService) {

    @PostMapping("/register")
    fun register(@RequestBody body: RegisterDTO): ResponseEntity<User> {
        val user = User().apply {
            username = body.username
            email = body.email
            password = body.password
        }

        return ResponseEntity.ok(userService.save(user))
    }

    @PostMapping(value = ["/login"], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun login(@RequestBody body: LoginDTO, response: HttpServletResponse): ResponseEntity<Any> {
        try {
            val user = userService.findByEmail(body.email)
                ?: return ResponseEntity.badRequest().body(Message("Usuário não encontrado!"))

            if (!user.comparePassword(body.password)) {
                return ResponseEntity.badRequest().body(Message("Senha inválida!"))
            }

            val issuer = user.id.toString()

            val jwt = Jwts.builder()
                .setIssuer(issuer)
                .setExpiration(Date(System.currentTimeMillis() + JwtConfig.TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, JwtConfig.YOUR_SECRET_KEY).compact()

            val cookie = Cookie("jwt", jwt)
            cookie.isHttpOnly = true
            cookie.path = "/" // Defina o caminho conforme necessário
            // cookie.domain = "seuDominio.com" // Defina o domínio conforme necessário
            response.addCookie(cookie)

            return ResponseEntity.ok(Message("Login bem-sucedido"))
        } catch (e: ExpiredJwtException) {
            return ResponseEntity.status(401).body(Message("Token expirado"))
        } catch (e: Exception) {
            return ResponseEntity.status(401).body(Message("Erro durante o login: ${e.message}"))
        }
    }

    @GetMapping("/user")
    fun user(@CookieValue("jwt") jwt: String?): ResponseEntity<Any> {
        try {
            if (jwt == null) {
                return ResponseEntity.status(401).body(Message("Não autenticado"))
            }

            val body = Jwts.parser().setSigningKey(JwtConfig.YOUR_SECRET_KEY).parseClaimsJws(jwt).body

            return ResponseEntity.ok(userService.getById(body.issuer.toInt()))
        } catch (e: ExpiredJwtException) {
            return ResponseEntity.status(401).body(Message("Token expirado"))
        } catch (e: Exception) {
            return ResponseEntity.status(401).body(Message("Erro durante a verificação do token: ${e.message}"))
        }
    }

    @PostMapping("logout")
    fun logout(response: HttpServletResponse): ResponseEntity<Any> {
        val cookie = Cookie("jwt", "")
        cookie.maxAge = 0
        response.addCookie(cookie)

        return ResponseEntity.ok(Message("Logout bem-sucedido"))
    }
}
