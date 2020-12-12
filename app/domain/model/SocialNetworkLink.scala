package domain.model

sealed trait SocialNetworkLink
final case class VkLink(link: String) extends SocialNetworkLink
final case class FacebookLink(link: String) extends SocialNetworkLink
final case class LinkedinLink(link: String) extends SocialNetworkLink
final case class InstagramLink(link: String) extends SocialNetworkLink
