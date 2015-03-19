class User < ActiveRecord::Base
<<<<<<< HEAD
	has_many :rooms, dependent: :destroy
	has_secure_password    
	validates :name,  presence: true, length: { maximum: 25 }, uniqueness: { case_sensitive: false }
	validates :password,  presence: true, length: { minimum: 6}
	def User.digest(string)
    cost = ActiveModel::SecurePassword.min_cost ? BCrypt::Engine::MIN_COST :
                                                  BCrypt::Engine.cost
    BCrypt::Password.create(string, cost: cost)
=======
  has_secure_password
  has_many :api_keys

  validates :name, presence: true

  def session_api_key
    api_keys.active.session.first_or_create
>>>>>>> SX1_user_can_login
  end
end
