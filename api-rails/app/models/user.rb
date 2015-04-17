
class User < ActiveRecord::Base
  
  has_many :rooms, dependent: :destroy
  has_many :api_keys
	has_secure_password    
  VALID_EMAIL_REGEX = /\A[\w+\-.]+@[a-z\d\-.]+\.[a-z]+\z/i 
  validates :email, length: { maximum: 255 }, :allow_nil => true,
                    format: { with: VALID_EMAIL_REGEX },
                   uniqueness: { case_sensitive: false }
                   
	validates :name,  presence: true, length: { maximum: 25 }, uniqueness: { case_sensitive: false }
	validates :password,  presence: true, length: { minimum: 6}
	def User.digest(string)
    cost = ActiveModel::SecurePassword.min_cost ? BCrypt::Engine::MIN_COST :
                                                  BCrypt::Engine.cost
    BCrypt::Password.create(string, cost: cost)
  end
    def session_api_key
    api_keys.active.session.first_or_create


  end
end
