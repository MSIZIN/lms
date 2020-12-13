package domain.service

import com.google.inject.ImplementedBy
import domain.model.SessionId
import domain.service.messages._

@ImplementedBy(classOf[AuthServiceImpl])
trait AuthService {
  def login(request: LoginRequest): LoginResponse
  def signup(request: SignupRequest): SignupResponse
  def whoami(sessionId: SessionId): WhoamiResponse
  def updatePassword(request: UpdatePasswordRequest): UpdatePasswordResponse
}
