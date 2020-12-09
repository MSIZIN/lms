package domain.service

import com.google.inject.ImplementedBy

@ImplementedBy(classOf[AuthServiceImpl])
trait AuthService {
  def login(request: LoginRequest): LoginResponse
}
